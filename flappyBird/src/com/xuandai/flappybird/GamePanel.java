package com.xuandai.flappybird;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.xuandai.flappybird.model.*;
import com.xuandai.flappybird.state.*;
import java.io.*;

/**
 * GamePanel là thành phần JPanel chính nơi trò chơi được vẽ và tương tác.
 * Nó quản lý vòng lặp game, trạng thái game, và xử lý sự kiện đầu vào.
 */
public class GamePanel extends JPanel implements Runnable, KeyListener {
    private Thread loopThread; // Thread cho vòng lặp game chính
    private volatile boolean running = false; // Cờ để kiểm soát vòng lặp game
    private GameState currentState; // Trạng thái hiện tại của game (ví dụ: Ready, Playing, GameOver)
    private final GameModel model; // Mô hình dữ liệu của game

    /**
     * Hàm khởi tạo cho GamePanel.
     * Thiết lập kích thước, khả năng nhận focus, đăng ký KeyListener,
     * tải điểm cao, đặt trạng thái ban đầu và bắt đầu vòng lặp game.
     */
    public GamePanel() {
        setPreferredSize(new Dimension(GameConfig.BASE_WIDTH, GameConfig.BASE_HEIGHT));
        setFocusable(true);
        addKeyListener(this);

        model = new GameModel(); // Khởi tạo model ở đây
        loadHighScore(); // Tải điểm cao khi game bắt đầu

        // Khởi tạo trạng thái ban đầu của game là ReadyState
        setState(new ReadyState(this));

        start();
    }

    /**
     * Thiết lập trạng thái hiện tại của game.
     * @param s Trạng thái mới để chuyển sang.
     */
    public void setState(GameState s){
        this.currentState = s;
        if (s != null) {
            s.enter(); // Gọi phương thức enter() của trạng thái mới
        }
    }

    /**
     * Trả về đối tượng GameModel.
     * @return GameModel của game.
     */
    public GameModel getModel() {
        return model;
    }

    /**
     * Bắt đầu vòng lặp game nếu nó chưa chạy.
     * Tạo và khởi chạy một Thread mới cho vòng lặp.
     */
    public synchronized void start(){ // Thêm synchronized để đảm bảo an toàn thread
        if(running) return;
        running = true;
        loopThread = new Thread(this, "GameLoopThread"); // Đặt tên cho Thread
        loopThread.start();
    }

    /**
     * Dừng vòng lặp game (chưa được sử dụng nhưng hữu ích).
     */
    public synchronized void stop() { // Thêm synchronized
        if (!running) return;
        running = false;
        try {
            loopThread.join(); // Chờ thread kết thúc
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức run() của interface Runnable, đây là vòng lặp game chính.
     * Vòng lặp sẽ cập nhật trạng thái game và vẽ lại giao diện với tần suất cố định (khoảng 60 FPS).
     */
    @Override
    public void run(){
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while(delta >= 1){
                if (currentState != null) {
                    currentState.update(); // Cập nhật logic game
                }
                delta--;
            }

            if(running){ // Kiểm tra lại running trước khi repaint
                repaint(); // Yêu cầu vẽ lại GamePanel
                frames++;
            }

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                // System.out.println("FPS: " + frames); // In FPS để debug (tùy chọn)
                frames = 0;
            }

            // Giữ cho vòng lặp không chạy quá nhanh không cần thiết
            // Cách sleep này có thể không chính xác bằng cách tính delta time cho sleep
            // nhưng với game đơn giản này có thể chấp nhận được.
            // Một cách tiếp cận tốt hơn là tính thời gian sleep dựa trên thời gian xử lý của frame.
            try {
                Thread.sleep(2); // Ngủ một chút để giảm tải CPU
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Ghi đè phương thức paintComponent để tùy chỉnh việc vẽ nội dung của GamePanel.
     * @param g Đối tượng Graphics được sử dụng để vẽ.
     */
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        // Bật anti-aliasing để đồ họa mượt mà hơn
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        if (currentState != null) {
            currentState.render(g2); // Ủy quyền việc vẽ cho trạng thái game hiện tại
        }
    }

    /**
     * Hàm tiện ích để vẽ các đối tượng game chung (chim, ống, điểm số)
     * được sử dụng bởi nhiều trạng thái game khác nhau.
     * @param g Đối tượng Graphics2D để vẽ.
     */
    public void renderGame(Graphics2D g){
        // Vẽ nền (nếu có, ví dụ: một màu hoặc hình ảnh)
        g.setColor(Color.CYAN); // Màu nền bầu trời đơn giản
        g.fillRect(0, 0, GameConfig.BASE_WIDTH, GameConfig.BASE_HEIGHT);

        Bird bird = model.getBird();

        // Vẽ các ống nước
        g.setColor(Color.GREEN.darker()); // Màu của ống
        for(Pipe pipe : model.getPipes()){
            // Vẽ phần ống trên
            g.fillRect(pipe.getX(), 0, GameConfig.PIPE_WIDTH, pipe.getTopPipeHeight());
            // Vẽ phần ống dưới
            int bottomPipeY = pipe.getTopPipeHeight() + pipe.getActualGapHeight();
            int bottomPipeHeight = GameConfig.BASE_HEIGHT - bottomPipeY;
            g.fillRect(pipe.getX(), bottomPipeY, GameConfig.PIPE_WIDTH, bottomPipeHeight);

            // (Tùy chọn) Vẽ đường viền cho ống để rõ ràng hơn
            g.setColor(Color.BLACK);
            g.drawRect(pipe.getX(), 0, GameConfig.PIPE_WIDTH, pipe.getTopPipeHeight());
            g.drawRect(pipe.getX(), bottomPipeY, GameConfig.PIPE_WIDTH, bottomPipeHeight);
            g.setColor(Color.GREEN.darker()); // Reset màu cho ống tiếp theo
        }

        // Vẽ con chim
        g.setColor(Color.ORANGE);
        g.fillOval((int)bird.getX(), (int)bird.getY(), bird.getWidth(), bird.getHeight());
        // (Tùy chọn) Vẽ đường viền cho chim
        g.setColor(Color.BLACK);
        g.drawOval((int)bird.getX(), (int)bird.getY(), bird.getWidth(), bird.getHeight());


        // Vẽ điểm số hiện tại và điểm cao
        g.setColor(Color.WHITE);
        g.setFont(new Font(GameConfig.FONT_NAME, Font.BOLD, GameConfig.SCORE_FONT_SIZE));

        String scoreText = "Score: " + model.getScore();
        g.drawString(scoreText, 10, 25);

        String highScoreText = "High: " + model.getHighScore();
        // Tính toán vị trí X cho điểm cao để căn phải hoặc đặt ở vị trí cố định
        FontMetrics fm = g.getFontMetrics();
        int highScoreTextWidth = fm.stringWidth(highScoreText);
        g.drawString(highScoreText, GameConfig.BASE_WIDTH - highScoreTextWidth - 10, 25);
    }

    /**
     * Lưu điểm cao nếu điểm hiện tại lớn hơn.
     * Dữ liệu điểm cao được lưu vào file "highscore.dat".
     */
    public void saveHighScore(){
        if(model.getScore() > model.getHighScore()){
            model.setHighScore(model.getScore()); // Cập nhật highscore trong model trước
            try(PrintWriter out = new PrintWriter(new FileWriter("highscore.dat"))){
                out.println(model.getHighScore());
            } catch(IOException e){
                System.err.println("Error saving high score: " + e.getMessage());
                // e.printStackTrace();
            }
        }
    }

    /**
     * Tải điểm cao từ file "highscore.dat" khi game bắt đầu.
     */
    public void loadHighScore(){
        File highScoreFile = new File("highscore.dat");
        if (highScoreFile.exists()) {
            try(java.util.Scanner sc = new java.util.Scanner(highScoreFile)){
                if(sc.hasNextInt()){
                    model.setHighScore(sc.nextInt());
                } else {
                    model.setHighScore(0); // Nếu file trống hoặc không hợp lệ
                }
            } catch(FileNotFoundException e) {
                // Điều này không nên xảy ra vì đã kiểm tra file.exists()
                System.err.println("High score file not found (should not happen here): " + e.getMessage());
                model.setHighScore(0);
            }
            // Không cần bắt IOException chung ở đây vì Scanner không ném nó cho constructor File
        } else {
            // File không tồn tại, có thể là lần chạy đầu tiên
            model.setHighScore(0);
        }
    }

    // ---------------- KeyListener Methods ----------------
    /**
     * Xử lý sự kiện khi một phím được nhấn.
     * Ủy quyền xử lý cho trạng thái game hiện tại.
     * @param e Đối tượng KeyEvent chứa thông tin về sự kiện.
     */
    @Override
    public void keyPressed(KeyEvent e){
        if (currentState != null) {
            currentState.keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e){}

    @Override
    public void keyTyped(KeyEvent e){}
}
