package ru.kode.tools.opengate.android.ui

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import ru.kode.tools.opengate.android.R
import ru.kode.tools.opengate.story.Story
import kotlin.math.roundToInt

private enum class AnchoredDraggableSampleValue {
    Start, Center, End
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SwipableRow(
    leftSideWidth: Dp = 100.dp,
    rightSideWidth: Dp = 100.dp,
    left: (@Composable () -> Unit)? = null,
    right: (@Composable () -> Unit)? = null,
    children: @Composable () -> Unit
) {
    val density = LocalDensity.current
    val animationSpec = tween<Float>()
    val positionalThreshold = { distance: Float -> distance * 0.5f }
    val velocityThreshold = { with(density) { 125.dp.toPx() } }

    val state = rememberSaveable(
        density,
        saver = AnchoredDraggableState.Saver(animationSpec, positionalThreshold, velocityThreshold)
    ) {
        AnchoredDraggableState(
            initialValue = AnchoredDraggableSampleValue.Center,
            positionalThreshold,
            velocityThreshold,
            animationSpec
        )
    }

    var layout by remember { mutableStateOf(IntSize.Zero) }

    val layoutDp = with(density) {  DpSize(
        width = layout.width.toDp(),
        height = layout.height.toDp())
    }

    val leftSideWidthPx = with(density) { leftSideWidth.toPx() }
    val rightSideWidthPx = with(density) { rightSideWidth.toPx() }

    println(layout)

    SideEffect {
        state.updateAnchors(
            DraggableAnchors {
                if (right != null) AnchoredDraggableSampleValue.Start at -rightSideWidthPx
                AnchoredDraggableSampleValue.Center at 0f
                if (left != null)  AnchoredDraggableSampleValue.End at leftSideWidthPx
            }
        )
    }

    Box(
        Modifier
            .fillMaxWidth()
            .onGloballyPositioned {
                layout = it.size
            }) {

        if (left != null && layout != IntSize.Zero) {
            Box(
                modifier = Modifier
                    .width(leftSideWidth)
                    .height(layoutDp.height)
                    .offset {
                        IntOffset(
                            x = -leftSideWidthPx.roundToInt() + state
                                .requireOffset()
                                .roundToInt(),
                            y = 0
                        )
                    }
            ) {
                left()
            }
        }


        Box(
            Modifier
                .fillMaxWidth()
                .offset {
                    IntOffset(
                        x = state
                            .requireOffset()
                            .roundToInt(),
                        y = 0
                    )
                }
                .anchoredDraggable(state, Orientation.Horizontal)
        )
        {
            children()
        }

        if (right != null && layout != IntSize.Zero) {
            Box(
                modifier = Modifier
                    .width(rightSideWidth)
                    .height(layoutDp.height)
                    .offset {
                        IntOffset(
                            x = layout.width + state
                                .requireOffset()
                                .roundToInt(),
                            y = 0
                        )
                    }
            ) {
                right()
            }
        }
    }
}

@Preview
@Story
@Composable
fun SwipableRowPreview() {
    SwipableRow(
        rightSideWidth = 80.dp,
        right = {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.secondary)
                    .clickable {}){
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = stringResource(id = R.string.profile),
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            }
        },
        left = {
            Text(text = "Left Side", color = Color.Green)
        },
        children = {
            Column{
                Text(text = "Title", color = Color.Red)

                Spacer(modifier = Modifier.height(25.dp))

                Text(text = "Subtitle", color = Color.Red)
            }

        }
    )
}
