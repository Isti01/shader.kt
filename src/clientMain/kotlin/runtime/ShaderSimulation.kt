package runtime

import com.salakheev.shaderbuilderkt.ShaderProgram
import org.khronos.webgl.Float32Array
import org.khronos.webgl.WebGLRenderingContext
import org.khronos.webgl.WebGLRenderingContext.Companion.FLOAT
import runtime.mesh.Mesh
import runtime.mesh.VertexBuffer

class ShaderSimulation(
    gl: WebGLRenderingContext,
    shaderProgram: ShaderProgram,
    name: String,
) : Simulation(gl, shaderProgram, name) {
    private val repository: DebugUberShaderRepository = DebugUberShaderRepository(gl)
    private lateinit var quad: Mesh

    private var time = 0.0f


    override fun init() {
        val positions = Float32Array(
            arrayOf(
                -1.0f, -1.0f, 0.0f,
                1.0f, -1.0f, 0.0f,
                -1.0f, 1.0f, 0.0f,
                1.0f, -1.0f, 0.0f,
                1.0f, 1.0f, 0.0f,
                -1.0f, 1.0f, 0.0f,
            )
        )
        val vertexData = listOf(VertexBuffer(positions.buffer, 6, "vertexPosition", 3, FLOAT))
        quad = Mesh.create(vertexData, gl) ?: throw IllegalStateException("Could not initialize simulation")
    }

    override fun update(deltaTime: Float) {
        time += deltaTime
        screenWidth = gl.canvas.width
        screenHeight = gl.canvas.height
    }

    override fun renderImpl(): Boolean {
        gl.viewport(0, 0, screenWidth, screenHeight)
        gl.clearColor(0.0f, 0.0f, 0.0f, 0.0f)
        gl.clear(WebGLRenderingContext.COLOR_BUFFER_BIT or WebGLRenderingContext.DEPTH_BUFFER_BIT)

        val program = repository.getProgram(shaderProgram) ?: return false

        gl.useProgram(program)
        val timeLocation = gl.getUniformLocation(program, "iTime")
        val resolutionLocation = gl.getUniformLocation(program, "iResolution")
        gl.uniform1f(timeLocation, time)
        gl.uniform2f(resolutionLocation, screenWidth.toFloat(), screenHeight.toFloat())

        quad.render(gl, program)
        return true
    }
}