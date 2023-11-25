package com.salakheev.shaderbuilderkt.builder.debug

import com.salakheev.shaderbuilderkt.builder.ShaderBuilder
import com.salakheev.shaderbuilderkt.builder.debug.breakpoint.Breakpoint
import com.salakheev.shaderbuilderkt.builder.debug.breakpoint.UnsetBreakpoint
import com.salakheev.shaderbuilderkt.builder.instruction.Instruction
import com.salakheev.shaderbuilderkt.builder.instruction.InstructionType

abstract class DebuggableShader : ShaderBuilder() {
    companion object {
        private const val INDENTATION = "    "
    }

    var breakpoint: Breakpoint = UnsetBreakpoint
    override val instructions: MutableList<Instruction>
        get() = ArrayList(super.instructions.filter { !it.isUnusedDefinition() })

    override fun getSource(): String {
        val sb = StringBuilder()
        sb.append("precision highp float;\n\n")
        sb.append(createDeclarations())

        sb.append("void main() {\n")
        sb.append(createMainBody())
        sb.append("}\n")

        return sb.toString()
    }

    private fun createDeclarations(): StringBuilder {
        val sb = StringBuilder()
        uniforms.forEach {
            sb.append("uniform $it;\n")
        }
        if (sb.isNotBlank()) {
            sb.append('\n')
        }
        attributes.forEach {
            sb.append("attribute $it;\n")
        }
        if (attributes.isNotEmpty()) {
            sb.append('\n')
        }
        varyings.forEach {
            sb.append("varying $it;\n")
        }
        if (varyings.isNotEmpty()) {
            sb.append('\n')
        }
        return sb
    }

    private fun createMainBody(): StringBuilder {
        val sb = StringBuilder()
        val interceptedInstructions = breakpoint.emitInstructions(instructions)
        var indentationLevel = 1
        for (instruction in interceptedInstructions) {
            // the closing brace should be an indentation level lower
            when (instruction.type) {
                InstructionType.ENDIF -> indentationLevel--
                else -> {}
            }

            indentLine(sb, indentationLevel)
            sb.append(instruction)

            // we only want to indent the following lines
            when (instruction.type) {
                InstructionType.IF,
                InstructionType.ELSEIF,
                InstructionType.ELSE -> indentationLevel++

                else -> {}
            }
        }
        return sb
    }

    private fun indentLine(sb: StringBuilder, indentationLevel: Int) {
        for (i in 0..<indentationLevel) {
            sb.append(INDENTATION)
        }
    }
}
