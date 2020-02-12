package com.oyvindmonsen.model;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.List;
import java.util.Vector;

public class Effects {


    public static final FaceDetector faceDetector = new FaceDetector();

    public Mat shrekify(Mat image) {
        Mat shrekImage = Mat.zeros(image.size(), image.type());
        image.copyTo(shrekImage);
        List<Rect> faces = faceDetector.findFaces(image);
        Mat shrekFace = Imgcodecs.imread(getClass().getResource("shrek_face.png").getPath(), Imgcodecs.IMREAD_UNCHANGED);

        for (Rect face : faces) {
            Mat shrekFaceOverlay = shrekFace.clone();
            Imgproc.resize(shrekFaceOverlay, shrekFaceOverlay, new Size(face.width, face.height));
            Vector<Mat> rgba = new Vector<Mat>();
            Core.split(shrekFaceOverlay, rgba);
            Mat mask = rgba.get(3);
            rgba.remove(rgba.size()-1);
            Core.merge(rgba, shrekFaceOverlay);


            Mat roi = shrekImage.submat(face);
            shrekFaceOverlay.copyTo(roi, mask);
        }

        return shrekImage;
    }
}
