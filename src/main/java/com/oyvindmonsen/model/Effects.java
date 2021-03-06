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


        for (Rect face : faces) {
            String shrekFaceFileName = "shrek_face" + (int)((((Math.random() * 10) % 4)+1)) + ".png";
            Mat shrekFace = Imgcodecs.imread(getClass().getResource(shrekFaceFileName).getPath(), Imgcodecs.IMREAD_UNCHANGED);
            Mat shrekFaceOverlay = shrekFace.clone();
            Imgproc.resize(shrekFaceOverlay, shrekFaceOverlay, new Size(face.width, face.height));
            Vector<Mat> rgba = new Vector<>();
            Core.split(shrekFaceOverlay, rgba);
            Mat mask = rgba.get(3);
            rgba.remove(rgba.size()-1);
            Core.merge(rgba, shrekFaceOverlay);


            Mat roi = shrekImage.submat(face);
            shrekFaceOverlay.copyTo(roi, mask);
        }

        return shrekImage;
    }

    public Mat blur(Mat image, double radius) {
        Mat bluredImage = image.clone();
        Imgproc.GaussianBlur(image, bluredImage, new Size(0, 0), radius, 0);

        return bluredImage;
    }
}
