package com.xuandai.flappybird;

/**
 * Lớp Main chứa phương thức main, là điểm khởi đầu của ứng dụng Flappy Bird.
 */
public class Main {

    /**
     * Phương thức main của ứng dụng.
     * Nó sử dụng SwingUtilities.invokeLater để đảm bảo rằng việc tạo và hiển thị
     * giao diện người dùng (GUI) được thực hiện trên Event Dispatch Thread (EDT),
     * điều này là cần thiết cho các ứng dụng Swing để tránh các vấn đề về thread.
     *
     * @param args Đối số dòng lệnh (không được sử dụng trong ứng dụng này).
     */
    public static void main(String[] args) {
        // Sử dụng SwingUtilities.invokeLater để tạo và hiển thị GameWindow.
        // Điều này đảm bảo rằng tất cả các tác vụ liên quan đến Swing GUI
        // được thực hiện một cách an toàn trên Event Dispatch Thread.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Tạo một đối tượng GameWindow mới.
                // Hàm khởi tạo của GameWindow sẽ xử lý việc thiết lập và hiển thị cửa sổ trò chơi.
                // Đây chính là bước khởi tạo để "Mở khung màn hình chơi".
                new GameWindow();
            }
        });
    }
}
