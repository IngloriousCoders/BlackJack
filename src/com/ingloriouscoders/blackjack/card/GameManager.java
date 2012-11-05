package com.ingloriouscoders.blackjack.card;



public class GameManager
{
      public Card[] cardList = new Card[52];
      private int currentCardIndex = 0;
      
      private Player mBankPlayer;
      private Player mHumanPlayer;
      
      private boolean gameEnded = false;
      
      private int step_count = 0;

      public static class GameState {         
    	 public static final int GAME_RUNNING = 0;
    	 public static final int GAME_ENDED_PLAYER_MORE_21 = 1;
    	 public static final int GAME_ENDED_PLAYER_EXACT_21 = 2;
    	 public static final int GAME_ENDED_PLAYER_LESS_21_BANK_MORE = 3;
    	 public static final int GAME_ENDED_PLAYER_LESS_21_PLAYER_CLOSER = 4;
    	 public static final int GAME_ENDED_PLAYER_LESS_21_BANK_CLOSER = 5;
    	 public static final int GAME_ENDED_PLAYER_EQUALS_BANK = 6;
         
    	 public static final int maxIndex = 5;
    	 public static final int startIndex = 0;    
      };

      public static interface onNewCardListener {
        void onNewCard(GameManager gameMgr, Player _player);
      }
      
      public static interface onGameEndedListener {
        void onGameEnded(GameManager gameMgr);
      }

      private onNewCardListener mOnNewCardListener;
      private onGameEndedListener mOnGameEndedListener;

      public GameManager() {
        int iterations = 0;
        for(int i=Card.CardColor.startIndex;i<=Card.CardColor.maxIndex;i++) {
          for(int j=Card.CardSymbol.startIndex;j<=Card.CardSymbol.maxIndex;j++) {
            cardList[iterations] = new Card(i, j);
            iterations++;
          }
        }
        
        randomizeCardList();
        
        // Bankspieler und Spieler erzeugen
        
        
        mBankPlayer = new Player("Bank");
        mHumanPlayer = new Player("Human");
      }
      
      private void randomizeCardList() {
        int randomNumber = 0;
        Card currentCard = null;
        for(int u=0; u<cardList.length; u++) {
          randomNumber = (int) (Math.random()*51);
          currentCard = cardList[randomNumber];
          cardList[randomNumber] = cardList[u];
          cardList[u] = currentCard;
        }
      }
      
      public void setOnNewCardListener(onNewCardListener _listener) {
        mOnNewCardListener = _listener;
      }
      public void setOnGameEndedListener(onGameEndedListener _listener) {
        mOnGameEndedListener = _listener;
      }
      private void callOnNewCardListener(Player _player) {
         if (mOnNewCardListener != null) {
            mOnNewCardListener.onNewCard(this,_player);
         }
      }
      private void callOnGameEndedListener() {
         gameEnded = true;
         System.out.println("Spiel hat geendet!");
         if (mOnGameEndedListener != null) {
            mOnGameEndedListener.onGameEnded(this);
         }
      }
      
      public void nextStep() {
    	  	step_count++;
    	  	if (currentCardIndex >= 51) {
               callOnGameEndedListener();
               return;
            }
      
            mBankPlayer.addCard(cardList[currentCardIndex]);
            mHumanPlayer.addCard(cardList[currentCardIndex+1]);
            callOnNewCardListener(mHumanPlayer);
            
            currentCardIndex += 2;
            

            
            if ( mHumanPlayer.getSum() >= 21 ) {
              callOnGameEndedListener();
            }
            
      }

      public Player getBankPlayer() {
        return mBankPlayer;
      }
      public Player getHumanPlayer() {
        return mHumanPlayer;
      }
      
      public int getGameState() {
         if ( gameEnded ) {
           Player humanPlayer = this.getHumanPlayer();
           Player bankPlayer = this.getBankPlayer();
           
           if (humanPlayer.getSum() == 21)
               return GameState.GAME_ENDED_PLAYER_LESS_21_BANK_MORE;

           if(humanPlayer.getSum() == bankPlayer.getSum() && humanPlayer.getSum() < 21)
        	 return GameState.GAME_ENDED_PLAYER_EQUALS_BANK;

           if (humanPlayer.getSum() > 21 )
             return GameState.GAME_ENDED_PLAYER_MORE_21;



           else if (humanPlayer.getSum() < 21 ) {
             int humanDelta = 21 - humanPlayer.getSum();
             int bankDelta = 21 - bankPlayer.getSum();
             if ( bankDelta < 0 )
                return GameState.GAME_ENDED_PLAYER_LESS_21_BANK_MORE;

             else if ( humanDelta < bankDelta )
                return GameState.GAME_ENDED_PLAYER_LESS_21_PLAYER_CLOSER;

             else if ( humanDelta > bankDelta )
                return GameState.GAME_ENDED_PLAYER_LESS_21_BANK_CLOSER;

             else
                return GameState.GAME_RUNNING;
           }
           else {
               return GameState.GAME_RUNNING;
           }
         }
         else {
              return GameState.GAME_RUNNING;
         }

      }
      
      public boolean userEndGame() 
      {
    	  if (gameEnded != true && step_count > 0)
    	  {
    		  gameEnded = true;
    		  return true;
    	  }
    	  return false;
      }
      
      public int getStepCount()
      {
    	  return step_count;
      }
}