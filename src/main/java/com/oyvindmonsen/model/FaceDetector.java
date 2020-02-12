package com.oyvindmonsen.model;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.util.List;

public class FaceDetector {

    private CascadeClassifier faceClassifier;

    public FaceDetector() {
        this.faceClassifier = new CascadeClassifier();
        this.faceClassifier.load(getClass().getResource("haarcascade_frontalface_default.xml").getPath());

    }

    public List<Rect> findFaces(Mat image) {
        Mat result = image;
        Mat gray = ImageAdjustments.grayscale(image);
        Imgproc.equalizeHist(gray, gray);

        // -- Detect faces
        MatOfRect faces = new MatOfRect();
        this.faceClassifier.detectMultiScale(gray, faces);

        List<Rect> listOfFaces = faces.toList();
        for (Rect face : listOfFaces) {
            Mat roi = result.submat(face);
        }


        return listOfFaces;
    }

}
