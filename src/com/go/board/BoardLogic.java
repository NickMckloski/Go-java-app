package com.go.board;

import javax.swing.JPanel;

public class BoardLogic {

    /**
     * The method that kicks off the proccess of searching for capture groups
     * 
     * @param rootNode
     *            the root node to check from
     * @param panel
     *            the panel to remove pieces from
     */
    public static void placePiece(BoardNode rootNode, JPanel panel) {
        BoardType type = rootNode.board.type;
        BoardNode[][] nodes = rootNode.board.nodes;

        // check east node for groups
        if (rootNode.row != type.rows) {
            BoardNode checkNode = nodes[rootNode.row + 1][rootNode.column];
            BoardNode[][] group = BoardLogic.findGroup(checkNode.row, checkNode.column, checkNode,
                    new BoardNode[type.rows + 1][type.columns + 1]);
            BoardLogic.checkGroupForCapture(checkNode, group, panel);
        }
        // check west node for groups
        if (rootNode.row != 0) {
            BoardNode checkNode = nodes[rootNode.row - 1][rootNode.column];
            BoardNode[][] group = BoardLogic.findGroup(checkNode.row, checkNode.column, checkNode,
                    new BoardNode[type.rows + 1][type.columns + 1]);
            BoardLogic.checkGroupForCapture(checkNode, group, panel);
        }
        // check south node for groups
        if (rootNode.column != type.columns) {
            BoardNode checkNode = nodes[rootNode.row][rootNode.column + 1];
            BoardNode[][] group = BoardLogic.findGroup(checkNode.row, checkNode.column, checkNode,
                    new BoardNode[type.rows + 1][type.columns + 1]);
            BoardLogic.checkGroupForCapture(checkNode, group, panel);
        }
        // check north node for groups
        if (rootNode.column != 0) {
            BoardNode checkNode = nodes[rootNode.row][rootNode.column - 1];
            BoardNode[][] group = BoardLogic.findGroup(checkNode.row, checkNode.column, checkNode,
                    new BoardNode[type.rows + 1][type.columns + 1]);
            BoardLogic.checkGroupForCapture(checkNode, group, panel);
        }
        // check the node that was just placed for groups
        BoardNode[][] group = BoardLogic.findGroup(rootNode.row, rootNode.column, rootNode,
                new BoardNode[type.rows + 1][type.columns + 1]);
        BoardLogic.checkGroupForCapture(nodes[rootNode.row][rootNode.column], group, panel);
    }

    /**
     * Flood fill-like algorithm to find a group of nodes
     * 
     * @param row
     *            the row of the node being checked
     * @param column
     *            the column of the node being checked
     * @param rootNode
     *            the root node being checked
     * @param group
     *            the group of nodes being populated
     * @return
     */
    public static BoardNode[][] findGroup(int row, int column, BoardNode rootNode, BoardNode[][] group) {
        BoardType type = rootNode.board.type;
        BoardNode[][] nodes = rootNode.board.nodes;

        // check if the nodes are nore null
        if (nodes[row][column].piece != null && rootNode.piece != null) {

            System.out.println("Checking node " + row + ", " + column);

            // check to see if the comparing nodes have the same owner
            // check if the group position already been filled with a node
            if (nodes[row][column].piece.owner == rootNode.piece.owner && group[row][column] == null) {

                System.out.println("Found group node " + row + ", " + column);

                // save this group member
                group[row][column] = nodes[row][column];

                // look at the neighbour nodes
                if (row != type.rows)
                    group = findGroup(row + 1, column, rootNode, group);
                if (row != 0)
                    group = findGroup(row - 1, column, rootNode, group);
                if (column != type.columns)
                    group = findGroup(row, column + 1, rootNode, group);
                if (column != 0)
                    group = findGroup(row, column - 1, rootNode, group);
            }
        }
        // return the collected group
        return group;
    }

    /**
     * Method to check if a group of pieces can be captured (when they are surrounded)
     * 
     * @param rootNode
     *            the rootnode
     * @param group
     *            group of pieces being checked
     * @param panel
     *            panel to remove the pieces from
     */
    public static void checkGroupForCapture(BoardNode rootNode, BoardNode[][] group, JPanel panel) {
        BoardNode[][] nodes = rootNode.board.nodes;
        BoardType type = rootNode.board.type;

        boolean canRemove = true;
        boolean groupHasNodes = false;
        // loop though all nodes in the group
        for (int row = 0; row < group.length; row++) {
            for (int column = 0; column < group[0].length; column++) {
                // if the group node is null then skip it
                if (group[row][column] != null) {
                    // if the board node in this position is null then the group isn't surrounded
                    if (nodes[row][column] != null) {

                        System.out.println("Looking at neighbor nodes of " + row + ", " + column);

                        // look at the neighbours
                        groupHasNodes = true;

                        // look east
                        if (row != type.rows)
                            // if board node in that position is null then the group is not surrounded
                            if (nodes[row + 1][column].piece != null) {
                                System.out.println("checking neighbor " + (row + 1) + ", " + column);
                                // check if the board node is the same owner as the root node
                                // then check if the node in the group is null
                                // if the node is the same owner and the group node is not null then piece is not surrounded
                                if (nodes[row + 1][column].piece.owner == rootNode.piece.owner
                                        && group[row + 1][column] == null) {
                                    System.out.println("neighbor enemy piece at " + (row + 1) + ", " + column);
                                    canRemove = false;
                                }
                            } else
                                canRemove = false;

                        // look west
                        if (row != 0)
                            // if board node in that position is null then the group is not surrounded
                            if (nodes[row - 1][column].piece != null) {
                                System.out.println("checking neighbor " + (row - 1) + ", " + column);
                                // check if the board node is the same owner as the root node
                                // then check if the node in the group is null
                                // if the node is the same owner and the group node is not null then piece is not surrounded
                                if (nodes[row - 1][column].piece.owner == rootNode.piece.owner
                                        && group[row - 1][column] == null) {
                                    System.out.println("neighbor enemy piece at " + (row - 1) + ", " + column);
                                    canRemove = false;
                                }
                            } else
                                canRemove = false;

                        // look south
                        if (column != type.columns)
                            // if board node in that position is null then the group is not surrounded
                            if (nodes[row][column + 1].piece != null) {
                                System.out.println("checking neighbor " + row + ", " + (column + 1));
                                // check if the board node is the same owner as the root node
                                // then check if the node in the group is null
                                // if the node is the same owner and the group node is not null then piece is not surrounded
                                if (nodes[row][column + 1].piece.owner == rootNode.piece.owner
                                        && group[row][column + 1] == null) {
                                    System.out.println("neighbor enemy piece at " + row + ", " + (column + 1));
                                    canRemove = false;
                                }
                            } else
                                canRemove = false;

                        // look north
                        if (column != 0)
                            // if board node in that position is null then the group is not surrounded
                            if (nodes[row][column - 1].piece != null) {
                                System.out.println("checking neighbor " + row + ", " + (column - 1));
                                // check if the board node is the same owner as the root node
                                // then check if the node in the group is null
                                // if the node is the same owner and the group node is not null then piece is not surrounded
                                if (nodes[row][column - 1].piece.owner == rootNode.piece.owner
                                        && group[row][column - 1] == null) {
                                    System.out.println("neighbor enemy piece at " + row + ", " + (column - 1));
                                    canRemove = false;
                                }
                            } else
                                canRemove = false;

                    } else
                        canRemove = false;
                }
            }
        }

        System.out.println(canRemove && groupHasNodes);
        if (canRemove && groupHasNodes)
            rootNode.board.removePieces(group, panel);
    }

}
