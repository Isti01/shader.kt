package components

import org.khronos.webgl.Uint8Array
import org.khronos.webgl.Uint8ClampedArray
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import react.*
import react.dom.html.ReactHTML.canvas
import react.dom.html.ReactHTML.h1


external interface ShaderCanvasProps : Props {
    var canvasRef: MutableRefObject<HTMLCanvasElement>
}

val ShaderCanvas = FC<ShaderCanvasProps> { props ->
    val debugData = useContext(DebugDataContext)

    h1 {
        +"Preview"
    }
    canvas {
        ref = props.canvasRef
        width = 450.0
        height = 450.0
    }

    for (data in debugData?.results ?: emptyList()) {
        TextureDisplay { imageData = data.data }
    }
}

external interface TextureDisplayProps : Props {
    var imageData: Uint8Array
}

val TextureDisplay = FC<TextureDisplayProps> { props ->
    val canvasRef = useRef<HTMLCanvasElement>()

    useLayoutEffect(canvasRef.current, props.imageData) {
        val canvas = canvasRef.current
        if (canvas != null) {
            val context = canvas.getContext("2d") as CanvasRenderingContext2D
            val imageData = context.createImageData(450.0, 450.0)
            imageData.data.set(Uint8ClampedArray(props.imageData.buffer))
            context.putImageData(imageData, 0.0, 0.0)
        }
    }

    canvas {
        ref = canvasRef
        width = 450.0
        height = 450.0
    }
}
