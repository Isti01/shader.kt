import com.salakheev.shaderbuilderkt.examples.SimpleFragmentShader
import kotlinx.browser.document

fun main() {
    val pre = document.createElement("pre")
    val code = document.createElement("code")
    pre.appendChild(code)
    code.innerHTML = SimpleFragmentShader(alphaTest = true, receiveShadow = true).getSource()
    document.body?.appendChild(pre) ?: println("Failed to attach element to body")
}