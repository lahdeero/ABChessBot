package abcb.datastructure;

/**
 * Its like poor man's java.util.ArrayList
 * @author Eero
 * @param <E> 
 */
public class MyRecord<E> {

    private E[] arr;
    private int size;

    public MyRecord() {
        this.arr = (E[]) new Object[8];
        this.size = 0;
    }

    /**
     * Adds element end of MyRecord.
     *
     * @param obj
     */
    public void add(E element) {
        arr[size++] = element;
        if (size == arr.length) {
            grow(2);
        }
    }

    public void addAll(MyRecord mr) {
        if (mr.size() + size >= arr.length) {
            int multiplier = 1;
            while (mr.size() + size >= arr.length * multiplier) {
                multiplier += 1;
            }
            grow(multiplier);
        }

        for (int i = 0; i < mr.size(); i++) {
            arr[size++] = (E) mr.get(i);
        }
    }

    private void grow(int multiplier) {
        E[] arr2 = (E[]) new Object[arr.length * multiplier];
        java.lang.System.arraycopy(arr, 0, arr2, 0, arr.length);
        this.arr = arr2;
    }

    public E get(int index) {
        if (index > size) {
            System.out.println(this.toString() + ": index out of bounds!");
            return null;
        }
        return (E) arr[index];
    }
    
    public boolean swap(int first, int second) {
        if ((first >= 0 && first < size) && (second >= 0 && second < size)) {
            E temp = arr[first];
            arr[first] = arr[second];
            arr[second] = temp;
            return true;
        }
        return false;
    }

    public int size() {
        return this.size;
    }
}
