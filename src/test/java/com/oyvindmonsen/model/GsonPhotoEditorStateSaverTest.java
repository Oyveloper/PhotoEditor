package com.oyvindmonsen.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opencv.imgcodecs.Imgcodecs;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class GsonPhotoEditorStateSaverTest {
    private final PhotoEditorStateSaver stateSaver = new GsonPhotoEditorStateSaver();
    private PhotoEditor editor;
    public static String resources = "src/test/resources/com.oyvindmonsen.model/";

    @BeforeAll
    public static void setup() {
        nu.pattern.OpenCV.loadLocally();

        // Make sure the filename "newFile" is not an actual file
        try {
            Files.deleteIfExists(Paths.get(resources + "newFile"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void beforeEach() {
        this.editor = new PhotoEditor();
    }

    @Test
    @DisplayName("Saving empty")
    public void testSaveEmpty() {
        try {
            String path = resources + "testfile";
            this.stateSaver.writeToFile(this.editor, path);

        } catch (IOException e) {
            // Thought that the exception didn't need to be "try catch"-ed since
            // an exception would cause it to fail anyway
            // Well, well...
            fail();
        }
    }

    @Test
    @DisplayName("Non empty editor")
    public void testNonEmptyEditorSave() {
        String path = resources + "selfie.png";
        this.editor.setImage(Imgcodecs.imread(path));

        try {
            this.stateSaver.writeToFile(editor, resources + "non_empty.pedit");
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    @DisplayName("Loading known file")
    public void testLoadKnown() {
        String path = resources + "save.pedit";
        try {
            this.stateSaver.getStateFromFile(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Empty file name")
    public void testEmptyFileName() {
        assertThrows(IOException.class, () -> this.stateSaver.writeToFile(this.editor, ""));
    }

    @Test
    @DisplayName("Illegal dir name")
    public void testIllegalDirName() {
        String path = "src/test/resources/non_existing_dir/file";
        assertThrows(IOException.class, () -> this.stateSaver.writeToFile(this.editor, path));
    }

    @Test
    @DisplayName("New file")
    public void testNewFile() {
        try {
            this.stateSaver.writeToFile(this.editor, resources + "newFile");
        } catch (IOException e) {
            fail();
        }
    }


}