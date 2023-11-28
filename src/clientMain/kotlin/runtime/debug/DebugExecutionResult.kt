package runtime.debug

import org.khronos.webgl.Uint8Array

data class DebugExecutionResult(
    val symbolName: String,
    val symbolType: String,
    val data: Uint8Array
)
