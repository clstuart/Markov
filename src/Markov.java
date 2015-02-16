/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author clstuart
 */
import java.io.*;
import java.util.ArrayList;
import java.util.*;

public class Markov {
    public String file;
    public HashMap<String, TreeMap<String, Integer>> index = new HashMap<>();
    public Markov(String filename) throws Exception {
	file = filename;
        try (BufferedReader br = new BufferedReader(new FileReader("/home/clstuart/NetBeansProjects/Markov/src/input.txt"))) {
            String line = br.readLine();
            while(br.ready()) {
                ArrayList<String> words = new ArrayList<>(Arrays.asList(line.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+")));
                for (int i = 0; i < words.size(); i++) {
                    //if the iterator is on the last word in the Words array, it must remember the word it wants
                    //then grab a new line, regex filter it to get the new words
                    //this should be easy to expand to more words later
                    if (i == words.size() - 1) {
                        String tempWord = words.get(i);
                        line = br.readLine();
                        ArrayList<String> wwords = new ArrayList<>(Arrays.asList(line.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+")));
                        words = wwords;
                        //if the word has never been indexed before
                        if (!index.containsKey(tempWord)) {
                            TreeMap<String, Integer> tempMap = new TreeMap<>();
                            tempMap.put(words.get(0), 1);
                            index.put(tempWord, tempMap);
                        }
                        else {
                            //if its proceeding word has not been indexed before
                            if (!index.get(tempWord).containsKey(words.get(0))) {
                                TreeMap<String, Integer> tempMap = new TreeMap<>(index.get(tempWord));
                                tempMap.put(words.get(0), 1);
                                index.put(tempWord, tempMap);
                            }
                            else {
                                TreeMap<String, Integer> tempMap = new TreeMap<>(index.get(tempWord));
                                tempMap.put(words.get(0), tempMap.get(words.get(0)) + 1);
                                index.put(tempWord, tempMap);
                            }
                        }
                    }
                    else {
                        if (!index.containsKey(words.get(i))) {
                            TreeMap<String, Integer> tempMap = new TreeMap<>();
                            tempMap.put(words.get(i + 1), 1);
                            index.put(words.get(i), tempMap);
                        }
                        else {
                            if (!index.get(words.get(i)).containsKey(words.get(i + 1))) {
                                TreeMap<String, Integer> tempMap = new TreeMap<>(index.get(words.get(i)));
                                tempMap.put(words.get(i + 1), 1);
                                index.put(words.get(i), tempMap);
                            }
                            else {
                                
                                TreeMap<String, Integer> tempMap = new TreeMap<>(index.get(words.get(i)));
                                tempMap.put(words.get(i + 1), tempMap.get(words.get(i + 1)) + 1);
                                index.put(words.get(i), tempMap);
                            }
                        }
                    }
                }
            }
        }
    }

    public void printWordCount() throws Exception {
	String line;
	HashMap<String, Integer> wordMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            while((line = br.readLine()) != null) {
                String[] words = line.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
                for (String word : words) {
                    if (!wordMap.containsKey(word)) {
                        wordMap.put(word, 1);
                    }
                    else {
                        wordMap.put(word, wordMap.get(word) + 1);
                    }
                }
            }
            
            for (Map.Entry<String, Integer> entry : wordMap.entrySet()) {
                String key;
                key = entry.getKey();
                Integer value = entry.getValue();
                System.out.println("key, " + key + " value " + value );
            }
        }
    }

    public void printProceedingWordFrequency(String inWord) throws Exception {	
	int ans = 0;
	if (index.containsKey(inWord)) {
	    for (Object entry : index.get(inWord).keySet()) {
		System.out.println(entry);
		ans = ans + index.get(inWord).get(entry);
		System.out.println(index.get(inWord).get(entry.toString()));
	   }
	}
	System.out.println(ans);
    }

    public String sentenceGenerator() {
	Random rand = new Random();
	Object[] values = index.values().toArray();
	String randomWord = (String) values[rand.nextInt(values.length)];
        return "Not Yet";
    }
    
    public String nextWord(String word) {
       
        return "not yet";
    }
}
