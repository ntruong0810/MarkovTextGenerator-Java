package edu.pasadena.cs.cs03b;

import java.util.Random;
import java.util.Set;

// TOOD: emulate tht functionality of MAP class to hold the Markov Chain model using primitive arrays
public class DreamMap {
    //TODO: define the required private variables to hold the keys and values per assignment requrements
    private String[] _keys;
    private DreamList[] _values;
    private int _size;
    private int _capacity;

    // test pull request

    // TODO: constructor to initialize the map with a given capacity
    public DreamMap(int capacity){ 
        if(capacity < 1){
            capacity = 1;
        }
        _capacity = capacity;
        _keys = new String[_capacity];
        _values = new DreamList[_capacity];
        _size = 0;
    }

    // TODO: implement put method to add a key-value pair to the map
    public void put(String key, DreamList value){
        // loop through _key list
        for (int i = 0; i < _size; i++){
            if (_keys[i].equals(key)) {
                // Append new values to the existing list
                for (int j = 0; j < value.size(); j++) {
                    _values[i].add(value.get(j));
                }
                return;
            }
        }
        
        if (_size >= _capacity) {
            resize();
        }
    
        // If key doesn't exist, insert it
        _keys[_size] = key;
        _values[_size] = value;
        _size++;

    }

    // TODO: implement put If Absent
    // 
    public void putIfAbsent(String key, DreamList value) {
        for (int i = 0; i < _size; i++) {
            if (_keys[i].equals(key)) {
                return; // key already exists, do nothing
            }
        }
    
        // Key is not found, insert it
        if (_size >= _capacity) {
            resize();
        }
    
        _keys[_size] = key;
        _values[_size] = value;
        _size++;
    }

    // TODO: implement get method to retrieve the value associated with the key
    public DreamList get(String key) {
        for (int i = 0; i < _size; i++){
            if (_keys[i].equals(key)) {
                return _values[i];
            }
        }
        return null;    // TODO: replace with actual implementation
    }

    // TODO: return size of the map
    public int size() {
        return _size;  // TODO: replace with actual implementation
    }   

    // TODO: Add a keys() method to return all keys as a String array
    public String[] keys(){
        String[] keysCopy = new String[_size];
        for(int i = 0; i < _size; i++){
        keysCopy[i] = _keys[i];
        }
        return keysCopy;
    }

    // TODO: resize the arrays to double their size when the map is full
    private void resize(){
        // TODO: replace with actual implementation
        int newCapacity = _capacity;
        if(_size >= _capacity)
            newCapacity = _capacity * 2;
        else if (_size < _capacity / 2 && _size >= 1) 
            newCapacity = _capacity / 2;
        else
            return ;
        
        String[] newKeys = new String[newCapacity];
        DreamList[] newValues = new DreamList[newCapacity];
    
        for (int i = 0; i < _size; i++) {
            newKeys[i] = _keys[i];
            newValues[i] = _values[i];
        }
    
        _keys = newKeys;
        _values = newValues;
        _capacity = newCapacity;
    }

    public String getRandomKey(){
    if(_size == 0) 
        return null;
    Random random = new Random();
    int randomIndex = random.nextInt(_size);
    return _keys[randomIndex];
    }

    // check if the key appear in DreamMap
    public boolean containsKey(String key){
    for (int i = 0; i < _size; i++){
        if (_keys[i].equals(key)) 
            return true;
    }
    return false;
    }


}
