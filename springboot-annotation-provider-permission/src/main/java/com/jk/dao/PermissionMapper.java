package com.jk.dao;

import com.jk.model.Permission;
import com.jk.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

    List<Permission> queryTree(@Param("id")Integer id,@Param("userid") Integer userid);

    List<Permission> getPerAll();

    void deletePer(@Param("ids")String ids);

    List<Permission> queryPer(@Param("sta")int sta, @Param("rows")Integer rows, @Param("permission")Permission permission);

    long queryPerCount();
}