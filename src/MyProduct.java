/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import entity.Product;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import util.MyBSTree;
import util.TNode;

/**
 *
 * @author LeVanAnh
 */
public class MyProduct {

    //a list of products
    private MyBSTree tree;
    private static final BufferedReader reader
            = new BufferedReader(new InputStreamReader(System.in));
    private final String DATA = "myproduct.txt";

    public MyProduct() {
        tree = new MyBSTree();
        loadData();
    }

    //1.1 input and insert a new product to tree
    public void insert() {
        Product p = new Product();
        p.setCode(stringReader("Enter product code: ").toUpperCase());
        p.setName(stringReader("Enter product name: "));
        p.setQuantity(intReader("Enter product quantity: ", 0, 1000000000));
        p.setSaled(intReader("Enter product saled: ", 0, 1000000000));
        p.setPrice(doubleReader("Enter product price: ", 0, 1000000000));
        if (tree.insert(p)) {
            System.out.println("Product is inserted!");
        }

    }

    //1.2 in-order traverse
    public void inOrder() {
        if (tree.isEmpty()) {
            System.out.println("List of products are empty!");
            return;
        }
        tree.inOrder();
    }

    //1.3 BFT a tree
    public void BFT() {
        if (tree.isEmpty()) {
            System.out.println("List of products are empty!");
            return;
        }
        tree.BFT();
    }

    //1.4 search a product by product code
    public void search() {
        if (tree.isEmpty()) {
            System.out.println("List of products are empty!");
            return;
        }
        String productCode = stringReader("Enter product code to search: ");
        TNode<Product> node = tree.search(productCode);
        if (tree.search(productCode) != null) {
            System.out.println("Information of product code " + productCode);
            tree.visit(node);
        } else {
            System.out.println("No product with code: " + productCode + " found");
        }
    }

    //1.5 delete a product by product code
    public void delete() {
        if (tree.isEmpty()) {
            System.out.println("List of products are empty!");
            return;
        }
        String productCode = stringReader("Enter product code to delete: ");
        tree.delete(productCode);
    }

    //1.6 simply balancing a tree
    public void balance() {
        if (tree.isEmpty()) {
            System.out.println("List of products are empty!");
            return;
        }
        tree.balance();
        System.out.println("tree is balanced complete");
    }

    //1.7 count the number of products in the tree
    public void size() {
        if (tree.isEmpty()) {
            System.out.println("List of products are empty!");
            return;
        }
        int nodeNum = tree.count();
        if (nodeNum > 0) {
            System.out.println("Number of product " + nodeNum);
        } else {
            System.out.println("No product found");
        }
    }
    
    // display products that have price higher then number that user inputs
    void priceFilter() {
        if (tree.isEmpty()) {
            System.out.println("List of products are empty!");
            return;
        }
        System.out.println("This filter out products with price that have price "
                + "higher than or equal your input");
        double price = doubleReader("Enter product's price to filter out: ", 0, 1000000000);
        tree.priceFilter(price);
    }

    void saveData() {
        try ( FileOutputStream fos = new FileOutputStream(DATA);  ObjectOutputStream oos = new ObjectOutputStream(fos);) {
            oos.writeObject(tree);
            System.out.println("Save file data to myproduct.txt complete");
            oos.close();
        } catch (IOException e) {
            System.out.println("Save data problem: " + e.getMessage());
        }
    }

    private void loadData() {
        try ( FileInputStream fis = new FileInputStream(DATA);  ObjectInputStream ois = new ObjectInputStream(fis)) {
            tree = (MyBSTree) ois.readObject();
            ois.close();
            System.out.println("Import data complete");
        } catch (Exception e) {
            System.out.println("Import data problem: " + e.getMessage());
        }
    }

    // read interger input
    public int intReader(String msg, int startRange, int endRange) {
        int choice = 0;
        boolean isValid = true;

        while (isValid) {
            System.out.print(msg);
            try {
                choice = Integer.parseInt(reader.readLine());
                if (choice >= startRange && choice <= endRange) {
                    isValid = false;
                } else {
                    System.out.println("Check your input number!");
                }
            } catch (IOException | NumberFormatException e) {
                System.out.println("Invalid Input!");
            }
        }
        return choice;
    }

    // read double input
    private double doubleReader(String msg, double startRange, double endRange) {
        double value = 0;
        boolean isValid = true;

        while (isValid) {
            System.out.print(msg);
            try {
                value = Double.parseDouble(reader.readLine());
                if (value >= startRange && value <= endRange) {
                    isValid = false;
                } else {
                    System.out.println("Check your input");
                }
            } catch (IOException | NumberFormatException e) {
                System.out.println("Invalid Input!");
            }
        }
        return value;
    }

    // read string input
    private String stringReader(String msg) {
        String value = "";
        boolean isValid = true;

        while (isValid) {
            System.out.print(msg);
            try {
                value = reader.readLine();
                if (value.trim().length() > 0) {
                    isValid = false;
                } else {
                    System.out.println("Not allow empty");
                }
            } catch (IOException | NumberFormatException e) {
                System.out.println("Invalid Input!");
            }
        }
        return value;
    }

}
