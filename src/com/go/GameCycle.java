package com.go;

import java.awt.Color;

import com.go.player.Player;

public class GameCycle {

    public Player[] players;
    public Player currentPlayer;

    public GameCycle() {
        players[0] = new Player(0, Color.WHITE);
        players[1] = new Player(1, Color.BLACK);

        currentPlayer = players[0];
    }

    public void cycle() {
        if (currentPlayer.order == 0)
            currentPlayer = players[1];
        else
            currentPlayer = players[0];
    }
}
