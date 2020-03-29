package com.example.springbootgettutor.service;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
@SpringBootTest
public class ActionService {

    @Test
    public void map_test01() {
        Map<String,Double> map=new TreeMap<String,Double>();
        map.put("mit", 3795104.300);
        map.put("ramin", 6.155);
        map.put("research", 889.159);
        map.put("mit1", 3795105.300);
        map.put("ramin1", 7.155);
        map.put("research1", 900.159);
        map.put("mix1", 2.375);
        map.put("gorgeou1", 10.341);
        map.put("shneiderman1", 8.775);

        List<Map.Entry<String,Double>> lists=new ArrayList<Map.Entry<String,Double>>(map.entrySet());
        Collections.sort(lists,new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> o1,Map.Entry<String, Double> o2)
            {
                double q1=o1.getValue();
                double q2=o2.getValue();
                double p=q2-q1;
                if(p>0){
                    return 1;
                }
                else if(p==0){
                    return 0;
                }
                else
                    return -1;
            }
        });

        if(lists.size()>=10){
            //lists.subList()用法
            for(Map.Entry<String, Double> set:lists.subList(0, 10)){
                map.put(set.getKey(), set.getValue());
            }
        }
        else{
            for(Map.Entry<String, Double> set:lists){
                map.put(set.getKey(), set.getValue());
            }
        }

        System.out.println(lists);
    }


}
