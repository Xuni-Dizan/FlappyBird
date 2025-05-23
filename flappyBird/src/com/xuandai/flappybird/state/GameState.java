package com.xuandai.flappybird.state;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
public interface GameState {
    void enter();
    void update();
    void render(Graphics2D g);
    void keyPressed(KeyEvent e);
}
