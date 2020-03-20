import java.util.List;

public class NavigableSetIterator<T> implements java.util.Iterator<T> {
    public int cursor;
    private List<T> array;

    NavigableSetIterator(List array){
        this.array = array;
        this.cursor = 0;
    }

    public boolean hasNext(){
        return array.size() > cursor;
    }

    public T next() {
        try {
            T t = (T)array.get(cursor);
            cursor++;
            return t;
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new java.util.NoSuchElementException();
        }
    }
}