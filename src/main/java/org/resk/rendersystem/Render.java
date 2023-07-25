package org.resk.rendersystem;

import java.util.Random;

public class Render {
    private int width, height;
    private int pixels[];
    private int tileIndex;
    private static final int MAP_SIZE = 32;
    private static final int MAP_SIZE_MASK = MAP_SIZE - 1;
    private int tiles[] = new int[MAP_SIZE * MAP_SIZE];

    private static final Random random = new Random();
    public Render(int width, int height, int[] pixels) {
        this.width = width;
        this.height = height;
        this.pixels = pixels;
        fill();
    }
    private void fill(){
        for (int i = 0; i< MAP_SIZE * MAP_SIZE  ; i++){
            tiles[i] = random.nextInt(0xffffff);

        }
    }
    public void clear(){
        for(int i = 0; i< pixels.length; i++){
            pixels[i] = 0;
        }
    }
    public void render(){
        for(int y = 0; y < height; y++ ){
            for(int x = 0; x< width; x++){
                tileIndex = ((y >> 4 ) ) + (((x >> 4) ) * MAP_SIZE);
                pixels[x + (y*width)] = tiles[tileIndex];
            }
        }
    }
}
