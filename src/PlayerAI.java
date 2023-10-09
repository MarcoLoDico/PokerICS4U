class PlayerAI extends Player {
    public String betAnswer() {

        // Sets default to fold to avoid errors.
        String betAnswer = "fold";

        // Gets the hand's strength.
        double handStrength = getHandValue();

        // Stores whether a raise is possible.
        boolean raisePossible = true;

        // Generates a random number between 1 and 100.
        int random = (int) ((Math.random() * 100) + 1);

        // Fold if not enough money to continue betting.
        if (money < mustBet) {
            return betAnswer = "fold";
        }

        // If no more money than what must be paid, the player cannot raise.
        if (money == mustBet) {
            raisePossible = false;
        }

        // Executes when there are 7 cards available.
        // This is important because high hand strengths are not attainable until more than two cards are available.
        // Therefore, probabilities should be adjusted accordingly.
        if (hand.getCardCount() == 7) {

            // Determines, based on hand strength, whether to fold, raise, or check.
            if (handStrength >= 1800) {
                // Strong hand, no reason not to raise.
                if(raisePossible == true)
                {
                    // 100% chance to raise if possible.
                    return betAnswer = "raise";
                }
                else{
                    // Check if cannot raise.
                    return betAnswer = "check";
                }
            } else if (handStrength >= 1200) {
                // Executes if the strength of the hand is greater than or equal to 1200 (flush).
                if (random > 50 && raisePossible == true) {
                    // 50% chance to raise.
                    return betAnswer = "raise";
                } else {
                    // 50% chance to check.
                    return betAnswer = "check";
                }
            } else if (handStrength >= 800) {
                // Executes if the strength of the hand is greater than or equal to 800 (three of a kind).
                if (random > 75 && raisePossible == true) {
                    // 25 % Chance to raise.
                    return betAnswer = "raise";
                }
                else if (random > 5)
                {
                    // 70% chance to check.
                    return betAnswer = "check";
                }
                else
                {
                    // 5% chance to fold.
                    if (mustBet > 0) {
                        // Fold if must bet anything to check.
                        return betAnswer = "fold";
                    } else {
                        // Otherwise check because nothing to lose.
                        return betAnswer = "check";
                    }
                }

            } else if (handStrength >= 400) {
                // Executes if the strength of the hand is greater than or equal to 400 (pair).
                if (random > 85 && raisePossible == true) {
                    // 15 % Chance to raise.
                    return betAnswer = "raise";
                }
                else if (random > 10)
                {
                    // 75% chance to check.
                    return betAnswer = "check";
                }
                else
                {
                    // 10% chance to fold if must bet anything.
                    if (mustBet > 0) {
                        // Fold if must bet anything to check.
                        return betAnswer = "fold";
                    } else {
                        // Otherwise check because nothing to lose.
                        return betAnswer = "check";
                    }
                }
            } else if (handStrength >= 0) {
                // Executes if the strength of the hand is greater than or equal to 0 (high card).
                // 30% chance to check
                if (random > 70)
                {
                    return betAnswer = "check";
                }
                else
                {
                    // 70% chance to fold if must bet anything.
                    if (mustBet > 0) {
                        // Fold if must bet anything to check.
                        return betAnswer = "fold";
                    } else {
                        // Otherwise check because nothing to lose.
                        return betAnswer = "check";
                    }
                }
            } else {
                return betAnswer = "fold";
            }
        }
        else if (hand.getCardCount() == 6) // If only 6 cards.
        {
            // This is the same logic as the above if branch, it just has slightly higher
            // possibilities of checking and raising for each strength since a player naturally has a weaker hand
            // with only 6 cards.
            if (handStrength >= 1800) {
                if(raisePossible == true)
                {
                    // 100% chance to raise if possible.
                    return betAnswer = "raise";
                }
                else{
                    // Check if cannot raise.
                    return betAnswer = "check";
                }
            } else if (handStrength >= 800) {
                if (random > 70 && raisePossible == true) {
                    // 30 % Chance to raise.
                    return betAnswer = "raise";
                }
                else if (random > 5)
                {
                    // 65% chance to check.
                    return betAnswer = "check";
                }
                else
                {
                    // 5% chance to fold.
                    if (mustBet > 0) {
                        // Fold if must bet anything to check.
                        return betAnswer = "fold";
                    } else {
                        // Otherwise check because nothing to lose.
                        return betAnswer = "check";
                    }
                }
            } else if (handStrength >= 200) {
                if (random > 90 && raisePossible == true) {
                    // 10 % Chance to raise.
                    return betAnswer = "raise";
                }
                else if (random > 40)
                {
                    // 50% chance to check.
                    return betAnswer = "check";
                }
                else
                {
                    // 40% chance to fold if must bet anything.
                    if (mustBet > 0) {
                        // Fold if must bet anything to check.
                        return betAnswer = "fold";
                    } else {
                        // Otherwise check because nothing to lose.
                        return betAnswer = "check";
                    }
                }
            } else if (handStrength >= 0) {
                // 50% chance to check
                if (random > 50)
                {
                    return betAnswer = "check";
                }
                else
                {
                    // 70% chance to fold if must bet anything.
                    if (mustBet > 0) {
                        // Fold if must bet anything to check.
                        return betAnswer = "fold";
                    } else {
                        // Otherwise check because nothing to lose.
                        return betAnswer = "check";
                    }
                }
            } else {
                return betAnswer = "fold";
            }
        }
        else if (hand.getCardCount() == 5)
        {
            // This is the same logic as the above if branch, it just has slightly higher
            // possibilities of checking and raising since a player naturally has a weaker hand with only 5 cards.
            if (handStrength >= 1400) {
                if(raisePossible == true)
                {
                    // 100% chance to raise if possible.
                    return betAnswer = "raise";
                }
                else{
                    // Check if cannot raise.
                    return betAnswer = "check";
                }
            } else if (handStrength >= 200) {
                if (random > 80 && raisePossible == true) {
                    // 20 % Chance to raise.
                    return betAnswer = "raise";
                }
                else if (random > 20)
                {
                    // 60% chance to check.
                    return betAnswer = "check";
                }
                else
                {
                    // 20% chance to fold if must bet anything.
                    if (mustBet > 0) {
                        // Fold if must bet anything to check.
                        return betAnswer = "fold";
                    } else {
                        // Otherwise check because nothing to lose.
                        return betAnswer = "check";
                    }
                }
            } else if (handStrength >= 0) {
                // 70% chance to check
                if (random > 30)
                {
                    return betAnswer = "check";
                }
                else
                {
                    // 30% chance to fold if must bet anything.
                    if (mustBet > 0) {
                        // Fold if must bet anything to check.
                        return betAnswer = "fold";
                    } else {
                        // Otherwise check because nothing to lose.
                        return betAnswer = "check";
                    }
                }
            } else {
                return betAnswer = "fold";
            }
        }
        else if (hand.getCardCount() == 2)
        {
            // Using else if, instead of else, to avoid unforeseen errors.
            // This is the same logic as the above if branch, it just has slightly higher
            // possibilities of checking and raising since a player naturally has a weaker hand with only 2 cards.
            if (handStrength >= 200) {
                if (random > 70 && raisePossible == true) {
                    // 30 % Chance to raise.
                    return betAnswer = "raise";
                }
                else
                {
                    // 70% chance to check
                    return betAnswer = "check";
                }
            }  else if (handStrength >= 0) {
                if (random > 90 && raisePossible == true) {
                    // 10 % Chance to raise.
                    return betAnswer = "raise";
                }
                else if (mustBet >= 100 && random > 75)
                {
                    // 15% chance to fold if the bet is getting too high.
                    return betAnswer = "fold";
                }
                else
                {
                    // 75% chance to check.
                    return betAnswer = "check";
                }
            } else {
                // Account for unforeseen circumstances.
                return betAnswer = "fold";
            }
        }

        // Return whatever the answer is.
        return betAnswer;
    }

    // Method to find the amount to raise the bet
    public int raiseAmount() {
        // Stores the amount the player will raise.
        int raiseAnswer;
        // Sets a maximum amount the player can raise.
        int maxRaise;

        // The maximum a player can raise is equal to their money minus how much they must bet.
        maxRaise = money - mustBet;

        // Generate a random number to raise.
        raiseAnswer = (int) ((Math.random() * maxRaise) + 1);

        // Reduce the raise by a certain amount since values were getting too high during playtesting.
        // Higher raise values are adjust down to a greater degree.
        if (raiseAnswer > 400)
        {
            raiseAnswer = raiseAnswer / 8;
        }
        else if (raiseAnswer > 200)
        {
            raiseAnswer = raiseAnswer / 6;
        }
        else if (raiseAnswer > 30)
        {
            raiseAnswer = raiseAnswer / 4;
        }

        // Returns the amount the player wants to raise.
        return raiseAnswer;
    }
}



