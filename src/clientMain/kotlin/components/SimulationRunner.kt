package components

import kotlinx.browser.window
import react.FC
import react.Props
import react.useContext
import react.useEffect
import runtime.Simulation
import kotlin.js.Date

val SimulationRunner = FC<Props> { props ->
    val paused = useContext(PauseContext)
    val simulation = useContext(SimulationContext)
    useEffect(simulation, paused) {
        if (paused || simulation == null) return@useEffect
        var keepRunning = true
        runSimulation(simulation, Date()) { keepRunning }
        cleanup { keepRunning = false }
    }
}

private fun runSimulation(simulation: Simulation, lastFrameTime: Date, keepRunning: () -> Boolean) {
    if (!keepRunning()) return
    window.requestAnimationFrame {
        if (!keepRunning()) return@requestAnimationFrame
        val currentTime = Date()

        val deltaTime = (currentTime.getTime() - lastFrameTime.getTime()).toFloat() / 1000.0f
        simulation.update(deltaTime)
        simulation.render()

        runSimulation(simulation, currentTime, keepRunning)
    }
}
