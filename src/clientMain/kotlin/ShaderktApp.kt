import com.salakheev.shaderbuilderkt.ShaderProgram
import components.ShaderOutline
import react.FC
import react.Props

external interface ShaderktAppProps : Props {
    var shader: ShaderProgram
}

val ShaderktApp = FC<ShaderktAppProps> { props ->
    ShaderOutline { fragmentShader = props.shader.fragmentShader }
}