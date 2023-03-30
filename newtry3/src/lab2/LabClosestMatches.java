/**
 * 
 */
package lab2;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author 20211001234 xxx
 *
 *假设在 codes/lab1/目录下存在以下结构的文件组织：
*├─Java课内实习-202110001234-xxx-实习1
*│  ├─Java课内实习-20211000123-xxx-实习1
*│  │  └─lab1_code
*│  │      ├─rules
*│  │      └─turtle
*│  └─lab1_code
*│      ├─rules
*│      └─turtle
*├─Java课内实习-20211001235-xxx-实习一
*│  └─lab1
*│      └─lab1_code
*│          └─lab1_code
*│              ├─bin
*│              │  ├─rules
*│              │  └─turtle
*│              ├─rules
*│              └─turtle
*├─Java课内实习-20211001236-xxxx-实习一
*│  ├─rules
*│  └─turtle
*└─Java课内实习20211001237-xxxx-实习一
*    └─Java课内实习20211001237-xxx-实习一
*       └─Java课内实习20211001237-xxxx-实习一
*            └─lab1_code
*               ├─123
*                ├─rules
*                │  └─bin
*               └─turtle
*                    └─bin
*
*/
public class LabClosestMatches {
	public static Map<String[],Double> closestCodes(String path, String fileNameMatches,double topRate,boolean removeComments) throws FileNotFoundException {
		if (fileNameMatches.contains("*")) {
			Map<String[], Double> map;
			//处理匹配字符串
			Scanner sc = new Scanner(fileNameMatches);
			sc.useDelimiter("\\\\\\*.");
			List<String> fileNameMatch=new ArrayList<>();
			while(sc.hasNext()) {
				fileNameMatch.add(sc.next());
			}
			String fileNameMatches_later = fileNameMatch.get(0);
			//*.java的特殊情况
			if(Objects.equals(fileNameMatches_later, "*.java")) {
				fileNameMatches="java";
				map = calculate(path, fileNameMatches, topRate, removeComments);
			}
			else{
				//对每个子目录进行搜索，找到匹配项记录并计算相似度
				fileNameMatches = fileNameMatch.get(1);
				map = calculate(path, fileNameMatches, topRate, removeComments, fileNameMatches_later);
			}
			print(map, topRate);
			return map;
		}
		else{
			//对每个子目录进行搜索，找到匹配项记录并计算相似度
			Map<String[],Double> map = calculate(path, fileNameMatches, topRate, removeComments);
			print(map, topRate);
			return map;
		}
	}
	//计算在path在路径下，所有以fileNameMatches_later结尾的目录下的以fileNameMatches结尾的文件
	public static Map<String[],Double> calculate(String path, String fileNameMatches,double topRate,boolean removeComments,String fileNameMatches_later) throws FileNotFoundException {
		//进入目标目录
		File directory = new File(path);
		if (!directory.exists()) {
			System.out.println("目录不存在");
			return null;
		}

		//筛选目标目录子目录
		List<String> in_directory = new ArrayList<String>();
		List<String> in_directory_name = new ArrayList<>();
		File[] files_directory = directory.listFiles();
		for (File file : files_directory) {
			if (file.isDirectory()) {
				// 如果是目录，则将目录路径添加到列表中
				in_directory.add(file.getAbsolutePath());
				Path one = Paths.get(file.toString());
				String str = one.getFileName().toString();
				in_directory_name.add(str);
			}
		}

		//找到其他目录里全部符合的匹配项的目录路径
		List<String> match_in_directory= new ArrayList<>();
		for(int i=0;i<in_directory.size();i++){
			StringBuilder url= new StringBuilder();
			List<String> urls = findDirectories(in_directory.get(i), fileNameMatches_later);
			List<String> match_in_directory_pre = new ArrayList<>(urls);
			for (int j = 0; j < match_in_directory_pre.size(); j++) {
				url.append(findFiles(match_in_directory_pre.get(j), fileNameMatches));
			}
			match_in_directory.add(url.toString());
		}

		//合并所有符合的文件
		List<String> compare = new ArrayList<>();
		for (int i = 0; i < match_in_directory.size(); i++) {
			String strs = mergeFiles(match_in_directory.get(i));
			compare.add(strs);
		}
		if(removeComments){
			for(int i=0;i<compare.size();i++){
				compare.set(i,removeComments(compare.get(i)));
			}
		}

		//计算相似度
		Map<String[],Double> maps = new HashMap<>();
		for (int i = 0; i < compare.size(); i++) {
			for (int j = 0; j < compare.size(); j++) {
				if (compare.get(i) == compare.get(j))
					continue;
				double rate = CodeFile.CompareTwo(compare.get(i), compare.get(j), 1);
				String[] newstr = {in_directory_name.get(i), in_directory_name.get(j)};
				maps.put(newstr, rate);
			}
		}
		return maps;
	}
	//计算在path在路径下，以fileNameMatches结尾的文件
	public static Map<String[],Double> calculate(String path, String fileNameMatches,double topRate,boolean removeComments) throws FileNotFoundException {
		//进入目标目录
		File directory = new File(path);
		if (!directory.exists()) {
			System.out.println("目录不存在");
			return null;
		}

		//筛选目标目录子目录
		List<String> in_directory = new ArrayList<String>();
		List<String> in_directory_name = new ArrayList<>();
		File[] files_directory = directory.listFiles();
		for (File file : files_directory) {
			if (file.isDirectory()) {
				// 如果是目录，则将目录路径添加到列表中
				in_directory.add(file.getAbsolutePath());
				Path one = Paths.get(file.toString());
				String str = one.getFileName().toString();
				in_directory_name.add(str);
			}
		}

		//找到其他目录里全部符合的匹配项的目录路径
		List<String> match_in_directory= new ArrayList<>();
		for(int i=0;i<in_directory.size();i++){
			StringBuilder url= new StringBuilder();
			url.append(findFiles(in_directory.get(i), fileNameMatches));
			match_in_directory.add(url.toString());
		}

		//合并所有符合的文件
		List<String> compare = new ArrayList<>();
		for (int i = 0; i < match_in_directory.size(); i++) {
			String strs = mergeFiles(match_in_directory.get(i));
			compare.add(strs);
		}

		//计算相似度
		Map<String[],Double> maps = new HashMap<>();
		for (int i = 0; i < compare.size(); i++) {
			for (int j = 0; j < compare.size(); j++) {
				if (compare.get(i) == compare.get(j))
					continue;
				double rate = CodeFile.CompareTwo(compare.get(i), compare.get(j), 1);
				String[] newstr = {in_directory_name.get(i),in_directory_name.get(j)};
				maps.put(newstr, rate);
			}
		}
		return maps;
	}
	//找到directoryPath以fileName结尾的文件路径合集
	public static String findFiles (String directoryPath, String fileName) {
		String matchingFiles = "";
		File directory = new File(directoryPath);
		if (directory.isDirectory()) {
			File[] files = directory.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					String matchingSubdirectoryFiles = findFiles(file.getAbsolutePath(), fileName);
					matchingFiles += matchingSubdirectoryFiles + ",";
				} else if (file.getName().endsWith(fileName)) {
					matchingFiles += file.getAbsolutePath() + ",";
				}
			}
		}
		return matchingFiles;
	}
	//找到dirpath下以suffix结尾的目录合集
	public static List<String> findDirectories(String dirPath, String suffix) {
		List<String> result = new ArrayList<>();
		File[] files = new File(dirPath).listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					if (file.getName().endsWith(suffix)) {
						result.add(file.getPath());
					}
					result.addAll(findDirectories(file.getPath(), suffix));
				}
			}
		}
		return result;
	}
	//合并match_in_directory中路径指向的所有文件
	public static String mergeFiles(String match_in_directory) {
		Scanner sc = new Scanner(match_in_directory);
		sc.useDelimiter(",");
		StringBuilder mergedJavaCode = new StringBuilder();
		while (sc.hasNext()) {
			try {
				byte[] byteArray = Files.readAllBytes(Paths.get(sc.next()));
				String javaCode = new String(byteArray);
				mergedJavaCode.append(javaCode);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return mergedJavaCode.toString();
	}
	//排序并打印
	public static void print(Map<String[],Double> map,double topRate){
		//排序
		List<Map.Entry<String[],Double>> list = new ArrayList<>(map.entrySet());
		list.sort(Map.Entry.<String[], Double>comparingByValue().reversed());
		LinkedHashMap<String[],Double> sortedMap = new LinkedHashMap<>();
		for (Map.Entry<String[],Double> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		//输出
		for(String[] str:sortedMap.keySet()){
			if(sortedMap.get(str)>topRate)
				System.out.println(sortedMap.get(str)+"	"+"\t"+str[0]+"\t"+str[1]);
		}
	}
	public static String removeComments(String code) {
		// 正则表达式，匹配所有注释
		String pattern = "(\\/\\/.*$)|(\\/\\*([\\s\\S]*?)\\*\\/|\n)";
		return code.replaceAll(pattern, "");
	}
}

