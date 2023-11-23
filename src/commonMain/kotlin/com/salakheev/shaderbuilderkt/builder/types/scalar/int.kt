package com.salakheev.shaderbuilderkt.builder.types.scalar

import com.salakheev.shaderbuilderkt.builder.ShaderBuilder
import com.salakheev.shaderbuilderkt.builder.instruction.Instruction
import com.salakheev.shaderbuilderkt.builder.types.Symbol

class GLInt(override val builder: ShaderBuilder) : Symbol {
    override val typeName: String = "int"
    override var value: String? = null

    constructor(builder: ShaderBuilder, value: String) : this(builder) {
        this.value = value
    }

    override var name: String? = null
    override lateinit var defineInstruction: Instruction

    override fun toVec4Expression(): String = "vec4($value.0, 0.0, 0.0, 0.0)"

    operator fun plus(a: GLInt) = GLInt(builder, "(${this.value} + ${a.value})")
    operator fun plus(a: Int) = GLInt(builder, "(${this.value} + $a)")
}
