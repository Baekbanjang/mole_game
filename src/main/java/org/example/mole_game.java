package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class mole_game extends JFrame {
    private Image garden, main_garden; // 게임 배경, 메인 화면 배경
    private Image hammer1, hammer2,hitimg; //망치 이미지
    private Image bigmole, smallmole; // 두더지 이미지
    private int p1_score, p2_score;
    private  JLabel p1_label, p2_label;
    private JPanel select_panel, game_panel;
    private JButton EasyBut, HardBut;

    public mole_game() {
        p1_score=0; p2_score=0;

        ImageIcon garden_icon=new ImageIcon(this.getClass().getResource("/images/garden.jpg"));
        garden=garden_icon.getImage();
        main_garden=garden_icon.getImage();

        ImageIcon hammer_icon1=new ImageIcon(this.getClass().getResource("/images/Player1.png"));
        hammer1=hammer_icon1.getImage();
        ImageIcon hammer_icon2=new ImageIcon(this.getClass().getResource("/images/Player2.png"));
        hammer2=hammer_icon2.getImage();

        ImageIcon bigmole_icon=new ImageIcon(this.getClass().getResource("/images/mole.jfif"));
        bigmole=bigmole_icon.getImage();

        setSize(1100,800);
        setTitle("Mole Game 20193324 백승환");
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        start_screen();
    }
    class hammer{  //망치를 구현 하는 클래스
    }

    public void start_screen() {
        select_panel=new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponents(g);
                if (main_garden != null) {
                    g.drawImage(main_garden,0,0,getWidth(),getHeight(), this);
                }
            }
        };
        select_panel.setLayout(null);
        select_panel.setSize(getWidth(),getHeight());

        EasyBut= new JButton("EASY");
        EasyBut.setBounds(400, 450, 80, 30);
        EasyBut.setBackground(Color.gray);

        HardBut=new JButton("HARD");
        HardBut.setBounds(600, 450, 80, 30);
        HardBut.setBackground(Color.gray);

        select_panel.add(EasyBut);
        select_panel.add(HardBut);

        p1_label=new JLabel("1p Score: "+p1_score);
        p1_label.setFont(new Font("Arial", Font.BOLD, 24));
        p1_label.setForeground(Color.white);
        p1_label.setBounds(10,10,200,30);

        p2_label=new JLabel("2p Score: "+p2_score);
        p2_label.setFont(new Font("Arial", Font.BOLD, 24));
        p2_label.setForeground(Color.white);
        p2_label.setBounds(getWidth()-210,10,200,30);

        select_panel.add(p1_label); select_panel.add(p2_label);


        add(select_panel);
        select_panel.setVisible(true);
    }

}
