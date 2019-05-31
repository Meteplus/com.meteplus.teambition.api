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
public class Stage {
    public String _id;
    public String name;
    public String _creatorId;
    public String _tasklistId;
    public String _projectId;
    public Boolean isArchived;
    public Integer totalCount;
    public Integer order;
    public static Stage createInstanceByJson(JSONObject jsStage){
        Stage stage=null;
        if(jsStage!=null){
            stage=new Stage();
            stage._id=jsStage.getString("_id");
            stage.name=jsStage.getString("name");
            stage._creatorId=jsStage.getString("_creatorId");
            stage._tasklistId=jsStage.getString("_tasklistId");
            stage._projectId=jsStage.getString("_projectId");
            stage.isArchived=jsStage.getBoolean("isArchived");
            stage.totalCount=jsStage.getInteger("totalCount");
            stage.order=jsStage.getInteger("order");
        }
        return stage;
    }      
    
}
