package org.example.gameLogik;

public abstract class Player {
    private final String name;
    private final ScoreCard scoreCard;
    private int rollsRemaining;
    private boolean[] diceHeld;

    public Player(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be null or empty");
        }
        this.name = name;
        this.scoreCard = new ScoreCard();
        this.rollsRemaining = 3; // Maximal 3 Würfe pro Runde
        this.diceHeld = new boolean[5]; // Für 5 Würfel
    }


    public void toggleDieHold(int dieIndex) {
        if (dieIndex < 0 || dieIndex >= 5) {
            throw new IllegalArgumentException("Die index must be between 0 and 4");
        }
        diceHeld[dieIndex] = !diceHeld[dieIndex];
    }


    public abstract void scoreInCategory(Dice[] dice);

    public void rollDice(Dice[] dice) {
        if (dice == null || dice.length != 5) {
            throw new IllegalArgumentException("Dice array must contain exactly 5 elements");
        }

        if (rollsRemaining > 0) {
            for (int i = 0; i < dice.length; i++) {
                if (!diceHeld[i]) {
                    dice[i].roll();
                }
            }
            rollsRemaining--;
        }
    }

    public void resetTurn() {
        rollsRemaining = 3;
        for (int i = 0; i < diceHeld.length; i++) {
            diceHeld[i] = false;
        }
    }

    public void scoreInCategory(ScoreCard.Category category, Dice[] dice) {
        int score = calculateScoreForCategory(category, dice);
        scoreCard.setScore(category, score);
        resetTurn();
    }

    public int calculateScoreForCategory(ScoreCard.Category category, Dice[] dice) {
        int[] counts = new int[6];
        for (Dice d : dice) {
            counts[d.getValue() - 1]++;
        }

        switch (category) {
            case EINSER:
                return counts[0] * 1;
            case ZWEIER:
                return counts[1] * 2;
            case DREIER:
                return counts[2] * 3;
            case VIERER:
                return counts[3] * 4;
            case FUENFER:
                return counts[4] * 5;
            case SECHSER:
                return counts[5] * 6;

            case DREIERPASCH:
                return isNOfAKind(counts, 3) ? sumDice(dice) : 0;
            case VIERERPASCH:
                return isNOfAKind(counts, 4) ? sumDice(dice) : 0;
            case FULL_HOUSE:
                return isFullHouse(counts) ? 25 : 0;
            case KLEINE_STRASSE:
                return isSmallStraight(counts) ? 30 : 0;
            case GROSSE_STRASSE:
                return isLargeStraight(counts) ? 40 : 0;
            case KNIFFEL:
                return isKniffel(counts) ? 50 : 0;
            case CHANCE:
                return sumDice(dice);
            default:
                return 0;
        }
    }
    private boolean isFullHouse(int[] counts) {
        boolean hasTwo = false, hasThree = false;
        for (int count : counts) {
            if (count == 2) hasTwo = true;
            if (count == 3) hasThree = true;
        }
        return hasTwo && hasThree;
    }

    private boolean isSmallStraight(int[] counts) {
        return (counts[0] >= 1 && counts[1] >= 1 && counts[2] >= 1 && counts[3] >= 1) ||
                (counts[1] >= 1 && counts[2] >= 1 && counts[3] >= 1 && counts[4] >= 1) ||
                (counts[2] >= 1 && counts[3] >= 1 && counts[4] >= 1 && counts[5] >= 1);
    }

    private boolean isLargeStraight(int[] counts) {
        return (counts[0] >= 1 && counts[1] >= 1 && counts[2] >= 1 && counts[3] >= 1 && counts[4] >= 1) ||
                (counts[1] >= 1 && counts[2] >= 1 && counts[3] >= 1 && counts[4] >= 1 && counts[5] >= 1);
    }

    private boolean isKniffel(int[] counts) {
        for (int count : counts) {
            if (count == 5) return true;
        }
        return false;
    }

    private int sumDice(Dice[] dice) {
        int sum = 0;
        for (Dice d : dice) {
            sum += d.getValue();
        }
        return sum;
    }

    private boolean isNOfAKind(int[] counts, int n) {
        for (int count : counts) {
            if (count >= n) return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public ScoreCard getScoreCard() {
        return scoreCard;
    }

    public int getRollsRemaining() {
        return rollsRemaining;
    }

    public boolean isDieHeld(int index) {
        return diceHeld[index];
    }

    @Override
    public String toString() {
        return name + " (Punkte: " + scoreCard.getTotalScore() + ")";
    }
}