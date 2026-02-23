package com.x12q.mocker123.app.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.x12q.adb_app.generated.resources.Res
import com.x12q.adb_app.generated.resources.no_profile
import com.x12q.mocker123.app.main_screen.adb_profile_screen.AdbProfileScreen
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.x12q.common_di.di.viewmodel_di.getVM
import com.x12q.mocker123.service.local_service.adb_profile.errors.CannotLoadProfile
import com.x12q.common_ui.text.ContentText
import com.x12q.common_ui.theme.BaseTheme
import org.jetbrains.compose.resources.stringResource


@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = getVM(),
    modifier: Modifier = Modifier
) {
    val repoCont = viewModel.adbProfileRepoContainer
    val initLoad = remember { mutableStateOf<Result<Unit, CannotLoadProfile>?>(null) }

    LaunchedEffect(Unit) {
        initLoad.value = repoCont.loadProfiles2()
    }

    when (initLoad.value) {

        is Ok -> {
            val selected by viewModel.selectedViewModel.collectAsState()
            val selectedVm = selected?.adbProfileId?.let { viewModel.getViewModel(it) }
            if (selectedVm != null) {
                AdbProfileScreen(selectedVm, modifier)
            } else {
                EmptyDefaultScreen()
            }
        }

        is Err -> ErrorScreen("Impossible: Unable to load data or create init data")
        null -> LoadingScreen()
    }
}

@Composable
fun LoadingScreen() {
    Box(Modifier.fillMaxSize().background(BaseTheme.colors.baseColors.surface1), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = BaseTheme.colors.baseColors.primary, strokeWidth = 5.dp)
    }
}

@Composable
fun EmptyDefaultScreen() {
    Box(Modifier.fillMaxSize().background(BaseTheme.colors.baseColors.surface1), contentAlignment = Alignment.Center) {
        ContentText(stringResource(Res.string.no_profile))
    }
}

@Composable
fun ErrorScreen(errMsg: String) {
    Box(Modifier.fillMaxSize().background(BaseTheme.colors.baseColors.surface1), contentAlignment = Alignment.Center) {
        ContentText(errMsg)
    }
}