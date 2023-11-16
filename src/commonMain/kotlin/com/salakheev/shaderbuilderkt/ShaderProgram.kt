package com.salakheev.shaderbuilderkt

import com.salakheev.shaderbuilderkt.builder.ShaderBuilder
import com.salakheev.shaderbuilderkt.sources.ShaderProgramSources

data class ShaderProgram(val vertexShader: ShaderBuilder, val fragmentShader: ShaderBuilder) : ShaderProgramSources {
    override val vertex: String
        get() = vertexShader.getSource()
    override val fragment: String
        get() = fragmentShader.getSource()
}