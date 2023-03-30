package lab2;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;

class Main_show extends JFrame {
    public Main_show() {
        // 设置窗口标题
        setTitle("欢迎！");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JButton Similarity = new JButton("文件查重");
        JButton Max_Similarity =new JButton("最相似文件输出");
        JButton All_Similarity = new JButton("全部文件输出");
        JLabel note=new JLabel("CUG-rico_guo设计！点击下面的按钮就可以开始了");
        JButton In_dir_sim=new JButton("子目录相似度");

        Similarity.setBounds(325,200,150,30);
        Max_Similarity.setBounds(325,275,150,30);
        All_Similarity.setBounds(325,350,150,30);
        note.setBounds(225,100,400,30);
        In_dir_sim.setBounds(325,425,150,30);

        panel.add(Similarity);
        panel.add(Max_Similarity);
        panel.add(All_Similarity);
        panel.add(note);
        panel.add(In_dir_sim);
        add(panel);

        Similarity.addActionListener(e -> {
            cal_Similarity target=new cal_Similarity();
        });
        Max_Similarity.addActionListener(e ->{
            max_Similarity target=new max_Similarity();
        });
        All_Similarity.addActionListener(e->{
            all_SImilarity target=new all_SImilarity();
        });
        In_dir_sim.addActionListener(e->{
            in_dir_sim target=new in_dir_sim();
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new Main_show();
    }
}
class cal_Similarity extends JFrame{
    public cal_Similarity() {
        setTitle("文件查重");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JButton clear1 = new JButton("清空");
        JButton clear2 = new JButton("清空");
        JTextField textField1 = new JTextField();
        JTextField textField2 = new JTextField();
        JButton open1 = new JButton("*");
        JButton open2 = new JButton("*");
        JButton sure = new JButton("开始查重");
        JLabel result = new JLabel("相似度在这里显示哦~");
        JLabel text1 = new JLabel("文件1");
        JLabel text2 = new JLabel("文件2");

        clear1.setBounds(250, 70, 100, 20);
        clear2.setBounds(250, 200, 100, 20);
        textField1.setBounds(140, 40, 300, 25);
        open1.setBounds(445, 40, 20, 20);
        textField2.setBounds(140, 170, 300, 25);
        open2.setBounds(445, 170, 20, 20);
        sure.setBounds(200, 285, 200, 50);
        result.setBounds(240, 250, 120, 20);
        text1.setBounds(140, 20, 50, 20);
        text2.setBounds(140, 150, 50, 20);

        panel.add(clear1);
        panel.add(clear2);
        panel.add(textField1);
        panel.add(textField2);
        panel.add(open1);
        panel.add(open2);
        panel.add(sure);
        panel.add(result);
        panel.add(text1);
        panel.add(text2);
        add(panel);

        clear1.addActionListener(e -> {
            textField1.setText("");
        });
        clear2.addActionListener(e -> {
            textField2.setText("");
        });
        sure.addActionListener(e -> {
            String url1 = textField1.getText();
            String url2 = textField2.getText();
            double rtn = 0;
            try {
                rtn = CodeFile.CompareTwo(url1, url2, 0) * 100;
            } catch (FileNotFoundException ex) {
                result.setText("检查路径！");
                throw new RuntimeException(ex);
            }
            DecimalFormat df = new DecimalFormat("#.0");
            String str = String.valueOf(df.format(rtn));
            result.setText(str + "%");
        });
        open1.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("选择要查重的文件");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            int userSelection = fileChooser.showOpenDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File selectedFolder = fileChooser.getSelectedFile();
                textField1.setText(selectedFolder.getAbsoluteFile().toString());
            }
        });
        open2.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("选择要查重的文件");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            int userSelection = fileChooser.showOpenDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File selectedFolder = fileChooser.getSelectedFile();
                textField2.setText(selectedFolder.getAbsoluteFile().toString());
            }
        });
        setVisible(true);
    }
}
class max_Similarity extends JFrame{
    public max_Similarity(){
        setTitle("最相似文件输出");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JTextField textField = new JTextField();
        JButton clear = new JButton("清空");
        JButton open = new JButton("*");
        JLabel text=new JLabel("文件");
        JButton sure = new JButton("检查");
        JLabel result = new JLabel("这里暂时是空的哦，输入文件路径然后检查就会出现结果了~");

        textField.setBounds(140, 40, 300, 25);
        open.setBounds(445, 40, 20, 20);
        clear.setBounds(250, 70, 100, 20);
        text.setBounds(140,20,50,20);
        sure.setBounds(200, 120, 200, 50);
        result.setBounds(40, 200, 520, 40);


        panel.add(textField);
        panel.add(clear);
        panel.add(open);
        panel.add(text);
        panel.add(result);
        panel.add(sure);
        add(panel);

        clear.addActionListener(e -> {
            textField.setText("");
        });
        open.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("选择要查重的文件");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            int userSelection = fileChooser.showOpenDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File selectedFolder = fileChooser.getSelectedFile();
                String str=textField.getText();
                textField.setText(str+ ","+selectedFolder.getAbsolutePath());
            }
        });
        sure.addActionListener(e -> {
            String url = textField.getText();
            String[] rtn;
            try {
                rtn = ClosestCodeMatch.CloseMatch(url);
            } catch (FileNotFoundException ex) {
                result.setText("检查路径！");
                throw new RuntimeException(ex);
            }
            result.setText("<html>" + rtn[0] + "<br>" + rtn[1] + "</html>");
        });

        setVisible(true);
    }
}
class all_SImilarity extends JFrame{
    public all_SImilarity(){
        setTitle("全部文件输出");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JTextField textField = new JTextField();
        JButton clear = new JButton("清空");
        JButton open = new JButton("*");
        JLabel text=new JLabel("文件1");
        JButton sure = new JButton("检查");
        JTextArea textArea = new JTextArea("这里暂时是空的哦，输入文件路径然后检查就会出现结果了~\n");

        textField.setBounds(440, 40, 300, 25);
        open.setBounds(745, 40, 20, 20);
        clear.setBounds(550, 70, 100, 20);
        text.setBounds(440,20,50,20);
        sure.setBounds(500, 120, 200, 50);
        textArea.setBounds(40, 200, 1120, 350);


        panel.add(textField);
        panel.add(clear);
        panel.add(open);
        panel.add(text);
        panel.add(textArea);
        panel.add(sure);
        add(panel);

        clear.addActionListener(e -> {
            textField.setText("");
        });
        open.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("选择要查重的文件");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            int userSelection = fileChooser.showOpenDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File selectedFolder = fileChooser.getSelectedFile();
                String str=textField.getText();
                textField.setText(str+ ","+selectedFolder.getAbsolutePath());
            }
        });
        sure.addActionListener(e -> {
            String url = textField.getText();
            Map<String,String> rtn;
            try {
                rtn = ClosestCodeMatches.Matche(url);
            } catch (FileNotFoundException ex) {
                textArea.setText("检查路径！");
                throw new RuntimeException(ex);
            }
            for(String str:rtn.keySet()){
                textArea.append(str+" "+"\n"+rtn.get(str)+"\n"+"\n");
            }
        });

        setVisible(true);

    }
}
class in_dir_sim extends  JFrame {
    public in_dir_sim() {
        setTitle("子目录相似度");
        setSize(1200, 900);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JButton clear1 = new JButton("清空");
        JButton clear2 = new JButton("清空");
        JTextField textField1 = new JTextField();
        JTextField textField2 = new JTextField();
        JTextField textFieldx = new JTextField("0");
        JButton open1 = new JButton("*");
        JButton open2 = new JButton("*");
        JButton sure = new JButton("计算子目录相似度");
        JLabel text1 = new JLabel("目标目录");
        JLabel textx = new JLabel("最小相似度");
        JLabel text2 = new JLabel("附加匹配条件");
        JTextArea textArea = new JTextArea("这里暂时是空的哦，输入文件路径然后检查就会出现结果了~\n");
        JCheckBox removeComments = new JCheckBox("removeComments");

        textArea.setBounds(30, 200, 1120, 650);
        clear1.setBounds(550, 70, 100, 20);
        clear2.setBounds(550, 150, 100, 20);
        textField1.setBounds(440, 40, 300, 25);
        open1.setBounds(745, 40, 20, 20);
        textField2.setBounds(440, 120, 300, 25);
        open2.setBounds(745, 120, 20, 20);
        sure.setBounds(900, 90, 200, 50);
        text1.setBounds(440, 20, 50, 20);
        text2.setBounds(440, 100, 100, 20);
        textx.setBounds(910,60,65,20);
        removeComments.setBounds(900,30,140,20);
        textFieldx.setBounds(975,60,50,20);


        panel.add(clear1);
        panel.add(clear2);
        panel.add(textField1);
        panel.add(textField2);
        panel.add(open1);
        panel.add(open2);
        panel.add(sure);
        panel.add(text1);
        panel.add(text2);
        panel.add(textx);
        panel.add(textArea);
        panel.add(removeComments);
        panel.add(textFieldx);
        add(panel);

        clear1.addActionListener(e -> {
            textField1.setText("");
        });
        clear2.addActionListener(e -> {
            textField2.setText("");
        });
        sure.addActionListener(e -> {
            String url1 = textField1.getText();
            String url2 = textField2.getText();
            double topRate= Double.parseDouble(textFieldx.getText());
            boolean removeComment=removeComments.isSelected();
            Map<String[],Double> rtn;
            try {
                rtn = LabClosestMatches.closestCodes(url1,url2,topRate,removeComment);
            } catch (FileNotFoundException ex) {
                textArea.setText("检查路径是否合法");
                throw new RuntimeException(ex);
            }
            List<Map.Entry<String[],Double>> list = new ArrayList<>(rtn.entrySet());
            list.sort(Map.Entry.<String[], Double>comparingByValue().reversed());
            LinkedHashMap<String[],Double> sortedMap = new LinkedHashMap<>();
            for (Map.Entry<String[],Double> entry : list) {
                sortedMap.put(entry.getKey(), entry.getValue());
            }
            DecimalFormat df = new DecimalFormat("#.0");
            for(String[] str:sortedMap.keySet()){
                textArea.append(str[0]+"\t"+str[1]+"\t"+df.format(rtn.get(str)*100)+"%"+"\n");
            }
        });
        open1.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("选择要查重的文件");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            int userSelection = fileChooser.showOpenDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File selectedFolder = fileChooser.getSelectedFile();
                textField1.setText(selectedFolder.getAbsoluteFile().toString());
            }
        });
        open2.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("选择要查重的文件");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            int userSelection = fileChooser.showOpenDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File selectedFolder = fileChooser.getSelectedFile();
                textField2.setText(selectedFolder.getAbsoluteFile().toString());
            }
        });
        setVisible(true);
    }

}