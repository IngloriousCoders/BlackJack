package com.ingloriouscoders.blackjack.gamehandling;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.*;

import com.ingloriouscoders.blackjack.card.Card;
import com.ingloriouscoders.blackjack.card.GameManager;
import com.ingloriouscoders.blackjack.card.Player;
import com.ingloriouscoders.blackjack.main.BlackJack;


public class CardCanvas extends JComponent implements GameManager.onNewCardListener, MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 1L;
	
	private FontMetrics fm;
	private Font serif_status = new Font ("Serif", Font.PLAIN, 20);

	public BlackJackMain blackjackmain = null;
	GameManager mgr;
	  
	private boolean showDialog = false;
	private String dialog_message = "";
	
	public int WIDTH, HEIGHT;
	public boolean GAME_ENDED, GAME_IS_BEING_ENDED, USER_ENDED_GAME;
	public boolean UPDATE_SUM = false, DRAW_BANK_SUM = false;
	public int HUMAN_SUM = 0;
	
	public int BANK_VICTORIES, HUMAN_VICTORIES; // Gehört eigentlich in die Player-Fachklasse
	
	boolean hoverNewGame = false, hoverFlip = false;
	  

	public CardCanvas(BlackJackMain blackjack) { 
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.blackjackmain = blackjack;
	}

	
	  //Zeichnen des Canvasinhalts:
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	
	    //Hintergrund:
	    g.setColor(Color.WHITE);
	    g.setFont(serif_status);
	    fm = g.getFontMetrics();
	    g.fillRect(0, 0, getWidth(), getHeight());
	    g.drawImage(CardWidget.background, 0, 0, null);
	    
	    //GUI:
	    if(hoverNewGame)
	    {
	    	g.setColor(Color.BLACK);
	    }
	    else
	    	g.setColor(Color.WHITE);
	    
	    
	    
	    g.drawRoundRect(WIDTH - 900, HEIGHT - 200, 200, 50, 10, 10);
	    g.drawString("Neues Spiel", WIDTH - 860, HEIGHT - 168);
	    
	    g.setFont(serif_status);
	    
	    if(hoverFlip)
	    {
	    	g.setColor(Color.BLACK);
	    }
	    else
	    	g.setColor(Color.WHITE);
	   
	    if (mgr == null || 
	    	mgr.getStepCount() <= 0 ||
	    	mgr.getGameState() != GameManager.GameState.GAME_RUNNING )
	    {
	    	g.setColor(Color.GRAY);
	    }
	    
	    g.drawRoundRect(WIDTH - 650, HEIGHT - 200, 200, 50, 10, 10);
	    g.drawString("Aufdecken", WIDTH - 600, HEIGHT - 168);
	    g.setColor(Color.WHITE);
	    
	    //Kartenstapel:
	    for(int i=0; i<3; i++) {
	    	g.drawImage(CardWidget.cardBack, WIDTH - 200 - (i*3), HEIGHT - 230 - (i*3), null);
	    }
	    
	    //Summe:
	    if(mgr != null) {
	    	if(UPDATE_SUM) {
	    		HUMAN_SUM = mgr.getHumanPlayer().getSum();
	    		UPDATE_SUM = false;
	    	}
	    	
	    	g.drawString("= " + HUMAN_SUM + " Punkte", mgr.getHumanPlayer().getCards().size() * (CardWidget.CARD_WIDTH + 10) + 110, 450);
	    	
	    	if(DRAW_BANK_SUM)
	    		g.drawString("= " + mgr.getBankPlayer().getSum() + " Punkte", mgr.getBankPlayer().getCards().size() * (CardWidget.CARD_WIDTH + 10) + 110, 175);
	    }
	    
	    //Punkte der Spieler:
    	g.setColor(Color.LIGHT_GRAY);
    	if(BANK_VICTORIES == 1)
    		g.drawString(BANK_VICTORIES + " Sieg", 150, 75);
    	else
    		g.drawString(BANK_VICTORIES + " Siege", 150, 75);
    	if(HUMAN_VICTORIES == 1)
    		g.drawString(HUMAN_VICTORIES + " Sieg", 150, 350);
    	else
    		g.drawString(HUMAN_VICTORIES + " Siege", 150, 350);
    	g.setColor(Color.WHITE);
	
	    //Karten:
	    g.drawString("Bank:", 75, 75);
	    g.drawString("Sie:", 75, 350);
	    if(mgr != null) {
	    	for(int i=0; i<mgr.getHumanPlayer().getCards().size(); i++) {
	    		Card card = mgr.getHumanPlayer().getCards().get(i);
	    		if(card.ANIMATION == -1)
	    			g.drawImage(CardWidget.getImageForCard(card), 100 + ((CardWidget.CARD_WIDTH+10) *i), 375, null);
    			else if(card.ANIMATION == Animator.MOVE) {
    				Dimension start = new Dimension(WIDTH - 209, HEIGHT - 239);
    				Dimension goal  = new Dimension(100 + ((CardWidget.CARD_WIDTH+10) * i), 375);
    				
    				g.drawImage(CardWidget.cardBack, (int) (goal.width + ((start.width - goal.width) * (1f - card.ITERATOR))), (int) (goal.height + ((start.height - goal.height) * (1f - card.ITERATOR))), null);
    			} else if(card.ANIMATION == Animator.TURN) {
    				BufferedImage resized = null;
    				
    				if(card.ITERATOR < 0.5f && (int) (CardWidget.CARD_WIDTH - (CardWidget.CARD_WIDTH * (card.ITERATOR * 2))) != 0)
    					resized = CardWidget.resize(CardWidget.cardBack, (int) (CardWidget.CARD_WIDTH - (CardWidget.CARD_WIDTH * (card.ITERATOR * 2))), CardWidget.CARD_HEIGHT);
    				else if(card.ITERATOR > 0.5f && (int) (CardWidget.CARD_WIDTH - (CardWidget.CARD_WIDTH * (card.ITERATOR * 2))) != 0)
    					resized = CardWidget.resize(CardWidget.getImageForCard(card), (int) (CardWidget.CARD_WIDTH - (CardWidget.CARD_WIDTH * ((-card.ITERATOR * 2) + 2))), CardWidget.CARD_HEIGHT);
    				
    				if(resized != null)
    					g.drawImage(resized, 100 + ((CardWidget.CARD_WIDTH+10) *i) - (resized.getWidth() / 2) + (CardWidget.CARD_WIDTH / 2), 375, null);
    			}
	    	}
	      
		    for(int i=0; i<mgr.getBankPlayer().getCards().size(); i++) {
		    	Card card = mgr.getBankPlayer().getCards().get(i);
		    	if(card.ANIMATION == -1) {
			    	if(GAME_ENDED)
			    		g.drawImage(CardWidget.getImageForCard(mgr.getBankPlayer().getCards().get(i)), 100 + ((CardWidget.CARD_WIDTH+10) *i), 100, null);
			    	else
			    		g.drawImage(CardWidget.cardBack, 100 + ((CardWidget.CARD_WIDTH+10) *i), 100, null);
		    	} else if(card.ANIMATION == Animator.MOVE) {
    				Dimension start = new Dimension(WIDTH - 209, HEIGHT - 239);
    				Dimension goal  = new Dimension(100 + ((CardWidget.CARD_WIDTH+10) *i), 100);
			    	
    				g.drawImage(CardWidget.cardBack, (int) (goal.width + ((start.width - goal.width) * (1f - card.ITERATOR))), (int) (goal.height + ((start.height - goal.height) * (1f - card.ITERATOR))), null);
		    	} else if(card.ANIMATION == Animator.TURN) {
					BufferedImage resized = null;
					
					if(card.ITERATOR < 0.5f && (int) (CardWidget.CARD_WIDTH - (CardWidget.CARD_WIDTH * (card.ITERATOR * 2))) != 0)
						resized = CardWidget.resize(CardWidget.cardBack, (int) (CardWidget.CARD_WIDTH - (CardWidget.CARD_WIDTH * (card.ITERATOR * 2))), CardWidget.CARD_HEIGHT);
					else if(card.ITERATOR > 0.5f && (int) (CardWidget.CARD_WIDTH - (CardWidget.CARD_WIDTH * (card.ITERATOR * 2))) != 0)
						resized = CardWidget.resize(CardWidget.getImageForCard(card), (int) (CardWidget.CARD_WIDTH - (CardWidget.CARD_WIDTH * ((-card.ITERATOR * 2) + 2))), CardWidget.CARD_HEIGHT);
					
					if(resized != null)
						g.drawImage(resized, 100 + ((CardWidget.CARD_WIDTH+10) *i) - (resized.getWidth() / 2) + (CardWidget.CARD_WIDTH / 2), 100, null);
				}
		    	
		    	if(BlackJack.DEBUG) {
			    	g.setColor(Color.cyan);
			    	g.drawString("Card " + i + ": " + card.ITERATOR + " ; " + card.ANIMATION, 50, 200 + i*50);
			    	
			    	g.drawString("User ended game: " + USER_ENDED_GAME, 50, 500);
			    	g.setColor(Color.white);
		    	}
		    }
	    }
	    
	    if ( showDialog ) {
	       /*g.setColor(dialog_color);
	       g.fillRect(0,(getHeight()/2)-dialog_height/2,getWidth(),dialog_height);*/
	       g.setFont(serif_status);
	       fm = g.getFontMetrics();
	       int string_width = fm.stringWidth(dialog_message);
	       drawText(dialog_message,serif_status, 20, Color.LIGHT_GRAY, (getWidth()/2)-(string_width/2), HEIGHT - 50, g);  //y = (getHeight()/2)-(fm.getHeight()/2)
	    }
	}

	public void drawText(String txt,Font _font, int size, Color c, int x, int y, Graphics g) {
		g.setColor(c);
		
		g.setFont(_font);
		g.drawString(txt,x,y);
	}
  
	public void onNewCard(GameManager mgr, Player player) {
		this.mgr = mgr;
		this.hideDialog();
		this.repaint();
	}
	  
	public void clear() {
		this.mgr = null;
		this.repaint();
	}
	
	public void showDialog(String msg) {
		this.dialog_message = msg;
		this.showDialog = true;
		this.repaint();
	}
	
	public void hideDialog() {
		this.showDialog = false;
		this.dialog_message = "";
		this.repaint(); 
	}


	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub


	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(BlackJack.DEBUG)
			System.out.println(arg0.getX() + " x " + arg0.getY());
		
		int mx = arg0.getX();
		int my = arg0.getY();
		
		if((mx > WIDTH - 209 && mx < WIDTH - 209 + CardWidget.CARD_WIDTH) && (my > HEIGHT - 239 && my < HEIGHT - 239 + CardWidget.CARD_HEIGHT) && !this.GAME_ENDED && !this.GAME_IS_BEING_ENDED) {
			blackjackmain.mgr.nextStep();

			Card animatedCard1 = blackjackmain.mgr.getHumanPlayer().getCards().get(blackjackmain.mgr.getHumanPlayer().getCards().size() - 1);
			Animator.animateCard(animatedCard1, Animator.MOVE, 1000, (int) (Math.random() * 100), this, 1);
			
			Card animatedCard2 = blackjackmain.mgr.getBankPlayer().getCards().get(blackjackmain.mgr.getBankPlayer().getCards().size() - 1);
			Animator.animateCard(animatedCard2, Animator.MOVE, 1000, (int) (Math.random() * 800), this, 0);
		}
		
		Rectangle newGame = new Rectangle(WIDTH - 900, HEIGHT - 200, 200, 50);
		Rectangle flip = new Rectangle(WIDTH - 650, HEIGHT - 200, 200, 50);
		
		if(hoverNewGame == true)
		{
			blackjackmain.startGame();
		}
		
		if(hoverFlip == true) {
			blackjackmain.aufdecken();
			USER_ENDED_GAME = true;
		}
		
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	public void rotateBankCards() {
		this.GAME_IS_BEING_ENDED = true;

		for(int i=0; i<blackjackmain.mgr.getBankPlayer().getCards().size(); i++) {
			Animator.animateCard(blackjackmain.mgr.getBankPlayer().getCards().get(i), Animator.TURN, 1000, i * 500, this, 0);
		}
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		Rectangle newGame = new Rectangle(WIDTH - 900, HEIGHT - 200, 200, 50);
		Rectangle flip = new Rectangle(WIDTH - 650, HEIGHT - 200, 200, 50);
		
		if(newGame.contains(e.getX(), e.getY())) {
			hoverNewGame = true;
			repaint();
		}
		else if(hoverNewGame = true) {
			hoverNewGame = false;
			repaint();
		}
		
		if(flip.contains(e.getX(), e.getY())) {
			hoverFlip = true;
			repaint();
		}
		else if(hoverFlip = true) {
			hoverFlip = false;
			repaint();
		}
		
		repaint();
	}
}