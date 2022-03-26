import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.awt.Graphics;
import java.lang.String; 
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class Main extends JFrame{//inheriting JFrame  
	JFrame f; 
	BufferedImage imagePreview;
	int width; //width of images
	int height; //height of images
	
	Main(){  
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		pack();
		JButton b=new JButton("click");//create button  
		b.setBounds(screenSize.width/2-50,screenSize.height-400,100, 40);  
		          
		add(b);//adding button on frame  
		
		
		JLabel Label1,Label2,Label3;  
		Label1=new JLabel("Original Image");  
		Label1.setBounds(25,50, 100,30);  
		
		Label2=new JLabel("Protanopia");  
		Label2.setBounds(400,50, 100,30);  
		
		Label3=new JLabel("Recolored");  
		Label3.setBounds(825,50, 100,30); 
	    add(Label1); 
	    add(Label2);
	    add(Label3);  

	    
	    
		setSize(screenSize.width,screenSize.height);
		setLayout(null);  
		setVisible(true);  
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        b.addActionListener(new ActionListener(){  
        	public void actionPerformed(ActionEvent e){  
        		uploadBtn();        	        
    		}  
	    });  
	}  
	
	 public void paint(Graphics g) {  
	        super.paint(g);

	        Toolkit t=Toolkit.getDefaultToolkit();  
	        Image i=t.getImage("p3.gif");  
	        
	        width = imagePreview.getWidth();// 
	  		height = imagePreview.getHeight();// 
	  		int new_height = height;
	  		int new_width = width;


	  	// first check if we need to scale width
	  	    if (imagePreview.getWidth() > 300) {
	  	        //scale width to fit
	  	        width = 300;
	  	        //scale height to maintain aspect ratio
	  	        new_height = (new_width * imagePreview.getHeight()) / imagePreview.getWidth();
	  	    }

	  	    // then check if we need to scale even with the new height
	  	    if (new_height > 300) {
	  	        //scale height to fit instead
	  	        new_height = 300;
	  	        //scale width to maintain aspect ratio
	  	      width = (new_height * imagePreview.getWidth()) / imagePreview.getHeight();
	  	    }
	        g.fillRect(25, 150, 350, 350);
			g.drawImage(imagePreview ,((150)-(width/2))+50,((150)-(new_height/2))+175,width, new_height,this);
//			g.drawImage(imagePreview ,25,50,width, new_height,this);
	          
	    }  
	public void uploadBtn() {
	     try {
			imagePreview = ImageIO.read(chooseImage());
		    } catch (IOException ex) {
		        ex.printStackTrace();
		    }
	   
  		repaint();
	}
	
	public File chooseImage() {
	    
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		 File selectedFile = null;
		jfc.setDialogTitle("Select an image");
		jfc.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPEG", "jpeg", "jpg", "png", "bmp", "gif");
		jfc.addChoosableFileFilter(filter);


		int returnValue = jfc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			System.out.println(jfc.getSelectedFile().getPath());
	         selectedFile = jfc.getSelectedFile();
		}
		return selectedFile;
	}
	
	public static void main(String[] args) {  
		new Main();  
	}
}  
