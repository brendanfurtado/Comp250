//Name: Brendan Furtado
//ID: 260737867

public class HashLinkedList<K,V>{
	/*
	 * Fields
	 */
	private HashNode<K,V> head;

	private Integer size;

	/*
	 * Constructor
	 */

	HashLinkedList(){
		head = null;
		size = 0;
	}


	/*
	 *Add (Hash)node at the front of the linked list
	 */

	public void add(K key, V value){

	    // ADD CODE BELOW HERE

        // create a new node with the key and value
        HashNode newnode = new HashNode(key, value);
        size++;

        if (head==null) {
            head = newnode;
            return;
        }

        //in this case there already exists a node so make newnode the head
        newnode.next = head;
        head = newnode;

		// ADD CODE ABOVE HERE
	}

	/*
	 * Get Hash(node) by key
	 * returns the node with key
	 */

	public HashNode<K,V> getListNode(K key){

	    // ADD CODE BELOW HERE

        // check the boundry case first
        if (isEmpty())
            return null;

        // itterate through the list and try to find the key
        int i = 0;
        HashNode node = head;

        while (i < size) {
            if (node.getKey() == key) {
                return node;
            }
            i++;
            node = node.next;
        }

        // if we reach the end and don't find the key then return null
        return null;

		// ADD CODE ABOVE HERE
	}


	/*
	 * Remove the head node of the list
	 * Note: Used by remove method and next method of hash table Iterator
	 */

	public HashNode<K,V> removeFirst(){

	    // ADD CODE BELOW HERE

        // first check if empty list
        if (head==null)
            return null;

        // next check if only one node

        if (size == 1) {
            HashNode first = head; // save away the head node
            head = null;
            size--;
            return first;
        }

        // in this case we have at least 2 nodes on the list so we remove the first and replace the head
        size--;
        HashNode first = head;
        HashNode newhead = head.next;
        head = head.next;
        return first;

		// ADD CODE ABOVE HERE
	}

	/*
	 * Remove Node by key from linked list 
	 */

	public HashNode<K,V> remove(K key){

	    // ADD CODE BELOW HERE

        // check the boundry case first
        if (isEmpty()) {
            return null;
        }

        // simple case where key is in the first node;

        if (head.getKey() == key) {
            return removeFirst();
        }


        // iterate through the list and try to find the key; n is the next and p is the previous node
        int i = 0;
        HashNode next = head;
        HashNode prev = next; ///////

        while (i < size) {

            if (next.getKey() == key) {
                prev.next = next.next;
                size--;
                return next;
            }
            // didn't find a matching key so keep looking;
            i++;
            prev = next;
            next = next.next;
        }

        // if we reach the end and don't find the key then return null
		// ADD CODE ABOVE HERE
		return null; // removing failed

	}



	/*
	 * Delete the whole linked list
	 */
	public void clear(){
		head = null;
		size = 0;
	}
	/*
	 * Check if the list is empty
	 */

	boolean isEmpty(){
		return size == 0? true:false;
	}

	int size(){
		return this.size;
	}

	//ADD YOUR HELPER  METHODS BELOW THIS

    public HashNode<K, V> getHead() { return head; }

    /*Helpers to check if the list works
    public String toString(){

        return "Linked List with " + size() + " elements." ;
    }

    public void printlist(){
        System.out.println("Contents of " + toString());

        HashNode node = head;
        while(node != null){
            System.out.println(node.toString());
            node= node.getNext();
        }
    }
    */


	//ADD YOUR HELPER METHODS ABOVE THIS



}
