package CSCI1933P3;

public class LinkedList<T extends  Comparable<T>> implements List<T> {
    private boolean isSorted;
    Node<T> head;
    Node<T> tail;
    private int size;

    public LinkedList(){
        head = new Node<T>(null, null);
        isSorted = true;
        size = 0;

    }

    public boolean checkIfSorted(){
        Node<T> current = head.getNext();
        if(current == null || current.getNext() == null){
            return true;
        }
        while(current.getNext() != null){
            if(current.getData().compareTo(current.getNext().getData()) > 0){
                return false;
            }
            current = current.getNext();
        }

        return true;
    }

    public boolean add(T element){
        if(element == null){
            return false;
        }


        else {
            if(head.getNext() == null){
                Node<T> newest = new Node (element, null);
                head.setNext(newest);
                tail = head.getNext();
                size++;
                isSorted = checkIfSorted();
            }

            else {
                Node <T> newest = new Node<T>(element, null);
                tail.setNext(newest);
                tail = tail.getNext();
                size++;
                isSorted = checkIfSorted();


            }
            return true;
        }

    }

    public boolean add (int index, T element){
        if(index >= size || element == null || index < 0){
            return false;
        }

        else{
            int count = 0;
            Node <T> current = head.getNext();
            if(index == 0){
                Node<T> newest = new Node<T>(element, head.getNext());
                head.setNext(newest);
                size++;
                isSorted = checkIfSorted();
                return true;
            }

            while(current != null){
                if(index == count + 1){
                  Node <T> newest = new Node <T>(element, current.getNext());
                  current.setNext(newest);
                  size++;
                  isSorted = checkIfSorted();
                  return true;

                }
                current = current.getNext();
                count++;




            }
        }


        return false;


    }

    public void clear() {
        head.setNext(null);
        tail = null;
        size = 0;
        isSorted = true;
    }

    public T get (int index){
        if(index >= size || index < 0){
            return null;
        }
        int count = 0;
        Node<T> curr = head.getNext();
        while (curr != null){
            if (index == count){
                return curr.getData();
            }
            count++;
            curr = curr.getNext();


        }

        return null;
    }

    public int indexOf (T element){
        int count = 0;
        if(element == null){
            return -1;
        }


        else{
            Node<T> current = head.getNext();
            if(isSorted == true){
                while(current != null){

                        if(current.getData().compareTo(element) == 0){
                            return count;
                        }


                        current = current.getNext();
                        count++;

                     }

                }
            //if isSorted = false;
            else{
                while(current != null) {

                    if (current.getData().compareTo(element) == 0){
                        return count;
                    }


                    current = current.getNext();

                   count++;


                }

            }

        }



        return -1;
    }

   public boolean isEmpty() {
       Node<T> current = head.getNext();
       if(current != null){
           return false;
       }
       isSorted = checkIfSorted();
       return true;
   }

   public int size(){
       return size;
    }

   public void sort(){
       if (isSorted || size <= 1) {
           return;
       }

       Node<T> sorted = head;
       Node<T> current = head.getNext();


       sorted.setNext(null);

       while (current != null) {
           Node<T> next = current.getNext();


           Node<T> prev = sorted;
           Node<T> scan = sorted.getNext();

           while (scan != null && scan.getData().compareTo(current.getData()) < 0) {
               prev = scan;
               scan = scan.getNext();
           }


           current.setNext(scan);
           prev.setNext(current);

           current = next;
       }

       isSorted = checkIfSorted();


        }

    public T remove(int index){
        if (index < 0 || index >= size) {
            return null;
        }

        Node<T> previous = head;
        Node<T> current = head.getNext();
        int counter = 0;

        while (current != null) {
            if (counter == index) {
                previous.setNext(current.getNext());
                size--;
                isSorted = checkIfSorted();
                return current.getData();
            }

            previous = current;
            current = current.getNext();
            counter++;
        }


        return null;

    }

    public void reverse(){
        if (head.getNext() == null){
            return;
        }
        Node<T> prev = null;
        Node<T> current = head.getNext();
        Node<T> nextTemp = current.getNext();

        while(current != null){
            current.setNext(prev);
            prev = current;
            current = nextTemp;
            if(nextTemp != null){
                nextTemp = nextTemp.getNext();

            }



        }

        tail = head.getNext();
        head.setNext(prev);
        isSorted = checkIfSorted();

    }

    public void removeDuplicates(){

        int countOne = 0;
        int countTwo;
        Node<T> current = head.getNext();
        while(current != null){
            Node<T> other = current.getNext();
            countTwo = countOne + 1;
            while(other != null){
                if(current.getData().compareTo(other.getData()) == 0 ){
                    remove(countTwo);
                    countTwo--;
                }
                other = other.getNext();
                countTwo++;
            }
            current = current.getNext();
            countOne++;
        }
        if(isSorted == false){
            isSorted = checkIfSorted();
        }
    }

   public void exclusiveOr (List<T> otherList){
       if (otherList == null) {
           return;
       }

       LinkedList<T> other = (LinkedList<T>) otherList;

       this.sort();
       this.removeDuplicates();
       other.sort();
       other.removeDuplicates();

       Node<T> p1 = head.getNext();
       Node<T> p2 = other.head.getNext();

       LinkedList<T> result = new LinkedList<>();

       while (p1 != null && p2 != null) {
           int checkForDiff = p1.getData().compareTo(p2.getData());

           if (checkForDiff < 0) {
               result.add(p1.getData());
               p1 = p1.getNext();
               isSorted = checkIfSorted();
           }
           else if (checkForDiff > 0) {
               result.add(p2.getData());
               p2 = p2.getNext();
               isSorted = checkIfSorted();
           }
           else {
               p1 = p1.getNext();
               p2 = p2.getNext();
           }
       }


       while (p1 != null) {
           result.add(p1.getData());
           p1 = p1.getNext();
           isSorted = checkIfSorted();
       }

       while (p2 != null) {
           result.add(p2.getData());
           p2 = p2.getNext();
           isSorted = checkIfSorted();
       }


       this.head.setNext(result.head.getNext());
       this.size = result.size;








    }

    public void merge (List<T> list){
        if (list == null) {
            return;
        }

        LinkedList<T> other = (LinkedList<T>) list;

        this.sort();
        other.sort();

        Node<T> p1 = this.head.getNext();
        Node<T> p2 = other.head.getNext();

        Node<T> mergedDummy = new Node<>(null);
        Node<T> tail = mergedDummy;

        while (p1 != null && p2 != null) {
            if (p1.getData().compareTo(p2.getData()) <= 0) {
                tail.setNext(p1);
                p1 = p1.getNext();
            } else {
                tail.setNext(p2);
                p2 = p2.getNext();
            }
            tail = tail.getNext();
        }


        if (p1 != null) {
            tail.setNext(p1);
        } else {
            tail.setNext(p2);
        }


        head.setNext(mergedDummy.getNext());
        isSorted = checkIfSorted();


        this.size += other.size;





    }

   public T getMin() {
        Node<T> current = head.getNext();
       if(current == null){
           return null;
       }

        Node<T> minValue = head.getNext();



        while(current != null){
            if(current.getData().compareTo(minValue.getData()) < 1){
                minValue = current;
            }


                current = current.getNext();

        }

        if(minValue == null){
            return null;
        }
       isSorted = checkIfSorted();

        return minValue.getData();

   }



   public T getMax(){
       Node<T> current = head.getNext();
       Node<T> maxValue = head.getNext();
       if(current == null || tail == null){
           return null;
       }


       if (isSorted == false) {
           while (current != null) {
               if (current.getData().compareTo(maxValue.getData()) > 0) {
                   maxValue = current;
               }

               current = current.getNext();

           }
           isSorted = checkIfSorted();
           return maxValue.getData();
       }

       else {
           maxValue = tail;
           isSorted = checkIfSorted();
           return maxValue.getData();
       }

   }



   public String toString(){
       String result = "";
       Node<T> current = head.getNext(); // skip dummy head
       while (current != null) {
           result += current.getData() + "\n";
           current = current.getNext();
       }
       return result;

    }

   public boolean isSorted(){
      return isSorted;

    }

}
