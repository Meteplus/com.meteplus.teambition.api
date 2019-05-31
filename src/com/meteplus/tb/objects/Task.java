/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.meteplus.tb.objects;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meteplus.utils.Utils;
import java.util.ArrayList;

/**
 *
 * @author HuangMing
 */
public class Task {
    public static final String PINBAN_TAG="579cc24111db9d1d5fca4fae";
    public static final String WLShejiIdFild="5b640051510d0900014febbd";
    
    public String _id;
    public String _creatorId;
    public Object _executorId;
    public String _projectId;
    public String _tasklistId;
    public ArrayList<String> tagIds;
    public String _stageId;
    public String visiable;
    public ArrayList<String> involveMembers;
  //    public String 567aba9727a96de55ed9ab49"
  //      ],
    public String updated;
    public String created;
    public Boolean isArchived;
    public Boolean isDone;
    public Integer priority;
    public Object dueDate;
    public Object accomplished;
    public String note;
    public String content;
    public Object _sourceId;
    public Object sourceDate;
    //public String subtasks": [],
    public Integer commentsCount;
    public Integer attachmentsCount;
    public Integer likesCount;
    public Integer objectlinksCount;
    public ArrayList<String> customer_phone=new ArrayList<>();
    //public String subtaskCount": {
   //   public String total": 0,
    //  public String done": 0
   //     },
//    public String executor": {
//      public String name": "姓名",
//      public String avatarUrl": "https://striker.teambition.net/thumbnail/110b42926231bf325ade5b7dc588c91adea5/w/100/h/100",
//      public String _id": "567aba9727a96de55ed9ab49"
//        },
    public String executor_name;
    public String executor_avatarUrl;
    public String executor_id;
    public Boolean isFavorite;  
    public String wlShejiId;
    public int askdinggao=0;
    public boolean ispinban;
	
    //public Boolean isPinBanTask=false;
    
    //public ArrayList<String> stageTraces=new ArrayList<>();
    
    public static Task createInstanceByJson(JSONObject jsTask){
        
        if(jsTask==null){
            return null;
        }
        
        Task task=new Task();
        task._id=jsTask.getString("_id");
        task.ispinban=jsTask.getBoolean("ispinban");
        task._creatorId=jsTask.getString("_creatorId");
        task._executorId=jsTask.get("_executorId");
        task._projectId=jsTask.getString("_projectId");
        task._tasklistId=jsTask.getString("_tasklistId");
        task._stageId=jsTask.getString("_stageId");
        //task.visiable=jsTask.getString("visiable");
        task.updated=jsTask.getString("updated");
        task.created=jsTask.getString("created");
        task.isArchived=jsTask.getBoolean("isArchived");
        task.isDone=jsTask.getBoolean("isDone");
        task.priority=jsTask.getInteger("priority");
        task.dueDate=jsTask.get("dueDate");
        task.accomplished=jsTask.get("accomplished");
        task.note=jsTask.getString("note");
        task.content=jsTask.getString("content");
        task._sourceId=jsTask.get("_sourceId");
        task.sourceDate=jsTask.get("sourceDate");
        task.commentsCount=jsTask.getInteger("commentsCount");
        task.objectlinksCount=jsTask.getInteger("objectlinksCount");
        task.attachmentsCount=jsTask.getInteger("attachmentsCount");
        //task.likesCount=jsTask.getInt("likesCount");

        //"customfields": [],
        /**
            "customfields": [
              {
                "_customfieldId": "5b640051510d0900014febbd",
                "type": "text",
                "value": [],
                "values": [
                  "wlsheji01@meteplus.com"
                ]
              }
            ],
         */
        
        JSONArray jsCustomfieldsArr=jsTask.getJSONArray("customfields");
        if(jsCustomfieldsArr!=null){
            final int size=jsCustomfieldsArr.size();
            if(size>0){
                JSONObject customfield=jsCustomfieldsArr.getJSONObject(0);
                if(customfield!=null){
                    String _customfieldId=customfield.getString("_customfieldId");
                    if(_customfieldId!=null&&_customfieldId.trim().equals(WLShejiIdFild)){
                        JSONArray jsValuesArr=customfield.getJSONArray("values");
                        if(jsValuesArr!=null&&jsValuesArr.size()>0){
                            task.wlShejiId=jsValuesArr.getString(0);
                        }
                    }
                    //WLShejiIdFild
                }
            }
            
        }        

//        try{
        Object jsObj=jsTask.get("executor");
        //
        if(jsObj!=null&&jsObj instanceof JSONObject){
            JSONObject jsExecutor=(JSONObject)jsObj;
            task.executor_name=jsExecutor.getString("name");
            task.executor_avatarUrl=jsExecutor.getString("avatarUrl");
            task.executor_id=jsExecutor.getString("_id");                
        }
//        }catch(Exception e){
//            //e.printStackTrace();
//        }

        task.isFavorite=jsTask.getBoolean("isFavorite");
        JSONArray jsInvolvedArr=jsTask.getJSONArray("involveMembers");
        if(jsInvolvedArr!=null){
            final int size=jsInvolvedArr.size();
            if(task.involveMembers==null){
                task.involveMembers=new ArrayList<>(size);
            }
            for(int i=0;i<size;i++){
                
                String member_id=jsInvolvedArr.getString(i);
                if(member_id!=null){
                    task.involveMembers.add(member_id);
                }
            }
        }

        //tagIds
        JSONArray tagIds=jsTask.getJSONArray("tagIds");
        if(tagIds!=null){
            final int size=tagIds.size();
            if( task.tagIds==null){
                 task.tagIds=new ArrayList<>(size);
            }
            for(int i=0;i<size;i++){
                String tagId=tagIds.getString(i);
                if(tagId!=null){
                    task.tagIds.add(tagId);
                }
            }
        }

        task.updateCustomerPhoneNum();
            
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        
        return task;
    }      
    
    public void updateCustomerPhoneNum(){
        if(note!=null){
            customer_phone=Utils.getMobile(note);
//            
//            if(strMobileList!=null){
//                int index=strMobileList.indexOf(",");
//                if(index>0){
//                    customer_phone=strMobileList.substring(0,index).trim();
//                }else{
//                    customer_phone=strMobileList.trim();
//                }
//            }           
        }
    }
    
    public boolean isPinbanTask(){
        if(tagIds!=null){
            for (String tagId : tagIds) {
                  if(tagId!=null&&tagId.equals(PINBAN_TAG)){
                      return true;
                  }
            }
        }
        return false;
    }
    
    //content格式目前是 日期任务名称:客户联系账号
    //比如 0904 广州高速:QQ 9388833
    public String makeChunkedContent(){
        
        if(content==null){
            return null;
        }
        String retContent=content.trim();
        int i=0;
        for(;i<retContent.length();i++){
            if(retContent.charAt(i)>'9'||retContent.charAt(i)<'0'){
                break;
            }
        }
        
        if(i<retContent.length()){
            
            retContent=retContent.substring(i); 
            int index=retContent.indexOf(":");
            if(index<0){
                index=retContent.indexOf("：");
            }
            if(index>=0){
                retContent=retContent.substring(0,index); 
            }
            
            String upperRetContent=retContent.toUpperCase();
            index=upperRetContent.indexOf("QQ");
            if(index>=0){
                upperRetContent=upperRetContent.substring(0,index); 
            }
            
            index=upperRetContent.indexOf("TB");
            if(index>=0){
                upperRetContent=upperRetContent.substring(0,index); 
            }
            index=upperRetContent.indexOf("ID");
            if(index>=0){
                upperRetContent=upperRetContent.substring(0,index); 
            }            
            
            index=upperRetContent.indexOf("淘宝");
            if(index>=0){
                upperRetContent=upperRetContent.substring(0,index); 
            }
            
            index=upperRetContent.indexOf("微信");
            if(index>=0){
                upperRetContent=upperRetContent.substring(0,index); 
            }
            
            index=upperRetContent.indexOf("旺旺");
            if(index>=0){
                upperRetContent=upperRetContent.substring(0,index); 
            }
            
            index=upperRetContent.indexOf(";");
            if(index>=0){
                upperRetContent=upperRetContent.substring(0,index); 
            }
            
            index=upperRetContent.indexOf("；");
            if(index>=0){
                upperRetContent=upperRetContent.substring(0,index); 
            }
            
            retContent=upperRetContent;
            
        }else{
            retContent="";
        }
        
        return retContent;
        
        
    }
    
    
    
}
