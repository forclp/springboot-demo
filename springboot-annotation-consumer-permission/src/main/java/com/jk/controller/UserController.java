/**
 * Copyright (C), 2019, XXX有限公司
 * FileName: UserController
 * Author:   clp
 * Date:     2019/8/9 15:47
 * Description: 用户--控制层
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.jk.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jk.model.Log;
import com.jk.model.Permission;
import com.jk.model.Role;
import com.jk.model.User;
import com.jk.service.UserService;
import com.jk.util.ExportExcel;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 〈一句话功能简述〉<br> 
 * 〈用户--控制层〉
 *
 * @author clp
 * @create 2019/8/9
 * @since 1.0.0
 */
@RequestMapping("/user")
@Controller
public class UserController {

    @Reference
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("login")
    public  String login(){
        return "login";
    }

    @RequestMapping("tree")
    public  String tree(){
        return "tree";
    }




    @RequestMapping("loginUser")
    @ResponseBody
    public String loginUser(User user, HttpSession session){
        User user2=userService.loginUser(user);
        if(user2!=null){
            session.setAttribute("loginUser",user2);
            return "1"; //登陆成功
        }else{
            return "2"; //账号密码错误
        }

    }
    @RequestMapping("zx")
    public String zx(HttpSession session){
        session.removeAttribute("loginUser");
        return "login";
    }

    @RequestMapping("queryTree")
    @ResponseBody
    public List<Permission> queryTree(Integer id,HttpSession session){
        User user= (User) session.getAttribute("loginUser");
        List<Permission> list=new ArrayList();
        String key="tree"+user.getUserid();
        if(redisTemplate.hasKey(key)){
            System.out.println("====缓存===");
            list=redisTemplate.opsForList().range(key,0,-1);
        }else{
            System.out.println("====数据库===");
            list=userService.queryTree(id,user.getUserid());
            for(int i=0;i<list.size();i++){
                redisTemplate.opsForList().rightPush(key,list.get(i));
            }
            redisTemplate.expire(key,1, TimeUnit.MINUTES);
        }
        return list;
    }



    @RequestMapping("showUserList")
    public  String showUserList(){
        return "userlist";
    }


    //queryUser 用户列表查询
    @RequestMapping("queryUser")
    @ResponseBody
    public Map queryUser(Integer page,Integer rows,User user){

        return userService.queryUser(page,rows,user);
    }

    //addUserlist 用户新增
    @RequestMapping("addUserlist")
    @ResponseBody
    public void addUserlist(User user){
        userService.addUserlist(user);
    }

    //deleteUser 批量删除
    @RequestMapping("deleteUser")
    @ResponseBody
    public void deleteUser(String ids){
        userService.deleteUser(ids);
    }

    //updateUser 修改
    @RequestMapping("updateUser")
    @ResponseBody
    public void updateUser(User user){
        userService.updateUser(user);
    }

    //getRoleByUid 用户赋角色回显  根据用户id 查询菜单
    @RequestMapping("getRoleByUid")
    @ResponseBody
    public List<Role> getRoleByUid(Integer uid){
        return userService.getRoleByUid(uid);
    }

    //updateUserRole 用户赋角色
    @RequestMapping("updateUserRole")
    @ResponseBody
    public int updateUserRole(Integer[] roleids,Integer uid2){
       int i=userService.updateUserRole(roleids,uid2);
        if(i<0){
            return 2;
        }
        return 1;
    }

    @RequestMapping("exportExcel")
    @ResponseBody
    public void exportExcel(HttpServletResponse response){
        //导出的excel的标题
        String title = "用户管理";
        //导出excel的列名
        String[] rowName = {"编号","用户名称","用户密码"};
        //导出的excel数据
        List<Object[]>  dataList = new ArrayList<Object[]>();
        //查询的数据库的书籍信息
        List<User> list=   userService.query();
        //循环书籍信息
        for(User user:list){
            Object[] obj =new Object[rowName.length];
            obj[0]=user.getUserid();
            obj[1]=user.getUsername();
            obj[2]=user.getUserpass();
            dataList.add(obj);
        }
        ExportExcel exportExcel =new ExportExcel(title,rowName,dataList,response);
        try {
            exportExcel.export();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("importExcel")
    public String importExcel(MultipartFile file, HttpServletResponse response){
        //获得上传文件上传的类型
        String contentType = file.getContentType();
        //上传文件的名称
        String fileName = file.getOriginalFilename();
        //获得文件的后缀名
        String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //上传文件的新的路径
        //生成新的文件名称
        String filePath = "./src/main/resources/templates/fileupload/";
        //创建list集合接收excel中读取的数据
        List<User> list =new ArrayList<>();
        try {
            uploadFile(file.getBytes(), filePath, fileName);
            SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
            //通过文件忽的WorkBook
            FileInputStream fileInputStream = new FileInputStream(filePath+fileName);
            Workbook workbook = WorkbookFactory.create(fileInputStream);
            //通过workbook对象获得sheet页 有可能不止一个sheet
            for(int i=0 ;i<workbook.getNumberOfSheets();i++){
                //获得里面的每一个sheet对象
                Sheet sheetAt = workbook.getSheetAt(i);
                //通过sheet对象获得每一行
                for(int j=3;j<sheetAt.getPhysicalNumberOfRows();j++){
                    //创建一个book对象接收excel的数据
                    User user=new User();
                    //获得每一行的数据
                    Row row = sheetAt.getRow(j);

                    //获得每一个单元格的数据

                    if(row.getCell(1)!=null && !"".equals(row.getCell(1))){
                        user.setUsername(this.getCellValue(row.getCell(1)));
                    }

                    if(row.getCell(2)!=null && !"".equals(row.getCell(2))){
                        user.setUserpass(this.getCellValue(row.getCell(2)));
                    }


                    list.add(user);
                }
            }
            userService.add(list);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "userlist";
    }

    //上传文件的方法
    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    //判断从Excel文件中解析出来数据的格式
    private static String getCellValue(Cell cell){
        String value = null;
        //简单的查检列类型
        switch(cell.getCellType())
        {
            case Cell.CELL_TYPE_STRING://字符串
                value = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC://数字
                long dd = (long)cell.getNumericCellValue();
                value = dd+"";
                break;
            case Cell.CELL_TYPE_BLANK:
                value = "";
                break;
            case Cell.CELL_TYPE_FORMULA:
                value = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BOOLEAN://boolean型值
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_ERROR:
                value = String.valueOf(cell.getErrorCellValue());
                break;
            default:
                break;
        }
        return value;
    }
    //判断从Excel文件中解析出来数据的格式
    private static String getCellValue(XSSFCell cell){
        String value = null;
        //简单的查检列类型
        switch(cell.getCellType())
        {
            case HSSFCell.CELL_TYPE_STRING://字符串
                value = cell.getRichStringCellValue().getString();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC://数字
                long dd = (long)cell.getNumericCellValue();
                value = dd+"";
                break;
            case HSSFCell.CELL_TYPE_BLANK:
                value = "";
                break;
            case HSSFCell.CELL_TYPE_FORMULA:
                value = String.valueOf(cell.getCellFormula());
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN://boolean型值
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            case HSSFCell.CELL_TYPE_ERROR:
                value = String.valueOf(cell.getErrorCellValue());
                break;
            default:
                break;
        }
        return value;
    }



    @RequestMapping("toquerylog")
    public String toquerylog(){
        return "showlog";
    }

    @RequestMapping("querlog")
    @ResponseBody
    public Map querlog(Integer page, Integer rows, Log log){
      return userService.querlog(page,rows,log);
    }


    @RequestMapping("toQueryRole")
    public String toQueryRole(){
        return "rolelist";
    }



    @RequestMapping("queryRole")
    @ResponseBody
    public Map queryRole(Integer page,Integer rows,Role role){
        return userService.queryRole(page,rows,role);
    }

    @RequestMapping("addRole")
    @ResponseBody
    public void addRole(Role role){
        userService.addRole(role);
    }

    @RequestMapping("deleteRole")
    @ResponseBody
    public void deleteRole(String ids){
        userService.deleteRole(ids);
    }

    @RequestMapping("updateRole")
    @ResponseBody
    public void updateRole(Role role){
        userService.updateRole(role);
    }

    @RequestMapping("getPermissionByRId")
    @ResponseBody
    public List getPermissionByRId(Integer roleid){
        System.out.println(roleid);
        return userService.getPermissionByRId(roleid);
    }

    @RequestMapping("updateRolePermiss")
    @ResponseBody
    public String updateRolePermiss(Integer[] perids,Integer roleid){
        int i=userService.updateRolePermiss(perids,roleid);
        if(i>=0){
            return "1";
        }else{
            return "2";
        }
    }


    @RequestMapping("toQueryPer")
    public String toQueryPer(){
        return "perlist";
    }

    @RequestMapping("queryPer")
    @ResponseBody
    public Map queryPer(Integer page,Integer rows,Permission permission){
        return userService.queryPer(page,rows,permission);
    }


    @RequestMapping("addPer")
    @ResponseBody
    public void addPer(Permission permission){
        userService.addPer(permission);
    }

    @RequestMapping("deletePer")
    @ResponseBody
    public void deletePer(String ids){
        userService.deletePer(ids);
    }

    @RequestMapping("updatePer")
    @ResponseBody
    public void updatePer(Permission permission){
        userService.updatePer(permission);
    }



}
