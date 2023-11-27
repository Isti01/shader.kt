package components

import org.w3c.dom.HTMLCanvasElement
import react.FC
import react.MutableRefObject
import react.Props
import react.dom.html.ReactHTML.canvas


external interface ShaderCanvasProps : Props {
    var canvasRef: MutableRefObject<HTMLCanvasElement>
}

val ShaderCanvas = FC<ShaderCanvasProps> { props ->
    canvas {
        ref = props.canvasRef
        width = 600.0
        height = 600.0
    }
}

