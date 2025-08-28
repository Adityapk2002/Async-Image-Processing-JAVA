package com.image.imageprocessing.image;

import javafx.scene.canvas.Canvas;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class DrawMultipleImageOnCanvas {

    private static DrawMultipleImageOnCanvas instance; // it is singleton
    private Queue<ImageData> queue = new LinkedBlockingQueue<>();
    private Canvas canvas;
    private GraphicsContext gc;
    private boolean isDrawing = false;
    private Stage primaryStage;

    public static DrawMultipleImageOnCanvas getInstance() {
        if (instance == null) {
            return new DrawMultipleImageOnCanvas();
        }
        return instance;
    }

    public void addImageToQueue(ImageData image) {
        queue.add(image);
    }

    public void initialize(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.canvas = new Canvas(1920, 1080);
        this.gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, 1920, 1080);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                // poll image from queue and draw on canvas and use stage to show the image
                if (!isDrawing && queue.isEmpty()) {
                    isDrawing = true;
                    new Thread(() -> {
                        drawNextImage();
                    }).start();
                }
            }
        }.start();
    }

    private void drawNextImage() {
        ImageData imageData = queue.poll();

        Platform.runLater(() -> {
            if (imageData != null) {
                gc.drawImage(SwingFXUtils.toFXImage(imageData.getImage(), null), imageData.getI(), imageData.getJ(),
                        imageData.getX(), imageData.getY());
                System.out
                        .println(String.format("Drawing image at i: {} , j: {},", imageData.getI(), imageData.getJ()));
            }
        });

    }

}
