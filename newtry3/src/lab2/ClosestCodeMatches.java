package lab2;
import java.io.FileNotFoundException;
import java.util.*;

public class ClosestCodeMatches {
    public static Map<String,String> Matche(String paths) throws FileNotFoundException {
        CodeFile file=new CodeFile();
        Scanner sc1 = new Scanner(paths);
        sc1.useDelimiter(",");
        ArrayList<String> path=new ArrayList<String>();

        while(sc1.hasNext()){
            String str=sc1.next();//获得路径
            path.add(str);
        }
        int n=path.size();

        Map<String,String>mapp=new HashMap<>();
        for(int i=0;i<path.size();i++){
            Map<Double, String[]> maps=new HashMap<>();
            for(int j=0;j<path.size();j++) {
                if(path.get(i)==path.get(j))
                    continue;
                double rate=file.CompareTwo(path.get(i),path.get(j),0);
                String[] newstr={path.get(i),path.get(j)};
                maps.put(rate,newstr);
            }
            double target=0;//目标键
            for(double boot:maps.keySet()){
                target=Math.max(target,boot);
            }
            mapp.put(path.get(i), maps.get(target)[1]);
        }
        return mapp;
    }
}