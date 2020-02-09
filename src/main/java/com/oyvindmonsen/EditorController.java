package com.oyvindmonsen;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javafx.css.Match;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;


public class EditorController {

    @FXML
    Slider opacitySlider;
    @FXML
    Slider brightnessSlider;
    @FXML
    Slider contrastSlider;


    @FXML
    Label opacityValueLabel;
    @FXML
    Label brightnessValueLabel;
    @FXML
    Label contrastValueLabel;

    @FXML
    ImageView imageView;

    private Mat image;
    private Mat adjustedImage;


    @FXML
    public void initialize() {

        try {
            image = Imgcodecs.imread(getClass().getResource("shrek.jpg").getPath());
            adjustedImage = image;
        } catch (Exception e) {
            System.out.println(e);
        }
        showCurrentImage();
    }



    @FXML
    void onOpacityValueChange() {
        double opacity = opacitySlider.getValue();
        opacityValueLabel.setText(String.format("%.1f", opacity) + "%");

    }

    @FXML
    void onBrightnessValueChange() {
        int brightness = (int) brightnessSlider.getValue();
        brightnessValueLabel.setText(String.valueOf(brightness));
    }

    @FXML
    void onContrastValueChange() {
        double contrast = contrastSlider.getValue();
        contrastValueLabel.setText(String.format("%.2f", contrast));

    }

    @FXML
    void onBrightnessSelected() {
        int brightness = (int) brightnessSlider.getValue();

        this.adjustedImage = ImageAdjustments.setBrightness(image, brightness);
        this.showCurrentImage();
    }

    @FXML
    void onContrastSelected() {
        double contrast = contrastSlider.getValue();

        this.adjustedImage = ImageAdjustments.setContrast(image, contrast);
        this.showCurrentImage();
    }



    private void showCurrentImage() {
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".png", adjustedImage, buffer);


        Image displayImage = new Image(new ByteArrayInputStream(buffer.toArray()));
        imageView.setImage(displayImage);
    }


}
