package com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._1_package_name_section

import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen.di.AdbProfileScreenScope
import com.x12q.mocker123._2_service.local_service.adb_profile.repo.AdbProfileRepo
import com.x12q.mocker123._2_service.local_service.global_coroutine_provider.GlobalCoroutineProvider
import com.x12q.common_utils.toStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@AdbProfileScreenScope
class PackageNameSectionViewModel @Inject constructor(
    val profileRepo: AdbProfileRepo,
    val crProvider: GlobalCoroutineProvider,
) {

    private val cr = crProvider.coroutineScope

    val profileNameFlow: StateFlow<String?> = profileRepo.profileFlow.map { it.name }
        .toStateFlow(cr,null)

    val packageNameFlow: StateFlow<String?> = profileRepo.profileFlow.map { it.packageName }
        .toStateFlow(cr,null)

    fun setPackageName(packageName: String) {
        val name = packageName.takeIf { it.isNotEmpty() && it.isNotBlank() }
        profileRepo.setPackageName(name?.trim())
    }

    fun setProfileName(profileName:String?){
        profileRepo.setProfileName(profileName)
    }

    companion object{
        fun forPreview(): PackageNameSectionViewModel{
            return PackageNameSectionViewModel(
                profileRepo = AdbProfileRepo.forPreview(),
                crProvider = GlobalCoroutineProvider.forPreview(),
            )
        }
    }
}
