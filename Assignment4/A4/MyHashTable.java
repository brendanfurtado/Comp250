import java.util.ArrayList;
import java.util.Iterator;

//Name: Brendan Furtado
//ID: 260737867

class MyHashTable<K,V> {
	/*
	 *   Number of entries in the HashTable. 
	 */
	private int entryCount = 0;

	/*
	 * Number of buckets. The constructor sets this variable to its initial value,
	 * which eventually can get changed by invoking the rehash() method.
	 */
	private int numBuckets;

	/**
	 * Threshold load factor for rehashing.
	 */
	private final double MAX_LOAD_FACTOR=0.75;

	/**
	 *  Buckets to store lists of key-value pairs.
	 *  Traditionally an array is used for the buckets and
	 *  a linked list is used for the entries within each bucket.   
	 *  We use an Arraylist rather than an array, since the former is simpler to use in Java.   
	 */

	ArrayList< HashLinkedList<K,V> >  buckets;

	/* 
	 * Constructor.
	 * 
	 * numBuckets is the initial number of buckets used by this hash table
	 */

	MyHashTable(int numBuckets) {

		//  ADD YOUR CODE BELOW HERE

        // allocate the arraylist and initialize the rest

        buckets = new ArrayList<>();
        this.numBuckets = numBuckets;
        entryCount = 0;

        // initialize the arraylist by adding an empty linked list to each index of the arraylist

        for (int i = 0; i < numBuckets; i++) {
            buckets.add(i, new HashLinkedList<K, V>());
        }

		//  ADD YOUR CODE ABOVE HERE

	}

	/**
	 * Given a key, return the bucket position for the key. 
	 */
	private int hashFunction(K key) {

		return  Math.abs( key.hashCode() ) % numBuckets ;
	}

	/**
	 * Checking if the hash table is empty.  
	 */

	public boolean isEmpty()
	{
		if (entryCount == 0)
			return true;
		else
			return(false);
	}

	/**
	 *   return the number of entries in the hash table.
	 */

	public int size()
	{
		return(entryCount);
	}

	/**
	 * Adds a key-value pair to the hash table. If the load factor goes above the 
	 * MAX_LOAD_FACTOR, then call the rehash() method after inserting. 
	 * 
	 *  If there was a previous value for the given key in this hashtable,
	 *  then overwrite it with new value and return the old value.
	 *  Otherwise return null.   
	 */

	public  V  put(K key, V value) {

		//  ADD YOUR CODE BELOW HERE

        // this implementation will not allow duplicates.
        // if a key already exists it will replace the value with the new value provided.

        HashLinkedList<K, V> list = buckets.get(hashFunction(key));
        HashNode<K, V> node = list.getListNode(key);

        // if the node already exists we need to replace it with the same key and new value
		//we are replacing a node, so there is no need to check if rehash() should be called
        if (node != null) {

            // remove the old node with the key, oldvalue
            list.remove(key);

            // add the same key back with new value
            list.add(key, value);

            // return the old value back, as node is still pointing to it
            return node.getValue();
        }

        // if key did not exist then simply add the node and return null

        list.add(key, value);
        entryCount++;

        // call rehash after adding a new node
        if ((1.0*entryCount)/numBuckets >= MAX_LOAD_FACTOR) {
            rehash();
        }

		//  ADD YOUR CODE ABOVE HERE
		return null;
	}

	/**
	 * Retrieves a value associated with some given key in the hash table.
     Returns null if the key could not be found in the hash table)
	 */
	public V get(K key) {

		//  ADD YOUR CODE BELOW HERE

        // first find the bucket using the hashFunction
        // then use the linked list method to return the node with the key or null.
        HashNode<K, V> node = buckets.get(hashFunction(key)).getListNode(key);

        if (node != null) {
            return node.getValue();
        }

		//  ADD YOUR CODE ABOVE HERE

		return null;
	}

	/**
	 * Removes a key-value pair from the hash table.
	 * Return value associated with the provided key.   If the key is not found, return null.
	 */
	public V remove(K key) {

		//  ADD YOUR CODE BELOW HERE

        HashLinkedList<K, V> list = buckets.get(hashFunction(key));

        // first check if the linked list associated with the bucket is not null
        if (list != null) {

            // use the linked list helper method to remove the key
            HashNode<K,V> node = list.remove(key);

            // if the key was successfully removed then return the value
            if (node != null) {
                entryCount--;
                return node.getValue();
            }
        }

		//  ADD  YOUR CODE ABOVE HERE

		return(null);
	}

	/*
	 *  This method is used for testing rehash().  Normally one would not provide such a method. 
	 */

	public int getNumBuckets(){
		return numBuckets;
	}

	/*
	 * Returns an iterator for the hash table. 
	 */

	public MyHashTable<K, V>.HashIterator  iterator(){
		return new HashIterator();
	}

	/*
	 * Removes all the entries from the hash table, 
	 * but keeps the number of buckets intact.
	 */
	public void clear()
	{
		for (int ct = 0; ct < buckets.size(); ct++){
			buckets.get(ct).clear();
		}
		entryCount=0;		
	}

	/**
	 *   Create a new hash table that has twice the number of buckets.
	 */


	public void rehash()
	{
		//   ADD YOUR CODE BELOW HERE

        // store away the previous arraylist
        ArrayList<HashLinkedList<K, V>> prevBuckets = buckets;
        int prevNumBuckets = numBuckets;

        // double the number of buckets
        numBuckets = 2 * numBuckets;
		entryCount = 0; //reinitialize entryCount

        // Allocate a new array list to hold the 2* buckets
        buckets = new ArrayList<>();


        //initialize the arraylist by adding an empty linked list to each index of the Arraylist
        for (int i = 0; i < numBuckets; i++) {
            buckets.add(i, new HashLinkedList<K, V>());
        }

        // now copy all the nodes from the old hash table to the new one
        // iterate through all the buckets and pick up the linked lsts that are not empty

        for (int i = 0; i < prevNumBuckets; i++ ) {
            HashLinkedList<K, V> list = prevBuckets.get(i);

            // for each non empty list remove a node from the old and add one to the new hash map
            while (!list.isEmpty()) {
                HashNode<K, V> node = list.removeFirst();
                put(node.getKey(), node.getValue());
            }
        }

		//   ADD YOUR CODE ABOVE HERE

	}


	/*
	 * Checks if the hash table contains the given key.
	 * Return true if the hash table has the specified key, and false otherwise.
	 */

	public boolean containsKey(K key)
	{
		int hashValue = hashFunction(key);
		if(buckets.get(hashValue).getListNode(key) == null){
			return false;
		}
		return true;
	}

	/*
	 * return an ArrayList of the keys in the hashtable
	 */

	public ArrayList<K>  keys()
	{

		ArrayList<K>  listKeys = new ArrayList<K>();

		//   ADD YOUR CODE BELOW HERE

        // we will iterate through all buckets and then each linked list that is not null we iterate through the nodes

        for (int i = 0; i < numBuckets; i++ ) {
            HashLinkedList<K, V> list = buckets.get(i);

            // for each non empty list remove a node from the old and add one to the new hash map
            if (!list.isEmpty()) {

                HashNode<K, V> head = list.getHead();

                // add all keys in the list to the listKeys
                while (head != null) {

                    listKeys.add(head.getKey());
                    head = head.getNext();
                }

            }
        }

        return listKeys;

		//   ADD YOUR CODE ABOVE HERE

	}

	/*
	 * return an ArrayList of the values in the hashtable
	 */
	public ArrayList <V> values()
	{
		ArrayList<V>  listValues = new ArrayList<V>();

		//   ADD YOUR CODE BELOW HERE

        // we will iterate through all buckets and then each linked list that is not null we iterate through the nodes

        for (int i = 0; i < numBuckets; i++ ) {
            HashLinkedList<K, V> list = buckets.get(i);

            // for each non empty list remove a node from the old and add one to the new hash map
            if (!list.isEmpty()) {

                HashNode<K, V> head = list.getHead();

                // add all keys in the list to the listKeys
                while (head != null) {

                    listValues.add(head.getValue());
                    head = head.getNext();
                }

            }
        }

        return listValues;

		//   ADD YOUR CODE ABOVE HERE

	}

	@Override
	public String toString() {
		/*
		 * Implemented method. You do not need to modify.
		 */
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buckets.size(); i++) {
			sb.append("Bucket ");
			sb.append(i);
			sb.append(" has ");
			sb.append(buckets.get(i).size());
			sb.append(" entries.\n");
		}
		sb.append("There are ");
		sb.append(entryCount);
		sb.append(" entries in the hash table altogether.");
		return sb.toString();
	}

	/*
	 *    Inner class:   Iterator for the Hash Table.
	 */
	public class HashIterator implements  Iterator<HashNode<K,V> > {
        HashLinkedList<K,V>  allEntries;

		/**
		 * Constructor:   make a linkedlist (HashLinkedList) 'allEntries' of all the entries in the hash table
		 */
		public  HashIterator()
		{

			//  ADD YOUR CODE BELOW HERE
            allEntries = new HashLinkedList<K, V>();

            for(int i = 0; i < numBuckets; i++) {
				HashLinkedList<K, V> list = buckets.get(i);


				// for each bucket, take all the nodes and add them to the allEntries linked list

				if (!list.isEmpty()) {
					HashNode<K, V> head = list.getHead();

					// add all keys in the list to the listKeys

					while (head != null) {
						allEntries.add(head.getKey(), head.getValue());
						head = head.getNext();
					}
				}
			}
				//  ADD YOUR CODE ABOVE HERE

		}

		//  Override
		@Override
		public boolean hasNext()
		{
			return !allEntries.isEmpty();
		}

		//  Override
		@Override
		public HashNode<K,V> next()
		{
			return allEntries.removeFirst();
		}

		@Override
		public void remove() {
			// not implemented,  but must be declared because it is in the Iterator interface

		}		
	}

}
