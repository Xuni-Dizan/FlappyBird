package com.xuandai.flappybird.model;

import java.awt.*;
import com.xuandai.flappybird.GameConfig;

public class Bird {
    private float x, y; // Vị trí hiện tại của chim (x, y)
    private float velY; // Vận tốc theo chiều dọc của chim

    // Kích thước của chim sẽ được lấy từ GameConfig
    // private final int width = 34, height = 24; // Giá trị cũ

    /**
     * Constructor cho đối tượng Bird.
     * @param startX Vị trí X ban đầu của chim.
     * @param startY Vị trí Y ban đầu của chim.
     */
    public Bird(float startX, float startY) {
        this.x = startX;
        this.y = startY;
        this.velY = 0; // Vận tốc ban đầu theo chiều Y là 0
    }

    /**
     * Khi chim vỗ cánh, đặt lại vận tốc theo chiều dọc.
     * Lực vỗ cánh được lấy từ GameConfig.
     */
    public void flap() {
        velY = GameConfig.FLAP_FORCE;
    }

    /**
     * Cập nhật vị trí và vận tốc của chim dựa trên trọng lực.
     * Trọng lực được lấy từ GameConfig.
     */
    public void update() {
        velY += GameConfig.GRAVITY; // Áp dụng trọng lực để tăng vận tốc rơi
        y += velY; // Cập nhật vị trí Y dựa trên vận tốc

        // Giới hạn vị trí của chim không vượt quá trần (ví dụ)
        // Mặc dù va chạm với trần/sàn đã được xử lý trong GameModel,
        // việc này có thể ngăn chim bay quá cao khỏi màn hình một cách trực quan.
        // Tuy nhiên, logic game gốc thường không giới hạn y ở đây mà để va chạm xử lý.
        // if (y < 0) {
        // y = 0;
        // velY = 0; // Ngăn vận tốc âm tiếp tục kéo chim lên
        // }

        // Việc kiểm tra va chạm với sàn (GameConfig.BASE_HEIGHT) sẽ được thực hiện trong GameModel.
    }

    /**
     * Trả về hình chữ nhật bao quanh con chim, dùng để kiểm tra va chạm.
     * Kích thước được lấy từ GameConfig.
     * @return Rectangle đại diện cho giới hạn của chim.
     */
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, GameConfig.BIRD_WIDTH, GameConfig.BIRD_HEIGHT);
    }

    // Getters
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    // Thêm getter cho chiều rộng và chiều cao nếu cần thiết từ bên ngoài lớp Bird,
    // mặc dù thường thì getBounds() là đủ cho việc kiểm tra va chạm và render.
    public int getWidth() {
        return GameConfig.BIRD_WIDTH;
    }

    public int getHeight() {
        return GameConfig.BIRD_HEIGHT;
    }

    /**
     * Reset trạng thái của chim về vị trí và vận tốc ban đầu.
     * Được gọi bởi GameModel khi bắt đầu game mới.
     * @param startX Vị trí X mới.
     * @param startY Vị trí Y mới.
     */
    public void reset(float startX, float startY) {
        this.x = startX;
        this.y = startY;
        this.velY = 0;
    }
}
