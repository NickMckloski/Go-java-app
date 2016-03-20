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
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import com.go.Game;
import com.go.GameCycle;

public class Board extends JFrame {

    public static GameCycle cycle;

    public int rows;
    public int columns;
    public int nodeSize;

    public BoardNode[][] nodes;

    public final int FRAME_WIDTH = 900;
    public final int FRAME_HEIGHT = 680;

    public final int BOARD_WIDTH = 589;
    public final int BOARD_HEIGHT = 589;
    public final int BOARD_X = 150;
    public final int BOARD_Y = 30;

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

        cycle = new GameCycle();

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
        backPanel.setLayout(null);

        JPanel boardPanel = new JPanel(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();
        boardPanel.setBounds(BOARD_X, BOARD_Y, BOARD_WIDTH, BOARD_HEIGHT);

        // layer panel on top of the board grid that pieces are drawn onto
        JPanel piecePanel = new JPanel();
        piecePanel.setBounds(BOARD_X - nodeSize / 2, BOARD_Y - nodeSize / 2, BOARD_WIDTH + nodeSize, BOARD_HEIGHT + nodeSize);
        piecePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        piecePanel.setOpaque(false);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

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
                nodes[row][column] = new BoardNode(nodeX, nodeY, this);
                // if this is the end of a row then insert extra node on the right
                if (row == rows - 1) {
                    nodes[row + 1][column] = new BoardNode(nodeX + squareWidth, nodeY, this);
                }
                // if this is the last column then add nodes at the bottom
                if (column == columns - 1) {
                    nodes[row][column + 1] = new BoardNode(nodeX, nodeY + squareHeight, this);
                }
                // if this is the bottom right corner of the whole grid then add a node
                if (row == rows - 1 && column == columns - 1) {
                    nodes[row + 1][column + 1] = new BoardNode(nodeX + squareWidth, nodeY + squareHeight, this);
                }

                // create square
                JPanel square = new JPanel();
                square.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                square.setPreferredSize(new Dimension(squareWidth, squareHeight));
                square.setBackground(Color.LIGHT_GRAY);
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
                            if (node.piece == null) {
                                System.out.println("node " + row + ", " + column);
                                placePiece(node, piecePanel);
                            }
                        }
                    }
                }
            }

        });

        // add panels
        layeredPane.add(piecePanel, 0);
        layeredPane.add(boardPanel, 1);
        backPanel.add(layeredPane);

        // finished
        setContentPane(backPanel);
    }

    /**
     * Places a board piece and advances the game cycle
     * 
     * @param node
     *            the node the piece is placed on
     * @param panel
     *            the panel the piece is places on
     */
    private void placePiece(BoardNode node, JPanel panel) {
        node.piece = new BoardPiece(cycle.currentPlayer, node);
        panel.add(node.piece.getComponent());
        repaint();
        cycle.cycle();
    }

    /**
     * Checks the distance of two points
     * 
     * @param range
     *            distance to check for
     * @param x1
     *            x1
     * @param y1
     *            y1
     * @param x2
     *            x2
     * @param y2
     *            y2
     * @return
     */
    public static boolean checkDistance(int range, int x1, int y1, int x2, int y2) {
        double distance = Point2D.distance(x1, y1, x2, y2);
        if (distance < range)
            return true;
        return false;
    }

}
