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
	JFrame frame; 
	BufferedImage imagePreview;
	BufferedImage imageColorBlindPreview;
	BufferedImage imageReColored;
	BufferedImage imageOutputFilter;
	
	JRadioButton protanopia = new JRadioButton();
	JRadioButton deuteranopia = new JRadioButton(); 
	JRadioButton tritanopia = new JRadioButton();
	
//	JRadioButton protanomaly = new JRadioButton();
//	JRadioButton deuteranomaly = new JRadioButton(); 
//	JRadioButton tritanomaly = new JRadioButton();
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	float screenWidth = screenSize.width;
	   float screenHeight = screenSize.height;

	int filterNumber;
	int width; //width of images
	int height; //height of images
	
	Main(){  
		
		pack();
		
		getContentPane().setBackground(Color.BLACK);
		
		JButton uploadBtn=new JButton("Upload Photo");//create button  
		uploadBtn.setBounds(screenSize.width/2-75,screenSize.height-400,150, 40);  
		uploadBtn.setBackground(Color.WHITE); 
		uploadBtn.setForeground(Color.BLACK); 
		uploadBtn.setBorderPainted(false); 
		uploadBtn.setOpaque(true);
		
		add(uploadBtn);//adding button on frame  
		
		ButtonGroup filterRadio = new ButtonGroup();
		
		protanopia.setText("Protanopia");
		protanopia.setForeground(Color.WHITE);

		protanopia.setBounds(screenSize.width/2-190,screenSize.height-370,150,100);
		protanopia.setSelected(true);


		deuteranopia.setText("Deuteranopia");
		deuteranopia.setBounds(screenSize.width/2-60,screenSize.height-370,150,100);  
		deuteranopia.setForeground(Color.WHITE);


		tritanopia.setText("Tritanopia");
		tritanopia.setBounds(screenSize.width/2+80 ,screenSize.height-370,150,100);  
		tritanopia.setForeground(Color.WHITE);



		filterRadio.add(protanopia);
		filterRadio.add(deuteranopia);
		filterRadio.add(tritanopia);
		
		this.add(protanopia);
		this.add(deuteranopia);
		this.add(tritanopia);

		 RadioButtonHandler roh = new RadioButtonHandler( );
		 protanopia.addItemListener( roh );
		 deuteranopia.addItemListener( roh );
		 tritanopia.addItemListener( roh );

		
		
		JLabel Label1,Label2,Label3,Label4;  
		Label1=new JLabel("Original Image:");  
		Label1.setBounds((int) (screenWidth/4)- 312,50, 100,30);  
		Label1.setForeground(Color.WHITE);

		
		Label2=new JLabel("Color Blind POV:");  
		Label2.setBounds((int) (screenWidth/4) +63,50, 150,30);  
		Label2.setForeground(Color.WHITE);

		
		Label3=new JLabel("Recolored:");  
		Label3.setBounds((int) (screenWidth/4) +438,50, 100,30); 
		Label3.setForeground(Color.WHITE);

		
		Label4=new JLabel("Recolored POV:");  
		Label4.setBounds((int) (screenWidth/4) +813,50, 100,30); 
		Label4.setForeground(Color.WHITE);

	    add(Label1); 
	    add(Label2);
	    add(Label3);  
	    add(Label4);  


	    
	 

		setSize(screenSize.width,screenSize.height);
		setLayout(null);  
		setVisible(true);  
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        uploadBtn.addActionListener(new ActionListener(){  
        	public void actionPerformed(ActionEvent e){  
        		uploadBtn();        	        
    		}  
	    });  
	}  
	
//	Updates the image based on the radio button that is selected
	private class RadioButtonHandler implements ItemListener
	 {
	  public void itemStateChanged( ItemEvent ie )
	  {
	        if ( ie.getSource( ) == protanopia )
	        	filterNumber = 0;
	        else if ( ie.getSource( ) == deuteranopia )
	        	filterNumber = 1;
	        else if ( ie.getSource( ) == tritanopia )
	        	filterNumber = 2;
	        
//	        else if ( ie.getSource( ) == protanomaly )
//	        	filterNumber = 3;
//	        else if ( ie.getSource( ) == deuteranomaly )
//	        	filterNumber = 4;
//	        else if ( ie.getSource( ) == tritanomaly )
//	        	filterNumber = 5;

	  		repaint();

	   }
	 }

	
	 public void paint(Graphics g) {  
	        super.paint(g);
	        g.setColor(getBackground());
	        
	        g.setColor(Color.ORANGE);

	        
	        g.fillRect((int) (screenWidth/4)- 312, 150, 350, 350);
	        g.fillRect((int) (screenWidth/4) +63, 150, 350, 350);
	        g.fillRect((int) (screenWidth/4) +438, 150, 350, 350);
	        g.fillRect((int) (screenWidth/4) +813, 150, 350, 350);
	        
	        g.setColor(Color.BLACK);
	        g.fillRect((int) (screenWidth/4)- 307, 155, 340, 340);
	        g.fillRect((int) (screenWidth/4)+68, 155, 340, 340);
	        g.fillRect((int) (screenWidth/4) +443, 155, 340, 340);
	        g.fillRect((int) (screenWidth/4) +818, 155, 340, 340);
	        
	      //Resize images with the correct aspect ratio
	  		int new_height = height;
	  		int new_width = width;
	  		
	  		// Try-catch ensures program does not crash upon entry!
	  		try {
	    		//  Block of code to try
		        width = imagePreview.getWidth();// 
		  		height = imagePreview.getHeight();// 

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
	  
		    imageColorBlindPreview = Filters.filterImage(imagePreview, Filters.FILTERS.get(filterNumber));
		    // imageReColored = Filters.FILTERS.get(0).colorCorrectedProtanopia(imagePreview);
		    imageReColored = Filters.colorCorrectedFilter(imagePreview, Filters.FIXEDFILTERS.get(filterNumber));
			imageOutputFilter = Filters.filterImage(imageReColored, Filters.FILTERS.get(filterNumber));
	  		
	     
			g.drawImage(imagePreview ,((150)-(width/2))+50,((150)-(new_height/2))+175,width, new_height,this);
			g.drawImage(imageColorBlindPreview ,((150)-(width/2))+425,((150)-(new_height/2))+175,width, new_height,this);
			g.drawImage(imageReColored ,((150)-(width/2))+800,((150)-(new_height/2))+175,width, new_height,this);
			g.drawImage(imageOutputFilter ,((150)-(width/2))+1175,((150)-(new_height/2))+175,width, new_height,this);
	  		}
	  	
	  	catch(Exception e) {
	  	  //  Block of code to handle errors
	  		System.out.println("Please upload an image to display output.");
	  	}
			

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
	    
		JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		 File fileSelected = null;
		fileChooser.setDialogTitle("Select an image");
		fileChooser.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPEG", "jpeg", "jpg", "png", "bmp", "gif");
		fileChooser.addChoosableFileFilter(filter);


		int returnValue = fileChooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			System.out.println(fileChooser.getSelectedFile().getPath());
	         fileSelected = fileChooser.getSelectedFile();
		}
		return fileSelected;
	}
	
	public static void main(String[] args) {  
		
		new Main();  
		
	}
}  


