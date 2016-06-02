package com.asakalou.twitch.core.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

public abstract class RetryTask<T> implements Callable<T> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private int attempts = 10;
    private int delay = 10 * 1000; // 10 seconds
    private boolean isCompleted;

    private int currentAttempt;

    public T call() throws Exception {
        if (isCompleted) {
            throw new IllegalStateException("Task has been already completed");
        }
        logger.debug("Task started");

        do {
            logger.debug("Attempt " + currentAttempt);
            try {
                T result = execute();
                isCompleted = true;
                return result;
            } catch (Exception e) {
                ++currentAttempt;
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e1) {
                    throw new IllegalStateException("Task has been interrupted");
                }
            }

        } while (!isCompleted && (currentAttempt < attempts));

        throw new IllegalStateException("Task has been completed but didn't return any result");
    }


    protected abstract T execute() throws Exception;


}
