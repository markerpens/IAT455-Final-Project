import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import java.awt.color.ColorSpace;

public class Filters {
	
//***************************************************************************	
	private String name;
	private float[][] imageFilter;

	public Filters(String name, float[][] filter) {
		this.name = name;
		this.imageFilter = filter;
	}

	public String getName() {
		return name;
	}
	
	public static final List<Filters> FILTERS = new ArrayList<>();

	static {
//		FILTERS.add(new Filters("Normal Vision", new float[][] { { 1f, 0f, 0f }, { 0f, 1f, 0f }, { 0f, 0f, 1f } }));
		FILTERS.add(new Filters("Protanopia", new float[][] { { 0.567f, 0.433f, 0f }, { 0.558f, 0.442f, 0f }, { 0f, 0.242f, 0.758f } }));
//		FILTERS.add(new Filters("Protanomaly", new float[][] { { 0.817f, 0.183f, 0f }, { 0.333f, 0.667f, 0f }, { 0f, 0.125f, 0.875f } }));
//		FILTERS.add(new Filters("Deuteranopia", new float[][] { { 0.625f, 0.375f, 0f }, { 0.7f, 0.3f, 0f }, { 0f, 0.3f, 0.7f } }));
//		FILTERS.add(new Filters("Deuteranomaly", new float[][] { { 0.8f, 0.2f, 0f }, { 0.258f, 0.742f, 0f }, { 0f, 0.142f, 0.858f } }));
//		FILTERS.add(new Filters("Tritanopia", new float[][] { { 0.95f, 0.05f, 0f }, { 0f, 0.433f, 0.567f }, { 0f, 0.475f, 0.525f } }));
//		FILTERS.add(new Filters("Tritanomaly", new float[][] { { 0.967f, 0.033f, 0f }, { 0f, 0.733f, 0.267f }, { 0f, 0.183f, 0.817f } }));
//		FILTERS.add(new Filters("Achromatopsia", new float[][] { { 0.299f, 0.587f, 0.114f }, { 0.299f, 0.587f, 0.114f }, { 0.299f, 0.587f, 0.114f } }));
//		FILTERS.add(new Filters("Achromatomaly", new float[][] { { 0.618f, 0.320f, 0.062f }, { 0.163f, 0.775f, 0.062f }, { 0.163f, 0.320f, 0.516f } }));
	}
	
	public static BufferedImage filterImage(BufferedImage src, Filters f) {
		BufferedImage result = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		for (int i = 0; i < src.getWidth(); i++) {
			for (int j = 0; j < src.getHeight(); j++) {
				Color pixelColor = new Color(src.getRGB(i, j), true);
				
				int red = pixelColor.getRed();
				int green = pixelColor.getGreen();
				int blue = pixelColor.getBlue();
				
				int alpha = pixelColor.getAlpha();
				
				int[] newRGB = mult(new float[] { red, green, blue }, f.imageFilter);
				
				Color newPixelColor = new Color(newRGB[0], newRGB[1], newRGB[2], alpha);
				
				result.setRGB(i, j, newPixelColor.getRGB());
			}
		}
		return result;
	}

	private static int[] mult(float[] vector, float[][] matrix) {
		int firstElement = (int) (matrix[0][0] * vector[0] + matrix[0][1] * vector[1] + matrix[0][2] * vector[2]);
		int secondElement = (int) (matrix[1][0] * vector[0] + matrix[1][1] * vector[1] + matrix[1][2] * vector[2]);
		int thirdElement = (int) (matrix[2][0] * vector[0] + matrix[2][1] * vector[1] + matrix[2][2] * vector[2]);
		
		return new int[] { firstElement, secondElement, thirdElement };
	}

//***************************************************************************	
	
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

	public BufferedImage protanopiaReplication(BufferedImage src) {
        BufferedImage result = new BufferedImage(src.getWidth(),
                src.getHeight(), src.getType());
        
//        var ColorMatrixMatrixes = {
//        	    Normal:       {
//        	        R:[100,      0,     0],
//        	        G:  [0,    100,      0],
//        	        B:  [0,      0, 100/*Fixed: was in the wrong spot in the original version*/]},
//        	    Protanopia:   {
//        	        R:[56.667, 43.333,  0],
//        	        G:[55.833, 44.167,  0],
//        	        B: [0,     24.167, 75.833]},
//        	    Protanomaly:  {
//        	        R:[81.667, 18.333,  0],
//        	        G:[33.333, 66.667,  0],
//        	        B: [0,     12.5,   87.5]},
//        	    Deuteranopia: {
//        	        R:[62.5, 37.5,  0],
//        	        G:[70,   30,    0],
//        	        B: [0,   30,   70]},
//        	    Deuteranomaly:{
//        	        R:[80,     20,      0],
//        	        G:[25.833, 74.167,  0],
//        	        B: [0,     14.167, 85.833]},
//        	    Tritanopia:   {
//        	        R:[95,  5,      0],
//        	        G: [0, 43.333, 56.667],
//        	        B: [0, 47.5,   52.5]},
//        	    Tritanomaly:  {
//        	        R:[96.667, 3.333,   0],
//        	        G: [0,     73.333, 26.667],
//        	        B: [0,     18.333, 81.667]},
//        	    Achromatopsia:{
//        	        R:[29.9, 58.7, 11.4],
//        	        G:[29.9, 58.7, 11.4],
//        	        B:[29.9, 58.7, 11.4]},
//        	    Achromatomaly:{
//        	        R:[61.8, 32,    6.2],
//        	        G:[16.3, 77.5,  6.2],
//        	        B:[16.3, 32.0, 51.6]}
//        	};
//        
        
        int[] ColorMatrixes = {0,1,2};

        
        // Protanopia:{ R:[56.667, 43.333, 0], G:[55.833, 44.167, 0], B:[0, 24.167, 75.833]},
        
        for (int i = 0; i < src.getWidth(); i++) {
        	for (int j = 0; j < src.getHeight(); j++) {
        		
        		int px = src.getRGB(i, j);
        		
        		int r = getRed(px);
        		int g = getGreen(px);
        		int b = getBlue(px);
        		
        		int newR = r - 0;
        		int newG = g - 0;
        		int newB = 	b;
        		
        		int newR2 = r - 228;
        		int newG2 = g - 215;
        		int newB2 = b;
        		
        		int newR3 = r;
        		int newG3 = g - 240;
        		int newB3 = b - 234;
        		
        		
        		
                newR = clip(newR);
                newG = clip(newG);
                newB = clip(newB);
                
        		
//                newR2 = clip(newR2);
//                newG2 = clip(newG2);
//                newB2 = clip(newB2);
//                
//        		
//                newR3 = clip(newR3);
//                newG3 = clip(newG3);
//                newB3 = clip(newB3);
        		
                int fuck = new Color(newR, newG, newB).getRGB();
//                int fuck2 = new Color(newR2, newG2, newB2).getRGB();
//                int fuck3 = new Color(newR3, newG3, newB3).getRGB();
                
                fuck = clip(fuck);
//                fuck2 = clip(fuck2);
//                fuck3 = clip(fuck3);
                
                
                
                
//        		int new_r = (int) (r * 2);
//        		int new_g = (int) (g * 2);
//        		int new_b = (int) (b * 2);
//        		
//        		int new_r2 = (int) (r * 0.55833);
//        		int new_g2 = (int) (g * 0.44167);
//        		int new_b2 = (int) (b);
//        		
//        		int new_r3 = (int) (r);
//        		int new_g3 = (int) (g * 024167);
//        		int new_b3 = (int) (b * 0.75833);
        		
//        		int new_r = r * 0.1;
//        		int new_g = g;
//        		int new_b = b;
        		
        		        		
//				if (r > g) {
//					result.setRGB(i, j, new Color(b, g, g).getRGB());
//				}
//				else {
//					result.setRGB(i, j, new Color(r, g, b).getRGB());
//				}
        		
//				float hsb[] = Color.RGBtoHSB(r, g, b, null);
//				float newR = hsb[0] * 56;
//				
//				 result.setRGB(i, j, Color.HSBtoRGB(newR, hsb[1], hsb[2]));
        		
        		
        		
        		
				
				  result.setRGB(i, j, new Color(newR, newG, newB).getRGB());
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
	
    private int clip(int v) {
        v = v > 255 ? 255 : v;
        v = v < 0 ? 0 : v;
        return v;
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

