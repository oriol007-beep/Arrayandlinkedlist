package CSCI1933P3;

public class ArrayList<T extends Comparable<T>> implements List<T> {
    private int size;
    T[] arrayList;
    private boolean isSorted;




    public ArrayList(){
        arrayList = (T[]) new Comparable[2];
        size = 0;
        isSorted = true;

    }

    public boolean checkIfSorted() {
        if (size <= 1) {
            return true;
        }

        for (int i = 0; i < size - 1; i++) {
            if (arrayList[i].compareTo(arrayList[i + 1]) > 0) {
                return false;
            }
        }
        return true;
    }





    private void grow(){
        T[] resizedArray = (T[]) new Comparable[arrayList.length * 2];
        for (int index = 0; index < size; index++) {
            resizedArray[index] = arrayList[index];
        }
        arrayList = resizedArray;

    }

    public boolean add(T element) {
        if (element == null) {
            return false;
        }
        if (size == arrayList.length) {
            T[] newArray = (T[]) new Comparable[arrayList.length * 2];
            for (int i = 0; i < size; i++) {
                newArray[i] = arrayList[i];
            }
            arrayList = newArray;
        }
        arrayList[size] = element;
        if (size > 0 && arrayList[size - 1].compareTo(element) > 0) {
            isSorted = false;
        }
        size++;
        return true;
    }


    public boolean add(int index, T element){
        if (index > size) {
            return false;
        }
        if (size == arrayList.length) {
            grow();
        }
        for (int i = size; i > index; i--) {
            arrayList[i] = arrayList[i - 1];
        }
        arrayList[index] = element;
        size++;


        for (int i = 1; i < size; i++) {
            if (arrayList[i].compareTo(arrayList[i - 1]) < 0) {
                isSorted = false;
                break;
            }
        }
        return true;



    }


   public void clear(){
       arrayList = (T[]) new Comparable[2];
       size = 0;
       isSorted = true;

    }

    public T get(int index){
        if (index < 0 || index >= size) {
            return null;
        }
        return arrayList[index];

    }

    public int indexOf(T element){
        if (element == null) {
            return -1;
        }
        for (int i = 0; i < size; i++) {
            int checksIfSame = arrayList[i].compareTo(element);
            if (checksIfSame == 0) {
                return i;
            }
            if (isSorted && checksIfSame > 0) {
                return -1;
            }
        }
        return -1;



    }

    public boolean isEmpty(){

        if(size == 0){
            return true;
        }
        return false;

    }

    public int size(){
        return size;

    }

    public void sort(){
        if (isSorted) {
            return;
        }
        for (int i = 0; i < size - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < size; j++) {
                if (arrayList[j].compareTo(arrayList[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            T temp = arrayList[i];
            arrayList[i] = arrayList[minIndex];
            arrayList[minIndex] = temp;
        }
        isSorted = true;
    }





    public T remove(int index){
        if (index < 0 || index >= size) {
            return null;
        }
        T removedValue = arrayList[index];
        for (int i = index; i < size - 1; i++) {
            arrayList[i] = arrayList[i + 1];
        }
        arrayList[size - 1] = null;
        size--;
        isSorted = checkIfSorted();
        return removedValue;
    }

   public void reverse(){
       for (int i = 0; i < size / 2; i++) {
           T temp = arrayList[i];
           arrayList[i] = arrayList[size - 1 - i];
           arrayList[size - 1 - i] = temp;
       }
       isSorted = checkIfSorted();
    }

   public void removeDuplicates(){

       for (int i = 0; i < size; i++) {
           for (int j = i + 1; j < size; j++) {
               if (arrayList[i].compareTo(arrayList[j]) == 0) {
                   for (int k = j; k < size - 1; k++) {
                       arrayList[k] = arrayList[k + 1];
                   }
                   size--;
                   j--;
               }
           }
       }
       isSorted = checkIfSorted();
   }



    public void exclusiveOr(List<T> otherList){
        if (otherList == null) {
            return;
        }
        for (int i = 0; i < otherList.size(); i++) {
            T value = otherList.get(i);
            int index = indexOf(value);
            if (index != -1) {
                remove(index);
            } else {
                add(value);
            }
        }
        sort();
        removeDuplicates();


    }

    public void merge(List<T> list) {
        if (list == null) {
            return;
        }


        if (!isSorted) {
            sort();
        }
        if (!list.isSorted()) {
            list.sort();
        }


        T[] merged = (T[]) new Comparable[size + list.size()];
        int i = 0, j = 0, k = 0;

        while (i < size && j < list.size()) {
            T thisVal = arrayList[i];
            T otherVal = list.get(j);

            if (thisVal.compareTo(otherVal) < 0) {
                merged[k++] = thisVal;
                i++;
            } else if (thisVal.compareTo(otherVal) > 0) {
                merged[k++] = otherVal;
                j++;
            } else {
                merged[k++] = thisVal; // keep one copy of duplicates
                i++;
                j++;
            }
        }

        while (i < size) {
            merged[k++] = arrayList[i++];
        }
        while (j < list.size()) {
            merged[k++] = list.get(j++);
        }

        arrayList = merged;
        size = k;
        isSorted = checkIfSorted();
    }

    public T getMin(){
        if (size == 0) {
            return null;
        }
        T minValue = arrayList[0];
        for (int i = 1; i < size; i++) {
            if (arrayList[i].compareTo(minValue) < 0) {
                minValue = arrayList[i];
            }
        }
        return minValue;
    }

    public T getMax(){
        if (size == 0) {
            return null;
        }
        if (isSorted) {
            return arrayList[size - 1];
        }
        T maxValue = arrayList[0];
        for (int i = 1; i < size; i++) {
            if (arrayList[i].compareTo(maxValue) > 0) {
                maxValue = arrayList[i];
            }
        }
        return maxValue;

    }

    public String toString(){
        String result = "";
        for (int i = 0; i < size; i++) {
            result += arrayList[i] + "\n";
        }
        return result;

    }

    public boolean isSorted(){
      return isSorted;

    }
}
