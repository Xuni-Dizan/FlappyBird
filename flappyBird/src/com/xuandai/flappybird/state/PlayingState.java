package com.xuandai.flappybird.state;

import java.awt.*;
import java.awt.event.*;
import com.xuandai.flappybird.GamePanel;
import com.xuandai.flappybird.model.GameModel;
// GameConfig có thể không cần import trực tiếp ở đây nếu không dùng hằng số nào của nó
// import com.xuandai.flappybird.GameConfig;

public class PlayingState implements GameState {
    private final GamePanel panel;
    private final GameModel model;

    public PlayingState(GamePanel p) {
        this.panel = p;
        this.model = p.getModel();
    }

    /**
     * Được gọi khi vào trạng thái Playing.
     * Có thể thực hiện các thiết lập ban đầu cho trạng thái này nếu cần.
     */
    @Override
    public void enter() {
        // Ví dụ: có thể bắt đầu phát nhạc nền cho game ở đây
        // model.getBird().setY(GameConfig.BIRD_START_Y); // Đảm bảo vị trí chim đúng nếu cần, dù ReadyState đã reset
    }

    /**
     * Cập nhật logic của game khi đang chơi.
     * Bao gồm cập nhật mô hình game (chim, ống, điểm) và kiểm tra va chạm.
     */
    @Override
    public void update() {
        model.update(); // Cập nhật vị trí chim, ống, tốc độ, điểm số
        if (model.isBirdColliding()) {
            // Nếu có va chạm, chuyển sang trạng thái GameOver
            panel.setState(new GameOverState(panel));
        }
    }

    /**
     * Vẽ các đối tượng game khi đang ở trạng thái Playing.
     * Ủy quyền việc vẽ cho GamePanel.renderGame().
     * @param g Đối tượng Graphics2D để vẽ.
     */
    @Override
    public void render(Graphics2D g) {
        panel.renderGame(g); // GamePanel sẽ vẽ tất cả các yếu tố của game
        // Trạng thái Playing thường không vẽ thêm thông báo gì đặc biệt lên trên game.
    }

    /**
     * Xử lý sự kiện nhấn phím khi đang chơi.
     * Phím SPACE: chim vỗ cánh.
     * Phím P: tạm dừng game.
     * @param e Đối tượng KeyEvent.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_SPACE) {
            model.getBird().flap();
        }
        if (keyCode == KeyEvent.VK_P) {
            panel.setState(new PauseState(panel)); // Chuyển sang trạng thái Pause
        }
        // Có thể thêm các phím khác ở đây nếu cần (ví dụ: cheat codes, debug)
    }
}
