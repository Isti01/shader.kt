import com.salakheev.shaderbuilderkt.ShaderProgram
import components.InstructionVisualization
import react.FC
import react.Props

external interface ShaderktAppProps : Props {
    var shader: ShaderProgram
}

val ShaderktApp = FC<ShaderktAppProps> { props ->
    InstructionVisualization { fragmentShader = props.shader.fragmentShader }
}