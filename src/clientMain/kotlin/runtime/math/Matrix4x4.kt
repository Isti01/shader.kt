package runtime.math

import kotlin.math.cos
import kotlin.math.sin

class Matrix4x4 private constructor(val data: Array<Float>) {
    companion object {
        fun rotate(x: Float, y: Float, z: Float): Matrix4x4 {
            return Matrix4x4(
                arrayOf(
                    cos(y) * cos(z),
                    cos(y) * sin(z),
                    -sin(y),
                    0.0f,

                    sin(x) * sin(y) * cos(z) - cos(x) * sin(z),
                    sin(x) * sin(y) * sin(z) + cos(x) * cos(z),
                    sin(x) * cos(y),
                    0.0f,


                    cos(x) * sin(y) * cos(z) + sin(x) * sin(z),
                    cos(x) * sin(y) * sin(z) - sin(x) * cos(z),
                    cos(x) * cos(y),
                    0.0f,

                    0.0f, 0.0f, 0.0f, 1.0f
                )
            )
        }
    }
}