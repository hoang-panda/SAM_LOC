package com.example.sam_loc_2;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AIStrategy {
    private AIDifficulty difficulty;

    public AIStrategy(AIDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public List<Card> chooseMove(List<Card> hand, List<Card> tableCards) {
        switch (difficulty) {
            case EASY:
                return chooseEasyMove(hand, tableCards);
            case MEDIUM:
                return chooseMediumMove(hand, tableCards);
            case HARD:
                return chooseHardMove(hand, tableCards);
            default:
                throw new IllegalStateException("Unexpected difficulty");
        }
    }

    private List<Card> chooseEasyMove(List<Card> hand, List<Card> tableCards) {
        // Logic đánh bài đơn giản nhất
        return hand.subList(0, Math.min(hand.size(), tableCards.size()));
    }

    private List<Card> chooseMediumMove(List<Card> hand, List<Card> tableCards) {
        // Logic thông minh hơn, ưu tiên đánh bài có thể loại bỏ nhiều nhất
        return hand.stream()
                .filter(card -> GameLogic.canBeatPreviousMove(List.of(card), tableCards))
                .findFirst()
                .map(List::of)
                .orElse(Collections.emptyList());
    }

    private List<Card> chooseHardMove(List<Card> hand, List<Card> tableCards) {
        // Logic phức tạp nhất, phân tích nhiều khả năng
        return hand.stream()
                .filter(card -> GameLogic.canBeatPreviousMove(List.of(card), tableCards))
                .min(Comparator.comparingInt(Card::getRankValue))
                .map(List::of)
                .orElse(Collections.emptyList());
    }
}
