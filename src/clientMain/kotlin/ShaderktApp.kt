import components.*
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
    val canvasRef = useRef<HTMLCanvasElement>()
    var simulations by useState<List<Simulation>?>(null)

    useLayoutEffect(props.simulationProviders, canvasRef.current) {
        val canvas = canvasRef.current
        if (canvas != null) {
            val gl = canvas.getContext("webgl") as WebGLRenderingContext
            val newSimulations = props.simulationProviders.map { it(gl) }
            newSimulations.forEach { it.init() }
            simulations = newSimulations
        }
    }

    ReactHTML.main {
        className = ClassName("shader-display")
        if (simulations != null) {
            PauseContextProvider {
                BreakpointProvider {
                    DebugDataProvider {
                        SimulationProvider {
                            this.simulations = simulations!!
                            ShaderDataDisplay { this.simulations = simulations!! }
                        }
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
