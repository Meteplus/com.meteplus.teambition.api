/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.meteplus.tb.objects.UnreadMessage;
 
import com.alibaba.fastjson.JSONObject;

/**
 *
 * @author HuangMing
 */
public class MentionedOne {
        
        private final  String _id;
        private final  String _name;
        
        public MentionedOne(String id,String name){
            this._id=id;
            this._name=name;
        }
        
        public String getId(){
            return _id;
        }
        
        public String getName(){
            return _name;
        }
        
        @Override
        public String toString(){
            JSONObject jsMentionedOne=new JSONObject();
            if(this._id!=null){
                jsMentionedOne.put("_id", this._id);
            }       
            if(this._name!=null){
                jsMentionedOne.put("_name", this._name);
            }   
            return jsMentionedOne.toString();
        }
            
}
