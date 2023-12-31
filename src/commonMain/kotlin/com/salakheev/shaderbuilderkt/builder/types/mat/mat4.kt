package com.salakheev.shaderbuilderkt.builder.types.mat

import com.salakheev.shaderbuilderkt.builder.ShaderBuilder
import com.salakheev.shaderbuilderkt.builder.types.Matrix
import com.salakheev.shaderbuilderkt.builder.types.vec.Vec4
import com.salakheev.shaderbuilderkt.builder.util.str

class Mat4(override val builder: ShaderBuilder) : Matrix {

    override val typeName: String = "mat4"
    override var value: String? = null

    private var column1 by MatrixColumnDelegate(0, ::Vec4)
    private var column2 by MatrixColumnDelegate(1, ::Vec4)
    private var column3 by MatrixColumnDelegate(2, ::Vec4)
    private var column4 by MatrixColumnDelegate(3, ::Vec4)

    constructor(builder: ShaderBuilder, value: String) : this(builder) {
        this.value = value
    }

    operator fun get(i: Int): Vec4 {
        return when (i) {
            0 -> column1
            1 -> column2
            2 -> column3
            3 -> column4
            else -> throw Error("Column index $i out of range [0..3]")
        }
    }

    operator fun times(a: Float) = Mat4(builder, "(${this.value} * ${a.str()})")
    operator fun div(a: Float) = Mat4(builder, "(${this.value} / ${a.str()})")

    operator fun times(a: Vec4) = Vec4(builder, "(${this.value} * ${a.value})")
    operator fun div(a: Vec4) = Vec4(builder, "(${this.value} / ${a.value})")

    operator fun times(a: Mat4) = Mat4(builder, "(${this.value} * ${a.value})")
    operator fun div(a: Mat4) = Mat4(builder, "(${this.value} / ${a.value})")
    operator fun plus(a: Mat4) = Mat4(builder, "(${this.value} + ${a.value})")
    operator fun minus(a: Mat4) = Mat4(builder, "(${this.value} - ${a.value})")

    operator fun unaryMinus() = Mat4(builder, "-(${this.value})")
}

operator fun Float.times(a: Mat4) = Mat4(a.builder, "(${this.str()} * ${a.value})")
operator fun Float.div(a: Mat4) = Mat4(a.builder, "(${this.str()} / ${a.value})")
