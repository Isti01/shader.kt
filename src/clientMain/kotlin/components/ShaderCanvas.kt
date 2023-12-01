package components

import org.khronos.webgl.Uint8Array
import org.khronos.webgl.Uint8ClampedArray
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.events.EventListener
import org.w3c.dom.events.MouseEvent
import react.*
import react.dom.html.ReactHTML.canvas
import react.dom.html.ReactHTML.h1

const val CANVAS_WIDTH = 450
const val CANVAS_HEIGHT = 450


external interface ShaderCanvasProps : Props {
    var canvasRef: MutableRefObject<HTMLCanvasElement>
}

val ShaderCanvas = FC<ShaderCanvasProps> { props ->
    val debugData = useContext(DebugDataContext)
    val canvasElement = props.canvasRef.current
    val setSelectedPixel = useContext(SetSelectedPixelContext)
    val showDebugTextures = useContext(ShowDebugTextureContext)

    useEffect(canvasElement) {
        if (canvasElement == null) return@useEffect
        val clickListener = EventListener {
            val pointerEvent = it as MouseEvent
            val point = Point(pointerEvent.offsetX.toInt(), pointerEvent.offsetY.toInt())
            setSelectedPixel(point)
        }
        canvasElement.addEventListener("click", clickListener)

        cleanup {
            canvasElement.removeEventListener("click", clickListener)
        }
    }

    h1 {
        +"Preview"
    }
    canvas {
        ref = props.canvasRef
        width = CANVAS_WIDTH.toDouble()
        height = CANVAS_HEIGHT.toDouble()
    }

    if (showDebugTextures) {
        for (data in debugData?.results ?: emptyList()) {
            TextureDisplay { imageData = data.data }
        }
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
        width = CANVAS_WIDTH.toDouble()
        height = CANVAS_HEIGHT.toDouble()
    }
}
