package com.go.board;

import javax.swing.JPanel;

import com.go.player.Player;

public class BoardPiece {

    public Player owner;
    public BoardNode parentNode;

    public JPanel panel;

    public BoardPiece(Player owner, BoardNode node) {
        this.owner = owner;
        this.parentNode = node;

        buildPiece();
    }

    private void buildPiece() {
        panel = new JPanel();
        panel.setBackground(owner.color);
        panel.setBounds(parentNode.x, parentNode.y, parentNode.board.nodeSize, parentNode.board.nodeSize);
    }

    public JPanel getComponent() {
        return panel;
    }
}
