package com.go.board;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import com.go.Game;
import com.go.GameCycle;

public class Board extends JFrame {

    /**
     * The gamecycle that this board is using
     */
    public static GameCycle cycle;
    /**
     * The type of board being played
     */
    public BoardType type;
    /**
     * Nodes on the board
     */
    public BoardNode[][] nodes;

    public final int FRAME_WIDTH = 1000;
    public final int FRAME_HEIGHT = 730;

    public final int BOARD_WIDTH = 589;
    public final int BOARD_HEIGHT = 589;
    public final int BOARD_X = 198;
    public final int BOARD_Y = 75;

    private JLabel turnTitle;
    private JPanel p1Panel;
    private JPanel p2Panel;
    
    /**
     * Initialize the board
     * 
     * @param type
     *            the type of board (enum)
     */
    public Board(BoardType type) {
        this.type = type;

        cycle = new GameCycle();

        buildBoard();
    }

    /**
     * Build the board frame
     */
    private void buildBoard() {
        setTitle("GoApp - " + type.rows + "x" + type.columns + " Board");
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
     * Build panels of the board
     */
    private void buildPanels() {
        // main conent panel
        JPanel backPanel = new JPanel();
        backPanel.setLayout(null);

        // panel with the board's node grid
        JPanel boardPanel = new JPanel(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();
        boardPanel.setBounds(BOARD_X, BOARD_Y, BOARD_WIDTH, BOARD_HEIGHT);

        // layer panel on top of the board grid that pieces are drawn onto
        JPanel piecePanel = new JPanel();
        // the piece layer needs to be slightly larger than game board
        // so that pieces can be displayed over the edge
        int pieceLayerX = BOARD_X - type.pieceSize / 2;
        int pieceLayery = BOARD_Y - type.pieceSize / 2;
        int pieceLayerWidth = BOARD_WIDTH + type.pieceSize;
        int pieceLayerHeight = BOARD_HEIGHT + type.pieceSize;
        piecePanel.setBounds(pieceLayerX, pieceLayery, pieceLayerWidth, pieceLayerHeight);
        piecePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        piecePanel.setOpaque(false);
        piecePanel.setLayout(null);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        // create the board's grid
        int squareWidth = BOARD_WIDTH / type.rows;
        int squareHeight = BOARD_HEIGHT / type.columns;
        // the row/column lengths are + 1 because each square has a node on each corner
        // basically resulting in an extra row and column
        nodes = new BoardNode[type.rows + 1][type.columns + 1];
        for (int row = 0; row < type.rows; row++) {
            for (int column = 0; column < type.columns; column++) {

                // create node for the top left corner of the square
                int nodeX = squareWidth * row;
                int nodeY = squareHeight * column;
                nodes[row][column] = new BoardNode(nodeX, nodeY, row, column, this);
                // if this is the end of a row then insert extra node on the right
                if (row == type.rows - 1) {
                    nodes[type.rows][column] = new BoardNode(nodeX + squareWidth, nodeY, type.rows, column, this);
                }
                // if this is the last column then add nodes at the bottom
                if (column == type.columns - 1) {
                    nodes[row][type.columns] = new BoardNode(nodeX, nodeY + squareHeight, row, type.columns, this);
                }
                // if this is the bottom right corner of the whole grid then add a node
                if (row == type.rows - 1 && column == type.columns - 1) {
                    nodes[type.rows][type.columns] = new BoardNode(nodeX + squareWidth, nodeY + squareHeight,
                            type.rows, type.columns, this);
                }

                // create square
                JPanel square = new JPanel();
                square.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                square.setPreferredSize(new Dimension(squareWidth, squareHeight));
                square.setBackground(new Color(219, 195, 86));
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
                        if (checkDistance(type.nodeSize, e.getX(), e.getY(), node.x, node.y)) {
                            if (node.piece == null) {
                                placePiece(node, piecePanel);
                            }
                        }
                    }
                }
            }

        });
        
        // turn title above board
        turnTitle = new JLabel("Player 1's turn (White)");
        turnTitle.setBounds(BOARD_X, 15, FRAME_WIDTH, 25);
        turnTitle.setFont(new Font("Arial", Font.PLAIN, 18));
        
        // player 1's captures
        JLabel p1Captures = new JLabel("Player 1's captures:");
        p1Captures.setBounds(15, BOARD_Y, 200, 25);
        p1Captures.setFont(new Font("Arial", Font.PLAIN, 14));
        
        p1Panel = new JPanel();
        p1Panel.setBounds(15, BOARD_Y + 30, 130, FRAME_HEIGHT - 400); 
        p1Panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));       
        
        // player 2's captures
        JLabel p2Captures = new JLabel("Player 2's captures:");
        p2Captures.setBounds(BOARD_X + BOARD_WIDTH + 50, BOARD_Y, 200, 25);
        p2Captures.setFont(new Font("Arial", Font.PLAIN, 14));
        
        p2Panel = new JPanel();
        p2Panel.setBounds(BOARD_X + BOARD_WIDTH + 50, BOARD_Y + 30, 130, FRAME_HEIGHT - 400); 
        p2Panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); 
        
        // add panels
        layeredPane.add(piecePanel, 0);
        layeredPane.add(boardPanel, 1);
        backPanel.add(turnTitle);
        backPanel.add(p1Captures);
        backPanel.add(p1Panel);
        backPanel.add(p2Captures);
        backPanel.add(p2Panel);
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
        // create and place board piece
        node.piece = new BoardPiece(cycle.currentPlayer, node);

        panel.add(node.piece.getComponent());
        revalidate();
        repaint();

        //check for groups around the node that was placed
        BoardLogic.placePiece(node, panel);

        // advance game cycle
        cycle.cycle();
        turnTitle.setText("Player "+(cycle.currentPlayer.order+1)+"'s turn ("+cycle.currentPlayer.color+")");
    }

    /**
     * Removes pieces off of the board
     * 
     * @param group
     *            the group of board nodes to clear
     * @param panel
     *            panel to remove the pieces from
     */
    public void removePieces(BoardNode[][] group, JPanel panel) {
        // loop group
        for (int row = 0; row < group.length; row++) {
            for (int column = 0; column < group[0].length; column++) {
                if (group[row][column] != null) {
                    // remove from panel
                    panel.remove(group[row][column].piece.getComponent());
                    
                    //add to the other players captures
                    JLabel piece = new JLabel();
                    int pieceSize = 15;
                    try {
                        BufferedImage image = ImageIO.read(new File("data/images/" + nodes[row][column].piece.owner.color + ".png"));
                        piece.setIcon(new ImageIcon(image.getScaledInstance(pieceSize, pieceSize, Image.SCALE_SMOOTH)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(nodes[row][column].piece.owner.order == 0)
                        p2Panel.add(piece);
                    else
                        p1Panel.add(piece);
                    
                    // clear piece from node
                    nodes[row][column].piece = null;
                }
            }
        }
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
