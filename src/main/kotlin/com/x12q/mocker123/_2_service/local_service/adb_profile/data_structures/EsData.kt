package com.x12q.mocker123._2_service.local_service.adb_profile.data_structures

import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._3_adb_section.messages.es.EscapeType
import com.x12q.common_utils.RandomUtils
import java.util.UUID
import kotlin.random.Random

/**
 * Text data, use under `--es` tag of `adb shell am broadcast`
 * https://developer.android.com/tools/adb#IntentSpec
 */
data class EsData(
    override val id: String,
    val key: String?,
    val value: String?,
    val escapeType: EscapeType,
    val keyIsLocked: Boolean,
):DataEntry() {

    /** Either has [key] or [value] */
    fun hasContent(): Boolean {
        return !key.isNullOrEmpty() || !value.isNullOrEmpty()
    }

    /** has content that can be used to construct a runnable "es" */
    fun hasRunnableContent(): Boolean{
        return !key.isNullOrEmpty() && !value.isNullOrEmpty()
    }

    fun coerceToEmptyIfNeed(): EsData {
        return this.copy(
            key = key.takeIf { !it.isNullOrEmpty() },
            value = value.takeIf { !it.isNullOrEmpty() }
        )
    }

    companion object {

        fun empty(): EsData {
            return EsData(
                id = UUID.randomUUID().toString(),
                key = null,
                value = null,
                escapeType = EscapeType.JSON,
                keyIsLocked = false,
            )
        }

        fun random(): EsData {
            return EsData(
                id = RandomUtils.randomStr("id"),
                key = RandomUtils.randomStr("esKey"),
                value = RandomUtils.randomStr("esValue"),
                escapeType = EscapeType.entries.random(),
                keyIsLocked = Random.nextBoolean(),
            )
        }
    }
}
