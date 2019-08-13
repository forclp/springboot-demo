package com.jk.dao;

import com.jk.model.RolePer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolePerMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(RolePer record);

    int insertSelective(RolePer record);

    RolePer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RolePer record);

    int updateByPrimaryKey(RolePer record);

    List<String> getPermissionByRId(@Param("roleid") Integer roleid);

    int deletePerByroleid(@Param("roleid")Integer roleid);
}