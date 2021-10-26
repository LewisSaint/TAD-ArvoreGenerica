package position;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;

public class ElementIterator<E> implements Iterator<E> {
    protected PositionList<E> list;
    protected Position<E> cursor;
    // Cria um elemento iterator
    public ElementIterator(PositionList<E> L) {
        list = L;
        cursor = (list.isEmpty()) ? null : list.first();
    }
    // Retorna se o iterator tem ou não um próximo objeto.
    public boolean hasNext() { return (cursor != null); }
    // Retorna o próximo objeto do iterator.
    public E next() throws NoSuchElementException {
        if (cursor == null) throw new NoSuchElementException("No next element");
        E toReturn = cursor.element();
        cursor = (cursor == list.last()) ? null : list.next(cursor);
        return toReturn;
    }
    // Dispara um {@link UnsupportedOperationException} para todos os casos, porque
    // a remoção não é uma operação suportada por este iterator.
    public void remove() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("remove");
    }
}
