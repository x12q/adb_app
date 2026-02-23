package com.x12q.mocker123.service.local_service.global_coroutine_provider

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import com.x12q.mocker123.di.AppGlobalComponent
import kotlinx.coroutines.SupervisorJob
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@ContributesBinding(AppGlobalComponent.Scope::class)
@SingleIn(AppGlobalComponent.Scope::class)
class GlobalCoroutineProviderImp: GlobalCoroutineProvider{
    override val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
}
