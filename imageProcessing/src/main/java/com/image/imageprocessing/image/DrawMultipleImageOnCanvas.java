package com.image.imageprocessing.image;

import javafx.scene.canvas.Canvas;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import javafx.scene.Scene;
import javafx.scene.Group;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class DrawMultipleImageOnCanvas {

    private static DrawMultipleImageOnCanvas instance; // it is singleton
    private final Queue<ImageData> queue = new LinkedBlockingQueue<>();
    private Canvas canvas;
    private GraphicsContext gc;

    // Private constructor to prevent direct instantiation
    private DrawMultipleImageOnCanvas() {
    }

    // Correct, thread-safe singleton implementation
    public static synchronized DrawMultipleImageOnCanvas getInstance() {
        if (instance == null) {
            instance = new DrawMultipleImageOnCanvas();
        }
        return instance;
    }

    public void addImageToQueue(ImageData image) {
        queue.add(image);
    }

    public void initialize(Stage primaryStage) {
        this.canvas = new Canvas(1920, 1080);
        this.gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, 1920, 1080);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Poll the queue on every frame. If an image is available, draw it.
                ImageData imageData = queue.poll();
                if (imageData != null) {
                    drawNextImage(imageData);
                }
            }
        }.start();

        Group root = new Group();
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    private void drawNextImage(ImageData imageData) {
        // This method is called from AnimationTimer, which runs on the JavaFX
        // Application Thread.
        // No need for Platform.runLater().
        gc.drawImage(SwingFXUtils.toFXImage(imageData.getImage(), null), imageData.getI(), imageData.getJ(),
                imageData.getX(), imageData.getY());
        // Using printf for formatted strings. The original String.format would throw an
        // exception.
        System.out.printf("Drawing image at x: %d, y: %d%n", imageData.getI(), imageData.getJ());
    }

}
