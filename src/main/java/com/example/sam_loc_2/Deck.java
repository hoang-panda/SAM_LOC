package com.example.sam_loc_2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        String[] suits = {"Hearts", "Clubs", "Diamonds", "Spades"};
        for (String rank : ranks) {
            for (String suit : suits) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    public List<String> getCardStrings() {
        List<String> cardStrings = new ArrayList<>();
        for (Card card : cards) {
            cardStrings.add(card.toString());
        }
        return cardStrings;
    }


    // Shuffle the deck
    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card dealOneCard() {
        if (!this.cards.isEmpty()) {
            return this.cards.remove(0);  // Xóa lá bài đầu tiên trong danh sách
        }
        return null;
    }


    // Deal cards to players
    public List<Card> dealCards(int numCards) {
        List<Card> hand = new ArrayList<>();
        for (int i = 0; i < numCards && !cards.isEmpty(); i++) {
            hand.add(cards.remove(0));
        }
        return hand;
    }

    // Check if selected cards form a valid move
    public boolean isValidMove(List<Card> selectedCards) {
        if (selectedCards.isEmpty()) {
            return false;
        }

        int size = selectedCards.size();
        if (size == 1) {
            return true; // Single card is always valid
        }

        // Check if all cards have the same rank
        String firstRank = selectedCards.get(0).getRank();
        boolean allSameRank = selectedCards.stream().allMatch(card -> card.getRank().equals(firstRank));
        if (allSameRank) {
            return true; // All cards are the same rank
        }

        // Check if the cards form a valid sequence with wrapping
        List<Integer> rankValues = new ArrayList<>();
        for (Card card : selectedCards) {
            rankValues.add(card.getRankValue());
        }
        Collections.sort(rankValues);

        // Handle wrapping sequence
        List<Integer> allRankValues = new ArrayList<>();
        for (int i = 1; i <= 13; i++) {
            allRankValues.add(i); // Normal ranks: 1 -> 13
        }
        allRankValues.add(1); // Add Ace again for wrapping

        for (int i = 0; i < allRankValues.size() - size; i++) {
            List<Integer> subSequence = allRankValues.subList(i, i + size);
            if (subSequence.equals(rankValues)) {
                return true; // Found a valid wrapped sequence
            }
        }

        return false; // Not valid
    }

    public Card drawCard() {
        if (!cards.isEmpty()) {
            return cards.remove(0); // Lấy lá bài đầu tiên và xóa nó khỏi bộ bài
        } else {
            throw new IllegalStateException("Deck is empty");
        }
    }


    // Get the remaining cards in the deck
    public int getRemainingCards() {
        return cards.size();
    }
}

