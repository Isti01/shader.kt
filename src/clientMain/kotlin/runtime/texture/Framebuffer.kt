package runtime.texture

import org.khronos.webgl.WebGLFramebuffer
import org.khronos.webgl.WebGLRenderingContext
import org.khronos.webgl.WebGLTexture


fun createFrameBuffer(gl: WebGLRenderingContext, texture: WebGLTexture): WebGLFramebuffer? {
    val framebuffer = gl.createFramebuffer() ?: return null

    gl.bindFramebuffer(WebGLRenderingContext.FRAMEBUFFER, framebuffer)

    val attachmentPoint = WebGLRenderingContext.COLOR_ATTACHMENT0
    gl.framebufferTexture2D(
        WebGLRenderingContext.FRAMEBUFFER,
        attachmentPoint,
        WebGLRenderingContext.TEXTURE_2D,
        texture,
        0
    )

    return framebuffer
}