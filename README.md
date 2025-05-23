# Flappy Bird Game (Java Swing)

Đây là một phiên bản đơn giản của trò chơi Flappy Bird cổ điển, được viết bằng Java sử dụng thư viện Swing để tạo giao diện người dùng đồ họa.

## Tính năng

* **Lối chơi cổ điển:** Điều khiển chú chim bay qua các ống nước để ghi điểm.
* **Các trạng thái game:**
    * **Ready:** Màn hình chờ, nhấn SPACE để bắt đầu.
    * **Playing:** Trạng thái chơi chính.
    * **Game Over:** Hiển thị khi chim va chạm, cho phép chơi lại.
    * **Pause:** Tạm dừng game, nhấn P để tạm dừng và R để tiếp tục.
* **Tính điểm:** Ghi điểm mỗi khi chim vượt qua một cặp ống.
* **Lưu điểm cao:** Điểm cao nhất đạt được sẽ được lưu lại và hiển thị.
* **Độ khó tăng dần:** Tốc độ của ống nước tăng dần theo thời gian chơi.

## Cấu trúc Project

Project được tổ chức thành các package chính sau:

* `com.xuandai.flappybird`: Chứa các lớp cốt lõi của game.
    * `Main.java`: Điểm khởi đầu của ứng dụng.
    * `GameWindow.java`: Cửa sổ chính (JFrame) của trò chơi.
    * `GamePanel.java`: Khu vực (JPanel) nơi game được vẽ và vòng lặp game chạy.
    * `GameConfig.java`: Chứa các hằng số cấu hình cho game (ví dụ: kích thước, lực hấp dẫn, tốc độ).
* `com.xuandai.flappybird.model`: Chứa các lớp mô hình dữ liệu của game.
    * `GameModel.java`: Quản lý trạng thái logic của game (chim, ống, điểm số, va chạm).
    * `Bird.java`: Đại diện cho đối tượng chim.
    * `Pipe.java`: Đại diện cho đối tượng ống nước.
* `com.xuandai.flappybird.state`: Quản lý các trạng thái khác nhau của game.
    * `GameState.java`: Interface định nghĩa các phương thức chung cho một trạng thái game.
    * `ReadyState.java`: Trạng thái sẵn sàng để bắt đầu game.
    * `PlayingState.java`: Trạng thái đang chơi.
    * `GameOverState.java`: Trạng thái kết thúc game.
    * `PauseState.java`: Trạng thái tạm dừng game.
* `com.xuandai.flappybird.event`: (Hiện tại chưa được sử dụng sâu trong logic chính của game được cung cấp, nhưng có cấu trúc cho việc xử lý sự kiện game tùy chỉnh)
    * `GameEvent.java`: Định nghĩa các loại sự kiện game.
    * `GameListener.java`: Interface cho các lớp lắng nghe sự kiện game.
    * `EventDispatcher.java`: Lớp điều phối sự kiện.

## Cách chạy Game

1.  **Biên dịch (Compile):**
    * Nếu bạn sử dụng một IDE (như IntelliJ IDEA, Eclipse, NetBeans), IDE sẽ tự động xử lý việc biên dịch.
    * Nếu biên dịch thủ công bằng JDK, bạn cần biên dịch tất cả các file `.java`. Ví dụ, từ thư mục `src`:
        ```bash
        javac com/xuandai/flappybird/*.java com/xuandai/flappybird/model/*.java com/xuandai/flappybird/state/*.java com/xuandai/flappybird/event/*.java
        ```
2.  **Chạy (Run):**
    * Trong IDE: Tìm và chạy lớp `com.xuandai.flappybird.Main`.
    * Từ dòng lệnh (sau khi biên dịch, từ thư mục `src` hoặc thư mục chứa các file `.class` đã biên dịch):
        ```bash
        java com.xuandai.flappybird.Main
        ```

## Điều khiển

* **SPACEBAR:** Khiến chim vỗ cánh (bay lên).
* **P:** Tạm dừng game khi đang ở trạng thái `Playing`.
* **R:** Tiếp tục game khi đang ở trạng thái `Pause`.
* **SPACEBAR (ở màn hình Game Over):** Chơi lại game mới.

## Các Lớp Chính và Vai Trò

* **`Main`**: Khởi tạo và hiển thị `GameWindow`.
* **`GameWindow`**: Là `JFrame` chính, chứa `GamePanel`.
* **`GamePanel`**: Là `JPanel` nơi tất cả đồ họa của game được vẽ. Nó cũng chứa vòng lặp game chính (`Runnable`) và xử lý đầu vào từ bàn phím (`KeyListener`).
* **`GameModel`**: Chứa tất cả dữ liệu và logic của game, như vị trí chim, danh sách ống, điểm số hiện tại, điểm cao. Nó cập nhật trạng thái của các đối tượng game và kiểm tra va chạm.
* **`Bird`**: Đại diện cho người chơi (chim). Quản lý vị trí, chuyển động (vỗ cánh, rơi do trọng lực).
* **`Pipe`**: Đại diện cho các chướng ngại vật (ống nước). Quản lý vị trí, kích thước khoảng trống và di chuyển.
* **`GameState` (và các lớp con triển khai):**
    * Sử dụng State Pattern để quản lý các giai đoạn khác nhau của trò chơi (Ready, Playing, Paused, Game Over).
    * Mỗi trạng thái xử lý việc cập nhật logic, vẽ đồ họa và xử lý đầu vào riêng.
* **`GameConfig`**: Một lớp tiện ích chứa các hằng số và giá trị cấu hình cho game, giúp dễ dàng điều chỉnh các thông số của game.

Chúc bạn chơi game vui vẻ!
