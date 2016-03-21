package com.go;

import com.go.player.Player;

public class GameCycle {

    /**
     * Players in the game
     */
    public Player[] players = new Player[2];
    /**
     * The player of the current cycle/turn
     */
    public Player currentPlayer;
    /**
     * The player not taking his turn this cycle
     */
    public Player oppositePlayer;

    /**
     * Construct a new gamecycle
     */
    public GameCycle() {
        players[0] = new Player(0, "White");
        players[1] = new Player(1, "Black");

        currentPlayer = players[0];
        oppositePlayer = players[1];
    }

    /**
     * Advance the game to the next cylce
     */
    public void cycle() {
        oppositePlayer = currentPlayer;
        currentPlayer = players[currentPlayer.order == 0 ? 1 : 0];
    }
}
