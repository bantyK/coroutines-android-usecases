package com.banty.coroutines.usescases.corotines.usecase5

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.banty.coroutines.R
import com.banty.coroutines.base.BaseActivity
import com.banty.coroutines.base.useCaseDescription5
import com.banty.coroutines.databinding.ActivityExceptionhandlingBinding
import com.banty.coroutines.utils.fromHtml
import com.banty.coroutines.utils.setGone
import com.banty.coroutines.utils.setVisible
import com.banty.coroutines.utils.toast
import kotlinx.android.synthetic.main.activity_exceptionhandling.*

class ExceptionHandlingActivity : BaseActivity() {
    override fun getToolbarTitle() = useCaseDescription5

    private val binding: ActivityExceptionhandlingBinding by lazy {
        ActivityExceptionhandlingBinding.inflate(layoutInflater)
    }

    private val viewModel: ExceptionHandlingViewModel by viewModels()

    private var operationStartTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.uiState().observe(this, Observer { render(it) })

        btnExceptionTryCatch.setOnClickListener { viewModel.handleExceptionHandlingWithTryCatch() }

        btnCoroutineExceptionHandler.setOnClickListener { viewModel.handleWithCoroutineExceptionHandler() }
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
        operationStartTime = System.currentTimeMillis()
        progressBar.setVisible()
        textViewDuration.text = ""
        textViewResult.text = ""
        disableButtons()
    }

    private fun onSuccess(uiState: UiState.Success) = with(binding) {
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

    private fun onError(uiState: UiState.Error) = with(binding) {
        progressBar.setGone()
        textViewDuration.setGone()
        toast(uiState.message)
        enableButtons()
    }

    private fun enableButtons() = with(binding) {
        btnExceptionTryCatch.isEnabled = true
        btnCoroutineExceptionHandler.isEnabled = true
        btnShowResultsEvenIfChildCoroutineFailsTryCatch.isEnabled = true
        btnShowResultsEvenIfChildCoroutineFailsRunCatching.isEnabled = true
    }

    private fun disableButtons() = with(binding) {
        btnExceptionTryCatch.isEnabled = false
        btnCoroutineExceptionHandler.isEnabled = false
        btnShowResultsEvenIfChildCoroutineFailsTryCatch.isEnabled = false
        btnShowResultsEvenIfChildCoroutineFailsRunCatching.isEnabled = false
    }
}