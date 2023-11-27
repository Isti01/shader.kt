package com.salakheev.shaderbuilderkt

import com.salakheev.shaderbuilderkt.builder.debug.DebuggableShader
import com.salakheev.shaderbuilderkt.builder.debug.breakpoint.Breakpoint
import com.salakheev.shaderbuilderkt.sources.ShaderProgramSources

data class ShaderProgram(
    val vertexShader: DebuggableShader,
    val fragmentShader: DebuggableShader
) : ShaderProgramSources {
    fun setFragmentShaderBreakpoint(breakpoint: Breakpoint) {
        fragmentShader.breakpoint = breakpoint
    }

    fun setVertexShaderBreakpoint(breakpoint: Breakpoint) {
        vertexShader.breakpoint = breakpoint
    }

    override val vertex: String
        get() = vertexShader.getSource()
    override val fragment: String
        get() = fragmentShader.getSource()
}