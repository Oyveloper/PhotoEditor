package com.oyvindmonsen.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Stack;

public interface PhotoEditorStateSaver {

    /*
    Retrieves a saved PhotoEditor from a file location
     */
    Stack<ImageState> getStateFromFile(String absoluteFilePath) throws FileNotFoundException;

    /*
    Saves a PhotoEditor to the given file location
     */
    void writeToFile(PhotoEditor editor, String absoluteFilePath) throws IOException;


}

