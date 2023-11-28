package runtime

import com.salakheev.shaderbuilderkt.ShaderProgram
import org.khronos.webgl.WebGLProgram
import org.khronos.webgl.WebGLRenderingContext
import org.khronos.webgl.WebGLShader

/// one shader provider can be configured to return different shader sources based on parameters like breakpoints
/// This class exists to instantiate and cache such shaders
class DebugUberShaderRepository(private val gl: WebGLRenderingContext) {
    private val shaders: MutableMap<String, WebGLShader> = HashMap()
    private val programs: MutableMap<String, WebGLProgram> = HashMap()


    fun getProgram(shaderProgram: ShaderProgram): WebGLProgram? {
        val vertexSource = shaderProgram.vertexShader.getSource()
        val fragmentSource = shaderProgram.fragmentShader.getSource()
        val programKey = getProgramKey(fragmentSource, vertexSource)
        return programs[programKey] ?: createShaderProgram(shaderProgram, programKey, fragmentSource, vertexSource)
    }

    private fun getProgramKey(fragmentSource: String, vertexSource: String): String = fragmentSource + vertexSource

    private fun createShaderProgram(
        shaderProgram: ShaderProgram,
        programKey: String,
        fragmentSource: String,
        vertexSource: String
    ): WebGLProgram? {
        val program = gl.createProgram()
        if (program == null) {
            console.log("Failed to create shader")
            return null
        }

        val vertexShader = getCachedShader(WebGLRenderingContext.VERTEX_SHADER, vertexSource) ?: return null
        gl.attachShader(program, vertexShader)

        val fragmentShader = getCachedShader(WebGLRenderingContext.FRAGMENT_SHADER, fragmentSource) ?: return null
        gl.attachShader(program, fragmentShader)
        gl.linkProgram(program)

        val success = gl.getProgramParameter(program, WebGLRenderingContext.LINK_STATUS)
        if (success is Boolean && success) {
            programs[programKey] = program
            enableShaderAttributes(program, shaderProgram)
            return program
        }

        console.log(gl.getProgramInfoLog(program))
        gl.deleteProgram(program)
        return null
    }

    private fun enableShaderAttributes(program: WebGLProgram, shaderProgram: ShaderProgram) {
        for (attribute in shaderProgram.vertexShader.attributeNames) {
            val location = gl.getAttribLocation(program, attribute)
            gl.enableVertexAttribArray(location)
        }
    }

    private fun getCachedShader(type: Int, source: String): WebGLShader? =
        shaders[source] ?: createShader(type, source)

    private fun createShader(type: Int, source: String): WebGLShader? {
        val shader = gl.createShader(type)
        if (shader == null) {
            console.log("Failed to create shader with type: $type")
            return null
        }

        gl.shaderSource(shader, source)
        gl.compileShader(shader)

        val success = gl.getShaderParameter(shader, WebGLRenderingContext.COMPILE_STATUS)
        if (success is Boolean && success) {
            shaders[source] = shader
            return shader
        }

        console.log(gl.getShaderInfoLog(shader))
        gl.deleteShader(shader)
        return null
    }

}