import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import java.util.Random;

public class SudokuGUI {
    static JButton[][] buttons = new JButton[9][9]; // 每个按钮代表一个棋盘位
    static int[][]board=new int[9][9];//棋盘
    static int[][]board_right=new int[9][9];//棋盘
    static JPanel boardPanel;
    JPanel actPanel;
    JLabel Welcome;
    JButton startButton;
    JButton resetButton;
    JButton hintButton;
    JButton FinishButton;
    JButton MusicPlayer;
    static JComboBox<String> difficultyComboBox;
    static JLabel statusLabel;
    static int Remi=-1;
    static int Remj=-1;


    public SudokuGUI() {
        // 创建 JFrame 和 JPanel
        JFrame frame = new JFrame("阿姨，你怎么才来啊阿姨");
        frame.setSize(600, 800);
        frame.setLocationRelativeTo(null);

        boardPanel = new JPanel(new GridLayout(9, 9));
        boardPanel.setBounds(110,330,360,360);
        actPanel = new JPanel();
        actPanel.setLayout(null);

        Welcome=new JLabel("数独");
        Welcome.setBounds(215,60,150,40);
        Welcome.setFont(new Font("Normal", Font.BOLD, 40));
        Welcome.setHorizontalAlignment(SwingConstants.CENTER);
        Welcome.setVerticalAlignment(SwingConstants.CENTER);
        statusLabel = new JLabel("卢本伟文本框同步更新");
        statusLabel.setBounds(140,270,300,40);
        statusLabel.setFont(new Font("Normal", Font.ITALIC, 16));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setVerticalAlignment(SwingConstants.CENTER);

        startButton = new JButton("快速开始");
        startButton.setBounds(90,230,120,30);
        resetButton = new JButton("重设棋盘");
        resetButton.setBounds(230,230,120,30);
        hintButton = new JButton("下一步提示");
        hintButton.setBounds(150,710,120,30);
        String[] difficulties = {"Easy", "Medium", "Hard"};
        difficultyComboBox = new JComboBox<>(difficulties);
        difficultyComboBox.setBounds(370,230,130,30);
        FinishButton=new JButton("提交");
        FinishButton.setBounds(300,710,120,30);
        MusicPlayer=new JButton();
        MusicPlayer.setBounds(550,0,30,30);



        // 初始化按钮和面板
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setPreferredSize(new Dimension(40, 40));
                buttons[i][j].setFont(new Font("Normal", Font.BOLD, 11));
                buttons[i][j].setBackground(Color.WHITE);
                boardPanel.add(buttons[i][j]);
            }
        }

        startButton.addActionListener(ActionFile.startbutton_listen);
        resetButton.addActionListener(ActionFile.resetbutton_listen);
        hintButton.addActionListener(ActionFile.hintButton_listen);
        difficultyComboBox.addActionListener(ActionFile.comboxbutton_listen);
        FinishButton.addActionListener(ActionFile.finishbutton_listen);
        MusicPlayer.addActionListener(ActionFile.musicplay_listen);

        actPanel.add(startButton);
        actPanel.add(resetButton);
        actPanel.add(hintButton);
        actPanel.add(difficultyComboBox);
        actPanel.add(statusLabel);
        actPanel.add(Welcome);
        actPanel.add(FinishButton);
        actPanel.add(MusicPlayer);

        frame.add(boardPanel);
        frame.add(actPanel);
        frame.setVisible(true);
    }
    public Color getRandomColor() {
        Random random = new Random();
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);
        Color color = new Color(red, green, blue);
        return color;
    }
    public static void main(String[] args) {
        SudokuGUI gui = new SudokuGUI();
    }
}