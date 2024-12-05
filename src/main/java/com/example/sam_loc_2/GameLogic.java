package com.example.sam_loc_2;

import java.util.List;
import java.util.stream.Collectors;

public class GameLogic {
    private static final List<String> RANK_ORDER = List.of(
            "2", "Ace", "King", "Queen", "Jack", "10", "9", "8", "7", "6", "5", "4", "3"
    );

    private static final List<String> RANK_SEQUENCE = List.of(
            "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"
    );

    // Kiểm tra tính hợp lệ của bài được chọn để đánh
        public static boolean isValidMove(List<Card> cards) {
            if (cards == null || cards.isEmpty()) return false;

            // Kiểm tra nếu là lá đơn
            if (cards.size() == 1) return true;

            // Kiểm tra các lá có cùng rank
            boolean sameRank = cards.stream()
                    .allMatch(card -> card.getRank().equals(cards.get(0).getRank()));
            if (sameRank) return true;

            // Kiểm tra dãy bài
            return isValidSequence(cards);
        }

        private static boolean isValidSequence(List<Card> cards) {
            // Logic kiểm tra dãy bài phức tạp hơn
            List<Integer> rankValues = cards.stream()
                    .map(Card::getRankValue)
                    .sorted()
                    .collect(Collectors.toList());

            // Kiểm tra tính liên tục của rank
            for (int i = 1; i < rankValues.size(); i++) {
                if (rankValues.get(i) - rankValues.get(i-1) != 1) {
                    return false;
                }
            }

            return true;
        }

        public static boolean canBeatPreviousMove(List<Card> previousMove, List<Card> currentMove) {
            if (previousMove == null || previousMove.isEmpty()) return true;

            // Kiểm tra số lượng lá bài
            if (currentMove.size() != previousMove.size()) {
                // Trường hợp đặc biệt với lá "2"
                if (previousMove.size() == 1 &&
                        previousMove.get(0).getRank().equals("2") &&
                        currentMove.size() == 4) {
                    return currentMove.stream()
                            .allMatch(card -> card.getRank().equals(currentMove.get(0).getRank()));
                }
                return false;
            }

            // So sánh rank
            return currentMove.get(0).getRankValue() > previousMove.get(0).getRankValue();
        }
    }

