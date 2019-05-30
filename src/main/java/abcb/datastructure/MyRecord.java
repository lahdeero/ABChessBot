package abcb.datastructure;

public class MyRecord {

    Object[] arr;
    private int size;

    public MyRecord() {
        this.arr = new Object[128];
        this.size = 0;
    }

    public void add(Object obj) {
        arr[size++] = obj;
        if (size == arr.length) {
            Object[] arr2 = new Object[arr.length*2];
            java.lang.System.arraycopy(arr, 0, arr2, 0, arr.length);
            this.arr = arr2;
            size = arr2.length;
        }
    }

    public Object get(int index) {
        return arr[index];
    }
    
    public int size() {
        return this.size;
    }
}
