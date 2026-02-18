package com.x12q.mocker123._2_service.system_service.system_clipboard

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.StringSelection
import javax.inject.Inject

class SystemClipboardProviderImp @Inject constructor() : SystemClipboardProvider{
    override fun getCurrentClipboard(): Clipboard? {
        val rt: Clipboard? = Toolkit.getDefaultToolkit().getSystemClipboard()
        return rt
    }

    override fun writeToClipboard(text: String): Result<Unit, CantWriteToClipboard> {
        val clipboard = getCurrentClipboard()
        if(clipboard==null){
            return Err(CantWriteToClipboard)
        }else{
            try{
                clipboard.setContents(StringSelection(text),null)
                return Ok(Unit)
            }catch (exception: Exception){
                return Err(CantWriteToClipboard)
            }
        }
    }
}
