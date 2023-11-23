import com.salakheev.shaderbuilderkt.ShaderProgram
import com.salakheev.shaderbuilderkt.examples.SimpleFragmentShader
import com.salakheev.shaderbuilderkt.examples.SimpleVertexShader
import kotlinx.browser.document
import react.StrictMode
import react.create
import react.dom.client.createRoot

fun main() {
    val shaderProgram = ShaderProgram(
        fragmentShader = SimpleFragmentShader(alphaTest = true, receiveShadow = true),
        vertexShader = SimpleVertexShader(true)
    )
    val app = StrictMode.create {
        ShaderktApp { shader = shaderProgram }
    }
    createRoot(document.getElementById("root")!!).render(app)
}
