package com.go.board;

public enum BoardType {
    X5 (5, 5, 40),
    X9 (9, 9, 30),
    X19 (19, 19, 15);

    public int rows;
    public int columns;
    public int nodeSize;
    
    BoardType(int rows, int columns, int nodeSize) {
        this.rows = rows;
        this.columns = columns;
        this.nodeSize = nodeSize;
    }
}
