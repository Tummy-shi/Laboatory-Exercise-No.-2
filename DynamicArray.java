public class DynamicArray<T> {      //cj 
    private Object[] array;
    private int size;
    private static final int INITIAL_CAPACITY = 10;        

    public DynamicArray() { 
        array = new Object[INITIAL_CAPACITY];            
        size = 0;
    }    
              
    public void add(T element) { //mem2
        ensureCapacity();
        array[size++] = element;
    } //mem2
                         

    public void insert(int index, T element) { // joshua
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        ensureCapacity();
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = element;
        size++; 
    } 

    public void remove(int index) {  //mem3
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        System.arraycopy(array, index + 1, array, index, size - index - 1);
        array[--size] = null; // Clear the reference 
    } //mem3

    public T get(int index) { //mem4
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return (T) array[index];
    } //mem4

    public int size() { //mem2
        return size;
    } //mem2
                         //Jason

    public boolean isEmpty() { //mem4    
        return size == 0;     
    } //mem4    

   
