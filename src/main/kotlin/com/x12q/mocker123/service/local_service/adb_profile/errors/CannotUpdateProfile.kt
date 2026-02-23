package com.x12q.mocker123.service.local_service.adb_profile.errors


sealed class CannotUpdateProfile{
    object ContainerInIllegalState: CannotUpdateProfile()
}

