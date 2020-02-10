package com.oyvindmonsen;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.Vector;

public class ImageAdjustments {


    private static Mat setBrightness(Mat image, int value) {
        Mat newImage = Mat.zeros(image.size(), image.type());

        Core.add(image, new Scalar(value, value, value), newImage);
        return newImage;
    }

    private static Mat setContrast(Mat image, double value) {
        Mat newImage = Mat.zeros(image.size(), image.type());

        Core.multiply(image, new Scalar(value, value, value), newImage);

        return newImage;
    }

    public static Mat setBrightnessAndContrast(Mat image, int brightness, double contrast) {
        Mat newImage = Mat.zeros(image.size(), image.type());

        newImage = setContrast(image, contrast);
        newImage = setBrightness(newImage, brightness);


        return newImage;
    }


    public static Mat grayscale(Mat image) {
        Mat newImage = Mat.zeros(image.size(), image.type());
        Imgproc.cvtColor(image, newImage, Imgproc.COLOR_RGB2GRAY);

        return newImage;
    }


}
