package components

import react.*

data class Point(val x: Int, val y: Int)

val SelectedPixelContext = createContext<Point?>()

val SetSelectedPixelContext = createContext<StateSetter<Point?>>()

val SelectedPixelProvider = FC<PropsWithChildren> { props ->
    val selectedPixel = useState<Point?>(null)

    SelectedPixelContext.Provider(selectedPixel.component1()) {
        SetSelectedPixelContext.Provider(selectedPixel.component2()) {
            +props
        }
    }
}
