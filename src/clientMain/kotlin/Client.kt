import com.salakheev.shaderbuilderkt.ShaderProgram
import kotlinx.browser.document
import kotlinx.browser.window
import org.khronos.webgl.WebGLRenderingContext
import org.w3c.dom.HTMLCanvasElement
import react.StrictMode
import react.create
import react.dom.client.createRoot
import runtime.MeshSimulation
import runtime.Simulation
import shader.domain_distortion.DomainDistortionFragmentShader
import shader.domain_distortion.DomainDistortionVertexShader
import shader.vertex_color.VertexColorFragmentShader
import shader.vertex_color.VertexColorVertexShader
import kotlin.js.Date

fun main() {
    val shaderProgram = ShaderProgram(
        fragmentShader = DomainDistortionFragmentShader(),
        vertexShader = DomainDistortionVertexShader()
    )
    val meshColoringProgram = ShaderProgram(
        vertexShader = VertexColorVertexShader(),
        fragmentShader = VertexColorFragmentShader()
    )
    val canvas = document.getElementById("canvas") as HTMLCanvasElement
    val context = canvas.getContext("webgl") as WebGLRenderingContext
    val sim = MeshSimulation(context, meshColoringProgram) //ShaderSimulation(context, shaderProgram)
    val date = Date()
    draw(sim, date)

    val app = StrictMode.create {
        ShaderktApp { shader = shaderProgram }
    }
    createRoot(document.getElementById("root")!!).render(app)
}

fun draw(sim: Simulation, start: Date) {
    window.requestAnimationFrame {
        sim.init()
        sim.update((Date().getTime() - start.getTime()).toFloat() / 750.0f)
        sim.render()
        draw(sim, start)
    }
}
