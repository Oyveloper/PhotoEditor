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
    private Stack<ImageState> undoStack;

    // Observer/observable
    private PropertyChangeSupport support;


    private Effects effects;



    public PhotoEditor(PropertyChangeListener listener) {
        this.effects = new Effects();

        this.support = new PropertyChangeSupport(this);
        this.addPropertyChangeListener(listener);

        this.history = new Stack<>();
        this.undoStack = new Stack();
    }


    private void recordChange(String changeDesc) {
        ImageState newState = new ImageState(this.image, this.adjustedImage, this.brightness, this.contrast, this.isColorOn, changeDesc);

        this.history.push(newState);
        this.undoStack = (Stack<ImageState>) this.history.clone();

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

    public int getBrightness() {
        return brightness;
    }

    public double getContrast() {
        return contrast;
    }

    public boolean isColorOn() {
        return isColorOn;
    }

    public boolean canUndo() {
        return this.history.size() > 1;
    }

    public boolean canRedo() {
        return this.history.size() < this.undoStack.size();
    }



    public void setAdjustedImage(Mat adjustedImage) {
        this.adjustedImage = adjustedImage;
    }

    private void updateFromImageState(ImageState state) {
        this.image = state.getImage();
        this.adjustedImage = state.getAdjustedImage();
        this.contrast = state.getContrast();
        this.brightness = state.getBrightness();
        this.isColorOn = state.isColorOn();
    }


    public void setColor(boolean colorOn) {
        isColorOn = colorOn;
        this.recordChange("Color toggled");
    }

    public void undo() {
        if (!this.canUndo()) {
            return;
        }
        this.history.pop();
        ImageState currentState = this.history.peek();
        this.updateFromImageState(currentState);

        this.support.firePropertyChange("Undo", null, this.history);
    }

    public void redo() {
        if (!this.canRedo()) {
            return;
        }

        this.history.push(this.undoStack.get(this.history.size()));
        ImageState currentState = this.history.peek();
        this.updateFromImageState(currentState);

        this.support.firePropertyChange("Redo", null, this.history);

    }


    //Update listeners
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

}
