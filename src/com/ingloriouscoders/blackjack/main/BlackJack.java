package com.ingloriouscoders.blackjack.main;

import com.ingloriouscoders.blackjack.gamehandling.BlackJackMain;

public class BlackJack {
	
	/** BlackJack von Luca Beurer-Kellner und Jan Tagscherer
	 *  2012
	 */
	
	public static boolean DEBUG = false;
	
	  public static void main(String[] args) {
	    BlackJackMain blackjack = new BlackJackMain(1024, 768);
	    blackjack.setupGame();
	    blackjack.startGame();
	  }
}