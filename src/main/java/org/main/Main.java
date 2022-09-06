package org.main;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        try (Scanner myReader = new Scanner(new File(args[0]));
             FileWriter myWriter = new FileWriter("output.txt")) {
            boolean foundPair;
            List<Long> previous = new ArrayList<>();
            List<List<Long>> temp = new ArrayList<>();
            List<List<List<Long>>> result = new ArrayList<>();
            while (myReader.hasNextLine()){
                foundPair = false;
                String[] dataRow = myReader.nextLine().split(";");
                List<Long> list = new ArrayList<>();
                try{
                    list = Arrays.stream(dataRow).map(x -> x.substring(1, x.length()-1))
                            .map(y -> {
                                if(y.equals("")){
                                    y = "0";
                                }
                                return y;
                            })
                            .map(Long::parseLong)
                            .collect(Collectors.toList());
                }catch (NumberFormatException ignored){}
                if(list.isEmpty())continue;
                if(previous.isEmpty()){
                    previous = list;
                    continue;
                }else{
                    int minArrayLength = Math.min(previous.size(), list.size());
                    for(int i = 0; i < minArrayLength; i++){
                        if(previous.get(i).equals(list.get(i)) && !previous.get(i).equals(0L)){
                            temp.add(previous);
                            previous = list;
                            foundPair = true;
                            break;
                        }
                    }
                    if((!foundPair || !myReader.hasNextLine()) && !temp.isEmpty()){
                        temp.add(previous);
                        previous = new ArrayList<>();
                        result.add(temp);
                        temp = new ArrayList<>();
                    }else{
                        previous = list;
                    }
                }
            }
            result.sort((o1, o2) -> Integer.compare(o2.size(), o1.size()));
            myWriter.write("Число групп: " + result.size() + "\n");
            for(int j = 0; j < result.size(); j++){
                myWriter.write("\n" + "Группа " + (j+1) + "\n");
                for(int k = 0; k < result.get(j).size(); k++){
                    myWriter.write("\n");
                    for(int e = 0; e < result.get(j).get(k).size(); e++){
                        if(e == result.get(j).get(k).size() - 1){
                            myWriter.write("\"" + result.get(j).get(k).get(e).toString() + "\"" + "\n");
                        }else{
                            if(result.get(j).get(k).get(e).equals(0L)){
                                myWriter.write("\"\";");
                            }else{
                                myWriter.write("\"" + result.get(j).get(k).get(e).toString() + "\";");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
