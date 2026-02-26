package com.x12q.mocker123.app.main_screen.adb_notification_screen

import androidx.lifecycle.ViewModel
import com.x12q.mocker123.app.main_screen.adb_notification_screen.package_name_section.PackageNameSectionViewModel
import com.x12q.mocker123.app.main_screen.adb_notification_screen.manifest_section.ManifestSectionViewModel
import com.x12q.mocker123.app.main_screen.adb_notification_screen.adb_message_builder_section.AdbSectionViewModel
import com.x12q.mocker123.app.main_screen.adb_notification_screen.adb_output.AdbOutputViewModel
import com.x12q.mocker123.service.local_service.adb_profile.AdbProfileRepoContainer
import com.x12q.mocker123.service.local_service.adb_profile.data_structures.AdbProfileId
import com.x12q.common_utils.toStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import java.util.UUID

@Inject
class AdbProfileScreenViewModel(
    @Assisted val adbProfileId: AdbProfileId,
    val container: AdbProfileRepoContainer,
    private val packageNameSectionViewModelFactory: (AdbProfileId) -> PackageNameSectionViewModel,
    private val manifestSectionViewModelFactory: (PackageNameSectionViewModel) -> ManifestSectionViewModel,
    private val adbSectionViewModelFactory: (AdbProfileId) -> AdbSectionViewModel,
    private val adbOutputViewModelFactory: (AdbProfileId) -> AdbOutputViewModel,
): ViewModel(){

    val packageNameSectionViewModel = packageNameSectionViewModelFactory(adbProfileId)
    val manifestSectionViewModel = manifestSectionViewModelFactory(packageNameSectionViewModel)
    val adbSectionViewModel = adbSectionViewModelFactory(adbProfileId)
    val adbOutputViewModel = adbOutputViewModelFactory(adbProfileId)

    val cr: CoroutineScope = CoroutineScope(SupervisorJob()+ Dispatchers.Default)

    val profileDisplayNameFlow: StateFlow<String?> = container.getProfileFlow(adbProfileId.uuid.toString())
        .filterNotNull()
        .map { profile->
            profile.name ?: profile.packageName
        }
        .toStateFlow(cr,null)

    fun changeProfileName(newName: String?){
        val currentProfile = container.profileFlow.value.firstOrNull { it.id == adbProfileId } ?: return
        val newProfile = currentProfile.setProfileName(newName)
        container.add(newProfile)
    }

    companion object{
        fun forPreview(): AdbProfileScreenViewModel{
            val container = AdbProfileRepoContainer.forPreview()
            val profileId = AdbProfileId(UUID.randomUUID())
            return AdbProfileScreenViewModel(
                adbProfileId = profileId,
                container = container,
                packageNameSectionViewModelFactory = { PackageNameSectionViewModel.forPreview() },
                manifestSectionViewModelFactory = { ManifestSectionViewModel.forPreview() },
                adbSectionViewModelFactory = { AdbSectionViewModel.forPreview() },
                adbOutputViewModelFactory = { AdbOutputViewModel.forPreview() },
            )
        }
    }
}
