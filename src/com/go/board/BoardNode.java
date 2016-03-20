package com.go.board;

public class BoardNode {

    public int x;
    public int y;
    public Board board;
    public BoardPiece piece;

    /**
     * Construct a new board node. This is a node that a player would click to place a piece.
     * 
     * @param x
     *            x cordinate
     * @param y
     *            y cordinate
     */
    public BoardNode(int x, int y, Board board) {
        this.x = x;
        this.y = y;
        this.board = board;
    }

}
