package com.xuandai.flappybird.state;

import java.awt.*;
import java.awt.event.*;
import com.xuandai.flappybird.GamePanel;
import com.xuandai.flappybird.GameConfig; // Import GameConfig
import com.xuandai.flappybird.model.GameModel;

public class GameOverState implements GameState {
    private final GamePanel panel;
    private final GameModel model;

    public GameOverState(GamePanel p) {
        this.panel = p;
        this.model = p.getModel();
    }

    /**
     * Được gọi khi vào trạng thái GameOver.
     * Lưu điểm cao nếu cần.
     */
    @Override
    public void enter() {
        panel.saveHighScore(); // Lưu điểm cao
        // Ví dụ: Dừng nhạc nền, phát âm thanh game over
        // SoundManager.stopBackgroundMusic();
        // SoundManager.playSound("gameover");
    }

    /**
     * Không có logic cập nhật nào trong trạng thái GameOver,
     * vì game đã kết thúc.
     */
    @Override
    public void update() {
        // Game logic is stopped
    }

    /**
     * Vẽ màn hình trạng thái GameOver.
     * Hiển thị trạng thái game cuối cùng, điểm số và thông báo.
     * @param g Đối tượng Graphics2D để vẽ.
     */
    @Override
    public void render(Graphics2D g) {
        // Vẽ trạng thái game cuối cùng (chim rơi, ống đứng yên)
        panel.renderGame(g);

        // Vẽ một lớp phủ mờ lên trên màn hình game
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f); // Độ mờ 60%
        g.setComposite(ac);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GameConfig.BASE_WIDTH, GameConfig.BASE_HEIGHT);

        // Reset composite để các thành phần vẽ sau không bị ảnh hưởng
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        // Thiết lập font và màu cho các thông báo
        g.setColor(Color.RED); // Màu chữ cho "GAME OVER"
        // Sử dụng font lớn hơn cho "GAME OVER"
        g.setFont(new Font(GameConfig.FONT_NAME, Font.BOLD, GameConfig.MESSAGE_FONT_SIZE + 10));
        FontMetrics fmGameOver = g.getFontMetrics();
        String gameOverText = "GAME OVER";
        int gameOverWidth = fmGameOver.stringWidth(gameOverText);
        g.drawString(gameOverText, (GameConfig.BASE_WIDTH - gameOverWidth) / 2, GameConfig.BASE_HEIGHT / 2 - 60);

        // Hiển thị điểm số và điểm cao
        g.setColor(Color.WHITE);
        g.setFont(new Font(GameConfig.FONT_NAME, Font.PLAIN, GameConfig.MESSAGE_FONT_SIZE - 2));
        FontMetrics fmScore = g.getFontMetrics();

        String finalScoreText = "Your Score: " + model.getScore();
        int finalScoreWidth = fmScore.stringWidth(finalScoreText);
        g.drawString(finalScoreText, (GameConfig.BASE_WIDTH - finalScoreWidth) / 2, GameConfig.BASE_HEIGHT / 2 - 20);

        String highScoreText = "High Score: " + model.getHighScore();
        int highScoreWidth = fmScore.stringWidth(highScoreText);
        g.drawString(highScoreText, (GameConfig.BASE_WIDTH - highScoreWidth) / 2, GameConfig.BASE_HEIGHT / 2 + 10);


        // Hiển thị thông báo chơi lại
        g.setFont(new Font(GameConfig.FONT_NAME, Font.ITALIC, GameConfig.MESSAGE_FONT_SIZE - 4));
        FontMetrics fmRestart = g.getFontMetrics();
        String restartMessage = "Press SPACE to Play Again";
        int restartMessageWidth = fmRestart.stringWidth(restartMessage);
        g.drawString(restartMessage, (GameConfig.BASE_WIDTH - restartMessageWidth) / 2, GameConfig.BASE_HEIGHT / 2 + 50);
    }

    /**
     * Xử lý sự kiện nhấn phím.
     * Nếu phím SPACE được nhấn, chuyển sang trạng thái ReadyState để bắt đầu game mới.
     * @param e Đối tượng KeyEvent.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            // Không cần model.reset() ở đây nữa vì ReadyState.enter() sẽ làm điều đó.
            panel.setState(new ReadyState(panel));
        }
    }
}
