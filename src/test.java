import java.util.Arrays;

public class test {
    public static void main(String[] args){
        AVLTree t = new AVLTree();
        int a = t.insert(10, "10");
        int b = t.insert(20, "20");
        int c = t.insert(15, "15");
       // System.out.println(t.getRoot().getKey());
        int d = t.insert(25, "25");
       // System.out.println(t.getRoot().getRight().getRight().getKey());
        int e = t.insert(30, "30");
        int f = t.insert(16, "16");
        int g = t.insert(18, "18");
        int h = t.insert(19, "19");

        System.out.println(t.getRoot().getKey());
        System.out.println(t.getRoot().getLeft().getKey());
        System.out.println(t.getRoot().getRight().getKey());
        System.out.println(t.getRoot().getLeft().getLeft().getKey());
        System.out.println(t.getRoot().getLeft().getRight().getKey());
        System.out.println(t.getRoot().getRight().getRight().getKey());
        System.out.println(t.getRoot().getLeft().getRight().getLeft().getKey());
        System.out.println(t.getRoot().getLeft().getRight().getRight().getKey());
        System.out.println(Arrays.toString(t.keysToArray()));
        AVLTree[] arr = t.split(10);
        System.out.println(Arrays.toString(arr[0].keysToArray()));
        System.out.println(Arrays.toString(arr[1].keysToArray()));




        /*System.out.println("key - "+ t.getRoot().getKey()+", height - "+t.getRoot().getHeight()+" balance - "+t.getRoot().getBalance()+ " size - " + t.getRoot().getSize());
        System.out.println("key - "+ t.getRoot().getLeft().getKey()+", height - "+t.getRoot().getLeft().getHeight()+" balance - "+t.getRoot().getLeft().getBalance()+ " size - " + t.getRoot().getLeft().getSize());
        System.out.println("key - "+ t.getRoot().getRight().getKey()+", height - "+t.getRoot().getRight().getHeight()+" balance - "+t.getRoot().getRight().getBalance()+ " size - " + t.getRoot().getRight().getSize());
        System.out.println("key - "+ t.getRoot().getLeft().getLeft().getKey()+", height - "+t.getRoot().getLeft().getLeft().getHeight()+" balance - "+t.getRoot().getLeft().getLeft().getBalance()+ " size - " + t.getRoot().getLeft().getLeft().getSize());
        System.out.println("key - "+ t.getRoot().getRight().getLeft().getKey()+", height - "+t.getRoot().getRight().getLeft().getHeight()+" balance - "+t.getRoot().getRight().getLeft().getBalance()+ " size - " + t.getRoot().getRight().getLeft().getSize());
        System.out.println("key - "+ t.getRoot().getRight().getRight().getKey()+", height - "+t.getRoot().getRight().getRight().getHeight()+" balance - "+t.getRoot().getRight().getRight().getBalance()+ " size - " + t.getRoot().getRight().getRight().getSize());*/
    }
}
