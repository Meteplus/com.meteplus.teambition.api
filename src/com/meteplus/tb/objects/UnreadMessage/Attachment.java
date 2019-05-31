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
public class Attachment {
        
        private   String _id="";
        private   String _creatorId="";
        private   String _fileName="";
        private   String _fileKey="";
        private   String _downloadUrl="";
        private   long _fileSize=0;
        private   int _order=0;
        private   String _thumbnail="";
        private   String _thumbnailUrl="";
        private   String _fileType="";
        private   String _fileCategory="";
        private   String _previewUrl="";
        
        public Attachment(JSONObject jsAttachement){
            
            if(jsAttachement!=null){
                this._id=jsAttachement.getString("_id");
                this._creatorId=jsAttachement.getString("_creatorId");
                this._fileName=jsAttachement.getString("fileName");
                this._fileKey=jsAttachement.getString("fileKey");
                this._downloadUrl=jsAttachement.getString("downloadUrl");
                this._fileSize=jsAttachement.getLong("fileSize");
                this._order=jsAttachement.getIntValue("_order");
                this._thumbnail=jsAttachement.getString("thumbnail");
                this._thumbnailUrl=jsAttachement.getString("thumbnailUrl");
                this._fileType=jsAttachement.getString("fileType");
                this. _fileCategory=jsAttachement.getString("fileCategory");
                this._previewUrl=jsAttachement.getString("previewUrl");
            }
            
        }
        
        public String getId(){
            return this._id;
        }
        
        public String getCreatorId(){
            return this._creatorId;
        }
        
        public String getFileName(){
            return this._fileName;
        }
        
        public String getFileKey(){
            return this._fileKey;
        }
        
        public String getDownloadUrl(){
            return this._downloadUrl;
        }
        
        public long getFileSize(){
            return this._fileSize;
        }
        
        public int getOrder(){
            return this._order;
        }
        
        public String getThumbnail(){
            return this._thumbnail;
        }
        
        public String getThumbnailUrl(){
            return this._thumbnailUrl;
        }
        
        public String getFileType(){
            return this._fileType;
        }
        
        public String getFileCategory(){
            return this._fileCategory;
        }
        
        public String getPreviewUrl(){
            return this._previewUrl;
        }
        
        
            
}
