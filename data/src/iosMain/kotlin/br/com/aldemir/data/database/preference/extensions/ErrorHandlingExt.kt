package br.com.aldemir.data.database.preference.extensions

import com.diamondedge.logging.logging
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCObjectVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.pointed
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import platform.Foundation.NSError

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
@Throws(Exception::class)
fun <T> executeWithErrorHandling(logTag: String = "ErrorHandlingExt", operation: (errorPtr: CPointer<ObjCObjectVar<NSError?>>) -> T): T {
    memScoped {
        val errorPtr: CPointer<ObjCObjectVar<NSError?>> = alloc<ObjCObjectVar<NSError?>>().ptr
        val result: T = operation(errorPtr)
        val error: NSError? = errorPtr.pointed.value
        if (error != null) {
            logging(logTag).i { error.localizedDescription }
            throw Exception(error.localizedDescription)
        }
        return result
    }
}