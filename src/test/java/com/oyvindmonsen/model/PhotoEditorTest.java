package com.oyvindmonsen.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;


class PhotoEditorTest {
    private Stack<ImageState> history = new Stack<>();
    private final PropertyChangeListener listener = (PropertyChangeEvent evt) -> this.history = (Stack<ImageState>)evt.getNewValue();
    public static final String resources = "src/test/resources/com.oyvindmonsen.model";

    @BeforeAll
    public static void setUp() {
        nu.pattern.OpenCV.loadLocally();
    }

    @Test
    @DisplayName("Instantiate")
    public void testInstantiate() {
        PhotoEditor editor = new PhotoEditor();
        Mat image = Imgcodecs.imread(resources + "selfie.png");
        editor.setImage(image);

        assertSame(editor.getAdjustedImage(), image);
    }

    @Test
    @DisplayName("Setting brightness")
    public void testBrightness() {
        PhotoEditor editor = new PhotoEditor();
        Mat image = Imgcodecs.imread(resources + "selfie.png");
        editor.setImage(image);

        editor.setBrightness(10);
        assertEquals(editor.getBrightness(), 10);

        editor.setBrightness(0);
        assertEquals(editor.getBrightness(), 0);

        editor.setBrightness(-10);
        assertEquals(editor.getBrightness(), -10);
    }

    @Test
    @DisplayName("Setting contrast")
    public void testContrast() {
        PhotoEditor editor = new PhotoEditor();
        Mat image = Imgcodecs.imread(resources + "selfie.png");
        editor.setImage(image);

        editor.setContrast(10);
        assertEquals(editor.getContrast(), 10);

        editor.setContrast(0);
        assertEquals(editor.getContrast(), 0);

        editor.setContrast(-10);
        assertEquals(editor.getContrast(), -10);
    }

    @Test
    @DisplayName("Testing listener update")
    public void testListenerUpdate() {
        PhotoEditor editor = new PhotoEditor();
        Mat image = Imgcodecs.imread(resources + "selfie.png");
        editor.setImage(image);
        editor.addPropertyChangeListener(this.listener);

        editor.setContrast(10);
        assertEquals(this.history, editor.getHistory());

        editor.setBrightness(10);
        assertEquals(this.history, editor.getHistory());

        editor.applyBlur(2.0);
        assertEquals(this.history, editor.getHistory());
    }

}