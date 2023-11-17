package com.salakheev.shaderbuilderkt.builder.debug

import com.salakheev.shaderbuilderkt.builder.ShaderBuilder
import com.salakheev.shaderbuilderkt.builder.instruction.InstructionType

abstract class DebuggableShader() : ShaderBuilder() {

    override fun getSource(): String {
        removeUnusedDefinitions()

        val sb = StringBuilder()
        sb.append(createDeclarations())

        sb.append("void main(void) {\n")
        sb.append(createMainBody())
        sb.append("}\n")

        return sb.toString()
    }

    private fun createDeclarations(): StringBuilder {
        val sb = StringBuilder()
        uniforms.forEach {
            sb.append("uniform $it;\n")
        }
        attributes.forEach {
            sb.append("attribute $it;\n")
        }
        varyings.forEach {
            sb.append("\nvarying $it;\n")
        }
        return sb
    }

    private fun createMainBody(): StringBuilder {
        val sb = StringBuilder()
        instructions.forEach {
            val instructionString = when (it.type) {
                InstructionType.DEFINE, InstructionType.ASSIGN -> "${it.result};"
                InstructionType.IF -> {
                    "if (${it.result}) {"
                }

                InstructionType.ELSEIF -> {
                    "else if (${it.result}) {"
                }

                InstructionType.ELSE -> {
                    "else {"
                }

                InstructionType.ENDIF -> "}"
                InstructionType.DISCARD -> "discard;"
            }
            sb.append(instructionString)
            sb.append('\n')
        }
        return sb
    }

    private fun removeUnusedDefinitions() {
        instructions.removeAll { it.result.contains("{def}") }
    }
}