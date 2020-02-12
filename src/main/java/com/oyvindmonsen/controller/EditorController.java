package com.oyvindmonsen.controller;

import com.oyvindmonsen.model.ImageState;
import com.oyvindmonsen.model.PhotoEditor;
import com.oyvindmonsen.view.HistoryListCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.util.Callback;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayInputStream;
import java.util.Stack;


public class EditorController implements PropertyChangeListener {


    @FXML
    private Slider brightnessSlider;
    @FXML
    private Slider contrastSlider;
    @FXML
    private ToggleButton colorCheckBox;


    @FXML
    private Label brightnessValueLabel;
    @FXML
    private Label contrastValueLabel;

    @FXML
    private ImageView imageView;

    @FXML
    private ListView<ImageState> historyListView;


    @FXML
    private MenuItem undoBtn;

    @FXML
    MenuItem redoBtn;

    private ObservableList<ImageState> stateObservableList = FXCollections.observableArrayList();

    private PhotoEditor editor;


    @FXML
    public void initialize() {

        Mat image = new Mat();

        try {
            image = Imgcodecs.imread(getClass().getResource("selfie.jpeg").getPath());
        } catch (Exception e) {
            System.out.println("Kunne ikke åpne bildet...");
        }

        this.editor = new PhotoEditor(this);
        editor.setImage(image);

        // Shortcuts
        undoBtn.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.SHORTCUT_DOWN));
        redoBtn.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN));
        this.historyListView.setCellFactory(new Callback<ListView<ImageState>, ListCell<ImageState>>() {
            @Override
            public ListCell<ImageState> call(ListView listView) {
                ListCell<ImageState> cell = new ListCell<ImageState>() {
                    @Override
                    protected void updateItem(ImageState state, boolean empty) {
                        super.updateItem(state, empty);

                        if (state != null) {
                            setText(state.getDescription());
                        } else {
                            setText("");
                        }
                    }
                };

                return cell;
            }
        });
    }


    @FXML
    void onColorCheckBoxChange() {
        this.editor.setColor(colorCheckBox.isSelected());
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
    }

    @FXML
    void onContrastSelected() {
        double contrast = contrastSlider.getValue();

        this.editor.setContrast(contrast);
    }

    @FXML
    void onShrekSelected() {
        this.editor.shrekify();

    }

    @FXML
    void historyCellSelected() {
        int index = historyListView.getSelectionModel().getSelectedIndex();
        System.out.println(index);
        this.editor.undoToIndex(index);
    }

    @FXML
    void undoPressed() {
        this.editor.undo();
        this.updateControls();
    }

    @FXML
    void redoPressed() {
        this.editor.redo();
        this.updateControls();
    }

    private void updateControls() {
        this.brightnessSlider.setValue(this.editor.getBrightness());
        this.contrastSlider.setValue(this.editor.getContrast());
        this.colorCheckBox.setSelected(this.editor.isColorOn());
    }

    private void showCurrentImage() {
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".png", editor.getAdjustedImage(), buffer);


        Image displayImage = new Image(new ByteArrayInputStream(buffer.toArray()));
        imageView.setImage(displayImage);
    }

    private void updateHistoryList(Stack<ImageState> history) {
        stateObservableList = FXCollections.observableArrayList();
        stateObservableList.addAll(history);
        historyListView.setItems(stateObservableList);
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.showCurrentImage();
        updateHistoryList((Stack<ImageState>)evt.getNewValue());

    }
}
