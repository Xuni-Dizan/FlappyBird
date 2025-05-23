package com.xuandai.flappybird.state;

import java.awt.*;
import java.awt.event.*;
import com.xuandai.flappybird.GamePanel;
import com.xuandai.flappybird.GameConfig; // Import GameConfig

public class PauseState implements GameState {
    private final GamePanel panel;
    // private final GameModel model; // Không cần model trực tiếp ở đây

    public PauseState(GamePanel p) {
        this.panel = p;
        // this.model = p.getModel();
    }

    /**
     * Được gọi khi vào trạng thái Pause.
     * Có thể dừng âm thanh hoặc các hoạt động khác ở đây.
     */
    @Override
    public void enter() {
        // Ví dụ: Dừng nhạc nền nếu có
        // SoundManager.pauseBackgroundMusic();
    }

    /**
     * Không có logic cập nhật nào trong trạng thái Pause,
     * vì game đã bị đóng băng.
     */
    @Override
    public void update() {
        // Game logic is paused
    }

    /**
     * Vẽ màn hình trạng thái Pause.
     * Hiển thị game bị đóng băng phía sau và thông báo tạm dừng.
     * @param g Đối tượng Graphics2D để vẽ.
     */
    @Override
    public void render(Graphics2D g) {
        // Vẽ trạng thái game hiện tại (bị đóng băng)
        panel.renderGame(g);

        // Vẽ một lớp phủ mờ lên trên màn hình game
        // để làm nổi bật thông báo tạm dừng
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f); // Độ mờ 50%
        g.setComposite(ac);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GameConfig.BASE_WIDTH, GameConfig.BASE_HEIGHT);

        // Reset composite để các thành phần vẽ sau không bị ảnh hưởng
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        // Thiết lập font và màu cho thông báo
        g.setColor(Color.WHITE); // Màu chữ cho thông báo (nổi bật trên nền mờ)
        g.setFont(new Font(GameConfig.FONT_NAME, Font.BOLD, GameConfig.MESSAGE_FONT_SIZE));

        // Hiển thị thông báo "PAUSED (press R to resume)" ở giữa màn hình
        String message = "PAUSED (press R to resume)";
        FontMetrics fm = g.getFontMetrics();
        int messageWidth = fm.stringWidth(message);
        int messageX = (GameConfig.BASE_WIDTH - messageWidth) / 2;
        // Điều chỉnh vị trí Y của thông báo cho phù hợp
        int messageY = GameConfig.BASE_HEIGHT / 2;

        g.drawString(message, messageX, messageY);
    }

    /**
     * Xử lý sự kiện nhấn phím.
     * Nếu phím 'R' được nhấn, quay lại trạng thái PlayingState.
     * @param e Đối tượng KeyEvent.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_R) {
            // Ví dụ: Tiếp tục nhạc nền nếu có
            // SoundManager.resumeBackgroundMusic();
            panel.setState(new PlayingState(panel));
        }
    }
}
