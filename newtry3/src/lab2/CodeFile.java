package lab2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import static java.lang.Math.sqrt;

public class CodeFile {
    public static Map<String, int[]> ReadFile(String path1, String path2) throws FileNotFoundException {
        Map<String, int[]> vectorMap = new HashMap<String, int[]>();
        Scanner sc1 = new Scanner(new File(path1));
        while(sc1.hasNext()){
            String str1=sc1.next();
            if(isCharacter(str1)){
                if(vectorMap.containsKey(str1)) vectorMap.get(str1)[0]++;//图中有，数量加1
                else{//图中没有，新建一个
                    int []temp=new int[2];
                    temp[0]=1;
                    temp[1]=0;
                    vectorMap.put(str1,temp);
                }
            }
        }
        Scanner sc2 = new Scanner(new File(path2));
        while(sc2.hasNext()){
            String str2=sc2.next();
            if(isCharacter(str2)){
                if(vectorMap.containsKey(str2)) vectorMap.get(str2)[1]++;//图中有，数量加1
                else{//图中没有，新建一个
                    int []temp=new int[2];
                    temp[0]=0;
                    temp[1]=1;
                    vectorMap.put(str2,temp);
                }
            }
        }
        return vectorMap;
    }
    public static Map<String, int[]> ReadFile(String path1, String path2,int sign) throws FileNotFoundException {
        Map<String, int[]> vectorMap = new HashMap<String, int[]>();
        Scanner sc1 = new Scanner(path1);
        Scanner sc2 = new Scanner(path2);
        while(sc1.hasNext()){
            String str1=sc1.next();
            if(isCharacter(str1)){
                if(vectorMap.containsKey(str1)) vectorMap.get(str1)[0]++;//图中有，数量加1
                else{//图中没有，新建一个
                    int []temp=new int[2];
                    temp[0]=1;
                    temp[1]=0;
                    vectorMap.put(str1,temp);
                }
            }
        }
        while(sc2.hasNext()){
            String str2=sc2.next();
            if(isCharacter(str2)){
                if(vectorMap.containsKey(str2)) vectorMap.get(str2)[1]++;//图中有，数量加1
                else{//图中没有，新建一个
                    int []temp=new int[2];
                    temp[0]=0;
                    temp[1]=1;
                    vectorMap.put(str2,temp);
                }
            }
        }
        return vectorMap;
    }
    public static double CompareTwo(String path1, String path2,int a) throws FileNotFoundException {
        Map<String, int[]> map;
        if(a==0) {
            map = ReadFile(path1, path2);
        } else {
            map = ReadFile(path1, path2,1);
        }
        double fenzi = 0;
        double fenmu = 0;
        double Denominator1 = 0;
        double Denominator2 = 0;
        Set<String> keySet=map.keySet();
        for(String str:keySet){
            int []temp=map.get(str);
            fenzi+=temp[0]*temp[1];
            Denominator1+=temp[0]*temp[0];
            Denominator2+=temp[1]*temp[1];
        }
        fenmu=sqrt(Denominator1*Denominator2);
        return fenzi/fenmu;
    }
    public static boolean isCharacter(String str){//这里我们引入一个isCharacter功能使用正则表达式来判断是否是单词
        if (null == str || str.trim().equals("")) {
            return false;
        }
        String regex = "^[a-zA-Z]+$";
        return str.matches(regex);
    }
};
