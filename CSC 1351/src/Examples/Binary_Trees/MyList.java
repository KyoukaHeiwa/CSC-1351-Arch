package Examples.Binary_Trees;

public class MyList<T> {
    int logical_size = 0;
    Object[] data = new Object[10];
    void grow(){
        if (logical_size >= data.length){
            Object[] temp = new Object[logical_size * 2];
            for (int i = 0; i < logical_size; i++){
                temp[i] = data[i];
            }
            data = temp;
        }
    }
    public void add(T t){
        data[logical_size++] = t;
    }
    @SuppressWarnings("unchecked")
    public T get(int i){
        return (T)data[i]; // can't do this because of type erasure
    }
    public void set(int i, T t){
        data[i] = t; //no cast because of the generic type
    }
}
