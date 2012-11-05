package com.ingloriouscoders.blackjack.card;

import java.util.*;

import java.util.ArrayList;


public class Player
{
    private String mName;
    private List<Card> mCards = new ArrayList<Card>();
    
    public Player(String _name)
    {
      mName = _name;
    }
    public String getName()
    {
      return mName;
    }
    public void addCard(Card _card)
    {
       this.mCards.add(_card);
    }
    public List<Card> getCards()
    {
       return mCards;
    }
    public int getSum()
    {
      int sum = 0;
      for ( Card currentCard : mCards )
      {
         sum += currentCard.getValue();
      }
      return sum;
    }

}