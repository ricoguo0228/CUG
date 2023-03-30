package lab2;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ClosestCodeMatch {
    public static String[] CloseMatch(String paths) throws FileNotFoundException {
        CodeFile file=new CodeFile();
        Scanner sc1 = new Scanner(paths);
        sc1.useDelimiter(",");
        ArrayList<String> path=new ArrayList<String>();

        while(sc1.hasNext()){
            String str=sc1.next();//获得路径
            path.add(str);
        }
        int n=path.size();

        Map<Double, String[]> maps=new HashMap<>();
        for(int i=0;i<path.size();i++){
            for(int j=i+1;j<path.size();j++) {
                double rate=file.CompareTwo(path.get(i),path.get(j),0);
                String[] newstr={new File(path.get(i)).getName(),new File(path.get(j)).getName()};
                maps.put(rate,newstr);
            }
        }

        double target=0;//目标键
        for(double boot:maps.keySet()){
            target=Math.max(target,boot);
        }
        return maps.get(target);
    }
}