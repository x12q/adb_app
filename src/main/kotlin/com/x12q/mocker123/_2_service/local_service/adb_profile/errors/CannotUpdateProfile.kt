package com.x12q.mocker123._2_service.local_service.adb_profile.errors

import kotlinx.serialization.SerializationException


sealed class CannotUpdateProfile{
    object ContainerInIllegalState: CannotUpdateProfile()
}

