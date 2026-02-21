package com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._1_package_name_section

import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen.di.AdbProfileScreenScope
import com.x12q.mocker123._2_service.local_service.adb_profile.AdbProfileRepoContainer
import com.x12q.mocker123._2_service.local_service.adb_profile.data_structures.AdbProfileId
import com.x12q.mocker123._2_service.local_service.global_coroutine_provider.GlobalCoroutineProvider
import com.x12q.common_utils.toStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

@AdbProfileScreenScope
class PackageNameSectionViewModel @Inject constructor(
    val adbProfileId: AdbProfileId,
    val container: AdbProfileRepoContainer,
    val crProvider: GlobalCoroutineProvider,
) {

    private val cr = crProvider.coroutineScope

    val profileNameFlow: StateFlow<String?> = container.getProfileFlow(adbProfileId.uuid.toString())
        .filterNotNull()
        .map { it.name }
        .toStateFlow(cr,null)

    val packageNameFlow: StateFlow<String?> = container.getProfileFlow(adbProfileId.uuid.toString())
        .filterNotNull()
        .map { it.packageName }
        .toStateFlow(cr,null)

    fun setPackageName(packageName: String) {
        val name = packageName.takeIf { it.isNotEmpty() && it.isNotBlank() }
        val currentProfile = container.profileFlow.value.firstOrNull { it.id == adbProfileId } ?: return
        val newProfile = currentProfile.setPackageName(name?.trim())
        container.add2(newProfile)
    }

    fun setProfileName(profileName: String?){
        val currentProfile = container.profileFlow.value.firstOrNull { it.id == adbProfileId } ?: return
        val newProfile = currentProfile.setProfileName(profileName)
        container.add2(newProfile)
    }

    companion object{
        fun forPreview(): PackageNameSectionViewModel{
            return PackageNameSectionViewModel(
                adbProfileId = AdbProfileId(UUID.randomUUID()),
                container = AdbProfileRepoContainer.forPreview(),
                crProvider = GlobalCoroutineProvider.forPreview(),
            )
        }
    }
}
