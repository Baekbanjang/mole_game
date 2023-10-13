package org.example;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class mole_game extends JFrame implements KeyListener{
    private Image grass; // 게임 배경, 메인 화면 배경
    private Image hammer1_Img, hammer2_Img, hit1_Img, hit2_Img; //망치 이미지
    private Image mole_Img; // 두더지 이미지
    private int p1_score, p2_score; // 플레이어 점수
    private  JLabel title_label,p1_label, p2_label;
    private JPanel menu_panel, game_panel;
    private JButton EasyBut, HardBut, ExitBut; // 버튼
    private List<mole> moles; // 두더지들
    private boolean gameStarted=false, gameOver=false;
    private hammer player1, player2; // 플레이어(망치)
    private HashSet<Integer> moveKey;
    private BufferedWriter writer;
    private String resultfile_path ="game_results.txt";

    public mole_game() {
        p1_score=0; p2_score=0;

        ImageIcon garden_icon=new ImageIcon(this.getClass().getResource("/images/garden.jpg"));
        grass =garden_icon.getImage();

        ImageIcon hammer_icon1=new ImageIcon(this.getClass().getResource("/images/Player1.png"));
        hammer1_Img=hammer_icon1.getImage();
        ImageIcon hammer_icon2=new ImageIcon(this.getClass().getResource("/images/Player2.png"));
        hammer2_Img=hammer_icon2.getImage();
        ImageIcon hit_icon1=new ImageIcon(this.getClass().getResource("/images/Player1hit.png"));
        hit1_Img=hit_icon1.getImage();
        ImageIcon hit_icon2=new ImageIcon(this.getClass().getResource("/images/Player2hit.png"));
        hit2_Img=hit_icon2.getImage();

        ImageIcon mole_icon=new ImageIcon(this.getClass().getResource("/images/dack.jpg"));
        mole_Img=mole_icon.getImage();

        player1=new hammer(100, 100, hammer1_Img, hit1_Img, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D);
        player2=new hammer(1000, 100, hammer2_Img, hit2_Img, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT);
        moveKey =new HashSet<>();

        moles=new ArrayList<>();
        for(int i=0; i<5; i++) {
            moles.add(new mole());
        }
        try{
            writer=new BufferedWriter(new FileWriter(resultfile_path));
        }catch (IOException e) {
            e.printStackTrace();
        }

        setSize(1100,800);
        setTitle("Mole Game 20193324 백승환");
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        addKeyListener(this);
        setFocusable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        requestFocus();

        start_screen();
    }

    public void start_screen() { // 시작화면 메소드
        menu_panel =new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponents(g);
                if (grass != null) {
                    g.drawImage(grass,0,0,getWidth(),getHeight(), this);
                }
            }
        };
        menu_panel.setLayout(null);
        menu_panel.setSize(getWidth(),getHeight());

        title_label=new JLabel("MOLE-GAME!");
        title_label.setFont(new Font("Arial", Font.BOLD, 40));
        title_label.setForeground(Color.white);
        title_label.setBounds(420,180,300,50);

        EasyBut= new JButton("EASY");
        EasyBut.setBounds(350, 300, 400, 80);
        EasyBut.setBackground(Color.gray);

        HardBut=new JButton("HARD");
        HardBut.setBounds(350, 380, 400, 80);
        HardBut.setBackground(Color.gray);

        ExitBut=new JButton("EXIT");
        ExitBut.setBounds(350, 460, 400, 80);
        ExitBut.setBackground(Color.gray);

        menu_panel.add(title_label);
        menu_panel.add(EasyBut); menu_panel.add(HardBut); menu_panel.add(ExitBut);

        EasyBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delaySet(2000);
                menu_panel.setVisible(false);
                gameStarted=true;
                start_game();
                requestFocus();
            }
        });

        HardBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delaySet(1000);
                menu_panel.setVisible(false);
                gameStarted=true;
                start_game();
                requestFocus();
            }
        });

        ExitBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        add(menu_panel);
        menu_panel.setVisible(true);

        try { // 게임 시작시 효과음
            URL soundFileUrl=hammer.class.getResource("/sound/startSound.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFileUrl);
            Clip clip = AudioSystem.getClip(); //
            clip.open(audioInputStream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-15.0f);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    private void start_game() { // 게임 시작
        for(mole mole : moles) {
            mole.timerstart();
        }
        p1_label=new JLabel("1p Score : "+p1_score);
        p1_label.setFont(new Font("Arial", Font.BOLD, 24));
        p1_label.setForeground(Color.white);
        p1_label.setBounds(10,10,200,30);

        p2_label=new JLabel("2p Score : "+p2_score);
        p2_label.setFont(new Font("Arial", Font.BOLD, 24));
        p2_label.setForeground(Color.white);
        p2_label.setBounds(870,10,200,30);

        add(p1_label); add(p2_label);

    }

    private void game_over() {
        if(p1_score >=10) {
            gameOver=true;
            try{
                writer=new BufferedWriter(new FileWriter(resultfile_path, true));
                writer.write("Player1 점수: " +p1_score+"  Player2 점수: "+p2_score+"  승자: Player1");
                writer.newLine();
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            JOptionPane.showMessageDialog(this,"Player1이 승리하였습니다\n 점수: "+p1_score, "게임 종료", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
        else if(p2_score >=10) {
            gameOver=true;
            try{
                writer=new BufferedWriter(new FileWriter(resultfile_path, true));
                writer.write("Player1 점수: " +p1_score+"  Player2 점수: "+p2_score+"  승자: Player2");
                writer.newLine();
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            JOptionPane.showMessageDialog(this,"Player2이 승리하였습니다\n 점수"+p2_score, "게임 종료", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }

    public void paint(Graphics g) {
        if(gameStarted) { // 게임 시작 시
            if(grass !=null) {
                g.drawImage(grass, 0,0,getWidth(), getHeight(), this);
            }
            if(mole_Img!=null) {
                for(mole mole: moles) {
                    g.drawImage(mole_Img, mole.getX(), mole.getY(),this);
                }
            }
            if(hammer1_Img !=null) player1.draw(g);
            if(hammer2_Img !=null) player2.draw(g);
            if(p1_label!=null) p1_label.repaint();
            if(p2_label!=null) p2_label.repaint();
        }
        else { // 시작 안했을 경우
            menu_panel.repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) { // 키를 눌렀을 때
        int keyCode=e.getKeyCode();
        moveKey.add(keyCode);

        player1.hammer_move(moveKey);
        player2.hammer_move(moveKey);

        if(keyCode==KeyEvent.VK_SPACE) {
            player1.hit();
            for(mole mole:moles){ // player1이 두더지를 잡은 경우(망치와 두더지 이미지가 겹쳐야함)
                if((player1.getX() <= mole.getX() + 50) && (player1.getX() >= mole.getX() - 50)
                && (player1.getY() <= mole.getY() + 50) && (player1.getY() <= mole.getY() + 50)){
                    p1_score++;
                    System.out.println("1P Score : " + p1_score);
                    p1_label.setText("1P Score: " + p1_score);
                    resetMole(mole);
                    game_over();
                }
            }
        }

        if(keyCode==KeyEvent.VK_ENTER) {
            player2.hit();
            for(mole mole:moles){ // player2가 두더지를 잡은 경우(망치와 두더지 이미지가 겹쳐야함)
                if((player2.getX() <= mole.getX() + 50) && (player2.getX() >= mole.getX() - 50)
                        && (player2.getY() <= mole.getY() + 50) && (player2.getY() <= mole.getY() + 50)){
                    p2_score++;
                    System.out.println("2P Score : " + p2_score);
                    p2_label.setText("2P Score : " + p2_score);
                    resetMole(mole);
                    game_over();
                }
            }
        }
        repaint();
    }

    private void resetMole(mole mole) { // 두더지를 잡았을 떄 새로운 두더지
        mole.setX((int)(Math.random()*(getWidth()-50)));
        mole.setY((int)(Math.random()*(getWidth()-50)));
        mole.mole_Initialization();
    }

    private void delaySet(int delay) { // 두더지 출현 시간 설정
        for(mole mole: moles) {
            mole.delaySet(delay);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { // 키를 똇을 떄
        int keyCode=e.getKeyCode();
        moveKey.remove(keyCode);
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e){}
    @Override
    public void dispose() {
        super.dispose();
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
