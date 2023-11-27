package runtime

import com.salakheev.shaderbuilderkt.ShaderProgram
import org.khronos.webgl.Float32Array
import org.khronos.webgl.WebGLRenderingContext
import org.khronos.webgl.WebGLRenderingContext.Companion.FLOAT
import runtime.math.Matrix4x4
import runtime.mesh.Mesh
import runtime.mesh.VertexBuffer

class MeshSimulation(gl: WebGLRenderingContext, shaderProgram: ShaderProgram) : Simulation(gl, shaderProgram, "Mesh") {
    private val repository: DebugUberShaderRepository = DebugUberShaderRepository(gl)
    private lateinit var quad: Mesh

    private var time = 0.0f
    private var screenWidth = 1
    private var screenHeight = 1
    override fun init() {
        val coords = arrayOf(
            -1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, -1.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,
            1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f, 1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f, -1.0f,
            1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, 1.0f, 1.0f,
            -1.0f, -1.0f, 1.0f,
            1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f, 1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,
            1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f,
            1.0f, -1.0f, 1.0f
        )
        val positions = Float32Array(coords.map { it / 2 }.toTypedArray())
        val colors = Float32Array(coords.map { it / 2 + 0.5f }.toTypedArray())
        val vertexPositions = VertexBuffer(positions.buffer, positions.length / 3, "vertexPosition", 3, FLOAT)
        val vertexColors = VertexBuffer(colors.buffer, colors.length / 3, "vertexColor", 3, FLOAT)
        val vertexData = listOf(vertexPositions, vertexColors)
        quad = Mesh.create(vertexData, gl) ?: throw IllegalStateException("Could not initialize simulation")
    }

    override fun update(deltaTime: Float) {
        time += deltaTime
        screenWidth = gl.canvas.width
        screenHeight = gl.canvas.height
    }

    override fun renderImpl() {
        gl.viewport(0, 0, screenWidth, screenHeight)
        gl.clearColor(0.0f, 0.0f, 0.0f, 0.0f)
        gl.clear(WebGLRenderingContext.COLOR_BUFFER_BIT or WebGLRenderingContext.DEPTH_BUFFER_BIT)
        gl.enable(WebGLRenderingContext.DEPTH_TEST)

        val program = repository.getProgram(shaderProgram)
            ?: throw IllegalStateException("Could not retrieve shader for rendering")

        gl.useProgram(program)
        val mvp = gl.getUniformLocation(program, "mvp")
        gl.uniformMatrix4fv(mvp, false, Matrix4x4.rotate(.3f + time / 7f, time / 2f, 1 + time / 11f).data)

        quad.render(gl, program)
    }
}