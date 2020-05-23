package com.banty.coroutines.usescases.corotines.usecase4

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.banty.coroutines.base.BaseActivity
import com.banty.coroutines.base.useCaseDescription4
import com.banty.coroutines.databinding.ActivityNetworkrequesttimeoutBinding
import com.banty.coroutines.utils.fromHtml
import com.banty.coroutines.utils.setGone
import com.banty.coroutines.utils.setVisible
import kotlinx.android.synthetic.main.activity_networkrequesttimeout.*

class NetworkRequestWithTimeoutActivity : BaseActivity() {
    override fun getToolbarTitle() = useCaseDescription4

    private val binding: ActivityNetworkrequesttimeoutBinding by lazy {
        ActivityNetworkrequesttimeoutBinding.inflate(layoutInflater)
    }

    private val viewModel: NetworkRequestTimeoutViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.uiState().observe(this, Observer {
            renderView(it)
        })


        btnPerformSingleNetworkRequest.setOnClickListener {
            val timeout = binding.editTextTimeOut.text.toString().toLongOrNull()
            timeout?.let {
                viewModel.performNetworkRequest(it)
            }
        }

    }

    private fun renderView(uiState: UiState) {
        when (uiState) {
            is UiState.Loading -> onLoad()
            is UiState.Success -> onSuccess(uiState)
            is UiState.Error -> onError(uiState)
        }
    }

    private fun onLoad() {
        progressBar.setVisible()
        textViewResult.text = ""
        btnPerformSingleNetworkRequest.isEnabled = true
    }

    private fun onSuccess(uiState: UiState.Success) {
        progressBar.setGone()
        btnPerformSingleNetworkRequest.isEnabled = true
        val readableVersions = uiState.recentVersions.map { "API : ${it.apiLevel} : ${it.name}" }
        textViewResult.text =
            fromHtml("<b>Android Versions</b><br> ${readableVersions.joinToString(separator = "<br>")}")
    }

    private fun onError(uiState: UiState.Error) {
        progressBar.setGone()
        btnPerformSingleNetworkRequest.isEnabled = true
        textViewResult.text = uiState.message
    }

}
