/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imageProcessing;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

/**
 *
 * @author chalbers2
 */
public class staticImageProcessingMethods {
    
    public static final int RGB_VALUE_BLACK = -16777216;
    public static final int RGB_VALUE_WHITE = -1;
    
    /**
     * This method will open up a new window and display a buffered image
     * @param image - BufferedImage to be displayed
     * @param windowName - String that is the title of the new window
     */
    public static void displayBufferedImage(BufferedImage image, String windowName){
        
        
        JFrame editorFrame = new JFrame(windowName);
        //editorFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        editorFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        ImageIcon imageIcon = new ImageIcon(image);
        JLabel jLabel = new JLabel();
        jLabel.setIcon(imageIcon);
        editorFrame.getContentPane().add(jLabel, BorderLayout.CENTER);

        editorFrame.pack();
        editorFrame.setLocationRelativeTo(null);
        editorFrame.setVisible(true);
    }
    
    /**
     * This method performs all necessary processing on an image to get into form for Etch-a-Sketch display.
     * @param image - BufferedImage to be displayed
     * @param etchWidth - Image width of the etch-a-sketch in pixels
     * @param etchHeight - Image height of the etch-a-sketch in pixels
     * @return - returns a BufferedImage suitable to be drawn on etch-a-sketch
     */
    public static BufferedImage getBufferedImageForEtchASketch(BufferedImage image, int etchWidth, int etchHeight){
        BufferedImage retval = null;
        
        // create new BufferedImage with same dimensions as etch-a-sketch
        retval = new BufferedImage(etchWidth, etchHeight, BufferedImage.TYPE_BYTE_BINARY);
        
        Graphics2D retvalGraphics = retval.createGraphics();
        
        BufferedImage scaledBWImage = getBWBufferedImageWithinMaxSizes(image, etchWidth-2, etchHeight-2);
        
        int scaledImageWidth = scaledBWImage.getWidth();
        int scaledImageHeight = scaledBWImage.getHeight();
        
        int xStartOfImage = (int)Math.round((etchWidth - scaledImageWidth) / 2.0);
        int yStartOfImage = (int)Math.round((etchHeight - scaledImageHeight) / 2.0);
        
        retvalGraphics.drawImage(scaledBWImage, xStartOfImage, yStartOfImage, null);
        
        
        // draw all white around the image
        retval = drawWhiteAroundImage(
            xStartOfImage, 
            yStartOfImage, 
            scaledImageWidth,
            scaledImageHeight,
            etchWidth,
            etchHeight, 
            retval);
        
        // draw a box around the image
        retval = drawBoxAroundImage(
            xStartOfImage, 
            yStartOfImage, 
            scaledImageWidth,
            scaledImageHeight,
            etchWidth,
            etchHeight, 
            retval);
        
        // draw a line from the etch-a-sketch origin to the box
        retval = drawLineToStartOfImage(
            xStartOfImage, 
            yStartOfImage, 
            scaledImageWidth,
            scaledImageHeight,
            etchWidth,
            etchHeight, 
            retval);
        
        return retval;
    }
    
    /**
     * This method will draw a line from the origin of the etch a sketch to the edge of the image
     * @param xStartOfImage
     * @param yStartOfImage
     * @param scaledImageWidth
     * @param scaledImageHeight
     * @param etchWidth
     * @param etchHeight
     * @param image
     * @return 
     */
    private static BufferedImage drawLineToStartOfImage(
            int xStartOfImage, 
            int yStartOfImage, 
            int scaledImageWidth,
            int scaledImageHeight,
            int etchWidth,
            int etchHeight, 
            BufferedImage image){
        
        for (int x = 0; x < xStartOfImage; x++){
            for (int y = etchHeight-1; y >= yStartOfImage + scaledImageHeight; y--){
                image.setRGB(x, y, RGB_VALUE_BLACK);
            }
        }
        
        return image;
    }
    
    /**
     * This method will draw a box around an image
     * @param xStartOfImage
     * @param yStartOfImage
     * @param scaledImageWidth
     * @param scaledImageHeight
     * @param etchWidth
     * @param etchHeight
     * @param image
     * @return 
     */
    private static BufferedImage drawBoxAroundImage(
            int xStartOfImage, 
            int yStartOfImage, 
            int scaledImageWidth,
            int scaledImageHeight,
            int etchWidth,
            int etchHeight, 
            BufferedImage image){
        
        // draw line on left of image
        for (int y = yStartOfImage; y < yStartOfImage + scaledImageHeight; y++){
            int x = xStartOfImage-1;
            image.setRGB(x, y, RGB_VALUE_BLACK);
        }
        
        // draw line on right of image
        for (int y = yStartOfImage; y < yStartOfImage + scaledImageHeight; y++){
            int x = xStartOfImage + scaledImageWidth;
            image.setRGB(x, y, RGB_VALUE_BLACK);
        }
        
        // draw line above image
        for (int x = xStartOfImage-1; x < xStartOfImage + scaledImageWidth + 1; x++){
            int y = yStartOfImage-1;
            image.setRGB(x, y, RGB_VALUE_BLACK);
        }
        
        // draw line below image
        for (int x = xStartOfImage-1; x < xStartOfImage + scaledImageWidth + 1; x++){
            int y = yStartOfImage + scaledImageHeight;
            image.setRGB(x, y, RGB_VALUE_BLACK);
        }
        
        return image;
    }
    
    /**
     * This method will draw all white around a centered image in the bufferedImage
     * @param xStartOfImage
     * @param yStartOfImage
     * @param scaledImageWidth
     * @param scaledImageHeight
     * @param etchWidth
     * @param etchHeight
     * @param image
     * @return 
     */
    private static BufferedImage drawWhiteAroundImage(
            int xStartOfImage, 
            int yStartOfImage, 
            int scaledImageWidth,
            int scaledImageHeight,
            int etchWidth,
            int etchHeight, 
            BufferedImage image){
        
        // draw white on left side of image
        for (int x = 0; x < xStartOfImage; x++){
            for (int y = 0; y < etchHeight; y++){
                image.setRGB(x, y, RGB_VALUE_WHITE);
            }
        }
        
        // draw white on right side of image
        for (int x = xStartOfImage + scaledImageWidth; x < etchWidth; x++){
            for (int y = 0; y < etchHeight; y++){
                image.setRGB(x, y, RGB_VALUE_WHITE);
            }
        }
        
        //draw white above image
        for (int x = xStartOfImage; x < xStartOfImage + scaledImageWidth; x++){
            for (int y = 0; y < yStartOfImage; y++){
                image.setRGB(x, y, RGB_VALUE_WHITE);
            }
        }
        
        // draw white below image
        for (int x = xStartOfImage; x < xStartOfImage + scaledImageWidth; x++){
            for (int y = yStartOfImage + scaledImageHeight; y < etchHeight; y++){
                image.setRGB(x, y, RGB_VALUE_WHITE);
            }
        }
        
        return image;
    }
    
    public static BufferedImage getBWBufferedImageWithinMaxSizes(BufferedImage image, int maxWidth, int maxHeight){
        BufferedImage retval = null;
        
        int originalImageWidth = image.getWidth();
        int originalImageHeight = image.getHeight();
        
        double scaleForWidth = (double)maxWidth / (double)originalImageWidth;
        double scaleForHeight = (double)maxHeight / (double)originalImageHeight;
        
        double scale = Math.min(scaleForWidth, scaleForHeight);
        
        retval = convertBufferedImageToBlackAndWhite(scaleBufferedImageByAmount(image, scale));
        
        return retval;
    }
    
    public static BufferedImage scaleBufferedImageByAmount(BufferedImage image, double scale){
        BufferedImage retval = null;
        
        int newWidth = (int)Math.round(image.getWidth() * scale);
        int newHeight = (int)Math.round(image.getHeight() * scale);
        
        Image scaledImg =  image.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
        
        
        
        retval = new BufferedImage(
                newWidth, 
                newHeight, 
                BufferedImage.TYPE_BYTE_BINARY);
        
        Graphics2D gScaled = retval.createGraphics();
        
        gScaled.drawImage(scaledImg, 0, 0, null);
        
        return retval;
    }
    
    public static BufferedImage getBufferedImageWithCorrectAspectRatio(BufferedImage image, double aspectRatio){
        BufferedImage retval = null;
        
        int newWidth = (int)Math.round(image.getWidth());
        int newHeight = (int)Math.round(image.getHeight() * aspectRatio);
        
        Image scaledImg =  image.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
        
        
        
        retval = new BufferedImage(
                newWidth, 
                newHeight, 
                BufferedImage.TYPE_BYTE_BINARY);
        
        Graphics2D gScaled = retval.createGraphics();
        
        gScaled.drawImage(scaledImg, 0, 0, null);
        
        return retval;
    }
    
    public static BufferedImage convertBufferedImageToBlackAndWhite(BufferedImage RGBImage){
        BufferedImage retval = null;
        
        retval = new BufferedImage(RGBImage.getWidth(), RGBImage.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        
        Graphics2D g = retval.createGraphics();
        
        g.drawImage(RGBImage, 0, 0, null);
        
        return retval;
    }
    
    public static String getStringOfCharactersForImage(BufferedImage image, String str){
        String retval = "";
        int width = image.getWidth();
        int height = image.getHeight();
        
        
        
        int currentCharIndex = -1;
        
        for (int i = 0; i<height; i++){
            retval += "\n";
            for (int j = 0; j<width; j++){
                if (image.getRGB(j, i) == RGB_VALUE_BLACK){
                    currentCharIndex++;
                    if (currentCharIndex < 0 || currentCharIndex >= str.length()){
                        currentCharIndex = 0;
                    }
                    char toUse = str.charAt(currentCharIndex);
                    retval += toUse;
                } else {
                    retval += " ";
                }
            }
        }
        
        return retval;
    }
}
