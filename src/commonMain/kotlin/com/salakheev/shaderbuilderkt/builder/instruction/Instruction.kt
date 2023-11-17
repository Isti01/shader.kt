package com.salakheev.shaderbuilderkt.builder.instruction

import com.salakheev.shaderbuilderkt.builder.delegates.UNUSED_DEFINITION_KEY

data class Instruction(
    val type: InstructionType,
    var result: String = "",
    val id: String = instanceCount++.toString()
) {
    companion object {
        private var instanceCount: Int = 0
        fun assign(left: String?, right: String?): Instruction {
            return Instruction(InstructionType.ASSIGN, "$left = $right")
        }

    }

    fun isUnusedDefinition(): Boolean = result.contains(UNUSED_DEFINITION_KEY)

    override fun toString(): String = when (type) {
        InstructionType.DEFINE, InstructionType.ASSIGN -> "${result};\n"
        InstructionType.IF -> "if (${result}) {\n"
        InstructionType.ELSEIF -> "else if (${result}) {\n"
        InstructionType.ELSE -> "else {\n"
        InstructionType.ENDIF -> "}\n"
        InstructionType.DISCARD -> "discard;\n"
    }
}