package ru.flexconstructor.AMZScout.service;

/**
 * Throttling service.
 */
public interface ThrottlingService {

    /**
     * Returns true if create new call is possible.
     */
    boolean throttle(String key);
}
