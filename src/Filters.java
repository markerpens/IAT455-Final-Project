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
	
	public static final ArrayList<Filters> FILTERS = new ArrayList<>();

	static {
//		FILTERS.add(new Filters("Normal Vision", new float[][] { { 1f, 0f, 0f }, { 0f, 1f, 0f }, { 0f, 0f, 1f } }));
		
		FILTERS.add(new Filters("Protanopia", new float[][] { { 0.567f, 0.433f, 0f }, { 0.558f, 0.442f, 0f }, { 0f, 0.242f, 0.758f } }));
//		FILTERS.add(new Filters("Protanomaly", new float[][] { { 0.817f, 0.183f, 0f }, { 0.333f, 0.667f, 0f }, { 0f, 0.125f, 0.875f } }));
		
//		FILTERS.add(new Filters("Deuteranopia", new float[][] { { 0.625f, 0.375f, 0f }, { 0.458f, 0.442f, 0f }, { 0f, 0.3f, 0.7f } }));
//		FILTERS.add(new Filters("Deuteranomaly", new float[][] { { 0.8f, 0.2f, 0f }, { 0.258f, 0.742f, 0f }, { 0f, 0.142f, 0.858f } }));
		
//		FILTERS.add(new Filters("Tritanopia", new float[][] { { 0.95f, 0.05f, 0f }, { 0f, 0.433f, 0.567f }, { 0f, 0.475f, 0.525f } }));
//		FILTERS.add(new Filters("Tritanomaly", new float[][] { { 0.967f, 0.033f, 0f }, { 0f, 0.733f, 0.267f }, { 0f, 0.183f, 0.817f } }));
		
//		FILTERS.add(new Filters("Achromatopsia", new float[][] { { 0.299f, 0.587f, 0.114f }, { 0.299f, 0.587f, 0.114f }, { 0.299f, 0.587f, 0.114f } }));
//		FILTERS.add(new Filters("Achromatomaly", new float[][] { { 0.618f, 0.320f, 0.062f }, { 0.163f, 0.775f, 0.062f }, { 0.163f, 0.320f, 0.516f } }));
		
		// DEUTERANOMALY, TRITANOPIA, TRITANOMALY WORKS
	}
	
	public static BufferedImage filterImage(BufferedImage src, Filters f) {
		BufferedImage result = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		for (int i = 0; i < src.getWidth(); i++) {
			for (int j = 0; j < src.getHeight(); j++) {
				Color px = new Color(src.getRGB(i, j), true);
				
				int red = px.getRed();
				int green = px.getGreen();
				int blue = px.getBlue();
				
				red = clip(red);
				green = clip(green);
				blue = clip(blue);
				
				int alpha = px.getAlpha();
				
				int[] new_rgb = matrixMult(new float[] { red, green, blue }, f.imageFilter);
				
				Color newPixelColor = new Color(new_rgb[0], new_rgb[1], new_rgb[2], alpha);
				
				result.setRGB(i, j, newPixelColor.getRGB());
			}
		}
		return result;
	}

	private static int[] matrixMult(float[] vector, float[][] matrix) {
		int rgba1 = (int) (matrix[0][0] * vector[0] + matrix[0][1] * vector[1] + matrix[0][2] * vector[2]);
		
		int rgba2 = (int) (matrix[1][0] * vector[0] + matrix[1][1] * vector[1] + matrix[1][2] * vector[2]);
		
		int rgba3 = (int) (matrix[2][0] * vector[0] + matrix[2][1] * vector[1] + matrix[2][2] * vector[2]);
		
		return new int[] { rgba1, rgba2, rgba3 };
	}

//***************************************************************************	
	
	public BufferedImage colorCorrectedProtanopia(BufferedImage src) {
        BufferedImage result = new BufferedImage(src.getWidth(),
                src.getHeight(), src.getType());
        
        
        for (int i = 0; i < src.getWidth(); i++) {
        	for (int j = 0; j < src.getHeight(); j++) {
        		
        		int px = src.getRGB(i, j);
        		
        		int red = getRed(px);
        		int green = getGreen(px);
        		int blue = (int) (getBlue(px) * 3.5);
        		
				red = clip(red);
				green = clip(green);
				blue = clip(blue);
	        		
				if (red > green) {
					result.setRGB(i, j, new Color(blue, green, green).getRGB());
				}
				else {
					result.setRGB(i, j, new Color(red, green, blue).getRGB());
				}

        	}
        }
        
        return result;
		
	}

	
    private static int clip(int v) {
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

