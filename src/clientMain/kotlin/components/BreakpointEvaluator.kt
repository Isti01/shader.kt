package components

import react.FC
import react.Props
import react.useContext
import react.useEffect


val BreakpointEvaluator = FC<Props> { props ->
    val breakpoint = useContext(BreakpointContext)
    val setDebugData = useContext(SetDebugDataContext)
    val simulation = useContext(SimulationContext)

    useEffect(breakpoint, simulation) {
        if (breakpoint != null && simulation != null) {
            val data = simulation.gatherDebugData(breakpoint)
            setDebugData(data)
        } else {
            setDebugData(null)
        }
    }
}
