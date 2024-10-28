package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");

        // Получить вопросы из дао и вывести их с вариантами ответов
        List<Question> questions = questionDao.findAll();
        for (Question question : questions) {
            printQuestion(question);
            ioService.printLine("");
        }
    }

    private void printQuestion(Question question) {
        ioService.printLine("Question:");
        ioService.printLine(question.text());
        int i = 1;
        for (Answer answer : question.answers()) {
            ioService.printFormattedLine("Answer %s: %s", i++, answer.isCorrect() ? "<Correct>" : "");
            ioService.printLine(answer.text());
        }
    }
}
