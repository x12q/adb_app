package com.x12q.mocker123._2_service.system_service.system_clipboard

import com.github.michaelbull.result.Result
import com.x12q.common_utils.ForTestOnly
import java.awt.datatransfer.Clipboard

/**
 * Provide access to the current system clipboard (for copy, paste, etc)
 */
interface SystemClipboardProvider {
    fun getCurrentClipboard(): Clipboard?

    /**
     * Write a [text] to the system clipboard
     */
    fun writeToClipboard(text:String): Result<Unit, CantWriteToClipboard>

    companion object{
        @OptIn(ForTestOnly::class)
        fun forPreview():SystemClipboardProvider{
            return FakeSystemClipboardProvider()
        }
    }
}

