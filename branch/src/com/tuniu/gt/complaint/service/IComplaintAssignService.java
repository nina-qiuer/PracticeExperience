/**
 * 
 */
package com.tuniu.gt.complaint.service;

import java.util.List;

import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.enums.AssignType;
import com.tuniu.gt.frm.entity.UserEntity;

/**
 * [投诉单分单服务类]
 * @author jiangye
 */
public interface IComplaintAssignService {
    
    boolean assign(ComplaintEntity cmpEntity, int userId);

    /**
     * 分配投诉单
     * @param cmpEntity 待分配投诉单
     * @param user  处理人用户实体
     * @param assignType 分单类型
     * @return  分配成功
     */
    boolean assign(ComplaintEntity cmpEntity, UserEntity user,AssignType assignType);

    int assign(List<ComplaintEntity> cmpList, int userId);

    /**
     * 批量分配投诉单
     * @param cmpList 待分配投诉单集合
     * @param user 处理人用户实体
     * @return  成功分配数量
     */
    int assign(List<ComplaintEntity> cmpList, UserEntity user);
}
