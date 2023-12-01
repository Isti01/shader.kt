package components

import com.salakheev.shaderbuilderkt.builder.types.Symbol
import csstype.FontFamily
import csstype.em
import csstype.px
import emotion.react.css
import org.khronos.webgl.Uint8Array
import org.khronos.webgl.get
import react.FC
import react.Props
import react.dom.html.ReactHTML.details
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.em
import react.dom.html.ReactHTML.span
import react.dom.html.ReactHTML.strong
import react.dom.html.ReactHTML.summary
import react.key
import react.useContext
import runtime.debug.DebugData

external interface SymbolsProps : Props {
    var symbols: List<Symbol>
}

val Symbols = FC<SymbolsProps> { props ->
    val debugData = useContext(DebugDataContext)
    val selectedPixel = useContext(SelectedPixelContext)

    details {
        summary {
            +"Shader symbols"
        }
        div {
            for (symbol in props.symbols.filter { !it.name.isNullOrBlank() && !it.isUnused() }) {
                div {
                    key = symbol.name
                    css {
                        padding = 5.px
                        fontFamily = FontFamily.monospace
                    }
                    strong {
                        +symbol.typeName
                    }
                    +" ${symbol.name}"
                    SymbolValue {
                        this.symbol = symbol
                        this.selectedPixel = selectedPixel
                        this.debugData = debugData
                    }
                }
            }
        }
    }
}

private external interface SymbolValueProps : Props {
    var debugData: DebugData?
    var symbol: Symbol
    var selectedPixel: Point?
}

private val SymbolValue = FC<SymbolValueProps> { props ->
    val selectedPixel = props.selectedPixel ?: return@FC
    val data = props.debugData?.results?.find { it.symbolName == props.symbol.name }?.data
    if (data == null) {
        em {
            css {
                fontSize = .75.em
            }
            +" = no data"
        }
        return@FC
    }

    span {
        +" = ${getSymbolValue(props.symbol, data, selectedPixel)}"
    }

}

@OptIn(ExperimentalStdlibApi::class)
fun getSymbolValue(symbol: Symbol, data: Uint8Array, selectedPixel: Point): String {
    val x = selectedPixel.x
    val y = selectedPixel.y
    return when (symbol.typeName) {
        "float" -> (getElementAt(x, y, 0, data) / 255.0f).toString()
        "vec2" -> "#" + getElementAt(x, y, 0, data).toHexString() + getElementAt(x, y, 1, data).toHexString()
        "vec3" -> "#" + getElementAt(x, y, 0, data).toHexString() + getElementAt(x, y, 1, data).toHexString() +
                getElementAt(x, y, 2, data).toHexString()

        "vec4" -> "#" + getElementAt(x, y, 0, data).toHexString() + getElementAt(x, y, 1, data).toHexString() +
                getElementAt(x, y, 2, data).toHexString() + getElementAt(x, y, 3, data).toHexString()

        else -> getElementAt(x, y, 0, data).toString()
    }
}

private fun getElementAt(x: Int, y: Int, offset: Int, data: Uint8Array): Byte =
    data[y * CANVAS_WIDTH * 4 + x * 4 + offset]
