package com.salakheev.shaderbuilderkt.builder.debug.breakpoint

import com.salakheev.shaderbuilderkt.builder.instruction.Instruction

object UnsetBreakpoint : Breakpoint {
    override fun emitInstructions(programInstructions: List<Instruction>): Iterable<Instruction> = programInstructions
}