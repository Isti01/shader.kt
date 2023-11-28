package runtime

import com.salakheev.shaderbuilderkt.ShaderProgram
import com.salakheev.shaderbuilderkt.builder.debug.breakpoint.Breakpoint
import com.salakheev.shaderbuilderkt.builder.debug.breakpoint.FramebufferOutputBreakpoint
import com.salakheev.shaderbuilderkt.builder.debug.breakpoint.UnsetBreakpoint
import com.salakheev.shaderbuilderkt.builder.types.Symbol
import org.khronos.webgl.Uint8Array
import org.khronos.webgl.WebGLFramebuffer
import org.khronos.webgl.WebGLRenderingContext
import org.khronos.webgl.WebGLTexture
import runtime.debug.DebugData
import runtime.debug.DebugExecutionResult
import runtime.texture.createFrameBuffer
import runtime.texture.createTexture

abstract class Simulation(protected val gl: WebGLRenderingContext, val shaderProgram: ShaderProgram, val name: String) {
    protected var screenWidth = 1
    protected var screenHeight = 1
    abstract fun init()
    abstract fun update(deltaTime: Float)
    protected abstract fun renderImpl(): Boolean

    fun render() {
        gl.bindFramebuffer(WebGLRenderingContext.FRAMEBUFFER, null) // binds the canvas framebuffer
        shaderProgram.setVertexShaderBreakpoint(UnsetBreakpoint)
        shaderProgram.setFragmentShaderBreakpoint(UnsetBreakpoint)
        renderImpl()
    }

    fun gatherDebugData(breakpoint: Int): DebugData {
        val texture = createTexture(gl, gl.canvas.width, gl.canvas.height) ?: return DebugData.empty()
        val framebuffer = createFrameBuffer(gl, texture)
        if (framebuffer == null) {
            gl.deleteTexture(texture)
            return DebugData.empty()
        }

        val results = ArrayList<DebugExecutionResult>()
        for (symbol in shaderProgram.fragmentShader.symbols.filter { it.name?.isNotBlank() == true }) {
            val result = getDebugResult(breakpoint, symbol, framebuffer, texture) ?: continue
            results.add(DebugExecutionResult(symbol.name!!, symbol.typeName, result))
        }

        gl.deleteFramebuffer(framebuffer)
        gl.deleteTexture(texture)

        return DebugData(results)
    }

    private fun getDebugResult(
        index: Int,
        symbol: Symbol,
        framebuffer: WebGLFramebuffer,
        texture: WebGLTexture
    ): Uint8Array? {
        val breakpoint = FramebufferOutputBreakpoint(index, symbol)
        if (!debugRender(breakpoint, framebuffer)) {
            console.log("Failed to do a debug render for symbol: ${symbol.typeName} ${symbol.name}")
            return null
        }
        val width = gl.canvas.width
        val height = gl.canvas.height
        val data = Uint8Array(width * height * 4)

        gl.bindTexture(WebGLRenderingContext.TEXTURE_2D, texture)
        gl.readPixels(0, 0, width, height, WebGLRenderingContext.RGBA, WebGLRenderingContext.UNSIGNED_BYTE, data)
        return data
    }

    private fun debugRender(fragmentShaderBreakpoint: Breakpoint, framebuffer: WebGLFramebuffer): Boolean {
        gl.bindFramebuffer(WebGLRenderingContext.FRAMEBUFFER, framebuffer)
        shaderProgram.setFragmentShaderBreakpoint(fragmentShaderBreakpoint)
        shaderProgram.setVertexShaderBreakpoint(UnsetBreakpoint)
        return renderImpl()
    }
}