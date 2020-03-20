import java.util.*;

public class NavigableSet<T> extends AbstractSet<T> implements java.util.NavigableSet<T> {
    private Comparator<T> comparator;
    private ArrayList<T> array;

    /**
     * Constructor that creates a set based of a collection a
     * @param a
     */
    public NavigableSet(Collection<T> a){
        array = (ArrayList) a;
    }

    /**
     * Constructor that creates a set based of a collection AND a comparator
     * @param a
     * @param comparator
     */
    public NavigableSet(Collection<? extends T> a, Comparator comparator){
        this.comparator = comparator;
        NavigableSetIterator iterator = (NavigableSetIterator) a.iterator();
        array = new ArrayList<>();
        while (iterator.hasNext())
            add((T) iterator.next());
    }

    /**
     * Adding elements with order, adding does not change the order in set
     * @param a
     * @return
     */
    public boolean add(T a){
        boolean isThere = false;
        for (int i = 0; i < array.size(); i++) {
            if (comparator.compare(a, array.get(i)) > 0) {
                array.add(i + 1, a);
                return true;
            }
            if (comparator.compare(a, array.get(i)) == 0) {
                return false;
            }
        }
        return false;
    }

    /**
     * returns an element that is closest to given and cannot be bigger or equal
     * @param t
     * @return
     */

    public T lower(T t) {
        T max = null;
        for (int i = 0; i < array.size(); i++){
            if (comparator.compare(array.get(i), t) < 0){
                max = array.get(i);
            }
        }
        return max;
    }

    /**
     * returns an element that is closest to given or equal and cannot be bigger
     * @param t
     * @return
     */
    public T floor(T t) {
        T max = null;
        for (int i = 0; i < array.size(); i++){
            if (comparator.compare(array.get(i), t) <= 0){
                max = array.get(i);
            }
        }
        return max;
    }

    /**
     * returns an element that is closest to given and cannot be smaller
     * @param t
     * @return
     */
    public T ceiling(T t) {
        for (int i = 0; i < array.size(); i++){
            if (comparator.compare(array.get(i), t) >= 0){
                return array.get(i);
            }
        }
        return null;
    }

    /**
     * returns an element that is closest to given and cannot be smaller and equal
     * @param t
     * @return
     */
    public T higher(T t) {
        for (int i = 0; i < array.size(); i++){
            if (comparator.compare(array.get(i), t) > 0){
                return array.get(i);
            }
        }
        return null;
    }

    /**
     * retrieves and deletes the first element of a set
     * @return
     */
    public T pollFirst() {
        T t = array.get(0);
        if(t != null){
            array.remove(0);
        }
        return t;
    }

    /**
     * retrieves and deletes the last element of a set
     * @return
     */
    public T pollLast() {
        T t = array.get(array.size());
        if(t != null){
            array.remove(t);
        }
        return t;
    }

    /**
     * returns iterator for specific set
     * @return
     */
    public Iterator<T> iterator() {
        return new NavigableSetIterator<T>(array);
    }


    /**
     * returns the reverse order of the specific set
     * @return
     */
    public java.util.NavigableSet<T> descendingSet() {
        ArrayList<T> al = new ArrayList<>();
        Iterator<T> it = this.descendingIterator();
        while (it.hasNext()) {
            al.add(it.next());
        }
        NavigableSet<T> ns = new NavigableSet<T>(al, comparator);
        return ns;
    }

    /**
     * returns an iterator of set in descending order. Used in descendingSet()
     * @return
     */
    public Iterator<T> descendingIterator() {
        return null;
    }

    /**
     * returns some part of the specific set in parts, first pair shows start, next pair - finale
     * @param fromElement
     * @param fromInclusive
     * @param toElement
     * @param toInclusive
     * @return
     */
    public java.util.NavigableSet<T> subSet(T fromElement, boolean fromInclusive, T toElement, boolean toInclusive) {
        ArrayList<T> al = new ArrayList<>();
        NavigableSet<T> ns = new NavigableSet<>(al);
        if ((comparator.compare(fromElement,toElement) > 0) || (comparator().compare(fromElement, first()) < 0)
                ||(comparator.compare(toElement, last())) > 0) {
            throw new IllegalArgumentException();
        }
        int from;
        int to;
        if(fromInclusive) {
            from = array.indexOf(fromElement);
        }
        else {
            from = array.indexOf(fromElement) + 1;
        }
        if (toInclusive){
            to = array.indexOf(toElement);
        }
        else {
            to = array.indexOf(toElement) - 1;
        }
        for(int i = from; i < to; i++){
            ns.add(array.get(i));
        }
        return ns;
    }

    /**
     * returns part of the set in which all of the elements are less (or equal if inclusive == true) that the specific element
     * @param toElement
     * @param inclusive
     * @return
     */
    public java.util.NavigableSet<T> headSet(T toElement, boolean inclusive) {
        if(comparator.compare(array.get(0), toElement) > 0) throw new IllegalArgumentException();
        return subSet(array.get(0), true, toElement, inclusive);
    }

    /**
     * returns part of the set in which all of the elements are greater (or equal if inclusive == true) that the specific element
     * @param fromElement
     * @param inclusive
     * @return
     */
    public java.util.NavigableSet<T> tailSet(T fromElement, boolean inclusive) {
        if(comparator.compare(array.get(size() - 1), fromElement) < 0) throw new IllegalArgumentException();
        return subSet(fromElement, inclusive, array.get(size() - 1), true);
    }

    public SortedSet<T> subSet(T fromElement, T toElement) {
        return subSet(fromElement, true, toElement, false);
    }

    /**
     * returns comparator that was used to order elements or, in case comparable is inherited, compares elements
     * @return
     */
    public Comparator<? super T> comparator() {
        return comparator;
    }

    public SortedSet<T> headSet(T toElement) {
        if ((comparator.compare(toElement, first()) < 0) || (comparator.compare(toElement, last()) > 0)){
            throw new IllegalArgumentException();
        }
        return headSet(toElement, false);
    }

    public SortedSet<T> tailSet(T fromElement) {
        if ((comparator.compare(fromElement, first()) < 0) || (comparator.compare(fromElement, last()) > 0)){
            throw new IllegalArgumentException();
        }
        return tailSet(fromElement, true);
    }

    /**
     * returns first element of a set and throws exception if anything
     * @return
     */
    public T first() throws NoSuchElementException{
        if (size() == 0){
            throw new NoSuchElementException();
        }
        else
            return (T) array.get(0);
    }

    /**
     * returns last element of a set and throws an exception if anything
     * @return
     * @throws NoSuchElementException
     */
    public T last() throws NoSuchElementException{
        if (size() == 0){
            throw new NoSuchElementException();
        }
        else
            return (T) array.get(array.size() - 1);
    }

    /**
     * returns size of a set
     * @return
     */
    public int size() {
        return array.size();
    }
}
