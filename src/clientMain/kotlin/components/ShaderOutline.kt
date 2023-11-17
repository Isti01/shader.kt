package components

import com.salakheev.shaderbuilderkt.builder.ShaderBuilder
import csstype.px
import emotion.react.css
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.strong
import react.key

external interface ShaderOutlineProps : Props {
    var fragmentShader: ShaderBuilder
}

val ShaderOutline = FC<ShaderOutlineProps> { props ->
    for (instruction in props.fragmentShader.instructions) {
        div {
            key = instruction.id
            css {
                padding = 5.px
            }
            strong {
                +instruction.type.toString()
            }
            +(" - $instruction")
        }
    }
}