package edu.pasadena.cs.cs03b;

import java.util.Random;

// TODO: implement the functionality of a list to hold the Markov Chain model using primitive arrays
// This class should be able to add, remove, and retrieve elements from the list
public class DreamList {
    //TODO: define the required private variables to hold the keys and values per assignment requrements
    private int _size;
    private int _capacity;
    private String[] _data;
    private int[] _counts; // calculate the frequency of that word

    // TODO: constructor to initialize the list with a given capacity
    public DreamList(int capacity) { // default constructor  
        if(capacity < 1)
            _capacity = 1;
        _capacity = capacity;
        _data = new String[_capacity];
        _counts = new int[_capacity]; 
        _size = 0;  
    }

    // TODO: implement methods to add element to the list
    public void add(String element){
    // check if the word already exist
    for(int i = 0; i < _size; i++){
        if(_data[i].equals(element)){
            _counts[i]++;
            return;
        }
    }
    if(_size >= _capacity) 
        resize();
    _data[_size] = element;
    _counts[_size] = 1;
    _size++;
}

    // TODO: implement methods retrieve element from the list
    public String get(int index) {
        if(index < 0 || index >= _size)
            throw new IndexOutOfBoundsException("Invalid index");
        
        return _data[index]; // TODO: replace with actual implementation
    }

    // TODO: implement methods to remove element from the list
    public void remove(int index) {
        if(index < 0 || index >= _size)
            throw new IndexOutOfBoundsException("Invalid index");
        if (_counts[index] > 1)
            _counts[index]--; // reduce the frequency
        else{    
            for(int i = index; i < _size - 1; i++){
            _data[i] = _data[i+1]; // shift all the elements from the right of data[index] to the left
            _counts[i] = _counts[i+1];
            }       
        _size --; // reduce the size by 1    
        _data[_size] = null; // set the last element to null to prevent garbage value
        }
        
        resize();
    }

    // TODO: resize the arrays to double their size when the list is full
    private void resize(){
    // Determine new capacity
    int newCapacity;
    if(_size >= _capacity) 
        newCapacity = _capacity * 2;  // Expand when full
    else if(_size < _capacity / 2 && _size >= 1) 
        newCapacity = _capacity / 2;  // Shrink when underutilized
    else
        return;  // No need to resize
    

    // Ensure minimum capacity is at least 1
    if(newCapacity < 1) 
        newCapacity = 1;

    // Create new arrays with updated capacity
    String[] newData = new String[newCapacity];
    int[] newCounts = new int[newCapacity];

    // Copy data and counts to new arrays
    for (int i = 0; i < _size; i++){
        newData[i] = _data[i];
        newCounts[i] = _counts[i];
    }

    // Update internal references
    _data = newData;
    _counts = newCounts;
    _capacity = newCapacity;
}
    // TODO: implement methods to return the size of the list
    public int size(){
        return _size; // TODO: replace with actual implementation
    }

    // how many time that word repeated
    public int getCount(int index){
    if (index < 0 || index >= _size)
        throw new IndexOutOfBoundsException();
    return _counts[index];
    }

    public String[] toArray(){
    String[] result = new String[_size];
    System.arraycopy(_data, 0, result, 0, _size);
    return result;
    }

    // get Random element from the list
    public String getRandomElement(Random rand){
        if (_size == 0) 
            return null;
        return _data[rand.nextInt(_size)];
    }

    // get total count of all elements in the list
    // used for weighted random selection
    public int getTotalCount(){
        int total = 0;
        for(int i = 0; i < _size; i++){
            total += _counts[i]; // sum up all the frequency of each word
        }
        return total;
    }

    // get weighted random element from the list
    public String getWeightedRandomElement(Random rand) {
    if (_size == 0) 
        return null;
    // get the total count of all elements in the list
    int totalCount = getTotalCount();
    double r = rand.nextDouble(); // random number from [0, 1)
    double cumulativeProbability = 0.0;

    // loop through the list
    for (int i = 0; i < _size; i++){
        // calculate the probability of each element in the list
        double probability = (double) _counts[i] / totalCount;
        cumulativeProbability += probability;
        // if the random number is less than the cumulative probability, return the element
        if(r < cumulativeProbability){ 
            return _data[i];
        }
    }

    return _data[_size - 1]; 
}
}

