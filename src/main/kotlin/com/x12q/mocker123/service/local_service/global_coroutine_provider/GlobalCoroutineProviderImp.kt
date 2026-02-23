package com.x12q.mocker123.service.local_service.global_coroutine_provider

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import com.x12q.mocker123.app.di.AppComponent
import kotlinx.coroutines.SupervisorJob
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@ContributesBinding(AppComponent.Scope::class)
@SingleIn(AppComponent.Scope::class)
class GlobalCoroutineProviderImp: GlobalCoroutineProvider{
    override val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
}
