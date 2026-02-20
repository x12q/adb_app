package com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen

import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._1_package_name_section.PackageNameSectionViewModel
import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._2_manifest_section.ManifestSectionViewModel
import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._3_adb_section.AdbSectionViewModel
import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._4_adb_output.AdbOutputViewModel
import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen.di.AdbProfileScreenScope
import com.x12q.mocker123._2_service.local_service.adb_profile.AdbProfileRepoContainer
import com.x12q.mocker123._2_service.local_service.adb_profile.data_structures.AdbProfileId
import com.x12q.common_utils.toStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

@AdbProfileScreenScope
class AdbProfileScreenViewModel @Inject constructor(
    val packageNameSectionViewModel: PackageNameSectionViewModel,
    val manifestSectionViewModel: ManifestSectionViewModel,
    val adbSectionViewModel: AdbSectionViewModel,
    val adbProfileId: AdbProfileId,
    val container: AdbProfileRepoContainer,
    val adbOutputViewModel: AdbOutputViewModel,
){

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
        container.add2(newProfile)
    }

    companion object{
        fun forPreview(): AdbProfileScreenViewModel{
            val container = AdbProfileRepoContainer.forPreview()
            val profileId = AdbProfileId(UUID.randomUUID())
            return AdbProfileScreenViewModel(
                manifestSectionViewModel = ManifestSectionViewModel.forPreview(),
                packageNameSectionViewModel = PackageNameSectionViewModel.forPreview(),
                adbSectionViewModel = AdbSectionViewModel.forPreview(),
                adbProfileId = profileId,
                container = container,
                adbOutputViewModel = AdbOutputViewModel.forPreview()
            )
        }
    }
}
