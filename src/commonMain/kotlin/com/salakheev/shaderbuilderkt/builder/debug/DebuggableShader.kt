package com.salakheev.shaderbuilderkt.builder.debug

import com.salakheev.shaderbuilderkt.builder.ShaderBuilder
import com.salakheev.shaderbuilderkt.builder.debug.breakpoint.Breakpoint
import com.salakheev.shaderbuilderkt.builder.debug.breakpoint.UnsetBreakpoint
import com.salakheev.shaderbuilderkt.builder.instruction.Instruction

abstract class DebuggableShader : ShaderBuilder() {

    var breakpoint: Breakpoint = UnsetBreakpoint
    override val instructions: MutableList<Instruction>
        get() = ArrayList(super.instructions.filter { !it.isUnusedDefinition() })

    override fun getSource(): String {
        val sb = StringBuilder()
        sb.append(createDeclarations())

        sb.append("void main(void) {\n")
        sb.append(createMainBody())
        sb.append("}\n")

        return sb.toString()
    }

    private fun createDeclarations(): StringBuilder {
        val sb = StringBuilder()
        uniforms.union(breakpoint.uniforms).forEach {
            sb.append("uniform $it;\n\n")
        }
        attributes.forEach {
            sb.append("attribute $it;\n\n")
        }
        varyings.forEach {
            sb.append("varying $it;\n\n")
        }
        return sb
    }

    private fun createMainBody(): StringBuilder {
        val sb = StringBuilder()
        val interceptedInstructions = breakpoint.emitInstructions(instructions)
        interceptedInstructions.forEach(sb::append)
        return sb
    }
}
