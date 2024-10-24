package com.pojo;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class page<T>{
     long total;
     List<T> records;

     //应对字段未访问的警告用的，没有任何意义
     void cir(){
          System.out.println(total);
          System.out.println(records);
     }
}
