package com.ingloriouscoders.blackjack.gamehandling;

import java.awt.*;
import javax.swing.*;

import com.ingloriouscoders.blackjack.card.GameManager;

public class BlackJackMain extends JFrame {
	private static final long serialVersionUID = 1L;
	
	JPanel cardFlow = new JPanel();
	public GameManager mgr;
  
	CardCanvas cardCanvas = new CardCanvas(this);

	
	public BlackJackMain(int width, int height) {
         super("17 und 4");
         setResizable(false);
         this.setSize(width, height);
         
         cardCanvas.WIDTH = width;
         cardCanvas.HEIGHT = height;


         //Main Container:
         Container cp = this.getContentPane();
         cardFlow.add(cardCanvas);
         cp.add(cardCanvas, BorderLayout.CENTER);

         setLocationRelativeTo(null);
         setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

         setVisible(true);


         //Bilddaten laden:
         CardWidget.initImages();
	}

	public void startGame() {
       resetGame();
	}
	
	public void setupGame() {
         mgr = new GameManager();
         mgr.setOnNewCardListener(cardCanvas);
         mgr.setOnGameEndedListener( new GameManager.onGameEndedListener() {
            public void onGameEnded(GameManager _mgr) {
              endGame();
            }
         });
	}
	
	public void resetGame() {
	    setupGame();
	    cardCanvas.clear();
	    cardCanvas.GAME_ENDED = false;
	    cardCanvas.DRAW_BANK_SUM = false;
	    cardCanvas.HUMAN_SUM = 0;
	    cardCanvas.USER_ENDED_GAME = false;
	    
	    cardCanvas.showDialog("Neues Spiel gestartet!");
	}
	
	public void aufdecken() {
	    if (mgr.userEndGame()) // userEndGame() gibt true zurück wenn das Spiel momentan nicht schon zu Ende ist
	    {
	    	endGame();
	    }
	}

	public void endGame() {
	     cardCanvas.rotateBankCards();
	}
	
	public void endGamePostAnimation() {
		cardCanvas.GAME_ENDED = true;
		cardCanvas.repaint();
		
	     switch ( mgr.getGameState() ) {
	       case GameManager.GameState.GAME_ENDED_PLAYER_MORE_21:
	           cardCanvas.showDialog("Sie haben verloren, da Sie mehr als 21 Punkte auf der Hand haben.");
	           cardCanvas.BANK_VICTORIES++;
	           break;
	       case GameManager.GameState.GAME_ENDED_PLAYER_EXACT_21:
	           cardCanvas.showDialog ("Sie haben gewonnen, Sie haben genau 21 Punkte auf der Hand.");
	           cardCanvas.HUMAN_VICTORIES++;
	           break;
	       case GameManager.GameState.GAME_ENDED_PLAYER_LESS_21_BANK_MORE:
	           cardCanvas.showDialog ("Sie haben gewonnen, da die Bank 21 Punkte bereits überschritten hat.");
	           cardCanvas.HUMAN_VICTORIES++;
	           break;
	       case GameManager.GameState.GAME_ENDED_PLAYER_LESS_21_PLAYER_CLOSER:
	           cardCanvas.showDialog ("Sie haben gewonnen, da Sie näher an den 21 Punkten liegen.");
	           cardCanvas.HUMAN_VICTORIES++;
	           break;
	       case GameManager.GameState.GAME_ENDED_PLAYER_LESS_21_BANK_CLOSER:
	           cardCanvas.showDialog ("Sie haben verloren, da die Bank näher an den 21 Punkten liegt.");
	           cardCanvas.BANK_VICTORIES++;
	           break;
	       case GameManager.GameState.GAME_ENDED_PLAYER_EQUALS_BANK:
	           cardCanvas.showDialog ("Das Spiel ist unentschieden.");
	           break;    
	       default:
	           break;
	     }
	}
}