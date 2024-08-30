public class DynamicArray<T> { //amancio
    private Object[] array;
    private int size;
    private static final int INITIAL_CAPACITY = 10;

    public DynamicArray() {
        array = new Object[INITIAL_CAPACITY];
        size = 0;
    }

    public void add(T element) { //castino
        ensureCapacity();
        array[size++] = element;
    }

    public void insert(int index, T element) { //joshua
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        ensureCapacity();
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = element;
        size++;
    }

  

    public T get(int index) { //manilag
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return (T) array[index];
    }

    public int size() { // manilag
        return size;
    }

    public boolean isEmpty() { //castino
        return size == 0;
    }

    private void ensureCapacity() { //amancio
        if (size == array.length) {
            int newCapacity = array.length * 2;
            Object[] newArray = new Object[newCapacity];
            System.arraycopy(array, 0, newArray, 0, size);
            array = newArray;
        }
    }
}

