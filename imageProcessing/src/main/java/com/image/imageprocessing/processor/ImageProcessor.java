package com.image.imageprocessing.processor;

import java.awt.image.BufferedImage;
import com.image.imageprocessing.filter.ImageFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.image.imageprocessing.image.DrawMultipleImageOnCanvas;
import com.image.imageprocessing.image.ImageData;

public class ImageProcessor {

    // the work of executorService is to process all small small images
    private ExecutorService executorService;
    private DrawMultipleImageOnCanvas drawFn;

    public ImageProcessor() {
        executorService = Executors.newFixedThreadPool(100);
        drawFn = DrawMultipleImageOnCanvas.getInstance();
    }

    public void processImage(BufferedImage image, int num, ImageFilter imageFilter) {
        int numHorizontalImages = image.getWidth() / num;
        int numVerticalImages = image.getHeight() / num;

        List<Future<ImageData>> futures = new ArrayList<>();

        for (int i = 0; i < numHorizontalImages; i++) {
            for (int j = 0; j < numVerticalImages; j++) {
                final int x = j * num;
                final int y = i * num;
                BufferedImage subImage = image.getSubimage(i * num, j * num, num, num);
                Future<ImageData> future = executorService.submit(new Callable<ImageData>() {
                    @Override
                    public ImageData call() {
                        // return null because we use callable and in it we have to return something
                        // runnable means returns nothing
                        BufferedImage result = imageFilter.filter(subImage);
                        return new ImageData(result, x, y, num, num); // here we want i and j so that we can draw that
                                                                      // image on canvas from i and j so we will get at
                                                                      // which spot we have to put
                    }
                });
                futures.add(future);
            }
        }
        for (Future<ImageData> future : futures) {
            try {
                drawFn.addImageToQueue(future.get());
            } catch (Exception ex) {
                System.err.println("Not able to push the image into the queue");
            }
        }

    }
}
