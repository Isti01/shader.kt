package runtime

import org.khronos.webgl.WebGLRenderingContext

abstract class Simulation(protected val gl: WebGLRenderingContext) {
    abstract fun init()
    abstract fun update(time: Float)
    abstract fun render()
}