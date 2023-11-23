package com.salakheev.shaderbuilderkt.builder.debug.breakpoint

import com.salakheev.shaderbuilderkt.builder.instruction.Instruction

interface Breakpoint {

    fun emitInstructions(programInstructions: List<Instruction>): Iterable<Instruction>
}