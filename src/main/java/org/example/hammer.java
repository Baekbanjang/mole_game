package org.example;

import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.io.*;
import javax.sound.sampled.*;
import java.net.*;

public class hammer {
    private int x,y; // 좌표
    private int up,down,left,right; // 이동
    private Image hammer_Img, hammer_hitImg, hammer_Img_backup; //이미지
    private javax.swing.Timer hit_Timer;

    hammer(int x, int y, Image hammerImg, Image hammer_hitImg, int up, int down, int left, int right){
        // 좌표, 이미지, 키 값을 입력 받음
        this.x=x;
        this.y=y;
        this.hammer_Img=hammerImg;
        this.hammer_hitImg=hammer_hitImg;
        // 키 값
        this.up=up;
        this.down=down;
        this.left=left;
        this.right=right;

       hit_Timer =new Timer(200, new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               restoration_Img();
               hit_Timer.stop();
           }
       });
       hit_Timer.setRepeats(false);
    }

    public void hammer_move(HashSet<Integer> moveKey) {
        // 키 누를시 발생
        if(moveKey.contains(down)){
            if((y+20)<750){
                y+=20;
            }
        }
        if(moveKey.contains(up)){
            if((y-20)>=-50){
                y-=20;
            }
        }
        if(moveKey.contains(right)){
            if((x+20)<1050){
                x+=20;
            }
        }
        if(moveKey.contains(left)){
            if((x-20)>-50){
                x-=20;
            }
        }
    }

    public void draw(Graphics g) {
        g.drawImage(hammer_Img, x, y, null);
    }
    public void restoration_Img() {
        hammer_Img=hammer_Img_backup;
    }
    public int getX() {return x;}
    public int getY() {return y;}
    public void hit() {
        if(!hit_Timer.isRunning()) {
            hammer_Img_backup=hammer_Img;
            hammer_Img=hammer_hitImg;
            hit_Timer.start();
            hammer_sound("/sound/hammerSound.wav");
        }
    }

    public static void hammer_sound(String soundFilePath) { // 두더지 잡을 때 효과음 실핼 메소드
        try {
            URL soundFileUrl=hammer.class.getResource(soundFilePath);
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
}
