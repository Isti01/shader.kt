package components

import react.*
import runtime.Simulation

val SimulationContext = createContext<Simulation?>()
val SetSimulationContext = createContext<(Simulation?) -> Unit>()

external interface SimulationProviderProps : PropsWithChildren {
    var simulations: List<Simulation>
}

val SimulationProvider = FC<SimulationProviderProps> { props ->
    val simulation = useState<Simulation?>(null)
    val setPaused = useContext(SetPauseContext)
    val setBreakpoint = useContext(SetBreakpointContext)
    val setDebugData = useContext(SetDebugDataContext)
    val setSimulation = useCallback<(Simulation?) -> Unit>(
        simulation.component2(),
        setPaused,
        setBreakpoint,
        setDebugData
    ) {
        setBreakpoint(null)
        setDebugData(null)
        setPaused(false)
        simulation.component2()(it)
    }

    useEffect(simulation, props.simulations) {
        if (props.simulations.isNotEmpty() && (props.simulations.indexOf(simulation.component1()) == -1)) {
            simulation.component2()(props.simulations.first())
        }
    }

    useLayoutEffect(simulation) {
        setBreakpoint(null)
        setDebugData(null)
        setPaused(false)
    }

    SimulationContext.Provider(simulation.component1()) {
        SetSimulationContext.Provider(setSimulation) {
            +props.children
        }
    }
}