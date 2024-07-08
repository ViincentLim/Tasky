package ui.vectors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

@Composable
fun rememberNotifications(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "notifications",
            defaultWidth = 28.0.dp,
            defaultHeight = 28.0.dp,
            viewportWidth = 40.0f,
            viewportHeight = 40.0f
        ).apply {
            path(
                fill = SolidColor(Color.LightGray),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(8.167f, 31.625f)
                quadToRelative(-0.542f, 0f, -0.938f, -0.396f)
                quadToRelative(-0.396f, -0.396f, -0.396f, -0.937f)
                quadToRelative(0f, -0.542f, 0.396f, -0.938f)
                quadToRelative(0.396f, -0.396f, 0.938f, -0.396f)
                horizontalLineToRelative(2.125f)
                verticalLineTo(16.542f)
                quadToRelative(0f, -3.375f, 2.041f, -6.084f)
                quadToRelative(2.042f, -2.708f, 5.334f, -3.416f)
                verticalLineTo(5.875f)
                quadToRelative(0f, -1f, 0.666f, -1.625f)
                quadTo(19f, 3.625f, 20f, 3.625f)
                quadToRelative(0.958f, 0f, 1.646f, 0.625f)
                quadToRelative(0.687f, 0.625f, 0.687f, 1.625f)
                verticalLineToRelative(1.167f)
                quadToRelative(3.292f, 0.708f, 5.375f, 3.416f)
                quadToRelative(2.084f, 2.709f, 2.084f, 6.084f)
                verticalLineToRelative(12.416f)
                horizontalLineToRelative(2.041f)
                quadToRelative(0.542f, 0f, 0.938f, 0.396f)
                quadToRelative(0.396f, 0.396f, 0.396f, 0.938f)
                quadToRelative(0f, 0.541f, -0.396f, 0.937f)
                reflectiveQuadToRelative(-0.938f, 0.396f)
                close()
                moveTo(20f, 19.375f)
                close()
                moveToRelative(0f, 17.083f)
                quadToRelative(-1.333f, 0f, -2.312f, -0.958f)
                quadToRelative(-0.98f, -0.958f, -0.98f, -2.292f)
                horizontalLineToRelative(6.584f)
                quadToRelative(0f, 1.334f, -0.959f, 2.292f)
                quadToRelative(-0.958f, 0.958f, -2.333f, 0.958f)
                close()
                moveToRelative(-7.083f, -7.5f)
                horizontalLineToRelative(14.208f)
                verticalLineTo(16.583f)
                quadToRelative(0f, -3.041f, -2.042f, -5.104f)
                quadToRelative(-2.041f, -2.062f, -5.041f, -2.062f)
                quadToRelative(-2.959f, 0f, -5.042f, 2.083f)
                quadToRelative(-2.083f, 2.083f, -2.083f, 5.042f)
                close()
            }
        }.build()
    }
}