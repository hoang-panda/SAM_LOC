package com.example.sam_loc_2;

import java.util.ArrayList;
import java.util.List;

public class AI_Player extends Player {
    private List<Card> hand = new ArrayList<>();

    public AI_Player(String name) {
        super(name);
    }

    /**
     * Lựa chọn bài để đánh dựa trên bài hiện có và bài trên bàn.
     * @param cardsInTable bài đang có trên bàn (bài của người liền trước).
     * @return danh sách các lá bài AI sẽ đánh hoặc null nếu không đánh được.
     */
    public List<Card> chooseMove(List<Card> cardsInTable, GameLogic gameLogic) {
        List<Card> cardsToPlay = null;

        // Nếu chưa có bài trên bàn, AI đánh bài bất kỳ hợp lệ.
        if (cardsInTable.isEmpty()) {
            cardsToPlay = findBestStartingMove(gameLogic);
        } else {
            cardsToPlay = findBestMoveToBeat(cardsInTable, gameLogic);
        }

        return cardsToPlay;
    }

    public List<Card> makeMove(List<Card> cardsInTable) {
        // Logic đơn giản: AI đánh bài hợp lệ nhỏ nhất
        List<Card> validMove = new ArrayList<>();
        for (Card card : getHand()) {
            validMove.add(card);
            if (GameLogic.isValidMove(validMove) &&
                    GameLogic.canBeatPreviousMove(validMove, cardsInTable)) {
                return validMove;
            }
            validMove.clear(); // Nếu không hợp lệ, thử bài khác
        }

        // Nếu không thể đánh bài, AI bỏ lượt (có thể trả về danh sách rỗng hoặc null)
        return validMove;
    }

    /**
     * Tìm nước đi khởi đầu tốt nhất.
     * @return danh sách bài để đánh.
     */
    private List<Card> findBestStartingMove(GameLogic gameLogic) {
        // Ưu tiên đánh dây hoặc đôi nếu có.
        for (int i = 3; i <= getHand().size(); i++) {
            List<Card> subset = getSubsetOfSize(i);
            if (gameLogic.isValidMove(subset)) {
                return subset;
            }
        }

        // Nếu không có dây hoặc đôi, đánh 1 lá.
        return List.of(getHand().get(0));
    }

    /**
     * Tìm nước đi để chặn bài trên bàn.
     * @param cardsInTable bài trên bàn.
     * @return danh sách bài để đánh.
     */
    private List<Card> findBestMoveToBeat(List<Card> cardsInTable, GameLogic gameLogic) {
        // Kiểm tra tất cả các tập hợp bài trong tay để tìm bài hợp lệ thắng bài trên bàn.
        for (int i = cardsInTable.size(); i <= getHand().size(); i++) {
            List<Card> subset = getSubsetOfSize(i);
            if (gameLogic.canBeatPreviousMove(subset, cardsInTable)) {
                return subset;
            }
        }

        // Nếu không có bài thắng được, bỏ lượt.
        return null;
    }

    /**
     * Lấy tập hợp bài có kích thước nhất định.
     * @param size kích thước tập hợp.
     * @return tập hợp bài.
     */
    private List<Card> getSubsetOfSize(int size) {
        List<Card> subset = new ArrayList<>();
        for (int i = 0; i < size && i < getHand().size(); i++) {
            subset.add(getHand().get(i));
        }
        return subset;
    }

    // Thêm bài vào tay của AI
    public void addCards(List<Card> cards) {
        this.getHand().addAll(cards);
    }
}


