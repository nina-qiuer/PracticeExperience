/**
 * 
 */
package com.tuniu.gt.uc.datastructure;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.tuniu.gt.uc.entity.DepartmentEntity;
import com.tuniu.gt.uc.service.user.IDepartmentService;

/**
 * @author jiangye
 *
 */
public class DepartUsersTree implements Serializable {
   
    private static final long serialVersionUID = 846119214222034462L;
    
    private TreeNode<String> root;

    public TreeNode<String> getRoot() {
        return root;
    }

    public void setRoot(TreeNode<String> root) {
        this.root = root;
    }
    
    
    
}
