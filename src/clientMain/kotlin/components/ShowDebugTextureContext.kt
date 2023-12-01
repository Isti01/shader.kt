package components

import react.*

val ShowDebugTextureContext = createContext<Boolean>()
val SetShowDebugTextureContext = createContext<StateSetter<Boolean>>()

val ShowDebugTextureProvider = FC<PropsWithChildren> { props ->
    val showDebugTextures = useState(false)
    ShowDebugTextureContext.Provider(showDebugTextures.component1()) {
        SetShowDebugTextureContext.Provider(showDebugTextures.component2()) {
            +props.children
        }
    }
}
