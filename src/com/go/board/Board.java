package com.go.board;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.go.Game;

public class Board extends JFrame {

    public int rows;
    public int columns;

    public static final int FRAME_WIDTH = 900;
    public static final int FRAME_HEIGHT = 650;

    public static final int BOARD_WIDTH = 550;
    public static final int BOARD_HEIGHT = 550;

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        buildBoard();
    }

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

    private void buildPanels() {
        // panels
        JPanel backPanel = new JPanel();
        JPanel boardPanel = new JPanel(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();
        boardPanel.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));

        // create the board's grid
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                JPanel square = new JPanel();
                square.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                square.setPreferredSize(new Dimension(BOARD_WIDTH / rows, BOARD_HEIGHT / columns));
                cons.gridx = x;
                cons.gridy = y;
                boardPanel.add(square, cons);
            }
        }

        // board listener
        boardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println(e.getX() + "," + e.getY());
            }
        });

        // add board to panel
        backPanel.add(boardPanel);

        // finished
        setContentPane(backPanel);
    }

}
