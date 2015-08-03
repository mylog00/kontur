package ru.kontur;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author Dmitry
 * @since 03.08.2015
 */
public class TWordSearcher {
    @Test
    public void testBase() {
        fileTest("input/test_base.in");
    }

//    @Test
    public void testMain() {
        fileTest("input/test.in");
    }

    private void fileTest(String resPath) {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(resPath).getFile());

        Scanner in;
        try {
            in = new Scanner(file);

            int wordNumber = in.nextInt();//количество слов в найденных текстах
//            System.err.println(wordNumber);
            List<String> wordsList = new ArrayList<>(wordNumber);//Список всех солов
            Map<String, Integer> wordsFrequency = new HashMap<>(wordNumber);//Частота повторения слова
            while (wordNumber > 0) {
                String word = in.next();
                Integer frequency = in.nextInt();
                wordsList.add(word);
                wordsFrequency.put(word, frequency);
                wordNumber--;
//                System.err.println(word + " " + frequency);
            }

            WordSearcher searcher = new WordSearcher(wordsList, wordsFrequency);
            wordNumber = in.nextInt();//количество слов для поиска
//            System.err.println(wordNumber);
            List<String> searchStringList = new ArrayList<>(wordNumber);
            while (wordNumber > 0) {
                searchStringList.add(in.next());
                wordNumber--;
            }

            searchStringList.forEach(s -> {
//                System.err.println(s + ":");
                searcher.getMostFrequentlyUsedWords(s).forEach(System.out::println);
                System.out.println();
            });
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
