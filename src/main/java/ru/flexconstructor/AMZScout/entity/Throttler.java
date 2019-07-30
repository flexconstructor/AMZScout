package ru.flexconstructor.AMZScout.entity;

import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Checks throttling.
 */
@RequiredArgsConstructor
public class Throttler {

    /**
     * Limit of calls.
     */
    private final int limit;

    /**
     * {@link Duration} of timeout.
     */
    private final Duration timeUnit;

    /**
     * List of calls.
     */
    private List<Long> calls = new ArrayList<>();

    /**
     * Mutex.
     */
    private ReadWriteLock lock = new ReentrantReadWriteLock(true);

    /**
     * Checks new calling. If count of current call greater then limit it returns false.
     * Add new calling to list otherwise.
     *
     * @return  check result.
     */
    public boolean throttle() {
        this.removeEldest();
        this.lock.readLock().lock();
        try {
            if (this.calls.size() > limit) {
                return false;
            }
        } finally {
            this.lock.readLock().unlock();
        }
        lock.writeLock().lock();

        try {
            if (calls.size() < limit) {
                calls.add(System.currentTimeMillis());
                return true;
            } else {
                return false;
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Removes all eldest calls form calls list.
     */
    public void removeEldest() {
        long threshold = System.currentTimeMillis() - timeUnit.toMillis();
        this.lock.writeLock().lock();
        try {
            calls.removeIf(it -> it < threshold);

        } finally {
            this.lock.writeLock().unlock();
        }
    }

    /**
     * Returns true if the calls list is empty.
     *
     * @return calls list is empty.
     */
    public boolean isEmpty() {
        lock.writeLock().lock();
        try {
            return calls.size() == 0;
        } finally {
            lock.writeLock().unlock();
        }
    }
}
