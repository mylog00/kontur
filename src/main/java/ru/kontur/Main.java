package ru.kontur;

import java.util.*;

/**
 * @author Dmitry
 * @since 01.08.2015
 */
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int wordNumber = in.nextInt();//количество слов в найденных текстах
        List<String> wordsList = new ArrayList<>(wordNumber);//Список всех солов
        Map<String, Integer> wordsFrequency = new HashMap<>(wordNumber);//Частота повторения слова
        while (wordNumber > 0) {
            String word = in.next();
            Integer frequency = in.nextInt();
            wordsList.add(word);
            wordsFrequency.put(word, frequency);
            wordNumber--;
        }

        WordSearcher searcher = new WordSearcher(wordsList, wordsFrequency);
        wordNumber = in.nextInt();//количество слов для поиска
        while (wordNumber > 0) {
            searcher.getMostFrequentlyUsedWords(in.next()).forEach(System.out::println);
            System.out.println();
            wordNumber--;
        }
        in.close();
    }
}
