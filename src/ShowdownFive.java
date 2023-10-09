class ShowdownFive extends Showdown
{
    // This class is the exact same as the main showdown class. It is only changed to take 5 cards instead of 7.
    public double getCards(Card one, Card two, Card three, Card four, Card five) {
        // Add five cards to the hand.
        hand.addCard(one);
        hand.addCard(two);
        hand.addCard(three);
        hand.addCard(four);
        hand.addCard(five);

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
            return highCardHandStrength;
        }
    }
}