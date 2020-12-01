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
	private IAVLNode min;
	private IAVLNode max;

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

	/**
	 * public String search(int k)
	 *
	 * returns the info of an item with key k if it exists in the tree
	 * otherwise, returns null
	 */
	public String search(int k)
	{

		if (this.empty()){
			return null;
		}
		return searchRec(k, this.root);
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
		if (z.getKey()<this.min.getKey()) //check if min/max need to be updated
			this.min = z;
		if (z.getKey()>this.max.getKey())
			this.max = z;
	}

	private void setRootParent(IAVLNode xParent, IAVLNode y) { //y is the new root, x is the old root
		if (xParent == null) {
			this.root = y;
			y.setParent(null);
		} else {
			if (y.getKey() < xParent.getKey()) {
				xParent.setLeft(y);
			} else {
				xParent.setRight(y);
			}
		}
	}

	private void update(IAVLNode y){
		y.setHeight();
		y.setSize();
		y.setBalance();
	}

	private IAVLNode leftRotate(IAVLNode x){//gets original root of subtree, returns new root of subtree
		IAVLNode y = x.getRight(); //y will be the new root
		IAVLNode xParent = x.getParent();
		x.setRight(y.getLeft());
		y.setLeft(x);
		//rotation has completed - update parents, size, height and balance
		setRootParent(xParent, y);
		update(x);
		update(y);
		return y; //return new root of subtree
	}

	private IAVLNode rightRotate(IAVLNode x){ //same implementation as leftRotate
		IAVLNode y = x.getLeft();
		IAVLNode xParent = x.getParent();
		x.setLeft(y.getRight());
		y.setRight(x);
		setRootParent(xParent, y);
		update(x);
		update(y);
		return y;
	}

	public int insert(int k, String i) {
		if (this.empty()){ //tree is empty
			IAVLNode node = new AVLNode(k, i);
			this.root = node; //node inserted is the root
			node.setVirtualSons();
			this.min = node;
			this.max = node;
			return 0;
		}
		else if (this.search(k) != null){ //node with key k already exists
			return -1;
		}
		else{
			IAVLNode newNode = new AVLNode(k, i);
			newNode.setVirtualSons(); //node will be inserted as leaf with 2 virtual sons
			bstInsert(newNode); //regular BST insert
			//insertion has completed - start balancing
			IAVLNode x = newNode.getParent();
			IAVLNode y = newNode;
			IAVLNode z = newNode.getLeft();
			int actions = 0; //counts # of actions taken to balance tree
			while (x!=null){
				x.setBalance();
				if (Math.abs(x.getBalance())>1){ //need to rotate
					if (x.getBalance()>1 && z.getKey()>y.getKey()){ //LR
						x.setLeft(leftRotate(x.getLeft()));
						x = rightRotate(x);
						actions +=5; //two rotations + 3 promotions/demotions
					}
					if (x.getBalance()>1 && z.getKey()<y.getKey()){ //LL
						x = rightRotate(x);
						actions += 3; // one rotations + 2 promotions/demotions
					}
					if (x.getBalance()<-1 && z.getKey()<y.getKey()){ //RL
						x.setRight(rightRotate(x.getRight()));
						x = leftRotate(x);
						actions += 5;
					}
					if (x.getBalance()<-1 && z.getKey()>y.getKey()){ //RR
						x = leftRotate(x);
						actions +=3;
					}
					return actions; //once rotation has occurred tree is balanced
				}
				else if (x.getHeight() == y.getHeight()){
					x.setHeight(); //promotes x's height
					actions +=1;
					z = y;
					y = x;
					x = x.getParent(); //continue loop to check if more balancing is required
				}
				else{ //heights and balance factors are legal - tree is balanced
					return actions;
				}
			}
			return actions;
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
		return 42;	// to be replaced by student code
	}

	/**
	 * public String min()
	 *
	 * Returns the info of the item with the smallest key in the tree,
	 * or null if the tree is empty
	 */
	//Daniella
	public String min() //O(1)
	{
		return this.min.getInfo();
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
		return this.root;
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
		public int getKey(); //returns node's key (for virtual node return -1)
		public String getInfo(); //returns node's value [info] (for virtual node return null)
		public void setLeft(IAVLNode node); //sets left child
		public IAVLNode getLeft(); //returns left child (if there is no left child return null)
		public void setRight(IAVLNode node); //sets right child
		public IAVLNode getRight(); //returns right child (if there is no right child return null)
		public void setParent(IAVLNode node); //sets parent
		public IAVLNode getParent(); //returns the parent (if there is no parent return null)
		public boolean isRealNode(); // Returns True if this is a non-virtual AVL node
		public void setHeight(int height); // sets the height of the node
		public void setHeight(); //set height via children
		public int getHeight(); // Returns the height of the node (-1 for virtual nodes)
		public void setSize(int size); //sets the size of the subtree node is the root of
		public void setSize(); //sets size of subtree vis children
		public int getSize(); //Returns the size of the subtree node is the root of
		public void setBalance(int balance); //sets balance factor of node
		public void setBalance(); //sets balance factor via children
		public int getBalance(); //return balance factor of node
		public void setVirtualSons(); //sets two virtual sons for new leaf node
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
		private int balance;
		private int size;

		public AVLNode(int key, String info){
			this.key = key;
			this.info = info;
		}

		public AVLNode(int key, String info, int height){
			this.key = key;
			this.info = info;
			this.height = height;
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
			this.left.setParent(this);

		}
		public IAVLNode getLeft()
		{

			return this.left;
		}
		public void setRight(IAVLNode node)
		{
			this.right = node;
			this.right.setParent(this);

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

		public void setHeight(){
			this.height = Math.max(this.left.getHeight(), this.right.getHeight())+1;
		}

		public int getHeight()
		{
			return this.height;
		}
		public void setSize(int size){
			this.size = size;
		}

		public void setSize(){
			this.size = Math.max(this.left.getSize(), this.right.getSize())+1;
		}

		public int getSize(){
			return this.size;
		}
		public void setBalance(){
			this.balance = this.left.getHeight()-this.right.getHeight();
		}

		public void setBalance(int b){
			this.balance = b;
		}

		public int getBalance(){
			return this.balance;
		}

		public void setVirtualSons(){
			this.right = new AVLNode(-1, null, -1);
			this.left = new AVLNode(-1, null, -1);
			this.right.setParent(this);
			this.left.setParent(this);
		}
	}

}
  

