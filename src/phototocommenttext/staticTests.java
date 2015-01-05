/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phototocommenttext;

import imageProcessing.staticImageProcessingMethods;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author chalbers2
 */
public class staticTests {
    public static void testOfAllInOneMethod(){
        String filename = "photoForTest_18.jpg";
        
        
        BufferedImage image = null;
        
        try
        {
          image = ImageIO.read(new File(filename));
        }
        catch (Exception e)
        {
          e.printStackTrace();
          System.exit(1);
        }
        
        BufferedImage imageToPrint = imageProcessing.staticImageProcessingMethods.getBufferedImageWithCorrectAspectRatio(image, 0.5);
        imageToPrint = imageProcessing.staticImageProcessingMethods.getBWBufferedImageWithinMaxSizes(imageToPrint, 140, 80);
        System.out.println("Width: " + imageToPrint.getWidth() + " Height: " + imageToPrint.getHeight());
        System.out.println(staticImageProcessingMethods.getStringOfCharactersForImage(imageToPrint, "x"));
    }
    
    
}
