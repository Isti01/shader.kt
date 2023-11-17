package com.salakheev.shaderbuilderkt.examples

import com.salakheev.shaderbuilderkt.builder.debug.DebuggableShader
import com.salakheev.shaderbuilderkt.builder.types.mat.Mat4
import com.salakheev.shaderbuilderkt.builder.types.vec.Vec2
import com.salakheev.shaderbuilderkt.builder.types.vec.Vec3
import com.salakheev.shaderbuilderkt.builder.types.vec.Vec4

class SimpleVertexShader(receiveShadow: Boolean) : DebuggableShader() {
    private val shadowMVP by uniform(::Mat4)

    private var vShadowCoord by varying(::Vec4)

    private val mvp by uniform(::Mat4)

    private val vertex by attribute(::Vec3)
    private val uv by attribute(::Vec2)

    private var vUV by varying(::Vec2)

    init {
        val inp by vec4(vertex, 1.0f)
        vUV = uv
        if (receiveShadow) {
            calcShadow(inp)
        }
        gl_Position = mvp * inp
    }

    private fun calcShadow(inp: Vec4) {
        vShadowCoord = shadowMVP * inp
        vShadowCoord.y = -vShadowCoord.y
        val offset by vec2(1f, 1f)
        vShadowCoord.xy = (vShadowCoord.xy + offset) / 2f
    }
}
