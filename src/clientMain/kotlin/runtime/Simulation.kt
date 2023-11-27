package runtime

import com.salakheev.shaderbuilderkt.ShaderProgram
import com.salakheev.shaderbuilderkt.builder.debug.breakpoint.Breakpoint
import com.salakheev.shaderbuilderkt.builder.debug.breakpoint.UnsetBreakpoint
import org.khronos.webgl.WebGLFramebuffer
import org.khronos.webgl.WebGLRenderingContext

abstract class Simulation(protected val gl: WebGLRenderingContext, val shaderProgram: ShaderProgram, val name: String) {
    abstract fun init()
    abstract fun update(deltaTime: Float)

    fun debugRender(fragmentShaderBreakpoint: Breakpoint, framebuffer: WebGLFramebuffer) {
        gl.bindFramebuffer(WebGLRenderingContext.FRAMEBUFFER, framebuffer)
        shaderProgram.setFragmentShaderBreakpoint(fragmentShaderBreakpoint)
        shaderProgram.setVertexShaderBreakpoint(UnsetBreakpoint)
        renderImpl()
    }

    fun render() {
        gl.bindFramebuffer(WebGLRenderingContext.FRAMEBUFFER, null) // binds the canvas framebuffer
        shaderProgram.setVertexShaderBreakpoint(UnsetBreakpoint)
        shaderProgram.setFragmentShaderBreakpoint(UnsetBreakpoint)
        renderImpl()
    }

    protected abstract fun renderImpl()
}