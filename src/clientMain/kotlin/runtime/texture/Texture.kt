package runtime.texture

import org.khronos.webgl.WebGLRenderingContext
import org.khronos.webgl.WebGLTexture

fun createTexture(gl: WebGLRenderingContext, width: Int, height: Int): WebGLTexture? {
    val targetTexture = gl.createTexture() ?: return null
    gl.bindTexture(WebGLRenderingContext.TEXTURE_2D, targetTexture)


    val level = 0
    val internalFormat = WebGLRenderingContext.RGBA
    val border = 0
    val format = WebGLRenderingContext.RGBA
    val type = WebGLRenderingContext.UNSIGNED_BYTE
    val data = null
    gl.texImage2D(WebGLRenderingContext.TEXTURE_2D, level, internalFormat, width, height, border, format, type, data)

    gl.texParameteri(
        WebGLRenderingContext.TEXTURE_2D,
        WebGLRenderingContext.TEXTURE_MIN_FILTER,
        WebGLRenderingContext.LINEAR
    )
    gl.texParameteri(
        WebGLRenderingContext.TEXTURE_2D,
        WebGLRenderingContext.TEXTURE_WRAP_S,
        WebGLRenderingContext.CLAMP_TO_EDGE
    )
    gl.texParameteri(
        WebGLRenderingContext.TEXTURE_2D,
        WebGLRenderingContext.TEXTURE_WRAP_T,
        WebGLRenderingContext.CLAMP_TO_EDGE
    )
    return targetTexture
}