package com.x12q.mocker123.service.local_service.adb_profile.errors

import kotlinx.serialization.SerializationException

sealed class CannotSaveAdbProfileStore {
    object AdbProfilesAreNull:CannotSaveAdbProfileStore()
    data class SerializationError(val e: SerializationException):CannotSaveAdbProfileStore()
}
