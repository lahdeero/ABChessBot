package abcb.datastructure;

public class MyRecord<E> {

    private E[] arr;
    private int size;

    public MyRecord() {
        this.arr = (E[]) new Object[128];
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
            grow();
        }
    }

    public void addAll(MyRecord mr) {
        if (mr.size() + this.size > arr.length) {
            grow();
        }
        for (int i = 0; i < mr.size(); i++) {
            arr[size++] = (E) mr.get(i);
        }
    }

    private void grow() {
        E[] arr2 = (E[]) new Object[arr.length * 2];
        java.lang.System.arraycopy(arr, 0, arr2, 0, arr.length);
        this.arr = arr2;
        size = arr2.length;
    }

    public E get(int index) {
        return (E) arr[index];
    }

    public int size() {
        return this.size;
    }
}
