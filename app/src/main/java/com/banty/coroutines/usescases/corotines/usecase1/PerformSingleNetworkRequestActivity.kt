package com.banty.coroutines.usescases.corotines.usecase1

import android.os.Bundle
import androidx.activity.viewModels
import com.banty.coroutines.base.BaseActivity
import com.banty.coroutines.base.useCaseDescription1
import com.banty.coroutines.databinding.ActivityPerformsinglenetworkrequestBinding
import androidx.lifecycle.Observer
import com.banty.coroutines.utils.fromHtml
import com.banty.coroutines.utils.setGone
import com.banty.coroutines.utils.setVisible
import com.banty.coroutines.utils.toast
import kotlinx.android.synthetic.main.activity_performsinglenetworkrequest.*

class PerformSingleNetworkRequestActivity : BaseActivity() {
    override fun getToolbarTitle() =
        useCaseDescription1

    private lateinit var binding: ActivityPerformsinglenetworkrequestBinding
    private val viewModel: PerformSingleNetworkRequestViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerformsinglenetworkrequestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.uiState().observe(this, Observer { uiState ->
            if (uiState != null) {
                render(uiState)
            }
        })


        binding.btnPerformSingleNetworkRequest.setOnClickListener {
            viewModel.performSingleNetworkRequest()
        }
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
        progressBar.setGone()
        btnPerformSingleNetworkRequest.isEnabled = true
        toast(uiState.message)
    }

    private fun onLoad() = with(binding) {
        progressBar.setVisible()
        textViewResult.text = ""
        btnPerformSingleNetworkRequest.isEnabled = false
    }

    private fun onSuccess(uiState: UiState.Success) = with(binding) {
        progressBar.setGone()
        btnPerformSingleNetworkRequest.isEnabled = true
        val readableVersions = uiState.recentVersions.map { "API ${it.apiLevel} :  ${it.name}" }
        textViewResult.text = fromHtml(
            "<b>Recent Android Versions</b><br>${readableVersions.joinToString(separator = "<br>")}"
        )
    }
}