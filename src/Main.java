class Main {

    // Holds the value of the monetary pot.
    static int pot = 0;
    // The position of the player the game started at.
    static int startingPlayer = 0;
    // A boolean variable to determine whether to continue the game.
    static boolean continueGame = true;
    // Creates a static deck of 52 cards.
    static Deck deck = new Deck(false);

    // Creates a hand for the community cards.
  /*
  Note: Although called community cards, these cards are from the same deck as the cards dealt
  to the players, unlike in some variations. This means no more than 4 cards of the same rank
  are ever active.
  */
    static Hand commHand = new Hand();

    // Variables that statically store the earnings of each player, so their money is not impacted by a new match.
    static int playerOneEarnings = 500, playerTwoEarnings = 500, playerThreeEarnings = 500, playerFourEarnings = 500, playerFiveEarnings = 500, playerSixEarnings = 500;

    // The main method.
    public static void main(String[] args) {

        // Gives an informational starting message:
        System.out.println("Welcome to Texas hold 'em Poker! Before you get started, be sure to note you will be forced to fold if you run out of money. If you notice that your group is getting low on money, it's probably best to take a break and come back later (restart the game) for maximum enjoyment! Have fun and good luck!");

        // Runs the game logic while true.
        while (continueGame)
        {
            // Creates 6 players, 5 AI players and 1 for the human to manipulate.
            PlayerAI player1 = new PlayerAI();
            PlayerAI player2 = new PlayerAI();
            PlayerAI player3 = new PlayerAI();
            PlayerAI player4 = new PlayerAI();
            PlayerAI player5 = new PlayerAI();
            Player humanPlayer = new Player();

            // Sets the money in the new player objects to the ending value from last round.
            player1.money = playerOneEarnings;
            player2.money = playerTwoEarnings;
            player3.money = playerThreeEarnings;
            player4.money = playerFourEarnings;
            player5.money = playerFiveEarnings;
            humanPlayer.money = playerSixEarnings;

            // Passes the player objects to the gameLoop subroutine.
            gameLoop(player1, player2, player3, player4, player5, humanPlayer);
        }

        // For testing.
        // You can comment out the main loop and uncomment this to test any combination of cards, and see their values.
    /*
    Hand test = new Hand();
    Card first = new Card(3, 1);
    Card second = new Card(3, 0);
    Card third = new Card(9, 2);
    Card fourth = new Card(7, 3);
    Card fifth = new Card(2, 2);
    Card sixth = new Card(2, 3);
    Card seventh = new Card(11, 0);
    test.addCard(first);
    test.addCard(second);
    test.addCard(third);
    test.addCard(fourth);
    test.addCard(fifth);
    test.addCard(sixth);
    test.addCard(seventh);

    Showdown evaluate = new Showdown();
    System.out.println(evaluate.getCards(test.getCard(0), test.getCard(1), test.getCard(2), test.getCard(3), test.getCard(4), test.getCard(5), test.getCard(6)));;
    */
    }

    // The gameLoop subroutine. This subroutine is responsible for the order of game events.
    public static void gameLoop(Player player1, Player player2, Player player3, Player player4, Player player5,
                                Player humanPlayer) {

        // A boolean that holds whether a winner has been determined.
        boolean winnerFound = false;

        // Shuffles the deck.
        shuffleDeck();

        // Finds the starting player by determining the blinds. Passes the player objects to the
        // payBlinds method.
        startingPlayer = payBlinds(player1, player2, player3, player4, player5, humanPlayer);

        // Deals two cards to the players.
        dealTwoCards(player1, player2, player3, player4, player5, humanPlayer);

        // Calls a betting round, which allows for the possibility of a winner to be determined.
        winnerFound = checkWinAfterBet(player1, player2, player3, player4, player5, humanPlayer, startingPlayer, true);

        // Check if a winner has been found.
        if(winnerFound == false)
        {
            // If no winner has been found, another round is needed.
            // Formats the output.
            System.out.println("");
            System.out.println("**********************************************************");
            System.out.println("                Dealing 3 Community Cards                 ");
            System.out.println("**********************************************************");

            // Deals three community cards.
            dealCommunityCards(player1, player2, player3, player4, player5, humanPlayer, 3, 0);
            // Calls a betting round, which allows for the possibility of a winner to be determined.
            winnerFound = checkWinAfterBet(player1, player2, player3, player4, player5, humanPlayer, startingPlayer, false);

            // Check if a winner has been found.
            if(winnerFound == false)
            {
                // If no winner has been found, another round is needed.
                // Formats the output.
                System.out.println("");
                System.out.println("**********************************************************");
                System.out.println("                Dealing 1 Community Card                  ");
                System.out.println("**********************************************************");

                // Deals one community card.
                dealCommunityCards(player1, player2, player3, player4, player5, humanPlayer, 1, 3);
                // Calls a betting round, which allows for the possibility of a winner to be determined.
                winnerFound = checkWinAfterBet(player1, player2, player3, player4, player5, humanPlayer, startingPlayer, false);

                // Check if a winner has been found.
                if (winnerFound == false)
                {
                    // If no winner has been found, another round is needed.
                    // Formats the output.
                    System.out.println("");
                    System.out.println("**********************************************************");
                    System.out.println("              Dealing the Last Community Card             ");
                    System.out.println("**********************************************************");

                    // Deals one more (the final) community card.
                    dealCommunityCards(player1, player2, player3, player4, player5, humanPlayer, 1, 4);
                    // Calls a betting round, which allows for the possibility of a winner to be determined.
                    winnerFound = checkWinAfterBet(player1, player2, player3, player4, player5, humanPlayer, startingPlayer, false);

                    // Check if a winner has been found.
                    if (winnerFound == false)
                    {
                        // If no winner has been found, another round is needed.
                        // Formats the output.
                        System.out.println("");
                        System.out.println("**********************************************************");
                        System.out.println("                    It's Showtime Folks                   ");
                        System.out.println("**********************************************************");

                        // After all betting rounds are complete, will need to determine the winner through a showdown.
                        checkWinFromShowdown(player1, player2, player3, player4, player5, humanPlayer);

                        // Calls the subroutine to determine whether the game should continue.
                        continuePlaying(player1, player2, player3, player4, player5, humanPlayer);
                    }
                }
                else
                {
                    // Executes if a winner has been found by players folding during the betting round.
                    // Calls the subroutine to determine whether the game should continue.
                    continuePlaying(player1, player2, player3, player4, player5, humanPlayer);
                }
            }
            else
            {
                // Executes if a winner has been found by players folding during the betting round.
                // Calls the subroutine to determine whether the game should continue.
                continuePlaying(player1, player2, player3, player4, player5, humanPlayer);
            }
        }
        else
        {
            // Executes if a winner has been found by players folding during the betting round.
            // Calls the subroutine to determine whether the game should continue.
            continuePlaying(player1, player2, player3, player4, player5, humanPlayer);
        }
    }

    // Shuffles the deck.
    public static void shuffleDeck() {
        deck.shuffle();
    }

    // THe subroutine that determines whether to continue the game.
    public static void continuePlaying(Player player1, Player player2, Player player3, Player player4, Player player5,
                                       Player humanPlayer)
    {
        // A string object that will hold the user's answer to a question.
        String answer = "";

        // A loop to repeatedly check for correct inputs.
        while (true)
        {
            // Asks the user about continuing the game.
            System.out.println("Do you want to continue playing? Enter 'yes' or 'no': ");
            // Takes the users answer as a word/String.
            answer = TextIO.getlnWord();

            // If a valid answer is found, break the loop.
            if (answer.equals("yes") || answer.equals("no"))
            {
                break;
            }
        }

        // Use the user's answer to stop or continue the game.
        if (answer.equals("no"))
        {
            // If the user answered "no", stop the game.
            continueGame = false;
        }
        else // Executes if the game should continue.
        {
            // Repoints the pointer at a new deck.
            Deck deckNew = new Deck(false);
            deck = deckNew;

            // Repoints the pointer at a new community hand.
            Hand commHandNew = new Hand();
            commHand = commHandNew;

            // Stores the money of each player.
            playerOneEarnings = player1.money;
            playerTwoEarnings = player2.money;
            playerThreeEarnings = player3.money;
            playerFourEarnings = player4.money;
            playerFiveEarnings = player5.money;
            playerSixEarnings = humanPlayer.money;

            // Gives a confirmation message to the user.
            System.out.println("");
            System.out.println("***************************************************************************");
            System.out.println("********************** Good to go for the next round! *********************");
            System.out.println("***************************************************************************");
            System.out.println("");
        }
    }

    // The method to pay the blinds.
    public static int payBlinds(Player one, Player two, Player three, Player four, Player five, Player six) {

        // Generates a random number between 1 and 6, which represents the player that will pay the small blind.
        int smallBlindPlayer = (int) ((Math.random() * 6) + 1);

        // Determines what player pays the small blind.
        // The player to pay the big blind is beside the small blind player.
        // Example: 6 --> 1, 4 --> 5, etc.
        if (smallBlindPlayer == 1) {
            // Player one pays the small blind.
            // Notifies the user about who is paying the blinds.
            System.out.println("Small Blind: Player " + 1);
            System.out.println("Big Blind: Player " + 2);
            // Adjusts the money of two players who pay the small and big blinds.
            one.adjustMoney(-1);
            two.adjustMoney(-2);
        } else if (smallBlindPlayer == 2) {
            // Player two pays the small blind.
            // Notifies the user about who is paying the blinds.
            System.out.println("Small Blind: Player " + 2);
            System.out.println("Big Blind: Player " + 3);
            // Adjusts the money of two players who pay the small and big blinds.
            two.adjustMoney(-1);
            three.adjustMoney(-2);
        } else if (smallBlindPlayer == 3) {
            // Player three pays the small blind.
            // Notifies the user about who is paying the blinds.
            System.out.println("Small Blind: Player " + 3);
            System.out.println("Big Blind: Player " + 4);
            // Adjusts the money of two players who pay the small and big blinds.
            three.adjustMoney(-1);
            four.adjustMoney(-2);
        } else if (smallBlindPlayer == 4) {
            // Player four pays the small blind.
            // Notifies the user about who is paying the blinds.
            System.out.println("Small Blind: Player " + 4);
            System.out.println("Big Blind: Player " + 5);
            // Adjusts the money of two players who pay the small and big blinds.
            four.adjustMoney(-1);
            five.adjustMoney(-2);
        } else if (smallBlindPlayer == 5) {
            // Player five pays the small blind.
            // Notifies the user about who is paying the blinds.
            System.out.println("Small Blind: Player " + 5);
            System.out.println("Big Blind: Player " + 6);
            // Adjusts the money of two players who pay the small and big blinds.
            five.adjustMoney(-1);
            six.adjustMoney(-2);
        } else {
            // Player six pays the small blind.
            // Notifies the user about who is paying the blinds.
            System.out.println("Small Blind: Player " + 6);
            System.out.println("Big Blind: Player " + 1);
            // Adjusts the money of two players who pay the small and big blinds.
            six.adjustMoney(-1);
            one.adjustMoney(-2);
        }

        // Update the pot with the blinds.
        pot += 3;

        // Returns the position of whoever started the game as the small blind.
        return smallBlindPlayer;
    }

    // The subroutine to deal two cards.
    public static void dealTwoCards(Player one, Player two, Player three, Player four, Player five, Player six) {

        // Loops around the "table" to deal one card to each person a total of two times.
        for (int i = 0; i < 2; i++) {
            one.hand.addCard(deck.dealCard());
            two.hand.addCard(deck.dealCard());
            three.hand.addCard(deck.dealCard());
            four.hand.addCard(deck.dealCard());
            five.hand.addCard(deck.dealCard());
            six.hand.addCard(deck.dealCard());
        }
    }

    // The method to deal community cards.
    // Limit of how many to deal and where in the array list to start from.
    public static void dealCommunityCards(Player one, Player two, Player three, Player four, Player five, Player six, int limit, int startFrom) {

        // Loops/deals the amount of cards determined by the limit.
        for (int i = 0; i < limit; i++) {

            // Adds a card to the community hand.
            commHand.addCard(deck.dealCard());

            // Lets the players see the community hand cards.
            // Effectively deals the community cards to each player.
            one.hand.addCard(commHand.getCard(startFrom + i));
            two.hand.addCard(commHand.getCard(startFrom + i));
            three.hand.addCard(commHand.getCard(startFrom + i));
            four.hand.addCard(commHand.getCard(startFrom + i));
            five.hand.addCard(commHand.getCard(startFrom + i));
            six.hand.addCard(commHand.getCard(startFrom + i));
        }
    }

    // Betting method.
    public static int bettingRound (Player one, Player two, Player three, Player four, Player five, Player six, int startingPlayer, boolean firstTime)
    {
        // Stores the number that represents the first player that needs to bet.
        int firstPlayerToBet = 0;

        // Starts the mandatory betting amount at two to represent the blinds.
        int mandatoryAmount = 2;

        // Counts the amount of turns uninterrupted by raises.
        int uninterruptedTurns = 0;

        // Stores the amount of players that folded.
        int foldedPlayers = 0;

        // Creates an array to store the players.
        Player[] players = new Player[6];

        // Stores each player at a spot in the array.
        players[0] = one;
        players[1] = two;
        players[2] = three;
        players[3] = four;
        players[4] = five;
        players[5] = six;

        // Determines the first player to bet as being two away from the small blind payer.
        if (startingPlayer == 1) {
            firstPlayerToBet = 3;
        } else if (startingPlayer == 2) {
            firstPlayerToBet = 4;
        } else if (startingPlayer == 3) {
            firstPlayerToBet = 5;
        } else if (startingPlayer == 4) {
            firstPlayerToBet = 6;
        } else if (startingPlayer == 5) {
            firstPlayerToBet = 1;
        } else {
            firstPlayerToBet = 2;
        }

        // Determines if it's the first betting round.
        if (firstTime == true)
        {
            // Account for blinds.
            // Each player must bet the amount of the mandatory blind.
            players[0].mustBet = mandatoryAmount;
            players[1].mustBet = mandatoryAmount;
            players[2].mustBet = mandatoryAmount;
            players[3].mustBet = mandatoryAmount;
            players[4].mustBet = mandatoryAmount;
            players[5].mustBet = mandatoryAmount;

            // Checks who paid the blinds already.
            // If a player paid a blind, reduce that amount from the 2 they must bet since it's already paid.
            if (startingPlayer == 1) {
                players[0].mustBet -= 1;
                players[1].mustBet -= 2;
            } else if (startingPlayer == 2) {
                players[1].mustBet -= 1;
                players[2].mustBet -= 2;
            } else if (startingPlayer == 3) {
                players[2].mustBet -= 1;
                players[3].mustBet -= 2;
            } else if (startingPlayer == 4) {
                players[3].mustBet -= 1;
                players[4].mustBet -= 2;
            } else if (startingPlayer == 5) {
                players[4].mustBet -= 1;
                players[5].mustBet -= 2;
            } else {
                players[5].mustBet -= 1;
                players[0].mustBet -= 2;
            }
        }
        else // Executes if it is not the first betting round.
        {
            // The mandatory amount to bet must not be determined by the blinds.
            // Players are not yet expected to bet a mandatory amount.
            mandatoryAmount = 0;
            players[0].mustBet = mandatoryAmount;
            players[1].mustBet = mandatoryAmount;
            players[2].mustBet = mandatoryAmount;
            players[3].mustBet = mandatoryAmount;
            players[4].mustBet = mandatoryAmount;
            players[5].mustBet = mandatoryAmount;

        }

        // Subtract one to account for array starting at 0.
        // Starts the loop at the player that was determined to be two away from the small blind payer.
        for (int i = firstPlayerToBet - 1; i < 6; i++)
        {
            // Check how many players have folded.
            for (int j = 0; j < 6; j ++)
            {
                // Check that a player wants to continue betting.
                if (players[j].continueBetting != true)
                {
                    //Count a player as having folded.
                    foldedPlayers++;
                }
            }

            // Determines if the current player wants to continue betting.
            if (players[i].continueBetting == true)
            {
                // Executes if the current player is still playing and all the others have folded.
                if (foldedPlayers == 5)
                {
                    // Returns player that has not folded from player 1-6, not 0-5.
                    return (i+1);
                }

                // Output to provide information on the player currently betting, the pot value, the player's money, and the value they must bet to continue.
                System.out.println("");
                System.out.println("Player: " + (i + 1));
                System.out.print("Pot: " + pot + " ");
                System.out.print("Money: " + players[i].money + " ");
                System.out.print("Must Bet: " + players[i].mustBet + " ");
                System.out.println("");

                // Find if the player wants to fold, check, or raise and assign it to a String object.
                String betChoice = players[i].betAnswer();

                // Selects what the player indicated.
                if (betChoice.equals("raise"))
                {
                    // Executes if raised was entered.

                    // Reset the amount of uninterrupted turns to 1 if someone raises the pot.
                    // Once this reaches six, meaning everyone else has folded or checked, the betting round ends.
                    // If this was not reset to 1, the current player who raised the bet would have to go again.
                    uninterruptedTurns = 1;

                    // Find the amount the player wants to raise by.
                    int raise = players[i].raiseAmount();

                    // Set the mandatory betting amount equal to what was just raised.
                    mandatoryAmount = raise;

                    // Adjusts for what players must bet after a raise.
                    players[0].mustBet += raise;
                    players[1].mustBet += raise;
                    players[2].mustBet += raise;
                    players[3].mustBet += raise;
                    players[4].mustBet += raise;
                    players[5].mustBet += raise;

                    // Adjust the current player's money so that the amount they must bet is subtracted from their account.
                    players[i].money = players[i].money - players[i].mustBet;

                    // Adds the bet money to the pot.
                    pot = pot + players[i].mustBet;

                    // The current player no longer has to bet anything, it's been put in the pot.
                    players[i].mustBet = 0;

                    // Give information on the player's status after the raise.
                    System.out.println("Player " + (i + 1) + " raised: " + raise);
                    System.out.print("Pot: " + pot + " ");
                    System.out.print("Money: " + players[i].money + " ");
                    System.out.print("Must Bet: " + players[i].mustBet + " ");
                    System.out.println("");
                }
                else if (betChoice.equals("check"))
                {
                    // Executes if check was indicated.

                    // Increases the amount of uninterruptedTurns.
                    uninterruptedTurns++;

                    // Subtract the amount of money the current player must bet from their account.
                    players[i].money -= players[i].mustBet;

                    // Add the amount of money the current player had to bet to the pot.
                    pot += players[i].mustBet;

                    // The current player does not have to bet any more money at the moment.
                    players[i].mustBet = 0;

                    // Give information on the player's status after the check.
                    System.out.println("Player " + (i + 1) + " checked.");
                    System.out.print("Pot: " + pot + " ");
                    System.out.print("Money: " + players[i].money + " ");
                    System.out.print("Must Bet: " + players[i].mustBet + " ");
                    System.out.println("");
                }
                else if (betChoice.equals("fold"))
                {
                    // Executes if fold was indicated.

                    // Increases the amount of uninterruptedTurns.
                    uninterruptedTurns++;
                    // Give information on the player's status after the fold.
                    System.out.println("Player " + (i + 1) + " folded.");
                    // This player will not continue betting.
                    players[i].continueBetting = false;
                }
            }
            else
            {
                // Checking for a winner assuming the current player has folded, and there are already 5 folded players
                // Checking again is necessary to cover these additional situations
                // Executes if the selected player is still playing and all the others have folded.
                if (foldedPlayers == 5)
                {
                    // Executes if 5 players have folded.
                    // Loop to iterate through the array of players.
                    for (int k = 0; k < 6; k++)
                    {
                        // Finds the one player who has not folded.
                        if(players[k].continueBetting == true)
                        {
                            // Returns player that has not folded from player 1-6, not 0-5.
                            System.out.println("Winner " + (k+1));

                            // Returns the number to represent the winning player.
                            return (k+1);
                        }
                    }
                }

                // Accounts for folded players.
                uninterruptedTurns++;
                // Once the uninterruptedTurns reaches six, nobody wants to bet anymore so end the round by breaking the loop.
                if (uninterruptedTurns == 6)
                {
                    break;
                }
            }

            // Resets the counter
            foldedPlayers = 0;

            // Executes when the last player in the array is reached.
            if (i == 5)
            {
                // Set to -1 since 1 will be added by the for loop.
                // This allows player 0 in the array to have a turn.
                i = -1;
            }

            // Once the uninterruptedTurns reaches six, nobody wants to bet anymore so end the round by breaking the loop.
            if (uninterruptedTurns == 6)
            {
                break;
            }
        }

        // Returns a number that does not represent a winner if no winner was found based on folding.
        return 7;
    }

    public static Boolean checkWinAfterBet(Player one, Player two, Player three, Player four, Player five, Player six, int startingPlayer, boolean firstTime)
    {
        int winner = bettingRound(one, two, three, four, five, six, startingPlayer, firstTime);

        boolean winnerCondition = false;

        if (winner == 1)
        {
            // Add money to the pot and set the pot equal to 0.
            one.money += pot;
            pot = 0;
            winnerCondition = true;
        }
        else if (winner == 2)
        {
            two.money += pot;
            pot = 0;
            winnerCondition = true;
        }
        else if (winner == 3)
        {
            three.money += pot;
            pot = 0;
            winnerCondition = true;
        }
        else if (winner == 4)
        {
            four.money += pot;
            pot = 0;
            winnerCondition = true;
        }
        else if (winner == 5)
        {
            five.money += pot;
            pot = 0;
            winnerCondition = true;
        }
        else if (winner == 6)
        {
            six.money += pot;
            pot = 0;
            winnerCondition = true;
        }
        else
        {
            return false;
        }

        if (winnerCondition = true)
        {
            System.out.println(" ************** Player " + winner + " Wins! ************** ");

            System.out.println("Player 1 Money: " + one.money);
            System.out.println("Player 2 Money: " + two.money);
            System.out.println("Player 3 Money: " + three.money);
            System.out.println("Player 4 Money: " + four.money);
            System.out.println("Player 5 Money: " + five.money);
            System.out.println("Player 6 Money: " + six.money);
        }

        return winnerCondition;
    }

    // Checks win with showdown values
    public static void checkWinFromShowdown(Player one, Player two, Player three, Player four, Player five, Player six)
    {
        // An array of doubles to hold the value of each player's hand.
        double scores[] = new double[6];

        // Get the value of a player's hand if they are still betting, otherwise their score is zero when folded.
        if (one.continueBetting == true)
        {
            scores[0] = one.getHandValue();
        }
        else
        {
            scores[0] = 0.0;
        }

        // Get the value of a player's hand if they are still betting, otherwise their score is zero when folded.
        if (two.continueBetting == true)
        {
            scores[1] = two.getHandValue();
        }
        else
        {
            scores[1] = 0.0;
        }

        // Get the value of a player's hand if they are still betting, otherwise their score is zero when folded.
        if (three.continueBetting == true)
        {
            scores[2] = three.getHandValue();
        }
        else
        {
            scores[2] = 0.0;
        }

        // Get the value of a player's hand if they are still betting, otherwise their score is zero when folded.
        if (four.continueBetting == true)
        {
            scores[3] = four.getHandValue();
        }
        else
        {
            scores[3] = 0.0;
        }

        // Get the value of a player's hand if they are still betting, otherwise their score is zero when folded.
        if (five.continueBetting == true)
        {
            scores[4] = five.getHandValue();
        }
        else
        {
            scores[4] = 0.0;
        }

        // Get the value of a player's hand if they are still betting, otherwise their score is zero when folded.
        if (six.continueBetting == true)
        {
            scores[5] = six.getHandValue();
        }
        else
        {
            scores[5] = 0.0;
        }

        // Show the hands of each player.
        System.out.println("");
        System.out.println("Player 1's Hand:");
        System.out.println("");
        one.sayHand();
        System.out.println(winReason(scores[0]));
        System.out.println("");
        System.out.println("Player 2's Hand:");
        System.out.println("");
        two.sayHand();
        System.out.println(winReason(scores[1]));
        System.out.println("");
        System.out.println("Player 3's Hand:");
        System.out.println("");
        three.sayHand();
        System.out.println(winReason(scores[2]));
        System.out.println("");
        System.out.println("Player 4's Hand:");
        System.out.println("");
        four.sayHand();
        System.out.println(winReason(scores[3]));
        System.out.println("");
        System.out.println("Player 5's Hand:");
        System.out.println("");
        five.sayHand();
        System.out.println(winReason(scores[4]));
        System.out.println("");
        System.out.println("Your Hand:");
        System.out.println("");
        six.sayHand();
        System.out.println(winReason(scores[5]));

        // An array of doubles to hold a copy of the scores array, this will then be used to sort.
        double newScores[] = new double[6];
        // Copying the contents of scores to new scores
        System.arraycopy(scores, 0, newScores, 0, scores.length);

        // Sort the scores in newScores using selection sort.
        for (int lastPlace = newScores.length-1; lastPlace > 0; lastPlace--) {
            // Keep track of the largest number by swapping it with the number in last place.

            int maxLoc = 0;  // Location of largest item seen so far.

            for (int j = 1; j <= lastPlace; j++) {
                if (newScores[j] > newScores[maxLoc]) {
                    // Make j the new location if the value of scores at j is bigger than what has been seen.
                    maxLoc = j;
                }
            }

            double temp = newScores[maxLoc];  // Swaps whatever the largest item is with the last spot in the array.
            newScores[maxLoc] = newScores[lastPlace];
            newScores[lastPlace] = temp;
        }

        // The highest score will be the score at the end of the newScores array.
        double highestScore = newScores[5];

        // Hold the value representing the winning player.
        int winner = 0;

        // Using a linear search to find the position in the original scores array that contained the highest score.
        for (int i = 0; i < scores.length; i++)
        {
            if (scores[i] == highestScore)
            {
                winner = i + 1;
            }
        }

        // Adjust the money depending on who wins.
        if (winner == 1)
        {
            // Add money to the pot and set the pot equal to 0.
            one.money += pot;
            pot = 0;
        }
        else if (winner == 2)
        {
            two.money += pot;
            pot = 0;
        }
        else if (winner == 3)
        {
            three.money += pot;
            pot = 0;
        }
        else if (winner == 4)
        {
            four.money += pot;
            pot = 0;
        }
        else if (winner == 5)
        {
            five.money += pot;
            pot = 0;
        }
        else
        {
            six.money += pot;
            pot = 0;
        }

        // Output a message for the winner
        System.out.println("");
        System.out.println(" ************** Player " + winner + " Wins! ************** ");

        System.out.println("Player 1 Money: " + one.money);
        System.out.println("Player 2 Money: " + two.money);
        System.out.println("Player 3 Money: " + three.money);
        System.out.println("Player 4 Money: " + four.money);
        System.out.println("Player 5 Money: " + five.money);
        System.out.println("Player 6 Money: " + six.money);

        // Uncomment to output the scores.
    /*
    for (int i = 0; i < 6; i++)
    {
      System.out.println(scores[i]);
    }
    */
    }

    // Provides information on why someone won, or what poker hand they had.
    public static String winReason(double value)
    {
        // Check for values and return the associated poker hand that correlates with that hand strength.
        if(value >= 2000)
        {
            return "Status: Royal Flush";
        }
        else if (value >= 1800)
        {
            return "Status: Straight Flush";
        }
        else if (value >= 1600)
        {
            return "Status: Four of a Kind";
        }
        else if (value >= 1400)
        {
            return "Status: Full House";
        }
        else if (value >= 1200)
        {
            return "Status: Flush";
        }
        else if (value >= 1000)
        {
            return "Status: Straight";
        }
        else if (value >= 800)
        {
            return "Status: Three of a Kind";
        }
        else if (value >= 600)
        {
            return "Status: Two Pair";
        }
        else if (value >= 400)
        {
            return "Status: Pair";
        }
        else if (value == 0)
        {
            return "Status: Folded";
        }
        else
        {
            return "Status: High Card";
        }
    }
}





































