package components

import react.FC
import react.Props
import react.useContext
import react.useEffect
import runtime.Simulation

external interface ShaderDataDisplayProps : Props {
    var selectedSimulation: Simulation?
    var simulations: List<Simulation>
    var onSelectedSimulationChanged: (Simulation) -> Unit
}

val ShaderDataDisplay = FC<ShaderDataDisplayProps> { props ->
    val setBreakpointLine = useContext(SetBreakpointContext)
    val selectedSimulation = props.selectedSimulation

    useEffect(selectedSimulation) {
        setBreakpointLine(null)
    }

    SimulationSettings {
        this.selectedSimulation = selectedSimulation
        simulations = props.simulations
        onSelectedSimulationChanged = props.onSelectedSimulationChanged
    }

    if (selectedSimulation != null) {
        SimulationRunner { simulation = selectedSimulation }
        ShaderOutline { fragmentShader = selectedSimulation.shaderProgram.fragmentShader }
        GlSlCode { source = selectedSimulation.shaderProgram.fragmentShader }
    }
}

