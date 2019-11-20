package com.practice.jwt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
 
public class Java8WordFrequencyCount {
 
    private static String fileName;
	public static void main(String[] args) throws IOException {
    	String fileName = "d://lines.txt";
        
        List <String> list = fileReader(fileName).stream().map(w -> w.split("\\s+")).flatMap(Arrays::stream) .collect(Collectors.toList());
        
 
        Map <String, Integer > wordCounter = list.stream()
            .collect(Collectors.toMap(w -> w.toLowerCase(), w -> 1, Integer::sum));
 
        //System.out.println(wordCounter);
        
        List<Map.Entry<String, Integer>> sorted = wordCounter.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toList());
 
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        sorted.forEach(e -> sortedMap.put(e.getKey(), e.getValue()));
        //System.out.println(sorted);
        fileWriter(sortedMap, String.join(" ", list));
 
    }
    
    public static List<String> fileReader(String filePath) {
    	
		List<String> list = new ArrayList<>();
		fileName = Paths.get(filePath).getFileName().toString();

		try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
			list = br.lines().collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list; 
    }
    
 public static void fileWriter(Map<String, Integer> wordCount,String fileData) throws IOException {
	StringBuilder html = new StringBuilder("<html><head><style>table {  width:100%;}"
			+ "table, th, td {  border: 1px solid black;  border-collapse: collapse;}"
			+ "th, td {  padding: 15px;  text-align: left;}"
			+ "table th {  background-color: gray;  color: black;}"
			+ "</style></head><body><h2>Input File Name: "+fileName);
	html.append("<br>");
	html.append("<table><tr><th>File Contains:</th></tr><tr><td>"+fileData+"</td></tr></table>");
	html.append("<br>");
	html.append("<table><tr><th>Word</th><th> Occurances</th></tr>");
	wordCount.forEach((key,value)-> html.append("<tr><td>"+key+"</td><td>"+value+"</td></tr>"));
	html.append("</table>");
	
	Path path = Paths.get("d:/output.html");
	 
	//Use try-with-resource to get auto-closeable writer instance
	try (BufferedWriter writer = Files.newBufferedWriter(path))
	{
	    writer.write(html.toString());
	}
	
	
    }
}
