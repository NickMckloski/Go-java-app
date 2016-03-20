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
     * Construct a new gamecycle
     */
    public GameCycle() {
        players[0] = new Player(0, "white");
        players[1] = new Player(1, "black");

        currentPlayer = players[0];
    }

    /**
     * Advance the game to the next cylce
     */
    public void cycle() {
        currentPlayer = players[currentPlayer.order == 0 ? 1 : 0];
    }
}
