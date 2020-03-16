package com.oyvindmonsen.controller;

import com.oyvindmonsen.model.GsonPhotoEditorStateSaver;
import com.oyvindmonsen.model.ImageState;
import com.oyvindmonsen.model.PhotoEditor;
import com.oyvindmonsen.model.PhotoEditorStateSaver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    private AnchorPane imgContainer;

    @FXML
    private ListView<ImageState> historyListView;


    @FXML
    private MenuItem undoBtn;

    @FXML
    private MenuItem redoBtn;

    @FXML
    private MenuItem saveBtn;

    @FXML
    private MenuItem openBtn;

    @FXML
    private MenuItem exportBtn;

    @FXML
    private MenuItem blurBtn;

    @FXML
    private MenuItem shrekifyBtn;

    private PhotoEditor editor;

    private PhotoEditorStateSaver saver = new GsonPhotoEditorStateSaver();


    @FXML
    public void initialize() {

        this.editor = new PhotoEditor(this);
        openFile();

        //Scale image
        imageView.fitWidthProperty().bind(imgContainer.widthProperty());
        setupShortcuts();

        this.historyListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<ImageState> call(ListView listView) {

                return new ListCell<>() {
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
            }
        });
    }

    private void setupShortcuts() {
        // Shortcuts
        undoBtn.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.SHORTCUT_DOWN));
        redoBtn.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN));
        saveBtn.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN));
        openBtn.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.SHORTCUT_DOWN));
        exportBtn.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.SHORTCUT_DOWN));
        blurBtn.setAccelerator(new KeyCodeCombination(KeyCode.B, KeyCombination.SHORTCUT_DOWN));
        shrekifyBtn.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.SHORTCUT_DOWN));
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

    @FXML
    void blurPressed() {
        showBlurSelect();
    }

    @FXML
    void savePressed() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Open Resource File");

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PhotoEditor files", "*.pedit"));
        File file = fileChooser.showSaveDialog(new Stage());
        if (file == null) {
            // user has abandoned
            return;
        }
        String path = file.getAbsolutePath();

        try {
            this.saver.writeToFile(this.editor, path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openPressed() {

        openFile();

    }

    @FXML
    void exportPNG() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose where to save your file");

        System.out.println("About to export some shit");

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png"));
        File file = fileChooser.showSaveDialog(new Stage());
        if (file == null) {
            // USer has abanoded
            return;
        }
        String path = file.getAbsolutePath();

        Imgcodecs.imwrite(path, this.editor.getAdjustedImage());
    }

    private void openFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file to open");

        FileChooser.ExtensionFilter peditFilter = new FileChooser.ExtensionFilter("PhotoEditor files", "*.pedit");
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Images", "*.png", "*.jpeg", "*.JPEG", "*.JPG", "*.jpg", "*.pedit");
        fileChooser.getExtensionFilters().add(peditFilter);
        fileChooser.getExtensionFilters().add(imageFilter);
        File file = fileChooser.showOpenDialog(new Stage());

        if (file == null) {
            // User has abandoned;
            return;
        }

        if (fileChooser.getSelectedExtensionFilter() == imageFilter) {
            try {
                Mat image = Imgcodecs.imread(file.getAbsolutePath());
                this.editor = new PhotoEditor(this);
                editor.setImage(image);
            } catch (Exception e) {
                this.showErrorMessage(e.getMessage());
            }
        } else {
            try {
                Stack<ImageState> history = this.saver.getStateFromFile(file.getAbsolutePath());
                this.editor.setHistory(history);
                this.showCurrentImage();
                this.updateControls();
                this.updateHistoryList(this.editor.getHistory());
            } catch (FileNotFoundException e) {
                this.showErrorMessage(e.getMessage());
            }
        }
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error: Something went wrong");
        alert.setContentText(message);
        alert.setTitle("Error");

        alert.showAndWait();
    }

    private void updateControls() {
        this.brightnessSlider.setValue(this.editor.getBrightness());
        this.brightnessValueLabel.setText(String.valueOf(this.editor.getBrightness()));
        this.contrastSlider.setValue(this.editor.getContrast());
        this.contrastValueLabel.setText(String.valueOf(String.format("%.2f", this.editor.getContrast())));
        this.colorCheckBox.setSelected(this.editor.isColorOn());
    }

    private void showCurrentImage() {
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".png", editor.getAdjustedImage(), buffer);


        Image displayImage = new Image(new ByteArrayInputStream(buffer.toArray()));
        imageView.setImage(displayImage);

        updateControls();
    }

    private void updateHistoryList(Stack<ImageState> history) {
        ObservableList<ImageState> stateObservableList = FXCollections.observableArrayList();
        stateObservableList.addAll(history);
        historyListView.setItems(stateObservableList);
        updateControls();
    }

    private void showBlurSelect() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("blurSelect.fxml"));
            root = loader.load();
            BlurSelectController blurSelectController = loader.getController();
            blurSelectController.setListener(this);
            Stage stage = new Stage();
            stage.setTitle("Blur");
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException ignored) {
        }
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        if (evt.getSource() instanceof BlurSelectController) {
            this.editor.applyBlur((double)evt.getNewValue());
        } else {
            this.showCurrentImage();
            updateHistoryList((Stack<ImageState>)evt.getNewValue());
        }


    }
}
