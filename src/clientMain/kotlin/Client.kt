import com.salakheev.shaderbuilderkt.ShaderProgram
import kotlinx.browser.document
import kotlinx.browser.window
import org.khronos.webgl.WebGLRenderingContext
import org.w3c.dom.HTMLCanvasElement
import react.StrictMode
import react.create
import react.dom.client.createRoot
import runtime.ShaderSimulation
import shader.DomainDistortionFragmentShader
import shader.DomainDistortionVertexShader
import kotlin.js.Date

fun main() {
    val shaderProgram = ShaderProgram(
        fragmentShader = DomainDistortionFragmentShader(),
        vertexShader = DomainDistortionVertexShader()
    )
    val canvas = document.getElementById("canvas") as HTMLCanvasElement
    val context = canvas.getContext("webgl") as WebGLRenderingContext
    val sim = ShaderSimulation(context, shaderProgram)
    val date = Date()
    draw(sim, date)

    val app = StrictMode.create {
        ShaderktApp { shader = shaderProgram }
    }
    createRoot(document.getElementById("root")!!).render(app)
}

fun draw(sim: ShaderSimulation, start: Date) {
    window.requestAnimationFrame {
        sim.init()
        sim.update((Date().getTime() - start.getTime()).toFloat() / 750.0f)
        sim.render()
        draw(sim, start)
    }
}
