package com.salakheev.shaderbuilderkt.examples

import com.salakheev.shaderbuilderkt.builder.debug.DebuggableShader
import com.salakheev.shaderbuilderkt.builder.types.sampler.Sampler2D
import com.salakheev.shaderbuilderkt.builder.types.scalar.GLFloat
import com.salakheev.shaderbuilderkt.builder.types.vec.Vec2
import com.salakheev.shaderbuilderkt.builder.types.vec.Vec4

class SimpleFragmentShader(alphaTest: Boolean, receiveShadow: Boolean) : DebuggableShader() {
    private val alphaTestThreshold by uniform(::GLFloat)
    private val texture by uniform(::Sampler2D)

    private val vUV by varying(::Vec2)

    private val shadowTexture by uniform(::Sampler2D)

    private var vShadowCoord by varying(::Vec4)

    init {
        var color by vec4()
        color = texture2D(texture, vUV)
        if (alphaTest) {
            If(color.w lt alphaTestThreshold) {
                discard()
            }
        }
        var brightness by float(1.0f)
        if (receiveShadow) {
            brightness = calcShadow(brightness)
        }
        brightness = clamp(brightness, 0.5f, 1.0f)
        color.xyz = color.xyz * brightness
        gl_FragColor = color
    }

    private fun calcShadow(brightness: GLFloat): GLFloat {
        val bias by float(0.0035f)
        val shadowStep by float(0.4f)

        var newBrightness by float(brightness)

        var pixel by float()
        pixel = texture2D(shadowTexture, vShadowCoord.xy).x
        If(pixel lt (vShadowCoord.z + 1.0f) / 2.0f - bias) {
            newBrightness = newBrightness - shadowStep
        }
        return newBrightness
    }
}