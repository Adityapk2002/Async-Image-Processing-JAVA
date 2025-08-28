package com.image.imageprocessing;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.awt.image.BufferedImage;

import com.image.imageprocessing.filter.GreyScaleFilter;
import com.image.imageprocessing.filter.ImageFilter;
import com.image.imageprocessing.image.DrawMultipleImageOnCanvas;
import com.image.imageprocessing.io.FileImageIO;
import com.image.imageprocessing.io.ImageReadInf;
import com.image.imageprocessing.processor.ImageProcessor;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ImageReadInf imageIO = new FileImageIO();
        BufferedImage image = imageIO.readImage(
                "/Users/adityakhandade/Desktop/Projects/Async  Image Processing System/imageProcessing/src/main/java/com/image/imageprocessing/io/project.jpg");
        DrawMultipleImageOnCanvas drawMultipleImageOnCanvas = DrawMultipleImageOnCanvas.getInstance();
        drawMultipleImageOnCanvas.initialize(stage);
        ImageProcessor processor = new ImageProcessor();
        ImageFilter imageFilter = new GreyScaleFilter();
        processor.processImage(image, 10, imageFilter);
    }

    public static void main(String[] args) {
        launch(args);
    }
}