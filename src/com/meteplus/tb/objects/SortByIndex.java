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
public class SortByIndex  implements Comparator {
    @Override
    public int compare(Object obj1, Object obj2) {
        mem_Stage stage1 = (mem_Stage) obj1;
        mem_Stage stage2 = (mem_Stage) obj2;
        if (stage1.stageIndex > stage2.stageIndex){
            return 1;
        }
        return -1;
    }
}