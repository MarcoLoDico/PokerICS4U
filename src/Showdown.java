public class Showdown {

    // Declare a hand object to hold the cards.
    Hand hand = new Hand();

    // Create a 4 x 15 array. This is capable of holding cards values from 0-14 and the different suits.
    // This array can easily be searched to find the value of a card.
    int[][] cards = new int[4][15];

    // Variable to store the strength of a hand.
    double handStrength = 0;

    // Variable that stores the strength of the high cards in a hand. This is important to break ties based on highest cards.
    double highCardHandStrength = 0.0;

    // Method that takes all 7 cards used in a showdown.
    public double getCards(Card one, Card two, Card three, Card four, Card five, Card six, Card seven) {

        // Add each card to the hand.
        hand.addCard(one);
        hand.addCard(two);
        hand.addCard(three);
        hand.addCard(four);
        hand.addCard(five);
        hand.addCard(six);
        hand.addCard(seven);

        // Populates the array with 0s to represent empty space.
        populateArray();

        // Finds the values associated with the card's suit and rank.
        findCards();

        // Gets the value of the high cards.
        highCard();


        // Check for each combination in order of strength. A royal flush is the most powerful and high cards are the least powerful.
        if (checkRoyalFlush() == true) {
            // Empty the array after the check is completed. This is a precaution to insure the same
            // showdown object never accumulates cards.
            clearArray();
            // Returns the hand strength based on the hand combination and the impact of high cards.
            return handStrength + highCardHandStrength;
            // The rest of the selection block follows the same logic.
        } else if (checkStraightFlush() == true) {
            clearArray();
            return handStrength + highCardHandStrength;
        } else if (checkFourOfAKind() == true) {
            clearArray();
            return handStrength + highCardHandStrength;
        } else if (checkFullHouse() == true) {
            clearArray();
            return handStrength + highCardHandStrength;
        } else if (checkFlush() == true) {
            clearArray();
            return handStrength + highCardHandStrength;
        } else if (checkStraight() == true) {
            clearArray();
            return handStrength + highCardHandStrength;
        } else if (checkThreeOfAKind() == true) {
            clearArray();
            return handStrength + highCardHandStrength;
        } else if (checkTwoPair() == true) {
            clearArray();
            return handStrength + highCardHandStrength;
        } else if (checkPair() == true) {
            clearArray();
            return handStrength + highCardHandStrength;
        } else {
            clearArray();
            // If no specific combinations, get the value based on the high card.
            return highCardHandStrength;
        }
    }

    // Fills the array with 0s.
    public void populateArray()
    {
        // Iterates through each row and column.
        for (int i = 0; i < cards.length; i++) {
            for (int j = 0; j < cards[0].length; j++) {
                // Places a 0 in each spot.
                cards[i][j] = 0;
            }
        }
    }

    // Places the card in the array.
    public void placeCardInArray(int row, int column) {
        // Put aces in the farthest right spot
        // The card class represents aces as 1s, but poker needs them as 14.
        if (column == 1) {
            cards[row][14] = 1;
        } else {
            // Place a card in the spot represented by its suit and rank.
            cards[row][column] = 1;
        }
    }

    // Subroutine that gets the value of the card's suit and rank.
    public void findCards() {
        // Iterate through each card in the hand.
        for (int i = 0; i < hand.getCardCount(); i++) {
            // Get the row and column represented by the suit and rank.
            int row = hand.getCard(i).getSuit();
            int column = hand.getCard(i).getValue();

            // Places each card in the array based on these values.
            placeCardInArray(row, column);
        }
    }

    // Prints the array.
    // For testing and visualization purposes.
    // Uncomment and call from the getCards method to use this subroutine.
  /*
  public void printArray() {
    for (int i = 0; i < cards.length; i++) {
      for (int j = 0; j < cards[0].length; j++) {
        System.out.print(cards[i][j]);
      }
      System.out.println();
    }
  }
  */

    // Clears the array.
    public void clearArray() {
        // Iterates through each column and row of the array and set the values to 0.
        for (int i = 0; i < cards.length; i++) {
            for (int j = 0; j < cards[0].length; j++) {
                cards[i][j] = 0;
            }
        }
    }

    // Method to check for a royal flush.
    public boolean checkRoyalFlush() {
        // Flush if 5 consecutive cards that start at 10.
        int consecutiveCards = 0;

        // Iterate through the last 5 columns of a row.
        for (int i = 0; i < cards.length; i++) {
            // Loop starts at the last 5 cards.
            for (int j = 10; j < 15; j++) {
                // Add one consecutive card each time a card is found.
                if (cards[i][j] == 1) {
                    consecutiveCards++;
                }
            }

            // If five consecutive cards are found, a royal flush is true.
            if (consecutiveCards == 5) {
                // Set to the maximum strength.
                handStrength = 2000;
                return true;
            }

            // Reset for the next row.
            consecutiveCards = 0;
        }

        // Return false if a royal flush is not found.
        return false;
    }

    // Checks for a straight flush.
    public boolean checkStraightFlush() {
        // Flush if 5 consecutive cards
        int consecutiveCards = 0;

        // Iterates through the array, going across columns then navigating different rows.
        for (int i = 0; i < cards.length; i++) {
            for (int j = 14; j >= 0; j--) {
                // If a card is present increase the consecutive card counter.
                if (cards[i][j] == 1) {
                    consecutiveCards++;
                } else {
                    // Reset the counter if no card is at the current position.
                    consecutiveCards = 0;
                }

                // If a flush is found.
                if (consecutiveCards == 5) {
                    // Add the value of the highest card.
                    // This breaks ties if two flushes appear.
                    // Adding 4 to j since j will be on the left most (lowest) card when the flush is detected.
                    // This positions it back to looking at the highest card to add to the strength.
                    handStrength = 1800 + (j + 4);
                    return true;
                }
            }
            // Reset for next row.
            consecutiveCards = 0;
        }

        // Returns if a flush is not found.
        return false;
    }

    // Check for four of a kind.
    public boolean checkFourOfAKind() {
        // Four of a kind if 4 consecutive cards within the same column.
        int consecutiveCards = 0;

        // Iterate through the rows then move to the next column.
        for (int j = 14; j >= 0; j--) {
            for (int i = 0; i < cards.length; i++) {
                // Find how many cards appear consecutively.
                if (cards[i][j] == 1) {
                    consecutiveCards++;
                } else {
                    consecutiveCards = 0;
                }

                // If four cards are consecutive in the same column, the condition is met.
                if (consecutiveCards == 4) {
                    // Return the strength, with a bonus based on the value of the card's rank.
                    handStrength = 1600 + j;
                    return true;
                }
            }
            // Reset for the next row.
            consecutiveCards = 0;
        }

        // Return false if four of a kind are not found.
        return false;
    }

    // Looks for a full house.
    public boolean checkFullHouse() {
        // Full house if 3 of the same kind and two others.
        // A variable to store the column where the three consecutive cards are found.
        int consecutiveCardsThreeColumn = 14;

        // Counters to find out how many consecutive cards there are.
        int consecutiveCardsThree = 0;
        int consecutiveCardsTwo = 0;

        // Iterate through the rows then move to the next column.
        for (int j = 14; j >= 0; j--) {
            for (int i = 0; i < cards.length; i++) {
                // Look for consecutive positions occupied by 1s representing cards.
                if (cards[i][j] == 1) {
                    consecutiveCardsThree++;
                }

                // Stop the inner loop if three consecutive cards are found.
                if (consecutiveCardsThree == 3) {
                    break;
                }
            }

            // Stop the outer loop if three consecutive cards are found.
            if (consecutiveCardsThree == 3) {
                break;
            }

            // Reset the counter with each new column.
            consecutiveCardsThree = 0;

            // Represents that the column value decreases going left across the array.
            consecutiveCardsThreeColumn--;
        }

        // Check if two consecutive cards in a different position than the previous three.
        // Iterate through the rows then move to the next column.
        for (int j = 14; j >= 0; j--) {
            for (int i = 0; i < cards.length; i++) {
                // Check for two consecutive cards that are not in the same column as the previous three cards.
                if (cards[i][j] == 1 && j != consecutiveCardsThreeColumn) {
                    consecutiveCardsTwo++;
                }

                // Stop the inner loop if two consecutive cards are found.
                if (consecutiveCardsTwo == 2) {
                    break;
                }
            }

            // Stop the outer loop if two consecutive cards are found.
            if (consecutiveCardsTwo == 2) {
                break;
            }

            // Reset if nothing is found that satisfies the conditions.
            consecutiveCardsTwo = 0;
        }

        // True if three consecutive cards and two consecutive cards are found.
        if (consecutiveCardsThree == 3 && consecutiveCardsTwo == 2) {
            // Hand strength is 1400 plus the column the three cards are found.
            handStrength = 1400 + consecutiveCardsThreeColumn;
            return true;
        }

        // Returns false if a full house is not found.
        return false;
    }

    // Looks for a flush.
    public boolean checkFlush() {
        // Variables to store the position (column) of each card.
        int firstCardPosition = 0, secondCardPosition = 0, thirdCardPosition = 0, fourthCardPosition = 0, fifthCardPosition = 0;
        // Counts how many have the same suit.
        int sameSuitCount = 0;

        // Iterates through the columns then rows of the array (left to right, up to down).
        for (int i = 0; i < cards.length; i++) {
            for (int j = 14; j >= 0; j--) {
                if (cards[i][j] == 1) {
                    // Increases as cards of the same suit are found in a row.
                    sameSuitCount++;

                    // Sets the position of the card being found. First found card starts as the first card in a position.
                    // Every time a new card is found a new position is stored.
                    if (sameSuitCount == 1) {
                        firstCardPosition = j;
                    } else if (sameSuitCount == 2) {
                        secondCardPosition = j;
                    } else if (sameSuitCount == 3) {
                        thirdCardPosition = j;
                    } else if (sameSuitCount == 4) {
                        fourthCardPosition = j;
                    } else {
                        fifthCardPosition = j;
                    }
                }

                // Executes when the condition for a flush has been met.
                if (sameSuitCount == 5) {
                    // Using math to find a strength that accounts for all the cards in the flush,
                    // such that if two people have 4 cards with the same value the fifth card will
                    // be able to decide.
                    // Note, the values are multiplied in a way so that the digits in the decimal places represent the card values.
                    handStrength = 1200.0 + (firstCardPosition * 2) + secondCardPosition + (0.1 * thirdCardPosition)
                            + (0.001 * fourthCardPosition) + (0.00001 * fifthCardPosition);
                    return true;
                }
            }

            // Reset how many cards of the same suit are counted when the new row starts.
            sameSuitCount = 0;
            // The card positions do not need to be reset as they naturally overwrite when the new row starts.
        }

        // Return false if could not find a flush.
        return false;
    }

    // Looks for a straight.
    public boolean checkStraight() {
        // Counter for consecutive columns.
        int consecutiveColumns = 0;
        // Boolean to store whether a card was found in a column.
        boolean found = false;
        // Stores the position of the cards.
        int firstCardPosition = 0, secondCardPosition = 0, thirdCardPosition = 0, fourthCardPosition = 0, fifthCardPosition = 0;

        // Iterates through each column of the array.
        for (int j = 14; j >= 0; j--) {
            if (found == false) {
                // If found is false, reset the counter as the previous column was empty.
                consecutiveColumns = 0;
            }

            // Set found equal to false.
            found = false;

            // Iterate down the rows of a column.
            for (int i = 0; i < 4; i++) {
                if (cards[i][j] == 1) {
                    // Increases the counter and stores that something has been found if a card was found in the column.
                    consecutiveColumns++;
                    found = true;

                    // Sets the position of the card being found. First found card starts as the first card in a position.
                    // Every time a new card is found a new position is stored.
                    if (consecutiveColumns == 1) {
                        firstCardPosition = j;
                    } else if (consecutiveColumns == 2) {
                        secondCardPosition = j;
                    } else if (consecutiveColumns == 3) {
                        thirdCardPosition = j;
                    } else if (consecutiveColumns == 4) {
                        fourthCardPosition = j;
                    } else {
                        fifthCardPosition = j;
                    }

                    // Break the loop since finding multiple cards in the same column is not relevant.
                    break;
                }
            }

            // If five consecutive columns have cards a straight exists.
            if (consecutiveColumns == 5) {
                // Using math to find a strength that accounts for all the cards in the straight
                // such that if two people have 4 cards with the same value the fifth card will
                // be able to decide.
                // Note, the values are multiplied in a way so that the digits in the decimal places represent the card values.
                handStrength = 1000.0 + (firstCardPosition * 2) + secondCardPosition + (0.1 * thirdCardPosition)
                        + (0.001 * fourthCardPosition) + (0.00001 * fifthCardPosition);
                return true;
            }
        }

        // Return false if a straight is not found.
        return false;
    }

    // Checks for three of a kind.
    public boolean checkThreeOfAKind() {
        // Variable to store the column of the three of a kind.
        int consecutiveCardsThreeColumn = 14;
        // Counter variable to determine when there are three of a kind.
        int consecutiveCardsThree = 0;

        // Iterate through the rows then move to the next column.
        for (int j = 14; j >= 0; j--) {
            for (int i = 0; i < cards.length; i++) {
                if (cards[i][j] == 1) {
                    // Increase the counter if a card is found.
                    consecutiveCardsThree++;
                }

                // Stop the inner loop if three consecutive cards are found.
                if (consecutiveCardsThree == 3) {
                    break;
                }
            }

            // Stop the outer loop if three consecutive cards are found.
            if (consecutiveCardsThree == 3) {
                break;
            }

            // Reset when a new column is entered.
            consecutiveCardsThree = 0;

            // Decrease the variable representing the current column.
            consecutiveCardsThreeColumn--;
        }

        // Executes if there are three consecutive cards in the same column (same rank).
        if (consecutiveCardsThree == 3) {
            // Hand strength is 800 plus the column/rank value.
            handStrength = 800.0 + consecutiveCardsThreeColumn;
            return true;
        }

        // Return false if not found..
        return false;
    }

    // Checks for two pairs.
    public boolean checkTwoPair() {
        // Variables that store the column of each pair.
        int consecutiveCardsTwoPairOneColumn = 14;
        int consecutiveCardsTwoPairTwoColumn = 14;

        // Counters to tell how many in each column.
        int consecutiveCardsTwoPairOne = 0;
        int consecutiveCardsTwoPairTwo = 0;

        // Iterate through the rows then move to the next column.
        for (int j = 14; j >= 0; j--) {
            for (int i = 0; i < cards.length; i++) {
                if (cards[i][j] == 1) {
                    // Increase when a card/1 is found.
                    consecutiveCardsTwoPairOne++;
                }

                // Stop the inner loop if two consecutive cards are found.
                if (consecutiveCardsTwoPairOne == 2) {
                    break;
                }
            }

            // Stop the outer loop if two consecutive cards are found.
            if (consecutiveCardsTwoPairOne == 2) {
                break;
            }

            // Reset the counter before changing columns.
            consecutiveCardsTwoPairOne = 0;

            // Updates with the current column value of the loop.
            consecutiveCardsTwoPairOneColumn--;
        }

        // Check if two consecutive cards in a different position.
        // Iterate through the rows then move to the next column.
        for (int j = 14; j >= 0; j--) {
            for (int i = 0; i < cards.length; i++) {
                // Check for two consecutive cards that are not in the same column as the
                // previous three cards.
                if (cards[i][j] == 1 && j != consecutiveCardsTwoPairOneColumn) {
                    // Increase when a card/1 is found, and it is not in the same column as the first pair.
                    consecutiveCardsTwoPairTwo++;
                }

                // Stop the inner loop if two consecutive cards are found.
                if (consecutiveCardsTwoPairTwo == 2) {
                    break;
                }
            }

            // Stop the outer loop if two consecutive cards are found.
            if (consecutiveCardsTwoPairTwo == 2) {
                break;
            }

            // Reset the counter before changing columns.
            consecutiveCardsTwoPairTwo = 0;

            // Updates with the current column value of the loop.
            consecutiveCardsTwoPairTwoColumn--;
        }

        // Executes if a two pair is found.
        if (consecutiveCardsTwoPairOne == 2 && consecutiveCardsTwoPairTwo == 2) {
            // Returns 600 plus the strength of the columns.
            // Preference to the strength of the first pair column.
            handStrength = 600 + (consecutiveCardsTwoPairOneColumn * 2) + (consecutiveCardsTwoPairTwoColumn * 0.1);
            return true;
        }

        // Returns false if nothing found.
        return false;
    }

    // Looks for pairs of cards.
    public boolean checkPair() {
        // Stores the pair column and a counter to determine when there is a pair.
        int pairCount = 0;
        int pairColumn = 14;

        // Iterate through the rows then move to the next column.
        for (int j = 14; j >= 0; j--) {
            for (int i = 0; i < cards.length; i++) {
                if (cards[i][j] == 1) {
                    // Increase the counter if a card is found.
                    pairCount++;
                }

                // Break the inner loop if two consecutive cards are found in the same column.
                if (pairCount == 2) {
                    break;
                }
            }

            // Break the outer loop if two consecutive cards are found in the same column.
            if (pairCount == 2) {
                break;
            }

            // Reset the pair count before changing columns.
            pairCount = 0;

            // Update to reflect the new column.
            pairColumn--;
        }

        // Execute if a pair is found.
        if (pairCount == 2) {
            // Set hand strength to 400 + the pair column.
            // The multiple is not applied for any reason except to create separation between similar numbers.
            handStrength = 400 + (pairColumn * 2);
            return true;
        }

        // Return false if a pair is not found.
        return false;
    }

    // Method to find the high cards.
    public void highCard() {
        // Stores the column each card is found in.
        int highCardColumnOne = 0;
        int highCardColumnTwo = 0;
        int highCardColumnThree = 0;
        int highCardColumnFour = 0;
        int highCardColumnFive = 0;
        int highCardColumnSix = 0;
        int highCardColumnSeven = 0;

        // Finding highest seven cards.
        // Finding the first card.
        // Iterate through the rows then move to the next column.
        for (int j = 14; j >= 0; j--) {
            for (int i = 0; i < cards.length; i++) {
                if (cards[i][j] == 1) {
                    // Store the value of the column the card is found at.
                    highCardColumnOne = j;
                    break;
                }
            }
            // Break the loop when a card is found.
            if (highCardColumnOne != 0) {
                break;
            }
        }

        // Iterate through the rows then move to the next column.
        for (int j = 14; j >= 0; j--) {
            for (int i = 0; i < cards.length; i++) {
                // Selection to determine the card position is not at a column previously checked.
                // This is the only logic that changes in all of these loops to look for high cards.
                if (cards[i][j] == 1 && j != highCardColumnOne) {
                    // Store the value of the column the card is found at.
                    highCardColumnTwo = j;
                    break;
                }
            }
            // Break the loop when a card is found.
            if (highCardColumnTwo != 0) {
                break;
            }
        }

        // Same logic as above loop/high card checker for all of the ones below.
        for (int j = 14; j >= 0; j--) {
            for (int i = 0; i < cards.length; i++) {
                if (cards[i][j] == 1 && j != highCardColumnOne && j != highCardColumnTwo) {
                    highCardColumnThree = j;
                    break;
                }
            }
            if (highCardColumnThree != 0) {
                break;
            }
        }

        for (int j = 14; j >= 0; j--) {
            for (int i = 0; i < cards.length; i++) {
                if (cards[i][j] == 1 && j != highCardColumnOne && j != highCardColumnThree && j != highCardColumnTwo) {
                    highCardColumnFour = j;
                    break;
                }
            }
            if (highCardColumnFour != 0) {
                break;
            }
        }

        for (int j = 14; j >= 0; j--) {
            for (int i = 0; i < cards.length; i++) {
                if (cards[i][j] == 1 && j != highCardColumnOne && j != highCardColumnTwo && j != highCardColumnThree
                        && j != highCardColumnFour) {
                    highCardColumnFive = j;
                    break;
                }
            }
            if (highCardColumnFive != 0) {
                break;
            }
        }

        for (int j = 14; j >= 0; j--) {
            for (int i = 0; i < cards.length; i++) {
                if (cards[i][j] == 1 && j != highCardColumnOne && j != highCardColumnTwo && j != highCardColumnThree
                        && j != highCardColumnFour && j != highCardColumnFive) {
                    highCardColumnSix = j;
                    break;
                }
            }
            if (highCardColumnSix != 0) {
                break;
            }
        }

        for (int j = 14; j >= 0; j--) {
            for (int i = 0; i < cards.length; i++) {
                if (cards[i][j] == 1 && j != highCardColumnOne && j != highCardColumnTwo && j != highCardColumnThree
                        && j != highCardColumnFour && j != highCardColumnFive && j != highCardColumnSix) {
                    highCardColumnSeven = j;
                    break;
                }
            }
            if (highCardColumnSeven != 0) {
                break;
            }
        }

        // The high card strength is the value of all high cards added by a multiple so they do not interfere with each other and other card combinations.
        highCardHandStrength = (highCardColumnOne * 0.000001) + (highCardColumnTwo * 0.0000001) + (highCardColumnThree * 0.000000001) + (highCardColumnFour * 0.0000000001) + (highCardColumnFive * 0.000000000001) + (highCardColumnSix * 0.00000000000001) + (highCardColumnSeven * 0.0000000000000001);
    }
}
