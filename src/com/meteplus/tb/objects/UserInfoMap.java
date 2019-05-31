/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.meteplus.tb.objects;

import java.util.HashMap;

/**
 *
 * @author HuangMing
 */
public class UserInfoMap {
    private static final HashMap<String,String> phone_wxpublicid_map=new HashMap<>();
    private static final Object synObject=new Object();
    public static String getWxPublicIdByPhone(final String phone){
        String wxpublicid=null;
        if(phone!=null){
            synchronized(synObject){
                wxpublicid=phone_wxpublicid_map.get(phone); 
            }
        }
        return wxpublicid;
    }
    public static void setPhoneWxPublicIdPair(final String phone,final String wxpublicid){
        if(phone!=null&&wxpublicid!=null){
            synchronized(synObject){
                phone_wxpublicid_map.put(phone, wxpublicid);
            }
        }        
    }
}
