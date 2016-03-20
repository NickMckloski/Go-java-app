package com.go;

import com.go.player.Player;

public class GameCycle {

    public Player[] players = new Player[2];
    public Player currentPlayer;

    public GameCycle() {
        players[0] = new Player(0, "white");
        players[1] = new Player(1, "black");

        currentPlayer = players[0];
    }

    public void cycle() {
        if (currentPlayer.order == 0)
            currentPlayer = players[1];
        else
            currentPlayer = players[0];
    }
}
