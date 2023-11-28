package components

import kotlinx.browser.window
import react.*
import runtime.Simulation
import runtime.debug.DebugData
import kotlin.js.Date

val SimulationRunner = FC<Props> { props ->
    val paused = useContext(PauseContext)
    val simulation = useContext(SimulationContext)
    val breakpoint = useContext(BreakpointContext)
    val setDebugData = useContext(SetDebugDataContext)

    useEffect(simulation, paused, breakpoint, setDebugData) {
        if (paused || simulation == null) return@useEffect
        var keepRunning = true
        runSimulation(simulation, breakpoint, setDebugData, Date()) { keepRunning }
        cleanup { keepRunning = false }
    }
}

private fun runSimulation(
    simulation: Simulation,
    breakpoint: Int?,
    setDebugData: StateSetter<DebugData?>,
    lastFrameTime: Date,
    keepRunning: () -> Boolean
) {
    if (!keepRunning()) return
    if (breakpoint != null) {
        val data = simulation.gatherDebugData(breakpoint)
        setDebugData(data)
    }
    window.requestAnimationFrame {
        if (!keepRunning()) return@requestAnimationFrame
        val currentTime = Date()

        val deltaTime = (currentTime.getTime() - lastFrameTime.getTime()).toFloat() / 1000.0f
        simulation.update(deltaTime)
        simulation.render()

        runSimulation(simulation, breakpoint, setDebugData, currentTime, keepRunning)
    }
}
