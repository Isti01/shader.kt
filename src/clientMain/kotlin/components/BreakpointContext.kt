package components

import react.*

val BreakpointContext = createContext<Int?>()
val SetBreakpointContext = createContext<StateSetter<Int?>>()

val BreakpointProvider = FC<PropsWithChildren> {props->
    val breakpoint = useState<Int?>(null)

    BreakpointContext(breakpoint.component1()) {
        SetBreakpointContext(breakpoint.component2()) {
            +props.children
        }
    }
}
