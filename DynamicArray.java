public class DynamicArray<T> { //cjay
    private Object[] array;
    private int size;
    private static final int INITIAL_CAPACITY = 10;

    public DynamicArray() {
        array = new Object[INITIAL_CAPACITY];  
        size = 0;
    }// cjay

    public void add(T element) { //mem2
        ensureCapacity();
        array[size++] = element;
    }//mem2

    public void insert(int index, T element) {//mem3
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        ensureCapacity();
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = element;
        size++; 
    } //mem3

    public void remove(int index) {  //mem5
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        System.arraycopy(array, index + 1, array, index, size - index - 1);
        array[--size] = null; // Clear the reference 
    } //mem5

    public T get(int index) { //mem4
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return (T) array[index];
    } //mem4

    public int size() { //mem2
        return size;
    } //mem2

    public boolean isEmpty() { //mem2
        return size == 0;
    } //mem2

    private void ensureCapacity() { //cj
        if (size == array.length) {
            int newCapacity = array.length * 2;
            Object[] newArray = new Object[newCapacity];
            System.arraycopy(array, 0, newArray, 0, size);
            array = newArray;
        }
    }
} //cj

