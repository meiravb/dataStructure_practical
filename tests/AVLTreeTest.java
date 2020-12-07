import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AVLTreeTest extends AVLTree {

    @Test
    public void insertTest(){
        AVLTree inserFirstTest = new AVLTree();
        assertEquals(0, inserFirstTest.insert(10, "info10"), "no balance is needed");
        assertEquals(1, inserFirstTest.insert(20, "info20"), "no balance is needed");
        assertEquals(5, inserFirstTest.insert(15, "info15"), "double rotation with demotion of 20,10 and promotion of 15");//6
        assertEquals(1, inserFirstTest.insert(25, "info25"), "single promotion of 15");
        assertEquals(2, inserFirstTest.insert(30, "info30"), "single left rotation of 20 and demote of 20");//4
        assertEquals(6, inserFirstTest.insert(16, "info16"), "promote and then double rotation");
        assertEquals(2, inserFirstTest.insert(18, "info18"), "two promotions of 15 and 10");//1
        assertEquals(2, inserFirstTest.insert(19, "info19"), "two promotions of 15 and 10");//2




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
        assertEquals(6, deleteFirstTest.delete(30), "double rotation 20 demoted twice 18 promoted and 15 demoted");







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

    }

    @Test
    public void keysToArrayTest(){

    }


}