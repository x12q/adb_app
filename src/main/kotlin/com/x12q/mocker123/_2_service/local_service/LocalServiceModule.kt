package com.x12q.mocker123._2_service.local_service

import com.x12q.mocker123._2_service.local_service.global_coroutine_provider.GlobalCoroutineProvider
import com.x12q.mocker123._2_service.local_service.global_coroutine_provider.GlobalCoroutineProviderImp
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface LocalServiceModule {
    @Binds
    @Singleton
    fun GlobalCoroutineProvider(i: GlobalCoroutineProviderImp): GlobalCoroutineProvider
}
