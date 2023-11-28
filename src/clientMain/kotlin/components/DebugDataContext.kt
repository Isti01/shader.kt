package components

import react.*
import runtime.debug.DebugData

val DebugDataContext = createContext<DebugData?>()
val SetDebugDataContext = createContext<StateSetter<DebugData?>>()

val DebugDataProvider = FC<PropsWithChildren> { props ->
    val data = useState<DebugData?>(null)
    DebugDataContext.Provider(data.component1()) {
        SetDebugDataContext(data.component2()) {
            +props.children
        }
    }
}
