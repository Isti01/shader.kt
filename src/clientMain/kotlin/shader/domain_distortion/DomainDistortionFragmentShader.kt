package shader.domain_distortion

import com.salakheev.shaderbuilderkt.builder.debug.DebuggableShader
import com.salakheev.shaderbuilderkt.builder.types.scalar.GLFloat
import com.salakheev.shaderbuilderkt.builder.types.vec.Vec2
import com.salakheev.shaderbuilderkt.builder.types.vec.Vec3

class DomainDistortionFragmentShader : DebuggableShader() {
    // source: https://www.shadertoy.com/view/lsl3RH
    private val iResolution by uniform(::Vec2)
    private val iTime by uniform(::GLFloat)

    init {
        var col by vec3()
        val p by vec2(gl_FragCoord.x / iResolution.x + iTime * 0.1f, gl_FragCoord.y / iResolution.y)
        val t by float(iTime * 0.1f)

        val a by vec3(0.5f, 0.5f, 0.5f)
        val b by vec3(1.0f, 1.0f, 1.0f)
        val c by vec3(0.0f, 0.33f, 0.67f)
        val d by vec3(0.0f, 0.10f, 0.20f)
        val e by vec3(0.3f, 0.20f, 0.20f)
        val g by vec3(1.0f, 1.0f, 0.5f)
        val j by vec3(0.8f, 0.90f, 0.30f)
        val k by vec3(1.0f, 0.7f, 0.4f)
        val l by vec3(0.0f, 0.15f, 0.20f)
        val m by vec3(2.0f, 1.0f, 0.0f)
        val n by vec3(0.5f, 0.20f, 0.25f)
        val o by vec3(0.8f, 0.5f, 0.4f)
        val q by vec3(0.2f, 0.4f, 0.2f)
        val r by vec3(2.0f, 1.0f, 1.0f)
        val s by vec3(0.0f, 0.25f, 0.25f)
        col = pal(p.x, a, a, b, c)
        If(p.y gt (1 / 7.0f)) {
            col = pal(p.x - 2.0f * t, a, a, b, d)
        }
        If(p.y gt (2 / 7.0f)) {
            col = pal(p.x, a, a, b, e)
        }
        If(p.y gt (3 / 7.0f)) {
            col = pal(p.x - 2.0f * t, a, a, g, j)
        }
        If(p.y gt (4 / 7.0f)) {
            col = pal(p.x, a, a, k, l)
        }
        If(p.y gt (5 / 7.0f)) {
            col = pal(p.x - 2.0f * t, a, a, m, n)
        }
        If(p.y gt (6 / 7.0f)) {
            col = pal(p.x, o, q, r, s)
        }

        val f by float(fract(p.y * 7.0f))
        val upperBorder by float(0.49f)
        val lowerBorder by float(0.47f)
        col *= smoothstep(upperBorder, lowerBorder, abs(f - 0.5f))
        col *= 0.5f + sqrt(f * (1.0f - f) * 4.0f) / 2.0f

        val res by vec4(col, 1.0f)
        gl_FragColor = res
    }

    private fun pal(t: GLFloat, a: Vec3, b: Vec3, c: Vec3, d: Vec3): Vec3 {
        return a + b * cos((c * t + d) * 6.28318f)
    }
}