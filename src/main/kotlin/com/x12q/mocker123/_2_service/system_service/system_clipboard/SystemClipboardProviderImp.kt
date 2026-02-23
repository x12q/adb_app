package com.x12q.mocker123._2_service.system_service.system_clipboard

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import com.x12q.mocker123.di.AppScope
import java.awt.datatransfer.StringSelection
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@ContributesBinding(AppScope::class)
@SingleIn(AppScope::class)
class SystemClipboardProviderImp : SystemClipboardProvider{
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
