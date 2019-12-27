/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author LeVanAnh
 */
public class Main {

    public static void main(String[] args) {
        MyProduct mp = new MyProduct();
        boolean keepWorking = true;

        while (keepWorking) {
            switch (mp.intReader("==========================="
                    + "\nProduct list"
                    + "\n1.Insert a new product"
                    + "\n2.In-order travese"
                    + "\n3.Breadth first travese"
                    + "\n4.Search by a product code"
                    + "\n5.Delete by a product code"
                    + "\n6.Simple balancing"
                    + "\n7.Count number of products"
                    + "\n8.Filter products with price"
                    + "\n0.Exit"
                    + "\nYour choice: ", 0, 8)) {
                case 1:
                    mp.insert();
                    break;
                case 2:
                    mp.inOrder();
                    break;
                case 3:
                    mp.BFT();
                    break;
                case 4:
                    mp.search();
                    break;
                case 5:
                    mp.delete();
                    break;
                case 6:
                     mp.balance();
                    break;
                case 7:
                    mp.size();
                    break;
                case 8:
                    mp.priceFilter();
                    break;
                case 0:
                    keepWorking = false;
                    mp.saveData();
                    break;

            }
        }

    }
}
