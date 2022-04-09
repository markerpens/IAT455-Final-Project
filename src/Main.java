import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.String; 
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Graphics;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.net.URL;

public class Main extends JFrame{//inheriting JFrame  
	JFrame frame; 
	BufferedImage imagePreview;
	BufferedImage imageColorBlindPreview;
	BufferedImage imageReColored;
	BufferedImage imageOutputFilter;
	JRadioButton protanopia = new JRadioButton();
	JRadioButton deuteranopia = new JRadioButton(); 
	JRadioButton tritanopia = new JRadioButton();
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	JTextArea imageURLString = new JTextArea("");
	
	float screenWidth = screenSize.width;
	float screenHeight = screenSize.height;

	int filterNumber;
	int width; //width of images
	int height; //height of images
	
	Main(){  
		
		pack();
		
		getContentPane().setBackground(Color.BLACK);
		
		JButton uploadBtn=new JButton("Upload From Files");//create button  
		
		uploadBtn.setBounds(screenSize.width/2-100,screenSize.height-450,200, 40);  
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

		 RadioButtonHandler colorBlindButtonHandler = new RadioButtonHandler( );
		 protanopia.addItemListener( colorBlindButtonHandler );
		 deuteranopia.addItemListener( colorBlindButtonHandler );
		 tritanopia.addItemListener( colorBlindButtonHandler );

		
		JButton urlUploadBtn=new JButton("Upload From URL");//create button  
		urlUploadBtn.setBounds(screenSize.width/2,screenSize.height-385,160, 30);  
		urlUploadBtn.setBackground(Color.WHITE); 
		urlUploadBtn.setForeground(Color.BLACK); 
		urlUploadBtn.setBorderPainted(false); 
		urlUploadBtn.setOpaque(true);
		add(urlUploadBtn);//adding button on frame  
		
		JButton downloadImage=new JButton("Save Image");//create button  
		downloadImage.setBounds(screenSize.width/2 - 80,screenSize.height-300,160, 30);  
		downloadImage.setBackground(Color.WHITE); 
		downloadImage.setForeground(Color.BLACK); 
		downloadImage.setBorderPainted(false); 
		downloadImage.setOpaque(true);
		add(downloadImage);//adding button on frame  
		
		
		
		imageURLString.setBounds(screenSize.width/2-220,screenSize.height-380,200,20);
		add(imageURLString);
		
		JLabel Label1,Label2,Label3,Label4,Label5;  
		Label1=new JLabel("Original Image:");  
		Label1.setBounds((int) (screenWidth/2)- 750,50, 100,30);  
		Label1.setForeground(Color.WHITE);

		
		Label2=new JLabel("Color Blind POV:");  
		Label2.setBounds((int) (screenWidth/2)- 375,50, 150,30);  
		Label2.setForeground(Color.WHITE);

		
		Label3=new JLabel("Recolored:");  
		Label3.setBounds((int) (screenWidth/2)+ 25,50, 100,30); 
		Label3.setForeground(Color.WHITE);

		
		Label4=new JLabel("Recolored POV:");  
		Label4.setBounds((int) (screenWidth/2)+ 400,50, 100,30); 
		Label4.setForeground(Color.WHITE);
		

		Label5=new JLabel("URL:");  
		Label5.setBounds((int) (screenSize.width/2) -220 ,screenSize.height-410, 100,30); 
		Label5.setForeground(Color.WHITE);

	    add(Label1); 
	    add(Label2);
	    add(Label3);  
	    add(Label4);  
	    add(Label5);  



		setSize(screenSize.width,screenSize.height);
		setLayout(null);  
		setVisible(true);  
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        uploadBtn.addActionListener(new ActionListener(){  
        	public void actionPerformed(ActionEvent e){  
        		uploadBtn();        	        
    		}  
	    });  
        
        urlUploadBtn.addActionListener(new ActionListener(){  
        	public void actionPerformed(ActionEvent e){  
        		URLUpload();        	        
    		}  
	    }); 
        downloadImage.addActionListener(new ActionListener(){  
        	public void actionPerformed(ActionEvent e){  
        		imageDownload();        	        
    		}  
	    }); 
	}  
	
//	Updates the image based on the radio button that is selected
	private class RadioButtonHandler implements ItemListener
	 {
	  public void itemStateChanged( ItemEvent ie ){
		  
	        if ( ie.getSource( ) == protanopia )
	        	filterNumber = 0;
	        else if ( ie.getSource( ) == deuteranopia )
	        	filterNumber = 1;
	        else if ( ie.getSource( ) == tritanopia )
	        	filterNumber = 2;
	  		repaint();

	   }
	 }

	
	 public void paint(Graphics g) {  
	        super.paint(g);
	        g.setColor(getBackground());
	        
	        g.setColor(Color.ORANGE);

	        
	        g.fillRect((int) (screenWidth/2)- 750, 150, 350, 350);
	        g.fillRect((int) (screenWidth/2)- 375, 150, 350, 350);
	        g.fillRect((int) (screenWidth/2)+ 25, 150, 350, 350);
	        g.fillRect((int) (screenWidth/2)+ 400, 150, 350, 350);
	        
	        g.setColor(Color.BLACK);
	        g.fillRect((int) (screenWidth/2)- 745, 155, 340, 340);
	        g.fillRect((int) (screenWidth/2)- 370, 155, 340, 340);
	        g.fillRect((int) (screenWidth/2)+ 30, 155, 340, 340);
	        g.fillRect((int) (screenWidth/2)+ 405, 155, 340, 340);
	        
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
	  		
	     
			g.drawImage(imagePreview ,((int) (screenWidth/2)- 745)+(170-(width/2 )),((150)-(new_height/2))+175,width, new_height,this);
			g.drawImage(imageColorBlindPreview ,((int) (screenWidth/2)- 370)+(170-(width/2 )),((150)-(new_height/2))+175,width, new_height,this);
			g.drawImage(imageReColored ,((int) (screenWidth/2)+ 30)+(170-(width/2 )),((150)-(new_height/2))+175,width, new_height,this);
			g.drawImage(imageOutputFilter ,((int) (screenWidth/2)+ 405) +(170-(width/2 )),((150)-(new_height/2))+175,width, new_height,this);
	  		}
	  	
	  	catch(Exception e) {
	  	  //  Block of code to handle errors
	  		System.out.println("Please upload an image to display output.");
	  	}
			

	    }  
	
	public void URLUpload() {
		try{
	         String imageURL = imageURLString.getText();
	         
	         System.out.println("Downloading File From: " + imageURL);
	         
	         URL imageUrl = new URL(imageURL);
	         imagePreview = ImageIO.read(imageUrl);;
	         System.out.println(imageURL);
	   		repaint();

	      } catch(Exception e) {
	         System.out.println("Exception: " + e.getMessage());
	      }
		
	}
	public void uploadBtn() {
	     try {
			imagePreview = ImageIO.read(chooseImage());
		    } catch (IOException ex) {
		        ex.printStackTrace();
		    }
	     System.out.println(filterNumber);
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
	
//	public void imageDownload() {
//		 JFileChooser getFile = new JFileChooser();
//         getFile.setCurrentDirectory(new File(System.getProperty("user.home")));
//         // Filter files
//         FileNameExtensionFilter filter1 = new FileNameExtensionFilter("*.Images", "jpg",
//                 "png");
//         getFile.addChoosableFileFilter(filter1);
//         int res = getFile.showSaveDialog(null);
//         if(res == JFileChooser.APPROVE_OPTION) {
//             selFile1 = getFile.getSelectedFile();
//             path1 = selFile1.getAbsolutePath();
//             label.setIcon(resize(path1));
//             System.out.println("1st selFile1 = " + selFile1);                    
//             try {
//                 ImageIO.write(imageReColored, "jpg", selFile1);
//             } catch (IOException ex) {
//             }
//         }
//	}
		
//		JFileChooser downloadChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
//		downloadChooser.setDialogTitle("Specify a file to save");   
//		 
//		int userSelection = downloadChooser.showOpenDialog(null);
//		 
//		if (userSelection == JFileChooser.APPROVE_OPTION) {
//		    File fileToSave = downloadChooser.getSelectedFile();
//		    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
//			try {
//				ImageIO.write(imageReColored,"jpg", fileToSave);
//			} catch (IOException e) {
//				  System.out.println("OOPSie");
//				  e.printStackTrace();
//			}
//
//		}

//		JFileChooser downloadChooser = new JFileChooser();
//		int returnVal = downloadChooser.showOpenDialog(null);
//		if ( returnVal == JFileChooser.APPROVE_OPTION ){
//		    File file = downloadChooser.getSelectedFile();
//		    try {
//				ImageIO.write(imageReColored,"jpg", file);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
			
	
	//}
	
	public static void main(String[] args) {  
		
		new Main();  
		
	}
}  


