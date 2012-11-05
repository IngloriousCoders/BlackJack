package com.ingloriouscoders.blackjack.card;

public class Card
{
      public static class CardColor {
             public static final int HERZ = 0;
             public static final int KARO = 1;
             public static final int PIK = 2;
             public static final int KREUZ = 3;
             
             public static final int maxIndex = 3;
             public static final int startIndex = 0;
      }
      
      public static class CardSymbol {
    	public static final int TWO=0;
    	public static final int THREE=1;
    	public static final int FOUR=2;
    	public static final int FIVE=3;
    	public static final int SIX=4;
    	public static final int SEVEN=5;
    	public static final int EIGHT=6;
    	public static final int NINE=7;
    	public static final int TEN=8;
    	public static final int J=9;
    	public static final int Q=10;
    	public static final int K=11;
    	public static final int A=12;
        
        public static final int maxIndex = 12;
        public static final int startIndex = 0;
      }
      
      private int mColor;
      private int mSymbol;
      private int mValue;
      
      public float ITERATOR = 0;
      public int ANIMATION = -1;

      public Card(int _color, int cardSymbol) {
         mColor = _color;
         mSymbol = cardSymbol;

         switch ( mSymbol ) {
	          case CardSymbol.TWO:
	               this.mValue = 2;
	               break;
	          case CardSymbol.THREE:
	               this.mValue = 3;
	               break;
	          case CardSymbol.FOUR:
	               this.mValue = 4;
	               break;
	          case CardSymbol.FIVE:
	               this.mValue = 5;
	               break;
	          case CardSymbol.SIX:
	               this.mValue = 6;
	               break;
	          case CardSymbol.SEVEN:
	               this.mValue = 7;
	               break;
	          case CardSymbol.EIGHT:
	               this.mValue = 8;
	               break;
	          case CardSymbol.NINE:
	               this.mValue = 9;
	               break;
	          case CardSymbol.TEN:
	               this.mValue = 10;
	               break;
	          case CardSymbol.J:
	               this.mValue = 10;
	               break;
	          case CardSymbol.Q:
	               this.mValue = 10;
	               break;
	          case CardSymbol.K:
	               this.mValue = 10;
	               break;
	          case CardSymbol.A:
	               this.mValue = 11;
	               break;
	          default:
	               this.mValue = 0;
	               break;
         }  
      }
      
      public int getValue() {
       return mValue;
      }
      public int getColor() {
       return mColor;
      }
      public int getSymbol() {
       return mSymbol;
      }
      
      public static String getSymbolString(int _symbol) {
        switch ( _symbol ) {
	          case CardSymbol.TWO:
	               return "2";
	          case CardSymbol.THREE:
	               return "3";
	          case CardSymbol.FOUR:
	               return "4";
	          case CardSymbol.FIVE:
	               return "5";
	          case CardSymbol.SIX:
	               return "6";
	          case CardSymbol.SEVEN:
	               return "7";
	          case CardSymbol.EIGHT:
	               return "8";
	          case CardSymbol.NINE:
	               return "9";
	          case CardSymbol.TEN:
	               return "10";
	          case CardSymbol.J:
	               return "J";
	          case CardSymbol.Q:
	               return "Q";
	          case CardSymbol.K:
	               return "K";
	          case CardSymbol.A:
	               return "A";
	          default:
	               return "INVALID";
        	}
      }
      
      public static String getColorString(int _color) {
        switch (_color) {
	          case CardColor.HERZ:
	               return "Herz";
	          case CardColor.KARO:
	               return "Karo";
	          case CardColor.PIK:
	               return "Pick";
	          case CardColor.KREUZ:
	               return "Kreuz";
	          default:
	               return "INVALID";
        }
      }
}