package com.salakheev.shaderbuilderkt.builder.types.scalar

import com.salakheev.shaderbuilderkt.builder.ShaderBuilder
import com.salakheev.shaderbuilderkt.builder.delegates.ComponentDelegate
import com.salakheev.shaderbuilderkt.builder.instruction.Instruction
import com.salakheev.shaderbuilderkt.builder.types.BoolResult
import com.salakheev.shaderbuilderkt.builder.types.Symbol
import com.salakheev.shaderbuilderkt.builder.util.str

class GLFloat : Symbol {

    override val builder: ShaderBuilder

    constructor(builder: ShaderBuilder) {
        this.builder = builder
    }

    override val typeName: String = "float"
    override var value: String? = null

    constructor(builder: ShaderBuilder, value: String) : this(builder) {
        this.value = value
    }

    override var name: String? = null
    override lateinit var defineInstruction: Instruction

    override fun toVec4Expression(): String = "vec4($value, 0.0, 0.0, 0.0)"

    operator fun times(a: Float) = GLFloat(builder, "(${this.value} * ${a.str()})")
    operator fun div(a: Float) = GLFloat(builder, "(${this.value} / ${a.str()})")
    operator fun plus(a: Float) = GLFloat(builder, "(${this.value} + ${a.str()})")
    operator fun minus(a: Float) = GLFloat(builder, "(${this.value} - ${a.str()})")

    operator fun times(a: GLFloat) = GLFloat(builder, "(${this.value} * ${a.value})")
    operator fun div(a: GLFloat) = GLFloat(builder, "(${this.value} / ${a.value})")
    operator fun plus(a: GLFloat) = GLFloat(builder, "(${this.value} + ${a.value})")
    operator fun minus(a: GLFloat) = GLFloat(builder, "(${this.value} - ${a.value})")

    operator fun unaryMinus() = GLFloat(builder, "-(${this.value})")

    infix fun eq(a: GLFloat) = BoolResult("(${this.value} == ${a.value})")
    infix fun gte(a: GLFloat) = BoolResult("(${this.value} >= ${a.value})")
    infix fun gt(a: GLFloat) = BoolResult("(${this.value} > ${a.value})")
    infix fun lte(a: GLFloat) = BoolResult("(${this.value} <= ${a.value})")
    infix fun lt(a: GLFloat) = BoolResult("(${this.value} < ${a.value})")

    infix fun lt(a: Float) = BoolResult("(${this.value} < ${a.str()})")
    infix fun gt(a: Float) = BoolResult("(${this.value} > ${a.str()})")
}

fun floatComponent() = ComponentDelegate(::GLFloat)
