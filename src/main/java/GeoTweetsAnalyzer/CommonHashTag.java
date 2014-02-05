package GeoTweetsAnalyzer;

/**
 * @author Toby "Jiajun" Li
 */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import au.com.bytecode.opencsv.CSVWriter;

public class CommonHashTag {
    public static void main(String[] args) throws IOException {
        File outFile = new File("output-super_bowl_day.csv");
        Scanner scanner = new Scanner(outFile);
        File f = new File("hashtag_superbowl.csv");
        final FileWriter fileWriter = new FileWriter(f);
        final CSVWriter csvWriter = new CSVWriter(fileWriter, ',');
        String word;
        String[] entries = new String[2];
        Map<String, Integer> countMap = new HashMap<String, Integer>();
        while(scanner.hasNext()){
            word = scanner.next();
            for(String singleWord : word.split("\"")){
             if(!singleWord.startsWith("#"))
                 continue;
             if(countMap.containsKey(singleWord))
                countMap.put(singleWord, countMap.get(singleWord) + 1);
             else
                countMap.put(singleWord, 1);
            }

        }
        for(Map.Entry<String, Integer> e : countMap.entrySet()){
            entries[0] = e.getKey();
            entries[1] = e.getValue().toString();
            System.out.print(e.getKey()+"   ");
            System.out.println(e.getValue());
            csvWriter.writeNext(entries);
            csvWriter.flush();
        }


    }

}
