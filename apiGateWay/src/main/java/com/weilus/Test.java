package com.weilus;


import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by liutq on 2018/4/27.
 */
public class Test {

    public static void main(String[] args) {
        String filename = "D:\\workspace\\xsf-app\\branches\\lanzhong_20180409_lwm\\change.log";
        Path path = Paths.get(filename);
        try {
            Map<Long, List<String>> arr = readLine(path,960,2);
            long nextStart = 0L;
            List<String> result = null;
            for (Map.Entry<Long,List<String>> entry:arr.entrySet()) {
                nextStart = entry.getKey();
                result = entry.getValue();
                break;
            }
            for (String str:result) {
                System.out.println(str);
            }
            System.out.println("下一次读取位置:"+nextStart);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Map<Long, List<String>> readLine(Path path, long start, int line_num)throws Exception{
        try (BufferedReader reader = Files.newBufferedReader(path, Charset.forName("UTF-8"))) {
            List<String> result = new ArrayList<>();
            reader.skip(start);
            long length = start;
            for (int num=0;num<line_num;num++) {
                String line = reader.readLine();
                if (line == null)
                    break;
                length =length + line.length() + 2;
                result.add(line);
            }
            return Collections.singletonMap(length,result);
        }
    }

}
