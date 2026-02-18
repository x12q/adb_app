package com.x12q.mocker123._2_service.local_service.global_coroutine_provider

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject

class GlobalCoroutineProviderImp @Inject constructor(): GlobalCoroutineProvider{
    override val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
}
