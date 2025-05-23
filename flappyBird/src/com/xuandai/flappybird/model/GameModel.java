package com.xuandai.flappybird.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Random;
import com.xuandai.flappybird.GameConfig;

public class GameModel {
    private Bird bird;
    private final List<Pipe> pipes = new ArrayList<Pipe>();
    private int score = 0;
    private int highScore = 0;
    private float pipeSpeed = GameConfig.PIPE_SPEED_START; // Tốc độ ống có thể là số thực
    private int tick = 0;
    private static final Random random = new Random();
    private int nextPipeFrame; // Thời điểm tạo ống tiếp theo (tính bằng frame)
    private static final int MIN_PIPE_SPAWN_INTERVAL = 90; // Khoảng cách frame tối thiểu giữa các lần tạo ống (1.5 giây ở 60FPS)
    private static final int MAX_PIPE_SPAWN_INTERVAL = 120; // Khoảng cách frame tối đa (2 giây ở 60FPS)
    private static final float MAX_PIPE_SPEED = 6.0f; // Tốc độ tối đa của ống
    private static final float PIPE_SPEED_INCREMENT = 0.001f; // Mức tăng tốc độ ống mỗi frame

    public GameModel() {
        // Không cần gọi loadHighScore ở đây, GamePanel sẽ gọi
        reset();
    }

    /**
     * Thiết lập lại trạng thái của trò chơi về ban đầu.
     * Được gọi khi bắt đầu game mới hoặc khi chơi lại.
     */
    public void reset() {
        bird = new Bird(GameConfig.BASE_WIDTH / 4, GameConfig.BASE_HEIGHT / 2 - 50); // Vị trí chim hợp lý hơn
        pipes.clear();
        score = 0;
        pipeSpeed = GameConfig.PIPE_SPEED_START;
        tick = 0;
        // Đặt thời điểm tạo ống đầu tiên
        nextPipeFrame = 10; // Tạo ống đầu tiên sớm hơn một chút để người chơi thấy
        // Tạo các ống ban đầu để lấp đầy màn hình
        spawnInitialPipes();
    }

    /**
     * Tạo các ống ban đầu khi game bắt đầu hoặc reset.
     * Điều này giúp màn hình không bị trống khi game mới bắt đầu.
     */
    private void spawnInitialPipes() {
        // Tạo ống đầu tiên ở một khoảng cách xa hơn một chút
        pipes.add(new Pipe(GameConfig.BASE_WIDTH + 100, pipeSpeed, true)); // Ống đầu tiên
        // Tạo thêm một vài ống nữa để lấp đầy màn hình ban đầu
        // Khoảng cách giữa các ống ban đầu có thể cố định hoặc hơi ngẫu nhiên
        int currentX = GameConfig.BASE_WIDTH + 100 + Pipe.HORIZONTAL_SPACING + random.nextInt(50) - 25;
        for (int i = 0; i < 2; i++) { // Tạo thêm 2 ống
            pipes.add(new Pipe(currentX, pipeSpeed, true));
            currentX += Pipe.HORIZONTAL_SPACING + random.nextInt(50) - 25;
        }
        // Đặt thời điểm tạo ống tiếp theo sau các ống ban đầu
        nextPipeFrame = tick + MIN_PIPE_SPAWN_INTERVAL + random.nextInt(MAX_PIPE_SPAWN_INTERVAL - MIN_PIPE_SPAWN_INTERVAL);
    }


    /**
     * Tạo một ống mới và thêm vào danh sách.
     * Ống mới sẽ được tạo ở bên phải màn hình.
     */
    private void spawnPipe() {
        // Ống mới sẽ được tạo ở ngoài màn hình bên phải
        // Vị trí X của ống mới sẽ là vị trí của ống cuối cùng + một khoảng cách ngẫu nhiên
        int lastPipeX = GameConfig.BASE_WIDTH; // Mặc định nếu chưa có ống nào
        if (!pipes.isEmpty()) {
            lastPipeX = pipes.get(pipes.size() - 1).getX();
        }
        // Đảm bảo ống mới được tạo đủ xa ống cuối cùng
        int newPipeX = Math.max(GameConfig.BASE_WIDTH + 50, lastPipeX + Pipe.HORIZONTAL_SPACING + random.nextInt(60) - 30);
        pipes.add(new Pipe(newPipeX, pipeSpeed, false)); // false vì đây không phải ống ban đầu
    }

    /**
     * Cập nhật trạng thái của tất cả các đối tượng trong game.
     * Bao gồm chim, ống, và logic tính điểm, kiểm tra va chạm.
     */
    public void update() {
        tick++;
        bird.update(); // Cập nhật vị trí của chim

        // Tăng tốc độ ống từ từ cho đến khi đạt tốc độ tối đa
        if (pipeSpeed < MAX_PIPE_SPEED) {
            pipeSpeed += PIPE_SPEED_INCREMENT;
        } else {
            pipeSpeed = MAX_PIPE_SPEED;
        }

        // Tạo ống mới nếu đến thời điểm
        if (tick >= nextPipeFrame) {
            spawnPipe();
            // Đặt thời điểm tạo ống tiếp theo
            nextPipeFrame = tick + MIN_PIPE_SPAWN_INTERVAL + random.nextInt(MAX_PIPE_SPAWN_INTERVAL - MIN_PIPE_SPAWN_INTERVAL);
        }

        // Di chuyển và kiểm tra ống
        Iterator<Pipe> it = pipes.iterator();
        while (it.hasNext()) {
            Pipe p = it.next();
            p.setSpeed(pipeSpeed); // Cập nhật tốc độ cho từng ống
            p.update();

            // Kiểm tra nếu chim đã vượt qua ống và chưa được tính điểm
            if (!p.isPassed() && bird.getX() > p.getX() + GameConfig.PIPE_WIDTH) {
                p.setPassed(true);
                score++;
                // System.out.println("Score: " + score); // Debug log
            }

            // Xóa ống nếu nó đã ra khỏi màn hình hoàn toàn
            if (p.isOffScreen()) {
                it.remove();
            }
        }
    }

    /**
     * Kiểm tra xem chim có va chạm với ống hoặc lề màn hình không.
     * @return true nếu có va chạm, false nếu không.
     */
    public boolean isBirdColliding() {
        Rectangle birdBounds = bird.getBounds();

        // Kiểm tra va chạm với lề trên và lề dưới màn hình
        if (bird.getY() <= 0 || bird.getY() + bird.getHeight() >= GameConfig.BASE_HEIGHT) {
            // System.out.println("Collision with ceiling/floor"); // Debug log
            return true;
        }

        // Kiểm tra va chạm với các ống
        for (Pipe p : pipes) {
            if (birdBounds.intersects(p.getTopRect()) || birdBounds.intersects(p.getBottomRect())) {
                // System.out.println("Collision with pipe at X: " + p.getX()); // Debug log
                return true;
            }
        }
        return false;
    }

    // Getters and Setters
    public Bird getBird() {
        return bird;
    }

    public List<Pipe> getPipes() {
        return pipes;
    }

    public int getScore() {
        return score;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int h) {
        // Chỉ cập nhật nếu điểm mới cao hơn
        if (h > highScore) {
            highScore = h;
        }
    }
}
