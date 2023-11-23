package com.salakheev.shaderbuilderkt.builder.debug.breakpoint

import com.salakheev.shaderbuilderkt.builder.ShaderBuilder.Companion.FRAG_COLOR_VARIABLE
import com.salakheev.shaderbuilderkt.builder.instruction.Instruction
import com.salakheev.shaderbuilderkt.builder.instruction.InstructionType
import com.salakheev.shaderbuilderkt.builder.types.Symbol

data class FramebufferOutputBreakpoint(val instructionIndex: Int, val variableToOutput: Symbol) : Breakpoint {
    companion object {
        private const val EMPTY_OUTPUT_EXPRESSION = "$FRAG_COLOR_VARIABLE = vec4(0.0, 0.0, 0.0, 0.0)"
    }

    override fun emitInstructions(programInstructions: List<Instruction>): Iterable<Instruction> =
        sequence {
            var scopeLevel = 0
            for ((i, instruction) in programInstructions.withIndex()) {
                if (i >= instructionIndex) {
                    break
                }
                yield(instruction)

                when (instruction.type) {
                    InstructionType.IF,
                    InstructionType.ELSEIF,
                    InstructionType.ELSE -> scopeLevel++

                    InstructionType.ENDIF -> scopeLevel--
                    else -> {}
                }

            }
            yield(getBreakpointInstruction())
            if (scopeLevel > 0) {
                for (i in 0..<scopeLevel) {
                    yield(Instruction(InstructionType.ENDIF))
                }
                yield(getEmptyOutputInstruction())
            }
        }.asIterable()

    private fun getBreakpointInstruction() =
        Instruction(InstructionType.ASSIGN, "$FRAG_COLOR_VARIABLE = (${variableToOutput.toVec4Expression()})")

    private fun getEmptyOutputInstruction() = Instruction(InstructionType.ASSIGN, EMPTY_OUTPUT_EXPRESSION)
}