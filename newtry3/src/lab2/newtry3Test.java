package lab2;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.*;

public class newtry3Test {
    @Test
    public void CodeFileTest() throws FileNotFoundException {
        DecimalFormat df = new DecimalFormat("#.0");
        Assert.assertEquals("44.4",df.format(CodeFile.CompareTwo("D:\\MyCode\\Java\\newtry3\\src\\test1","D:\\MyCode\\Java\\newtry3\\src\\test2",0)*100));
    }

    @Test
    public void CloestCodeMatchTest() throws FileNotFoundException {
        String[] test={"sample1.code","sample2.code"};
        Assert.assertEquals(test,ClosestCodeMatch.CloseMatch("D:\\MyCode\\Java\\newtry3\\src\\sample1.code,D:\\MyCode\\Java\\newtry3\\src\\sample2.code,D:\\MyCode\\Java\\newtry3\\src\\test1,D:\\MyCode\\Java\\newtry3\\src\\test2"));
    }

    @Test
    public void CloestCodeMatchesTest() throws FileNotFoundException {
        Map<String,String> test=new HashMap<>();
        test.put("D:\\MyCode\\Java\\newtry3\\src\\test1","D:\\MyCode\\Java\\newtry3\\src\\test2");
        test.put("D:\\MyCode\\Java\\newtry3\\src\\test2","D:\\MyCode\\Java\\newtry3\\src\\test1");
        test.put("D:\\MyCode\\Java\\newtry3\\src\\sample1.code","D:\\MyCode\\Java\\newtry3\\src\\sample2.code");
        test.put("D:\\MyCode\\Java\\newtry3\\src\\sample2.code","D:\\MyCode\\Java\\newtry3\\src\\sample1.code");
        Assert.assertEquals(test,ClosestCodeMatches.Matche("D:\\MyCode\\Java\\newtry3\\src\\test1,D:\\MyCode\\Java\\newtry3\\src\\test2,D:\\MyCode\\Java\\newtry3\\src\\sample1.code,D:\\MyCode\\Java\\newtry3\\src\\sample2.code"));
    }

    @Test
    public void LabCloestCodeMatchesTest() throws FileNotFoundException {
        Map<String[],Double> test=new HashMap<>();
        Map<String[],Double> newmap=LabClosestMatches.closestCodes("D:\\MyCode\\Java\\newtry3\\lab1-codes-fortest","*.java",0.9,true);

        List<Map.Entry<String[],Double>> list = new ArrayList<>(newmap.entrySet());
        list.sort(Map.Entry.<String[], Double>comparingByValue().reversed());
        LinkedHashMap<String[],Double> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String[],Double> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        for(String[] str:sortedMap.keySet()) System.out.println(sortedMap.get(str)+"	"+"\t"+str[0]+"\t"+str[1]);
        Assert.assertFalse(newmap.isEmpty());
    }

    public static  boolean isEqual(Map<String[], Double> map1, Map<String[], Double> map2) {
        // 如果两个Map对象的大小不同，则它们不相等
        if (map1.size() != map2.size()) {
            return false;
        }

        // 遍历map1中的每个键值对，逐个判断其是否在map2中也存在，并且值是否相同
        for (String[] key: map1.keySet()) {
            Double value1 = map1.get(key);
            Double value2 = map2.get(key);
            if(value1!=value2){
                return false;
            }
        }

        // 所有键值对的值都相等，则两个Map对象相等
        return true;
    }
}