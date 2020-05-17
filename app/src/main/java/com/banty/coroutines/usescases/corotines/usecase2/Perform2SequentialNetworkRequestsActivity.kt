package com.banty.coroutines.usescases.corotines.usecase2

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.banty.coroutines.base.BaseActivity
import com.banty.coroutines.base.useCaseDescription2
import com.banty.coroutines.databinding.ActivityPerform2sequentialnetworkrequestsBinding
import com.banty.coroutines.utils.fromHtml
import com.banty.coroutines.utils.setGone
import com.banty.coroutines.utils.setVisible
import com.banty.coroutines.utils.toast
import kotlinx.android.synthetic.main.activity_perform2sequentialnetworkrequests.*

class Perform2SequentialNetworkRequestsActivity : BaseActivity() {
    override fun getToolbarTitle() = useCaseDescription2

    private val binding by lazy {
        ActivityPerform2sequentialnetworkrequestsBinding.inflate(
            layoutInflater
        )
    }

    private val viewModel: Perform2SequentialNetworkRequestViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.uiState().observe(this, Observer { uiState ->
            render(uiState)
        })

        binding.btnRequestsSequentially.setOnClickListener {
            viewModel.perform2SequentialNetworkRequest()
        }
    }

    private fun render(uiState: UiState?) {
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

    private fun onLoad() {
        progressBar.setVisible()
        textViewResult.text = ""
        btnRequestsSequentially.isEnabled = false
    }

    private fun onSuccess(uiState: UiState.Success) {
        progressBar.setGone()
        btnRequestsSequentially.isEnabled = true
        textViewResult.text = fromHtml(
            "<b>Features of most recent Android Version \" ${uiState.versionFeatures.androidVersion.name} \"</b><br>" +
                    uiState.versionFeatures.features.joinToString(
                        prefix = "- ",
                        separator = "<br>- "
                    )
        )

    }

    private fun onError(uiState: UiState.Error) {
        progressBar.setGone()
        btnRequestsSequentially.isEnabled = true
        toast(uiState.errorMessage)
    }
}
