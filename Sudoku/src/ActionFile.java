import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.TimerTask;

public class ActionFile extends SudokuGUI {
    //开始、重置和提示按钮的监听
    public static ActionListener startbutton_listen = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 开始游戏
            SudokuGenerator Sukudo_board = new SudokuGenerator();
            Sukudo_board.generate();
            board_right=Sukudo_board.Get_Board();
            Sukudo_board.Go_Bad(new Random().nextInt(3));
            board=Sukudo_board.Get_Board();
            int[][] board = Sukudo_board.Get_Board();
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    String a = String.valueOf(board[i][j]);
                    if (a.equals("0"))
                        buttons[i][j].setText(" ");
                    else
                        buttons[i][j].setText(a);
                    if (i % 3 == 0 && j % 3 == 0)
                        buttons[i][j].setBackground(Color.GRAY);
                    int finalI = i;
                    int finalJ = j;
                    buttons[i][j].addActionListener(e1 -> {
                        //删除之前的
                        if (Remi!=-1&&Remj!=-1) {
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
                            if(k==finalJ)
                                buttons[finalI][k].setBackground(Color.YELLOW);
                            else
                                buttons[finalI][k].setBackground(Color.ORANGE);
                        }
                        for (int k = 0; k < 9; k++) {
                            if(k==finalI)
                                buttons[k][finalJ].setBackground(Color.YELLOW);
                            else
                                buttons[k][finalJ].setBackground(Color.ORANGE);
                        }
                        Remi=finalI;Remj=finalJ;
                        //空值可选
                        if(Objects.equals(buttons[finalI][finalJ].getText(), " ")){
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
                                            if(!c.equals(String.valueOf(board_right[finalI][finalJ]))) {
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
                                                Timer timer = new Timer(2000, new ActionListener() {
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
            statusLabel.setText("阿姨喝咖啡吗阿姨");
        }
    };
    public static ActionListener resetbutton_listen=new ActionListener() {
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
        }
    };
    public static ActionListener comboxbutton_listen=new ActionListener() {
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
            if (difficultyComboBox.getSelectedItem() == "Easy") {
                statusLabel.setText("阿姨喝咖啡吗阿姨");
                SudokuGenerator Sukudo_board = new SudokuGenerator();
                Sukudo_board.generate();
                //保留正确的副本
                board_right=Sukudo_board.Get_Board();
                Sukudo_board.Go_Bad(0);
                //设定难度所对应的棋盘
                board = Sukudo_board.Get_Board();
            }
            if (difficultyComboBox.getSelectedItem() == "Medium") {
                statusLabel.setText("阿姨喝咖啡吗阿姨");
                SudokuGenerator Sukudo_board = new SudokuGenerator();
                Sukudo_board.generate();
                //保留正确的副本
                board_right=Sukudo_board.Get_Board();
                Sukudo_board.Go_Bad(1);
                //设定难度所对应的棋盘
                board = Sukudo_board.Get_Board();
            }
            if (difficultyComboBox.getSelectedItem() == "Hard") {
                statusLabel.setText("给这位阿姨倒一杯卡布奇诺");
                SudokuGenerator Sukudo_board = new SudokuGenerator();
                Sukudo_board.generate();
                //保留正确的副本
                board_right=Sukudo_board.Get_Board();
                Sukudo_board.Go_Bad(2);
                //设定难度所对应的棋盘
                board = Sukudo_board.Get_Board();
            }
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    String a = String.valueOf(board[i][j]);
                    if (a.equals("0"))
                        buttons[i][j].setText(" ");
                    else
                        buttons[i][j].setText(a);
                    if (i % 3 == 0 && j % 3 == 0)
                        buttons[i][j].setBackground(Color.GRAY);
                    int finalI = i;
                    int finalJ = j;
                    buttons[i][j].addActionListener(e1 -> {
                        //删除之前的
                        if (Remi!=-1&&Remj!=-1) {
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
                            if(k==finalJ)
                                buttons[finalI][k].setBackground(Color.YELLOW);
                            else
                                buttons[finalI][k].setBackground(Color.ORANGE);
                        }
                        for (int k = 0; k < 9; k++) {
                            if(k==finalI)
                                buttons[k][finalJ].setBackground(Color.YELLOW);
                            else
                                buttons[k][finalJ].setBackground(Color.ORANGE);
                        }
                        Remi=finalI;Remj=finalJ;
                        //空值可选
                        if(Objects.equals(buttons[finalI][finalJ].getText(), " ")){
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
                                            if(!c.equals(String.valueOf(board_right[finalI][finalJ]))) {
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
                                                Timer timer = new Timer(2000, new ActionListener() {
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
        }
    };
    public static ActionListener hintButton_listen=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            statusLabel.setText("来一波卢本伟的番茄连招");
            int[][] check = new int[9][9];
            int count=0;
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
                if(count==0) {
                    statusLabel.setText("得得得得得得得得得得得得");
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
            int count=0;
            for(int i=0;i<9;i++){
                for(int j=0;j<9;j++){
                    if(!Objects.equals(buttons[i][j].getText(), String.valueOf(board_right[i][j]))) {
                        buttons[i][j].setBackground(Color.RED);
                        int finalI = i;
                        int finalJ = j;
                        Timer timer = new Timer(2000, new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                buttons[finalI][finalJ].setBackground(Color.WHITE);
                            }
                        });
                        timer.setRepeats(false);
                        timer.start();
                        statusLabel.setText("玩游戏一定要笑，要笑着玩");
                        count--;
                    }
                    count++;
                }
            }
            if(count==81)
                statusLabel.setText("我卢本伟愿称你为赌怪");
        }
    };
    public static ActionListener musicplay_listen=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // 从指定路径加载音频文件
                File musicFile = new File("D:\\MyCode\\Java\\Sudoku\\src\\Music.wav");
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };
}