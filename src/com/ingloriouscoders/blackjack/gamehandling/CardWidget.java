package com.ingloriouscoders.blackjack.gamehandling;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

import com.ingloriouscoders.blackjack.card.Card;

import java.io.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;



public class CardWidget {
  public static int CARD_WIDTH = 100, CARD_HEIGHT = 150;
  public static int SYMBOL_WIDTH = 30, SYMBOL_HEIGHT = 30;
  public static BufferedImage herz, karo, pik, kreuz;
  public static BufferedImage herzFlipped, karoFlipped, pikFlipped, kreuzFlipped;
  public static BufferedImage cardImage, cardBack, background;
  public static BufferedImage backside;
  
  public static void initImages() {
    try {
      cardImage = ImageIO.read(new File("res/CardColors.png"));
      cardBack = ImageIO.read(new File("res/Rueckseite.png"));
      background = ImageIO.read(new File("res/Hintergrund.png"));
    } catch (IOException e) {
      System.err.println("The card image file could not be loaded. Check if all files exist.");
      e.printStackTrace();
    }
    
    kreuz = resize(cardImage.getSubimage(0, 0, 55, 52), SYMBOL_WIDTH, SYMBOL_HEIGHT);
    karo = resize(cardImage.getSubimage(53, 0, 47, 52), SYMBOL_WIDTH, SYMBOL_HEIGHT);
    herz = resize(cardImage.getSubimage(0, 51, 52, 49), SYMBOL_WIDTH, SYMBOL_HEIGHT);
    pik = resize(cardImage.getSubimage(53, 51, 47, 49), SYMBOL_WIDTH, SYMBOL_HEIGHT);
    
    kreuzFlipped = flipVertically(kreuz);
    karoFlipped = flipVertically(karo);
    herzFlipped = flipVertically(herz);
    pikFlipped = flipVertically(pik);
  }

  public static BufferedImage getImageForCard(Card card) {
    BufferedImage result = new BufferedImage(CARD_WIDTH, CARD_HEIGHT, BufferedImage.TYPE_INT_ARGB);
    Graphics g = result.getGraphics();
    
    g.setColor(Color.WHITE);
    g.fillRoundRect(0, 0, CARD_WIDTH, CARD_HEIGHT, 10, 10);
    
    g.setColor(Color.BLACK);
    g.drawRoundRect(0, 0, CARD_WIDTH - 1, CARD_HEIGHT - 1, 10, 10);
    
    drawText(Card.getSymbolString(card.getSymbol()), 20, Color.darkGray, (CARD_WIDTH/2) - 5, (CARD_HEIGHT/2), g);

    switch(card.getColor()) {
      case Card.CardColor.KREUZ:
           g.drawImage(kreuz, 10, 10, null);
           g.drawImage(kreuzFlipped, CARD_WIDTH - SYMBOL_WIDTH - 10, CARD_HEIGHT - SYMBOL_HEIGHT - 10, null);
           break;
      case Card.CardColor.KARO:
           g.drawImage(karo, 10, 10, null);
           g.drawImage(karoFlipped, CARD_WIDTH - SYMBOL_WIDTH - 10, CARD_HEIGHT - SYMBOL_HEIGHT - 10, null);
           break;
      case Card.CardColor.HERZ:
           g.drawImage(herz, 10, 10, null);
           g.drawImage(herzFlipped, CARD_WIDTH - SYMBOL_WIDTH - 10, CARD_HEIGHT - SYMBOL_HEIGHT - 10, null);
           break;
      case Card.CardColor.PIK:
           g.drawImage(pik, 10, 10, null);
           g.drawImage(pikFlipped, CARD_WIDTH - SYMBOL_WIDTH - 10, CARD_HEIGHT - SYMBOL_HEIGHT - 10, null);
           break;
    }

    g.dispose();
    
    return result;
  }
  
  public static void drawText(String txt, int size, Color c, int x, int y, Graphics g) {
    g.setColor(c);
    Font font = new Font("Arial", Font.BOLD, size);
    g.setFont(font);
    g.drawString(txt,x,y);
  }
  
  public static BufferedImage resize(BufferedImage start, int width, int height) {
	BufferedImage scaledImage = null;
	  
	try {
		if(width > 0 && height > 0)
			scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		else {
			System.err.println("Error: Width (" + width + ") and height (" + height + ") of the destinated size must both be bigger than 0.");
			return null;
		}
	
	    Graphics2D g = scaledImage.createGraphics();
	    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g.drawImage(start, 0, 0, width, height, null);
	
	    g.dispose();
	} catch(Exception e) {
		System.err.println("Something went wrong while resizing an image.");
		e.printStackTrace();
	}
    
    return scaledImage;
  }
  
  public static BufferedImage flipVertically(BufferedImage start) {
    AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
    tx.translate(0, -start.getHeight(null));
    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
    start = op.filter(start, null);
    
    return start;
  }
}