package com.xuandai.flappybird.model;

import java.awt.*;
import java.util.Random;
import com.xuandai.flappybird.GameConfig;

public class Pipe {
    private int x; // Vị trí x của ống
    private float speed; // Tốc độ di chuyển của ống
    private boolean passed; // Cờ đánh dấu chim đã vượt qua ống này chưa
    private boolean isInitialPipe; // Đánh dấu nếu đây là ống được tạo ban đầu

    private int topPipeHeight; // Chiều cao của phần ống trên
    private int actualGapHeight; // Chiều cao thực tế của khoảng trống giữa hai ống

    private static final Random random = new Random();
    public static final int HORIZONTAL_SPACING = 220; // Khoảng cách ngang tối thiểu giữa các ống

    /**
     * Constructor cho đối tượng Pipe.
     * @param startX Vị trí X ban đầu của ống.
     * @param speed Tốc độ di chuyển ban đầu của ống.
     * @param isInitialPipe Đánh dấu nếu đây là một trong các ống được tạo khi game bắt đầu.
     */
    public Pipe(int startX, float speed, boolean isInitialPipe) {
        this.x = startX;
        this.speed = speed;
        this.passed = false;
        this.isInitialPipe = isInitialPipe; // Lưu lại trạng thái này, có thể dùng sau
        randomizeGap();
    }

    /**
     * Tạo ngẫu nhiên chiều cao cho ống trên và khoảng trống giữa hai ống.
     * Điều này tạo ra sự đa dạng trong thử thách của game.
     */
    private void randomizeGap() {
        // Chiều cao của khoảng trống được chọn ngẫu nhiên trong khoảng [GAP_MIN, GAP_MAX]
        this.actualGapHeight = GameConfig.GAP_MIN + random.nextInt(GameConfig.GAP_MAX - GameConfig.GAP_MIN + 1);

        // Chiều cao của ống trên được chọn ngẫu nhiên,
        // đảm bảo cả ống trên, khoảng trống và một phần ống dưới đều có thể hiển thị.
        int minTopPipeRenderHeight = 40; // Chiều cao tối thiểu để ống trên có thể nhìn thấy
        int minBottomPipeRenderHeight = 40; // Chiều cao tối thiểu để ống dưới có thể nhìn thấy

        // Tính toán giới hạn cho chiều cao của ống trên
        int minPossibleTopHeight = minTopPipeRenderHeight;
        int maxPossibleTopHeight = GameConfig.BASE_HEIGHT - minBottomPipeRenderHeight - this.actualGapHeight;

        if (minPossibleTopHeight >= maxPossibleTopHeight) {
            // Trường hợp không đủ không gian (ví dụ: màn hình quá nhỏ hoặc khoảng trống quá lớn)
            // Đặt khoảng trống ở giữa theo chiều dọc
            this.topPipeHeight = (GameConfig.BASE_HEIGHT - this.actualGapHeight) / 2;
            if (this.topPipeHeight < minTopPipeRenderHeight) { // Đảm bảo ống trên vẫn nhìn thấy được
                this.topPipeHeight = minTopPipeRenderHeight;
            }
            if (this.topPipeHeight + this.actualGapHeight > GameConfig.BASE_HEIGHT - minBottomPipeRenderHeight) {
                // Điều chỉnh nếu ống dưới bị quá nhỏ hoặc biến mất
                this.topPipeHeight = GameConfig.BASE_HEIGHT - minBottomPipeRenderHeight - this.actualGapHeight;
            }

        } else {
            this.topPipeHeight = minPossibleTopHeight + random.nextInt(maxPossibleTopHeight - minPossibleTopHeight + 1);
        }
        //System.out.println("New Pipe: topH=" + topPipeHeight + ", gapH=" + actualGapHeight + ", bottomY=" + (topPipeHeight + actualGapHeight));
    }

    /**
     * Cập nhật vị trí của ống.
     * Ống di chuyển từ phải sang trái.
     */
    public void update() {
        x -= speed;
    }

    /**
     * Kiểm tra xem ống đã hoàn toàn ra khỏi màn hình bên trái chưa.
     * @return true nếu ống đã ra khỏi màn hình, false nếu chưa.
     */
    public boolean isOffScreen() {
        return x + GameConfig.PIPE_WIDTH < 0;
    }

    /**
     * Reset vị trí và tốc độ của ống (không dùng trong logic hiện tại nhưng có thể hữu ích).
     * @param newX Vị trí X mới.
     * @param newSpeed Tốc độ mới.
     */
    public void reset(int newX, float newSpeed) {
        this.x = newX;
        this.speed = newSpeed;
        this.passed = false;
        randomizeGap();
    }

    // Getters
    public int getX() {
        return x;
    }

    /**
     * Trả về chiều cao của phần ống trên.
     * @return Chiều cao của ống trên.
     */
    public int getTopPipeHeight() {
        return topPipeHeight;
    }

    /**
     * Trả về chiều cao thực tế của khoảng trống.
     * @return Chiều cao khoảng trống.
     */
    public int getActualGapHeight() {
        return actualGapHeight;
    }


    public boolean isPassed() {
        return passed;
    }

    // Setters
    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    /**
     * Trả về hình chữ nhật bao quanh phần ống trên để kiểm tra va chạm.
     * @return Rectangle của ống trên.
     */
    public Rectangle getTopRect() {
        return new Rectangle(x, 0, GameConfig.PIPE_WIDTH, topPipeHeight);
    }

    /**
     * Trả về hình chữ nhật bao quanh phần ống dưới để kiểm tra va chạm.
     * @return Rectangle của ống dưới.
     */
    public Rectangle getBottomRect() {
        int bottomPipeY = topPipeHeight + actualGapHeight;
        int bottomPipeHeight = GameConfig.BASE_HEIGHT - bottomPipeY;
        // Đảm bảo bottomPipeHeight không âm nếu có lỗi logic nào đó
        if (bottomPipeHeight < 0) bottomPipeHeight = 0;
        return new Rectangle(x, bottomPipeY, GameConfig.PIPE_WIDTH, bottomPipeHeight);
    }
}
