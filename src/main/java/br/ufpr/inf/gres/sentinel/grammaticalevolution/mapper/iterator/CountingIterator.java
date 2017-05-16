package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.iterator;

import java.util.Iterator;

/**
 *
 * @author Giovani Guizzo
 * @param <T>
 */
public class CountingIterator<T> implements Iterator<T> {

    private int count;
    private Iterator<T> iterator;

    /**
     *
     * @param iterator
     */
    public CountingIterator(Iterator<T> iterator) {
        this.iterator = iterator;
        this.count = 0;
    }

    /**
     *
     * @return
     */
    public int getCount() {
        return this.count;
    }

    @Override
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override
    public T next() {
        T next = this.iterator.next();
        this.count++;
        return next;
    }
}
