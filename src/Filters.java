import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.lang.String; 
import javax.imageio.ImageIO;

public class Filters {
	
	public BufferedImage colorCorrectedProtanopia(BufferedImage src) {
        BufferedImage result = new BufferedImage(src.getWidth(),
                src.getHeight(), src.getType());
        
        for (int i = 0; i < src.getWidth(); i++) {
        	for (int j = 0; j < src.getHeight(); j++) {
        		
        		int px = src.getRGB(i, j);
        		
        		int r = getRed(px);
        		int g = getGreen(px);
        		int b = getBlue(px);
        		
//        		int new_r = r * 0.1;
//        		int new_g = g;
//        		int new_b = b;
        		
        		        		
				if (r > g) {
					result.setRGB(i, j, new Color(b, g, g).getRGB());
				}
				else {
					result.setRGB(i, j, new Color(r, g, b).getRGB());
				}
				
//				 result.setRGB(i, j, new Color(new_r, new_g, new_b).getRGB());
        	}
        }
        
        return result;
		
	}
	
	public BufferedImage correctedProtanopiaPerspective(BufferedImage src) {
        BufferedImage result = new BufferedImage(src.getWidth(),
                src.getHeight(), src.getType());
        
        for (int i = 0; i < src.getWidth(); i++) {
        	for (int j = 0; j < src.getHeight(); j++) {
        		
        		int px = src.getRGB(i, j);
        		
        		int r = getRed(px);
        		int g = getGreen(px);
        		int b = getBlue(px);
        		
        		int new_r = r;
        		int new_g = g;
        		int new_b = b;
        		
        		result.setRGB(i, j, new Color(new_r, new_g, new_b).getRGB());
        	}
        }
        
        return result;
		
	}
	
	protected int getRed(int pixel) {
		return (pixel >>> 16) & 0xFF;
	}

	protected int getGreen(int pixel) {
		return (pixel >>> 8) & 0xFF;
	}

	protected int getBlue(int pixel) {
		return pixel & 0xFF;
	}
	
}
//hola

