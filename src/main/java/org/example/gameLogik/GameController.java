package org.example.gameLogik;

public class GameController {
    private final Player player;
    private final Dice[] dices;
    private int rollCount;
    private boolean gameOver;
    private GameState gameState;

    public enum GameState {
        ROLLING, SELECTING_CATEGORY, GAME_OVER
    }

    public GameController(String playerName) {
        this.player = new HumanPlayer(playerName);

        this.dices = new Dice[5];
        for (int i = 0; i < 5; i++) {
            dices[i] = new Dice();
        }

        this.rollCount = 0;
        this.gameOver = false;
        this.gameState = GameState.ROLLING;
    }

    public void rollDice() {
        if (gameState != GameState.ROLLING || rollCount >= 3) {
            return;
        }

        player.rollDice(dices);
        rollCount++;

        if (rollCount == 3) {
            gameState = GameState.SELECTING_CATEGORY;
        }
    }

    public void toggleDieHold(int dieIndex) {
        if (gameState == GameState.ROLLING && dieIndex >= 0 && dieIndex < 5) {
            player.toggleDieHold(dieIndex);
        }
    }

    public void selectCategory(ScoreCard.Category category) {
        if (gameState != GameState.SELECTING_CATEGORY || category == null) {
            return;
        }

        player.scoreInCategory(category, dices);

        if (player.getScoreCard().isComplete()) {
            gameState = GameState.GAME_OVER;
            gameOver = true;
        } else {
            startNewTurn();
        }
    }

    private void startNewTurn() {
        rollCount = 0;
        player.resetTurn();
        gameState = GameState.ROLLING;
    }

    public Player getPlayer() {
        return player;
    }

    public Dice[] getDices() {
        return dices.clone();
    }

    public int getRollCount() {
        return rollCount;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public GameState getGameState() {
        return gameState;
    }
}