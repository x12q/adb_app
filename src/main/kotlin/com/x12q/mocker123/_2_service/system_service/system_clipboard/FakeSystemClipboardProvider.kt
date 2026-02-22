package com.x12q.mocker123._2_service.system_service.system_clipboard

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.x12q.common_utils.ForTestOnly
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.StringSelection
import me.tatarka.inject.annotations.Inject

@ForTestOnly
@Inject
class FakeSystemClipboardProvider : SystemClipboardProvider {
    val fakeClipboard = Clipboard("fake_clipboard")
    override fun getCurrentClipboard(): Clipboard? {
        return fakeClipboard
    }

    override fun writeToClipboard(text: String): Result<Unit, CantWriteToClipboard> {
        try {
            fakeClipboard.setContents(StringSelection(text), null)
            return Ok(Unit)
        } catch (exception: Exception) {
            return Err(CantWriteToClipboard)
        }
    }
}
