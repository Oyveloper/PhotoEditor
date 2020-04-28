package com.oyvindmonsen;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        nu.pattern.OpenCV.loadLocally();


        Scene scene = new Scene(loadFXML());
        stage.setScene(scene);
        stage.setTitle("Photo Editor");
        stage.show();
    }

    private static Parent loadFXML() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("editor" + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {

        launch();
    }

}