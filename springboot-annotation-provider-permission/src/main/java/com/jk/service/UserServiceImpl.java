/**
 * Copyright (C), 2019, XXX有限公司
 * FileName: UserServiceImpl
 * Author:   clp
 * Date:     2019/8/9 15:18
 * Description: 用户--业务处理层
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.jk.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.jk.dao.*;
import com.jk.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 〈一句话功能简述〉<br> 
 * 〈用户--业务处理层〉
 *
 * @author clp
 * @create 2019/8/9
 * @since 1.0.0
 */
@Service
public class UserServiceImpl implements UserService{


    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private RolePerMapper rolePerMapper;

    @Override
    public User loginUser(User user) {


        return userMapper.loginUser(user);
    }

    @Override
    public List<Permission> queryTree(Integer id, Integer userid) {
        if(id==null){
             id=0;
        }
        List<Permission> list=permissionMapper.queryTree(id,userid);
        for (Permission permission:list ) {
            List<Permission> list2=permissionMapper.queryTree(permission.getId(),userid);
            if(list2!=null && list2.size()>0){
                permission.setChildren(list2);
            }
        }
        return list;
    }

    @Override
    public Map queryUser(Integer page, Integer rows, User user) {
        int sta=(page-1)*rows;
        List<User> list=userMapper.queryUser(sta,rows,user);
        long count=userMapper.queryCount();
        Map map=new HashMap();
        map.put("rows",list);
        map.put("total",count);
        return map;
    }

    @Override
    public void addUserlist(User user) {
        userMapper.insertSelective(user);
    }

    @Override
    public void deleteUser(String ids) {
        userMapper.deleteUser(ids);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    //根据  用户id  获取 所对应的 菜单
    @Override
    public List<Role> getRoleByUid(Integer uid) {
        List<Integer> list=userRoleMapper.getRoleByUid(uid);
        List<Role> list2=roleMapper.getRoleAll();
        for (int i=0;i<list.size();i++){
            for (int j=0;j<list2.size();j++){
               if(list.get(i)==list2.get(j).getRoleid()){
                    list2.get(j).setChecked("true");
               }
            }
        }
        return list2;
    }

    @Override
    public int updateUserRole(Integer[] roleids, Integer uid2) {
        int i=userRoleMapper.deleteRoleByUid(uid2);
         if(i>=0){
            for (int j=0;j<roleids.length;j++){
                UserRole userRole=new UserRole();
                userRole.setUserid(uid2);
                userRole.setRoleid(roleids[j]);
                i=userRoleMapper.insert(userRole);
            }
         }
        return i;
    }

    @Override
    public List<User> query() {
        return userMapper.query();
    }

    @Override
    public void add(List<User> list) {
        userMapper.add(list);
    }

    @Override
    public Map querlog(Integer page, Integer rows, Log log) {
       Map map=new HashMap();
        Query query=new Query();
        Criteria c=new Criteria();
        if(log!=null){
            if(log.getLogname()!=null && !"".equals(log.getLogname())){
                Pattern pattern = Pattern.compile("^.*" + log.getLogname() + ".*$", Pattern.CASE_INSENSITIVE);
            }
        }

        query.addCriteria(c);
        query.skip((page-1)*rows);
        query.limit(rows);
        long count=mongoTemplate.count(query,Log.class,"logdb");
        List<Log> list=mongoTemplate.find(query,Log.class,"logdb");
         map.put("rows",list);
         map.put("total",count);


        return map;
    }

    @Override
    public Map queryRole(Integer page, Integer rows, Role role) {
        int sta=(page-1)*rows;
        List<User> list=roleMapper.queryRole(sta,rows,role);
        long count=roleMapper.queryRoleCount();
        Map map=new HashMap();
        map.put("rows",list);
        map.put("total",count);
        return map;
    }

    @Override
    public void addRole(Role role) {
         roleMapper.insertSelective(role);
    }

    @Override
    public void deleteRole(String ids) {
         roleMapper.deleteRole(ids);
    }

    @Override
    public void updateRole(Role role) {
        roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public List getPermissionByRId(Integer roleid) {
        List<String> list=rolePerMapper.getPermissionByRId(roleid);
        List<Permission> list2=permissionMapper.getPerAll();
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list2.size(); j++) {
                if(list.get(i).equals(list2.get(j).getId().toString())){
                    list2.get(j).setChecked("true");
                }
            }
        }
        return list2;
    }

    @Override
    public int updateRolePermiss(Integer[] perids, Integer roleid) {
        int i=rolePerMapper.deletePerByroleid(roleid);
        if(i>=0){
           for (int j=0;j<perids.length;j++){
               RolePer rolePer=new RolePer();
               rolePer.setPerid(perids[j]);
               rolePer.setRoleid(roleid);
               i=rolePerMapper.insert(rolePer);
           }
        }
        return i;
    }

    @Override
    public void addPer(Permission permission) {
        permissionMapper.insertSelective(permission);
    }

    @Override
    public void deletePer(String ids) {
       permissionMapper.deletePer(ids);
    }

    @Override
    public void updatePer(Permission permission) {
          permissionMapper.updateByPrimaryKeySelective(permission);
    }

    @Override
    public Map queryPer(Integer page, Integer rows, Permission permission) {
        int sta=(page-1)*rows;
        System.out.println(sta);
        System.out.println(page);
        System.out.println(rows);
        List<Permission> list=permissionMapper.queryPer(sta,rows,permission);
        long percount=permissionMapper.queryPerCount();
        System.out.println(percount);
        Map map=new HashMap();
        map.put("rows",list);
        map.put("total",percount);
        return map;
    }


}
