package com.banty.coroutines.usescases.corotines.usecase2

import android.os.Bundle
import com.banty.coroutines.base.BaseActivity
import com.banty.coroutines.base.useCaseDescription2
import com.banty.coroutines.databinding.ActivityPerform2sequentialnetworkrequestsBinding

class Perform2SequentialNetworkRequests : BaseActivity() {
    override fun getToolbarTitle() = useCaseDescription2

    private val binding by lazy {
        ActivityPerform2sequentialnetworkrequestsBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

}
