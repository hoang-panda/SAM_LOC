package com.example.sam_loc_2;

public class GameException extends RuntimeException {
    public enum ErrorType {
        INVALID_MOVE,
        CANNOT_BEAT_PREVIOUS_MOVE,
        NOT_ENOUGH_CARDS,
        GAME_OVER,
        PLAYER_OUT_OF_CARDS
    }

    private final ErrorType errorType;

    public GameException(String message, ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
