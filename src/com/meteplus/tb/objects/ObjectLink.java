/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.meteplus.tb.objects;

import com.alibaba.fastjson.JSONObject;


/**
 *
 * @author HuangMing
 */
public class ObjectLink {

    public String _id;
    public String _parentId;
    public String _linkedId;
    public String _creatorId;
    //public int  __v;
    public String created;
    public String linkedType;
    public String  parentType;
    public Object creator;
    public String title;
    public String project;
   
    public static ObjectLink createInstanceByJson(JSONObject jsObjectLink){
        ObjectLink objLink=null;
        try{
            objLink=new ObjectLink();
            objLink._id=jsObjectLink.getString("_id");
            objLink._parentId=jsObjectLink.getString("_parentId");
            objLink._linkedId=jsObjectLink.getString("_linkedId");
            objLink._creatorId=jsObjectLink.getString("_creatorId");
            
            //objLink.__v=jsObjectLink.getInt("__v");
            objLink.created=jsObjectLink.getString("created");
            objLink.linkedType=jsObjectLink.getString("linkedType");
            objLink.parentType=jsObjectLink.getString("parentType");
           // objLink.creator=jsObjectLink.getString("creator");
           // objLink.title=jsObjectLink.getString("title");
           // objLink.project=jsObjectLink.getString("project");
           
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return objLink;
    }
        
}
