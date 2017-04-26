package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.iterator;

import java.util.Iterator;

/**
 *
 * @author Giovani Guizzo
 */
public class CountingIterator<T> implements Iterator<T> {

    private Iterator<T> iterator;
    private int count;

    public CountingIterator(Iterator<T> iterator) {
        this.iterator = iterator;
        this.count = 0;
    }

    public int getCount() {
        return count;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public T next() {
        T next = iterator.next();
        count++;
        return next;
    }
}
