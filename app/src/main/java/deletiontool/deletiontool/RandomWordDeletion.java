package deletiontool.deletiontool;/*
 * *
 *  * Created by Husayn on 22/10/2021, 5:04 PM
 *  * Copyright (c) 2021 . All rights reserved.
 *  * Last modified 22/10/2021, 2:29 PM
 *
 */

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomWordDeletion {
    public String inputText;
    public int seekValue;
    public List<String> excluded;
    public int linecount;

    public RandomWordDeletion(String inputText, int seekValue, List<String> excluded, int linecount) {
        this.inputText = inputText;
        this.seekValue = seekValue;
        this.excluded = excluded;
        this.linecount = linecount;
    }

    public final String deleteLinePreserved() {

        final String[] words_all = inputText.trim().split("\\s+");

        List<String> all_words_array = Arrays.asList(words_all);

        final String[] byLines = inputText.trim().split("\\n");

        List<String> byLines_words = Arrays.asList(byLines);

        Log.d("byLinesw", byLines_words.toString());

        List<String> listall = new ArrayList<>();
        List<String> replacedbyLines = new ArrayList<>();
        StringBuffer stringBuffer = new StringBuffer();

        for (int i = 0; i < byLines_words.size(); i++){

            String[] splittedBylines = byLines_words.get(i).trim().split("[ \\t\\\\\f\\r]+");

            listall.addAll(Arrays.asList(splittedBylines));

            List<String> words = Arrays.asList(splittedBylines);

            StringBuffer sb = new StringBuffer();

            ReplaceText(words);

            for (String s : words) {
                sb.append(s);
                sb.append(" ");
            }

            replacedbyLines.add(sb.toString());

            stringBuffer.append("\n"+sb);

        }

        Log.d("words_each_line", listall.toString());

        return stringBuffer.toString();

    }

    public final void ReplaceText(List<String> words){

        int words_array_size = words.size();

        Log.d("words_array_size", String.valueOf(words_array_size));

        float percent = words_array_size * seekValue / 100;

        String val = String.format("%.0f", percent);
        int percentInt = Integer.parseInt(val);

        Log.d("valper", String.valueOf(percentInt));

        List<Integer> listRand = new ArrayList<Integer>();

        for (int z = 0; z < percentInt; ) {
            int rand = new Random().nextInt(words_array_size);
            if (!listRand.contains(rand)) {
                listRand.add(rand);
                z++;
            }
        }

        for (int w = 0; w < listRand.size(); w++) {
            String words_arr = words.get(listRand.get(w)).trim();
            String wordonly = words_arr.replaceAll("[^A-Za-z0-9]", "").trim();
            String replaced = words_arr.replaceAll("[^/(?!,|.)]", "_").trim();

            Log.d("excluded", excluded.toString());

            if (excluded.contains(wordonly)) {
                Log.d("wordOnly", wordonly);
            } else {
                Log.d("replaced", replaced);
                String modified = words_arr.replace(words_arr, replaced);
                words.set(listRand.get(w), modified);
            }
            /*exclude*/


        }
    }

    public final String deleteRandomWord() {
        Log.d("inputTexxt", inputText);
        /*\n*/
        final String[] words = inputText.trim().split("\\s+");

        List<String> words_array = Arrays.asList(words);

        Log.d("wordsArr", words_array.toString());

        int words_array_size = words_array.size();

        Log.d("words_array_size", String.valueOf(words_array_size));

        float percent = words_array_size * seekValue / 100;

        String val = String.format("%.0f", percent);
        int percentInt = Integer.parseInt(val);

        Log.d("percent", String.valueOf(percentInt));
        Log.d("seekvalue", String.valueOf(seekValue));

        List<Integer> listRand = new ArrayList<Integer>();

        for (int i = 0; i < percentInt; ) {
            int rand = new Random().nextInt(words_array_size);
            if (!listRand.contains(rand)) {
                listRand.add(rand);
                i++;
            }
        }
        Log.d("wordlist", words_array.toString());
        for (int i = 0; i < listRand.size(); i++) {
            String words_arr = words_array.get(listRand.get(i)).trim();
            String wordonly = words_arr.replaceAll("[^A-Za-z0-9 ]", "").trim();
            String replaced = words_arr.replaceAll("[^/(?!,|.)]", "_").trim();
            Log.d("excluded", excluded.toString());

            if (excluded.contains(wordonly)) {
                Log.d("wordOnly", wordonly);
            } else {
                Log.d("replaced", replaced);
                String modified = words_arr.replace(words_arr, replaced);
                words_array.set(listRand.get(i), modified);
            }
            /*exclude*/


        }

        StringBuffer sb = new StringBuffer();

        for (String s : words_array) {
            sb.append(s);
            sb.append(" ");
        }

        return sb.toString();
    }

}
