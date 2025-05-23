package com.xuandai.flappybird;

/**
 * Lớp GameConfig chứa các hằng số cấu hình cho trò chơi.
 * Việc sử dụng các hằng số giúp dễ dàng quản lý và thay đổi các giá trị quan trọng của game.
 * Constructor được để private để ngăn việc tạo instance từ lớp này (utility class).
 */
public final class GameConfig {

    // Ngăn không cho tạo đối tượng từ lớp này
    private GameConfig() {}

    // Kích thước cơ bản của cửa sổ game, dựa trên kích thước của tài nguyên gốc.
    public static final int BASE_WIDTH  = 288;   // Chiều rộng gốc của tài nguyên game
    public static final int BASE_HEIGHT = 512;   // Chiều cao gốc của tài nguyên game

    // Các hằng số liên quan đến vật lý của chim
    public static final float GRAVITY    = 0.30f; // Lực hấp dẫn tác động lên chim (giảm nhẹ để kiểm soát tốt hơn)
    public static final float FLAP_FORCE = -6.0f; // Lực đẩy lên khi chim vỗ cánh (giảm nhẹ)
    public static final int BIRD_START_X = BASE_WIDTH / 4; // Vị trí X ban đầu của chim
    public static final int BIRD_START_Y = BASE_HEIGHT / 2 - 50; // Vị trí Y ban đầu của chim
    public static final int BIRD_WIDTH = 34;     // Chiều rộng của chim (sprite gốc)
    public static final int BIRD_HEIGHT = 24;    // Chiều cao của chim (sprite gốc)


    // Các hằng số liên quan đến ống nước
    public static final int PIPE_WIDTH   = 52;    // Chiều rộng của một ống nước
    public static final int GAP_MIN      = 100;   // Khoảng trống tối thiểu giữa ống trên và ống dưới (tăng nhẹ)
    public static final int GAP_MAX      = 150;   // Khoảng trống tối đa giữa ống trên và ống dưới (tăng nhẹ)
    public static final float PIPE_SPEED_START = 1.8f; // Tốc độ di chuyển ban đầu của ống nước (float)
    public static final float MAX_PIPE_SPEED = 5.0f; // Tốc độ tối đa của ống
    public static final float PIPE_SPEED_INCREMENT = 0.0008f; // Mức tăng tốc độ ống mỗi frame khi game chạy

    // Các hằng số liên quan đến việc tạo ống
    public static final int MIN_PIPE_SPAWN_INTERVAL_FRAMES = 90;  // Khoảng cách frame tối thiểu giữa các lần tạo ống (1.5 giây @ 60FPS)
    public static final int MAX_PIPE_SPAWN_INTERVAL_FRAMES = 120; // Khoảng cách frame tối đa (2 giây @ 60FPS)
    public static final int PIPE_HORIZONTAL_SPACING = 220; // Khoảng cách ngang cố định giữa các ống (tham chiếu từ Pipe.java)

    // Font và hiển thị
    public static final String FONT_NAME = "Arial";
    public static final int SCORE_FONT_SIZE = 24;
    public static final int MESSAGE_FONT_SIZE = 20;
    public static final int HIGHSCORE_X_OFFSET = 110; // Điều chỉnh vị trí hiển thị điểm cao
}
