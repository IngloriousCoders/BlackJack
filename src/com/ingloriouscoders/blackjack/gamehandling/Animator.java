package com.ingloriouscoders.blackjack.gamehandling;

import com.ingloriouscoders.blackjack.card.Card;

public class Animator {
	public static int MOVE = 0, TURN = 1;
	public static int ANIMATIONS_PENDING = 0;
	
	public static void animateCard(final Card card, final int animation, final int duration, final int delay, final CardCanvas canvas, final int owner) { //owner: Bank=0, Human=1
		card.ANIMATION = animation;
		ANIMATIONS_PENDING++;
		
		Thread thread = new Thread() {
		    @Override
		    public void run() {
		    	if(owner == 0 && animation == Animator.TURN) {
		    		try {
		    			Thread.sleep(200);
		    			if(!canvas.USER_ENDED_GAME) {
		    				Thread.sleep(2500);
		    			}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    	}
		    	
		    	try {
					Thread.sleep(delay);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    	
		    	for(int i=0; i<duration; i++) {
		    		//card.ITERATOR = (float) (i * 1.0 / duration);
		    		card.ITERATOR = (float) (Math.sin(((i * 1.0 * 1.5) / duration)));
		    		
		    		if(owner == 0 && animation == Animator.TURN)
		    			card.ANIMATION = Animator.TURN;
		    		
		    		try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    		canvas.repaint();
		    	}
		    	
		    	if(owner == 1 && animation == Animator.MOVE) {
			    	card.ANIMATION = Animator.MOVE;
			    	card.ITERATOR = 1.0f;
		    		
		    		try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    		Animator.animateCard(card, Animator.TURN, 1000, 0, canvas, 1);
		    	} else if(owner == 0 && animation == Animator.TURN) {
		    		canvas.DRAW_BANK_SUM = true;
		    		canvas.repaint();
		    		try {
						Thread.sleep(canvas.blackjackmain.mgr.getBankPlayer().getCards().size() * 500 + 1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    		
		    		if(canvas.GAME_IS_BEING_ENDED)
		    			canvas.blackjackmain.endGamePostAnimation();
		    		
		    		canvas.GAME_IS_BEING_ENDED = false;
		    	} else {
			    	card.ANIMATION = -1;
			    	card.ITERATOR = 0.0f;
		    	}
		    	if(owner == 1 && animation == Animator.TURN) {
		    		canvas.UPDATE_SUM = true;
		    		canvas.repaint();
		    	}
		    }
		};
		thread.start();
		
		ANIMATIONS_PENDING--;
	}
}
