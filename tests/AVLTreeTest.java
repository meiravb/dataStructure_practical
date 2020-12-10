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
        //to be continued






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

        //double rotation LR
        AVLTree deleteLR = new AVLTree();
        deleteLR.insert(50, "info20");
        deleteLR.insert(40, "info10");
        deleteLR.insert(60, "info30");
        deleteLR.insert(45, "info5");
        assertEquals(6, deleteLR.delete(60), "LR rotation promote 45 demote 40 and demote twice 50");

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

    }

    @Test
    public void keysToArrayTest(){

    }


}
