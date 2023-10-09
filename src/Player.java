public class Player {
    // Variable to hold the amount of money the player has.
    public int money = 500;
    // Variable to hold how much money the player must bet.
    public int mustBet = 0;
    // Creates a hand to store the cards in.
    Hand hand = new Hand();
    // Variable that stores the strength of the player's hand.
    public double handStrength = 0;
    // Variable that stores whether the player (wants to)/can continue betting.
    public boolean continueBetting = true;

    // Polymorphism that uses different forms of the showdown class depending on how many cards need comparison.
    // The different showdown classes only have their input method altered.
    Showdown checkSeven = new Showdown();
    ShowdownTwo checkTwo = new ShowdownTwo();
    ShowdownFive checkFive = new ShowdownFive();
    ShowdownSix checkSix = new ShowdownSix();

    // Subroutine that allows the money to be adjusted.
    public void adjustMoney(int amount) {
        money = money + amount;
    }

    // Returns the answer to whether or not the player will bet.
    public String betAnswer()
    {
        // Stores the answer to the question.
        String betAnswer;

        // Tells the user their hand before they fold, raise, or bet.
        sayHand();

        // Break the loop when valid answer is given.
        while (true)
        {
            // Notify the user of their options.
            System.out.println("Bet. Options: 'fold', 'check', or 'raise':");

            // Get the answer from input.
            betAnswer = TextIO.getlnWord();

            // Fold if cannot match the bet.
            if (money < mustBet)
            {
                betAnswer = "fold";
            }

            // If a valid answer has been entered, break the loop.
            if (betAnswer.equals("raise") || betAnswer.equals("fold") || betAnswer.equals("check"))
            {
                break;
            }
        }

        // Return the answer.
        return betAnswer;
    }

    // Method to give the raise amount.
    public int raiseAmount()
    {
        int raiseAnswer;

        // Break when a valid answer is given.
        while (true)
        {
            // Gives the user instructions on what can be entered.
            System.out.println("Input an amount to raise the pot greater than what must be bet and less than your monetary balance. Note, 0 can be entered and is equivalent to checking.");
            // Takes the user's answer to the amount they want to raise.
            raiseAnswer = TextIO.getlnInt();

            // Breaks the loop if a valid answer is given.
            if (raiseAnswer + mustBet <= money)
            {
                break;
            }
        }

        // Returns the answer to the amount raised.
        return raiseAnswer;
    }

    // Method to get the hand value.
    public double getHandValue()
    {
        // Depending on the amount of cards, call an object related to a different version of the showdown class.
        // The value returned from these objects is equal to the hand strength.
        if (hand.getCardCount() == 2)
        {
            // Uses an object that can handle taking two cards.
            handStrength = checkTwo.getCards(hand.getCard(0), hand.getCard(1));
        }
        else if (hand.getCardCount() == 5)
        {
            // Uses an object that can handle taking five cards.
            handStrength = checkFive.getCards(hand.getCard(0), hand.getCard(1), hand.getCard(2), hand.getCard(3), hand.getCard(4));
        }
        else if (hand.getCardCount() == 6)
        {
            // Uses an object that can handle taking six cards.
            handStrength = checkSix.getCards(hand.getCard(0), hand.getCard(1), hand.getCard(2), hand.getCard(3), hand.getCard(4), hand.getCard(5));
        }
        else if (hand.getCardCount() == 7)
        {
            // Uses an object that can handle taking seven cards.
            handStrength = checkSeven.getCards(hand.getCard(0), hand.getCard(1), hand.getCard(2), hand.getCard(3), hand.getCard(4), hand.getCard(5), hand.getCard(6));
        }

        // Return the hand strength.
        return handStrength;
    }

    // Outputs the player's hand
    public void sayHand()
    {
        // Select how many cards are in the hand.
        if (hand.getCardCount() == 2)
        {
            // Give the user a message to let them know their hand is now being outputted.
            System.out.println("Player hand:");

            // Iterate twice for two cards.
            for (int i = 0; i < 2; i++)
            {
                // Output the cards.
                System.out.println(hand.getCard(i));
            }
        }
        else if (hand.getCardCount() == 5){
            // Iterate 5 times for 5 cards.
            for (int i = 0; i < 5; i++)
            {
                if (i == 0)
                {
                    // Give the user a message to let them know their hand is now being outputted.
                    System.out.println("Player hand:");
                }
                else if (i == 2)
                {
                    // Give the user a message to let them know the community cards are now being outputted.
                    System.out.println("Community cards:");
                }

                // Output the cards.
                System.out.println(hand.getCard(i));
            }
        }
        else if (hand.getCardCount() == 6){
            // Iterate 6 times for 6 cards.
            for (int i = 0; i < 6; i++)
            {
                if (i == 0)
                {
                    // Give the user a message to let them know their hand is now being outputted.
                    System.out.println("Player hand:");
                }
                else if (i == 2)
                {
                    // Give the user a message to let them know the community cards are now being outputted.
                    System.out.println("Community cards:");
                }

                // Output the cards.
                System.out.println(hand.getCard(i));
            }
        }
        else if (hand.getCardCount() == 7){
            // Iterate 7 times for 7 cards.
            for (int i = 0; i < 7; i++)
            {
                if (i == 0)
                {
                    // Give the user a message to let them know their hand is now being outputted.
                    System.out.println("Player hand:");
                }
                else if (i == 2)
                {
                    // Give the user a message to let them know the community cards are now being outputted.
                    System.out.println("Community cards:");
                }

                // Output the cards.
                System.out.println(hand.getCard(i));
            }
        }
    }
}


