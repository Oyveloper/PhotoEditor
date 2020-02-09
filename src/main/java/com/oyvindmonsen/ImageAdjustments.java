package com.oyvindmonsen;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

import java.util.Vector;

public class ImageAdjustments {


    public static Mat setBrightness(Mat image, int value) {
        Mat newImage = Mat.zeros(image.size(), image.type());

        Core.add(image, new Scalar(value, value, value), newImage);
        return newImage;
    }

    public static Mat setContrast(Mat image, double value) {
        Mat newImage = Mat.zeros(image.size(), image.type());

        Core.multiply(image, new Scalar(value, value, value), newImage);

        return newImage;
    }
}
