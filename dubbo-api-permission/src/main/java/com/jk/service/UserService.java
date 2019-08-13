/**
 * Copyright (C), 2019, XXX有限公司
 * FileName: UserService
 * Author:   clp
 * Date:     2019/8/9 15:13
 * Description: 用户---接口
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.jk.service;

import com.jk.model.Log;
import com.jk.model.Permission;
import com.jk.model.Role;
import com.jk.model.User;

import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈用户---接口〉
 *
 * @author clp
 * @create 2019/8/9
 * @since 1.0.0
 */
public interface UserService {

    User loginUser(User user);

    List<Permission> queryTree(Integer id, Integer userid);

    Map queryUser(Integer page, Integer rows, User user);

    void addUserlist(User user);

    void deleteUser(String ids);

    void updateUser(User user);

    List<Role> getRoleByUid(Integer uid);

    int updateUserRole(Integer[] roleids, Integer uid2);

    List<User> query();

    void add(List<User> list);

    Map querlog(Integer page, Integer rows, Log log);

    Map queryRole(Integer page, Integer rows, Role role);

    void addRole(Role role);

    void deleteRole(String ids);

    void updateRole(Role role);

    List getPermissionByRId(Integer roleid);

    int updateRolePermiss(Integer[] perids, Integer roleid);

    void addPer(Permission permission);

    void deletePer(String ids);

    void updatePer(Permission permission);

    Map queryPer(Integer page, Integer rows, Permission permission);
}
