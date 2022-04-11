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
	
	// Color Blindness Type Values and Color Matrix obtained from:
	// ColorJack: http://web.archive.org/web/20081014161121/http://www.colorjack.com/labs/colormatrix/
	// Coblis: https://www.color-blindness.com/coblis-color-blindness-simulator/
	// Microsoft .NET: https://docs.microsoft.com/en-us/dotnet/desktop/winforms/advanced/how-to-use-a-color-matrix-to-transform-a-single-color?view=netframeworkdesktop-4.8

	// Adapted from Doga Uruc's Java CB Simulator: https://github.com/aeris170/ColorBlindnessSimulator
	// Translates ColorJack/Coblis values + color matrix
	
	private float[][] colorBlindFilter;

	public Filters(String colorBlindnessName, float[][] filterType) {
		this.colorBlindFilter = filterType;
	}

	public static final ArrayList<Filters> COLOR_BLIND_FILTERS = new ArrayList<>();

	static {
		// Protanopia values
		COLOR_BLIND_FILTERS.add(new Filters("Protanopia", new float[][] { 
			{ 0.567f, 0.433f, 0f }, { 0.558f, 0.442f, 0f }, { 0f, 0.242f, 0.758f } }));	
		
		COLOR_BLIND_FILTERS.add(new Filters("Deuteranopia", new float[][] { 
			{ 0.625f, 0.375f, 0f }, { 0.458f, 0.442f, 0f }, { 0f, 0.3f, 0.7f } }));
		
		COLOR_BLIND_FILTERS.add(new Filters("Tritanopia", new float[][] { 
			{ 0.95f, 0.05f, 0f }, { 0f, 0.433f, 0.567f }, { 0f, 0.475f, 0.525f } }));

		// Protanopaly, Deuteranopaly, Tritanomaly values
		COLOR_BLIND_FILTERS.add(new Filters("Protanomaly", new float[][] { 
			{ 0.817f, 0.183f, 0f }, { 0.333f, 0.667f, 0f }, { 0f, 0.125f, 0.875f } }));
		
		COLOR_BLIND_FILTERS.add(new Filters("Deuteranomaly", new float[][] 
				{ { 0.8f, 0.2f, 0f }, { 0.258f, 0.742f, 0f }, { 0f, 0.142f, 0.858f } }));
		
		COLOR_BLIND_FILTERS.add(new Filters("Tritanomaly", new float[][] { {
			0.967f, 0.033f, 0f }, { 0f, 0.733f, 0.267f }, { 0f, 0.183f, 0.817f } }));
	}
	
	public static BufferedImage colorBlindFilter(BufferedImage src, Filters filter) {
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
				
				int[] new_rgb = matrixMult(new float[] { red, green, blue }, filter.colorBlindFilter);
				
				Color newPixelColor = new Color(new_rgb[0], new_rgb[1], new_rgb[2], alpha);
				
				result.setRGB(i, j, newPixelColor.getRGB());
			}
		}
		return result;
	}

	//***************************************************************************	
	
	public static final ArrayList<Filters> FIXED_FILTERS = new ArrayList<>();

	static {
		// Fixed Protanopia, Deuteranopia, Tritanopia values
		FIXED_FILTERS.add(new Filters("ProtanopiaFixed", new float[][] { 
			{ 0.8f, 0.1f, 0f }, { 0.8f, 0.2f, 0f }, { 0.1f, 0.6f, 0.2f } }));
		
		FIXED_FILTERS.add(new Filters("DeuteranopiaFixed", new float[][] { 
			{ 0.925f, 0.075f, 0f }, { 0.558f, 0.142f, 0.2f }, { 0f, 0.8f, 0.2f } }));
		
		FIXED_FILTERS.add(new Filters("TritanopiaFixed", new float[][] { 
			{ 0.05f, 0.95f, 0f }, { 0f, 0.133f, 0.867f }, { 0f, 0.475f, 0.525f } }));

		// Fixed Protanopaly, Deuteranopaly, Tritanomaly values
		FIXED_FILTERS.add(new Filters("ProtanomalyFixed", new float[][] { 
			{ 0.8f, 0.1f, 0f }, { 0.8f, 0.2f, 0f }, { 0.1f, 0.6f, 0.2f } }));
		
		FIXED_FILTERS.add(new Filters("DeuteranomalyFixed", new float[][] 
				{ { 0.925f, 0.075f, 0f }, { 0.558f, 0.142f, 0.2f }, { 0f, 0.8f, 0.2f } }));
		
		FIXED_FILTERS.add(new Filters("TritanomalyFixed", new float[][] { 
			{ 0.05f, 0.95f, 0f }, { 0f, 0.133f, 0.867f }, { 0f, 0.475f, 0.525f } }));
	}
	
	public static BufferedImage colorCorrectedFilter(BufferedImage src, Filters filter) {
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
				
				int[] new_rgb = matrixMult(new float[] { red, green, blue }, filter.colorBlindFilter);
				
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