package org.example.gameLogik;

import java.util.Scanner;

public class HumanPlayer extends Player {
    private final Scanner inputScanner;

    public HumanPlayer(String name) {
        super(name);
        this.inputScanner = new Scanner(System.in);
    }

    @Override
    public void scoreInCategory(Dice[] dice) {
        if (dice == null || dice.length != 5) {
            throw new IllegalArgumentException("Invalid dice array");
        }

        System.out.println("\n" + getName() + ", wähle eine Kategorie:");
        printAvailableCategories();

        ScoreCard.Category selectedCategory = selectCategory();
        super.scoreInCategory(selectedCategory, dice);
    }

    private void printAvailableCategories() {
        System.out.println("Verfügbare Kategorien:");
        ScoreCard scoreCard = getScoreCard();

        for (ScoreCard.Category category : ScoreCard.Category.values()) {
            if (!scoreCard.isCategoryUsed(category)) {
                System.out.printf("%d. %s%n", category.ordinal() + 1, formatCategoryName(category));
            }
        }
    }

    private String formatCategoryName(ScoreCard.Category category) {
        return category.name().toLowerCase()
                .replace('_', ' ')
                .replace("er", "er")
                .replace("pasch", "Pasch")
                .replace("house", "House")
                .replace("strasse", "Straße");
    }

    private ScoreCard.Category selectCategory() {
        ScoreCard.Category[] categories = ScoreCard.Category.values();
        while (true) {
            try {
                System.out.print("Deine Wahl (1-" + categories.length + "): ");
                int choice = inputScanner.nextInt() - 1;
                inputScanner.nextLine(); // Clear buffer

                if (choice >= 0 && choice < categories.length) {
                    ScoreCard.Category selected = categories[choice];
                    if (!getScoreCard().isCategoryUsed(selected)) {
                        return selected;
                    }
                    System.out.println("Diese Kategorie wurde bereits verwendet!");
                } else {
                    System.out.println("Ungültige Eingabe! Bitte Zahl zwischen 1 und " + categories.length + " eingeben.");
                }
            } catch (Exception e) {
                System.out.println("Bitte eine gültige Zahl eingeben!");
                inputScanner.nextLine(); // Clear invalid input
            }
        }
    }

    public void selectDiceToHold(Dice[] dice) {
        System.out.println("\nAktuelle Würfel:");
        for (int i = 0; i < dice.length; i++) {
            System.out.printf("%d: %s %s%n",
                    i + 1,
                    dice[i].getValue(),
                    isDieHeld(i) ? "[GEHALTEN]" : "");
        }

        System.out.println("Welche Würfel möchtest du halten/freigeben? (1-5, 0 zum Fertig)");
        while (true) {
            System.out.print("Wahl: ");
            try {
                int choice = inputScanner.nextInt();
                if (choice == 0) break;
                if (choice >= 1 && choice <= 5) {
                    toggleDieHold(choice - 1);
                    System.out.println("Würfel " + choice + " wurde " +
                            (isDieHeld(choice - 1) ? "gehalten" : "freigegeben"));
                }
            } catch (Exception e) {
                System.out.println("Ungültige Eingabe!");
                inputScanner.nextLine(); // Clear invalid input
            }
        }
    }

    @Override
    public void rollDice(Dice[] dice) {
        System.out.println("\n" + getName() + " würfelt...");
        super.rollDice(dice);
        System.out.println("Verbleibende Würfe: " + getRollsRemaining());
    }

    public void closeResources() {
        inputScanner.close();
    }
}