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

	public void updateTillRoot(IAVLNode node){
		while (node != null){
			update(node);
			node = node.getParent();
		}
	}


	public int balanceWithNewBalance(IAVLNode node){
		int amountOfBalances = 0;
		while (node != null && !node.isBalanced()){
			int newBalance = node.getBeforeUpdateBalance();
			if(newBalance == -2){
				if(node.getRight().getBalance() == 0){
					IAVLNode y = leftRotate(node);
					node = y.getRight();
					amountOfBalances+=3;
				}
				else if(node.getRight().getBalance() == -1){
					IAVLNode rightChild = node.getRight();
					if(rightChild.getHeightDif(rightChild.getLeft()) == 2 && rightChild.getHeightDif(rightChild.getRight()) == 1){
						node = leftRotate(node);
						amountOfBalances+=3;
					}
					else{
						node = rightRotate(node.getRight());
						node = leftRotate(node.getParent());
						amountOfBalances += 6;
					}

				}
			}
			else if(newBalance == 2){
				if(node.getRight().getBalance() == 0){
					IAVLNode y = rightRotate(node);
					node = y.getLeft();
					amountOfBalances+=3;
				}
				if(node.getRight().getBalance() == 1){
					IAVLNode leftChild = node.getLeft();
					if(leftChild.getHeightDif(leftChild.getLeft()) == 1 && leftChild.getHeightDif(leftChild.getRight()) == 2){
						node = rightRotate(node);
						amountOfBalances+=3;
					}
					else{
						node = leftRotate(node.getRight());
						node = rightRotate(node.getParent());
						amountOfBalances += 6;
					}

				}

			}
			else{
				update(node);
				amountOfBalances++;
			}
			node = node.getParent();
		}

		updateTillRoot(node);
		return amountOfBalances;

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
			else{
				parentToCheck = succ;
			}
			switchPosition(node, succ);
			succ.setLeft(node.getLeft());
			succ.getLeft().setParent(succ);
			update(succ);
		}

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
		while (x.getKey() != -1){ //while x is a real node
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

	private void setRootParent(IAVLNode xParent, IAVLNode y) { //y is the new root, x is the old root
		if (xParent == null) {
			this.root = y;
			y.setParent(xParent);
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


	static void printInorder(IAVLNode node)
	{
		if (node.getKey() == -1)
			return;

		/* first recur on left child */
		printInorder(node.getLeft());

		/* then print the data of node */
		System.out.print(node.getKey() + " ");

		/* now recur on right child */
		printInorder(node.getRight());
	}

	static void printPreorder(IAVLNode node)
	{
		if (node.getKey() == -1)
			return;

		/* first print data of node */
		System.out.print(node.getKey() + " ");

		/* then recur on left sutree */
		printPreorder(node.getLeft());

		/* now recur on right subtree */
		printPreorder(node.getRight());
	}

	public int insert(int k, String i) {
		if (this.empty()){ //tree is empty
			this.root = new AVLNode(k, i); //node inserted is the root
			this.root.setVirtualSons();
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

	private int getDifference(IAVLNode n){
		return n.getRight().getHeight()-n.getLeft().getHeight();
	}

	private IAVLNode searchForMin(IAVLNode root){
		while (root != null){
			root = root.getLeft();
		}
		return root;
	}

	private IAVLNode searchForMax(IAVLNode root){
		while(root != null){
			root = root.getRight();
		}
		return root;
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
		int newBalance = currParent.getBeforeUpdateBalance();

		if(newBalance == 1 || newBalance == -1)
			return balanceOpCounter;
		else {
			balanceOpCounter = balanceWithNewBalance(currParent);
		}
		if(k == this.min.getKey()){
			this.min = searchForMin(this.root);
		}
		if(k == this.max.getKey()){
			this.max = searchForMax(this.root);
		}

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
		return this.max.getInfo();
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
		if(this.empty())
			return new int[0];
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
		return this.size();
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


	public void insertBalance(IAVLNode node){

	}

	public IAVLNode getMax(){
		return this.max;
	}

	public IAVLNode getMin(){
		return this.min;
	}

	public void setRoot(IAVLNode newRoot){
		this.root = newRoot;
		newRoot.setParent(null);
	}

	public IAVLNode findNodeWithRankLessThen(int rank){
		IAVLNode node = this.getRoot();
		while (node.getHeight() > rank){
			node = node.getLeft();
		}
		return node;
	}

	public IAVLNode findNodeWithRankLessThenRight(int rank){
		IAVLNode node = this.getRoot();
		while (node.getHeight() > rank){
			node = node.getRight();
		}
		return node;
	}

	public void insertXForJoining(IAVLNode x, IAVLNode joinNode, IAVLNode otherRoot){
		x.setLeft(otherRoot);
		x.setRight(joinNode);
		joinNode.getParent().setLeft(x);
		x.setParent(joinNode.getParent());
		otherRoot.setParent(x);
		joinNode.setParent(x);

	}

	public void insertForJoiningRight(IAVLNode x, IAVLNode joinNode, IAVLNode otherRoot){
		x.setRight(otherRoot);
		x.setLeft(joinNode);
		joinNode.getParent().setRight(x);
		x.setParent(joinNode.getParent());
		otherRoot.setParent(x);
		joinNode.setParent(x);
	}

	public void updateThisTreeWhenThisIsHigherAndBigger(AVLTree t, IAVLNode x){
		int smallerRank = t.getRoot().getHeight();
		IAVLNode joinNode = this.findNodeWithRankLessThen(smallerRank);
		this.insertXForJoining(x, joinNode, t.getRoot());
		update(x);
		update(x.getParent());
		insertBalance(x);
	}

	public void updateThisTreeWhenThisIsShorterAndSmaller(AVLTree t, IAVLNode x){
		int smallerRank = this.getRoot().getHeight();
		IAVLNode joinNode = t.findNodeWithRankLessThenRight(smallerRank);
		t.insertForJoiningRight(x, joinNode, this.getRoot());
		update(x);
		update(x.getParent());
		insertBalance(x);
	}

	public void updateTreeWhenRankEqual(AVLTree leftTree, AVLTree rightTree, IAVLNode x){
		x.setLeft(leftTree.getRoot());
		x.setRight(rightTree.getRoot());
		this.setRoot(x);
		update(x);
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
		int timeComplex = 0;
		if(this.empty() && t.empty()){
			this.root = x;
			timeComplex = 1;
		}

		else if(this.empty() && !t.empty()){
			t.insert(x.getKey(), x.getInfo());
			this.root = t.getRoot();
			timeComplex = t.getRoot().getHeight() + 1;
		}
		else if(!this.empty() && t.empty()){
			this.insert(x.getKey(), x.getInfo());
			timeComplex = this.getRoot().getHeight() + 1;
		}
		else{
			if(t.getMax().getKey() < this.getMin().getKey()){
				if(t.getRoot().getHeight() < this.getRoot().getHeight()){
					this.updateThisTreeWhenThisIsHigherAndBigger(t, x);
				}
				else if(t.getRoot().getHeight() > this.getRoot().getHeight()){
					this.updateThisTreeWhenThisIsShorterAndSmaller(t,x);
					this.setRoot(t.getRoot());
				}
				else {
					this.updateTreeWhenRankEqual(t, this, x);
				}
			}
			else if(t.getMin().getKey() > this.getMax().getKey()){
				if(t.getRoot().getHeight() > this.getRoot().getHeight()){
					t.updateThisTreeWhenThisIsHigherAndBigger(this, x);
					this.setRoot(t.getRoot());
				}
				else if(t.getRoot().getHeight() < this.getRoot().getHeight()){
					t.updateThisTreeWhenThisIsShorterAndSmaller(this, x);

				}
				else{
					this.updateTreeWhenRankEqual(this, t, x);
				}

			}

			timeComplex = Math.abs(this.root.getHeight() - t.getRoot().getHeight()) + 1;

		}
		return timeComplex;
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
		public int getHeightDif(IAVLNode child);
		public boolean isBalanced();
		public int getBeforeUpdateBalance();
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
		public int getHeightDif(IAVLNode child){
			int diff = this.getHeight() - child.getHeight();
			return this.getHeight() - child.getHeight();
		};
		public boolean isBalanced(){
			if(this == null || !this.isRealNode())
				return true;
			if(this.getBalance() != 0 && this.getBalance() != 1 &&this.getBalance() != -1)
				return false;
			if(this.getHeightDif(this.getLeft()) == 2 && this.getHeightDif(this.getRight()) == 2)
				return false;
			if(this.getHeightDif(this.getLeft()) == 3 || this.getHeightDif(this.getRight()) == 3)
				return false;
			return true;
		};

		public int getBeforeUpdateBalance(){
			return this.getLeft().getHeight() - this.getRight().getHeight();
		};
	}


	public static void main(String[] args){
		//IAVLNode joinNode = new AVLNode(4, "info");
		AVLTree tree = new AVLTree();
		/*
		tree.insert(1, "bb");
		tree.insert(2, "cc");
		//tree.insert(4, "rr");
		tree.insert(5, "tt");
		tree.insert(6, "bgtrb");
		tree.insert(7, "bgtrb");
		tree.insert(8, "bgtrb");
		tree.insert(9, "bb");
		//tree.insert(10, "rg");
		tree.insert(11, "grgg");
		tree.insert(3, "grgg");*/
		tree.insert(7, "bb");
		tree.insert(5, "aa");
		tree.insert(10, "ss");
		tree.insert(8, "dd");

		AVLTree tree2 = new AVLTree();
		tree2.insert(2, "bb");
		tree2.insert(1, "bb");
		tree2.insert(3, "bb");
		String info = "info";
		//IAVLNode joinNode = new AVLNode(4, info);
		//tree.join(joinNode,tree2);
		printPreorder(tree.root);

	}
}
  

