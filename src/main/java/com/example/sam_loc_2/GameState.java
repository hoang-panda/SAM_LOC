package com.example.sam_loc_2;

import java.util.List;

public class GameState {
    private List<Player> players;
    private List<Card> currentTableCards;
    private int currentPlayerIndex;
    private boolean gameOver;

    // Các phương thức quản lý trạng thái game
    public void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public boolean checkGameOver() {
        return players.stream().anyMatch(player -> player.getCards().isEmpty());
    }
}
