import java.util.ArrayList;
import java.util.Random;

///////////////////////// TOP OF FILE COMMENT BLOCK ////////////////////////////
//
// Title: Blackjack Simulation
// Course: Independent Project, Summer 2020
//
// Author: Ben Milas
// Email: bmilas@wisc.edu
//
///////////////////////////////// CITATIONS ////////////////////////////////////
//
/////////////////////////////// 80 COLUMNS WIDE ////////////////////////////////
public class Deck {
    private ArrayList<Card> cards;
    public static int numAces = 0;
    
    public Deck() {
        cards = new ArrayList<Card>();
        for (int value = 1; value <= 13; value++) {
            for (int suit = 0; suit <= 3; suit++) {
                cards.add(new Card(value, suit));
            }
        }
    }

    public Card drawRandomCard(ArrayList<Card> whichHand) {
        Random generator = new Random();
        int index = generator.nextInt(cards.size());
        whichHand.add(cards.get(index));
        return cards.remove(index);
    }

    public static String getHand(ArrayList<Card> whichHand) {
        String hand = "";
        for (int i = 0; i < whichHand.size() - 1; i++) {
            hand += whichHand.get(i) + ", ";
        }
        if (hand.length() >= 2) {
            hand = hand.substring(0, hand.length() - 2);
        }
        if (whichHand.size() > 1) {
            hand += " and ";
        }
        hand += whichHand.get(whichHand.size() - 1);
        return hand;
    }
    
    public static String getDrawnCard(ArrayList<Card> whichHand) {
        String drawnCard = "";
        drawnCard += whichHand.get(whichHand.size() - 1);
        return drawnCard;
    }
    
    public static void addDrawnCard(ArrayList<Card> whichHand) {
        whichHand.add(Catch21Simulation.dealtCard.get(Catch21Simulation.dealtCard.size() - 1));
    }
    
    public static int getNumAces(ArrayList<Card> whichHand) {
        numAces = 0;
        for (int i = 0; i < whichHand.size(); i++) {
            if (whichHand.get(i).getValue() == 1) {
                numAces++;
            }
        }
        return numAces;
    }
    public static int getHandValue(ArrayList<Card> whichHand) {
        int handValue = 0;
        for (int i = 0; i < whichHand.size(); i++) {
            handValue += whichHand.get(i).getValue();
        }
        if (getNumAces(whichHand) >= 1 && (handValue + 10 <= 21)) {
            handValue += 10;
        }
        return handValue;
    }
}
