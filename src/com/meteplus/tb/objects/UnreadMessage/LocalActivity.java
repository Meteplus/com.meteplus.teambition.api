/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.meteplus.tb.objects.UnreadMessage;
 
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meteplus.utils.Utils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * 未读消息结构
 * @author HuangMing
 */
public class LocalActivity {
    
    private JSONObject _src;
    
    private String title;//消息内容
    private String subtitle;//通过title和objectType 判断是否系统消息，系统消息此值为“系统消息",用户消息此值为任务名称。
    private String _objectId;
    private String _objectType;

    private String _id;
    private String _userId;
    
    private String _latestActivityId;
    private String latestActivityAction;
    
    private String action;
    
    private int unreadActivitiesCount;

    private final ArrayList<MentionedOne> mentionedOnes=new ArrayList<>();
    private Creator creator;
    
    private String created;
    private String updated;
    
    private Boolean isRead;
    
    private String comment;
    
    private final ArrayList<Attachment> attachments=new ArrayList<>();
    
    public JSONObject getSrc(){
        return _src;
    }
   
    public static LocalActivity createInstanceByJson(JSONObject jsActivity){
        
        if(jsActivity==null){
            return null;
        }
        
        LocalActivity activity=new LocalActivity();
        activity._src=jsActivity;
        activity._id=jsActivity.getString("_id");
        activity._userId=jsActivity.getString("_userId");
        activity._latestActivityId=jsActivity.getString("_latestActivityId");
        activity.latestActivityAction=jsActivity.getString("latestActivityAction");
        activity.unreadActivitiesCount=jsActivity.getIntValue("unreadActivitiesCount");
        activity._objectId=jsActivity.getString("_objectId");
        activity.isRead=jsActivity.getBoolean("isRead");
        activity._objectType=jsActivity.getString("objectType");
        activity.title=jsActivity.getString("title");
        activity.subtitle=jsActivity.getString("subtitle");
        activity.created=jsActivity.getString("created");
        activity.updated=jsActivity.getString("updated");
        activity.action=jsActivity.getString("action");
        
        parseactivityContent(activity,jsActivity.getJSONObject("content"));
        
        
        activity.creator=new Creator(jsActivity.getJSONObject("creator"));
        
        return activity;
    }    
    
    private static void parseMentionedOnes(LocalActivity activity,JSONObject jsMentions){
       if(jsMentions!=null){
            Iterator it= jsMentions.keySet().iterator();
            while(it.hasNext()){
                String key=(String)it.next();
                String value=jsMentions.getString(key);
                MentionedOne mentionedOne=new MentionedOne(key,value);
                activity.mentionedOnes.add(mentionedOne);
            }
       }
    }
    
    private static void parseactivityContent(LocalActivity activity,JSONObject jsonActivityContent){
        if(jsonActivityContent==null){
            return;
        }
        
        activity.comment=jsonActivityContent.getString("comment");
        JSONArray jsAttachments=jsonActivityContent.getJSONArray("attachments");
        if(jsAttachments!=null){
            for(int i=0;i<jsAttachments.size();i++){
                JSONObject jsAttachment=jsAttachments.getJSONObject(i);
                if(jsAttachment!=null){
                    Attachment attachment=new Attachment(jsAttachment); 
                    activity.attachments.add(attachment);
                }
            }
        }
        
        parseMentionedOnes(activity,jsonActivityContent.getJSONObject("mentions"));
        
    }
    
    
    //title和comment目前看起来是一样的，不过title有可能在字数超过一定数量是会被截断
    public String getTitle(){
        return this.title;
    }
    
    public String getSubtitle(){
        return this.subtitle;
    }
    
    public String getComment(){
        return this.comment;
    }
    
    public String getObjectId(){
        return this._objectId;
    }
    
    public String getObjectType(){
        return this._objectType;
    }
    
    public String getId(){
        return this._id;
    }
    
    public String getUserId(){
        return this._userId;
    }
    
    public String getLatestActivityId(){
        return this._latestActivityId;
    }
    
    public String getLatestActivityAction(){
        return this.latestActivityAction;
    }
    
    public int getUnreadActivitiesCount(){
        return this.unreadActivitiesCount;
    }
    
    public Creator getCreator(){
        return this.creator;
    }
    
    public boolean isRead(){
        return this.isRead;
    }
    
    public String getAction(){
        return this.action;
    }
    
    public Date getCreatedLocalTime(){
        if(created!=null){
            String createdtime=Utils.utc2Local(created, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd HH:mm:ss");
            try{
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = sdf.parse(createdtime);
                return date;
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public Date getUpdatedTime(){
        if(updated!=null){
            String createdtime=Utils.utc2Local(updated, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd HH:mm:ss");
            try{
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = sdf.parse(createdtime);
                return date;
            }catch(Exception e){
                e.printStackTrace();
            }        
        }
        return null;
    }
    
    public ArrayList<MentionedOne> getMentionedOnes(){
        return this.mentionedOnes;
    }
    
    public ArrayList<Attachment> getAttachments(){
        return this.attachments;
    }
    
    
    public boolean isSystemMessage(){
        return subtitle!=null&&subtitle.trim().equals("系统消息");
    }
    

}
