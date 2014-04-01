package com.base.DoomGame;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Admin on 3/26/14.
 */
public class Bitmap {
    private int width;
    private int height;
    private int[] pixels;

    public Bitmap( String fileName ){
        try {
            BufferedImage image = ImageIO.read(new File("./resources/bitmaps/" + fileName));
            width = image.getWidth( );
            height = image.getHeight( );
            pixels = new int[width * height];
            image.getRGB( 0, 0, width, height, pixels, 0, width );
        } catch ( Exception e ){
            e.printStackTrace( );
            System.exit( 1 );
        }
    }

    public Bitmap( int width, int height ){
        this.width = width;
        this.height = height;
        this.pixels = new int[ width * height ];
    }

    public Bitmap FlipX( ){
        int[] temp = new int[ pixels.length ];
        for( int i = 0; i < width; i++ ){
            for( int j = 0; j < height; j++  ){
                temp[i + j * width ] = pixels[ i + ( height -  j - 1 ) * width];
            }
        }
        pixels = temp;
        return this;
    }

    public int getWidth( ) {
        return width;
    }

    public int getHeight( ) {
        return height;
    }

    public int[] getPixels( ) {
        return pixels;
    }

    public int GetPixel( int x, int y ){
        return  pixels[ x + y * width ];
    }

    public void SetPixel( int x, int y, int value ){
        pixels[ x + y * width ] = value;
    }
}
