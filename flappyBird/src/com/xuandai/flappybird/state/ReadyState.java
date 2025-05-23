package com.xuandai.flappybird.state;

import com.xuandai.flappybird.GameConfig;
import com.xuandai.flappybird.GamePanel;
import com.xuandai.flappybird.model.GameModel; // Import GameModel
import java.awt.*;
import java.awt.event.*;

public class ReadyState implements GameState {
    private final GamePanel panel;
    private final GameModel model; // Thêm tham chiếu đến GameModel

    public ReadyState(GamePanel p) {
        this.panel = p;
        this.model = p.getModel(); // Lấy GameModel từ GamePanel
    }

    /**
     * Được gọi khi vào trạng thái Ready.
     * Reset trạng thái game để chuẩn bị cho lượt chơi mới.
     */
    @Override
    public void enter() {
        model.reset(); // Reset vị trí chim, ống, điểm số, v.v.
        // Không cần tải lại highscore ở đây vì nó đã được tải khi GamePanel khởi tạo
        // và chỉ được cập nhật trong model, lưu khi vào GameOverState.
    }

    /**
     * Không có logic cập nhật cụ thể trong trạng thái Ready,
     * vì game chưa bắt đầu. Chim có thể có một hoạt ảnh nhẹ nhàng (ví dụ: bay lên xuống)
     * nhưng hiện tại chúng ta giữ đơn giản.
     */
    @Override
    public void update() {
        // Có thể thêm hoạt ảnh cho chim ở đây nếu muốn (ví dụ: bay tại chỗ)
        // model.getBird().idleAnimation(); // Ví dụ
    }

    /**
     * Vẽ màn hình trạng thái Ready.
     * Hiển thị hướng dẫn bắt đầu và các yếu tố game cơ bản.
     * @param g Đối tượng Graphics2D để vẽ.
     */
    @Override
    public void render(Graphics2D g) {
        // Vẽ các thành phần game cơ bản (nền, chim ở vị trí chờ, điểm số ban đầu)
        panel.renderGame(g); // GamePanel sẽ vẽ nền, chim, ống (nếu có ống chờ), điểm

        // Thiết lập font và màu cho thông báo
        g.setColor(Color.BLACK); // Màu chữ cho thông báo
        g.setFont(new Font(GameConfig.FONT_NAME, Font.BOLD, GameConfig.MESSAGE_FONT_SIZE));

        // Hiển thị thông báo "Press SPACE to start" ở giữa màn hình
        String message = "Press SPACE to start";
        FontMetrics fm = g.getFontMetrics();
        int messageWidth = fm.stringWidth(message);
        int messageX = (GameConfig.BASE_WIDTH - messageWidth) / 2;
        int messageY = GameConfig.BASE_HEIGHT / 2; // Có thể điều chỉnh vị trí Y

        g.drawString(message, messageX, messageY);

        // (Tùy chọn) Hiển thị hình ảnh "Get Ready" hoặc logo game
        // Ví dụ: if (getReadyImage != null) g.drawImage(getReadyImage, x, y, null);
    }

    /**
     * Xử lý sự kiện nhấn phím.
     * Nếu phím SPACE được nhấn, chuyển sang trạng thái PlayingState.
     * @param e Đối tượng KeyEvent.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            panel.setState(new PlayingState(panel));
        }
    }
}
