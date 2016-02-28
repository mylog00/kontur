package ru.kontur;

import ru.kontur.prefixtree.PrefixTree;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Dmitry
 * @since 28.02.2016
 */
public class WordSearcherPrefixTree implements IWordSearcher {

    private final PrefixTree<StringWrapper> prefixTree = new PrefixTree<>();
    //Кэш. Хранит результаты поисковых запросов
    private final Map<String, List<String>> resultCache;

    public WordSearcherPrefixTree() {
        this.resultCache = new HashMap<>();
    }

    /**
     * Создает экземпляр объекта для поиска поиска наиболее часто употребляемых слов
     *
     * @param word      слово
     * @param frequency частота повторения слова
     */
    @Override
    public void add(String word, Integer frequency) {
        prefixTree.put(word, new StringWrapper(word, frequency));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getMostFrequentlyUsedWords(String searchWord) {
        //Поиск результата в кэше
        if (this.resultCache.containsKey(searchWord)) {
            //Если для этого слова уже есть результат, возвращаем его
            return this.resultCache.get(searchWord);
        }

        List<String> result = prefixTree.find(searchWord)
                .stream()
                .limit(MAX_ANSWER_NUMBER)
                .map(StringWrapper::getWord)
                .collect(Collectors.toList());
        //добавляем результаты поиска для строки в кэш
        this.resultCache.put(searchWord, result);
        //возвращаем результат
        return result;
    }

    private static class StringWrapper implements Comparator<StringWrapper>, Comparable<StringWrapper> {
        private final String word;
        private final int frequency;

        public StringWrapper(String word, int frequency) {
            if (word == null)
                throw new IllegalArgumentException("Argument 'word' must be not null");
            if (frequency <= 0)
                throw new IllegalArgumentException("Argument 'frequency' must be great then 0");
            this.word = word;
            this.frequency = frequency;
        }

        public String getWord() {
            return word;
        }

        public int getFrequency() {
            return frequency;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            StringWrapper that = (StringWrapper) o;

            return frequency == that.frequency && word.equals(that.word);

        }

        @Override
        public int hashCode() {
            int result = word.hashCode();
            result = 31 * result + frequency;
            return result;
        }

        @Override
        public int compare(StringWrapper o1, StringWrapper o2) {
            final int cmp = Integer.compare(o2.getFrequency(), o1.getFrequency());
            if (cmp == 0) {
                return o1.getWord().compareTo(o2.getWord());
            }
            return cmp;
        }

        @Override
        public int compareTo(StringWrapper o) {
            return compare(this, o);
        }
    }

}
