package com.salakheev.shaderbuilderkt.builder.types

import com.salakheev.shaderbuilderkt.builder.ShaderBuilder
import com.salakheev.shaderbuilderkt.builder.instruction.Instruction

interface Variable {
    val builder: ShaderBuilder
    val typeName: String
    var value: String?
}

interface Symbol : Variable {
    var name: String?
    var defineInstruction: Instruction

    fun isUnused() = defineInstruction.isUnusedDefinition()
    fun toVec4Expression(): String
}

interface Vector : Symbol
interface Matrix : Variable
