 /**
     * this class is used to store the binary sequence and its key. it is for efficient encoding
     * @author Jeffrey Chen
*/
import java.util.Iterator;
import java.util.NoSuchElementException;

public class HuffmanCodeBook implements Iterable<Character> {
    
    private LinkedList[] mValues;
    private int mSize;
    private int mCapacity;
    private float mLoadFactor;

    /**class for storing the binary sequence and its key
     * this is just pair like (x,y)
     * 
     */
   
     private class HashElement implements Comparable<HashElement> {
       
        private Character mKey;
        private BinarySequence mBinaryElem;
        public HashElement(char key, BinarySequence value) {
            mKey = (Character) key;
            mBinaryElem = value;
        }
        @Override
        public int compareTo(HuffmanCodeBook.HashElement o) {
            return mKey.compareTo(o.mKey);
        }
    }

    private class LinkedList {

        /**
         * helper class for storing the elements in the linked list
         */
        private class Node{
            private Node mNext;
            private HashElement mValue;
            private Node(HashElement value){
                mValue = value;
            }
            private void replace(HashElement value){
                mValue = value;
            }
            
        }
        private Node mHead;
        private Node mTail;
        private int mSize;
        private LinkedList(){
            mHead = null;
            mTail = null;
            mSize = 0;
        }
        /**
         * add @param value to the end of the list
         * 
         */
        public void add(HashElement value){
            //pair up the value with the key
            Node newNode = new Node(value);
            if(mHead == null){
                mHead = newNode;
                mTail = newNode;
            }
            else{
                mTail.mNext = newNode;
                mTail = newNode;
            }
            mSize++;
        }
       
        /**
         * get the node at the @param index
         */
        public Node get(int index){
            //only get the node inside the bounds
            if(index < 0 || index >= mSize){
                return null;
            }
            Node node = mHead;
            for(int i = 0; i < index; i++){
                node = node.mNext;
            }
            return node;
        }
        /**
         *return the size of the list
         */
        public int size(){
            return mSize;
        }
        /**
         * 
         * @param value that we are searching 
         * @return the index of the element else -1
         */
        public int search(char key){
            Node node = mHead;
            int i = 0;
        
            while(node != null){
                //if the key is found return the index
                if(node.mValue.mKey.compareTo(key) == 0){
                    return i;
                }
                node = node.mNext;
                i++;
            }
            return -1;
        }
        /**
         * clone the list
         */
        public LinkedList clone(){
            LinkedList clone = new LinkedList();
            Node node = mHead;
            while(node != null){
                clone.add(node.mValue);
                node = node.mNext;
            }
            return clone;
        }
    }
    public HuffmanCodeBook(){
        mCapacity = 10;
        mLoadFactor = 0.75f;
        mSize = 0;
        mValues = new LinkedList[mCapacity];
    }    
   /**
    * 
    * @param c the key
    * @param bs the value
    */
    public void addSequence(char c, BinarySequence bs){
        if(mSize >= mCapacity * mLoadFactor){
            resize();
        }
        int index = c % mCapacity;
        if(mValues[index] == null){
            mValues[index] = new LinkedList();
        }
        mValues[index].add(new HashElement(c, bs));
        mSize++;
    }
   /**
    * 
    * @param c the key to search
    * @return the value of the key
    */
    public BinarySequence getSequence(char c){

        //find the character and return the binary sequence
        int index = c % mCapacity;
        if(mValues[index] == null){
            return null;
        }
        //get the position of the character inside the list
        int i = mValues[index].search(c);
        if(i == -1){
            return null;
        }
        return mValues[index].get(i).mValue.mBinaryElem;

    }
    /**
     * @return true if the key is in the hash table
     */
    public boolean contains(char c){
        //check if the character is in the hash table
        int index = c % mCapacity;
        if(mValues[index] == null){
            return false;
        }
        int i = mValues[index].search(c);
        if(i == -1){
            return false;
        }
        return true;
    }
    /**
     * @return if all the characters in the string are in the hash table
     */
    public boolean containsAll(String str)
    {
        //check if the string is in the hash table
        for(int i = 0; i < str.length(); i++)
        {
            if(!contains(str.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }
    /**
     * returns the size of the hash table
     */
    public int size(){
        return mSize;
    }
    /**
     * resizes the hash table
     */
    private void resize(){
        //create a new array with double the capacity
        //esentially rehashing the table 
        LinkedList[] newValues = new LinkedList[mCapacity*2];
        //rehash the values
        for(int i = 0; i < mCapacity; i++){
            if(mValues[i] != null){
                
                LinkedList list = mValues[i].clone();
                for(int j = 0; j < list.size(); j++){
                    HashElement element = list.get(j).mValue;
                    //get new index based on the new capacity
                    int index = element.mKey.hashCode() % newValues.length;
                    if(newValues[index] == null){
                        newValues[index] = new LinkedList();
                    }
                    newValues[index].add(element);
                }
            }
        }
        mCapacity = mCapacity*2;
        mValues = newValues;
    }
   /**
    * @return the BinarySequence form of the string @param s
    */
    public BinarySequence encode(String s){
        BinarySequence bs = new BinarySequence();
        for(Character c: s.toCharArray())
        {
            //get the binary sequence of the character and add it to the binary sequence
            BinarySequence temp = getSequence(c);
            if(temp!=null)
            {
                bs.append(temp);
            }
        }
        return bs;
    }
    public Iterator<Character> iterator()
    {
        return new KeyIterator();
    }
    //implement KeyIterator
    private class KeyIterator implements Iterator<Character>{
        private int mIndex;
        private Character keys[];
        private KeyIterator(){
            mIndex = 0;
            keys= new Character[mCapacity];
            int key_index=0;
            for(int i = 0; i < mCapacity; i++){
                //get the linked list
                LinkedList list = mValues[i];
                if(list != null){
                    //get the keys
                    for(int j = 0; j < list.size(); j++){
                        keys[key_index++] = list.get(j).mValue.mKey;
                    }
                }
            }
        }
        public boolean hasNext(){
            return mIndex < keys.length;
        }
        public Character next(){
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            return keys[mIndex++];
        }
        public void remove(){
            throw new UnsupportedOperationException();
        }
    }


}

