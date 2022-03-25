import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.lang.String; 

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;


class Main extends JFrame{
	BufferedImage imagePreview;
	BufferedImage imageCBFilter; 
	BufferedImage imageOutput; 
    Button UploadBtn;

	int width; //width of images
	int height; //height of images
	int h;
	Filters filter;
	
	
	public Main() {
		setTitle("IAT 455 Final");
        setSize(1000, 1000);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
       
        
        h = this.height;
  		
  		UploadBtn = new Button("Choose your Image");   
  		        
  		JButton uploadBtn = new JButton("Click here!");
	     uploadBtn.setBounds(40,800,100,40);
	     uploadBtn.addActionListener(new ActionListener(){
    	   public void actionPerformed(ActionEvent e){
    		   uploadBtn();
    	   }
    	});
        
        JPanel panel = new JPanel();
        uploadBtn.setBounds(40,800,100,40);

        panel.setLayout(null);
        panel.add(uploadBtn);
        
        this.getContentPane().add(panel);
      

	}
	
	public void uploadBtn() {
		 
	     try {
			imagePreview = ImageIO.read(chooseImage());
		    } catch (IOException ex) {
		        ex.printStackTrace();
		    }
	        width = imagePreview.getWidth();// 
	  		height = imagePreview.getHeight();// 
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
	
	public void paint(Graphics g){
		int w = width/5; 
		int h = height/5;
		
		

		g.drawImage(imagePreview ,25,50,w, h,this);
	    g.drawImage(imageCBFilter, 100+w, 50, w, h,this);
	    g.drawImage(imageOutput, 200+w*2, 50, w, h,this);
	       
	    
	    g.setColor(Color.BLACK);
	    Font f1 = new Font("Verdana", Font.PLAIN, 13);  
	    g.setFont(f1); 
	    
	    g.drawString("Original Image", 25, 40); 
	    g.drawString("Protanopia", 125+w, 40); 
	    g.drawString("Color Corrected", 325+w, 40); 

	    		    	    
	}
	 public static void main(String[] args){
		
	    Main img = new Main();//instantiate this object
	    img.repaint();//render the image
		
	  }
}
