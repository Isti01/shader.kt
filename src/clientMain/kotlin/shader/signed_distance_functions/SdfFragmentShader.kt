package shader.signed_distance_functions

import com.salakheev.shaderbuilderkt.builder.debug.DebuggableShader
import com.salakheev.shaderbuilderkt.builder.types.scalar.GLFloat
import com.salakheev.shaderbuilderkt.builder.types.vec.Vec2
import kotlin.math.PI

class SdfFragmentShader : DebuggableShader() {
    private val iResolution by uniform(::Vec2)
    private val iTime by uniform(::GLFloat)

    init {
        var d by float(100.0f)
        var col by vec3()
        val p by vec2(gl_FragCoord.x / iResolution.x, gl_FragCoord.y / iResolution.y)


        val circleRadius by float(.1f)
        var leftCirclePosition by vec2(.5f + .25f * cos(iTime), .5f + .25f * sin(iTime))
        d = min(d, length(p - leftCirclePosition) - circleRadius)

        var rightCirclePosition by vec2(
            .5f + .25f * cos(iTime + PI.toFloat() / 2.0f),
            .5f + .25f * sin(iTime + PI.toFloat() / 2.0f)
        )
        d = min(d, length(p - rightCirclePosition) - circleRadius / 2.0f)

        val white by vec3(1.0f, 1.0f, 1.0f)

        leftCirclePosition = white.xy - leftCirclePosition
        d = min(d, length(p - leftCirclePosition) - circleRadius)

        rightCirclePosition = white.xy - rightCirclePosition
        d = min(d, length(p - rightCirclePosition) - circleRadius / 2.0f)

        If(d gt 0.0f) {
            val positiveColor by vec3(0.9f, 0.6f, 0.3f)
            col = positiveColor
        }
        Else {
            val negativeColor by vec3(0.65f, 0.85f, 1.0f)
            col = negativeColor
        }
        col *= 1.0f - exp(-24.0f * abs(d))
        col *= 0.8f + 0.2f * cos(300.0f * d - iTime * 3.0f)
        col = mix(col, white, 1.0f - smoothstep(0.0f, 0.005f, abs(d)))
        gl_FragColor.xyz = col
        gl_FragColor.w = white.x
    }
}