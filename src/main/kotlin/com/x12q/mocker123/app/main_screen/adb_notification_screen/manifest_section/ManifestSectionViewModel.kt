package com.x12q.mocker123.app.main_screen.adb_notification_screen.manifest_section

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.x12q.mocker123.app.main_screen.adb_notification_screen.package_name_section.PackageNameSectionViewModel
import com.x12q.mocker123.service.system_service.system_clipboard.SystemClipboardProvider
import kotlinx.coroutines.flow.StateFlow
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class ManifestSectionViewModel(
    val clipboardProvider: SystemClipboardProvider,
    @Assisted val appPackageNameViewModel: PackageNameSectionViewModel,
) {

    fun onClickCopy(text: String) {
        val rs = clipboardProvider.writeToClipboard(text)

        when (rs) {
            is Err -> {
                // TODO display the error from this rs
            }

            is Ok -> {

            }
        }
    }

    val packageNameFlow: StateFlow<String?> = appPackageNameViewModel.packageNameFlow

    companion object {
        fun forPreview(): ManifestSectionViewModel {
            return ManifestSectionViewModel(
                clipboardProvider = SystemClipboardProvider.forPreview(),
                appPackageNameViewModel = PackageNameSectionViewModel.forPreview(),
            )
        }
    }
}
