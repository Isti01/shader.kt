package components

import com.salakheev.shaderbuilderkt.builder.instruction.Instruction
import csstype.*
import emotion.react.css
import react.FC
import react.Props
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.code
import react.dom.html.ReactHTML.h2
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.pre
import react.dom.html.ReactHTML.span
import react.key
import react.useContext

external interface InstructionsProps : Props {
    var instructions: List<Instruction>
}

val Instructions = FC<InstructionsProps> { props ->
    val breakpointLine = useContext(BreakpointContext)
    val setBreakpointLine = useContext(SetBreakpointContext)

    h2 {
        +"Shader instructions"
    }
    p {
        +"Click to place a breakpoint"
    }
    code {
        pre {
            css {
                padding = 0.px
                border = None.none
            }
            for ((i, instruction) in props.instructions.withIndex()) {
                val isBreakpoint = i == breakpointLine
                InstructionLine {
                    lineNumber = i
                    this.instruction = instruction
                    onSelected = { if (isBreakpoint) setBreakpointLine(null) else setBreakpointLine(i) }
                    this.isBreakpoint = isBreakpoint
                }
            }
        }
    }
}

private external interface InstructionLineProps : Props {
    var lineNumber: Int
    var instruction: Instruction
    var onSelected: () -> Unit
    var isBreakpoint: Boolean
}

private var InstructionLine = FC<InstructionLineProps> { props ->
    ReactHTML.div {
        onClick = { props.onSelected() }
        key = props.instruction.id
        css {
            cursor = Cursor.pointer
            display = Display.flex
        }
        span {
            css {
                display = Display.flex
                alignItems = AlignItems.center
                justifyContent = JustifyContent.spaceBetween
                minWidth = 5.em
                paddingLeft = 1.em
                paddingRight = 1.em
                background = Color("#FFF2")
                marginRight = 1.em
            }
            +(props.lineNumber + 1).toString()
            span {
                css {
                    display = Display.inlineBlock
                    borderRadius = 100.pct
                    width = .85.em
                    height = .85.em
                    backgroundColor = if (props.isBreakpoint) Color("#e11") else NamedColor.transparent
                }
            }
        }

        span {
            +props.instruction.toString()
        }
    }
}
