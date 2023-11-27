package components

import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.option
import react.dom.html.ReactHTML.select
import react.useState
import runtime.Simulation

external interface SimulationSelectorProps : Props {
    var selectedSimulation: Simulation?
    var simulations: List<Simulation>
    var onSelectedSimulationChanged: (Simulation) -> Unit
}

val SimulationSelector = FC<SimulationSelectorProps> { props ->
    var selectedSimulationIndex by useState(getIndexOrNull(props.simulations, props.selectedSimulation))
    div {
        select {
            value = selectedSimulationIndex.toString()
            onChange = {
                val index = it.target.value
                selectedSimulationIndex = index
                props.onSelectedSimulationChanged(props.simulations[index.toInt()])
            }
            for ((i, simulation) in props.simulations.withIndex()) {
                option {
                    value = i.toString()
                    +simulation.name
                }
            }
        }
    }
}

private fun getIndexOrNull(simulations: List<Simulation>, selected: Simulation?): String? {
    if (selected == null) return null
    val index = simulations.indexOf(selected)
    return if (index == -1) null else index.toString()
}
