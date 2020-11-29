/**
 *
 * AVLTree
 *
 * An implementation of a AVL Tree with
 * distinct integer keys and info
 *
 */

public class AVLTree {
	private IAVLNode root;

	/**
	 * public boolean empty()
	 *
	 * returns true if and only if the tree is empty
	 *
	 */
	public boolean empty() {

		return this.root == null;
	}
//Meira
	public String searchRec(int k, IAVLNode root){
		if(root.getKey() == k)
			return root.getInfo();
		if(root.getKey() == -1)
			return null;

		if(k < root.getKey()){
			return searchRec(k, root.getLeft());
		}
		else {
			return searchRec(k, root.getRight());
		}

	}

	public IAVLNode searchNodeRec(int k, IAVLNode root){
		if(root.getKey() == k)
			return root;
		if(root.getKey() == -1)
			return null;

		if(k < root.getKey()){
			return searchNodeRec(k, root.getLeft());
		}
		else {
			return searchNodeRec(k, root.getRight());
		}

	}

	/**
	 * public String search(int k)
	 *
	 * returns the info of an item with key k if it exists in the tree
	 * otherwise, returns null
	 */
	public String search(int k)
	{
		//the case when the tree is empty
		if(this.empty())
			return null;
		return searchRec(k, this.root);
	}

	public IAVLNode searchNode(int k)
	{
		//the case when the tree is empty
		if(this.empty())
			return null;
		return searchNodeRec(k, this.root);
	}

	public IAVLNode minNode(IAVLNode root){
		while (root.getLeft().isRealNode())
			root = root.getLeft();
		return root;
	}
	//

	public void switchPosition(IAVLNode x, IAVLNode y){
		if(x.getParent() == null)
			this.root = y;
		else if(equals(x, x.getParent().getLeft()))
			x.getParent().setLeft(y);
		else
			x.getParent().setRight(y);
		if(y != null)
			y.setParent(x.getParent());

	}
	public IAVLNode returnCurrParent(IAVLNode node){
		if(node.getParent() == null){
			return this.root;
		}
		return node.getParent();
	}

	public int maxHeight(int h1, int h2){
		if(h1 >= h2)
			return h1;
		return h2;
	}

	public int leftRotation(IAVLNode node){
		IAVLNode rightChild = node.getRight();
		node.setRight(rightChild.getLeft());
		rightChild.getLeft().setParent(node);
		rightChild.setParent(node.getParent());
		if(node.getParent() == null)
			this.root = rightChild;
		else if(equals(node.getParent().getLeft(), node))
			node.getParent().setLeft(rightChild);
		else
			node.getParent().setRight(rightChild);
		rightChild.setLeft(node);
		node.setParent(rightChild);
		int prevNodeHeight = node.getHeight();
		int prevChildHeigh = rightChild.getHeight();
		node.update();
		rightChild.update();
		int nodeHeightDiff = Math.abs(prevNodeHeight - node.getHeight());
		int rightHeightDiff = Math.abs(prevChildHeigh - rightChild.getHeight());
		return nodeHeightDiff + rightHeightDiff;
	}

	public int rightRotation(IAVLNode node){
		IAVLNode leftChild = node.getLeft();
		node.setRight(leftChild.getRight());
		leftChild.getRight().setParent(node);
		leftChild.setParent(node.getParent());
		if(node.getParent() == null)
			this.root = leftChild;
		else if(equals(node.getParent().getLeft(), node))
			node.getParent().setRight(leftChild);
		else
			node.getParent().setLeft(leftChild);
		leftChild.setRight(node);
		node.setParent(leftChild);
		int prevNodeHeight = node.getHeight();
		int prevChildHeigh = leftChild.getHeight();
		node.update();
		leftChild.update();
		int nodeHeightDiff = Math.abs(prevNodeHeight - node.getHeight());
		int leftHeightDiff = Math.abs(prevChildHeigh - leftChild.getHeight());
		return nodeHeightDiff + leftHeightDiff;
	}







	public int balance(IAVLNode node){
		int amountOfBalances = 0;
		while (!node.isBalanced() && node != null){
			if(node.getBalance() == 2){
				if(node.getHeightDif(node.getLeft()) == 3){
					if(node.getRight().getBalance() == 0){
						amountOfBalances += leftRotation(node);
						amountOfBalances++;
					}
					if(node.getRight().getBalance() == 1){
						IAVLNode rightChild = node.getRight();
						if(rightChild.getHeightDif(rightChild.getLeft()) == 2 && rightChild.getHeightDif(rightChild.getRight()) == 1){
							amountOfBalances += leftRotation(node);
							amountOfBalances++;
						}
						else{
							amountOfBalances += rightRotation(node.getRight());
							amountOfBalances += leftRotation(node);
							amountOfBalances += 2;
						}

					}
				}
				else if(node.getHeightDif(node.getRight()) == 3){
					if(node.getRight().getBalance() == 0){
						amountOfBalances += rightRotation(node);
						amountOfBalances++;
					}
					if(node.getRight().getBalance() == 1){
						IAVLNode leftChild = node.getLeft();
						if(leftChild.getHeightDif(leftChild.getLeft()) == 1 && leftChild.getHeightDif(leftChild.getRight()) == 2){
							amountOfBalances += rightRotation(node);
							amountOfBalances++;
						}
						else{
							amountOfBalances += leftRotation(node.getLeft());
							amountOfBalances += rightRotation(node);
							amountOfBalances += 2;
						}

					}

				}
				else{
					node.update();
					amountOfBalances++;
				}
				node = node.getParent();
			}

		}
		return amountOfBalances;

/*		if(node.getBalance() == 1)
			return 0;
		if(node.getBalance() == 2){
			if(node.getHeightDif(node.getLeft()) == 3){
				amountOfBalances++;
				amountOfBalances = amountOfBalances + leftRotation(node);
			}
			else {
				amountOfBalances++;
				amountOfBalances = amountOfBalances + rightRotation(node);
			}
			return amountOfBalances;
		}*/

	}



	public IAVLNode deleteBst(IAVLNode node){

		IAVLNode parentToCheck = null;
		if(!node.getLeft().isRealNode()){
			switchPosition(node, node.getRight());
			parentToCheck = returnCurrParent(node.getRight());
		}
		else if(!node.getRight().isRealNode()){
			switchPosition(node, node.getLeft());
			parentToCheck = returnCurrParent(node.getLeft());
		}
		else{
			IAVLNode succ = minNode(node.getRight());
			parentToCheck = returnCurrParent(succ);
			if(!equals(succ.getParent(), node)){
				switchPosition(succ, succ.getRight());
				succ.setRight(node.getRight());
				succ.getRight().setParent(succ);
			}
			switchPosition(node, succ);
			succ.setLeft(node.getLeft());
			succ.getLeft().setParent(succ);
		}
		if(parentToCheck != null)
			parentToCheck.update();
		return parentToCheck;

	}

	private boolean equals(IAVLNode x, IAVLNode y) {
		return x.getKey() == y.getKey();

	}

	/**
	 * public int insert(int k, String i)
	 *
	 * inserts an item with key k and info i to the AVL tree.
	 * the tree must remain valid (keep its invariants).
	 * returns the number of rebalancing operations, or 0 if no rebalancing operations were necessary.
	 * promotion/rotation - counted as one rebalnce operation, double-rotation is counted as 2.
	 * returns -1 if an item with key k already exists in the tree.
	 */

	//Daniella

	private IAVLNode findParent(int k){ //find parent of node to be inserted
											// O(height of tree) = O(logn)
		IAVLNode x = this.root;
		IAVLNode y = null;
		while (x != null){
			y = x;
			if (x.getKey() > k)
				x = x.getLeft();
			else
				x = x.getRight();
		}
		return y;
	}

	private void bstInsert(IAVLNode z){ //regular insert to bst - before balancing
		IAVLNode y = findParent(z.getKey());
		z.setParent(y);
		if (z.getKey()<y.getKey())
			y.setLeft(z);

		else
			y.setRight(z);
	}

	private int getDifference(IAVLNode n){
		return n.getRight().getHeight()-n.getLeft().getHeight();
	}

	public int insert(int k, String i) {
		if (this.empty()){ //tree is empty
			this.root = new AVLNode(k, i); //node inserted is the root
			return 0;
		}
		else if (this.search(k) != null){ //node with key k already exists
			return -1;
		}
		else{
			IAVLNode newNode = new AVLNode(k, i); //node will be inserted as leaf with 2 virtual sons
			newNode.setLeft(new AVLNode(-1, null));
			newNode.getLeft().setHeight(-1);
			newNode.setRight(new AVLNode(-1, null));
			newNode.getRight().setHeight(-1);
			bstInsert(newNode);
			newNode.setHeight(0);
			IAVLNode y = newNode.getParent();
			IAVLNode z = newNode;
			int actions = 0; //counts # of actions taken to balance tree
			while (y.getParent()!=null){
				boolean promotedHeight = false;
				if (z.getHeight() == y.getHeight()){
					y.setHeight(y.getHeight()+1); //promote y's height
					promotedHeight = true;
					actions+=1;
				}
				if (Math.abs(getDifference(y))<2){
					if (!promotedHeight) { //difference is valid and no need to promote ancestors
						return actions;
					}
					else{
						z = y;
						y = y.getParent(); //continue loop to check if more promotions are necessary
					}
				}
				else{

				}
			}

		}

	}


	/**
	 * public int delete(int k)
	 *
	 * deletes an item with key k from the binary tree, if it is there;
	 * the tree must remain valid (keep its invariants).
	 * returns the number of rebalancing operations, or 0 if no rebalancing operations were needed.
	 * demotion/rotation - counted as one rebalnce operation, double-rotation is counted as 2.
	 * returns -1 if an item with key k was not found in the tree.
	 */
	//Meirav
	public int delete(int k)
	{
		int balanceOpCounter = 0;
		if(searchNode(k) == null)
			return -1;
		IAVLNode deletedNode = searchNode(k);
		IAVLNode currParent = deleteBst(deletedNode);
		if(currParent.isBalanced()){
			if(currParent.getParent() == null)
				return 0;
			if(currParent.getParent().isBalanced())
				return 0;
			else
				balanceOpCounter = balance(currParent.getParent());
		}
		else
			balanceOpCounter = balance(currParent);
		return balanceOpCounter;
	}

	/**
	 * public String min()
	 *
	 * Returns the info of the item with the smallest key in the tree,
	 * or null if the tree is empty
	 */
	//Daniella
	public String min()
	{
		return "42"; // to be replaced by student code
	}

	/**
	 * public String max()
	 *
	 * Returns the info of the item with the largest key in the tree,
	 * or null if the tree is empty
	 */
	//Meirav
	public String max()
	{
		return "42"; // to be replaced by student code
	}

	/**
	 * public int[] keysToArray()
	 *
	 * Returns a sorted array which contains all keys in the tree,
	 * or an empty array if the tree is empty.
	 */
	//Meirav
	public int[] keysToArray()
	{
		int[] arr = new int[42]; // to be replaced by student code
		return arr;              // to be replaced by student code
	}

	/**
	 * public String[] infoToArray()
	 *
	 * Returns an array which contains all info in the tree,
	 * sorted by their respective keys,
	 * or an empty array if the tree is empty.
	 */
	//Daniella
	public String[] infoToArray()
	{
		String[] arr = new String[42]; // to be replaced by student code
		return arr;                    // to be replaced by student code
	}

	/**
	 * public int size()
	 *
	 * Returns the number of nodes in the tree.
	 *
	 * precondition: none
	 * postcondition: none
	 */
	//meirav
	public int size()
	{
		return 42; // to be replaced by student code
	}

	/**
	 * public int getRoot()
	 *
	 * Returns the root AVL node, or null if the tree is empty
	 *
	 * precondition: none
	 * postcondition: none
	 */
	public IAVLNode getRoot()
	{
		return null;
	}
	/**
	 * public string split(int x)
	 *
	 * splits the tree into 2 trees according to the key x.
	 * Returns an array [t1, t2] with two AVL trees. keys(t1) < x < keys(t2).
	 * precondition: search(x) != null (i.e. you can also assume that the tree is not empty)
	 * postcondition: none
	 */
	//Daniella
	public AVLTree[] split(int x)
	{
		return null;
	}
	/**
	 * public join(IAVLNode x, AVLTree t)
	 *
	 * joins t and x with the tree.
	 * Returns the complexity of the operation (|tree.rank - t.rank| + 1).
	 * precondition: keys(x,t) < keys() or keys(x,t) > keys(). t/tree might be empty (rank = -1).
	 * postcondition: none
	 */
	//Meirav
	public int join(IAVLNode x, AVLTree t)
	{
		return 0;
	}

	/**
	 * public interface IAVLNode
	 * ! Do not delete or modify this - otherwise all tests will fail !
	 */
	public interface IAVLNode{
		public int getKey(); //returns node's key (for virtuval node return -1)
		public String getInfo(); //returns node's value [info] (for virtuval node return null)
		public void setLeft(IAVLNode node); //sets left child
		public IAVLNode getLeft(); //returns left child (if there is no left child return null)
		public void setRight(IAVLNode node); //sets right child
		public IAVLNode getRight(); //returns right child (if there is no right child return null)
		public void setParent(IAVLNode node); //sets parent
		public IAVLNode getParent(); //returns the parent (if there is no parent return null)
		public boolean isRealNode(); // Returns True if this is a non-virtual AVL node
		public void setHeight(int height); // sets the height of the node
		public int getHeight(); // Returns the height of the node (-1 for virtual nodes)
		public void update();
		public int getBalance();
		public void demote();
		public void promote();
		public int getHeightDif(IAVLNode child);
		public boolean isBalanced();

	}

	/**
	 * public class AVLNode
	 *
	 * If you wish to implement classes other than AVLTree
	 * (for example AVLNode), do it in this file, not in
	 * another file.
	 * This class can and must be modified.
	 * (It must implement IAVLNode)
	 */
	public class AVLNode implements IAVLNode{
		private int key;
		private String info;
		private IAVLNode left;
		private IAVLNode right;
		private IAVLNode parent;
		private int height;

		public AVLNode(int key, String info){
			this.key = key;
			this.info = info;
		}

		public int getKey()
		{

			return this.key;
		}
		public String getInfo()
		{

			return this.info; // to be replaced by student code
		}
		public void setLeft(IAVLNode node)
		{
			this.left = node;

		}
		public IAVLNode getLeft()
		{

			return this.left; // to be replaced by student code
		}
		public void setRight(IAVLNode node)
		{
			this.right = node;

		}
		public IAVLNode getRight()
		{

			return this.right; // to be replaced by student code
		}
		public void setParent(IAVLNode node)
		{
			this.parent = node;

		}
		public IAVLNode getParent()
		{

			return this.parent; // to be replaced by student code
		}
		// Returns True if this is a non-virtual AVL node
		public boolean isRealNode()
		{
			if(this.key != -1)
				return true;
			return false;
		}
		public void setHeight(int height)
		{
			this.height = height;
		}
		public int getHeight()
		{
			return this.height;
		}
		public void update(){
			int newHeight;
			if(this.getLeft().getHeight() >= this.getRight().getHeight())
				newHeight = this.getLeft().getHeight();
			else
				newHeight = this.getRight().getHeight();
			this.setHeight(newHeight);

		};
		public int getBalance(){
			if(this == null || this.isRealNode())
				return 0;
			return Math.abs(this.getLeft().getHeight() - this.getRight().getHeight());
		}
		public void demote(){
			this.height --;

		};
		public void promote(){

		};
		public int getHeightDif(IAVLNode child){
			return this.getHeight() - child.getHeight();
		};
		public boolean isBalanced(){
			if(this.getBalance() != 0 && this.getBalance() != 1 &&this.getBalance() != -1)
				return false;
			if(this.getHeightDif(this.getLeft()) == 2 && this.getHeightDif(this.getRight()) == 2)
				return false;
			return true;
		};
	}

}
  

