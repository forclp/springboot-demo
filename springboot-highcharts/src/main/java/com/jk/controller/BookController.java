/**
 * Copyright (C), 2019, 金科教育
 * FileName: BookController
 * Author:   zyl
 * Date:     2019/8/8 14:23
 * History:
 * zyl          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.jk.controller;

import com.jk.model.Book;
import com.jk.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.management.ObjectName;
import java.util.*;

/**
 * 〈一句话功能简述〉<br> 
 * 〈a〉
 *
 * @author clp
 * @create 2019/8/12
 * @since 1.0.0
 */
@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @RequestMapping("toshow")
    public String toshow(){
        return "show";
    }
    @RequestMapping("toshowYear")
    public String toshowYear(){
        return "showYear";
    }

    @RequestMapping("queryBook")
    @ResponseBody
    public List<Map<String,Object>> queryBook(){
        List<Map<String,Object>> list=bookService.queryBook();
        List<Map<String,Object>> list1=new ArrayList<>();
       
        for(Map<String,Object> map1:list){
           System.out.println("11111");
           System.out.println("测试");
            Map<String,Object> map=new HashMap<>();
            Integer  object = Integer.parseInt(map1.get("类型").toString()) ;
              if(object==1){
                map.put("name","小说");
              }
              else if(object==2){
                map.put("name","科幻");
              }
              else{
                  map.put("name","校园");
              }
            map.put("y",map1.get("总个数"));
            map.put("sliced","true");
            map.put("selected","true");
            list1.add(map);
        }

        return list1;
    }
    @RequestMapping("queryYear")
    @ResponseBody
    public List<Map<String,Object>> queryYear(){
        List<Map<String,Object>> list=bookService.queryYear();
        List<Map<String,Object>> list1=new ArrayList<>();
        System.out.println(list);
        for(Map<String,Object> map1:list){

            Map<String,Object> map=new HashMap<>();
            Integer  object = Integer.parseInt(map1.get("年份").toString()) ;
              if(object==2019){
                map.put("name","2019年");
              }
              else if(object==2018){
                map.put("name","2018年");
              }
              else if(object==2017){
                  map.put("name","2017年");
              }
            Integer  object1 = Integer.parseInt(map1.get("总个数").toString()) ;
            int[] aa=new int[]{object1};
              map.put("data",aa);
            list1.add(map);
        }

        return list1;
    }


}
