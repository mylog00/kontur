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

        Map<String, Integer> wordsFrequency = new HashMap<>(wordNumber);//Частота повторения слова
        while (wordNumber > 0) {
            String word = in.next();
            Integer frequency = in.nextInt();
            wordsFrequency.put(word, frequency);
            wordNumber--;
        }

        wordNumber = in.nextInt();//количество слов для поиска
        List<String> searchedWordsList = new ArrayList<>(wordNumber);//Список слов для поиска
        while (wordNumber > 0) {
            searchedWordsList.add(in.next());
            wordNumber--;
        }
        in.close();

        IWordSearcher wordSearcher = new WordSearcher(wordsFrequency);
        for (String searchedWord : searchedWordsList) {
            wordSearcher.getMostFrequentlyUsedWords(searchedWord).forEach(System.out::println);
            System.out.println();
        }

    }
}
