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
public class Tag {
    public String _id;
    public String _projectId;
    public String name;
    public String _creatorId;
    public String updated;
    public String created;
    public Object isArchived;
    public String color;
    
    public static Tag createInstanceByJson(JSONObject jsTag){
        Tag tag=null;
        if(jsTag!=null){
            tag=new Tag();
            tag._id=jsTag.getString("_id");
            tag._projectId=jsTag.getString("_projectId");
            tag.name=jsTag.getString("name");
            tag._creatorId=jsTag.getString("_creatorId");
            tag.updated=jsTag.getString("updated");
            tag.created=jsTag.getString("created");
            tag.isArchived=jsTag.get("isArchived");
            tag.color=jsTag.getString("color");
        }
        return tag;
    }    
    
}
