package com.go.board;

public class BoardNode {

    public int x;
    public int y;
    public int row;
    public int column;
    public Board board;
    public BoardPiece piece;

    /**
     * Construct a new board node. This is a node that a player would click to place a piece.
     * 
     * @param x
     *            absolute x cord
     * @param y
     *            absolute y cord
     * @param row
     *            row number
     * @param column
     *            column number
     * @param board
     *            node's parent board
     */
    public BoardNode(int x, int y, int row, int column, Board board) {
        this.x = x;
        this.y = y;
        this.row = row;
        this.column = column;
        this.board = board;
    }

}
