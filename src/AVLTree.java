import java.sql.Array;
import java.util.Arrays;
import java.util.Stack;

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
	public boolean empty() { //O(1)

		return this.root == null || !this.root.isRealNode();
	}

	/**
	* public String searchRec(int k, IAVLNode root)
	 *
	 * returns the info of a node with key k if exists in the tree,
	 * otherwise returns null.
	* */
	public String searchRec(int k, IAVLNode root){ //O(log(n))
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
	 * public IAVLNode searchNodeRec(int k, IAVLNode root)
	 *
	 * returns the node with key k if exists in the tree,
	 * otherwise returns null.
	 * */
	public IAVLNode searchNodeRec(int k, IAVLNode root){ //O(kog(n)
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
	 * O(log(n))
	 */
	public String search(int k)
	{
		//the case when the tree is empty
		if(this.empty())
			return null;
		return searchRec(k, this.root);
	}

	/**
	 * public IAVLNode searchNode(int k)
	 *
	 * returns the node with key k if it exists in the tree
	 * otherwise, returns null
	 * O(log(n))
	 */
	public IAVLNode searchNode(int k)
	{
		if(this.empty())
			return null;
		return searchNodeRec(k, this.root);
	}



	/**
	 * public IAVLNode minNode(IAVLNode root)
	 * @param root - the root of this tree
	 * @return the node in tree with minimal key value
	 * O(log(n))
	 */
	public IAVLNode minNode(IAVLNode root){
		while (root.getLeft().isRealNode())
			root = root.getLeft();
		return root;
	}

	/**
	 * public void switchPosition(IAVLNode x, IAVLNode y)
	 * the function puts node y in the position of node x in the tree
	 * @param x -the node you want to switch with y
	 * @param y -the node to place instead of x
	 * O(1)
	 */
	public void switchPosition(IAVLNode x, IAVLNode y){
		if(x.getParent() == null)
			this.root = y;
		else if(x.equals(x.getParent().getLeft()))
			x.getParent().setLeft(y);
		else
			x.getParent().setRight(y);
		if(y != null)
			y.setParent(x.getParent());

	}


	/**
	 * public IAVLNode returnCurrParent(IAVLNode node)
	 * @param node
	 * @return the parent node of node,
	 * if node.getParent()==null returns the root of the tree
	 * O(1)
	 */
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

	/**
	 * public void updateTillRoot(IAVLNode node)
	 * the function call update(node) to every node in the route from node to this.root
	 * @param node - the node where to start the update with
	 * O(log(n))
	 */
	public void updateTillRoot(IAVLNode node){
		while (node != null){
			update(node);
			node = node.getParent();
		}
	}


	/**
	 * public int deleteBalance(IAVLNode node)
	 * rebalacing the tree from node
	 * @param node -the node to start the rebalancing process with
	 * @return the amount of rebalance steps that was needed
	 * O(log(n))
	 */
	public int deleteBalance(IAVLNode node){
		int amountOfBalances = 0;
		while (node != null && !node.isBalanced()){
			int newBalance = node.getBeforeUpdateBalance();// check if can be changed with set balance
			if(newBalance == -2){
				if(node.getRight().getBalance() == 0){
					//IAVLNode y = leftRotate(node);
					//node = y.getRight();
					node = leftRotate(node);
					amountOfBalances+=3;
				}
				else if(node.getRight().getBalance() == -1){
					IAVLNode rightChild = node.getRight();//
					if(rightChild.getHeightDif(rightChild.getLeft()) == 2 && rightChild.getHeightDif(rightChild.getRight()) == 1){//
						node = leftRotate(node);
						amountOfBalances+=3;
					}
				}
				else{
					node = rightRotate(node.getRight());
					node = leftRotate(node.getParent());
					amountOfBalances += 6;
				}
			}
			else if(newBalance == 2){
				if(node.getLeft().getBalance() == 0){
					//IAVLNode y = rightRotate(node);
					//node = y.getLeft();
					node = rightRotate(node);
					amountOfBalances+=3;
				}
				else if(node.getLeft().getBalance() == 1){
					IAVLNode leftChild = node.getLeft();//
					if(leftChild.getHeightDif(leftChild.getLeft()) == 1 && leftChild.getHeightDif(leftChild.getRight()) == 2){//
						node = rightRotate(node);
						amountOfBalances+=3;
					}
				}
				else{
					node = leftRotate(node.getLeft());
					node = rightRotate(node.getParent());
					amountOfBalances += 6;
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

	/**
	 * public void updateMinAndMaxDelete(int k)
	 * updates min and max values after delete.
	 * @param k - the deleted key value
	 * O(log(n))
	 */
	public void updateMinAndMaxDelete(int k){
		if(k == this.min.getKey()){
			this.min = searchForMin(this.root);
		}
		if(k == this.max.getKey()){
			this.max = searchForMax(this.root);
		}
	}


	/**
	 * public IAVLNode deleteBst(IAVLNode node)
	 * the function implements a regular delete from a binary search tree
	 * @param node - the node to be deleted
	 * @return - the node from which to start the balance process if needed.
	 * O(log(n))
	 */
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
			IAVLNode succ = minNode(node.getRight()); //check is can be changed with searchmin
			parentToCheck = returnCurrParent(succ);
			if(!succ.getParent().equals(node)){
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

	/**
	 * private IAVLNode findParent(int k)
	 * @post there is no node in the tree with the key k
	 * returns the parent of the node to be inserted
	 * O(logn)
	 */
	private IAVLNode findParent(int k){ //find parent of node to be inserted
		IAVLNode x = this.getRoot();
		IAVLNode y = null;
		while (x.isRealNode()){
			y = x;
			if (x.getKey() > k)
				x = x.getLeft();
			else
				x = x.getRight();
		}
		return y;
	}

	/**
	 * private void bstInsert(IAVLNode z)
	 * @param z
	 * the function implements a regular BST insert (before re-balancing)
	 * the function updates min and max
	 * O(logn)
	 */
	private void bstInsert(IAVLNode z){ //regular insert to bst - before balancing - O(logn)
		IAVLNode y = findParent(z.getKey());
		z.setParent(y);
		if (z.getKey()<y.getKey())
			y.setLeft(z);
		else
			y.setRight(z);
		if (this.getMax() == null)
			this.setMax();
		if (this.getMin() == null)
			this.setMin();
		if (z.getKey()<this.getMin().getKey()) //check if min/max need to be updated
			this.setMin(z);
		if (z.getKey()>this.getMax().getKey())
			this.setMax(z);
	}

	/**
	 * private void setRootParent(IAVLNode xParent, IAVLNode y)
	 * the function sets the parent of a root of a sub-tree after a rotation.
	 * If necessary, updates the node as the root of the whole tree
	 * O(1)
	 */
	private void setRootParent(IAVLNode xParent, IAVLNode y) { //y is the new root, x is the old root, O(1)
		if (xParent == null) {
			this.setRoot(y);
		}
		else {
			y.setParent(xParent);
			if (y.getKey() < xParent.getKey()) {
				xParent.setLeft(y);
			} else {
				xParent.setRight(y);
			}
		}
	}

	/**
	 * private void update(IAVLNode y)
	 * updates height, size and balance of a node
	 * O(1)
	 */
	private void update(IAVLNode y){//O(1)
		y.setHeight();
		y.setSize();
		y.setBalance();
	}

	/**
	 * private IAVLNode leftRotate(IAVLNode x)
	 * @param x - root of sub-tree (before rotation)
	 * performs left rotation on given tree
	 * @return y - root of subtree after rotation
	 * O(1)
	 */
	private IAVLNode leftRotate(IAVLNode x){//gets original root of subtree, returns new root of subtree - O(1)
		IAVLNode y = x.getRight(); //y will be the new root
		IAVLNode xParent = x.getParent();
		x.setRight(y.getLeft());
		y.setLeft(x);
		//rotation has completed - update parents, size, height and balance
		setRootParent(xParent, y);
		x.getRight().setParent(x);
		y.getLeft().setParent(y);
		update(x);
		update(y);
		return y; //return new root of subtree
	}

	/**
	 * private IAVLNode rightRotate(IAVLNode x)
	 * @param x - root of sub-tree (before rotation)
	 * performs right rotation on given tree
	 * @return y - root of subtree after rotation
	 * O(1)
	 */
	private IAVLNode rightRotate(IAVLNode x){ //same implementation as leftRotate - O(1)
		IAVLNode y = x.getLeft();
		IAVLNode xParent = x.getParent();
		x.setLeft(y.getRight());
		y.setRight(x);
		setRootParent(xParent, y);
		x.getLeft().setParent(x);
		y.getRight().setParent(y);
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

	/**
	 * public int insertBalance(IAVLNode node)
	 * balances the tree whose root is node
	 * @return number of balance actions taken (promotions, demotions and rotations)
	 * O(logn)
	 */
	public int insertBalance(IAVLNode node){
		int actions =0;
		while (node != null){
			node.setBalance();
			if (Math.abs(node.getBalance())>1) { //need to rotate
				//0212 single rotation when adding from the left
				if (node.getBalance() == 2 && node.getLeft().getBalance() == 1) {
					node = rightRotate(node);
					actions += 2;
				} else if (node.getBalance() == -2 && node.getRight().getBalance() == -1) {
					node = leftRotate(node);
					actions += 2;
				}
				//0221 double rotation when adding from the left
				else if (node.getBalance() == 2 && node.getLeft().getBalance() == -1) {
					node = leftRotate(node.getLeft());
					node = rightRotate(node.getParent());
					actions += 5;
				} else if (node.getBalance() == -2 && node.getRight().getBalance() == 1) {
					node = rightRotate(node.getRight());
					node = leftRotate(node.getParent());
					actions += 5;
				} else if (node.getBalance() == 2 && node.getLeft().getBalance() == 0) {
					node = rightRotate(node);
					actions += 2;
				} else if (node.getBalance() == -2 && node.getRight().getBalance() == 0) {
					node = leftRotate(node);
					actions += 2;
				}
				updateTillRoot(node);
				return actions; //after rotation tree is balanced
			}
			else if(node.getHeight() == node.getLeft().getHeight() || node.getHeight() == node.getRight().getHeight()){//need to promote
				update(node);
				actions +=1;
				node = node.getParent();//continue loop to check if more balancing is required
			}
			else{ //heights and balance factors are legal - tree is balanced
				updateTillRoot(node);
				return actions;
			}
		}

		return actions;
	}

	/**
	 * public int insert(int k, String i)
	 *
	 * inserts an item with key k and info i to the AVL tree.
	 * the tree must remain valid (keep its invariants).
	 * returns the number of rebalancing operations, or 0 if no rebalancing operations were necessary.
	 * promotion/rotation - counted as one rebalnce operation, double-rotation is counted as 2.
	 * returns -1 if an item with key k already exists in the tree.
	 * O(logn)
	 */
	public int insert(int k, String i) { // O(logn)
		if (this.empty()){ //tree is empty
			IAVLNode node = new AVLNode(k, i);
			this.setRoot(node); //node inserted is the root
			node.setVirtualSons();
			update(node);
			this.setMin(node);
			this.setMax(node);
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
			int actions = 0; //counts # of actions taken to balance tree
			actions = insertBalance(x);
			return actions;
		}
	}
	/**
	 * private IAVLNode searchForMin(IAVLNode root)
	 * @param root -where to start the search from
	 * @return - if root is a real node- the node with the min key value in the tree,
	 * otherwise, returns root.
	 * O(logn)
	 */
	public IAVLNode searchForMin(IAVLNode root){
		if(!root.isRealNode())
			return root;
		while (root.getLeft().isRealNode()){
			root = root.getLeft();
		}
		return root;
	}

	/**
	 * private IAVLNode searchForMax(IAVLNode root)
	 * @param root -where to start the search from
	 * @return - if root is a real node- the node with the max key value in the tree,
	 * otherwise, returns root.
	 */
	public IAVLNode searchForMax(IAVLNode root){
		if(!root.isRealNode())
			return root;
		while(root.getRight().isRealNode()){
			root = root.getRight();
		}
		return root;
	}


	/**
	 * public void setToEmptyTree()
	 * sets root, max, min to null
	 */
	public void setToEmptyTree(){
		this.root = null;
		this.max = null;
		this.min = null;
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
	public int delete(int k)
	{
		int balanceOpCounter = 0;
		if(searchNode(k) == null)
			return -1;
		IAVLNode deletedNode = searchNode(k);
		IAVLNode currParent = deleteBst(deletedNode);
		if(this.empty()){
			this.setToEmptyTree();
			return 0;
		}
		int newBalance = currParent.getBeforeUpdateBalance();
		if(newBalance != 1 && newBalance != -1){
			balanceOpCounter = deleteBalance(currParent);
		}
		else {
			this.updateTillRoot(currParent);
		}
		this.updateMinAndMaxDelete(k);
		return balanceOpCounter;
	}

	/**
	 * public String min()
	 *
	 * Returns the info of the item with the smallest key in the tree,
	 * or null if the tree is empty
	 * O(1)
	 */
	public String min()
	{
		if (!this.empty())
			return this.min.getInfo();
		return null;
	}

	/**
	 * public String max()
	 *
	 * Returns the info of the item with the largest key in the tree,
	 * or null if the tree is empty
	 * O(1)
	 */
	public String max()
	{
		if (!this.empty())
			return this.max.getInfo();
		return null;
	}

	/**
	 * public int[] keysToArray()
	 *
	 * Returns a sorted array which contains all keys in the tree,
	 * or an empty array if the tree is empty.
	 * O(n)
	 */
	public int[] keysToArray()
	{
		if (this.empty())
			return new int[0];
		int[] arr = new int[this.root.getSize()]; // add empty tree case
		int i = 0;
		Stack<IAVLNode> s = new Stack<>();
		IAVLNode current = this.root;
		while (current.getKey()!=-1 || s.size()>0){
			while (current.getKey() != -1){
				s.push(current);
				current = current.getLeft();
			}
			current = s.pop();
			arr[i] = current.getKey();
			i++;
			current = current.getRight();
		}
		return arr;
	}

	/**
	 * public String[] infoToArray()
	 *
	 * Returns an array which contains all info in the tree,
	 * sorted by their respective keys,
	 * or an empty array if the tree is empty.
	 * O(n)
	 */
	public String[] infoToArray()
	{
		if (this.empty())
			return new String[0];
		String[] arr = new String[this.root.getSize()];
		int i = 0;
		Stack<IAVLNode> s = new Stack<>();
		IAVLNode current = this.root;
		while (current.getKey()!=-1 || s.size()>0){
			while (current.getKey() != -1){
				s.push(current);
				current = current.getLeft();
			}
			current = s.pop();
			arr[i] = current.getInfo();
			i++;
			current = current.getRight();
		}
		return arr;
	}

	/**
	 * public int size()
	 *
	 * Returns the number of nodes in the tree.
	 *
	 * precondition: none
	 * postcondition: none
	 * O(1)
	 */
	public int size()
	{
		if (!this.empty())
			return this.root.getSize();
		return 0;
	}

	/**
	 * public int getRoot()
	 *
	 * Returns the root AVL node, or null if the tree is empty
	 *
	 * precondition: none
	 * postcondition: none
	 * O(1)
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
	 * O(1)
	 */
	//Daniella
	public AVLTree[] split(int x)
	{
		AVLTree smaller = new AVLTree();
		AVLTree greater = new AVLTree();
		IAVLNode s = searchNode(x);
		if (s.getLeft().isRealNode()){
			smaller.setRoot(s.getLeft());
		}
		if (s.getRight().isRealNode()){
			greater.setRoot(s.getRight());
		}
		while (s.getParent()!=null) { //last iteration is at the root's son
			if (s.getParent().getKey() < s.getKey()) { //s is a right son
				IAVLNode node = new AVLNode(s.getParent().getKey(), s.getParent().getInfo());
				AVLTree t = new AVLTree();
				if (s.getParent().getLeft().isRealNode())
					t.setRoot(s.getParent().getLeft()); //t is the subtree whose root is s.parent.left
				smaller.join(node, t);
				s = s.getParent();
			}
			else { //s is a left son
				IAVLNode node = new AVLNode(s.getParent().getKey(), s.getParent().getInfo());
				AVLTree t = new AVLTree();
					if (s.getParent().getRight().isRealNode())
						t.setRoot(s.getParent().getRight()); //t is the subtree whose root is s.parent.right
				greater.join(node, t);
				s = s.getParent();
				}
		}
		//set min and max of new trees (each operation O(logn))
		smaller.setMin();
		smaller.setMax();
		greater.setMin();
		greater.setMax();

		AVLTree[] arr = new AVLTree[2];
		arr[0] = smaller;
		arr[1] = greater;

		return arr;
	}

	/**
	 * public IAVLNode getMax()
	 * @return - max node of tree
	 * O(1)
	 */
	public IAVLNode getMax(){
		return this.max;
	}

	/**
	 * public IAVLNode getMin()
	 * @return - min node of tree
	 * O(1)
	 */
	public IAVLNode getMin(){
		return this.min;
	}

	/**
	 * public void setMax(IAVLNode newMax)
	 * @param newMax - the new node with max key
	 * O(1)
	 */
	public void setMax(IAVLNode newMax){
		this.max = newMax;
	}

	/**
	 * public void setMax()
	 * sets the max of a tree when max is unknown
	 * searches for max and then sets is
	 * O(logn)
	 */
	public void setMax(){
		if (this.empty())
			return;
		IAVLNode m = this.getRoot();
		this.setMax(searchForMax(m));
	}

	/**
	 * public void setMin(IAVLNode newMin)
	 * @param newMin - the new node with the min key
	 * O(1)
	 */
	public void setMin(IAVLNode newMin){
		this.min = newMin;
	}

	/**
	 * public void setMin()
	 * sets the min of a tree when min is unknown
	 * searches for min and then sets it
	 * O(logn)
	 */
	public void setMin(){
		if (this.empty())
			return;
		IAVLNode m = this.getRoot();
		this.setMin(searchForMin(m));
	}

	/**
	 * public void setRoot(IAVLNode newRoot)
	 * sets a root to this
	 * @param newRoot - the new root
	 * O(1)
	 */

	public void setRoot(IAVLNode newRoot){
		this.root = newRoot;
		if(newRoot !=null){
			newRoot.setParent(null);
		}
	}

	/**
	 * public IAVLNode findNodeWithRankLessThen
	 * finds the first node in this with heigh/rank less then rank
	 * @param rank - the searched rank
	 * @return - the first node with heigh = rank in this
	 */
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
		//update(x.getParent());
		insertBalance(x.getParent());
	}

	public void updateThisTreeWhenThisIsShorterAndSmaller(AVLTree t, IAVLNode x){
		int smallerRank = this.getRoot().getHeight();
		IAVLNode joinNode = t.findNodeWithRankLessThenRight(smallerRank);
		t.insertForJoiningRight(x, joinNode, this.getRoot());
		update(x);
		//update(x.getParent());
		insertBalance(x.getParent());
	}

	public void updateTreeWhenRankEqual(AVLTree leftTree, AVLTree rightTree, IAVLNode x){
		x.setLeft(leftTree.getRoot());
		x.getLeft().setParent(x);
		x.setRight(rightTree.getRoot());
		x.getRight().setParent(x);
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
			this.insert(x.getKey(), x.getInfo());
			timeComplex = 1;
		}
		else if(this.empty() && !t.empty()){
			t.insert(x.getKey(), x.getInfo());
			this.root = t.getRoot();
			this.setMax(t.getMax());
			this.setMin(t.getMin());
			timeComplex = t.getRoot().getHeight() + 1;
		}
		else if(!this.empty() && t.empty()){
			this.insert(x.getKey(), x.getInfo());
			timeComplex = this.getRoot().getHeight() + 1;
		}
		else{
			int thisBeforeHeight = this.getRoot().getHeight();
			int otherBeforeHeight = t.getRoot().getHeight();
			if (t.getRoot().getKey()< this.getRoot().getKey()){
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
				this.setMin(t.getMin());
			}
			else if (t.getRoot().getKey()>this.getRoot().getKey()){
				if(t.getRoot().getHeight() > this.getRoot().getHeight()){
					t.updateThisTreeWhenThisIsHigherAndBigger(this, x);
					this.setRoot(t.getRoot());
				}
				else if(t.getRoot().getHeight() < this.getRoot().getHeight()){
					t.updateThisTreeWhenThisIsShorterAndSmaller(this, x);
					this.setRoot(t.getRoot());

				}
				else{
					this.updateTreeWhenRankEqual(this, t, x);
				}
				this.setMax(t.getMax());

			}

			timeComplex = Math.abs(thisBeforeHeight - otherBeforeHeight) + 1;

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
		public void setSize(); //sets size of subtree via children
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
			this.size = 1;
		}

		public AVLNode(int key, String info, int height, int size){
			this.key = key;
			this.info = info;
			this.height = height;
			this.size = size;
		}

		public int getKey()
		{

			return this.key;
		}
		public String getInfo()
		{

			return this.info;
		}
		public void setLeft(IAVLNode node)
		{
			this.left = node;

		}
		public IAVLNode getLeft()
		{

			return this.left;
		}
		public void setRight(IAVLNode node)
		{
			this.right = node;

		}
		public IAVLNode getRight()
		{

			return this.right;
		}
		public void setParent(IAVLNode node)
		{
			this.parent = node;

		}
		public IAVLNode getParent()
		{

			return this.parent;
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
			this.size = 1+this.left.getSize()+ this.right.getSize();
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
			this.setRight(new AVLNode(-1, null, -1, 0));
			this.setLeft(new AVLNode(-1, null, -1, 0));
			this.right.setParent(this);
			this.left.setParent(this);
		}
		public int getHeightDif(IAVLNode child){
			int diff = this.getHeight() - child.getHeight();
			return this.getHeight() - child.getHeight();
		}
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
		public boolean equals(IAVLNode y) {
			return this.getKey() == y.getKey();
		}
	}



	public static void main(String[] args){
		AVLTree t = new AVLTree();
		t.insert(3, "3");
		t.insert(2, "2");
		//t.insert(6, "6");
		t.insert(1, "1");
/*		t.insert(5, "5");
		t.insert(7, "7");
		t.insert(4, "4");
		t.insert(8, "8");
		t.insert(9, "9");
		AVLTree[] arr = t.split(9);
		System.out.println(arr[0].getRoot());*/
		/*t.insert(6,"6");
		t.insert(5, "5");
		t.insert(7, "7");
		t.insert(4, "4");
		t.insert(8, "8");*/
		AVLTree a = new AVLTree();
		a.insert(5, "5");
		a.insert(6, "6");
		a.insert(7, "7");
		AVLTree c = new AVLTree();
		c.insert(4, "4");
		IAVLNode node = c.getRoot();
		t.join(node, a);
		System.out.println(t.getRoot().getLeft().getParent().getKey());


		/*t.insert(5, "5");
		t.insert(4, "4");
		t.insert(7, "7");
		t.insert(2, "2");
		t.insert(6, "6");
		t.insert(8, "8");*/
		//System.out.println(t.getRoot().getKey());
		//System.out.println(Arrays.toString(t.keysToArray()));
		/*t.insert(18, "13");
		t.insert(19, "12");
		t.delete(30);*/
		//AVLTree[] arr = new AVLTree[2];
		/*AVLTree[] arr = t.split(8);
		System.out.println(Arrays.toString(arr[0].keysToArray()));
		System.out.println(Arrays.toString(arr[1].keysToArray()));*/


		//IAVLNode joinNode = new AVLNode(4, "info");
		//AVLTree tree = new AVLTree();
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
		/*tree.insert(7, "bb");
		tree.insert(5, "aa");
		tree.insert(10, "ss");
		tree.insert(8, "dd");*/

		/*AVLTree tree2 = new AVLTree();
		tree2.insert(2, "bb");
		tree2.insert(1, "bb");
		tree2.insert(3, "bb");
		String info = "info";*/
		//IAVLNode joinNode = new AVLNode(4, info);
		//tree.join(joinNode,tree2);
		//printPreorder(tree.root);

	}
}
  

