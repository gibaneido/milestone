package br.com.projeto.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Teste {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		   
		         int chunkWidth, chunkHeight;  
		         int type;  
		         //fetching image files  
		         File[] imgFiles = new File[2];  
		         imgFiles[0] = new File("/temp/post.jpg");
		         imgFiles[1] = new File("/temp/fashion.png");
		   
		        //creating a bufferd image array from image files  
		         BufferedImage[] buffImages = new BufferedImage[2];  
	             try {
					buffImages[0] = ImageIO.read(imgFiles[0]);
					buffImages[1] = ImageIO.read(imgFiles[1]);
				} catch (IOException e) {
					e.printStackTrace();
				}
		         type = buffImages[0].getType();  
		         chunkWidth = buffImages[0].getWidth();  
		         chunkHeight = buffImages[0].getHeight();  
		   
		         //Initializing the final image  
		         BufferedImage finalImg = new BufferedImage(chunkWidth, chunkHeight, type);  
		   
		         finalImg.createGraphics().drawImage(buffImages[0], 0, 0, null);
		         finalImg.createGraphics().drawImage(buffImages[1], 0, 0, null);
		         System.out.println("Image concatenated.....");  
		         try {
					ImageIO.write(finalImg, "jpeg", new File("/temp/finalImg.jpg"));
				} catch (IOException e) {
					e.printStackTrace();
				}
	}

}
