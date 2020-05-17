package com.banty.coroutines.usescases.corotines.usecase2.callbacks

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.banty.coroutines.base.BaseActivity
import com.banty.coroutines.base.useCase2UsingCallbacksDescription
import com.banty.coroutines.databinding.ActivityPerform2sequentialnetworkrequestsBinding
import com.banty.coroutines.utils.fromHtml
import com.banty.coroutines.utils.setGone
import com.banty.coroutines.utils.setVisible
import com.banty.coroutines.utils.toast

class SequentialNetworkRequestCallbackActivity : BaseActivity() {
    override fun getToolbarTitle() = useCase2UsingCallbacksDescription

    private val viewModel: SequentialNetworkRequestCallbackViewModel by viewModels()

    private val binding: ActivityPerform2sequentialnetworkrequestsBinding by lazy {
        ActivityPerform2sequentialnetworkrequestsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.uiState().observe(this, Observer { uiState ->
            if (uiState != null) {
                render(uiState)
            }
        })
        binding.btnRequestsSequentially.setOnClickListener {
            viewModel.perform2NetworkRequests()
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

    private fun onLoad() = with(binding) {
        progressBar.setVisible()
        textViewResult.text = ""
    }

    private fun onSuccess(uiState: UiState.Success) = with(binding) {
        progressBar.setGone()
        textViewResult.text = fromHtml(
            "<b>Features of most recent Android Version \" ${uiState.versionFeatures.androidVersion.name} \"</b><br>" +
                    uiState.versionFeatures.features.joinToString(
                        prefix = "- ",
                        separator = "<br>- "
                    )
        )
    }

    private fun onError(uiState: UiState.Error) = with(binding) {
        progressBar.setGone()
        btnRequestsSequentially.isEnabled = true
        toast(uiState.message)
    }
}