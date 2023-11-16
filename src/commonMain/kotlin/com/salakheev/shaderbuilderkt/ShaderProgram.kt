package com.salakheev.shaderbuilderkt

import com.salakheev.shaderbuilderkt.builder.ShaderBuilder

data class ShaderProgram(val vertexShader: ShaderBuilder, val fragmentShader: ShaderBuilder)