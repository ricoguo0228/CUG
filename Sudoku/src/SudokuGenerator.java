import java.util.Random;
public class SudokuGenerator {
    private static final int BOARD_SIZE = 9;//默认棋盘大小9*9
    private static final int EMPTY = 0;
    private static final int FULL = 1;
    private static final int EAZY=0,MID=1,CREAZY=2;
    private final int[][] board;//棋盘
    Random random;//随机数

    public SudokuGenerator() {
        board = new int[BOARD_SIZE][BOARD_SIZE];
        random=new Random();
    }
    public void generate() {
        int[] firstRow = generateRandomSequence();
        System.arraycopy(firstRow, 0, board[0], 0, BOARD_SIZE);
        generateHelper(1, 0);
    }
    private boolean generateHelper(int row, int col) {
        // 扫描到最后一个位置，数独生成完毕
        if (row == BOARD_SIZE) {
            return true;
        }

        int nextRow, nextCol;
        if (col == BOARD_SIZE - 1) { // 当前列是最后一列，跳到下一行的第一列
            nextRow = row + 1;
            nextCol = 0;
        } else { // 跳到下一列
            nextRow = row;
            nextCol = col + 1;
        }

        // 当前位置已经有数字，直接跳到下一个位置
        if (board[row][col] == FULL) {
            return generateHelper(nextRow, nextCol);
        }

        // 随机选择一个数字填入当前位置，然后判断是否合法
        for (int num = 1; num <= BOARD_SIZE; num++) {
            if (isValid(row, col, num)) {
                board[row][col] = num;
                if (generateHelper(nextRow, nextCol)) { // 继续扫描下一个位置
                    return true;
                }
                board[row][col] = EMPTY; // 回溯到前一个位置
            }
        }

        return false; // 所有数字都尝试过，回溯到前一个位置
    }
    private int[] generateRandomSequence() {
        //初始化第一行随机数
        int[] sequence = new int[BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            sequence[i] = i + 1;
        }
        for (int i = 0; i < BOARD_SIZE; i++) {
            int j = random.nextInt(BOARD_SIZE);
            int temp = sequence[i];
            sequence[i] = sequence[j];
            sequence[j] = temp;
        }
        return sequence;
    }
    public int[][] Get_Board(){
        int[][] result_board=new int[BOARD_SIZE][];
        for(int i=0;i<BOARD_SIZE;i++)
            result_board[i]=board[i].clone();
        return result_board;
    }
    private boolean isValid(int row, int col, int num) {
        // 检查当前位置所在的行、列和宫是否已经有相同的数字
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[row][i] == num || board[i][col] == num) {
                return false;
            }
        }
        //找到所属宫的左上角位置，开始筛查宫
        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (board[i][j] == num) {
                    return false;
                }
            }
        }
        return true;
    }
    public void Go_Bad(int num){
        if(num==EAZY)
            for(int i=0;i<36;i++)
                board[random.nextInt(BOARD_SIZE)][random.nextInt(BOARD_SIZE)]=EMPTY;

        else if(num==MID)
            for(int i=0;i<46;i++)
                board[random.nextInt(BOARD_SIZE)][random.nextInt(BOARD_SIZE)]=EMPTY;

        else if(num==CREAZY)
            for(int i=0;i<56;i++)
                board[random.nextInt(BOARD_SIZE)][random.nextInt(BOARD_SIZE)]=EMPTY;

    }
    public static void main(String[] args) {
        SudokuGenerator Sudoku=new SudokuGenerator();
        Sudoku.generate();
        Sudoku.Go_Bad(2);
        int[][] result=Sudoku.Get_Board();
        return;
    }
}