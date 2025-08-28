package com.image.imageprocessing.processor;

import java.awt.image.BufferedImage;
import java.awt.image.ImageFilter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.image.imageprocessing.image.DrawMultipleImageOnCanvas;

public class ImageProcessor {

    // the work of executorService is to process all small small images
    private ExecutorService executorService;
    private DrawMultipleImageOnCanvas drawFn;

    ImageProcessor() {
        executorService = Executors.newFixedThreadPool(100);
        drawFn = DrawMultipleImageOnCanvas.getInstance();
    }

    public void processImage(BufferedImage image, int num, ImageFilter imageFilter) {

    }
}
