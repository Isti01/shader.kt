package components

import csstype.Display
import csstype.em
import emotion.react.css
import react.FC
import react.Props
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.option
import react.dom.html.ReactHTML.select
import react.useContext
import react.useState
import runtime.Simulation

external interface SimulationSettingsProps : Props {
    var simulations: List<Simulation>
}

val SimulationSettings = FC<SimulationSettingsProps> { props ->
    val selectedSimulation = useContext(SimulationContext)
    val setSelectedSimulation = useContext(SetSimulationContext)
    var selectedSimulationIndex by useState(getIndexOrNull(props.simulations, selectedSimulation))
    val setPaused = useContext(SetPauseContext)
    val paused = useContext(PauseContext)

    val showDebugTextures = useContext(ShowDebugTextureContext)
    val setShowDebugTextures = useContext(SetShowDebugTextureContext)
    div {
        h1 {
            +"Simulation settings"
        }
        div {
            css {
                display = Display.flex
                gap = 1.em
            }
            select {
                value = selectedSimulationIndex.toString()
                onChange = {
                    val index = it.target.value
                    selectedSimulationIndex = index
                    setSelectedSimulation(props.simulations[index.toInt()])
                }
                for ((i, simulation) in props.simulations.withIndex()) {
                    option {
                        value = i.toString()
                        +"${simulation.name} shader"
                    }
                }
            }
            button {
                onClick = { setPaused(!paused) }
                +(if (paused) "Resume simulation" else "Pause simulation")
            }
            button {
                onClick = { setShowDebugTextures(!showDebugTextures) }
                +(if (showDebugTextures) "Hide Debug Textures" else "Show Debug Textures")
            }
        }
    }
}

private fun getIndexOrNull(simulations: List<Simulation>, selected: Simulation?): String? {
    if (selected == null) return null
    val index = simulations.indexOf(selected)
    return if (index == -1) null else index.toString()
}
