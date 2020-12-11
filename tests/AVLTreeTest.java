//import org.junit.Test;
import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.

import static org.junit.jupiter.api.Assertions.*;

class AVLTreeTest extends AVLTree {

    @Test
    public void insertTest(){
        AVLTree inserFirstTest = new AVLTree();
        assertEquals(0, inserFirstTest.insert(10, "info10"), "no balance is needed");
        assertEquals(1, inserFirstTest.insert(20, "info20"), "promote 10");
        assertEquals(6, inserFirstTest.insert(15, "info15"), "double rotation with demotion of 20,10 and promotion of 15");//5
        assertEquals(2, inserFirstTest.insert(25, "info25"), "promote 20 and 15");
        assertEquals(3, inserFirstTest.insert(30, "info30"), "promote 25 single left rotation of 20 and demote of 20");//3
        assertEquals(7, inserFirstTest.insert(16, "info16"), "promote and then double rotation");
        assertEquals(3, inserFirstTest.insert(18, "info18"), "promote 18, 16, 20");//1
        assertEquals(3, inserFirstTest.insert(19, "info19"), "promote 18 left rotate of 16 demote 16");//2
        assertEquals(30, inserFirstTest.getMax().getKey());
        assertEquals(10, inserFirstTest.getMin().getKey());

        //test insert flow with right rotations, left rotations and 2 RL double rotations
        AVLTree insertFlowSecond = new AVLTree();
        assertEquals(0, insertFlowSecond.insert(14, "info10"), "no balance is needed when inserting the first node in the root");
        assertEquals(1, insertFlowSecond.insert(17, "info20"), "promote 14 after inserting 17");
        assertEquals(0, insertFlowSecond.insert(11, "info15"), "no balance is needed");//5
        assertEquals(2, insertFlowSecond.insert(7, "info25"), "promote 14, promote 14");//1
        assertEquals(1, insertFlowSecond.insert(53, "info30"), "promote 17");//4
        assertEquals(3, insertFlowSecond.insert(4, "info16"), "promote and then double rotation");
        assertEquals(3, insertFlowSecond.insert(13, "info18"), "promote 11, 7, 14");//1
        assertEquals(6, insertFlowSecond.insert(12, "info19"), "two promotions of 15 and 10");//2
        assertEquals(7, insertFlowSecond.insert(8, "info19"), "two promotions of 15 and 10");//2
        assertEquals(4, insertFlowSecond.getMin().getKey());
        assertEquals(53, insertFlowSecond.getMax().getKey());

        //LR rotation
        AVLTree inserLRCase = new AVLTree();
        assertEquals(0, inserLRCase.insert(10, "info10"), "no balance is needed when inserting the first node in the root");
        assertEquals(1, inserLRCase.insert(5, "info20"), "promote 10 after inserting 17");
        assertEquals(0, inserLRCase.insert(20, "info15"), "no balance is needed");
        assertEquals(2, inserLRCase.insert(4, "info25"), "promote 5, promote 10");
        assertEquals(0, inserLRCase.insert(8, "info30"), "no promotion is needed");//4
        assertEquals(1, inserLRCase.insert(25, "info16"), "promote and then double rotation");
        assertEquals(3, inserLRCase.insert(3, "info18"), "promote 4, 5, 10");//1
        assertEquals(1, inserLRCase.insert(7, "info19"), "promote 8, 5, 10");//2
        assertEquals(0, inserLRCase.insert(9, "info19"), "two promotions of 15 and 10");//2
        assertEquals(8, inserLRCase.insert(6, "info19"), "LR rotation");//2
        assertEquals(3, inserLRCase.getMin().getKey());
        assertEquals(25, inserLRCase.getMax().getKey());

        //RL
        AVLTree inserRLCase = new AVLTree();
        inserRLCase.insert(8, "info8");
        inserRLCase.insert(10, "info10");
        assertEquals(6, inserRLCase.insert(9, "info9"));
        assertEquals(8, inserRLCase.getMin().getKey());
        assertEquals(10, inserRLCase.getMax().getKey());

        //insert after deleting all nodes in the tree
        AVLTree inserAndDelete = new AVLTree();
        inserAndDelete.insert(8, "info8");
        inserAndDelete.delete(8);
        assertEquals(0, inserAndDelete.insert(9, "info9"));
        assertEquals(9, inserAndDelete.getMin().getKey());
        assertEquals(9, inserAndDelete.getMax().getKey());

    }

    @Test
    public void deleteTest(){
        AVLTree deleteFirstTest = new AVLTree();
        deleteFirstTest.insert(10, "info10");
        deleteFirstTest.insert(20, "info20");
        deleteFirstTest.insert(15, "info15");
        deleteFirstTest.insert(25, "info25");
        deleteFirstTest.insert(30, "info30");
        deleteFirstTest.insert(16, "info16");
        deleteFirstTest.insert(18, "info18");
        deleteFirstTest.insert(19, "info19");
        //double rotation- rotate 15 left promote 18 demote 15 demote twice 20. 6 actions in total.
        assertEquals(7, deleteFirstTest.delete(30), "double rotation 20 demoted twice 18 promoted and 15 demoted");
        assertEquals(-1, deleteFirstTest.delete(30), "30 doesn't exist in the tree therefore should return -1");
        //case of deleting a binary node, no balance is needed, therefore 0.
        assertEquals(0, deleteFirstTest.delete(20), "20 is a binary node. after deletion should be replaced by 25 with no balance steps at all");
        //case of deleting a leaf, no balance is needed.
        assertEquals(1, deleteFirstTest.delete(19), "removing 19 from the tree doesn't break the balance");
        //deleting the root- single right rotate of 25.
        assertEquals(3, deleteFirstTest.delete(18), "single right rotation, demote 25 promote 15");
        //promote 15 after deleting 25 and replacing it with 16
        assertEquals(1, deleteFirstTest.delete(25), "30 doesn't exist in the tree therefore should return -1");
        //no balance is needed
        assertEquals(0, deleteFirstTest.delete(10), "30 doesn't exist in the tree therefore should return -1");
        //promote 15
        assertEquals(1, deleteFirstTest.delete(16), "30 doesn't exist in the tree therefore should return -1");
        //deleting the last node in the tree, no balance is needed.
        assertEquals(0, deleteFirstTest.delete(15), "30 doesn't exist in the tree therefore should return -1");
        assertTrue(deleteFirstTest.empty(), "isEmpty return false after deleting all nodes in the tree.");
        assertEquals(-1, deleteFirstTest.delete(15), "the tree is empty therefore suppose to return -1");

        //single right rotate test with balance 0 of left child
        AVLTree deleteSingleRightRotate = new AVLTree();
        deleteSingleRightRotate.insert(20, "info20");
        deleteSingleRightRotate.insert(10, "info10");
        deleteSingleRightRotate.insert(30, "info30");
        deleteSingleRightRotate.insert(5, "info5");
        deleteSingleRightRotate.insert(15, "info15");
        assertEquals(3, deleteSingleRightRotate.delete(30), "right rotation promote 10 demote 20");
        assertEquals(20, deleteSingleRightRotate.getMax().getKey());
        assertEquals(5, deleteSingleRightRotate.getMin().getKey());


        //single right rotate test with balance 1 of left child
        AVLTree deleteSingleRightRotateWithBalance1 = new AVLTree();
        deleteSingleRightRotateWithBalance1.insert(50, "info20");
        deleteSingleRightRotateWithBalance1.insert(40, "info10");
        deleteSingleRightRotateWithBalance1.insert(60, "info30");
        deleteSingleRightRotateWithBalance1.insert(30, "info5");
        deleteSingleRightRotateWithBalance1.insert(55, "info15");
        deleteSingleRightRotateWithBalance1.insert(45, "4");
        deleteSingleRightRotateWithBalance1.insert(10, "23");
        assertEquals(4, deleteSingleRightRotateWithBalance1.delete(55), "demote 50 twice");
        assertEquals(60, deleteSingleRightRotateWithBalance1.getMax().getKey());
        assertEquals(10, deleteSingleRightRotateWithBalance1.getMin().getKey());

        //double rotation LR
        AVLTree deleteLR = new AVLTree();
        deleteLR.insert(50, "info20");
        deleteLR.insert(40, "info10");
        deleteLR.insert(60, "info30");
        deleteLR.insert(45, "info5");
        assertEquals(6, deleteLR.delete(60), "LR rotation promote 45 demote 40 and demote twice 50");
        assertEquals(50, deleteLR.getMax().getKey());
        assertEquals(40, deleteLR.getMin().getKey());

        //another single right rotation but with higher tree
        AVLTree deleteR = new AVLTree();
        deleteR.insert(10, "info20");
        deleteR.insert(7, "info10");
        deleteR.insert(20, "info30");
        deleteR.insert(5, "info5");
        deleteR.insert(8, "info5");
        deleteR.insert(25, "info5");
        deleteR.insert(4, "info5");
        deleteR.insert(6, "info5");
        assertEquals(3, deleteR.delete(8), "Right rotate of 7 demote 7 promote 5");
        assertEquals(50, deleteLR.getMax().getKey());
        assertEquals(40, deleteLR.getMin().getKey());

        //RL rotation and R rotation
        AVLTree deleteRLandRight = new AVLTree();
        deleteRLandRight.insert(16, "info20");
        deleteRLandRight.insert(12, "info10");
        deleteRLandRight.insert(20, "info30");
        deleteRLandRight.insert(7, "info5");
        deleteRLandRight.insert(13, "info5");
        deleteRLandRight.insert(17, "info5");
        deleteRLandRight.insert(25, "info5");
        deleteRLandRight.insert(5, "inf44o5");
        deleteRLandRight.insert(9, "info5555");
        deleteRLandRight.insert(14, "66");
        deleteRLandRight.insert(22, "55");
        deleteRLandRight.insert(2, "44");
        assertEquals(9, deleteRLandRight.delete(17), "RL rotate of 25, and right rotate of 16");
        assertEquals(0, deleteRLandRight.delete(12), "no balances is needed here");
        assertEquals(3, deleteRLandRight.delete(14), "no balances is needed here");
        assertEquals(6, deleteRLandRight.delete(22), "no balances is needed here");
        assertEquals(0, deleteRLandRight.delete(13), "no balances is needed here");
        assertEquals(4, deleteRLandRight.delete(9), "no balances is needed here");
        assertEquals(0, deleteRLandRight.delete(16), "no balances is needed here");
        assertEquals(3, deleteRLandRight.delete(25), "no balances is needed here");
        assertEquals(6, deleteRLandRight.delete(2), "no balances is needed here");
        assertEquals(0, deleteRLandRight.delete(7), "no balances is needed here");
        assertEquals(1, deleteRLandRight.delete(5), "no balances is needed here");
        assertEquals(0, deleteRLandRight.delete(20), "no balances is needed here");
        assertEquals(-1, deleteRLandRight.delete(20), "no balances is needed here");


        //delete from empty tree that was emptied by delete
        AVLTree onlyRoot = new AVLTree();
        onlyRoot.insert(2, "info2");
        onlyRoot.delete(2);
        assertEquals(-1, onlyRoot.delete(2));

    }

    @Test
    public void joinTest(){
        AVLTree emptyTree1 = new AVLTree();
        AVLTree emptyTree2 = new AVLTree();
        IAVLNode joinNode = new AVLNode(4, "info");
        AVLTree tree = new AVLTree();
        tree.insert(7, "info1");
        tree.insert(5, "info2");
        tree.insert(10, "info3");
        tree.insert(8, "info4");

        AVLTree tree2 = new AVLTree();
        tree2.insert(2, "info2");
        tree2.insert(1, "info1");
        tree2.insert(3, "info3");

        AVLTree tree3 = new AVLTree();
        tree3.insert(15, "info15");
        tree3.insert(14, "info14");
        tree3.insert(17, "info17");
        IAVLNode joinNode2 = new AVLNode(12, "info12");


        // testing special case with balance 0 and balance 2 of the parent
        AVLTree tree4 = new AVLTree();
        tree4.insert(7, "info7");
        tree4.insert(6, "info6");
        tree4.insert(10, "info10");
        tree4.insert(5, "info5");
        AVLTree tree5 = new AVLTree();
        tree5.insert(2, "info2");
        tree5.insert(1, "info1");
        tree5.insert(3, "info3");
        IAVLNode joinNode3 = new AVLNode(4, "INFO 4");

        //testing the case of double rotation + special case
        AVLTree tree6 = new AVLTree();
        tree6.insert(7, "info7");
        tree6.insert(6, "info6");
        tree6.insert(10, "info10");
        tree6.insert(5, "info5");
        IAVLNode joinNode4 = new AVLNode(4, "INFO 4");
        AVLTree tree7 = new AVLTree();
        tree7.insert(2, "info2");

        //testing single left rotation (happens when adding from the right)
        //AVLTree singleLeftRotation
        assertEquals(1, emptyTree1.join(joinNode, emptyTree2), "When joining two empty trees, this tree should contain only join node");
        assertEquals(3, emptyTree2.join(joinNode, tree), "suppose to return 3, when joining with this empty");
        tree.delete(4);
        assertEquals(2, tree.join(joinNode, tree2), "suppose to return 2");
        assertEquals(3, tree.join(joinNode2, tree3), "suppose to return 3");
        assertEquals(2, tree4.join(joinNode3, tree5), "suppose to return 2");
        assertEquals(3, tree6.join(joinNode4, tree7), "suppose to return 3");
    }

    @Test
    public void splitTest(){
        AVLTree t = new AVLTree();
        t.insert(14, "14");
        t.insert(17, "17");
        t.insert(11, "11");
        t.insert(7, "7");
        t.insert(53, "53");
        t.insert(4, "4");
        t.insert(13, "13");
        t.insert(12, "12");
        AVLTree[] arr = new AVLTree[2];
        arr = t.split(12);
        assertEquals(7, arr[0].getRoot().getKey());
        int[] a = arr[0].keysToArray();
        assertEquals(4, a[0]);

        AVLTree firstTest = new AVLTree();
        firstTest.insert(3, "info3");
        firstTest.insert(2, "info2");
        firstTest.insert(6, "info6");
        firstTest.insert(1, "info1");
        firstTest.insert(5, "info5");
        firstTest.insert(7, "info7");
        firstTest.insert(4, "info4");
        firstTest.insert(8, "info8");
        firstTest.insert(9, "info9");
        AVLTree[] arr1 = new AVLTree[2];
        arr1 = firstTest.split(6);
        int[] firstTreeKeysExpected = {1,2,3,4,5};
        int[] secondTreeKeysExpected = {7,8,9};
        int[] firstTreeKeys = arr1[0].keysToArray();
        int[] secondTreeKeys = arr1[1].keysToArray();
        assertArrayEquals(firstTreeKeysExpected, firstTreeKeys);
        assertArrayEquals(secondTreeKeysExpected, secondTreeKeys);
        assertEquals(3, arr1[0].getRoot().getKey());
        assertEquals(8, arr1[1].getRoot().getKey());
        //check max and min
        assertEquals(arr1[0].getMax().getKey(), 5);
        assertEquals(arr1[0].getMin().getKey(), 1);
        assertEquals(arr1[1].getMax().getKey(), 9);
        assertEquals(arr1[1].getMin().getKey(), 7);

        //split with the biggest key in the tree
        AVLTree biggestKey = new AVLTree();
        biggestKey.insert(1, "info1");
        biggestKey.insert(2, "info2");
        biggestKey.insert(3, "info3");
        AVLTree[] arr2 = new AVLTree[2];
        arr2 = biggestKey.split(3);
        int[] biggestKeyExpacted = {1,2};
        int[] biggestKeyKeys = arr2[0].keysToArray();
        assertArrayEquals(biggestKeyExpacted, biggestKeyKeys);
        assertEquals(2, arr2[0].getRoot().getKey());
        assertTrue(arr2[1].empty());
        assertEquals(1, arr2[0].getMin().getKey());
        assertEquals(2, arr2[0].getMax().getKey());

        //split with the smallest key in the tree
        AVLTree smallestKey = new AVLTree();
        smallestKey.insert(1, "info1");
        smallestKey.insert(2, "info2");
        smallestKey.insert(3, "info3");
        AVLTree[] arr3 = new AVLTree[2];
        arr3 = smallestKey.split(1);
        int[] smallestKeyExpacted = {2,3};
        int[] smallestKeyKeys = arr3[1].keysToArray();
        assertArrayEquals(smallestKeyExpacted, smallestKeyKeys);
        assertEquals(2, arr3[1].getRoot().getKey());
        assertTrue(arr2[0].empty());
        assertEquals(2, arr3[1].getMin().getKey());
        assertEquals(3, arr3[1].getMax().getKey());

        //
        AVLTree biggestTest = new AVLTree();
        biggestTest.insert(3, "info3");
        biggestTest.insert(2, "info2");
        biggestTest.insert(6, "info6");
        biggestTest.insert(1, "info1");
        biggestTest.insert(5, "info5");
        biggestTest.insert(7, "info7");
        biggestTest.insert(4, "info4");
        biggestTest.insert(8, "info8");
        biggestTest.insert(9, "info9");
        AVLTree[] arr4 = new AVLTree[2];
        arr4 = biggestTest.split(9);
        int[] biggestExpected2 = {1,2,3,4,5,6,7,8};
        int[] biggestKeys2 = arr4[0].keysToArray();
        assertArrayEquals(biggestExpected2, biggestKeys2);
        assertEquals(3, arr4[0].getRoot().getKey());
        //bu smallest key
        AVLTree[] arr5 = new AVLTree[2];
        arr5 = arr4[0].split(1);
        int[] smallestExpected2 = {2,3,4,5,6,7,8};
        int[] smallestKeys2 = arr5[1].keysToArray();
        assertArrayEquals(smallestExpected2, smallestKeys2);
        assertEquals(6, arr5[1].getRoot().getKey());
        assertTrue(arr5[0].empty());
        assertEquals(2, arr5[1].getMin().getKey());
        assertEquals(8, arr5[1].getMax().getKey());

        //split by root
        AVLTree rootTest = arr5[1];
        AVLTree[] arr6 = new AVLTree[2];
        arr6 = rootTest.split(6);
        int[] firstExpectedSmallerThenRoot = {2,3,4,5};
        int[] firstExpectedBiggerThenRoot = {7,8};
        int[] smallerThenRootKey = arr6[0].keysToArray();
        int[] BiggerThenRootKey = arr6[1].keysToArray();
        assertArrayEquals(firstExpectedSmallerThenRoot, smallerThenRootKey);
        assertEquals(3, arr6[0].getRoot().getKey());
        assertArrayEquals(firstExpectedBiggerThenRoot, BiggerThenRootKey);
        assertEquals(8, arr6[1].getRoot().getKey());
        assertEquals(2, arr6[0].getMin().getKey());
        assertEquals(5, arr6[0].getMax().getKey());
        assertEquals(7, arr6[1].getMin().getKey());
        assertEquals(8, arr6[1].getMax().getKey());

        //split by key from the lest sub tree
        AVLTree leftSubTree = new AVLTree();
        leftSubTree.insert(6, "info6");
        leftSubTree.insert(3, "info3");
        leftSubTree.insert(8, "info8");
        leftSubTree.insert(2, "info6");
        leftSubTree.insert(5, "info5");
        leftSubTree.insert(7, "info7");
        leftSubTree.insert(4, "info6");
        AVLTree[] arr7 = new AVLTree[2];
        arr7 = leftSubTree.split(5);
        int[] leftExpected = {2,3,4};
        int[] rightExpected = {6,7,8};
        int[] keysFromLeft = arr7[0].keysToArray();
        int[] keysFromRight = arr7[1].keysToArray();
        assertArrayEquals(leftExpected, keysFromLeft);
        assertArrayEquals(rightExpected, keysFromRight);
        //make sure to add a root test here when you know exactly how it works
        assertEquals(2, arr7[0].getMin().getKey());
        assertEquals(4, arr7[0].getMax().getKey());
        assertEquals(6, arr7[1].getMin().getKey());
        assertEquals(8, arr7[1].getMax().getKey());



    }

    @Test
    public void keysToArrayTest(){
        //empty tree
        AVLTree emptyTree = new AVLTree();
        int[] emptyTest = {};
        assertArrayEquals(emptyTest, emptyTree.keysToArray(), "returned the wrong array");
        //tree with only root
        AVLTree onlyRoot = new AVLTree();
        onlyRoot.insert(2, "INFO2");
        int[] onlyRootTest = {2};
        assertArrayEquals(onlyRootTest, onlyRoot.keysToArray(), "returned the wrong array");
        //delete the root and call keysToArray with new empty tree
        onlyRoot.delete(2);
        assertArrayEquals(emptyTest, onlyRoot.keysToArray(), "returned the wrong array");
        //8,9,10
        AVLTree firstReg = new AVLTree();
        int[] firstRegTest = {8,9,10};
        firstReg.insert(8, "info8");
        firstReg.insert(10, "info10");
        firstReg.insert(9, "info9");
        assertArrayEquals(firstRegTest, firstReg.keysToArray(), "returned the wrong array");
        //tree with unary root- right child
        AVLTree unaryTest = new AVLTree();
        unaryTest.insert(8, "info8");
        unaryTest.insert(10, "info10");
        int[] unaryTestArr = {8,10};
        assertArrayEquals(unaryTestArr, unaryTest.keysToArray(), "returned the wrong array");
        //tree with unary root- right child
        AVLTree unaryTestLeft = new AVLTree();
        unaryTestLeft.insert(8, "info8");
        unaryTestLeft.insert(9, "info10");
        int[] unaryTestLeftArr = {8,9};
        assertArrayEquals(unaryTestLeftArr, unaryTestLeft.keysToArray(), "returned the wrong array");
        //regular big tree
        AVLTree regularTree = new AVLTree();
        int[] regularTreeTest = {3,4,5,6,7,8,9,10,20,25};
        regularTree.insert(10, "info10");
        regularTree.insert(5, "info5");
        regularTree.insert(20, "info20");
        regularTree.insert(4, "info4");
        regularTree.insert(8, "info8");
        regularTree.insert(25, "info25");
        regularTree.insert(3, "info3");
        regularTree.insert(7, "info7");
        regularTree.insert(9, "info9");
        regularTree.insert(6, "info6");
        assertArrayEquals(regularTreeTest, regularTree.keysToArray(), "returned the wrong array");
    }

    @Test
    public void infoToArrayTest(){
        //empty tree
        AVLTree emptyTree = new AVLTree();
        String[] emptyTest = {};
        assertArrayEquals(emptyTest, emptyTree.infoToArray(), "returned the wrong array");
        //tree with only root
        AVLTree onlyRoot = new AVLTree();
        onlyRoot.insert(2, "INFO2");
        String[] onlyRootTest = {"INFO2"};
        assertArrayEquals(onlyRootTest, onlyRoot.infoToArray(), "returned the wrong array");
        //delete the root and call keysToArray with new empty tree
        onlyRoot.delete(2);
        assertArrayEquals(emptyTest, onlyRoot.infoToArray(), "returned the wrong array");
        //8,9,10
        AVLTree firstReg = new AVLTree();
        String[] firstRegTest = {"info8","info9","info10"};
        firstReg.insert(8, "info8");
        firstReg.insert(10, "info10");
        firstReg.insert(9, "info9");
        assertArrayEquals(firstRegTest, firstReg.infoToArray(), "returned the wrong array");
        //tree with unary root- right child
        AVLTree unaryTest = new AVLTree();
        unaryTest.insert(8, "info8");
        unaryTest.insert(10, "info10");
        String[] unaryTestArr = {"info8","info10"};
        assertArrayEquals(unaryTestArr, unaryTest.infoToArray(), "returned the wrong array");
        //tree with unary root- right child
        AVLTree unaryTestLeft = new AVLTree();
        unaryTestLeft.insert(8, "info8");
        unaryTestLeft.insert(9, "info10");
        String[] unaryTestLeftArr = {"info8","info10"};
        assertArrayEquals(unaryTestLeftArr, unaryTestLeft.infoToArray(), "returned the wrong array");
        //regular big tree
        AVLTree regularTree = new AVLTree();
        String[] regularTreeTest = {"info3","info4","info5","info6","info7","info8","info9","info10","info20","info25"};
        regularTree.insert(10, "info10");
        regularTree.insert(5, "info5");
        regularTree.insert(20, "info20");
        regularTree.insert(4, "info4");
        regularTree.insert(8, "info8");
        regularTree.insert(25, "info25");
        regularTree.insert(3, "info3");
        regularTree.insert(7, "info7");
        regularTree.insert(9, "info9");
        regularTree.insert(6, "info6");
        assertArrayEquals(regularTreeTest, regularTree.infoToArray(), "returned the wrong array");

        //equal info for some nodes
        AVLTree equalNodesForSome = new AVLTree();
        String[] equalNodesForSomeTest = {"info1", "info2", "info3", "info3", "info3"};
        equalNodesForSome.insert(1, "info1");
        equalNodesForSome.insert(2, "info2");
        equalNodesForSome.insert(3, "info3");
        equalNodesForSome.insert(4, "info3");
        equalNodesForSome.insert(5, "info3");
        String[] newOne = equalNodesForSome.infoToArray();
        assertArrayEquals(equalNodesForSomeTest, equalNodesForSome.infoToArray(), "returned the wrong array");

        //all info are the same
        AVLTree equalNodes = new AVLTree();
        String[] equalNodesTest = {"info1","info1", "info1"};
        equalNodes.insert(1, "info1");
        equalNodes.insert(2, "info1");
        equalNodes.insert(3, "info1");
        assertArrayEquals(equalNodesTest, equalNodes.infoToArray(), "returned the wrong array");


    }




}
