import com.salakheev.shaderbuilderkt.ShaderProgram
import components.BreakpointProvider
import components.DebugDataProvider
import components.PauseContextProvider
import kotlinx.browser.document
import org.khronos.webgl.WebGLRenderingContext
import react.StrictMode
import react.create
import react.dom.client.createRoot
import runtime.MeshSimulation
import runtime.ShaderSimulation
import runtime.Simulation
import shader.domain_distortion.DomainDistortionFragmentShader
import shader.domain_distortion.DomainDistortionVertexShader
import shader.vertex_color.VertexColorFragmentShader
import shader.vertex_color.VertexColorVertexShader

fun main() {
    val domainDistortionProgram = ShaderProgram(
        fragmentShader = DomainDistortionFragmentShader(),
        vertexShader = DomainDistortionVertexShader()
    )
    val meshColoringProgram = ShaderProgram(
        vertexShader = VertexColorVertexShader(),
        fragmentShader = VertexColorFragmentShader()
    )

    val providers = listOf<(WebGLRenderingContext) -> Simulation>(
        { MeshSimulation(it, meshColoringProgram) },
        { ShaderSimulation(it, domainDistortionProgram) }
    )

    val app = StrictMode.create {
        PauseContextProvider {
            BreakpointProvider {
                DebugDataProvider {
                    ShaderktApp { this.simulationProviders = providers }
                }
            }
        }
    }

    createRoot(document.getElementById("root")!!).render(app)
}
