package com.github.jameshnsears.cameraoverlay.model.edgedetection

import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

class Canny : EdgeDetectionUtils() {
    fun applyCanny(blurredImage: Mat): Mat {
        val cannyMat = Mat(blurredImage.size(), CvType.CV_8UC1)
        Imgproc.cvtColor(blurredImage, cannyMat, Imgproc.COLOR_RGB2GRAY, 4)
        Imgproc.Canny(cannyMat, cannyMat, 80.0, 100.0)
        return cannyMat
    }
}
