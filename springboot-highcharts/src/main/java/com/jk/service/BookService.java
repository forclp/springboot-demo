/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: BookService
 * Author:   zyl
 * Date:     2019/8/8 14:25
 * History:
 */
package com.jk.service;


import com.jk.model.Book;

import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author zyl
 * @create 2019/8/8
 * @since 1.0.0
 */
public interface BookService {


    List<Map<String, Object>> queryBook();

    List<Map<String, Object>> queryYear();
}
