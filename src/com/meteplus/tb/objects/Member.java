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
public class Member {
    
    public String _id;
    public String _userId;
    public String _boundToObjectId;
    public String boundToObjectType;
    public String  _roleId;
    public String visited;
    public String joined;
    public Boolean  pushStatus;
    public String nickname;
    public String nicknamePy;
    public String nicknamePinyin;
    public Boolean hasVisited;
    public String _memberId;
    //public Object location;
    public String website;
    public String latestActived;
    public Boolean isActive;
    public String email;
    public String name;
    public String avatarUrl;
    public String title;
    public String pinyin;
    public String py;    
    
    public static Member createInstanceByJson(JSONObject jsMember){
        Member member=null;
        if(jsMember!=null){

            member=new Member();
            member._id=jsMember.getString("_id");
            member._userId=jsMember.getString("_userId");
            member._boundToObjectId=jsMember.getString("_boundToObjectId");
            member.boundToObjectType=jsMember.getString("boundToObjectType");

            member._roleId=jsMember.getString("_roleId");
            member.visited=jsMember.getString("visited");
            member.joined=jsMember.getString("joined");
            member.pushStatus=jsMember.getBoolean("pushStatus");
            member.nickname=jsMember.getString("nickname");
            member.nicknamePy=jsMember.getString("nicknamePy");
            member.nicknamePinyin=jsMember.getString("nicknamePinyin");
            member.hasVisited=jsMember.getBoolean("hasVisited");
            member._memberId=jsMember.getString("_memberId");
            member.website=jsMember.getString("website");
            member.latestActived=jsMember.getString("latestActived");
            member.isActive=jsMember.getBoolean("isActive");
            member.email=jsMember.getString("email");
            member.name=jsMember.getString("name");
            member.avatarUrl=jsMember.getString("avatarUrl");
            member.title=jsMember.getString("title");
            member.pinyin=jsMember.getString("pinyin");
            member.py=jsMember.getString("py");            
        }
        
        return member;
    }
    
    @Override
    public String toString(){
        JSONObject jsMember=new JSONObject();
        jsMember.put("_id", _id);
        jsMember.put("_userId", _userId);
        jsMember.put("_boundToObjectId", _boundToObjectId);
        jsMember.put("boundToObjectType", boundToObjectType);
        jsMember.put("_roleId", _roleId);
        jsMember.put("visited", visited);
        jsMember.put("joined", joined);
        jsMember.put("pushStatus", pushStatus);
        jsMember.put("nickname", nickname);
        jsMember.put("nicknamePy", nicknamePy);
        jsMember.put("nicknamePinyin", nicknamePinyin);
        jsMember.put("hasVisited", hasVisited);
        jsMember.put("_memberId", _memberId);
        jsMember.put("website", website);
        jsMember.put("latestActived", latestActived);
        jsMember.put("isActive", isActive);
        jsMember.put("email", email);
        jsMember.put("name", name);
        jsMember.put("avatarUrl", avatarUrl);
        jsMember.put("title", title);
        jsMember.put("pinyin", pinyin);
        jsMember.put("py", py);
        return jsMember.toString();
    }
    
    
}
