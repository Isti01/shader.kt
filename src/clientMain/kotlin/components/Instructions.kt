package components

import com.salakheev.shaderbuilderkt.builder.instruction.Instruction
import csstype.px
import emotion.react.css
import react.FC
import react.Props
import react.dom.html.ReactHTML
import react.key

external interface InstructionsProps : Props {
    var instructions: List<Instruction>
}

val Instructions = FC<InstructionsProps> { props ->
    for (instruction in props.instructions) {
        ReactHTML.div {
            key = instruction.id
            css {
                padding = 5.px
            }
            +instruction.toString()
        }
    }
}

