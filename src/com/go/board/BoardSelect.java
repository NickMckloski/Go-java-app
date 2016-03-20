package com.go.board;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class BoardSelect extends JFrame {

    public static Board board;

    /**
     * Board selection constructor
     * 
     * @param title
     */
    public BoardSelect(String title) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setTitle(title);
        setSize(new Dimension(400, 200));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        buildPanel();

        pack();
        setVisible(true);
    }

    /**
     * Build panels for the boardselecting frame
     */
    private void buildPanel() {
        // panels
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();

        // components
        JLabel label = new JLabel("Select a board size:");
        label.setFont(new Font("Arial", Font.BOLD, 18));

        // configure and add
        cons.gridx = 0;
        cons.gridy = 0;
        cons.ipady = 10;
        cons.ipadx = 20;
        cons.gridwidth = 5;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.insets = new Insets(10, 10, 0, 10);
        panel.add(label, cons);

        // add board options
        for (int i = 0; i < BoardType.values().length; i++) {
            BoardType type = BoardType.values()[i];
            JButton button = new JButton(type.rows + "x" + type.columns);
            // action listener
            button.addActionListener(e -> selectBoard(type));
            if (i == 0) {
                cons.gridx = 0;
                cons.gridy = 1;
                cons.ipady = 50;
                cons.ipadx = 50;
                cons.gridwidth = 2;
                cons.insets = new Insets(10, 10, 10, 10);
            } else {
                cons.gridx = i * 2;
            }
            panel.add(button, cons);
        }

        // finished
        setContentPane(panel);
    }

    /**
     * Creates a board frame with given size
     * 
     * @param rows
     *            rows on the game board
     * @param columns
     *            columns on the game board
     */
    private void selectBoard(BoardType type) {
        setVisible(false);
        board = new Board(type);
    }

}
