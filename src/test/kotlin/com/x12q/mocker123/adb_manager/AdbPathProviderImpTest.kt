package com.x12q.mocker123.adb_manager

import app.cash.turbine.turbineScope
import com.x12q.mocker123._2_service.system_service.adb_path_manager.AdbPathProviderImp
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import kotlin.io.path.Path
import kotlin.test.*


class AdbPathProviderImpTest {
    @Test
    fun stdTest(){
        runTest{
            turbineScope{
                val provider = AdbPathProviderImp()
                val adbPathFlow = provider.adbPathFlow.testIn(backgroundScope)
                adbPathFlow.awaitItem() shouldBe Path("adb")
                provider.getCurrentAdbPath() shouldBe Path("adb")

                val p = Path("something")
                provider.setCustomAdbPath(p)
                adbPathFlow.awaitItem() shouldBe p
                provider.getCurrentAdbPath() shouldBe p

                provider.removeCustomAdbPath()
                adbPathFlow.awaitItem() shouldBe Path("adb")
                provider.getCurrentAdbPath() shouldBe Path("adb")
            }
        }
    }
}
