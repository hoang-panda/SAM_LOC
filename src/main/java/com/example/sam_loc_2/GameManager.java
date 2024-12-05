package com.example.sam_loc_2;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private GameLogger logger;
    private GameScoreTracker scoreTracker;
    public static List<Player> players;
    private int currentPlayerIndex;
    private List<Card> cardsInTable; // Lưu bài của lượt trước
    private final GameLogic gameLogic;
    public GameManager(GameLogic gameLogic){
        this.gameLogic = gameLogic;
        this.logger = new GameLogger();
        this.scoreTracker = new GameScoreTracker();
    }

    public GameManager(List<Player> players) {
        this.players = players;
        this.currentPlayerIndex = 0;
        this.cardsInTable = new ArrayList<>();
        this.gameLogic = new GameLogic();
    }

    public List<Player> getPlayers() {
        return this.players;
    }
    // Thêm người chơi AI vào game
    public static void addAIPlayer(AI_Player aiPlayer) {
        players.add(aiPlayer);
    }
    public void endGame(Player winner) {
        logger.log("Game won by: " + winner.getName());
        scoreTracker.updateScore(winner, players);
    }


    // Xử lý lượt chơi tiếp theo
    public void nextTurn(List<Card> selectedCards) {
        Player currentPlayer = players.get(currentPlayerIndex);

        // Nếu là AI_Player, tự động thực hiện nước đi
        if (currentPlayer instanceof AI_Player) {
            AI_Player aiPlayer = (AI_Player) currentPlayer;

            // AI tự động chọn bài
            List<Card> aiSelectedCards = aiPlayer.makeMove(cardsInTable);

            // Kiểm tra xem bài AI chọn có hợp lệ không
            if (!gameLogic.isValidMove(aiSelectedCards)) {
                currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
                return ;
            }

            if (!gameLogic.canBeatPreviousMove(aiSelectedCards, cardsInTable)) {
                currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
                return ;
            }

            // Cập nhật bài đã đánh
            cardsInTable = aiSelectedCards;
            currentPlayer.removePlayedCards(aiSelectedCards);

            // Kiểm tra điều kiện thắng
            if (currentPlayer.countCards() == 0) {
                System.out.println("Player " + currentPlayer.getName() + " wins!");
                return;
            }

            // Chuyển lượt sang người chơi tiếp theo
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        } else {
            // Đối với người chơi thường, xử lý nước đi được truyền vào
            if (!gameLogic.isValidMove(selectedCards)) {
                throw new IllegalArgumentException("LƯỢT ĐI KHÔNG HỢP LỆ. THỬ LẠI!");
            }

            if (!gameLogic.canBeatPreviousMove(selectedCards, cardsInTable)) {
                throw new IllegalArgumentException("BÀI CỦA BẠN KHÔNG THỂ ĐÁNH!!!");
            }

            // Cập nhật bài đã đánh
            cardsInTable = selectedCards;
            currentPlayer.removePlayedCards(selectedCards);

            // Kiểm tra điều kiện thắng
            if (currentPlayer.countCards() == 0) {
                System.out.println("Player " + currentPlayer.getName() + " wins!");
                return;
            }

            // Chuyển lượt sang người chơi tiếp theo
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        }
    }

    public String getGameState() {
        StringBuilder state = new StringBuilder();
        state.append("Current Player: ").append(getCurrentPlayer().getName()).append("\n");
        state.append("Cards on Table: ").append(cardsInTable.isEmpty() ? "None" : cardsInTable).append("\n");
        for (Player player : players) {
            state.append(player.getName()).append("'s hand: ").append(player.getHand().size()).append(" cards\n");
        }
        return state.toString();
    }



    // Kiểm tra điều kiện thắng
    public boolean checkWinCondition(Player player) {
        return player.countCards() == 0;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }
}

