package com.oyvindmonsen.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class BlurSelectController {

    @FXML
    private Slider radiusSlider;
    @FXML
    private Label radiusValueLabel;

    private PropertyChangeListener listener;

    public void setListener(PropertyChangeListener listener) {
        this.listener = listener;
    }


    @FXML
    public void selectBlurRadius() {
        double radius =  this.radiusSlider.getValue();

        this.listener.propertyChange(new PropertyChangeEvent(this, "Blur selected", null, radius));

        Stage stage = (Stage) this.radiusSlider.getScene().getWindow();

        stage.close();
    }

    @FXML
    public void blurRadiusUpdated() {
        double radius = this.radiusSlider.getValue();
        this.radiusValueLabel.setText(String.format("%.1f", radius) + "px");
    }

}
