import com.salakheev.shaderbuilderkt.ShaderProgram
import components.ShaderOutline
import react.FC
import react.Props
import react.dom.html.ReactHTML.code
import react.dom.html.ReactHTML.pre

external interface ShaderktAppProps : Props {
    var shader: ShaderProgram
}

val ShaderktApp = FC<ShaderktAppProps> { props ->
    console.log(props.shader.fragmentShader)
    ShaderOutline { fragmentShader = props.shader.fragmentShader }
    pre {
        code {
            +props.shader.fragmentShader.getSource()
        }
    }
}