package com.indrih.transitionandcompose

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter

@Composable
@Suppress("UndocumentedPublicFunction")
fun ArticleCard(title: String, image: String, onArticleClick: () -> Unit) {
    ClickableCard(
        modifier = Modifier.padding(vertical = 6.dp, horizontal = 12.dp),
        onClick = onArticleClick
    ) {
        Column {

            // Заголовок к статье
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                text = title,
                fontSize = 16.sp
            )

            // Картинка статьи
            Image(
                painter = rememberImagePainter(image),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.padding(top = 16.dp).aspectRatio(2f)
            )

            Spacer(modifier = Modifier.padding(bottom = 24.dp))
        }
    }
}

/** Стандартная карточка. */
@Composable
fun ClickableCard(
    modifier: Modifier = Modifier,
    elevation: Dp = 6.dp,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .then(modifier)
            .let { if (onClick != null) it.scaleAnimationOnPress(onClick) else it },
        elevation = elevation,
        shape = RoundedCornerShape(8.dp),
        content = content
    )
}

/** Добавляет анимацию скалирования при клике. */
fun Modifier.scaleAnimationOnPress(
    onClick: () -> Unit
): Modifier {
    return composed(
        inspectorInfo = debugInspectorInfo {
            name = "scaleAnimationOnPressModifier"
        }
    ) {
        var isPressed by remember { mutableStateOf(false) }
        val scale by animateFloatAsState(targetValue = if (isPressed) 0.9f else 1f, animationSpec = tween())
        then(
            scale(scale)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            isPressed = true
                            tryAwaitRelease()
                            isPressed = false
                        },
                        onTap = { onClick() }
                    )
                }
        )
    }
}