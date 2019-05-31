/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.meteplus.tb.objects;

import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;

/**
 *
 * @author HuangMing
 */
public class SubTask {
    String _id;
    String _projectId;
    String _creatorId;
    String content;
    Boolean isDone;
    Object _executorId;
    String _taskId;
    Object dueDate;
    Integer order;
    public String executor_name;
    public String executor_avatarUrl;
    public String executor_id;
    
    public static SubTask createInstanceByJson(JSONObject jsTask){
        SubTask task=null;
        if(jsTask!=null){
            task=new SubTask();
            task._id=jsTask.getString("_id");
            task._creatorId=jsTask.getString("_creatorId");
            task._executorId=jsTask.get("_executorId");
            task._projectId=jsTask.getString("_projectId");
            task._taskId=jsTask.getString("_taskId");
            task.order=jsTask.getIntValue("order");
            task.isDone=jsTask.getBoolean("isDone");
            task.dueDate=jsTask.get("dueDate");
            task.content=jsTask.getString("content");
            
            Object jsObj=jsTask.get("executor");
            //
            if(jsObj instanceof JSONObject){
                JSONObject jsExecutor=(JSONObject)jsObj;
                task.executor_name=jsExecutor.getString("name");
                task.executor_avatarUrl=jsExecutor.getString("avatarUrl");
                task.executor_id=jsExecutor.getString("_id");                
            }
 
        }
        
        return task;
    }      
    
}
