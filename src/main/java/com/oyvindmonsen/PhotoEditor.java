package com.oyvindmonsen;

import org.opencv.core.Mat;

import java.util.Stack;


public class PhotoEditor {

    // Image
    private Mat image;
    private Mat adjustedImage;

    // Effects and adjustments
    private int brightness = 0;
    private double contrast = 1;

    private int red;
    private int green;
    private int blue;

    private boolean isColorOn = true;

    // State
    private Stack<ImageState> history;

    private Effects effects;

    public PhotoEditor(Mat image) {
        this.setImage(image);

        this.effects = new Effects();

        this.history = new Stack<ImageState>();
        this.history.push(new ImageState(this.image, this.brightness, this.contrast, "Original"));

    }

    private void recordChange(String changeDesc) {
        this.history.push(new ImageState(this.image, this.brightness, this.contrast, changeDesc));
    }

    private void updateContrastAndBrightness() {
        this.setAdjustedImage(ImageAdjustments.setBrightnessAndContrast(this.image, this.brightness, this.contrast));
    }

    public void setBrightness(int value) {
        this.brightness = value;
        updateContrastAndBrightness();
    }

    public void setContrast(double value) {
        this.contrast = value;
        updateContrastAndBrightness();
    }

    public void shrekify() {
        this.image = effects.shrekify(image);
        this.updateContrastAndBrightness();
    }


    // Getters and setters
    public Mat getImage() {
        return image;
    }

    public void setImage(Mat image) {
        this.image = image;
        setAdjustedImage(image);
    }

    public Mat getAdjustedImage() {
        if (this.isColorOn) {
            return adjustedImage;
        } else {
            return ImageAdjustments.grayscale(this.adjustedImage);
        }

    }

    public void setAdjustedImage(Mat adjustedImage) {
        this.adjustedImage = adjustedImage;
    }

    public boolean isColorOn() {
        return isColorOn;
    }

    public void setColor(boolean colorOn) {
        isColorOn = colorOn;
    }
}
