package components

import csstype.ClassName
import org.w3c.dom.HTMLCanvasElement
import react.FC
import react.MutableRefObject
import react.Props
import react.dom.html.ReactHTML.canvas
import react.dom.html.ReactHTML.h1


external interface ShaderCanvasProps : Props {
    var canvasRef: MutableRefObject<HTMLCanvasElement>
}

val ShaderCanvas = FC<ShaderCanvasProps> { props ->
    h1 {
        +"Preview"
    }
    canvas {
        ref = props.canvasRef
        width = 450.0
        height = 450.0
    }
}

