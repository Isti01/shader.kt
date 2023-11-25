package runtime

import com.salakheev.shaderbuilderkt.ShaderProgram
import org.khronos.webgl.Float32Array
import org.khronos.webgl.WebGLRenderingContext
import org.khronos.webgl.WebGLRenderingContext.Companion.FLOAT
import runtime.mesh.Mesh
import runtime.mesh.VertexBuffer

class ShaderSimulation(gl: WebGLRenderingContext, private val shaderProgram: ShaderProgram) : Simulation(gl) {
    private val repository: DebugUberShaderRepository = DebugUberShaderRepository(gl)
    private lateinit var quad: Mesh

    var time = 0.0f
    var screenWidth = 1.0f
    var screeHeight = 1.0f


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

    override fun update(time: Float) {
        this.time = time
        screenWidth = gl.canvas.width.toFloat()
        screeHeight = gl.canvas.height.toFloat()
    }

    override fun render() {
        gl.viewport(0, 0, gl.canvas.width, gl.canvas.height)
        gl.clearColor(0.0f, 0.0f, 0.0f, 0.0f)
        gl.clear(WebGLRenderingContext.COLOR_BUFFER_BIT or WebGLRenderingContext.DEPTH_BUFFER_BIT)

        val program = repository.getProgram(shaderProgram)
            ?: throw IllegalStateException("Could not retrieve shader for rendering")

        gl.useProgram(program)
        val timeLocation = gl.getUniformLocation(program, "iTime")
        val resolutionLocation = gl.getUniformLocation(program, "iResolution")
        gl.uniform1f(timeLocation, time)
        gl.uniform2f(resolutionLocation, screenWidth, screeHeight)

        quad.render(gl, program)
    }
}