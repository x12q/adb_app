package com.x12q.mocker123._2_service.local_service.global_coroutine_provider

import kotlinx.coroutines.CoroutineScope

interface GlobalCoroutineProvider {
    val coroutineScope: CoroutineScope

    companion object{
        fun forPreview():GlobalCoroutineProvider{
            return GlobalCoroutineProviderImp()
        }
    }
}

