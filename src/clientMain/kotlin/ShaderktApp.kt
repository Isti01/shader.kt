import components.BreakpointProvider
import components.SetPauseContext
import components.ShaderCanvas
import components.ShaderDataDisplay
import csstype.ClassName
import org.khronos.webgl.WebGLRenderingContext
import org.w3c.dom.HTMLCanvasElement
import react.*
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.aside
import runtime.Simulation

external interface ShaderktAppProps : Props {
    var simulationProviders: List<(WebGLRenderingContext) -> Simulation>
}

val ShaderktApp = FC<ShaderktAppProps> { props ->
    var selectedSimulation by useState<Simulation?>(null)
    val setPaused = useContext(SetPauseContext)
    val canvasRef = useRef<HTMLCanvasElement>()
    var simulations by useState<List<Simulation>?>(null)

    useLayoutEffect(props.simulationProviders, canvasRef.current) {
        selectedSimulation = null
        val canvas = canvasRef.current
        if (canvas != null) {
            val gl = canvas.getContext("webgl") as WebGLRenderingContext
            val newSimulations = props.simulationProviders.map { it(gl) }
            newSimulations.forEach { it.init() }
            simulations = newSimulations
        }
    }

    useEffect(selectedSimulation, simulations) {
        if (selectedSimulation == null && simulations?.isNotEmpty() == true) {
            selectedSimulation = simulations!!.first()
        }
    }


    ReactHTML.main {
        className = ClassName("shader-display")
        if (simulations != null) {
            BreakpointProvider {
                ShaderDataDisplay {
                    this.selectedSimulation = selectedSimulation
                    this.simulations = simulations!!
                    onSelectedSimulationChanged = {
                        selectedSimulation = it
                        setPaused(false)
                    }
                }
            }
        }
    }
    aside {
        className = ClassName("canvas-container")
        ShaderCanvas { this.canvasRef = canvasRef }
    }
}
