package org.example.gameLogik;

import java.util.EnumMap;
import java.util.Map;

public class ScoreCard {
    public enum Category {
        EINSER, ZWEIER, DREIER, VIERER, FUENFER, SECHSER,
        DREIERPASCH, VIERERPASCH, FULL_HOUSE(25),
        KLEINE_STRASSE(30), GROSSE_STRASSE(40),
        KNIFFEL(50), CHANCE;

        private final int fixedValue;

        Category() { this(0); }
        Category(int fixedValue) { this.fixedValue = fixedValue; }

        public boolean isUpperSection() {
            return ordinal() <= SECHSER.ordinal();
        }
    }

    private final Map<Category, Integer> scores;
    private int totalScore;
    private boolean bonus;

    public ScoreCard() {
        scores = new EnumMap<>(Category.class);
        for (Category c : Category.values()) {
            scores.put(c, null);
        }
        totalScore = 0;
        bonus = false;
    }

    public void setScore(Category category, int value) {
        if (category == null) {
            throw new IllegalArgumentException("Kategorie darf nicht null sein");
        }
        if (scores.get(category) != null) {
            throw new IllegalStateException("Kategorie wurde bereits verwendet");
        }

        scores.put(category, category.fixedValue > 0 ?
                (value > 0 ? category.fixedValue : 0) :
                Math.max(value, 0));
        calculateTotal();
    }

    public Integer getScore(Category category) {
        return scores.get(category);
    }

    public boolean isCategoryUsed(Category category) {
        return scores.get(category) != null;
    }

    private void calculateTotal() {
        int upperSum = 0;
        for (Category c : Category.values()) {
            if (c.isUpperSection() && scores.get(c) != null) {
                upperSum += scores.get(c);
            }
        }

        bonus = upperSum >= 63;
        int bonusPoints = bonus ? 35 : 0;

        int lowerSum = 0;
        for (Category c : Category.values()) {
            if (!c.isUpperSection() && scores.get(c) != null) {
                lowerSum += scores.get(c);
            }
        }

        totalScore = upperSum + bonusPoints + lowerSum;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public boolean hasBonus() {
        return bonus;
    }

    public boolean isComplete() {
        return !scores.containsValue(null);
    }
}