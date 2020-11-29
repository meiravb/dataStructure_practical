
public class test {
    public static void main(String[] args){
        AVLTree t = new AVLTree();
        System.out.println(t.insert(20, "h"));
        System.out.println(t.insert(10, "h"));
        System.out.println(t.insert(25, "j"));
        System.out.println(t.insert(5, "h"));
        System.out.println(t.insert(15, "h"));
        System.out.println(t.insert(20, "j"));
        System.out.println(t.insert(17, "h"));


        System.out.println("key - "+ t.getRoot().getKey()+", height - "+t.getRoot().getHeight()+" balance - "+t.getRoot().getBalance());
        System.out.println("key - "+ t.getRoot().getLeft().getKey()+", height - "+t.getRoot().getLeft().getHeight()+" balance - "+t.getRoot().getLeft().getBalance());
        System.out.println("key - "+ t.getRoot().getRight().getKey()+", height - "+t.getRoot().getRight().getHeight()+" balance - "+t.getRoot().getRight().getBalance());
        System.out.println("key - "+ t.getRoot().getLeft().getLeft().getKey()+", height - "+t.getRoot().getLeft().getLeft().getHeight()+" balance - "+t.getRoot().getLeft().getLeft().getBalance());
        System.out.println("key - "+ t.getRoot().getRight().getLeft().getKey()+", height - "+t.getRoot().getRight().getLeft().getHeight()+" balance - "+t.getRoot().getRight().getLeft().getBalance());
        System.out.println("key - "+ t.getRoot().getRight().getRight().getKey()+", height - "+t.getRoot().getRight().getRight().getHeight()+" balance - "+t.getRoot().getRight().getRight().getBalance());


    }
}
