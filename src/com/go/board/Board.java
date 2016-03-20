package com.go.board;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.go.Game;

public class Board extends JFrame {

    public int rows;
    public int columns;
    public int nodeSize;

    public BoardNode[][] nodes;

    public static final int FRAME_WIDTH = 900;
    public static final int FRAME_HEIGHT = 650;

    public static final int BOARD_WIDTH = 589;
    public static final int BOARD_HEIGHT = 589;

    /**
     * Initialize board
     * 
     * @param rows
     *            rows of squares in the grid
     * @param columns
     *            columns of squares in the grid
     * @param nodeSize
     *            the size of each node on the grid
     */
    public Board(int rows, int columns, int nodeSize) {
        this.rows = rows;
        this.columns = columns;
        this.nodeSize = nodeSize;
        buildBoard();
    }

    /**
     * Build the board frame
     */
    private void buildBoard() {
        setTitle("GoApp - " + rows + "x" + columns + " Board");
        setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Game.select.setVisible(true);
            }
        });
        setResizable(false);

        buildPanels();

        setVisible(true);
    }

    /**
     * Build panels of the board frame
     */
    private void buildPanels() {
        // panels
        JPanel backPanel = new JPanel();
        JPanel boardPanel = new JPanel(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();
        boardPanel.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));

        // create the board's grid
        int squareWidth = BOARD_WIDTH / rows;
        int squareHeight = BOARD_HEIGHT / columns;
        // the row/column lengths are + 1 because each square has a node on each corner
        // basically resulting in an extra row and column
        nodes = new BoardNode[rows + 1][columns + 1];
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {

                // create node for the top left corner of the square
                int nodeX = squareWidth * row;
                int nodeY = squareHeight * column;
                nodes[row][column] = new BoardNode(nodeX, nodeY);
                // if this is the end of a row then insert extra node on the right
                if (row == rows - 1) {
                    nodes[row + 1][column] = new BoardNode(nodeX + squareWidth, nodeY);
                }
                // if this is the last column then add nodes at the bottom
                if (column == columns - 1) {
                    nodes[row][column + 1] = new BoardNode(nodeX, nodeY + squareHeight);
                }
                // if this is the bottom right corner of the whole grid then add a node
                if (row == rows - 1 && column == columns - 1) {
                    nodes[row + 1][column + 1] = new BoardNode(nodeX + squareWidth, nodeY + squareHeight);
                }

                // create square
                JPanel square = new JPanel();
                square.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                square.setPreferredSize(new Dimension(squareWidth, squareHeight));
                cons.gridx = row;
                cons.gridy = column;
                // add square to panel
                boardPanel.add(square, cons);
            }
        }

        // board listener
        boardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // check the nodes for a match in our click
                for (int row = 0; row < nodes.length; row++) {
                    for (int column = 0; column < nodes[0].length; column++) {
                        BoardNode node = nodes[row][column];
                        if (checkDistance(nodeSize, e.getX(), e.getY(), node.x, node.y)) {
                            System.out.println("node " + row + ", " + column);
                        }
                    }
                }
            }
        });

        // add board to panel
        backPanel.add(boardPanel);

        // finished
        setContentPane(backPanel);
    }
    
    public static boolean checkDistance(int range, int x1, int y1, int x2, int y2) {
        double distance = Point2D.distance(x1, y1, x2, y2);
        if (distance < range)
            return true;
        return false;
    }

}
