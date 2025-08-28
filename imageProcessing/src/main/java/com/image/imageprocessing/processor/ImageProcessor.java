package com.image.imageprocessing.processor;

import java.awt.image.BufferedImage;
import com.image.imageprocessing.filter.ImageFilter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.image.imageprocessing.image.ImageData;

public class ImageProcessor {

    // the work of executorService is to process all small small images
    private final ExecutorService executorService;

    public ImageProcessor() {
        executorService = Executors.newFixedThreadPool(100);
    }

    public void processImage(BufferedImage image, int num, ImageFilter imageFilter, DrawMultipleImageOnCanvas drawFn) {
        int numHorizontalImages = image.getWidth() / num;
        int numVerticalImages = image.getHeight() / num;

        for (int i = 0; i < numHorizontalImages; i++) {
            for (int j = 0; j < numVerticalImages; j++) {
                final int x = i * num;
                final int y = j * num;
                final BufferedImage subImage = image.getSubimage(x, y, num, num);

                // Submit a task (as a Runnable) to the thread pool.
                // The task will process the sub-image and add it to the drawing queue.
                executorService.submit(() -> {
                    BufferedImage result = imageFilter.filter(subImage);
                    ImageData imageData = new ImageData(result, x, y, num, num);
                    drawFn.addImageToQueue(imageData);
                });
            }
        }
        // The second loop that added images to the queue has been removed.
        // It was redundant because the task now adds the image to the queue itself.
        // It also blocked the main thread with future.get() unnecessarily.
    }
}
