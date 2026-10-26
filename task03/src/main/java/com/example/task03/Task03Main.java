package com.example.task03;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

public class Task03Main {

    private static final String DICTIONARY = "/test.txt";

    private static final Charset DICTIONARY_CHARSET = Charset.forName("windows-1251");

    public static void main(String[] args) throws IOException {

        try (InputStream dictionary = Task03Main.class.getResourceAsStream(DICTIONARY)) {
            List<Set<String>> anagrams = findAnagrams(dictionary, DICTIONARY_CHARSET);
            for (Set<String> anagram : anagrams) {
                System.out.println(anagram);
            }
        }

    }

    public static List<Set<String>> findAnagrams(InputStream inputStream, Charset charset) {

        List<Set<String>> finalList = new ArrayList<>();

        String line;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charset))) {

            while ((line = reader.readLine()) != null) {

                line = line.toLowerCase();

                if (line.length() < 3 || line.matches(".*[a-z].*") || line.contains("-")) {
                    continue;
                }

                boolean found = false;
                for (Set<String> set : finalList) {
                    if (isAnagram(line, set.iterator().next())) {
                        set.add(line);
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    Set<String> newSet = new TreeSet<>();
                    newSet.add(line);
                    finalList.add(newSet);
                }
            }
        } catch (IOException e){
            throw new UncheckedIOException(e);
        }
        finalList.removeIf(n -> n.size() < 2);

        finalList.sort(Comparator.comparing(set -> set.stream().findFirst().orElse("")));

        return finalList;
    }

    public static boolean isAnagram(String a, String b){

        if (a.length() != b.length()) return false;

        char[] x = a.toCharArray();
        char[] y = b.toCharArray();
        Arrays.sort(x);
        Arrays.sort(y);
        return Arrays.equals(x, y);
    }
}