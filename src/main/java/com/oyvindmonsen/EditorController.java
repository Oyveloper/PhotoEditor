package com.oyvindmonsen;

import java.io.ByteArrayInputStream;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;


public class EditorController {


    @FXML
    Slider brightnessSlider;
    @FXML
    Slider contrastSlider;
    @FXML
    CheckBox colorCheckBox;


    @FXML
    Label brightnessValueLabel;
    @FXML
    Label contrastValueLabel;

    @FXML
    ImageView imageView;



    private PhotoEditor editor;


    @FXML
    public void initialize() {

        Mat image = new Mat();

        try {
            image = Imgcodecs.imread(getClass().getResource("shrek.png").getPath());
        } catch (Exception e) {
            System.out.println(e);
        }

        this.editor = new PhotoEditor(image);
        showCurrentImage();
    }


    @FXML
    void onColorCheckBoxChange() {
        this.editor.setColor(colorCheckBox.isSelected());
        this.showCurrentImage();
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

        this.editor.setBrightness(brightness);
        this.showCurrentImage();
    }

    @FXML
    void onContrastSelected() {
        double contrast = contrastSlider.getValue();

        this.editor.setContrast(contrast);
        this.showCurrentImage();
    }

    @FXML
    void onShrekSelected() {
        this.editor.shrekify();
        showCurrentImage();
    }



    private void showCurrentImage() {
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".png", editor.getAdjustedImage(), buffer);


        Image displayImage = new Image(new ByteArrayInputStream(buffer.toArray()));
        imageView.setImage(displayImage);
    }


}
