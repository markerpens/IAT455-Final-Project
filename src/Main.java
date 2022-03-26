import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.lang.String; 
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Graphics;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class Main extends JFrame{//inheriting JFrame  
	JFrame f; 
	BufferedImage imagePreview;
	BufferedImage imageColorBlindPreview;
	BufferedImage imageReColored;
	BufferedImage imageOutputFilter;

	int width; //width of images
	int height; //height of images
	
	Main(){  
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		pack();
		JButton b=new JButton("Upload Photo");//create button  
		b.setBounds(screenSize.width/2-50,screenSize.height-400,100, 40);  
		          
		add(b);//adding button on frame  
		
		
		JLabel Label1,Label2,Label3,Label4;  
		Label1=new JLabel("Original Image");  
		Label1.setBounds(25,50, 100,30);  
		
		Label2=new JLabel("Protanopia");  
		Label2.setBounds(400,50, 100,30);  
		
		Label3=new JLabel("Recolored");  
		Label3.setBounds(775,50, 100,30); 
		
		Label4=new JLabel("Recolored POV");  
		Label4.setBounds(1150,50, 100,30); 
		
	    add(Label1); 
	    add(Label2);
	    add(Label3);  
	    add(Label4);  


	    
	    
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

//Resize images with the correct aspect ratio
	  		
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
	  	      new_width = (new_height * imagePreview.getWidth()) / imagePreview.getHeight();
	  	    }
	  	    
			
		    
		    imageColorBlindPreview = Filters.filterImage(imagePreview, Filters.FILTERS.get(0));
		    imageReColored = Filters.FILTERS.get(0).colorCorrectedProtanopia(imagePreview);
			imageOutputFilter = Filters.filterImage(imageReColored, Filters.FILTERS.get(0));
	      
			g.fillRect(25, 150, 350, 350);
	        g.fillRect(400, 150, 350, 350);
	        g.fillRect(775, 150, 350, 350);
	        g.fillRect(1150, 150, 350, 350);

			g.drawImage(imagePreview ,((150)-(width/2))+50,((150)-(new_height/2))+175,width, new_height,this);
			g.drawImage(imageColorBlindPreview ,((150)-(width/2))+425,((150)-(new_height/2))+175,width, new_height,this);
			g.drawImage(imageReColored ,((150)-(width/2))+800,((150)-(new_height/2))+175,width, new_height,this);
			g.drawImage(imageOutputFilter ,((150)-(width/2))+1175,((150)-(new_height/2))+175,width, new_height,this);


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


