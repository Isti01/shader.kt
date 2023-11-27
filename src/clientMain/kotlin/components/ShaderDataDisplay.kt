package components

import react.FC
import react.Props
import react.useEffect
import runtime.Simulation

external interface ShaderDataDisplayProps : Props {
    var selectedSimulation: Simulation?
    var simulations: List<Simulation>
    var onSelectedSimulationChanged: (Simulation) -> Unit
}

val ShaderDataDisplay = FC<ShaderDataDisplayProps> { props ->
    useEffect(props.selectedSimulation, props.simulations) {
        if (props.selectedSimulation == null && props.simulations.isNotEmpty()) {
            props.onSelectedSimulationChanged(props.simulations.first())
        }
    }
    SimulationSelector {
        selectedSimulation = props.selectedSimulation
        simulations = props.simulations
        onSelectedSimulationChanged = props.onSelectedSimulationChanged
    }
    if (props.selectedSimulation != null) {
        val shader = props.selectedSimulation?.shaderProgram!!
        SimulationRunner { simulation = props.selectedSimulation!! }
        ShaderOutline { fragmentShader = shader.fragmentShader }
        GlSlCode { source = shader.fragmentShader }
    }
}

