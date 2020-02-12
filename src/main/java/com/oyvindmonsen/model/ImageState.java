package com.oyvindmonsen.model;

import org.opencv.core.Mat;

public class ImageState {

    private Mat image;
    private int brightness;
    private double contrast;
    private String description;


    public ImageState(Mat image, int brightness, double contrast, String description) {
        this.image = image;
        this.brightness = brightness;
        this.contrast = contrast;
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
