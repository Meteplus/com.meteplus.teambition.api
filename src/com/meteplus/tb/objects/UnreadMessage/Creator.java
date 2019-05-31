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
public class Creator {
        
        private   String _id="";
        private   String _name="";
        private   String _email="";
        private   String _avatarUrl="";        
        
        public Creator( JSONObject jsCreator){
            if(jsCreator!=null){
                this._id=jsCreator.getString("_id");
                this._name=jsCreator.getString("name");
                this._email=jsCreator.getString("email");
                this._avatarUrl=jsCreator.getString("avatarUrl"); 
            }
        }
        
        public String getId(){
            return _id;
        }
        
        public String getName(){
            return _name;
        }
        
        public String getEmail(){
            return _email;
        }
        
        public String getAvatarUrl(){
            return _avatarUrl;
        }
        
        @Override
        public String toString(){
            
            JSONObject jsCreator=new JSONObject();
            if(this._id!=null){
                jsCreator.put("_id", this._id);
            }
            
            if(this._name!=null){
                jsCreator.put("_name", this._name);
            }
            
            if(this._email!=null){
                jsCreator.put("_email", this._email);
            }
            
            if(this._avatarUrl!=null){
                jsCreator.put("_avatarUrl", this._avatarUrl);
            }
            
            return jsCreator.toString();
        }
            
}
