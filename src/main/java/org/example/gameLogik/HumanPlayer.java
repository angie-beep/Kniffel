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
        System.out.println("\n" + getName() + ", wählen Sie eine Kategorie:");
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
                System.out.print("Ihre Wahl (1-" + categories.length + "): ");
                int choice = inputScanner.nextInt() - 1;
                inputScanner.nextLine(); // Puffer leeren

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
                inputScanner.nextLine(); // Ungültige Eingabe verwerfen
            }
        }
    }

    public void closeResources() {
        inputScanner.close();
    }
}