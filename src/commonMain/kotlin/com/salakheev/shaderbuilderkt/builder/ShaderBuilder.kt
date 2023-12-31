package com.salakheev.shaderbuilderkt.builder

import com.salakheev.shaderbuilderkt.builder.delegates.*
import com.salakheev.shaderbuilderkt.builder.instruction.Instruction
import com.salakheev.shaderbuilderkt.builder.instruction.InstructionType.*
import com.salakheev.shaderbuilderkt.builder.types.BoolResult
import com.salakheev.shaderbuilderkt.builder.types.Symbol
import com.salakheev.shaderbuilderkt.builder.types.Variable
import com.salakheev.shaderbuilderkt.builder.types.mat.Mat3
import com.salakheev.shaderbuilderkt.builder.types.mat.Mat4
import com.salakheev.shaderbuilderkt.builder.types.sampler.Sampler2D
import com.salakheev.shaderbuilderkt.builder.types.sampler.Sampler2DArray
import com.salakheev.shaderbuilderkt.builder.types.sampler.ShadowTexture2D
import com.salakheev.shaderbuilderkt.builder.types.scalar.GLFloat
import com.salakheev.shaderbuilderkt.builder.types.scalar.GLInt
import com.salakheev.shaderbuilderkt.builder.types.vec.Vec2
import com.salakheev.shaderbuilderkt.builder.types.vec.Vec3
import com.salakheev.shaderbuilderkt.builder.types.vec.Vec4
import com.salakheev.shaderbuilderkt.builder.util.str
import com.salakheev.shaderbuilderkt.sources.ShaderSourceProvider

@Suppress("PropertyName", "FunctionName", "unused")
abstract class ShaderBuilder : ShaderSourceProvider {
    companion object {
        const val FRAG_COLOR_VARIABLE = "gl_FragColor"
    }

    val uniforms = HashSet<String>()
    val attributes = HashSet<String>()
    val attributeNames = HashSet<String>()
    val varyings = HashSet<String>()
    val symbols = ArrayList<Symbol>()

    private val _instructions = ArrayList<Instruction>()
    open val instructions: List<Instruction> get() = _instructions

    protected var gl_Position by BuiltinVarDelegate()
    protected var gl_FragCoord by BuiltinVarDelegate()
    protected var gl_FragColor by BuiltinVarDelegate()

    fun addInstruction(instruction: Instruction) = _instructions.add(instruction)
    fun addSymbol(symbol: Symbol) = symbols.add(symbol)

    protected fun <T : Variable> varying(factory: (ShaderBuilder) -> T) = VaryingDelegate(factory)
    protected fun <T : Variable> attribute(factory: (ShaderBuilder) -> T) = AttributeDelegate(factory)
    protected fun <T : Variable> uniform(factory: (ShaderBuilder) -> T) = UniformDelegate(factory)
    protected fun <T : Variable> uniformArray(size: Int, init: (builder: ShaderBuilder) -> T) =
        UniformArrayDelegate(size, init)

    protected fun <T : Variable> samplersArray(size: Int) = UniformArrayDelegate(size, ::Sampler2DArray)

    protected fun discard() = addInstruction(Instruction(DISCARD))

    protected fun If(condition: BoolResult, body: () -> Unit) {
        addInstruction(Instruction(IF, condition.value))
        body()
        addInstruction(Instruction(ENDIF))
    }

    protected fun ElseIf(condition: BoolResult, body: () -> Unit) {
        addInstruction(Instruction(ELSEIF, condition.value))
        body()
        addInstruction(Instruction(ENDIF))
    }

    protected fun Else(body: () -> Unit) {
        addInstruction(Instruction(ELSE))
        body()
        addInstruction(Instruction(ENDIF))
    }

    protected fun castMat3(m: Mat4) = Mat3(this, "mat3(${m.value})")
    protected fun int(v: GLFloat) = GLInt(this, "int(${v.value})")

    protected fun radians(v: GLFloat) = GLFloat(this, "radians(${v.value})")
    protected fun radians(v: Vec2) = Vec2(this, "radians(${v.value})")
    protected fun radians(v: Vec3) = Vec3(this, "radians(${v.value})")
    protected fun radians(v: Vec4) = Vec4(this, "radians(${v.value})")

    protected fun degrees(v: GLFloat) = GLFloat(this, "degrees(${v.value})")
    protected fun degrees(v: Vec2) = Vec2(this, "degrees(${v.value})")
    protected fun degrees(v: Vec3) = Vec3(this, "degrees(${v.value})")
    protected fun degrees(v: Vec4) = Vec4(this, "degrees(${v.value})")

    protected fun sin(v: GLFloat) = GLFloat(this, "sin(${v.value})")
    protected fun sin(v: Vec2) = Vec2(this, "sin(${v.value})")
    protected fun sin(v: Vec3) = Vec3(this, "sin(${v.value})")
    protected fun sin(v: Vec4) = Vec4(this, "sin(${v.value})")

    protected fun cos(v: GLFloat) = GLFloat(this, "cos(${v.value})")
    protected fun cos(v: Vec2) = Vec2(this, "cos(${v.value})")
    protected fun cos(v: Vec3) = Vec3(this, "cos(${v.value})")
    protected fun cos(v: Vec4) = Vec4(this, "cos(${v.value})")

    protected fun tan(v: GLFloat) = GLFloat(this, "tan(${v.value})")
    protected fun tan(v: Vec2) = Vec2(this, "tan(${v.value})")
    protected fun tan(v: Vec3) = Vec3(this, "tan(${v.value})")
    protected fun tan(v: Vec4) = Vec4(this, "tan(${v.value})")

    protected fun acos(v: GLFloat) = GLFloat(this, "acos(${v.value})")
    protected fun acos(v: Vec2) = Vec2(this, "acos(${v.value})")
    protected fun acos(v: Vec3) = Vec3(this, "acos(${v.value})")
    protected fun acos(v: Vec4) = Vec4(this, "acos(${v.value})")

    protected fun atan(v: GLFloat) = GLFloat(this, "atan(${v.value})")
    protected fun atan(v: Vec2) = Vec2(this, "atan(${v.value})")
    protected fun atan(v: Vec3) = Vec3(this, "atan(${v.value})")
    protected fun atan(v: Vec4) = Vec4(this, "atan(${v.value})")

    protected fun exp(v: GLFloat) = GLFloat(this, "exp(${v.value})")
    protected fun exp(v: Vec2) = Vec2(this, "exp(${v.value})")
    protected fun exp(v: Vec3) = Vec3(this, "exp(${v.value})")
    protected fun exp(v: Vec4) = Vec4(this, "exp(${v.value})")

    protected fun log(v: GLFloat) = GLFloat(this, "log(${v.value})")
    protected fun log(v: Vec2) = Vec2(this, "log(${v.value})")
    protected fun log(v: Vec3) = Vec3(this, "log(${v.value})")
    protected fun log(v: Vec4) = Vec4(this, "log(${v.value})")

    protected fun exp2(v: GLFloat) = GLFloat(this, "exp2(${v.value})")
    protected fun exp2(v: Vec2) = Vec2(this, "exp2(${v.value})")
    protected fun exp2(v: Vec3) = Vec3(this, "exp2(${v.value})")
    protected fun exp2(v: Vec4) = Vec4(this, "exp2(${v.value})")

    protected fun log2(v: GLFloat) = GLFloat(this, "log2(${v.value})")
    protected fun log2(v: Vec2) = Vec2(this, "log2(${v.value})")
    protected fun log2(v: Vec3) = Vec3(this, "log2(${v.value})")
    protected fun log2(v: Vec4) = Vec4(this, "log2(${v.value})")

    protected fun sqrt(v: GLFloat) = GLFloat(this, "sqrt(${v.value})")
    protected fun sqrt(v: Vec2) = Vec2(this, "sqrt(${v.value})")
    protected fun sqrt(v: Vec3) = Vec3(this, "sqrt(${v.value})")
    protected fun sqrt(v: Vec4) = Vec4(this, "sqrt(${v.value})")

    protected fun inversesqrt(v: GLFloat) = GLFloat(this, "inversesqrt(${v.value})")
    protected fun inversesqrt(v: Vec2) = Vec2(this, "inversesqrt(${v.value})")
    protected fun inversesqrt(v: Vec3) = Vec3(this, "inversesqrt(${v.value})")
    protected fun inversesqrt(v: Vec4) = Vec4(this, "inversesqrt(${v.value})")

    protected fun abs(v: GLFloat) = GLFloat(this, "abs(${v.value})")
    protected fun abs(v: Vec2) = Vec2(this, "abs(${v.value})")
    protected fun abs(v: Vec3) = Vec3(this, "abs(${v.value})")
    protected fun abs(v: Vec4) = Vec4(this, "abs(${v.value})")

    protected fun sign(v: GLFloat) = GLFloat(this, "sign(${v.value})")
    protected fun sign(v: Vec2) = Vec2(this, "sign(${v.value})")
    protected fun sign(v: Vec3) = Vec3(this, "sign(${v.value})")
    protected fun sign(v: Vec4) = Vec4(this, "sign(${v.value})")

    protected fun floor(v: GLFloat) = GLFloat(this, "floor(${v.value})")
    protected fun floor(v: Vec2) = Vec2(this, "floor(${v.value})")
    protected fun floor(v: Vec3) = Vec3(this, "floor(${v.value})")
    protected fun floor(v: Vec4) = Vec4(this, "floor(${v.value})")

    protected fun ceil(v: GLFloat) = GLFloat(this, "ceil(${v.value})")
    protected fun ceil(v: Vec2) = Vec2(this, "ceil(${v.value})")
    protected fun ceil(v: Vec3) = Vec3(this, "ceil(${v.value})")
    protected fun ceil(v: Vec4) = Vec4(this, "ceil(${v.value})")

    protected fun fract(v: GLFloat) = GLFloat(this, "fract(${v.value})")
    protected fun fract(v: Vec2) = Vec2(this, "fract(${v.value})")
    protected fun fract(v: Vec3) = Vec3(this, "fract(${v.value})")
    protected fun fract(v: Vec4) = Vec4(this, "fract(${v.value})")

    protected fun mod(v: GLFloat, base: GLFloat) = GLFloat(this, "mod(${v.value}, ${base.value})")
    protected fun mod(v: Vec2, base: Vec2) = Vec2(this, "mod(${v.value}, ${base.value})")
    protected fun mod(v: Vec3, base: Vec3) = Vec3(this, "mod(${v.value}, ${base.value})")
    protected fun mod(v: Vec4, base: Vec4) = Vec4(this, "mod(${v.value}, ${base.value})")
    protected fun mod(v: Vec2, base: GLFloat) = Vec2(this, "mod(${v.value}, ${base.value})")
    protected fun mod(v: Vec3, base: GLFloat) = Vec3(this, "mod(${v.value}, ${base.value})")
    protected fun mod(v: Vec4, base: GLFloat) = Vec4(this, "mod(${v.value}, ${base.value})")

    protected fun min(v: GLFloat, base: GLFloat) = GLFloat(this, "min(${v.value}, ${base.value})")
    protected fun min(v: Vec2, base: Vec2) = Vec2(this, "min(${v.value}, ${base.value})")
    protected fun min(v: Vec3, base: Vec3) = Vec3(this, "min(${v.value}, ${base.value})")
    protected fun min(v: Vec4, base: Vec4) = Vec4(this, "min(${v.value}, ${base.value})")
    protected fun min(v: Vec2, base: GLFloat) = Vec2(this, "min(${v.value}, ${base.value})")
    protected fun min(v: Vec3, base: GLFloat) = Vec3(this, "min(${v.value}, ${base.value})")
    protected fun min(v: Vec4, base: GLFloat) = Vec4(this, "min(${v.value}, ${base.value})")

    protected fun max(v: GLFloat, v2: Float) = GLFloat(this, "max(${v.value}, ${v2.str()})")
    protected fun max(v: GLFloat, base: GLFloat) = GLFloat(this, "max(${v.value}, ${base.value})")
    protected fun max(v: Vec2, base: Vec2) = Vec2(this, "max(${v.value}, ${base.value})")
    protected fun max(v: Vec3, base: Vec3) = Vec3(this, "max(${v.value}, ${base.value})")
    protected fun max(v: Vec4, base: Vec4) = Vec4(this, "max(${v.value}, ${base.value})")
    protected fun max(v: Vec2, base: GLFloat) = Vec2(this, "max(${v.value}, ${base.value})")
    protected fun max(v: Vec3, base: GLFloat) = Vec3(this, "max(${v.value}, ${base.value})")
    protected fun max(v: Vec4, base: GLFloat) = Vec4(this, "max(${v.value}, ${base.value})")

    protected fun clamp(v: GLFloat, min: GLFloat, max: GLFloat) =
        GLFloat(this, "clamp(${v.value}, ${min.value}, ${max.value})")

    protected fun clamp(v: Vec2, min: Vec2, max: Vec2) = Vec2(this, "clamp(${v.value}, ${min.value}, ${max.value})")
    protected fun clamp(v: Vec3, min: Vec3, max: Vec3) = Vec3(this, "clamp(${v.value}, ${min.value}, ${max.value})")
    protected fun clamp(v: Vec4, min: Vec4, max: Vec4) = Vec4(this, "clamp(${v.value}, ${min.value}, ${max.value})")
    protected fun clamp(v: Vec2, min: GLFloat, max: GLFloat) =
        Vec2(this, "clamp(${v.value}, ${min.value}, ${max.value})")

    protected fun clamp(v: Vec3, min: GLFloat, max: GLFloat) =
        Vec3(this, "clamp(${v.value}, ${min.value}, ${max.value})")

    protected fun clamp(v: Vec4, min: GLFloat, max: GLFloat) =
        Vec4(this, "clamp(${v.value}, ${min.value}, ${max.value})")

    protected fun clamp(v: GLFloat, min: Float, max: Float) =
        GLFloat(this, "clamp(${v.value}, ${min.str()}, ${max.str()})")

    protected fun clamp(v: GLFloat, min: GLFloat, max: Float) =
        GLFloat(this, "clamp(${v.value}, ${min.value}, ${max.str()})")

    protected fun clamp(v: Vec2, min: Float, max: Float) = Vec2(this, "clamp(${v.value}, ${min.str()}, ${max.str()})")
    protected fun clamp(v: Vec3, min: Float, max: Float) = Vec3(this, "clamp(${v.value}, ${min.str()}, ${max.str()})")
    protected fun clamp(v: Vec4, min: Float, max: Float) = Vec4(this, "clamp(${v.value}, ${min.str()}, ${max.str()})")

    protected fun mix(v: GLFloat, y: GLFloat, a: GLFloat) = GLFloat(this, "mix(${v.value}, ${y.value}, ${a.value})")
    protected fun mix(v: Vec2, y: Vec2, a: Vec2) = Vec2(this, "mix(${v.value}, ${y.value}, ${a.value})")
    protected fun mix(v: Vec3, y: Vec3, a: Vec3) = Vec3(this, "mix(${v.value}, ${y.value}, ${a.value})")
    protected fun mix(v: Vec4, y: Vec4, a: Vec4) = Vec4(this, "mix(${v.value}, ${y.value}, ${a.value})")
    protected fun mix(v: Vec2, y: Vec2, a: GLFloat) = Vec2(this, "mix(${v.value}, ${y.value}, ${a.value})")
    protected fun mix(v: Vec3, y: Vec3, a: GLFloat) = Vec3(this, "mix(${v.value}, ${y.value}, ${a.value})")
    protected fun mix(v: Vec4, y: Vec4, a: GLFloat) = Vec4(this, "mix(${v.value}, ${y.value}, ${a.value})")

    protected fun step(v: GLFloat, x: GLFloat) = GLFloat(this, "step(${v.value}, ${x.value})")
    protected fun step(v: Vec2, x: Vec2) = Vec2(this, "step(${v.value}, ${x.value})")
    protected fun step(v: Vec3, x: Vec3) = Vec3(this, "step(${v.value}, ${x.value})")
    protected fun step(v: Vec4, x: Vec4) = Vec4(this, "step(${v.value}, ${x.value})")
    protected fun step(v: Vec2, x: GLFloat) = Vec2(this, "step(${v.value}, ${x.value})")
    protected fun step(v: Vec3, x: GLFloat) = Vec3(this, "step(${v.value}, ${x.value})")
    protected fun step(v: Vec4, x: GLFloat) = Vec4(this, "step(${v.value}, ${x.value})")

    protected fun pow(v: GLFloat, x: Float) = GLFloat(this, "pow(${v.value}, ${x.str()})")

    protected fun smoothstep(v: GLFloat, u: GLFloat, x: GLFloat) =
        GLFloat(this, "smoothstep(${v.value}, ${u.value}, ${x.value})")

    protected fun smoothstep(v: Float, u: Float, x: GLFloat) =
        GLFloat(this, "smoothstep(${v.str()}, ${u.str()}, ${x.value})")

    protected fun smoothstep(v: Vec2, u: Vec2, x: Vec2) = Vec2(this, "smoothstep(${v.value}, ${u.value}, ${x.value})")
    protected fun smoothstep(v: Vec3, u: Vec3, x: Vec3) = Vec3(this, "smoothstep(${v.value}, ${u.value}, ${x.value})")
    protected fun smoothstep(v: Vec4, u: Vec4, x: Vec4) = Vec4(this, "smoothstep(${v.value}, ${u.value}, ${x.value})")

    protected fun length(v: Symbol) = GLFloat(this, "length(${v.value})")
    protected fun distance(a: Symbol, b: Symbol) = GLFloat(this, "distance(${a.value}, ${b.value})")
    protected fun dot(a: Symbol, b: Symbol) = GLFloat(this, "dot(${a.value}, ${b.value})")
    protected fun cross(a: Vec3, b: Vec3) = Vec3(this, "dot(${a.value}, ${b.value})")
    protected fun normalize(v: GLFloat) = GLFloat(this, "normalize(${v.value})")
    protected fun normalize(v: Vec3) = Vec3(this, "normalize(${v.value})")
    protected fun normalize(v: Vec4) = Vec4(this, "normalize(${v.value})")
    protected fun reflect(i: Symbol, n: Symbol) = Vec3(this, "reflect(${i.value}, ${n.value})")
    protected fun refract(i: Symbol, n: Symbol, eta: GLFloat) =
        Vec3(this, "refract(${i.value}, ${n.value}, ${eta.value})")

    protected fun shadow2D(sampler: ShadowTexture2D, v: Vec2) = Vec4(this, "shadow2D(${sampler.value}, ${v.value})")
    protected fun texture2D(sampler: Sampler2D, v: Vec2) = Vec4(this, "texture2D(${sampler.value}, ${v.value})")

    protected fun float() = ConstructorDelegate(GLFloat(this))
    protected fun float(x: Float) = ConstructorDelegate(GLFloat(this), x.str())
    protected fun float(x: GLFloat) = ConstructorDelegate(GLFloat(this), x.value)

    protected fun intVal() = ConstructorDelegate(GLInt(this))
    protected fun intVal(x: GLInt) = ConstructorDelegate(GLInt(this), x.value)
    protected fun intVal(x: Int) = ConstructorDelegate(GLInt(this), "$x")

    protected fun vec2() = ConstructorDelegate(Vec2(this))
    protected fun vec2(x: Vec2) = ConstructorDelegate(Vec2(this), "${x.value}")
    protected fun vec2(x: Float, y: Float) = ConstructorDelegate(Vec2(this), "vec2(${x.str()}, ${y.str()})")
    protected fun vec2(x: GLFloat, y: Float) = ConstructorDelegate(Vec2(this), "vec2(${x.value}, ${y.str()})")
    protected fun vec2(x: Float, y: GLFloat) = ConstructorDelegate(Vec2(this), "vec2(${x.str()}, ${y.value})")
    protected fun vec2(x: GLFloat, y: GLFloat) = ConstructorDelegate(Vec2(this), "vec2(${x.value}, ${y.value})")

    protected fun vec3() = ConstructorDelegate(Vec3(this))
    protected fun vec3(x: GLFloat, y: GLFloat, z: GLFloat) =
        ConstructorDelegate(Vec3(this), ("vec3(${x.value}, ${y.value}, ${z.value})"))

    protected fun vec3(x: GLFloat, y: GLFloat, z: Float) =
        ConstructorDelegate(Vec3(this), ("vec3(${x.value}, ${y.value}, ${z.str()})"))

    protected fun vec3(x: GLFloat, y: Float, z: GLFloat) =
        ConstructorDelegate(Vec3(this), ("vec3(${x.value}, ${y.str()}, ${z.value})"))

    protected fun vec3(x: GLFloat, y: Float, z: Float) =
        ConstructorDelegate(Vec3(this), ("vec3(${x.value}, ${y.str()}, ${z.str()})"))

    protected fun vec3(x: Float, y: GLFloat, z: GLFloat) =
        ConstructorDelegate(Vec3(this), ("vec3(${x.str()}, ${y.value}, ${z.value})"))

    protected fun vec3(x: Float, y: GLFloat, z: Float) =
        ConstructorDelegate(Vec3(this), ("vec3(${x.str()}, ${y.value}, ${z.str()})"))

    protected fun vec3(x: Float, y: Float, z: GLFloat) =
        ConstructorDelegate(Vec3(this), ("vec3(${x.str()}, ${y.str()}, ${z.value})"))

    protected fun vec3(x: Float, y: Float, z: Float) =
        ConstructorDelegate(Vec3(this), ("vec3(${x.str()}, ${y.str()}, ${z.str()})"))

    protected fun vec3(v2: Vec2, z: Float) = ConstructorDelegate(Vec3(this), ("vec3(${v2.value}, ${z.str()})"))
    protected fun vec3(v2: Vec2, z: GLFloat) = ConstructorDelegate(Vec3(this), ("vec3(${v2.value}, ${z.value})"))
    protected fun vec3(x: Float, v2: Vec2) = ConstructorDelegate(Vec3(this), ("vec3(${x.str()}, ${v2.value})"))
    protected fun vec3(x: GLFloat, v2: Vec2) = ConstructorDelegate(Vec3(this), ("vec3(${x.value}, ${v2.value})"))

    protected fun vec4() = ConstructorDelegate(Vec4(this))
    protected fun vec4(vec3: Vec3, w: Float) = ConstructorDelegate(Vec4(this), ("vec4(${vec3.value}, ${w.str()})"))
    protected fun vec4(vec3: Vec3, w: GLFloat) = ConstructorDelegate(Vec4(this), ("vec4(${vec3.value}, ${w.value})"))
    protected fun vec4(vec2: Vec2, z: Float, w: Float) =
        ConstructorDelegate(Vec4(this), ("vec4(${vec2.value}, ${z.str()}, ${w.str()})"))

    protected fun vec4(x: GLFloat, y: GLFloat, zw: Vec2) =
        ConstructorDelegate(Vec4(this), ("vec4(${x.value}, ${y.value}, ${zw.value})"))

    protected fun vec4(x: Float, y: Float, z: Float, w: Float) =
        ConstructorDelegate(Vec4(this), ("vec4(${x.str()}, ${y.str()}, ${z.str()}, ${w.str()})"))

    protected fun vec4(x: Float, y: Float, z: Float, w: GLFloat) =
        ConstructorDelegate(Vec4(this), ("vec4(${x.str()}, ${y.str()}, ${z.str()}, ${w.value})"))

    protected fun vec4(x: GLFloat, y: GLFloat, z: GLFloat, w: GLFloat) =
        ConstructorDelegate(Vec4(this), ("vec4(${x.value}, ${y.value}, ${z.value}, ${w.value})"))

    protected fun vec4(x: GLFloat, y: GLFloat, z: GLFloat, w: Float) =
        ConstructorDelegate(Vec4(this), ("vec4(${x.value}, ${y.value}, ${z.value}, ${w.str()})"))

    protected fun vec4(x: GLFloat, y: GLFloat, z: Float, w: Float) =
        ConstructorDelegate(Vec4(this), ("vec4(${x.value}, ${y.value}, ${z.str()}, ${w.str()})"))

    protected fun mat3() = ConstructorDelegate(Mat3(this))

    operator fun Float.minus(a: GLFloat) = GLFloat(a.builder, "(${this.str()} - ${a.value})")
    operator fun Float.plus(a: GLFloat) = GLFloat(a.builder, "(${this.str()} + ${a.value})")
    operator fun Float.times(a: GLFloat) = GLFloat(a.builder, "(${this.str()} * ${a.value})")
    operator fun Float.div(a: GLFloat) = GLFloat(a.builder, "(${this.str()} / ${a.value})")
}



