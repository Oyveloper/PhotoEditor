package com.oyvindmonsen;

import org.opencv.core.Mat;


public class PhotoEditor {

    // Image
    private Mat image;
    private Mat currentEdit;

    // Effects and adjustments
    private int brightness = 0;
    private double contrast = 1;

    private int red;
    private int green;
    private int blue;

    private boolean isColorOn = true;

    public PhotoEditor(Mat image) {
        this.setImage(image);
    }

    private void updateContrastAndBrightness() {
        this.setCurrentEdit(ImageAdjustments.setBrightnessAndContrast(this.image, this.brightness, this.contrast));
    }

    public void setBrightness(int value) {
        this.brightness = value;
        updateContrastAndBrightness();
    }

    public void setContrast(double value) {
        this.contrast = value;
        updateContrastAndBrightness();
    }


    // Getters and setters
    public Mat getImage() {
        return image;
    }

    public void setImage(Mat image) {
        this.image = image;
        setCurrentEdit(image);
    }

    public Mat getCurrentEdit() {
        if (this.isColorOn) {
            return currentEdit;
        } else {
            return ImageAdjustments.grayscale(this.currentEdit);
        }

    }

    public void setCurrentEdit(Mat currentEdit) {
        this.currentEdit = currentEdit;
    }

    public boolean isColorOn() {
        return isColorOn;
    }

    public void setColor(boolean colorOn) {
        isColorOn = colorOn;
    }
}
