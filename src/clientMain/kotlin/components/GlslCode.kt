package components

import com.salakheev.shaderbuilderkt.sources.ShaderSourceProvider
import csstype.ClassName
import csstype.None
import csstype.px
import emotion.react.css
import react.FC
import react.Props
import react.dom.html.ReactHTML.code
import react.dom.html.ReactHTML.details
import react.dom.html.ReactHTML.pre
import react.dom.html.ReactHTML.summary
import react.key
import react.useEffect

external interface GlslCodeProps : Props {
    var source: ShaderSourceProvider
}

val GlSlCode = FC<GlslCodeProps> { props ->
    val source = props.source.getSource()
    useEffect(source) {
        eval("hljs.highlightAll();")
    }
    details {
        summary {
            +"Generated GLSL"
        }
        pre {
            css {
                padding = 0.px
                border = None.none
            }
            code {
                key = source
                className = ClassName("language-glsl")
                +source
            }
        }
    }
}