package com.salakheev.shaderbuilderkt.builder.instruction

data class Instruction(val type: InstructionType, var result: String = "") {
    companion object {
        fun assign(left: String?, right: String?): Instruction {
            return Instruction(InstructionType.ASSIGN, "$left = $right")
        }
    }
}