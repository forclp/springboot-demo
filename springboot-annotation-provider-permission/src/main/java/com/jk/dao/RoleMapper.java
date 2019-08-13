package com.jk.dao;

import com.jk.model.Role;
import com.jk.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer roleid);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer roleid);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> getRoleAll();

    void deleteRole(@Param("ids")String ids);

    List<User> queryRole(@Param("sta") int sta, @Param("rows")Integer rows, @Param("role")Role role);

    long queryRoleCount();
}