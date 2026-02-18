package com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen

import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._1_package_name_section.PackageNameSectionViewModel
import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._2_manifest_section.ManifestSectionViewModel
import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._3_adb_section.AdbSectionViewModel
import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._4_adb_output.AdbOutputViewModel
import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen.di.AdbProfileScreenScope
import com.x12q.mocker123._2_service.local_service.adb_profile.repo.AdbProfileRepo
import com.x12q.common_utils.toStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@AdbProfileScreenScope
class AdbProfileScreenViewModel @Inject constructor(
    val packageNameSectionViewModel: PackageNameSectionViewModel,
    val manifestSectionViewModel: ManifestSectionViewModel,
    val adbSectionViewModel: AdbSectionViewModel,
    val adbProfileRepo: AdbProfileRepo,
    val adbOutputViewModel: AdbOutputViewModel,
){

    val cr: CoroutineScope = CoroutineScope(SupervisorJob()+ Dispatchers.Default)
    val adbProfile get()= adbProfileRepo.getProfile()

    val profileDisplayNameFlow: StateFlow<String?> = adbProfileRepo.profileFlow
        .map { profile->
            profile.name ?: profile.packageName
        }
        .toStateFlow(cr,null)

    fun changeProfileName(newName:String?){
        adbProfileRepo.setProfileName(newName)
    }

    companion object{
        fun forPreview(): AdbProfileScreenViewModel{
            val adbSectionVm = AdbSectionViewModel.forPreview()
            val adbProfileRepo = adbSectionVm.adbProfileRepo
            return AdbProfileScreenViewModel(
                manifestSectionViewModel = ManifestSectionViewModel.forPreview(),
                packageNameSectionViewModel = PackageNameSectionViewModel.forPreview(),
                adbSectionViewModel = adbSectionVm,
                adbProfileRepo = adbProfileRepo,
                adbOutputViewModel = AdbOutputViewModel.forPreview()
            )
        }
    }
}
