package com.oyvindmonsen.model;

import org.opencv.core.Mat;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Stack;


public class PhotoEditor {

    // Image
    private Mat image;
    private Mat adjustedImage;

    // Effects and adjustments
    private int brightness = 0;
    private double contrast = 1;



    private boolean isColorOn = true;

    // State
    private Stack<ImageState> history;

    // Observer/observable
    private PropertyChangeSupport support;


    private Effects effects;



    public PhotoEditor(PropertyChangeListener listener) {
        this.effects = new Effects();

        this.support = new PropertyChangeSupport(this);
        this.addPropertyChangeListener(listener);

        this.history = new Stack<>();
    }


    private void recordChange(String changeDesc) {
        ImageState newState = new ImageState(this.image, this.brightness, this.contrast, changeDesc);

        this.history.push(newState);

        this.support.firePropertyChange("New history", null, this.history);
    }

    private void updateContrastAndBrightness() {
        this.setAdjustedImage(ImageAdjustments.setBrightnessAndContrast(this.image, this.brightness, this.contrast));

    }

    public void setBrightness(int value) {
        this.brightness = value;
        updateContrastAndBrightness();
        this.recordChange("Set brightness");
    }

    public void setContrast(double value) {
        this.contrast = value;
        updateContrastAndBrightness();
        this.recordChange("Set contrast");
    }

    public void shrekify() {
        this.image = effects.shrekify(image);
        this.updateContrastAndBrightness();
        this.recordChange("Shrekified");
    }


    // Getters and setters
    public Mat getImage() {
        return image;
    }

    public void setImage(Mat image) {
        this.image = image;
        setAdjustedImage(image);
        this.recordChange("Original");
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


    public void setColor(boolean colorOn) {
        isColorOn = colorOn;
        this.recordChange("Color toggled");
    }


    //Update listeners
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

}
