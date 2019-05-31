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
public class Message {
    
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
    
   
    public static Message createInstanceByJson(JSONObject jsMessage){
        
        if(jsMessage==null){
            return null;
        }
        
        Message message=new Message();
        message._id=jsMessage.getString("_id");
        message._userId=jsMessage.getString("_userId");
        message._latestActivityId=jsMessage.getString("_latestActivityId");
        message.latestActivityAction=jsMessage.getString("latestActivityAction");
        message.unreadActivitiesCount=jsMessage.getIntValue("unreadActivitiesCount");
        message._objectId=jsMessage.getString("_objectId");
        message.isRead=jsMessage.getBoolean("isRead");
        message._objectType=jsMessage.getString("objectType");
        message.title=jsMessage.getString("title");
        message.subtitle=jsMessage.getString("subtitle");
        message.created=jsMessage.getString("created");
        message.updated=jsMessage.getString("updated");
        message.action=jsMessage.getString("action");
        
        parseactivityContent(message,jsMessage.getJSONObject("activityContent"));
        parseMentionedOnes(message,jsMessage.getJSONObject("mentions"));
        
        message.creator=new Creator(jsMessage.getJSONObject("creator"));
        
        return message;
    }    
    
    private static void parseMentionedOnes(Message message,JSONObject jsMentions){
       if(jsMentions!=null){
            Iterator it= jsMentions.keySet().iterator();
            while(it.hasNext()){
                String key=(String)it.next();
                String value=jsMentions.getString(key);
                MentionedOne mentionedOne=new MentionedOne(key,value);
                message.mentionedOnes.add(mentionedOne);
            }
       }
    }
    
    private static void parseactivityContent(Message message,JSONObject jsonActivityContent){
        if(jsonActivityContent==null){
            return;
        }
        
        message.comment=jsonActivityContent.getString("comment");
        JSONArray jsAttachments=jsonActivityContent.getJSONArray("attachments");
        if(jsAttachments!=null){
            for(int i=0;i<jsAttachments.size();i++){
                JSONObject jsAttachment=jsAttachments.getJSONObject(i);
                if(jsAttachment!=null){
                    Attachment attachment=new Attachment(jsAttachment); 
                    message.attachments.add(attachment);
                }
            }
        }
        
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
