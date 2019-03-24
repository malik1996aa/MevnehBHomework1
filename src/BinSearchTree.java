import java.util.Stack;

public class BinSearchTree<T> {
	private BinNode<Integer> root;

	// constructor
	public BinSearchTree() {
		root = null ;

	}
	public BinNode<Integer> getRoot() {
		return root;
	}

	public void add (int x){
		if (root == null) // tree is empty
			this.root = new BinNode<Integer>(x); // set as root
		else {
			addIterative(x); // or alternatively
			// addRcrsv(this.root, x);
		}
	}

	// add new value to the tree in iterative manner
	private void addIterative (int x){
		//we already have at least the root of the tree
		BinNode<Integer> current = this.root;
		while ( (x < current.getValue() && current.hasLeft() ) 
				||  (x >= current.getValue() && current.hasRight()) ) {
			if (x < current.getValue())
				current = current.getLeft(); // go left
			else
				current = current.getRight(); // go right
		}//End of while

		BinNode<Integer> node = new BinNode<Integer>(x);

		if (x < current.getValue())
			current.setLeft(node);
		else
			current.setRight(node);
	}//End of method


	// add new value to the tree in recursive manner
	private  void addRcrsv (BinNode<Integer> current, int x){   
		if (x < current.getValue()) {
			if (! current.hasLeft()) //if no left child
				current.setLeft(new BinNode<Integer>(x)); //add it as a left child
			else
				addRcrsv(current.getLeft(),x); //continue search on left side
		}
		else { //x >=current.getValue() 
			if (! current.hasRight()) //if no right child
				current.setRight(new BinNode<Integer>(x)); //add it as a right child
			else
				addRcrsv(current.getRight(),x);//continue search on right side
		}
	}

	// print tree values
	public void print(){
		printInOrder(this.root);
		System.out.println();
	}

	// traverse the tree in order and print all values 
	public void printInOrder (BinNode<Integer> current){
		if (current != null)
		{
			printInOrder(current.getLeft());
			System.out.print(current.getValue() + ", ");
			printInOrder(current.getRight());
		}
	}

	//inorder traversal
	public void inOrder()
	{
		callInOrder(this.root);
	}

	private void callInOrder(BinNode<Integer> current)
	{
		if (current != null)
		{
			callInOrder(current.getLeft());
			System.out.print(current.getValue() + ", ");
			callInOrder(current.getRight());
		}
	}

	//preOrder traversal
	public void preOrder()
	{
		callPreOrder(this.root);
	}

	private void callPreOrder(BinNode<Integer> current)
	{
		if (current != null)
		{
			System.out.print(current.getValue() + ", ");
			callPreOrder(current.getLeft());
			callPreOrder(current.getRight());
		}
	}

	//preOrder traversal
	public void postOrder()
	{
		callPostOrder(this.root);
	}

	private void callPostOrder(BinNode<Integer> current)
	{
		if (current != null)
		{
			callPostOrder(current.getLeft());
			callPostOrder(current.getRight());
			System.out.print(current.getValue() + ", ");
		}
	}

	// find x in the tree
	public boolean exists(int x) {
		return existsIterative(x); // alternatively -->
		//return existsRcsv(this.root, x);		
	}

	// find x the tree in iterative manner
	private  boolean existsIterative(int x) {
		BinNode<Integer> current = this.root;
		while (current != null)
		{
			if (current.getValue() == x)
				return true;
			if (x < current.getValue())
				current = current.getLeft();
			else
				current = current.getRight();
		}
		return false;
	}

	// find x the tree in recursive manner
	private  boolean existsRcrsv (BinNode<Integer> current, int x){
		if (current == null) //tree is null return false
			return false;
		if (current.getValue() == x) //if the value is x then x is exsit
			return true;
		if (x < current.getValue())// check if to search in the left sub-tree
			return existsRcrsv (current.getLeft(), x);
		return existsRcrsv (current.getRight(),x);// serach in the right sub-tree
	}

	public  Integer getNode (int x) {
		return getNode(this.root, x);
	}

	private  Integer getNode (BinNode<Integer> current, int x){
		if (current == null)  
			return null;
		if (current.getValue() == x)  
			return x;
		if (x < current.getValue()) 
			return getNode (current.getLeft(), x);
		else
			return getNode (current.getRight(),x); 
	}


	// find node with maximum value - recursive
	public int biggest () throws Exception{
		if (root == null)
			throw new Exception("Tree is Empty");
		return biggest(this.root).getValue();
	}

	private BinNode<Integer> biggest (BinNode<Integer> current){
		if (!current.hasRight())
			return current;
		return biggest(current.getRight());
	}

	// find node with maximum value
	public int smallest () throws Exception{
		if (root == null) 
			throw new Exception("Tree is Empty");
		return smallest(this.root).getValue();
	}

	// find node with minimum value - iterative 
	private  BinNode<Integer> smallest (BinNode<Integer> current){
		while (current.hasLeft())
			current = current.getLeft();
		return current;
	}


	//return node with next-highest value after deletItem
	//goes to right child,then right child's left descendants
	private  BinNode<Integer>  getSuccessor(BinNode<Integer> node, Stack<BinNode<Integer>> ancestors) {
		if (node == biggest(root))
			return null;
		else if (node.hasRight()) // item has right subtree
			return smallest(node.getRight());
		else {
			 BinNode<Integer> parent = ancestors.pop();
			 while(parent.getRight() == node && !ancestors.isEmpty()) {
				 node = parent;
				 parent = ancestors.pop(); // previous ancestor
			 }
			 return parent;
		}
	}

	private  BinNode<Integer> getPredeccessor (BinNode<Integer> node, Stack<BinNode<Integer>> ancestors){
		if (node == smallest(root))
			return null;
		else if (node.hasLeft()) // item has left subtree
			return biggest(node.getLeft());
		else {
			 BinNode<Integer> parent = ancestors.pop();
			 while(parent.getLeft() == node && !ancestors.isEmpty()) {
				 node = parent;
				 parent = ancestors.pop(); // previous ancestor
			 }
			 return parent;
		}
	}

	// calculate tree height
	public int calcHeight() {
		return calcHeight(this.root); 
	}

	private int calcHeight(BinNode<Integer> current ){
		if (current==null)
			return -1;
		else
			return Math.max (calcHeight (current.getLeft() ), calcHeight (current.getRight() ) ) + 1;
	}
	/************************************************************************************************************/
	//Delete node by its key
	public boolean delete(int key) {

		if (root == null)
			return false;
		
		Stack<BinNode<Integer>> ancestors = new Stack<BinNode<Integer>>(); 
		BinNode<Integer> current = root;
		BinNode<Integer> parent = null;

		while(current.getValue() != key) {//search for the node with the value of key
			ancestors.push(current);
			if(key < current.getValue())  //go left
				current = current.getLeft();
			else  //go right
				current = current.getRight();

			if(current == null) //Didn't find it
				return false; //return false 
		}
		// we get to this section- the node to be deleted exists and reached
		// current holds the node to be deleted
		// ancestors holds all ancestors along the path from root to current

		//Case One: THE NODE TO BE DELETED HAS NO CHILDREN		
		//if no children, simply delete it
		if( current.hasLeft() == false && current.hasRight() == false)  
			// other way to write it : if( !(current.hasLeft()  && current.hasRight() ) ) 
			deleteLeaf(current,ancestors);

		//Case Two: THE NODE TO BE DELETED HAS ONE CHILD	
		//if only one child, replace with subtree
		else if(!current.hasLeft() || !current.hasRight()) 
			deleteNodeWithOneSubtree(current,ancestors);
		
		//Case 3: THE NODE TO BE DELETED HAS TWO CHILDREN		
		else 
			deleteNodeWithTwoSubtrees(current,ancestors);
			
		return true;
	}
		
	private void deleteNodeWithOneSubtree(BinNode<Integer> itemToDelete, Stack<BinNode<Integer>> ancestors) {
		if(itemToDelete == root) {
			if (itemToDelete.hasLeft())
				root = itemToDelete.getLeft();
			else 
				root = itemToDelete.getRight();
		}
		else {
			BinNode<Integer> parent = ancestors.pop(); // last ancestors from stack
			if (itemToDelete == parent.getLeft()) { // itemToDelete is a left child
				if (itemToDelete.hasLeft()) // itemToDelete has a left child 
					parent.setLeft(itemToDelete.getLeft());
				else // itemToDelete has a right child 
					parent.setLeft(itemToDelete.getRight());
			}	
			else { // itemToDelete is a right child
				if (itemToDelete.hasLeft()) // itemToDelete has a left child 
					parent.setRight(itemToDelete.getLeft());
				else // itemToDelete has a right child 
					parent.setRight(itemToDelete.getRight());
			}	
		}
	}

	private void deleteLeaf(BinNode<Integer> itemToDelete, Stack<BinNode<Integer>> ancestors) {
		if(itemToDelete == root) //we have a tree with just a root 
			root = null;    //tree is empty
		else {
			BinNode<Integer> parent = ancestors.pop(); // last ancestors popped from stack
			if (itemToDelete == parent.getLeft()) // itemToDelete is a left child
				parent.setLeft(null);
			else // deletedItem is a right child
				parent.setRight(null);
		}
	}


	private void deleteNodeWithTwoSubtrees(BinNode<Integer> itemToDelete, Stack<BinNode<Integer>> ancestors) {
		BinNode<Integer> successor =  getSuccessor(itemToDelete, ancestors);
		delete(successor.getValue()); // delete the successor from the tree
		if(itemToDelete == root)
			root = successor;
		else { // connect parent of deletedItem to successor
			BinNode<Integer> parent = ancestors.pop(); // last ancestors f stack
			if (itemToDelete == parent.getLeft()) // itemToDelete is a left child
				parent.setLeft(successor);
			else // deletedItem is a right child
				parent.setRight(successor);
		}

		//connect successor to itemToDelete's left child
		successor.setLeft(itemToDelete.getLeft());
		//connect successor to itemToDelete's right child
		successor.setRight(itemToDelete.getRight());
	}
	
	//*************************************************************************************///
	
	//Homework 1 (Question 3 + 4)
	
	//Question 3
	
	private static int sum=0;
	
	/**
	 * A function that updates every node value to the sum of all greater nodes values.
	 * @param BinNode
	 */
	public void ModifyNodeToSum(BinNode<Integer> BinNode) {
		
		if (BinNode == null) {return;}
		ModifyNodeToSum(BinNode.getRight());       
		int NodeValue = (Integer) BinNode.getValue();
		BinNode.setValue(sum);
		sum = sum + NodeValue;
		ModifyNodeToSum(BinNode.getLeft());       
	}
	
	//Question 4
	
	private static BinNode<Integer> firstSwappedNode,SecondSwappedNode,previousNode;
	/**
	 * A function that fixes the BST.
	 * First it calls the findSwappedNodes function,
	 *  after that it's Swaps the values of the nodes
	 */
	public void FixingSwappedNodes() {
		
		findSwappedNodes(this.getRoot());
		int temp=(Integer) SecondSwappedNode.getValue();
		SecondSwappedNode.setValue(firstSwappedNode.getValue());
		firstSwappedNode.setValue(temp);
	}
	/**
	 * A function that finds the swapped nodes.
	 * walks Inorder through the BST with.
	 * the job of the previousNode variable is to hold the previous Node.
	 * @param current
	 */
	private void findSwappedNodes(BinNode<Integer> current) {
		
		if(current==null) {return;}
		findSwappedNodes(current.getLeft());
		if(previousNode != null && current.getValue() > previousNode.getValue()) {
			SecondSwappedNode=current;
			if(firstSwappedNode==null) {
				firstSwappedNode = previousNode;
			}
		}
		previousNode=current;
		findSwappedNodes(current.getRight());	
	}
}




