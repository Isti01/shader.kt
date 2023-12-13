import com.salakheev.shaderbuilderkt.ShaderProgram
import components.*
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
import shader.signed_distance_functions.SdfFragmentShader
import shader.signed_distance_functions.SdfVertexShader
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
    val sdfProgram = ShaderProgram(
        vertexShader = SdfVertexShader(),
        fragmentShader = SdfFragmentShader()
    )

    val providers = listOf<(WebGLRenderingContext) -> Simulation>(
        { ShaderSimulation(it, sdfProgram, "Signed distance function") },
        { MeshSimulation(it, meshColoringProgram, "Mesh with vertex coloring") },
        { ShaderSimulation(it, domainDistortionProgram, "Domain distortion effect") },
    )

    val app = StrictMode.create {
        ShowDebugTextureProvider {
            SelectedPixelProvider {
                PauseContextProvider {
                    BreakpointProvider {
                        DebugDataProvider {
                            ShaderktApp { this.simulationProviders = providers }
                        }
                    }
                }
            }
        }
    }

    createRoot(document.getElementById("root")!!).render(app)
}
