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
public class Project {
    
        public String _id;
        public String name;
        public String _creatorId;
        public String logo;
        public String py;
        public String pinyin;
        public Object description;
        public String category;
        public String _organizationId;
////        public String navigation": {
//        public String navigation_home;
//        public String navigation_tasks;
//        public String navigation_posts;
//        public String navigation_files;
//        public String navigation_events;
//        public String navigation_review;
//        public String navigation_tags;
//        public String navigation_bookkeeping;
////            },
//        public String visibility;
//        public String isPublic;
//        public String created;
//        public String updated;
//        public String isArchived;
//        public String inviteLink;
//        public String isStar;
//        public String hasRight;
//        public String hasOrgRight;
////        public String organization": {
//        public String organization_name;
//        public String organization_description;
//        public String organization_logo;
//        public String organization_isPublic;
//        public String organization_id;
//        public String organization_isExpired;
////            },
//        public String forksCount;
//        public String tasksCount;
//        public String postsCount;
//        public String eventsCount;
//        public String worksCount;
//        public String tagsCount;
//        public String _defaultRoleId;
////        public String creator": {
//        public String creator_name;
//        public String creator_avatarUrl;
//        public String creator_id;
////            },
//        public String unreadCount;
//        public String unreadMessageCount;
//        public String pushStatus;
//        public String canQuit;
//        public String canDelete;
//        public String canArchive;
//        public String canTransfer;
//        public String _roleId;
////        public String link": {
//        public String link_inviteLink;
//        public String link_mobileInviteLink;
//        public String link_signCode;
//        public String link_created;
//        public String link_expiration;
////            },
//        public String mobileInviteLink;
//        public String signCode;
//        public String starsCount;
//        public String _rootCollectionId;
//        public String _defaultCollectionId;
//        public String shortLink;
//        public String calLink;
//        public String taskCalLink;
//        public String _orgRoleId;
        
        public static Project createInstanceByJson(JSONObject jsProjects){
            Project proj=null;
            try{
                proj=new Project();
                proj._id=jsProjects.getString("_id");
                proj.name=jsProjects.getString("name");
                proj._creatorId=jsProjects.getString("_creatorId");
                proj.logo=jsProjects.getString("logo");
                proj.py=jsProjects.getString("py");
                proj.pinyin=jsProjects.getString("pinyin");
                proj.description=jsProjects.get("description");
                proj.category=jsProjects.getString("category");
                proj._organizationId=jsProjects.getString("_organizationId");      
            }catch(Exception e){
                e.printStackTrace();
            }

            return proj;
        }      
            
        
        
}
