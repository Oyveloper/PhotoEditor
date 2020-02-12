package com.oyvindmonsen.model;

import org.opencv.core.Mat;

public class ImageState {

    private Mat image;
    private Mat adjustedImage;
    private int brightness;
    private double contrast;
    private String description;
    private boolean isColorOn;


    public ImageState(Mat image, Mat adjustedImage, int brightness, double contrast, boolean isColorOn, String description) {
        this.image = image;
        this.adjustedImage = adjustedImage;
        this.brightness = brightness;
        this.contrast = contrast;
        this.isColorOn = isColorOn;
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public Mat getImage() {
        return image;
    }

    public Mat getAdjustedImage() {
        return adjustedImage;
    }

    public int getBrightness() {
        return brightness;
    }

    public double getContrast() {
        return contrast;
    }

    public boolean isColorOn() {
        return isColorOn;
    }
}
