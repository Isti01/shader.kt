package com.salakheev.shaderbuilderkt.builder.delegates

import com.salakheev.shaderbuilderkt.builder.instruction.Instruction
import com.salakheev.shaderbuilderkt.builder.instruction.InstructionType
import com.salakheev.shaderbuilderkt.builder.types.Variable
import kotlin.reflect.KProperty


const val UNUSED_DEFINITION_KEY = "{{unused_def}}"


class ConstructorDelegate<T : Variable>(private val v: T, initialValue: String? = null) {
    private var define: Instruction
    private var defined: Boolean = false

    init {
        val definitionString = "${v.typeName} ${UNUSED_DEFINITION_KEY}${getInitializerExpr(initialValue)}"
        define = Instruction(InstructionType.DEFINE, definitionString)
        v.builder.addInstruction(define)
    }

    operator fun provideDelegate(thisRef: Any?, property: KProperty<*>): ConstructorDelegate<T> {
        v.value = property.name
        return this
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        if (!defined) {
            define.result = define.result.replace(UNUSED_DEFINITION_KEY, property.name)
            defined = true
        }
        return v
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (!defined) {
            define.result = define.result.replace(UNUSED_DEFINITION_KEY, property.name)
            defined = true
        }
        v.builder.addInstruction(Instruction.assign(property.name, value.value))
    }

    private fun getInitializerExpr(initialValue: String?): String {
        return if (initialValue == null) "" else " = $initialValue"
    }
}
