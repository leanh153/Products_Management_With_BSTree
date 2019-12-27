/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.Serializable;

/**
 *
 * @author LeAnh
 * @param <T> generic data type
 */
public class TNode<T> implements Serializable{

    private static final long serialVersionUID = 1L;

    private T info;
    private int high;

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }
    
    private TNode<T> left, right;

    public TNode() {
    }

    public TNode(T info, TNode<T> left, TNode<T> right) {
        this.info = info;
        this.left = left;
        this.right = right;
    }

    public TNode(T info) {
        this(info, null, null);
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    public TNode<T> getLeft() {
        return left;
    }

    public void setLeft(TNode<T> left) {
        this.left = left;
    }

    public TNode<T> getRight() {
        return right;
    }

    public void setRight(TNode<T> right) {
        this.right = right;
    }

}
