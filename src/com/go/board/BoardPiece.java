package com.go.board;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.go.player.Player;

public class BoardPiece {

    public Player owner;
    public BoardNode parentNode;

    public JLabel piece;

    public BoardPiece(Player owner, BoardNode node) {
        this.owner = owner;
        this.parentNode = node;

        buildPiece();
    }

    private void buildPiece() {
        piece = new JLabel();
        int pieceSize = parentNode.board.type.pieceSize;
        try {
            BufferedImage image = ImageIO.read(new File("data/images/"+owner.color+".png"));
            piece.setIcon(new ImageIcon(image.getScaledInstance(pieceSize, pieceSize, Image.SCALE_SMOOTH)));
        } catch(Exception e) {
            e.printStackTrace();
        }
        piece.setBounds(parentNode.x, parentNode.y, pieceSize, pieceSize);
    }

    public JLabel getComponent() {
        return piece;
    }
}
