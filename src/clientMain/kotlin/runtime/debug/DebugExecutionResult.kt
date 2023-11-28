package runtime.debug

import org.khronos.webgl.Uint8Array

private var instanceCount: Int = 0

data class DebugExecutionResult(
    val symbolName: String,
    val symbolType: String,
    val data: Uint8Array,
    val id: Int = instanceCount++
)
