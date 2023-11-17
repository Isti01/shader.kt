package components

import com.salakheev.shaderbuilderkt.sources.ShaderSourceProvider
import csstype.ClassName
import react.FC
import react.Props
import react.dom.html.ReactHTML.code
import react.dom.html.ReactHTML.pre

external interface GlslCodeProps : Props {
    var source: ShaderSourceProvider
}

val GlSlCode = FC<GlslCodeProps> { props ->
    pre {
        code {
            className = ClassName("language-glsl")
            +props.source.getSource()
        }
    }
}