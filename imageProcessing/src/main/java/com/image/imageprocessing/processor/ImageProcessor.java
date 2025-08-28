package com.image.imageprocessing.processor;

import java.awt.image.BufferedImage;
import java.awt.image.ImageFilter;
import java.util.concurrent.Callable;
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
        int numHorizontalImages = image.getWidth() / num;
        int numVerticalImages = image.getWidth() / num;

        for (int i = 0; i < numVerticalImages; i++) {
            for (int j = 0; j < numHorizontalImages; j++) {
                BufferedImage subImage = image.getSubimage(i * num, j * num, num, num);
                executorService.submit(new Callable<BufferedImage>() {
                    @Override
                    public BufferedImage call() {
                        // return null because we use callable and in it we have to return something
                        // runnable means returns nothing
                        return imageFilter.filter(subImage);
                    }
                });
            }
        }

    }
}
