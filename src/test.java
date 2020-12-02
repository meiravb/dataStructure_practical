import java.util.Arrays;

public class test {
    public static void main(String[] args){
        AVLTree t = new AVLTree();
        t.insert(20, "20");
        t.insert(10, "10");
        t.insert(25, "25");
        t.insert(5, "5");
        t.insert(15, "15");
        t.insert(20, "20");
        t.insert(17, "17");
        //System.out.println(t.g);
        System.out.println(Arrays.toString(t.infoToArray()));

        System.out.println("key - "+ t.getRoot().getKey()+", height - "+t.getRoot().getHeight()+" balance - "+t.getRoot().getBalance()+ " size - " + t.getRoot().getSize());
        System.out.println("key - "+ t.getRoot().getLeft().getKey()+", height - "+t.getRoot().getLeft().getHeight()+" balance - "+t.getRoot().getLeft().getBalance()+ " size - " + t.getRoot().getLeft().getSize());
        System.out.println("key - "+ t.getRoot().getRight().getKey()+", height - "+t.getRoot().getRight().getHeight()+" balance - "+t.getRoot().getRight().getBalance()+ " size - " + t.getRoot().getRight().getSize());
        System.out.println("key - "+ t.getRoot().getLeft().getLeft().getKey()+", height - "+t.getRoot().getLeft().getLeft().getHeight()+" balance - "+t.getRoot().getLeft().getLeft().getBalance()+ " size - " + t.getRoot().getLeft().getLeft().getSize());
        System.out.println("key - "+ t.getRoot().getRight().getLeft().getKey()+", height - "+t.getRoot().getRight().getLeft().getHeight()+" balance - "+t.getRoot().getRight().getLeft().getBalance()+ " size - " + t.getRoot().getRight().getLeft().getSize());
        System.out.println("key - "+ t.getRoot().getRight().getRight().getKey()+", height - "+t.getRoot().getRight().getRight().getHeight()+" balance - "+t.getRoot().getRight().getRight().getBalance()+ " size - " + t.getRoot().getRight().getRight().getSize());
    }
}
