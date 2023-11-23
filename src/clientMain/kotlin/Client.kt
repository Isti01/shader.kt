import com.salakheev.shaderbuilderkt.ShaderProgram
import kotlinx.browser.document
import react.StrictMode
import react.create
import react.dom.client.createRoot
import shader.DomainDistortionFragmentShader
import shader.DomainDistortionVertexShader

fun main() {
    val shaderProgram = ShaderProgram(
        fragmentShader = DomainDistortionFragmentShader(),
        vertexShader = DomainDistortionVertexShader()
    )
    val app = StrictMode.create {
        ShaderktApp { shader = shaderProgram }
    }
    createRoot(document.getElementById("root")!!).render(app)
}
