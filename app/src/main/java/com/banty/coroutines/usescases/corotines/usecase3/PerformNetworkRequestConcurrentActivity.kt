package com.banty.coroutines.usescases.corotines.usecase3

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.banty.coroutines.R
import com.banty.coroutines.base.BaseActivity
import com.banty.coroutines.base.useCaseDescription3
import com.banty.coroutines.databinding.ActivityPerformnetworkrequestsconcurrentBinding
import com.banty.coroutines.utils.fromHtml
import com.banty.coroutines.utils.setGone
import com.banty.coroutines.utils.setVisible
import com.banty.coroutines.utils.toast
import kotlinx.android.synthetic.main.activity_performnetworkrequestsconcurrent.*

class PerformNetworkRequestConcurrentActivity : BaseActivity() {

    private val binding: ActivityPerformnetworkrequestsconcurrentBinding by lazy {
        ActivityPerformnetworkrequestsconcurrentBinding.inflate(layoutInflater)
    }

    private val viewModel: PerformNetworkRequestsConcurrentViewModel by viewModels()

    private var operationStartTime = 0L

    override fun getToolbarTitle() = useCaseDescription3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnRequestsConcurrently.setOnClickListener {
            viewModel.performNetworkRequestsConcurrently()
        }

        binding.btnRequestsSequentially.setOnClickListener {
            viewModel.performNetworkRequestsSequentially()
        }

        viewModel.uiState().observe(this, Observer {
            render(it)
        })
    }

    private fun render(uiState: UiState) {
        when (uiState) {
            is UiState.Loading -> {
                onLoad()
            }

            is UiState.Success -> {
                onSuccess(uiState)
            }

            is UiState.Error -> {
                onError(uiState)
            }
        }
    }

    private fun onError(uiState: UiState.Error) {
        binding.progressBar.setGone()
        binding.textViewDuration.setGone()
        toast(uiState.errorMessage)
        enableButtons()
    }

    private fun onSuccess(uiState: UiState.Success) {
        enableButtons()
        progressBar.setGone()
        val duration = System.currentTimeMillis() - operationStartTime
        textViewDuration.text = getString(R.string.duration, duration)
        val versionFeatures = uiState.versionFeatures

        val versionFeaturesString = versionFeatures.joinToString(separator = "<br><br>") {
            "<b>New Features of ${it.androidVersion.name} </b> <br> ${it.features.joinToString(
                prefix = "- ",
                separator = "<br>- "
            )}"
        }
        textViewResult.text = fromHtml(versionFeaturesString)
    }

    private fun onLoad() {
        operationStartTime = System.currentTimeMillis()
        progressBar.setVisible()
        textViewDuration.text = ""
        textViewResult.text = ""
        disableButtons()
    }

    private fun enableButtons() {
        binding.btnRequestsSequentially.isEnabled = true
        binding.btnRequestsConcurrently.isEnabled = true
    }

    private fun disableButtons() {
        binding.btnRequestsSequentially.isEnabled = false
        binding.btnRequestsConcurrently.isEnabled = false
    }

}