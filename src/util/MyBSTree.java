/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import entity.Product;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LeVanAnh
 */
public class MyBSTree implements Serializable {

    private static final long serialVersionUID = 1L;

    //a root of tree
    TNode<Product> root;

    public MyBSTree() {
        root = null;
    }

    //visit a node of a tree -> output information of visited node
    public void visit(TNode<Product> p) {
        System.out.println(p.getInfo().toString());
    }

    public void visitWithNodeLevel(TNode<Product> p) {
        System.out.printf("%-10d%-70s%n", p.getHigh(), p.getInfo().toString());
    }

    //return true if tree is empty otherwise return false
    public boolean isEmpty() {
        return root == null;
    }

    //inorder a tree
    public void inOrder() {
        System.out.printf("%-10s%-20s%-10s%-10s%-10s%n", "Code", "Name", "Quantity", "Saled", "Price");
        inOrder(root);
    }

    private void inOrder(TNode<Product> node) {
        if (node == null) {
            return;
        }
        inOrder(node.getLeft());
        visit(node);
        inOrder(node.getRight());
    }

    private void inOrderWithNodeLevel(TNode<Product> node) {
        if (node == null) {
            return;
        }
        inOrderWithNodeLevel(node.getLeft());
        visitWithNodeLevel(node);
        inOrderWithNodeLevel(node.getRight());
    }

    //count number of products
    public int count() {
        return countNode(root);
    }

    private int countNode(TNode<Product> node) {
        if (node == null) {
            return 0;
        }

        return 1 + countNode(node.getLeft()) + countNode(node.getRight());

    }

    //breadth-first traverse a tree
    @SuppressWarnings("unchecked")
    public void BFT() {
        if (root == null) {
            return;
        }

        MyLinkedQueue q = new MyLinkedQueue();
        q.enqueue(root);
        TNode<Product> p;
        System.out.printf("%-10s%-20s%-10s%-10s%-10s%n", "Code", "Name", "Quantity", "Saled", "Price");
        while (!q.isEmpty()) {
            p = (TNode<Product>) q.dequeue();
            if (p.getLeft() != null) {
                q.enqueue(p.getLeft());
            }
            if (p.getRight() != null) {
                q.enqueue(p.getRight());
            }
            visit(p);
        }
    }

    //insert a new Product to a tree
    public boolean insert(Product product) {
        boolean inserted = false;
        if (root == null) {
            root = new TNode<>(product);
            root.setHigh(0);
            return true;
        }

        TNode<Product> parent = root;
        TNode<Product> current = root;

        while (current != null) {
            if (current.getInfo().equals(product)) {
                System.out.println("Product of the same code: " + product.getCode() + " already exists!");
                return false;
            }
            // set parent data
            parent = current;

            if (current.getInfo().getCode().compareToIgnoreCase(product.getCode()) < 0) {
                current = current.getRight();
            } else {
                current = current.getLeft();
            }

        }
        int highOfParent = parent.getHigh();
        TNode<Product> newNode = new TNode<>(product);
        newNode.setHigh(highOfParent + 1);
        if (parent.getInfo().getCode().compareTo(product.getCode()) < 0) {
            parent.setRight(newNode);
            inserted = true;
        } else {
            parent.setLeft(newNode);
            inserted = true;

        }
        return inserted;
    }

    //buildTree a tree
    //step 1: traverse inorder tree and copy all item on tree to an arraylist
    //step 2: insert all items of list to a tree
    private void buildArray(List<TNode<Product>> list, TNode<Product> p) {
        if (p == null) {
            return;
        }
        buildArray(list, p.getRight());
        list.add(p);
        buildArray(list, p.getLeft());
    }

    //step 2:
    private void buildTree(List<TNode<Product>> list, int f, int l) {
        if (f > l) {
            return;
        }
        int midle = (f + l) / 2;
        TNode<Product> p = list.get(midle);
        insert(p.getInfo());
        buildTree(list, f, midle - 1);
        buildTree(list, midle + 1, l);
    }

    // balance a tree
    public void balance() {
        List<TNode<Product>> list = new ArrayList<>();
        buildArray(list, root);
        MyBSTree tree = new MyBSTree();
        tree.buildTree(list, 0, list.size() - 1);
        root = tree.root;
    }

    //search a TNode of tree by product code
    //return null if given code does not exists
    public TNode<Product> search(String code) {
        return searchRecursive(root, code);
    }

    private TNode<Product> searchRecursive(TNode<Product> node, String code) {
        if (node == null) {
            return null;
        }

        if (node.getInfo().getCode().equalsIgnoreCase(code)) {
            return node;
        }

        return node.getInfo().getCode().compareToIgnoreCase(code) < 0
                ? searchRecursive(node.getRight(), code)
                : searchRecursive(node.getLeft(), code);
    }

    //delete a node by a given product code
    public void delete(String code) {
        /*  Example BS tree
              50 
           /     \ 
          30      70 
         /  \    /   
       20   40  60   
               /  \
              58  61
                \
                 59
        if delete 50 then 58 become root node and 59 become child of 60
        root is not only node, but also tree which is base to get other nodes
         */
        root = deleteRecursive(root, code);

    }

    // delete recursive 
    private TNode<Product> deleteRecursive(TNode<Product> current, String code) {
        // break point
        if (current == null) {
            return null;
        }

        if (current.getInfo().getCode().compareToIgnoreCase(code) < 0) {
            // recursive left child
            current.setRight(deleteRecursive(current.getRight(), code));
        } else if (current.getInfo().getCode().compareToIgnoreCase(code) > 0) {
            // recursive right child
            current.setLeft(deleteRecursive(current.getLeft(), code));
        } else {
            // TNode to delete found
            // case current have no child or one child
            if (current.getRight() != null && current.getLeft() != null) {
                // case have 2 child find sucessor
                TNode<Product> sucessor = findSuccessor(current.getRight());
                // set current Info
                current.setInfo(sucessor.getInfo());

                /* case delete 50 delete sucesscor  
        
              50                        58                        
           /     \                   /     \                      
          30     70                30      70               
         /  \    /                  /  \    /                   
       20   40  60                 20   40  60                   
               /  \                        /  \                          
              58  61                      59  61                      
                \
                 59
        if delete 50 then 58 is sucessor(remove 58 from 60 left child also)
                if 59 not exist then 60 becom sucessor
        and 59 become child of 60
                 */
                current.setRight(deleteRecursive(current.getRight(), sucessor.getInfo().getCode()));
                return current;
            } // current have one child
            else if (current.getLeft() != null) {
                return current.getLeft();
            } else if (current.getRight() != null) {
                return current.getRight();
            } // current have no child
            else {
                return null;
            }
        }
        System.out.println("Product code " + code + " has been deleted");
        // at last return this, current is root node now
        return current;
    }

    // find sucessor 
    private TNode<Product> findSuccessor(TNode<Product> node) {
        /*  
        
              50 
           /     \ 
          30      70 
         /  \    /   
       20   40  60   
               /  \
              58  61
                \
                 59
        if delete 50 then 58 is sucessor if 59 not exist then 60 becom sucessor
         */

        if (node.getLeft() == null) {
            return node;
        } else {
            return findSuccessor(node.getLeft());
        }
    }

    // 
    public void priceFilter(double price) {
        List<TNode<Product>> list = new ArrayList<>();
        getProductWithPrice(list, root, price);
        if (list.isEmpty()) {
            System.out.println("No Products found with your input!");

        } else {
            MyBSTree priceTree = new MyBSTree();
            priceTree.buildTree(list, 0, list.size() - 1);
            root = priceTree.root;
            System.out.printf("%-10s%-10s%-20s%-10s%-10s%-10s%n", "Level", "Code", "Name", "Quantity", "Saled", "Price");
            inOrderWithNodeLevel(root);
        }

    }

    // use inorder algorithm to add Tnode to list  
    private void getProductWithPrice(List<TNode<Product>> list, TNode<Product> node, double price) {
        if (node == null) {
            return;
        }
        getProductWithPrice(list, node.getLeft(), price);
        if (node.getInfo().getPrice() >= price) {
            list.add(node);
        }
        getProductWithPrice(list, node.getRight(), price);
    }

}
