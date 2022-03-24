import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.lang.String; 

import javax.imageio.ImageIO;


class Main extends Frame{

	BufferedImage imagePreview; 
	BufferedImage imageCBFilter; 
	BufferedImage imageOutput; 
	int width; //width of images
	int height; //height of images
	
	public Main() {
		
		try {
			imagePreview = ImageIO.read(new File("DotImageNormal.png"));
			
		} catch (IOException e) {
			System.out.println("Cannot load the provided image");

		} 

		width = imagePreview.getWidth();// 
		height = imagePreview.getHeight();// 
		
		this.setTitle("IAT 455 Final");
		this.setVisible(true);

		
		this.addWindowListener(
				new WindowAdapter(){//anonymous class definition
					public void windowClosing(WindowEvent e){
						System.exit(0);//terminate the program
					}//end windowClosing()
				}//end WindowAdapter
				);//end addWindowListener
		
	}
	
	
	public void paint(Graphics g){
		int w = width/5; 
		int h = height/5;
		
		
		this.setSize(w*5 +100,h*4+50);
		
		g.drawImage(imagePreview ,25,50,w, h,this);
	    g.drawImage(imageCBFilter, 100+w, 50, w, h,this);
	    g.drawImage(imageOutput, 200+w*2, 50, w, h,this);
	       
	    
	    g.setColor(Color.BLACK);
	    Font f1 = new Font("Verdana", Font.PLAIN, 13);  
	    g.setFont(f1); 
	    
	    g.drawString("Original Image", 25, 40); 
	    g.drawString("Color Blind FIlter", 125+w, 40); 
	    g.drawString("Color Corrected", 425+w, 40); 

	    		    	    
	}
	 public static void main(String[] args){
		
	    Main img = new Main();//instantiate this object
	    img.repaint();//render the image
		
	  }
}
