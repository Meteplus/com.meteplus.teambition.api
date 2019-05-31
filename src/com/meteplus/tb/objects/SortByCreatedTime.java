/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.meteplus.tb.objects;

import java.util.Comparator;

/**
 *
 * @author HuangMing
 */
public class SortByCreatedTime  implements Comparator {
    @Override
    public int compare(Object obj1, Object obj2) {
        Task task1 = (Task) obj1;
        Task task2 = (Task) obj2;
        if (task1.created.compareTo(task2.created)>0){
            return 1;
        }
        return -1;
    }
}
