package com.image.imageprocessing.image;

import com.image.imageprocessing.image.DrawMultipleImageOnCanvas;

import javafx.scene.canvas.Canvas;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import javafx.scene.Scene;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class DrawMultipleImageOnCanvas {

    private static DrawMultipleImageOnCanvas instance;
    private Queue<ImageData> queue = new LinkedBlockingQueue<>();
    private GraphicsContext gc;
    private boolean isDrawing = false;

    public static DrawMultipleImageOnCanvas getInstance() {
        if (instance == null) {
            instance = new DrawMultipleImageOnCanvas();
        }
        return instance;
    }

    public void addImageToQueue(ImageData image) {
        queue.offer(image);
    }

    public void initialize(Stage primaryStage) {
        Canvas canvas = new Canvas(1920, 1080);
        this.gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, 1920, 1080);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!isDrawing && !queue.isEmpty()) { // ✅ changed
                    new Thread(() -> drawNextImage()).start();
                }
            }
        }.start();

        StackPane stack = new StackPane(canvas);
        Scene scene = new Scene(stack, 1920, 1080);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Canvas Example");
        primaryStage.show();
    }

    private void drawNextImage() {
        isDrawing = true; // ✅
        ImageData imageData = queue.poll();

        Platform.runLater(() -> {
            if (imageData != null) {
                gc.drawImage(SwingFXUtils.toFXImage(imageData.getImage(), null),
                        imageData.getI(), imageData.getJ(),
                        imageData.getX(), imageData.getY());
                System.out.println(String.format("Drawing image at i: %d , j: %d",
                        imageData.getI(), imageData.getJ()));
            }
            isDrawing = false; // ✅
        });
    }
}
