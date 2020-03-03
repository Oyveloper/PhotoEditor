package com.oyvindmonsen.model;


import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Base64;
import java.util.Stack;

public class GsonPhotoEditorStateSaver implements PhotoEditorStateSaver {

    private Gson gson;
    private Type historyType = new TypeToken<Stack<ImageState>>() {}.getType();

    private class imageSerializer implements JsonSerializer<Mat> {

        @Override
        public JsonElement serialize(Mat src, Type typeOfSrc, JsonSerializationContext context) {
            MatOfByte buff = new MatOfByte();
            Imgcodecs.imencode(".png", src, buff);

            byte[] bytes = buff.toArray();

            return new JsonPrimitive(Base64.getEncoder().encodeToString(bytes));

        }
    }

    private class imageDeserializer implements JsonDeserializer<Mat> {

        @Override
        public Mat deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            byte[] bytes = Base64.getDecoder().decode(json.getAsString());
            return Imgcodecs.imdecode(new MatOfByte(bytes), Imgcodecs.CV_LOAD_IMAGE_UNCHANGED);
        }
    }


    public GsonPhotoEditorStateSaver() {
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(Mat.class, new imageSerializer());
        gsonBuilder.registerTypeAdapter(Mat.class, new imageDeserializer());

        this.gson = gsonBuilder.create();
    }

    @Override
    public Stack<ImageState> getStateFromFile(String absoluteFilePath) throws FileNotFoundException {

        FileReader reader = new FileReader(absoluteFilePath);

        return this.gson.fromJson(reader, historyType);

    }

    @Override
    public void writeToFile(PhotoEditor editor, String absoluteFilePath) throws IOException {

        String json = this.gson.toJson(editor.getHistory(), historyType);
        FileWriter writer = new FileWriter(absoluteFilePath);
        writer.write(json);
        writer.flush();
        writer.close();


    }
}


