import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Random;

public class ActionFile extends SudokuGUI {
    //开始、重置和提示按钮的监听
    public static ActionListener startbutton_listen = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 开始游戏
            SudokuGenerator Sukudo_board = new SudokuGenerator();
            Sukudo_board.generate();
            board_right = Sukudo_board.Get_Board();
            Sukudo_board.Go_Bad(new Random().nextInt(3));
            board = Sukudo_board.Get_Board();
            int[][] board = Sukudo_board.Get_Board();
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    String a = String.valueOf(board[i][j]);
                    if (a.equals("0"))
                        buttons[i][j].setText(" ");
                    else {
                        buttons[i][j].setText(a);
                        buttons[i][j].setForeground(Color.blue);
                    }
                    if (i % 3 == 0 && j % 3 == 0)
                        buttons[i][j].setBackground(Color.GRAY);
                    int finalI = i;
                    int finalJ = j;
                    buttons[i][j].addActionListener(e1 -> {
                        //删除之前的
                        if (Remi != -1 && Remj != -1) {
                            for (int k = 0; k < 9; k++) {
                                buttons[Remi][k].setBackground(Color.WHITE);
                                if (Remi % 3 == 0 && k % 3 == 0)
                                    buttons[Remi][k].setBackground(Color.GRAY);
                            }
                            for (int k = 0; k < 9; k++) {
                                buttons[k][Remj].setBackground(Color.WHITE);
                                if (k % 3 == 0 && Remj % 3 == 0)
                                    buttons[k][Remj].setBackground(Color.GRAY);
                            }
                        }
                        //标记当前的
                        for (int k = 0; k < 9; k++) {
                            if (k == finalJ)
                                buttons[finalI][k].setBackground(Color.ORANGE);
                            else
                                buttons[finalI][k].setBackground(Color.YELLOW);
                        }
                        for (int k = 0; k < 9; k++) {
                            if (k == finalI)
                                buttons[k][finalJ].setBackground(Color.ORANGE);
                            else
                                buttons[k][finalJ].setBackground(Color.YELLOW);
                        }
                        Remi = finalI;
                        Remj = finalJ;
                        //空值可选
                        if (Objects.equals(buttons[finalI][finalJ].getText(), " ")) {
                            buttons[finalI][finalJ].addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    // 添加键盘监控程序
                                    buttons[finalI][finalJ].addKeyListener(new KeyAdapter() {
                                        @Override
                                        public void keyTyped(KeyEvent e) {
                                            // 获取用户输入的字符
                                            String c = String.valueOf(e.getKeyChar());
                                            // 值改为用户输入的字符
                                            buttons[finalI][finalJ].setText(c);
                                            //如果输入不正确
                                            if (!c.equals(String.valueOf(board_right[finalI][finalJ]))) {
                                                statusLabel.setText("买挂吗阿姨");
                                                buttons[finalI][finalJ].setBackground(Color.RED);
                                                //为用户提供提示，错在哪了
                                                for (int i = 0; i < 9; i++) {
                                                    if (Objects.equals(buttons[finalI][i].getText(), c)) {
                                                        buttons[finalI][i].setBackground(Color.RED);
                                                    }
                                                    if (Objects.equals(buttons[i][finalJ].getText(), c)) {
                                                        buttons[i][finalJ].setBackground(Color.RED);
                                                    }
                                                }
                                                boardPanel.revalidate();
                                                //显示两秒然后恢复
                                                Timer timer = new Timer(200, new ActionListener() {
                                                    public void actionPerformed(ActionEvent e) {
                                                        for (int i = 0; i < 9; i++) {
                                                            buttons[finalI][i].setBackground(Color.YELLOW);
                                                            buttons[i][finalJ].setBackground(Color.YELLOW);
                                                        }
                                                        buttons[finalI][finalJ].setBackground(Color.ORANGE);
                                                    }
                                                });
                                                timer.setRepeats(false);
                                                timer.start();
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            }
            seconds=0;
            statusLabel.setText("正式开始，设置难度随机");
            timer.start();
        }
    };
    public static ActionListener resetbutton_listen = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 重置棋盘
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    String a = String.valueOf(board[i][j]);
                    if (a.equals("0"))
                        buttons[i][j].setText(" ");
                    else
                        buttons[i][j].setText(a);
                    buttons[i][j].setBackground(Color.WHITE);
                    if (i % 3 == 0 && j % 3 == 0)
                        buttons[i][j].setBackground(Color.GRAY);
                }
            }
            statusLabel.setText("棋盘重置");
            seconds=0;
            timer.start();
        }
    };
    public static ActionListener comboxbutton_listen = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    String a = String.valueOf(board[i][j]);
                    if (a.equals("0"))
                        buttons[i][j].setText(" ");
                    else
                        buttons[i][j].setText(a);
                    buttons[i][j].setBackground(Color.WHITE);
                    if (i % 3 == 0 && j % 3 == 0)
                        buttons[i][j].setBackground(Color.GRAY);
                }
            }
            // 根据选择的难度设置棋盘
            if (difficultyComboBox.getSelectedItem() == "入门级") {
                statusLabel.setText("难度简单");
                SudokuGenerator Sukudo_board = new SudokuGenerator();
                Sukudo_board.generate();
                //保留正确的副本
                board_right = Sukudo_board.Get_Board();
                Sukudo_board.Go_Bad(0);
                //设定难度所对应的棋盘
                board = Sukudo_board.Get_Board();
            }
            if (difficultyComboBox.getSelectedItem() == "进阶") {
                statusLabel.setText("难度正常");
                SudokuGenerator Sukudo_board = new SudokuGenerator();
                Sukudo_board.generate();
                //保留正确的副本
                board_right = Sukudo_board.Get_Board();
                Sukudo_board.Go_Bad(1);
                //设定难度所对应的棋盘
                board = Sukudo_board.Get_Board();
            }
            if (difficultyComboBox.getSelectedItem() == "大师") {
                statusLabel.setText("给这位阿姨倒一杯卡布奇诺");
                SudokuGenerator Sukudo_board = new SudokuGenerator();
                Sukudo_board.generate();
                //保留正确的副本
                board_right = Sukudo_board.Get_Board();
                Sukudo_board.Go_Bad(2);
                //设定难度所对应的棋盘
                board = Sukudo_board.Get_Board();
            }
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    String a = String.valueOf(board[i][j]);
                    if (a.equals("0"))
                        buttons[i][j].setText(" ");

                    else {
                        buttons[i][j].setText(a);
                        buttons[i][j].setForeground(Color.blue);
                    }
                    if (i % 3 == 0 && j % 3 == 0)
                        buttons[i][j].setBackground(Color.GRAY);
                    int finalI = i;
                    int finalJ = j;
                    buttons[i][j].addActionListener(e1 -> {
                        //删除之前的
                        if (Remi != -1 && Remj != -1) {
                            for (int k = 0; k < 9; k++) {
                                buttons[Remi][k].setBackground(Color.WHITE);
                                if (Remi % 3 == 0 && k % 3 == 0)
                                    buttons[Remi][k].setBackground(Color.GRAY);
                            }
                            for (int k = 0; k < 9; k++) {
                                buttons[k][Remj].setBackground(Color.WHITE);
                                if (k % 3 == 0 && Remj % 3 == 0)
                                    buttons[k][Remj].setBackground(Color.GRAY);
                            }
                        }
                        //标记当前的
                        for (int k = 0; k < 9; k++) {
                            if (k == finalJ)
                                buttons[finalI][k].setBackground(Color.YELLOW);
                            else
                                buttons[finalI][k].setBackground(Color.ORANGE);
                        }
                        for (int k = 0; k < 9; k++) {
                            if (k == finalI)
                                buttons[k][finalJ].setBackground(Color.YELLOW);
                            else
                                buttons[k][finalJ].setBackground(Color.ORANGE);
                        }
                        Remi = finalI;
                        Remj = finalJ;
                        //空值可选
                        if (Objects.equals(buttons[finalI][finalJ].getText(), " ")) {
                            buttons[finalI][finalJ].addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    // 添加键盘监控程序
                                    buttons[finalI][finalJ].addKeyListener(new KeyAdapter() {
                                        @Override
                                        public void keyTyped(KeyEvent e) {
                                            // 获取用户输入的字符
                                            String c = String.valueOf(e.getKeyChar());
                                            // 值改为用户输入的字符
                                            buttons[finalI][finalJ].setText(c);
                                            //如果输入不正确
                                            if (!c.equals(String.valueOf(board_right[finalI][finalJ]))) {
                                                statusLabel.setText("您填错了");
                                                buttons[finalI][finalJ].setBackground(Color.RED);
                                                //为用户提供提示，错在哪了
                                                for (int i = 0; i < 9; i++) {
                                                    if (Objects.equals(buttons[finalI][i].getText(), c)) {
                                                        buttons[finalI][i].setBackground(Color.RED);
                                                    }
                                                    if (Objects.equals(buttons[i][finalJ].getText(), c)) {
                                                        buttons[i][finalJ].setBackground(Color.RED);
                                                    }
                                                }
                                                boardPanel.revalidate();
                                                //显示两秒然后恢复
                                                Timer timer = new Timer(200, new ActionListener() {
                                                    public void actionPerformed(ActionEvent e) {
                                                        for (int i = 0; i < 9; i++) {
                                                            buttons[finalI][i].setBackground(Color.YELLOW);
                                                            buttons[i][finalJ].setBackground(Color.YELLOW);
                                                        }
                                                        buttons[finalI][finalJ].setBackground(Color.ORANGE);

                                                    }
                                                });
                                                timer.setRepeats(false);
                                                timer.start();
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            }
            seconds=0;
            timer.start();
        }
    };
    public static ActionListener hintButton_listen = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            statusLabel.setText("为您提示");
            int[][] check = new int[9][9];
            int count = 0;
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (Objects.equals(buttons[i][j].getText(), " ")) {
                        check[i][j] = 1;
                        count++;
                    }
                }
            }
            Random rand1 = new Random();
            Random rand2 = new Random();
            while (true) {
                if (count == 0) {
                    statusLabel.setText("棋盘已满");
                    break;
                }
                int ii = rand1.nextInt(9);
                int jj = rand1.nextInt(9);
                if (check[ii][jj] == 1) {
                    buttons[ii][jj].setBackground(Color.pink);
                    buttons[ii][jj].setText(String.valueOf(board_right[ii][jj]));
                    break;
                }
            }
        }
    };
    public static ActionListener finishbutton_listen = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int count = 0;
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (!Objects.equals(buttons[i][j].getText(), String.valueOf(board_right[i][j]))) {
                        buttons[i][j].setBackground(Color.RED);
                        int finalI = i;
                        int finalJ = j;
                        Timer timer = new Timer(2000, new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                if (finalI % 3 == 0 && finalJ % 3 == 0)
                                    buttons[finalI][finalJ].setBackground(Color.GRAY);
                                else
                                    buttons[finalI][finalJ].setBackground(Color.WHITE);
                            }
                        });
                        timer.setRepeats(false);
                        timer.start();
                        statusLabel.setText("棋盘中中存在错误");
                        count--;
                    }
                    count++;
                }
            }
            if (count == 81) {
                statusLabel.setText("数独解决！");
                timer.stop();
            }
        }
    };
    public static ActionListener musicplay_listen = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isPlaying) {
                // 如果正在播放音乐，就暂停音乐并更新按钮文本
                music_player.stopmusic();
                isPlaying = false;
            } else {
                // 如果没有在播放音乐，就播放音乐并更新按钮文本
                music_player.playmusic();
                isPlaying = true;
            }
        }
    };
}
class MusicIndexFrm extends JFrame {
    private Player player ;
    MyRunnable musicTask;
    Thread musicThread;

    class MyRunnable implements Runnable {
        public void run() {
            try {
                if (null != player) {
                    player.close();
                }
                player = new Player(new FileInputStream(new File(getClass().getResource("src/Music.wav").toURI())));
                player.play();
            } catch (FileNotFoundException | JavaLayerException e1) {
                e1.printStackTrace();
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void playmusic(){
        musicTask = new MyRunnable();
        musicThread = new Thread(musicTask);
        musicThread.start();
    }
    public void stopmusic(){
        player.close();
    }
}

