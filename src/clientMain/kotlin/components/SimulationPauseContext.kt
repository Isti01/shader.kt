package components

import react.*

val PauseContext = createContext<Boolean>()
val SetPauseContext = createContext<StateSetter<Boolean>>()

val PauseContextProvider = FC<PropsWithChildren> { props ->
    val paused = useState(false)

    PauseContext.Provider(paused.component1()) {
        SetPauseContext.Provider(paused.component2()) {
            +props.children
        }
    }
}