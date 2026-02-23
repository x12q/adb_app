package com.x12q.mocker123.service.local_service.adb_profile.data_structures

/**
 * Int data, use under `--ei` tag of `adb shell am broadcast`
 * https://developer.android.com/tools/adb#IntentSpec
 */
data class EiData(
    override val id: String,
    val key: String?,
    val value: Int,
): DataEntry()