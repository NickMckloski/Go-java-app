package com.go.board;

public enum BoardType {
    X5 (5, 5, 40, 60),
    X9 (9, 9, 30, 50),
    X19 (19, 19, 15, 25);

    public int rows;
    public int columns;
    public int nodeSize;
    public int pieceSize;
    
    BoardType(int rows, int columns, int nodeSize, int pieceSize) {
        this.rows = rows;
        this.columns = columns;
        this.nodeSize = nodeSize;
        this.pieceSize = pieceSize;
    }
}
