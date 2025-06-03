package org.example.gameLogik;

public class GameController {
    private Player[] players;
    private int currentPlayerIndex;
    private Dice[] dices;
    private int rollCount;
    private boolean gameOver;
    private GameState gameState;

    public enum GameState {
        ROLLING, SELECTING_CATEGORY, GAME_OVER
    }

    public GameController(int gameMode, int playerCount) {
        if (playerCount < 1 || playerCount > 4) {
            throw new IllegalArgumentException("Player count must be between 1 and 4");
        }

        players = new Player[playerCount];
        for (int i = 0; i < playerCount; i++) {
            players[i] = new HumanPlayer("Spieler " + (i + 1));
        }

        dices = new Dice[5];
        for (int i = 0; i < 5; i++) {
            dices[i] = new Dice();
        }

        currentPlayerIndex = 0;
        rollCount = 0;
        gameOver = false;
        gameState = GameState.ROLLING;
    }

    public void rollDice() {
        if (gameState != GameState.ROLLING || rollCount >= 3) {
            return;
        }

        Player currentPlayer = getCurrentPlayer();
        currentPlayer.rollDice(dices);
        rollCount++;

        if (rollCount == 3) {
            gameState = GameState.SELECTING_CATEGORY;
        }
    }

    public void toggleDieHold(int dieIndex) {
        if (gameState == GameState.ROLLING && dieIndex >= 0 && dieIndex < 5) {
            getCurrentPlayer().toggleDieHold(dieIndex);
        }
    }

    public void selectCategory(ScoreCard.Category category) {
        if (gameState != GameState.SELECTING_CATEGORY || category == null) {
            return;
        }

        Player currentPlayer = getCurrentPlayer();
        currentPlayer.scoreInCategory(category, dices);

        if (isGameComplete()) {
            gameState = GameState.GAME_OVER;
            gameOver = true;
        } else {
            nextPlayer();
            gameState = GameState.ROLLING;
        }
    }

    private void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
        rollCount = 0;
        getCurrentPlayer().resetTurn();
    }

    public boolean isGameComplete() {
        for (Player player : players) {
            if (!player.getScoreCard().isComplete()) {
                return false;
            }
        }
        return true;
    }

    public Player getCurrentPlayer() {
        return players[currentPlayerIndex];
    }

    public Player[] getPlayers() {
        return players.clone();
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

    public Player getWinner() {
        if (!gameOver) return null;

        Player winner = players[0];
        for (Player player : players) {
            if (player.getScoreCard().getTotalScore() > winner.getScoreCard().getTotalScore()) {
                winner = player;
            }
        }
        return winner;
    }
}