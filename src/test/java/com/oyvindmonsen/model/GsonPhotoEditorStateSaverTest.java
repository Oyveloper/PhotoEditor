package com.oyvindmonsen.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class GsonPhotoEditorStateSaverTest {
    private PhotoEditorStateSaver stateSaver;
    private PhotoEditor editor;

    @BeforeAll
    public void setUp() {
        this.stateSaver = new GsonPhotoEditorStateSaver();
    }

    @BeforeEach
    public void beforeEach() {
        this.editor = new PhotoEditor();
    }

    @Test
    @DisplayName("Saving empty")
    public void testSaveEmpty() {
        try {
            this.stateSaver.writeToFile(this.editor, "./file");
        } catch (IOException e) {
            // Thought that the exception didn't need to be "try catch"-ed since
            // an exception would cause it to fail anyway
            // Well, well...
            fail();
        }
    }

    @Test
    @DisplayName("Empty file name")
    public void testEmptyFileName() {
        assertThrows(IOException.class, () -> {
            this.stateSaver.writeToFile(this.editor, "");
        });
    }

    @Test
    @DisplayName("Load image")
    public void testLoadImage() {

    }

    @Test
    @DisplayName("")
    public void testEditorWithImage() {

    }

}