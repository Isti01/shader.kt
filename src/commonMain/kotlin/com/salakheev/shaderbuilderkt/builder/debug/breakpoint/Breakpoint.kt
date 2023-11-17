package com.salakheev.shaderbuilderkt.builder.debug.breakpoint

import com.salakheev.shaderbuilderkt.builder.instruction.Instruction

interface Breakpoint {
    val uniforms: Set<String>

    fun emitInstructions(programInstructions: List<Instruction>): Iterable<Instruction>
}