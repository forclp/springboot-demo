package com.jk.dao;


import com.jk.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User loginUser(User user);

    List<User> queryUser(@Param("sta") int sta, @Param("rows")Integer rows,@Param("user") User user);

    long queryCount();

    void deleteUser(@Param("ids")String ids);

    List<User> query();

    void add(List<User> list);
}