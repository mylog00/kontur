package ru.kontur;

import java.util.*;

/**
 * @author Dmitry
 * @since 02.08.2015
 */
public class WordSearcher {
    private static final int MAX_ANSWER_NUMBER = 10;
    private final List<String> sortedWordsList;//Список всех солов
    private final Map<String, Integer> wordsFrequency;//Частота повторения слова
    private final Map<String, List<String>> cache;

    public WordSearcher(List<String> wordsList, Map<String, Integer> wordsFrequency) {
        this.sortedWordsList = wordsList;
        Collections.sort(this.sortedWordsList);
        this.wordsFrequency = wordsFrequency;
        this.cache = new HashMap<>(wordsList.size() / 2);
    }

    public List<String> getMostFrequentlyUsedWords(String searchWord) {
        if (this.cache.containsKey(searchWord)) {
            return this.cache.get(searchWord);
        }
        final int firstElementPos = findFirstBinarySearch(this.sortedWordsList, searchWord);
        List<String> result = Collections.<String>emptyList();
        if (firstElementPos >= 0) {

            final int lastElementPos = findLastBinarySearch(this.sortedWordsList, searchWord);
            List<String> matchedWords = new ArrayList<>(this.sortedWordsList.subList(firstElementPos, lastElementPos + 1));
            Collections.sort(matchedWords, (s2, s1) -> {
                Integer f1 = this.wordsFrequency.get(s1);
                Integer f2 = this.wordsFrequency.get(s2);
                int res = Integer.compare(f1, f2);
                if (res == 0) {
                    return s1.compareTo(s2);
                }
                return res;
            });
            result = new ArrayList<>(matchedWords.subList(0, Math.min(matchedWords.size(), MAX_ANSWER_NUMBER)));
        }

        this.cache.put(searchWord, result);
        return result;
    }

    private int findFirstBinarySearch(List<String> l, String key) {
        int low = 0;
        int high = l.size() - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            String midVal = l.get(mid);
            int cmp = midVal.substring(0, Math.min(key.length(), midVal.length())).compareTo(key);

            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
            else if (low != mid) //Equal but range is not fully scanned
                high = mid; //Set upper bound to current number and rescan
            else
                return mid; // key found
        }
        return -(low + 1);  // key not found
    }

    private int findLastBinarySearch(List<String> l, String key) {
        int low = 0;
        int high = l.size() - 1;

        while (low <= high) {
            int mid = (low + high + 1) >>> 1;
            String midVal = l.get(mid);
            int cmp = midVal.substring(0, Math.min(key.length(), midVal.length())).compareTo(key);

            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
            else if (high != mid) //Equal but range is not fully scanned
                low = mid; //Set lower bound to current number and rescan
            else
                return mid; // key found
        }
        return -(low + 1);  // key not found
    }
}
