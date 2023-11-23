package components

import com.salakheev.shaderbuilderkt.builder.types.Symbol
import csstype.px
import emotion.react.css
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.strong
import react.key

external interface SymbolsProps : Props {
    var symbols: List<Symbol>
}

val Symbols = FC<SymbolsProps> { props ->
    console.log(props.symbols)
    for (symbol in props.symbols.filter { !it.name.isNullOrBlank() && !it.isUnused() }) {
        div {
            key = symbol.name
            css {
                padding = 5.px
            }
            strong {
                +symbol.typeName
            }
            +" ${symbol.name}"
        }
    }
}

