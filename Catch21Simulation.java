import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

///////////////////////// TOP OF FILE COMMENT BLOCK ////////////////////////////
//
// Title: Catch 21
// Course: Independent Project, Summer 2020
//
// Author: Ben Milas
// Email: bmilas@wisc.edu
//
///////////////////////////////// CITATIONS ////////////////////////////////////
//
/////////////////////////////// 80 COLUMNS WIDE ////////////////////////////////

// TODO: fix it so it doesn't prompt the user that they are bustable if a hand contains an ace and
// is soft

/**
 * A class that allows a user to simulate the final round of Catch 21, as seen on GSN.
 * 
 * @author Ben Milas
 */
public class Catch21Simulation {
    private static int numberOfChips = 3;
    private static int totalWinnings = 0;
    private static int numberOf21s = 0;
    private static boolean hypotheticalGameplay = false;
    private static Deck deck = new Deck();
    static ArrayList<Card> dealtCard = new ArrayList<Card>();
    static ArrayList<Card> hand1 = new ArrayList<Card>();
    static ArrayList<Card> hand2 = new ArrayList<Card>();
    static ArrayList<Card> hand3 = new ArrayList<Card>();
    static ArrayList<Card> prizeHand1 = new ArrayList<Card>();
    static ArrayList<Card> prizeHand2 = new ArrayList<Card>();
    static ArrayList<Card> prizeHand3 = new ArrayList<Card>();

    /**
     * Displays the rules and starts the game of Catch 21
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        gameStart();
    }

    /**
     * A method that initializes the starting hands and draws the first card that the player must decide what to do with.
     * @throws InterruptedException
     */
    public static void catch21Game() throws InterruptedException {
        Scanner scnr = new Scanner(System.in);
        Deck.numAces = 0;
        System.out.println("The cards are being dealt.\n");
        System.out.println(".");
        TimeUnit.MILLISECONDS.sleep(500);
        System.out.println("..");
        TimeUnit.MILLISECONDS.sleep(500);
        System.out.println("...\n");
        TimeUnit.MILLISECONDS.sleep(500);

        deck.drawRandomCard(hand1);
        deck.drawRandomCard(hand2);
        deck.drawRandomCard(hand3);

        System.out.println("Your first three cards are " + Deck.getHand(hand1) + ", "
            + Deck.getHand(hand2) + ", and " + Deck.getHand(hand3) + ".\n");
        drawNewCard();
        scnr.close();
    }

    /**
     * A method that checks whether a hand is bustable, or if they player has managed to get 21 in
     * all 3 hands.
     * @throws InterruptedException
     */
    public static void bustableCheck() throws InterruptedException {

        boolean isBustable = false;

        if (Deck.getHandValue(hand1) == 21 && Deck.getHandValue(hand2) == 21
            && Deck.getHandValue(hand3) == 21) {
            if (hypotheticalGameplay) { // hypothetical gameplay: "what if" scenario after player
                                        // decides to walk away
                System.out.println(
                    "Oh no! You would have won the $25000 if you had continued playing!\n");
                checkPrizeMoney(prizeHand1, prizeHand2, prizeHand3);
            } else
                checkPrizeMoney(hand1, hand2, hand3);
        }

        if (Deck.getHandValue(hand1) > 11 && Deck.getHandValue(hand2) > 11
            && Deck.getHandValue(hand3) > 11) {
            if (Deck.getHandValue(hand1) < 21 && Deck.getHandValue(hand2) < 21
                && Deck.getHandValue(hand3) < 21) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(
                    "You didn't catch 21 with any hands yet, so we're just going to play on.\n");
            } else
                isBustable = true;
        }
        if (isBustable) {
            if (!hypotheticalGameplay) {
                bustablePosition();
            } else {
                drawNewCard();
            }
        } else {
            drawNewCard();
        }
    }

    public static void gameStart() throws InterruptedException {
        Scanner scnr = new Scanner(System.in);
        System.out
            .println("Welcome to Catch 21! Would you like to read the rules? (type 'y' or 'n')\n");
        char response = scnr.next().charAt(0);
        while (response != 'y' && response != 'n') {
            System.out.println("Invalid response. (type 'y' or 'n')");
            response = scnr.next().charAt(0);
        }
        if (response == 'y') {
            System.out.println(
                "You control three hands at once and are trying to get a total of 21 in each hand.\n");
            TimeUnit.SECONDS.sleep(3);
            System.out.println(
                "If any hand busts, you lose. You have 3 powerchips that you can use at any time if you "
                    + "don't like a card that's been dealt.\n"
                    + "THIS DOES NOT COUNT AS A STOP CHIP. It only allows you to see the next card.\n");
            TimeUnit.SECONDS.sleep(7);
            System.out.println("If you Catch 21 in one hand, you'll win $2500.\n"
                + "If you Catch 21 in two hands, you'll win $5000.\nCatch 21 in all three hands and you'll win $25000!\n");
            TimeUnit.SECONDS.sleep(4);
        }
        System.out.println("Let's play!\n\n");
        TimeUnit.SECONDS.sleep(1);
        catch21Game();
        scnr.close();
    }

    /**
     * A method that prompts the user to make a decision when their hand is in a bustable position. 
     * @throws InterruptedException
     */
    public static void bustablePosition() throws InterruptedException {
        Scanner scnr = new Scanner(System.in);
        System.out.println(
            "--------------------------------------------------------------------------------\n");
        TimeUnit.SECONDS.sleep(2);
        System.out.println(
            "You are in a bustable position with money on the line.\nWould you like to 'continue' or 'walk' away?");
        if (scnr.hasNext()) {
            String response = scnr.next();
            while (!response.equalsIgnoreCase("continue") && !response.equalsIgnoreCase("walk")) {
                System.out.println("Invalid response. (type 'continue' or 'walk')");
                response = scnr.next();
            }
            if (response.equalsIgnoreCase("continue")) {
                System.out.println("You chose to continue. A true gambler, I see!\n");
                drawNewCard();
            } else if (response.equalsIgnoreCase("walk")) {
                System.out.println("Ah, good choice. Gotta know when to walk away!\n");
                TimeUnit.SECONDS.sleep(2);
                hypotheticalScenario();

            }
        }
        scnr.close();
    }

    /**
     * A method that swaps the player's hands with hands responsible for determining how much money they 
     * actually won when they are about to play the "what if" scenario after they walk away.
     * @throws InterruptedException
     */
    public static void hypotheticalScenario() throws InterruptedException {
        System.out
            .println("But... we need to know what would have happened if you played on, right?\n");
        hypotheticalGameplay = true;
        ArrayList<Card> temp1 = hand1;
        ArrayList<Card> temp2 = hand2;
        ArrayList<Card> temp3 = hand3;
        prizeHand1 = new ArrayList<Card>(hand1);
        prizeHand2 = new ArrayList<Card>(hand2);
        prizeHand3 = new ArrayList<Card>(hand3);
        hand1 = temp1;
        hand2 = temp2;
        hand3 = temp3;
        drawNewCard();
    }

    /**
     * A method that draws a card and passes along to the makeDecision() method
     * where the player will decide what to do with that card
     * @throws InterruptedException
     */
    public static void drawNewCard() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Drawing a card.\n");
        System.out.println(".");
        TimeUnit.MILLISECONDS.sleep(500);
        System.out.println("..");
        TimeUnit.MILLISECONDS.sleep(500);
        System.out.println("...\n");
        TimeUnit.MILLISECONDS.sleep(500);
        deck.drawRandomCard(dealtCard);
        makeDecision();
    }

    /**
     * A method that enters blank print lines for 15 lines as a way to "clear"
     * the console and keep the game looking cleaner.
     */
    public static void clearScreen() {
        for (int clear = 0; clear < 15; clear++) {
            System.out.println("");
        }
    }

    /**
     * A method that shows the player's information pertaining to chips and hand cards/value, and then
     * prompts them to make a decision about what to do with the newly drawn card.
     * @throws InterruptedException
     */
    public static void makeDecision() throws InterruptedException {
        clearScreen();
        System.out.println("Number of chips remaining: " + numberOfChips);
        System.out
            .println("\nHand 1: " + Deck.getHand(hand1) + " | Value: " + Deck.getHandValue(hand1));
        System.out
            .println("Hand 2: " + Deck.getHand(hand2) + " | Value: " + Deck.getHandValue(hand2));
        System.out
            .println("Hand 3: " + Deck.getHand(hand3) + " | Value: " + Deck.getHandValue(hand3));

        System.out.println(
            "--------------------------------------------------------------------------------");
        System.out.println("Drawn card: " + Deck.getDrawnCard(dealtCard) + "\n");
        System.out.println("Choices: (Put on '1', '2', '3', or 'chip' it)");
        Scanner scnr = new Scanner(System.in);
        if (scnr.hasNext()) {
            String response = scnr.next();
            while (!response.equals("1") && !response.equals("2") && !response.equals("3")
                && !response.equalsIgnoreCase("chip") && !response.equalsIgnoreCase("hand")) {
                System.out.println("Invalid response. (type '1', '2', '3', or 'chip')");
                response = scnr.next();
            }
            if (response.equals("1") && Deck.getHandValue(hand1) < 21) {
                Deck.addDrawnCard(hand1);
                checkForLoss();
                if (Deck.getHandValue(hand1) == 21) {
                    if (!hypotheticalGameplay) {
                        System.out.println("Congratulations! You caught 21 with Hand 1!\n");
                        bustableCheck();
                    } else {
                        System.out.println("Oh shucks! You would have caught 21 with Hand 1!\n");
                        bustableCheck();
                    }
                } else {
                    System.out.println("You chose to add " + Deck.getDrawnCard(dealtCard)
                        + " to Hand 1, increasing its value to " + Deck.getHandValue(hand1)
                        + ".\n");
                    bustableCheck();
                }
            } else if (response.equals("1") && Deck.getHandValue(hand1) == 21) {
                System.out.println("That hand already has 21. Pick a different hand.\n");
                TimeUnit.SECONDS.sleep(2);
                makeDecision();
            } else if (response.equals("2") && Deck.getHandValue(hand2) < 21) {
                Deck.addDrawnCard(hand2);
                checkForLoss();
                if (Deck.getHandValue(hand2) == 21) {
                    if (!hypotheticalGameplay) {
                        System.out.println("Congratulations! You caught 21 with Hand 2!\n");
                        bustableCheck();
                    } else {
                        System.out.println("Oh shucks! You would have caught 21 with Hand 2!\n");
                        bustableCheck();
                    }
                } else {
                    System.out.println("You chose to add " + Deck.getDrawnCard(dealtCard)
                        + " to Hand 2, increasing its value to " + Deck.getHandValue(hand2)
                        + ".\n");
                    bustableCheck();
                }
            } else if (response.equals("2") && Deck.getHandValue(hand2) == 21) {
                System.out.println("That hand already has 21. Pick a different hand.\n");
                TimeUnit.SECONDS.sleep(2);
                makeDecision();
            } else if (response.equals("3") && Deck.getHandValue(hand3) < 21) {
                Deck.addDrawnCard(hand3);
                checkForLoss();
                if (Deck.getHandValue(hand3) == 21) {
                    if (!hypotheticalGameplay) {
                        System.out.println("Congratulations! You caught 21 with Hand 3!\n");
                        bustableCheck();
                    } else {
                        System.out.println("Oh shucks! You would have caught 21 with Hand 3!\n");
                        bustableCheck();
                    }
                } else {
                    System.out.println("You chose to add " + Deck.getDrawnCard(dealtCard)
                        + " to Hand 3, increasing its value to " + Deck.getHandValue(hand3)
                        + ".\n");
                    bustableCheck();
                }
            } else if (response.equals("3") && Deck.getHandValue(hand3) == 21) {
                System.out.println("That hand already has 21. Pick a different hand.\n");
                TimeUnit.SECONDS.sleep(2);
                makeDecision();
            } else if (response.equalsIgnoreCase("chip")) {
                if (numberOfChips <= 0) {
                    System.out.println("You're out of chips. Choose a different response.\n");
                    TimeUnit.SECONDS.sleep(2);
                    makeDecision();
                } else {
                    numberOfChips--;
                    System.out.println("You chose to use a chip on that card.\n");
                    drawNewCard(); // chip does not mean stop, so there is no bustable check
                }
            }
            checkForLoss();
        }
        scnr.close();
    }

    /**
     * A method that checks if any of the player's hands go over the 21 value. If
     * the situation is hypothetical, it will tell the user they made the right decision in
     * walking away and will send them to the checkPrizeMoney method. Otherwise, it will
     * promptPlayAgain.
     * @throws InterruptedException
     */
    public static void checkForLoss() throws InterruptedException {
        if (Deck.getHandValue(hand1) > 21 || Deck.getHandValue(hand2) > 21
            || Deck.getHandValue(hand3) > 21) {
            if (!hypotheticalGameplay) {
                System.out.println("You busted! You walk away with nothing.\n");
                promptPlayAgain();
            } else {
                System.out.println("You would have busted. You made the right decision!\n");
                checkPrizeMoney(prizeHand1, prizeHand2, prizeHand3);
            }
        }
    }

    /**
     * A method that checks how many of a player's hands are valued at 21, and gives out money accordingly.
     * Parameters of hands are necessary because sometimes the hands will be prizeHands (due to walking away).
     * @param hand1 the first hand that gets checked
     * @param hand2 the second hand that gets checked
     * @param hand3 the third hand that gets checked
     * @throws InterruptedException
     */
    public static void checkPrizeMoney(ArrayList<Card> hand1, ArrayList<Card> hand2,
        ArrayList<Card> hand3) throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        if (Deck.getHandValue(hand1) == 21) {
            numberOf21s++;
        }
        if (Deck.getHandValue(hand2) == 21) {
            numberOf21s++;
        }
        if (Deck.getHandValue(hand3) == 21) {
            numberOf21s++;
        }
        if (numberOf21s == 1) {
            System.out.println("You got one hand valued at 21. You won $2500!");
            totalWinnings += 2500;
            promptPlayAgain();
        } else if (numberOf21s == 2) {
            System.out.println("You got two hands valued at 21. You won $5000!");
            totalWinnings += 5000;
            promptPlayAgain();
        } else if (numberOf21s == 3) {
            System.out.println("You got all three hands valued at 21. You won $25000!");
            totalWinnings += 25000;
            promptPlayAgain();
        }
    }

    /**
     * A method that updates the player's total winnings and prompts the player to play again.
     * If yes, it resets the fields to their appropriate states.
     * @throws InterruptedException
     */
    public static void promptPlayAgain() throws InterruptedException {
        Scanner scnr = new Scanner(System.in);
        System.out.println(
            "--------------------------------------------------------------------------------\n"
                + "Your total winnings thus far are $" + totalWinnings + ".\n");
        TimeUnit.SECONDS.sleep(2);
        System.out.println("\nWould you like to play again? (Type 'y' or 'n')");
        if (scnr.hasNext()) {
            char response = scnr.next().charAt(0);
            while (response != 'y' && response != 'n') {
                System.out.println("Invalid response. (type 'y' or 'n')");
                response = scnr.next().charAt(0);
            }
            if (response == 'y') {
                deck = new Deck();
                dealtCard.clear();
                hand1.clear();
                hand2.clear();
                hand3.clear();
                prizeHand1.clear();
                prizeHand2.clear();
                prizeHand3.clear();
                numberOfChips = 3;
                numberOf21s = 0;
                hypotheticalGameplay = false;
                System.out.println("");
                catch21Game();
            } else if (response == 'n') {
                System.out.println("Thanks for playing!");
                System.exit(0);
            }
        }
        scnr.close();
    }
}
