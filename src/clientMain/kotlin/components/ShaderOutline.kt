package components

import com.salakheev.shaderbuilderkt.builder.ShaderBuilder
import csstype.px
import emotion.react.css
import react.FC
import react.Props
import react.dom.html.ReactHTML.div

external interface ShaderOutlineProps : Props {
    var fragmentShader: ShaderBuilder
}

val ShaderOutline = FC<ShaderOutlineProps> { props ->
    val instructions = props.fragmentShader.instructions
    for (instruction in instructions) {
        console.log(instruction)
        div {
            css {
                padding = 5.px
            }
            +("${instruction.type} - $instruction")
        }
    }
}