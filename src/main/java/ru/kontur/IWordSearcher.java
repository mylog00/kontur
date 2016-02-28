package ru.kontur;

import java.util.List;

/**
 * @author Dmitry
 * @since 28.02.2016
 */
public interface IWordSearcher {
    //Максимальная длинна списка
    int MAX_ANSWER_NUMBER = 10;

    /**
     * <p>
     * Метод возвращающий наиболее часто употребляемые слова начинающихся с {@code searchWord}
     * в порядке убывания частоты. В случае совпадения частот слова сортируются по алфавиту.
     * Длинна выходного списка не превышает 10 слов.
     * </p>
     *
     * @param searchWord строка с которой должны начинаться найденные слова
     * @return список наиболее часто употребляемых слов начинающихся с {@code searchWord}.<br/>
     * Длинна списка не может быть больше 10.
     */
    List<String> getMostFrequentlyUsedWords(String searchWord);
}
