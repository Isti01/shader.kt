package runtime.texture

import org.khronos.webgl.WebGLFramebuffer
import org.khronos.webgl.WebGLRenderbuffer
import org.khronos.webgl.WebGLRenderingContext
import org.khronos.webgl.WebGLTexture


fun createFrameBuffer(
    gl: WebGLRenderingContext,
    depthBuffer: WebGLRenderbuffer?,
    width: Int,
    height: Int,
    texture: WebGLTexture
): WebGLFramebuffer? {
    if (depthBuffer == null) {
        return null
    }
    val framebuffer = gl.createFramebuffer() ?: return null

    gl.bindRenderbuffer(WebGLRenderingContext.RENDERBUFFER, depthBuffer)
    gl.bindFramebuffer(WebGLRenderingContext.FRAMEBUFFER, framebuffer)

    gl.renderbufferStorage(WebGLRenderingContext.RENDERBUFFER, WebGLRenderingContext.DEPTH_COMPONENT16, width, height)
    gl.framebufferRenderbuffer(
        WebGLRenderingContext.FRAMEBUFFER,
        WebGLRenderingContext.DEPTH_ATTACHMENT,
        WebGLRenderingContext.RENDERBUFFER,
        depthBuffer
    )

    gl.framebufferTexture2D(
        WebGLRenderingContext.FRAMEBUFFER,
        WebGLRenderingContext.COLOR_ATTACHMENT0,
        WebGLRenderingContext.TEXTURE_2D,
        texture,
        0
    )

    return framebuffer
}