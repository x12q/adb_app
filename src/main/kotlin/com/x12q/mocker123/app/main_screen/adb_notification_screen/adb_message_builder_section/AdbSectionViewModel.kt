package com.x12q.mocker123.app.main_screen.adb_notification_screen.adb_message_builder_section

import com.x12q.mocker123.app.main_screen.adb_notification_screen.adb_message_builder_section.add_message_selector.MessageType
import com.x12q.mocker123.app.main_screen.adb_notification_screen.adb_message_builder_section.messages.es.EscapeType
import com.x12q.mocker123.service.local_service.adb_profile.data_structures.EsData
import com.x12q.mocker123.service.local_service.adb_profile.AdbProfileRepoContainer
import com.x12q.mocker123.service.local_service.adb_profile.data_structures.AdbProfile
import com.x12q.mocker123.service.local_service.adb_profile.data_structures.AdbProfileId
import com.x12q.mocker123.service.local_service.global_coroutine_provider.GlobalCoroutineProvider
import com.x12q.mocker123.service.local_service.global_coroutine_provider.GlobalCoroutineProviderImp
import com.x12q.common_utils.toStateFlow
import com.x12q.mocker123.service.local_service.adb_profile.data_structures.DataEntry
import com.x12q.mocker123.service.local_service.adb_profile.data_structures.EiData
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import java.util.UUID

@Inject
class AdbSectionViewModel(
    @Assisted val adbProfileId: AdbProfileId,
    val container: AdbProfileRepoContainer,
    val coroutineProvider: GlobalCoroutineProvider,
) {

    private val cr = coroutineProvider.coroutineScope

    private fun currentProfile(): AdbProfile? = container.profileFlow.value.firstOrNull { it.id == adbProfileId }

    val adbProfileFlow = container.getProfileFlow(adbProfileId.uuid.toString()).filterNotNull()

    val esMapFlow: StateFlow<Map<String, EsData>> = adbProfileFlow
        .map { it.esMap }
        .toStateFlow(cr, emptyMap())

    fun onEntryChange(entry: DataEntry) {
        when (entry) {
            is EsData -> onEsChange(entry)
            is EiData -> {}
        }
    }

    fun onEsChange(newEs: EsData) {
        val profile = currentProfile() ?: return
        container.add(profile.updateEs(newEs.coerceToEmptyIfNeed()))
    }

    fun addBlankEs() {
        val profile = currentProfile() ?: return
        container.add(profile.addEs(EsData.empty()))
    }

    fun addTitleEs() {
        val profile = currentProfile() ?: return
        container.add(
            profile.addEs(
                EsData.empty().copy(
                    key = "title",
                    keyIsLocked = true,
                    escapeType = EscapeType.PLAIN_TEXT,
                )
            )
        )
    }

    fun addBodyEs() {
        val profile = currentProfile() ?: return
        container.add(
            profile.addEs(
                EsData.empty().copy(
                    key = "body",
                    keyIsLocked = true,
                    escapeType = EscapeType.PLAIN_TEXT,
                )
            )
        )
    }

    fun onSelectMessageType(messageType: MessageType) {
        when (messageType) {
            MessageType.TITLE -> addTitleEs()
            MessageType.BODY -> addBodyEs()
            MessageType.JSON -> addBlankEsWithType(EscapeType.JSON)
            MessageType.XML -> addBlankEsWithType(EscapeType.XML)
            MessageType.PLAIN_TEXT -> addBlankEsWithType(EscapeType.PLAIN_TEXT)
        }
    }

    private fun addBlankEsWithType(escapeType: EscapeType) {
        val profile = currentProfile() ?: return
        container.add(
            profile.addEs(
                EsData.empty().copy(escapeType = escapeType)
            )
        )
    }

    fun onRemoveEsClick(esData: EsData) {
        val profile = currentProfile() ?: return
        container.add(profile.removeEs(esData))
    }

    fun onRemoveEiClick(eiData: EiData) {
        val profile = currentProfile() ?: return
        container.add(profile.removeEi(eiData))
    }

    fun onRemoveClick(entry: DataEntry) {
        when (entry) {
            is EiData -> onRemoveEiClick(entry)
            is EsData -> {
                onRemoveEsClick(entry)
            }
        }
    }

    companion object {
        fun forPreview(): AdbSectionViewModel {
            return AdbSectionViewModel(
                adbProfileId = AdbProfileId(UUID.randomUUID()),
                container = AdbProfileRepoContainer.forPreview(),
                coroutineProvider = GlobalCoroutineProviderImp(),
            )
        }
    }
}