package ru.kontur;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;

/**
 * @author Dmitry
 * @since 03.08.2015
 */
public class TWordSearcher {
    @Test
    public void testBase() throws FileNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("output/test_base.out").getFile());
        Scanner in;
        in = new Scanner(file);
        List<List<String>> expectedResult = new ArrayList<>();
        while (in.hasNext()) {
            String line = in.nextLine();
            expectedResult.add(Arrays.asList(line.split(" ")));
        }
        in.close();
        fileTest("input/test_base.in", expectedResult);
    }

    @Test
    public void testMain() throws FileNotFoundException {
        fileTest("input/test.in", null);
    }

    private void fileTest(String resPath, List<List<String>> expectedResult) throws FileNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(resPath).getFile());

        Scanner in = new Scanner(file);
        int wordNumber = in.nextInt();//количество слов в найденных текстах
        Map<String, Integer> wordsFrequency = new HashMap<>(wordNumber);//Частота повторения слова
        while (wordNumber > 0) {
            String word = in.next();
            Integer frequency = in.nextInt();
            wordsFrequency.put(word, frequency);
            wordNumber--;
        }

        WordSearcher searcher = new WordSearcher(wordsFrequency);
        wordNumber = in.nextInt();//количество слов для поиска
        List<String> searchStringList = new ArrayList<>(wordNumber);
        while (wordNumber > 0) {
            searchStringList.add(in.next());
            wordNumber--;
        }

        if (expectedResult == null) {
            searchStringList.forEach(s -> {
                searcher.getMostFrequentlyUsedWords(s).forEach(System.out::println);
                System.out.println();
            });
        } else {
            Iterator<List<String>> iterator = expectedResult.iterator();
            for (String word : searchStringList) {
                List<String> actual = searcher.getMostFrequentlyUsedWords(word);
                List<String> expected = iterator.next();
                Assert.assertThat(actual, is(expected));
            }
        }
        in.close();

    }
}
