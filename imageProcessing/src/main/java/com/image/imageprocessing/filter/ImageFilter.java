package com.image.imageprocessing.filter;

import java.awt.image.BufferedImage;
import java.nio.Buffer;

public interface ImageFilter {

    BufferedImage filter(BufferedImage image);
}
