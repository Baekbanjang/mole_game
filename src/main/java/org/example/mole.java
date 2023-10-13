package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class mole extends JFrame {
    private int x, y; // 두더지의 좌표
    private javax.swing.Timer mole_Timer; // 두더지에게 적용되는 타이머
    private int delay; // 타이머 주기

    public mole() {
        x=-50; y=-50; delay=2000; // 두더지를 사라지게 하고 두더지 출현 시간 2초
        mole_Timer=new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // 랜덤 위치
                x = (int)(Math.random() * (1100 - 50));
                y = (int)(Math.random() * (800 - 50));
                repaint();
            }
        });
    }

    public void mole_Initialization() { // 두더지 초기화 메소드
        x = -50; y = -50; // 두더지 삭제
        mole_Timer.restart(); // 타이머 재시작
        mole_Timer.setInitialDelay(delay); // 두더지 출현 시간 재설정
    }

    public void timerstart() { mole_Timer.start(); }
    public void delaySet(int delay) {this.delay = delay;} // 두더지 출현 시간 설정
    public int getX() { return x; }
    public int getY() { return y; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
}
