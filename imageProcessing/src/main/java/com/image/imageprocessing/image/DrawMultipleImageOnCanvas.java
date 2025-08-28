package com.image.imageprocessing.image;

public class DrawMultipleImageOnCanvas {

    private static DrawMultipleImageOnCanvas instance; // it is singleton

    public static DrawMultipleImageOnCanvas getInstance() {
        if (instance == null) {
            return new DrawMultipleImageOnCanvas();
        }
        return instance;
    }

}
