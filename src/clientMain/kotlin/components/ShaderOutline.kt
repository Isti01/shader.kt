package components

import com.salakheev.shaderbuilderkt.builder.ShaderBuilder
import react.FC
import react.Props

external interface ShaderOutlineProps : Props {
    var fragmentShader: ShaderBuilder
}

val ShaderOutline = FC<ShaderOutlineProps> { props ->
    Symbols {
        symbols = props.fragmentShader.symbols
    }
    Instructions {
        instructions = props.fragmentShader.instructions
    }
}
