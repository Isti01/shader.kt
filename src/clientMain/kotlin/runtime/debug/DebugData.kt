package runtime.debug

data class DebugData(val results: List<DebugExecutionResult>) {
    companion object {
        fun empty() = DebugData(emptyList())
    }
}