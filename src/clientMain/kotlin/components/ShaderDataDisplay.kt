package components

import react.*
import runtime.Simulation

external interface ShaderDataDisplayProps : Props {
    var simulations: List<Simulation>
}

val ShaderDataDisplay = FC<ShaderDataDisplayProps> { props ->
    val selectedSimulation = useContext(SimulationContext)

    SimulationSettings {
        simulations = props.simulations
    }

    if (selectedSimulation != null) {
        BreakpointEvaluator { }
        SimulationRunner { }
        ShaderOutline { fragmentShader = selectedSimulation.shaderProgram.fragmentShader }
        GlSlCode { source = selectedSimulation.shaderProgram.fragmentShader }
    }
}

