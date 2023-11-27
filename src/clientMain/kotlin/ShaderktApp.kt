import components.ShaderCanvas
import components.ShaderDataDisplay
import org.khronos.webgl.WebGLRenderingContext
import org.w3c.dom.HTMLCanvasElement
import react.*
import runtime.Simulation

external interface ShaderktAppProps : Props {
    var simulationProviders: List<(WebGLRenderingContext) -> Simulation>
}

val ShaderktApp = FC<ShaderktAppProps> { props ->
    var selectedSimulation by useState<Simulation?>(null)
    val canvasRef = useRef<HTMLCanvasElement>()
    var simulations by useState<List<Simulation>?>(null)

    useLayoutEffect(props.simulationProviders, canvasRef.current) {
        selectedSimulation = null
        val canvas = canvasRef.current
        if (canvas != null) {
            val gl = canvas.getContext("webgl") as WebGLRenderingContext
            simulations = props.simulationProviders.map { it(gl) }
        }
    }

    if (simulations != null) {
        ShaderDataDisplay {
            this.selectedSimulation = selectedSimulation
            this.simulations = simulations!!
            onSelectedSimulationChanged = { selectedSimulation = it }
        }

    }
    ShaderCanvas { this.canvasRef = canvasRef }
}
