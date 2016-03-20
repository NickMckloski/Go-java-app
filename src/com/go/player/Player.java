package com.go.player;

public class Player {

    public int order;
    public String color;

    /**
     * Creates a new player
     * 
     * @param order
     *            order in which the player takes his turn
     * @param color
     *            color of the player's pieces
     */
    public Player(int order, String color) {
        this.order = order;
        this.color = color;
    }

}
