package components

import kotlinx.browser.window
import react.FC
import react.Props
import react.useContext
import react.useEffect
import runtime.Simulation
import kotlin.js.Date

external interface SimulationRunnerProps : Props {
    var simulation: Simulation
}

val SimulationRunner = FC<SimulationRunnerProps> { props ->
    val paused = useContext(PauseContext)
    useEffect(props.simulation, paused) {
        if (paused) return@useEffect
        var keepRunning = true
        runSimulation(props.simulation, Date()) { keepRunning }
        cleanup { keepRunning = false }
    }
}

private fun runSimulation(simulation: Simulation, lastFrameTime: Date, keepRunning: () -> Boolean) {
    if (!keepRunning()) return
    window.requestAnimationFrame {
        if (!keepRunning()) return@requestAnimationFrame
        val currentTime = Date()

        simulation.update((currentTime.getTime() - lastFrameTime.getTime()).toFloat() / 1000.0f)
        simulation.render()

        runSimulation(simulation, currentTime, keepRunning)
    }
}
