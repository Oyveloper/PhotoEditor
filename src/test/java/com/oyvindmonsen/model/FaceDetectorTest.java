package com.oyvindmonsen.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FaceDetectorTest {
    private FaceDetector faceDetector = new FaceDetector();
    private String resources = "/src/test/resources/com.oyvindmonsen.model/";

    @BeforeAll
    public static void setup() {
        nu.pattern.OpenCV.loadLocally();
    }

    @Test
    public void testImageWithFaces() {
        String absolutePath =
                new File(
                        getClass()
                        .getClassLoader()
                        .getResource("com.oyvindmonsen.model/selfie.png")
                        .getFile())
                .getAbsolutePath();
        System.out.println(absolutePath);
        Mat image = Imgcodecs.imread(absolutePath);

        assertTrue(this.faceDetector.findFaces(image).size() > 0);
    }

    @Test
    public void testImageWithoutFaces() {
        // Using a completely green image to ensure no faces
        String absolutePath =
                new File(
                        getClass()
                                .getClassLoader()
                                .getResource("com.oyvindmonsen.model/green.jpg")
                                .getFile())
                        .getAbsolutePath();
        Mat image = Imgcodecs.imread(absolutePath);

        assertEquals(0, this.faceDetector.findFaces(image).size());
    }

}