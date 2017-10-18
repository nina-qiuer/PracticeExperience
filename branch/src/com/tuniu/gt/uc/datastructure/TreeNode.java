/**
 * 
 */
package com.tuniu.gt.uc.datastructure;

import java.io.Serializable;
import java.util.List;

/**
 * @author jiangye
 *
 */
public class TreeNode<T> implements Serializable {

    private static final long serialVersionUID = 562476440811098527L;

    private T data;
    
    private List<TreeNode<T>>  childs;
    
    public TreeNode(T data) {
        this.data = data;
    }

    public TreeNode(T data,  List<TreeNode<T>>  childs) {
        this.data = data;
        this.childs = childs;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<TreeNode<T>> getChilds() {
        return childs;
    }

    public void setChilds(List<TreeNode<T>> childs) {
        this.childs = childs;
    }

    
    
}
