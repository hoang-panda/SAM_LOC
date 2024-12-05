package com.example.sam_loc_2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GameScoreTracker {
    private Map<Player, Integer> playerScores = new HashMap<>();
    private Map<Player, Integer> gamesWon = new HashMap<>();

    public void updateScore(Player winner, List<Player> players) {
        // Tính điểm dựa trên số lá bài còn lại của các người chơi khác
        int baseScore = players.stream()
                .filter(p -> p != winner)
                .mapToInt(p -> p.getHand().size())
                .sum();

        playerScores.merge(winner, baseScore, Integer::sum);
        gamesWon.merge(winner, 1, Integer::sum);
    }

    public int getPlayerScore(Player player) {
        return playerScores.getOrDefault(player, 0);
    }

    public int getPlayerGamesWon(Player player) {
        return gamesWon.getOrDefault(player, 0);
    }

    public List<Player> getLeaderboard() {
        return playerScores.entrySet().stream()
                .sorted(Map.Entry.<Player, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
