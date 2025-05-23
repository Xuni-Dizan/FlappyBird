package com.xuandai.flappybird;

import javax.swing.*;

/**
 * Lớp GameWindow đại diện cho cửa sổ chính của trò chơi Flappy Bird.
 * Nó kế thừa từ JFrame để tạo một cửa sổ ứng dụng desktop.
 */
public class GameWindow extends JFrame {

    /**
     * Hàm khởi tạo cho GameWindow.
     * Thiết lập các thuộc tính của cửa sổ và hiển thị nó.
     */
    public GameWindow() {
        // Đặt tiêu đề cho cửa sổ trò chơi
        setTitle("Flappy Bird – Java");

        // Ngăn không cho người dùng thay đổi kích thước cửa sổ
        // Điều này giúp duy trì kích thước cố định cho game panel
        setResizable(false);

        // Thiết lập hành động mặc định khi người dùng đóng cửa sổ
        // JFrame.EXIT_ON_CLOSE sẽ kết thúc ứng dụng khi cửa sổ được đóng
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Tạo một đối tượng GamePanel mới (nơi trò chơi thực sự diễn ra)
        // và thêm nó vào cửa sổ GameWindow
        add(new GamePanel());

        // Điều chỉnh kích thước cửa sổ sao cho vừa với các thành phần con bên trong (GamePanel)
        pack();

        // Đặt vị trí cửa sổ ở giữa màn hình
        setLocationRelativeTo(null);

        // Hiển thị cửa sổ trò chơi
        // Đây là bước quan trọng để "Mở khung màn hình chơi"
        setVisible(true);
    }
}
