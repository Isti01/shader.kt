import com.salakheev.shaderbuilderkt.ShaderProgram
import components.GlSlCode
import components.ShaderOutline
import react.FC
import react.Props

external interface ShaderktAppProps : Props {
    var shader: ShaderProgram
}

val ShaderktApp = FC<ShaderktAppProps> { props ->
    console.log(props.shader.fragmentShader)
    ShaderOutline { fragmentShader = props.shader.fragmentShader }
    GlSlCode { source = props.shader.fragmentShader }
}