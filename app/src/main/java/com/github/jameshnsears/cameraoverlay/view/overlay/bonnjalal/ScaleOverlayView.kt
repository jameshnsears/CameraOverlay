package com.github.jameshnsears.cameraoverlay.view.overlay.bonnjalal

import android.content.Context
import android.graphics.PointF
import android.graphics.RectF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.changedToDown
import androidx.compose.ui.input.pointer.changedToUp
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import com.github.jameshnsears.cameraoverlay.R
import com.github.jameshnsears.cameraoverlay.view.overlay.bonnjalal.DeviceDimensionsHelper.convertDpToPixel
import timber.log.Timber
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
fun ScaleOverView(
    context: Context, enableDrag: Boolean = true,
    showGrid: Boolean = true,
    strokeColor: Color,
    fillColor: Color,
    gridLineCount: Int,
    gridColor: Color,
    strokeSize: Float,
    gridLineSize: Float,
    cornerBitmap: ImageBitmap? = null,
    cropBoxData: (cropRect: RectF) -> Unit
) {

    val ACTION_IDLE = 0
    val ACTION_DOWN = 1
    val ACTION_MOVE = 2
    val ACTION_UP = 3

    //val mTouchPointThreshold =
    //  dimensionResource(id = R.dimen.ucrop_default_crop_rect_corner_touch_threshold)
    val mTPT = DeviceDimensionsHelper.convertDpToPixel(30F, context)

    var groupId by remember { mutableStateOf(-1) }

    val path = remember { Path() }
    var motionEvent by remember { mutableStateOf(ACTION_IDLE) }
    var currentPosition by remember { mutableStateOf(Offset.Unspecified) }

    var mPrevious by remember { mutableStateOf(Offset.Unspecified) }

    //var points by remember { mutableStateOf(arrayOfNulls<Offset>(4)) }

    //var mCropRect by remember { mutableStateOf(RectF()) }
    //var mTempRect by remember { mutableStateOf(RectF()) }

    val mCropRect = remember { RectF() }
    val mTempRect = remember { RectF() }

    //var points = arrayOfNulls<Point>(4)
    //var points by remember { mutableStateOf(arrayOfNulls<PointF>(4)) }

    var points = remember { arrayOfNulls<PointF>(4) }


    // color and text are for debugging and observing state changes and position
    //var gestureColor by remember { mutableStateOf(Color.LightGray) }
    //var gestureText by remember { mutableStateOf("Touch to Draw") }

    val drawModifier = Modifier
        .fillMaxSize()
        //.background(gestureColor)
        //.clipToBounds()
        .pointerInput(Unit) {
            forEachGesture {
                awaitPointerEventScope {

                    // Wait for at least one pointer to press down, and set first contact position
                    val down: PointerInputChange = awaitFirstDown().also {
                        motionEvent = ACTION_DOWN
                        currentPosition = it.position
                        //gestureColor = Color.Blue
                    }

                    do {
                        // This PointerEvent contains details including events, id, position and more
                        val event: PointerEvent = awaitPointerEvent()

                        var eventChanges =
                            "DOWN changedToDown: ${down.changedToDown()} changedUp: ${down.changedToUp()}\n"
                        event.changes
                            .forEachIndexed { index: Int, pointerInputChange: PointerInputChange ->
                                eventChanges += "Index: $index, id: ${pointerInputChange.id}, " +
                                        "changedUp: ${pointerInputChange.changedToUp()}" +
                                        "pos: ${pointerInputChange.position}\n"

                                // This necessary to prevent other gestures or scrolling
                                // when at least one pointer is down on canvas to draw
                                pointerInputChange.consumePositionChange()
                            }

                        //gestureText = "EVENT changes size ${event.changes.size}\n" + eventChanges

                        //gestureColor = Color.Green
                        motionEvent = ACTION_MOVE
                        currentPosition = event.changes.first().position
                    } while (event.changes.any { it.pressed })

                    motionEvent = ACTION_UP
                    //gestureColor = Color.Yellow

                    //gestureText += "UP changedToDown: ${down.changedToDown()} " +
                    //      "changedUp: ${down.changedToUp()}\n"
                }
            }
        }


    Canvas(modifier = drawModifier) {

        //val X = currentPosition.x
        //val Y = currentPosition.y
        when (motionEvent) {
            ACTION_DOWN -> {
                //path.moveTo(currentPosition.x, currentPosition.y)

                val X = currentPosition.x
                val Y = currentPosition.y
                if (points[0] == null) {

                    //initRectangle(currentPosition.x, currentPosition.y)
                    points[0] = PointF()
                    points[0]!!.x = X
                    points[0]!!.y = Y
                    points[1] = PointF()
                    points[1]!!.x = X
                    points[1]!!.y = Y + 200
                    //points[2] = Point()
                    points[2]!!.x = X // + 200;
                    points[2]!!.y = Y // + 200;
                    points[3] = PointF()
                    points[3]!!.x = X + 200
                    points[3]!!.y = Y
                    //balID = 2
                    groupId = 1

                    var left: Float
                    var top: Float
                    var right: Float
                    var bottom: Float
                    left = points[0]!!.x
                    top = points[0]!!.y
                    right = points[0]!!.x
                    bottom = points[0]!!.y
                    for (i in 1 until points.size) {
                        left = Math.min(left, points[i]!!.x)
                        top = Math.min(top, points[i]!!.y)
                        right = Math.max(right, points[i]!!.x) // +50);
                        bottom = Math.max(bottom, points[i]!!.y) // +50);
                    }

                    mCropRect.left = left//(left + colorballs[0].widthOfBall / 2).toFloat()
                    mCropRect.top = top//(top + colorballs[1].widthOfBall / 2).toFloat()
                    mCropRect.right = right//(right + colorballs[2].widthOfBall / 2).toFloat()
                    mCropRect.bottom = bottom//(bottom + colorballs[3].widthOfBall / 2).toFloat()

                    cropBoxData(mCropRect)
                } else {
                    //resize rectangle
                    //balID = -1
                    groupId = -1

                    //paint!!.color = Color.Yellow
                    mPrevious = currentPosition
                    groupId = getCurrentTouchIndex(
                        currentPosition.x,
                        currentPosition.y,
                        mCropRect,
                        mTPT,
                        enableDrag
                    )
                    //invalidate()
                }
            }
            ACTION_MOVE -> {

                if (currentPosition != Offset.Unspecified) {
                    val X = currentPosition.x
                    val Y = currentPosition.y
                    //path.lineTo(currentPosition.x, currentPosition.y)
                    //paint!!.color = Color.Yellow
                    //updateCropViewRect(event.x,event.y)
                    mTempRect.set(mCropRect)
                    when (groupId) {
                        0 -> mTempRect.set(X, Y, mCropRect.right, mCropRect.bottom)
                        1 -> mTempRect.set(mCropRect.left, Y, X, mCropRect.bottom)
                        2 -> mTempRect.set(mCropRect.left, mCropRect.top, X, Y)
                        3 -> mTempRect.set(X, mCropRect.top, mCropRect.right, Y)
                        4 -> mTempRect.set(mCropRect.left, Y, mCropRect.right, mCropRect.bottom)
                        5 -> mTempRect.set(mCropRect.left, mCropRect.top, X, mCropRect.bottom)
                        6 -> mTempRect.set(mCropRect.left, mCropRect.top, mCropRect.right, Y)
                        7 -> mTempRect.set(X, mCropRect.top, mCropRect.right, mCropRect.bottom)
                        8 -> {
                            mTempRect.offset(X - mPrevious.x, Y - mPrevious.y)
                            if (mTempRect.left > mCropRect.left && mTempRect.top > mCropRect.top && mTempRect.right < mCropRect.right && mTempRect.bottom < mCropRect.bottom) {
                                mCropRect.set(mTempRect)
                                //updateGridPoints()
                                //invalidate()
                            }
                            //return

                        }
                    }
                    val mCropRectMinSize = convertDpToPixel(100F, context)
                    val changeHeight: Boolean = mTempRect.height() >= mCropRectMinSize
                    val changeWidth: Boolean = mTempRect.width() >= mCropRectMinSize
                    mCropRect.set(
                        if (changeWidth) mTempRect.left else mCropRect.left,
                        if (changeHeight) mTempRect.top else mCropRect.top,
                        if (changeWidth) mTempRect.right else mCropRect.right,
                        if (changeHeight) mTempRect.bottom else mCropRect.bottom
                    )
                    if (changeHeight || changeWidth) {
                        //updateGridPoints()
                        //invalidate()
                    }
                    //invalidate()
                    mPrevious = currentPosition

                    cropBoxData(mCropRect)
                }
            }

            ACTION_UP -> {
                //path.lineTo(currentPosition.x, currentPosition.y)
                mPrevious = Offset.Unspecified
            }

            else -> Unit
        }

        /*
        drawPath(
            color = Color.Red,
            path = path,
            style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        )

         */

        if (points[3] == null) {
            val X = size.width / 2
            val Y = size.height / 2
            //point4 null when view first create
            //initRectangle(width / 2, height / 2)
            points[0] = PointF()
            points[0]!!.x = Y
            points[0]!!.y = Y
            points[1] = PointF()
            points[1]!!.x = X
            points[1]!!.y = Y + 200
            points[2] = PointF()
            points[2]!!.x = X // + 200;
            points[2]!!.y = Y // + 200;
            points[3] = PointF()
            points[3]!!.x = X + 200
            points[3]!!.y = Y
            //balID = 2
            groupId = 1

            var left: Float
            var top: Float
            var right: Float
            var bottom: Float
            left = points[0]!!.x
            top = points[0]!!.y
            right = points[0]!!.x
            bottom = points[0]!!.y
            for (i in 1 until points.size) {
                left = Math.min(left, points[i]!!.x)
                top = Math.min(top, points[i]!!.y)
                right = Math.max(right, points[i]!!.x) // +50);
                bottom = Math.max(bottom, points[i]!!.y) // +50);
            }

            mCropRect.left = left//(left + colorballs[0].widthOfBall / 2).toFloat()
            mCropRect.top = top//(top + colorballs[1].widthOfBall / 2).toFloat()
            mCropRect.right = right//(right + colorballs[2].widthOfBall / 2).toFloat()
            mCropRect.bottom = bottom//(bottom + colorballs[3].widthOfBall / 2).toFloat()
        }

        val rectSize = Size(mCropRect.width(), mCropRect.height())
        //draw stroke
        drawRect(
            color = strokeColor,
            topLeft = Offset(x = mCropRect.left, y = mCropRect.top),
            size = rectSize,
            style = Stroke(
                join = StrokeJoin.Round,
                width = strokeSize,
            )

        )

        //fill the rectangle
        drawRect(
            color = fillColor,
            topLeft = Offset(x = mCropRect.left, y = mCropRect.top),
            size = rectSize,

            )

        // draw the balls on the canvas
        if (cornerBitmap != null) {
            val mCropGridCorners = getCornersFromRect(mCropRect)
            var i = 0
            while (i < 8) {
                drawImage(
                    image = cornerBitmap,
                    topLeft = Offset(
                        mCropGridCorners[i] - (cornerBitmap.width / 2),
                        mCropGridCorners[i + 1] - (cornerBitmap.width / 2)
                    )
                )
                i += 2
            }
        }

        // draw grid Lines
        if (showGrid) {
            //val startOffset = Offset.Unspecified
            //val endOffset = Offset.Unspecified
            //var index = 0
            for (i in 0 until gridLineCount) {
                val xStart = mCropRect.left
                val yStart =
                    mCropRect.height() * ((i.toFloat() + 1.0f) / (gridLineCount + 1).toFloat()) + mCropRect.top
                val xEnd = mCropRect.right
                val yEnd =
                    mCropRect.height() * ((i.toFloat() + 1.0f) / (gridLineCount + 1).toFloat()) + mCropRect.top

                drawLine(
                    color = gridColor,
                    start = Offset(xStart, yStart),
                    end = Offset(xEnd, yEnd),
                    strokeWidth = gridLineSize
                )
            }
            for (i in 0 until gridLineCount) {
                val xStart =
                    mCropRect.width() * ((i.toFloat() + 1.0f) / (gridLineCount + 1).toFloat()) + mCropRect.left
                val yStart = mCropRect.top.toFloat()
                val xEnd =
                    mCropRect.width() * ((i.toFloat() + 1.0f) / (gridLineCount + 1).toFloat()) + mCropRect.left
                val yEnd = mCropRect.bottom.toFloat()

                drawLine(
                    color = gridColor,
                    start = Offset(xStart, yStart),
                    end = Offset(xEnd, yEnd),
                    strokeWidth = gridLineSize
                )
            }
        }

    }

}

fun getCornersFromRect(r: RectF): FloatArray {
    return floatArrayOf(
        r.left, r.top,
        r.right, r.top,
        r.right, r.bottom,
        r.left, r.bottom
    )
}

fun getCurrentTouchIndex(
    touchX: Float, touchY: Float,
    mCropRect: RectF,
    mTouchPointThreshold: Float,
    mEnableDrag: Boolean
): Int {

    //val mTouchPointThreshold =
    //  resources.getDimensionPixelSize(R.dimen.ucrop_default_crop_rect_corner_touch_threshold)
    val mCropGridCorners: FloatArray = getCornersFromRect(mCropRect)
    var closestPointIndex = -1
    var closestPointDistance: Double = mTouchPointThreshold.toDouble()
    var i = 0

    while (i < 8) {

        val distanceToCorner = sqrt(
            (touchX - mCropGridCorners[i]).toDouble().pow(2.0)
                    + (touchY - mCropGridCorners[i + 1]).toDouble().pow(2.0)
        )
        if (distanceToCorner < closestPointDistance) {
            closestPointDistance = distanceToCorner
            closestPointIndex = i / 2
        }
        i += 2
    }

    /**
     * you can use mCropRect.contains(touchX, touchY)
     * for drag the hole rectangle
     */

    //if (closestPointIndex < 0  && mCropRect.contains(touchX, touchY)) {
    //  return 8
    //}

    return if (closestPointIndex >= 0) {
        closestPointIndex
    } else if (mEnableDrag &&
        touchY > (mCropRect.top + closestPointDistance) &&
        touchY < (mCropRect.bottom - closestPointDistance) &&
        touchX > (mCropRect.left + closestPointDistance) &&
        touchX < (mCropRect.right - closestPointDistance)
    ) {
        8
    } else if (abs(touchY - mCropRect.top) < closestPointDistance &&
        touchX > (mCropRect.left + closestPointDistance) &&
        touchX < (mCropRect.right - closestPointDistance)
    ) {
        4
    } else if (abs(touchX - mCropRect.right) < closestPointDistance &&
        touchY > (mCropRect.top + closestPointDistance) &&
        touchY < (mCropRect.bottom - closestPointDistance)
    ) {
        5
    } else if (abs(touchY - mCropRect.bottom) < closestPointDistance &&
        touchX > (mCropRect.left + closestPointDistance) &&
        touchX < (mCropRect.right - closestPointDistance)
    ) {
        6
    } else if (abs(touchX - mCropRect.left) < closestPointDistance &&
        touchY > (mCropRect.top + closestPointDistance) &&
        touchY < (mCropRect.bottom - closestPointDistance)
    ) {
        7
    } else {
        -1
    }

}

@Preview
@Composable
fun Preview() {
    val activity = LocalContext.current


    val yourBitmap = ImageBitmap.imageResource(
        LocalContext.current.resources,
        R.drawable.cat
    )


    ScaleOverView(context = activity,
        enableDrag = true,
        showGrid = true,
        strokeColor = Color.White,
        fillColor = Color(0x55A3B8F8),
        gridLineCount = 4,
        gridColor = Color.White,
        strokeSize = 2F,
        cornerBitmap = yourBitmap,
        gridLineSize = 1F,
        cropBoxData = { cropRect ->
            Timber.d(
                "CropRectData: ", "left: ${cropRect.left} \n " +
                        "right: ${cropRect.right} \n" +
                        "top: ${cropRect.top} \n " +
                        "bottom: ${cropRect.bottom} "
            )
        }
    )
}