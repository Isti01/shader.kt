package components

import com.salakheev.shaderbuilderkt.builder.ShaderBuilder
import csstype.px
import csstype.rgb
import emotion.react.css
import react.FC
import react.Props
import react.dom.html.ReactHTML.div

external interface InstructionVisualizationProps : Props {
    var fragmentShader: ShaderBuilder
}

val InstructionVisualization = FC<InstructionVisualizationProps> { props ->
    for (instruction in props.fragmentShader.instructions) {
        console.log(instruction)
        div {
            css {
                padding = 5.px
                backgroundColor = rgb(8, 97, 22)
                color = rgb(56, 246, 137)
            }
            +instruction.type.toString()
        }
    }
}