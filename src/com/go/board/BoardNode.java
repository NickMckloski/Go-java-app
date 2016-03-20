package com.go.board;

public class BoardNode {

    public int x;
    public int y;

    /**
     * Construct a new board node. This is a node that a player would click to place a piece.
     * 
     * @param x
     *            x cordinate
     * @param y
     *            y cordinate
     */
    public BoardNode(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
