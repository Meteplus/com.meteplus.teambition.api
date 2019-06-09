/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.meteplus.tb.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.meteplus.http.HttpRequest;
import com.meteplus.http.MPHttpClient;
import com.meteplus.http.wraper.HttpErrorResponseHandler;
import com.meteplus.http.wraper.MPHttpClientCaller;
import com.meteplus.tb.objects.ObjectLink;
import com.meteplus.tb.objects.Task;
import com.meteplus.utils.UrlBuilder;
import java.util.ArrayList;
import com.meteplus.tb.objects.UnreadMessage.Message;
import com.meteplus.utils.Utils;
import java.util.Date;
/**
 *
 * @author HuangMing
 */
public class TeambitionService {

   public static final String API_TEAMBITION="api.teambition.com/api";
   public static final String API_TEAMBITION_V2="api.teambition.com/api/v2";
   public static final String API_TEAMBITION_UNAUHTENED="www.teambition.com/api";
   public static final String SUBPATH_APPLICATIONS="applications";
   public static final String SUBPATH_ORGANIZATIONS="organizations";
   public static final String SUBPATH_PROJECTS="projects";
   public static final String SUBPATH_TASKLISTS="tasklists";
   public static final String SUBPATH_STAGES="stages";
   public static final String SUBPATH_TASKS="tasks";
   public static final String SUBPATH_SUBTASKS="subtasks";
   public static final String SUBPATH_USERS="users";
   public static final String SUBPATH_WORKS="works";
   public static final String SUBPATH_MESSAGES="messages";
   
   //---------- webhooks ----------
   public static String EVENT_TASK_CREATE="task.create";
   public static String EVENT_TASK_MOVE="task.move";
   public static String EVENT_TASK_DONE="task.done";
   public static String EVENT_TASK_REMOVE="task.remove";
   public static String EVENT_OBJECTLINK_CREATE="objectlink.create";
   public static String EVENT_OBJECTLINK_REMOVE="objectlink.remove";
   
   //---------- webhook qos --------   
   public static final int WEB_HOOK_QOS_SEND_ONCE_AND_RETURN=1;
   public static final int WEB_HOOK_QOS_TRY_UNTIL_RETURN_SUCESS=2;
   
   
   private HttpErrorResponseHandler httpErrorResponseHandler;
   
   private String appId;
   private String appSecret;
   private String access_token;
    
   private static TeambitionService instance;
   
   static{
       getInstance();
   }
   
   public TeambitionService(){}
   public TeambitionService(String appId,String appSecret,String access_token,
                            HttpErrorResponseHandler httpErrorResponseHandler){
       this.appId=appId;
       this.appSecret=appSecret;
       this.access_token=access_token;
       this.httpErrorResponseHandler=httpErrorResponseHandler;
   }
   
   public static TeambitionService getInstance(){
       if(instance==null){
           instance=new TeambitionService();
       }
       return instance;
   }
   
   public void setHttpErrorResponseHandler(HttpErrorResponseHandler httpErrorResponseHandler){
       this.httpErrorResponseHandler=httpErrorResponseHandler;
   }
   
   public HttpErrorResponseHandler getHttpErrorResponseHandler(){
       return httpErrorResponseHandler;
   }
   
   public TeambitionService setAppId(String appId){
       this.appId=appId;
       return this;
   }
   
   public String getAppId(){
       return this.appId;
   }
   
   public TeambitionService setAppSecret(String appSecret){
       this.appSecret=appSecret;
       return this;
   }
   
   public String getAppSecret(){
       return this.appSecret;
   }
   
   public TeambitionService setAccessToken(String access_token){
       this.access_token=access_token;
       return this;
   }
   
   public String getAccessToken(){
       return access_token;
   }
   
   private UrlBuilder createProjectUrl(String projectId,String opId){
       return new UrlBuilder().path(API_TEAMBITION).path(SUBPATH_PROJECTS).path(projectId)
               .path(opId).query("access_token",access_token);
   }
   
   private UrlBuilder createTaskUrl(String taskId,String opId){
       return new UrlBuilder().path(API_TEAMBITION).path(SUBPATH_TASKS).path(taskId)
               .path(opId).query("access_token",access_token);
   }   
   

   
   /**
    * 返回数据格式如下:
    * [
    *  {
    *   "_id":"5b0d4e6dc624ba0001d81ddc","name":"web","active":true,
    *   "events":["task.create","task.move","task.done","task.remove","objectlink.create"],
    *   "callbackURL":"http://www.zhibei.biz/webhook",
    *   "lastResponse":{"code":0,"message":""},"qos":1,
    *   "created":"2018-05-29T12:58:21.177Z",
    *   "updated":"2018-05-29T12:58:21.177Z"
    *  }
    * ]
    * @param projectId
    * @return 
    */
   public JSONArray getProjectWebHooks(String projectId){
       
        String url=new UrlBuilder().path(API_TEAMBITION).path(SUBPATH_PROJECTS).path(projectId)
               .path("hooks")
               .query("access_token",access_token)
               .toString();  

        return MPHttpClientCaller.doReusableHttpGetJsonArray(url, httpErrorResponseHandler);       
   }
   
   /**
    * 返回数据格式如下:
    *  {
    *   "_id":"5b0d4e6dc624ba0001d81ddc","name":"web","active":true,
    *   "events":["task.create","task.move","task.done","task.remove","objectlink.create"],
    *   "callbackURL":"http://www.zhibei.biz/webhook",
    *   "lastResponse":{"code":0,"message":""},"qos":1,
    *   "created":"2018-05-29T12:58:21.177Z",
    *   "updated":"2018-05-29T12:58:21.177Z"
    *  }
    * @param projectId
     * @param webhookId
    * @return 
    */
   public JSONObject getProjectWebHook(String projectId,String webhookId){
       
        String url=new UrlBuilder().path(API_TEAMBITION).path(SUBPATH_PROJECTS).path(projectId)
               .path("hooks").path(webhookId)
               .query("access_token",access_token)
               .toString();  

        return MPHttpClientCaller.doReusableHttpGetJsonObj(url, httpErrorResponseHandler);       
   }   
   

    /**
     * create a new web hook with default event list
     * @param projectId
     * @param callBackUrl
     * @return 
     */
   public JSONObject registerProjectWebHook(String projectId,String callBackUrl){

        JSONArray jsArr=new JSONArray();
        try{
            jsArr.add(EVENT_TASK_CREATE);
            jsArr.add(EVENT_TASK_MOVE);
            jsArr.add(EVENT_TASK_DONE);
            jsArr.add(EVENT_TASK_REMOVE);
            jsArr.add(EVENT_OBJECTLINK_CREATE);
            jsArr.add(EVENT_OBJECTLINK_REMOVE);        
            
        }catch(JSONException e){
            e.printStackTrace();
        }
        
        return registerProjectWebHook(projectId,callBackUrl,jsArr);
        
   }
       
   /**
     * create a new web hook
     * @param projectId
     * @param callBackUrl
     * @param jsEventArr
     * @return 
    */
   public JSONObject registerProjectWebHook(String projectId,String callBackUrl,JSONArray jsEventArr){
      
       return registerProjectWebHook(projectId,callBackUrl,jsEventArr,WEB_HOOK_QOS_SEND_ONCE_AND_RETURN);
        
   }   
   
   /**
    * 
    * @param projectId
    * @param callBackUrl
    * @param jsEventArr
    * @param qos
    * @return 
    */
   public JSONObject registerProjectWebHook(String projectId,String callBackUrl,JSONArray jsEventArr,int qos){
        
        String url=new UrlBuilder().path(API_TEAMBITION).path(SUBPATH_PROJECTS).path(projectId)
               .path("hooks")
               .query("access_token",access_token)
               .toString();          

        JSONObject jsonObj=new JSONObject();
        try{
            jsonObj.put("callbackURL", callBackUrl);
            jsonObj.put("active", true);
            jsonObj.put("qos", qos);
            jsonObj.put("events", jsEventArr);
        }catch(JSONException e){
            e.printStackTrace();
        }
        
        return MPHttpClientCaller.doReusableHttpPostForJsonObject(url, jsonObj, httpErrorResponseHandler);
        
   }      
   
   /**
     * create a new web hook
     * @param projectId
     * @param callBackUrl
     * @param jsEventArr
     * @param hookId
     * @return 
    */
   public JSONObject updateProjectWebHook(String projectId,String callBackUrl,JSONArray jsEventArr,String hookId){
        
        String url=new UrlBuilder().path(API_TEAMBITION).path(SUBPATH_PROJECTS).path(projectId)
               .path("hooks").path(hookId) 
               .query("access_token",access_token)
               .toString();          

        JSONObject jsonObj=new JSONObject();
        try{
            jsonObj.put("callbackURL", callBackUrl);
            jsonObj.put("active", true);
            jsonObj.put("events", jsEventArr);
        }catch(JSONException e){
            e.printStackTrace();
        }
        
        return MPHttpClientCaller.doReusableHttpPutForJsonObject(url, jsonObj, httpErrorResponseHandler);
        
   }         
   
   
   /**
     * create a new web hook
     * @param projectId
     * @param callBackUrl
     * @param jsEventArr
     * @param qos
     * @param hookId
     * @return 
    */
   public JSONObject updateProjectWebHook(String projectId,String callBackUrl,JSONArray jsEventArr,int qos,String hookId){
        
        String url=new UrlBuilder().path(API_TEAMBITION).path(SUBPATH_PROJECTS).path(projectId)
               .path("hooks").path(hookId) 
               .query("access_token",access_token)
               .toString();          

        JSONObject jsonObj=new JSONObject();
        try{
            jsonObj.put("callbackURL", callBackUrl);
            jsonObj.put("active", true);
            jsonObj.put("events", jsEventArr);
            jsonObj.put("qos", qos);
        }catch(JSONException e){
            e.printStackTrace();
        }
        
        return MPHttpClientCaller.doReusableHttpPutForJsonObject(url, jsonObj, httpErrorResponseHandler);
        
   }      
   

   /**
     * create a new web hook
     * @param projectId
     * @param hookId
     * @return 
    */
   public JSONObject deleteProjectWebHook(String projectId,String hookId){
        
        String url=new UrlBuilder().path(API_TEAMBITION).path(SUBPATH_PROJECTS).path(projectId)
                .path("hooks").path(hookId) 
               .query("access_token",access_token)
               .toString();          
        
        return MPHttpClientCaller.doReusableHttpDeleteJsonObj(url, httpErrorResponseHandler);
        
   }        
   
   
   public boolean checkAccessToken(){
       String url=new UrlBuilder().path(API_TEAMBITION).path(SUBPATH_APPLICATIONS).path(appId)
               .path("tokens").path("check")
               .toString();
       return  new MPHttpClient().newCall(
                new HttpRequest()
                        .url(url)
                        .addRequestHeader(HttpRequest.REQ_HEADER_NAME_AUTHORIZATION, access_token)
                        .build()).isHttpSuccess();
        
   }
   
    
   public JSONArray getOrganizations(){
       
        String url=new UrlBuilder().path(API_TEAMBITION).path(SUBPATH_ORGANIZATIONS)
               .query("access_token",access_token)
               .toString();  

        return MPHttpClientCaller.doReusableHttpGetJsonArray(url, httpErrorResponseHandler);
         
   }

   
   public JSONArray getMembersInOrg(String orgId){
       
        String url=new UrlBuilder().path(API_TEAMBITION_V2).path(SUBPATH_ORGANIZATIONS).path(orgId)
               .path("members")
               .query("access_token",access_token)
               .toString();             
        
        return MPHttpClientCaller.doReusableHttpGetJsonArray(url, httpErrorResponseHandler);
        
   }
   
   public JSONArray getProjectsInOrg(String orgId){
       
        String url=new UrlBuilder().path(API_TEAMBITION_V2).path(SUBPATH_ORGANIZATIONS).path(orgId)
               .path("projects")
               .query("access_token",access_token)
               .toString();             
        
        return MPHttpClientCaller.doReusableHttpGetJsonArray(url, httpErrorResponseHandler);     
     
   }
   
 
   public JSONArray getMembersInProj(String orgId){
       
        String url=new UrlBuilder().path(API_TEAMBITION_V2).path(SUBPATH_PROJECTS).path(orgId)
               .path("members")
               .query("access_token",access_token)
               .toString();             
        
        return MPHttpClientCaller.doReusableHttpGetJsonArray(url, httpErrorResponseHandler);            
       
   }
   
    public JSONArray getTags(String projId){
        
        String url=new UrlBuilder().path(API_TEAMBITION).path(SUBPATH_PROJECTS).path(projId)
               .path("tags")
               .query("access_token",access_token) 
               .toString();             
        
        return MPHttpClientCaller.doReusableHttpGetJsonArray(url, httpErrorResponseHandler);           
            
    }
    
    public JSONArray getTaskLists(String projId){

        String url=new UrlBuilder().path(API_TEAMBITION).path(SUBPATH_PROJECTS).path(projId)
               .path("tasklists")
               .query("access_token",access_token) 
               .toString();   
        return MPHttpClientCaller.doReusableHttpGetJsonArray(url, httpErrorResponseHandler);        
        
    }
    
    
    public JSONArray getStages(String taskListId){

        String url=new UrlBuilder().path(API_TEAMBITION).path(SUBPATH_TASKLISTS).path(taskListId)
               .path("stages")
               .query("access_token",access_token) 
               .toString();             
        return MPHttpClientCaller.doReusableHttpGetJsonArray(url, httpErrorResponseHandler);     
        
    }
    
    /**
     * @deprecated 这个接口无法实现正确分页，未来将被废除
     * @param _stageId
     * @param isDone
     * @param pageIndex
     * @param count
     * @return 
     */
    public JSONArray getTasksInStage(String _stageId,boolean isDone,final int pageIndex,final int count){
        
        String url=new UrlBuilder().path(API_TEAMBITION).path(SUBPATH_STAGES).path(_stageId)
               .path("tasks")
               .query("isDone",isDone)
               .query("count",count)
               .query("page",pageIndex)
               .query("_stageId",_stageId)
               .query("access_token",access_token) 
               .toString();             
        return MPHttpClientCaller.doReusableHttpGetJsonArray(url, httpErrorResponseHandler);             
        
    }
    
    

    /**
     * @param _stageId
     * @param pageIndex
     * @param count
     * @return 
     */
    public JSONArray getTasksInStage(String _stageId,final int pageIndex,final int count){
        
        String url=new UrlBuilder().path(API_TEAMBITION)
               .path("tasks")
               .query("count",count)
               .query("page",pageIndex)
               .query("_stageId",_stageId)
               .query("access_token",access_token) 
               .toString();             
        return MPHttpClientCaller.doReusableHttpGetJsonArray(url, httpErrorResponseHandler);             
        
    }    
    

    /**
     * @param _tasklistId
     * @param pageIndex
     * @param count
     * @return 
     */
    public JSONArray getTasksInTasklist(String _tasklistId,final int pageIndex,final int count){
        
        String url=new UrlBuilder().path(API_TEAMBITION)
               .path("tasks")
               .query("count",count)
               .query("page",pageIndex)
               .query("_tasklistId",_tasklistId)
               .query("access_token",access_token) 
               .toString();             
        return MPHttpClientCaller.doReusableHttpGetJsonArray(url, httpErrorResponseHandler);             
        
    }      
    
    /**
     * @deprecated 该接口已过时
     * @param _taskId
     * @return 
     */
    public JSONArray getSubTasks(String _taskId){
        String url=new UrlBuilder().path(API_TEAMBITION).path(SUBPATH_SUBTASKS)
               .query("_taskId",_taskId)
               .query("access_token",access_token) 
               .toString();        
        return MPHttpClientCaller.doReusableHttpGetJsonArray(url, httpErrorResponseHandler);
          
    }    
    
    /**
     * @param _taskId
     * @param pageIndex
     * @param count
     * @return 
     */
    public JSONArray getSubtasks(String _taskId,final int pageIndex,final int count){
        
        String url=new UrlBuilder().path(API_TEAMBITION)
               .path("tasks")
               .query("count",count)
               .query("page",pageIndex)
               .query("_ancestorId",_taskId)
               .query("access_token",access_token) 
               .toString();             
        return MPHttpClientCaller.doReusableHttpGetJsonArray(url, httpErrorResponseHandler);             
        
    }    
    
    /**
     * @param tasks
     * @param _stageId
     * @param pageIndex
     * @param count
     * @return 
     */
    public int getTasksInStage(ArrayList<Task> tasks,String _stageId,final int pageIndex,final int count){
            
        if(tasks==null||_stageId==null){
            return 0;
        }

        int size=0;
        try{

            JSONArray jsArr=getTasksInStage(_stageId,pageIndex,count);
            size=jsArr.size();
            for(int i=0;i<size;i++){

                JSONObject jsTask=jsArr.getJSONObject(i);
                String jsTaskId=jsTask.getString("_id");

                if(jsTaskId==null){
                    System.err.println("tbAdmin.java >> getTasksInStage >>解析任务id异常");
                    continue;
                }

                Task task=null;
                try{
                    task=Task.createInstanceByJson(jsTask);

                }catch(Exception e){
                    e.printStackTrace();
                }                    

                int j=0;
                for(;j<tasks.size();j++){
                    String _id=tasks.get(j)._id;
                    if(_id!=null&&_id.equals(jsTaskId)){
                        break;
                    }
                }

                if(j>=tasks.size()){//不存在则添加
                    tasks.add(task);
                }else{//存在着替换，这样的操作可以保证1、不需要互斥操作 2、客户端列表保持原顺序不变，不会不断清空刷新
                    tasks.set(j, task);
                }

            }

            size=jsArr.size();

            for(int j=0;j<tasks.size();j++){

                Task task=tasks.get(j);

                int i=0;
                for(;i<size;i++){

                    JSONObject jsTask=jsArr.getJSONObject(i);
                    String jsTaskId=jsTask.getString("_id");
                    if(jsTaskId==null){
                        continue;
                    }

                    if(jsTaskId.equals(task._id)){
                        break;
                    }

                }

                if(i>=size){
                    tasks.remove(j--); 
                }

            }


        }catch(Exception e){
            e.printStackTrace();
        }               

        return size;
            
    }
    
 
    /**
     * 
     * @param taskId
     * @param updateInvolvers
     * @param addInvolvers
     * @param delInvolvers 
     * 
     * 注意这个函数，删除操作时能删除任何人包括自己，但是无法删除创建者！
     * @return 
     * 
     */
    
    public JSONObject updateInvolvers(String taskId,
                                ArrayList<String> updateInvolvers,
                                ArrayList<String> addInvolvers,
                                ArrayList<String> delInvolvers ){
          
        JSONObject jsObj=new JSONObject();
        try{
             if(updateInvolvers!=null&&updateInvolvers.size()>0){
                JSONArray updateJsonArr=new JSONArray();
                for(int i=0;i<updateInvolvers.size();i++){
                    updateJsonArr.add(updateInvolvers.get(i)); 
                }
                jsObj.put("involveMembers", updateJsonArr);
            }

            if(addInvolvers!=null&&addInvolvers.size()>0){
                JSONArray addJsonArr=new JSONArray();
                for(int i=0;i<addInvolvers.size();i++){
                    addJsonArr.add(addInvolvers.get(i)); 
                }
                jsObj.put("addInvolvers", addJsonArr);
            }

            if(delInvolvers!=null&&delInvolvers.size()>0){
                JSONArray delJsonArr=new JSONArray();
                for(int i=0;i<delInvolvers.size();i++){
                    delJsonArr.add(delInvolvers.get(i)); 
                }   
                jsObj.put("delInvolvers", delJsonArr);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        //System.out.println("POST 请求的内容:"+content);
        String url=new UrlBuilder().path(API_TEAMBITION).path(SUBPATH_TASKS).path(taskId)
               .path("involveMembers")
               .query("access_token",access_token) 
               .toString();  

        return MPHttpClientCaller.doReusableHttpPutForJsonObject(url, jsObj, httpErrorResponseHandler);

    }
    
    /**
     * 返回的消息结构如下：
     *{
     * "_id":"5c208e58a239d800187fd837",
     * "isArchived":true,
     * "_projectId":"567ae10930c5066130a38b45",
     * "updated":"2019-01-12T19:01:35.518Z"
     * }
     * @param _taskId
     * @return 
     */
    public JSONObject setArchive(String _taskId){

        String url=new UrlBuilder().path(API_TEAMBITION).path(SUBPATH_TASKS).path(_taskId)
               .path("archive")
               .query("access_token",access_token) 
               .toString();            

        return MPHttpClientCaller.doReusableHttpPostForJsonObject(url, new JSONObject(), httpErrorResponseHandler);
    }
    

   /**
    * 返回消息结构如下
    * {
    * "taskIds":["5b75bd5295e6c30018dc4774","5b9175ac178332001a017b9b","5b8760335cf2670018260932"],
    * "_projectId":"567ae10930c5066130a38b45",
    * "updated":"2019-01-12T13:43:07.158Z"
    * }
    * @param _projectId
    *  @param _taskIdList ID个数不能超过50.
    * @return  
    * @throws java.lang.Exception 
    */
    public String setArchiveBulkly(String _projectId,JSONArray _taskIdList) throws Exception{

        if(_taskIdList==null||_taskIdList.isEmpty()||_taskIdList.size()>50)
            throw new Exception("任务列表数量不得超过50个");

        JSONObject jsArchiveBulk=new JSONObject();
        jsArchiveBulk.put("taskIds", _taskIdList);

        String url=new UrlBuilder().path(API_TEAMBITION).path(SUBPATH_PROJECTS).path(_projectId)
               .path("tasks") 
               .path("archive")
               .path("bulk")
               .query("access_token",access_token) 
               .toString();  

        return MPHttpClientCaller.doReusableHttpPut(url, jsArchiveBulk, httpErrorResponseHandler);            

    }       
       
   
    /**
     * @deprecated 解除归档后放在原任务阶段，
     * 按照https://docs.teambition.com 规定:解除归档的接口
     * 目标阶段ID和目的企业ID, _stageId/_organizationId 至少提供一个
     * 因此这种既不指定企业ID又不指定阶段ID的接口应该是非官方合法接口。
     * 不排除以后无法正确实现解除任务归档操作
     * @param _taskId
     * @return 
     */
    public JSONObject unArchive(String _taskId){
        
        String url=new UrlBuilder().path(API_TEAMBITION).path(SUBPATH_TASKS).path(_taskId)
               .path("archive")
               .query("access_token",access_token) 
               .toString();                  
        return MPHttpClientCaller.doReusableHttpDeleteJsonObj(url, httpErrorResponseHandler);

    }    
    
    /**
     * 
     * @param _taskId
     * @param destStageId 解除归档后放入哪个阶段
     * @return 
     */
    public JSONObject unArchive(String _taskId,String destStageId){
        
        String url=new UrlBuilder().path(API_TEAMBITION).path(SUBPATH_TASKS).path(_taskId)
               .path("archive")
               .query("_stageId",destStageId)
               .query("access_token",access_token) 
               .toString(); 
        return MPHttpClientCaller.doReusableHttpDeleteJsonObj(url, httpErrorResponseHandler);
    }        
    
   
    public JSONObject  getActivities(String taskId){
         String url=new UrlBuilder().path(API_TEAMBITION).path(SUBPATH_TASKS).path(taskId)
                .path("activities")
                .query("access_token",access_token) 
                .toString();        
         return MPHttpClientCaller.doReusableHttpGetJsonObj(url, httpErrorResponseHandler);

    }
    
    /**
     * 
     * @param _tasklistId
     * @param _stageId
     * @param content
     * @param _executorId
     * @param involveMembers
     * @param priority
     * @return 
     */
    public JSONObject createTask(String _tasklistId,String _stageId,String content,
            String _executorId,JSONArray involveMembers,
            int priority){


        JSONObject jsSubTask=new JSONObject();
        try{
            jsSubTask.put("_tasklistId", _tasklistId);
            jsSubTask.put("_stageId", _stageId);
            jsSubTask.put("content", content);
            jsSubTask.put("access_token", access_token);
            if(_executorId!=null){
                jsSubTask.put("_executorId", _executorId);
            }
            if(involveMembers!=null){
                jsSubTask.put("involveMembers", involveMembers);
            }
            if(priority>0){
                if(priority>2){
                    priority=2;
                }
                jsSubTask.put("priority", priority);
            }
            

        }catch(Exception e){
            e.printStackTrace();
        }

         String url=new UrlBuilder().path(API_TEAMBITION).path(SUBPATH_TASKS)
                .query("access_token",access_token) 
                .toString();    
        
        return MPHttpClientCaller.doReusableHttpPostForJsonObject(url, jsSubTask, httpErrorResponseHandler);
         
    }        
    
    /**
     * @param _tasklistId
     * @param _stageId
     * @param content
     * @param _executorId
     * @param involveMembers
     * @param priority
     * @return 
     */    
    public Task createTask2(String _tasklistId,String _stageId,String content,
                           String _executorId,JSONArray involveMembers,int priority){
        Task task=null;
        try{
            task=Task.createInstanceByJson(createTask(_tasklistId,_stageId,content,_executorId,involveMembers,priority));
        }catch(Exception e){
            e.printStackTrace();
        }
       
        return task;
                 
    }            
    
    /**
     * @return 
     * @deprecated 该接口为抓包分析所得，非官方正式接口
     * @param _taskId
     * @param _executorId
     * @param content 
     */
    public JSONArray addSubTask(String _taskId,String _executorId,String content){

        JSONObject jsSubTask=new JSONObject();
        
        try{
            jsSubTask.put("_taskId", _taskId);
            jsSubTask.put("_executorId", _executorId);
            jsSubTask.put("content", content);
            jsSubTask.put("type", "subtask");
            jsSubTask.put("isSubtask", true);
            jsSubTask.put("isDone", false);
            JSONArray nullArry=new JSONArray();
            jsSubTask.put("task", nullArry);

        }catch(Exception e){
            e.printStackTrace();
        }

         String url=new UrlBuilder().path(API_TEAMBITION_UNAUHTENED).path(SUBPATH_SUBTASKS)
                .query("access_token",access_token) 
                .toString();    
        
        return MPHttpClientCaller.doReusableHttpPostForJsonArray(url, jsSubTask, httpErrorResponseHandler);
         
    }
    
    /**
     * @param involveMembers 默认为空
     * @param priority 可选值为0，1，2分别对应普通\紧急\非常紧急
     * @return 
     * @param _taskId 
     * @param _executorId 默认为空
     * @param content 
     */
    public JSONArray addSubTask(String _taskId,String content,
                                String _executorId,JSONArray involveMembers,
                                int priority){

        JSONObject jsSubTask=new JSONObject();
        
        try{
            
            jsSubTask.put("_ancestorId", _taskId);
            jsSubTask.put("content", content);
            jsSubTask.put("access_token", access_token);
            if(_executorId!=null){
                jsSubTask.put("_executorId", _executorId);
            }
            if(involveMembers!=null){
                jsSubTask.put("involveMembers", involveMembers);
            }
            if(priority>0){
                if(priority>2){
                    priority=2;
                }
                jsSubTask.put("priority", priority);
            }
            

        }catch(Exception e){
            e.printStackTrace();
        }

         String url=new UrlBuilder().path(API_TEAMBITION).path(SUBPATH_TASKS)
                .query("access_token",access_token) 
                .toString();    
        
        return MPHttpClientCaller.doReusableHttpPostForJsonArray(url, jsSubTask, httpErrorResponseHandler);
         
    }    
    
    /**
     * 添加备注(note)
     * @param _taskId
     * @param remark 
     * @return  
     */
    public JSONObject addRemark(String _taskId,String remark){
        
        JSONObject jsRemark=new JSONObject();
        jsRemark.put("note", remark);
        
        String url=new UrlBuilder().path(API_TEAMBITION).path(SUBPATH_TASKS).path(_taskId) 
                                   .path("note") 
                                   .query("access_token",access_token) 
                                   .toString(); 
        return MPHttpClientCaller.doReusableHttpPostForJsonObject(url, jsRemark, httpErrorResponseHandler);
       
    }    
    
    /**
     * 
     * @param taskId
     * @return 
     */
    public JSONArray getLinkObjects(String taskId){

        String url=new UrlBuilder().path(API_TEAMBITION).path(SUBPATH_TASKS).path(taskId) 
                                   .path("objectlinks") 
                                   .query("access_token",access_token) 
                                   .toString(); 
        return MPHttpClientCaller.doReusableHttpGetJsonArray(url, httpErrorResponseHandler);
            
    }
    
    /**
     * 
     * @param taskId
     * @return 
     */
    public ArrayList<ObjectLink> getLinkObjects2(String taskId){

        ArrayList<ObjectLink> objLinks=null;

        try{
            
            JSONArray jsArr=getLinkObjects(taskId);
            final int size=jsArr.size();
            if(size>0){
                objLinks=new ArrayList<>(size);
                for(int i=0;i<size;i++){
                    JSONObject jsObjLink=jsArr.getJSONObject(i);
                    ObjectLink objLink=ObjectLink.createInstanceByJson(jsObjLink);
                    if(objLink.linkedType!=null&&objLink.linkedType.equals("task")){
                        objLinks.add(objLink);
                    }

                }                    
            }

        }catch(Exception e){
            e.printStackTrace();
        }      

        return objLinks;
            
    }
    
    /**
     * 
     * @param _taskId
     * @param _linkedId
     * @param _linktype 
     * @return  
     */
    public JSONObject addObjLink(String _taskId,String _linkedId,String _linktype){

        JSONObject jsObj=new JSONObject();
        jsObj.put("access_token", access_token);
        jsObj.put("_linkedId", _linkedId);
        jsObj.put("linkedType", _linktype);
        String url=new UrlBuilder().path(API_TEAMBITION).path(SUBPATH_TASKS).path(_taskId) 
                                   .path("objectlinks") 
                                   .query("access_token",access_token) 
                                   .toString(); 
        return MPHttpClientCaller.doReusableHttpPostForJsonObject(url, jsObj, httpErrorResponseHandler);
    }    
    
    
    /**
     * 
     * @param _taskId
     * @param _linkedId
     * @return  
     */
    public JSONObject addObjLink(String _taskId,String _linkedId){
        return addObjLink(_taskId,_linkedId,"task");
    }        
    
    /**
     * 
     * @param _taskId
     * @param _linkedId
     * @return 
     */
    public ObjectLink addObjLink2(String _taskId,String _linkedId){
        
        ObjectLink objLink=null;
        try{
            objLink=ObjectLink.createInstanceByJson(addObjLink(_taskId,_linkedId));            
        }catch(Exception e){
            e.printStackTrace();
        }
        return objLink;
            
    }        

    /**
     * 
     * @return 
     */
    public String getStrikerAuth(){
        
        String strStrikerAuth=null;
        String url=new UrlBuilder().path(API_TEAMBITION).path(SUBPATH_USERS)
                                   .path("me") 
                                   .query("access_token",access_token) 
                                   .toString(); 

        try{

            JSONObject jsRet=MPHttpClientCaller.doReusableHttpGetJsonObj(url, httpErrorResponseHandler);   
            strStrikerAuth=jsRet.getString("strikerAuth");
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return strStrikerAuth;
                
    }


    /**
     * 返回值格式
     * {
     * "customfields":[{"_customfieldId":"5b640051510d0900014febbd","type":"text","value":[],"values":["sssssss@xxxxx3"]}],
     * "updated":"2018-08-03T17:13:24.720Z"
     * }
     * @param _taskId
     * @param cuzFeildId
     * @param jsCuzFieldValueArr
     * @return 
     */    
    public JSONObject setCustomizedField(String _taskId,String cuzFeildId,JSONArray jsCuzFieldValueArr){

        JSONObject jsQuery=new JSONObject();
        jsQuery.put("_customfieldId", cuzFeildId);
        jsQuery.put("values", jsCuzFieldValueArr);

        String url=new UrlBuilder().path(API_TEAMBITION).path(SUBPATH_TASKS).path(_taskId) 
                                   .path("customfields") 
                                   .query("access_token",access_token) 
                                   .toString(); 
        return MPHttpClientCaller.doReusableHttpPutForJsonObject(url, jsQuery, httpErrorResponseHandler);

    }         
    
    
    /**
     * 返回值格式
     * {
     * "customfields":[{"_customfieldId":"5b640051510d0900014febbd","type":"text","value":[],"values":["sssssss@xxxxx3"]}],
     * "updated":"2018-08-03T17:13:24.720Z"
     * }
     * @param _taskId
     * @param cuzFeildId
     * @param cuzFieldValue
     * @return 
     */    
    public boolean setCustomizedField(String _taskId,String cuzFeildId,String cuzFieldValue){
        
        try{
            
            JSONArray jsValuesArr=new JSONArray();
            jsValuesArr.add(cuzFieldValue);
            JSONObject jsRet=setCustomizedField(_taskId,cuzFeildId,jsValuesArr);
            JSONArray jsCustomfieldsArr=jsRet.getJSONArray("customfields");
            if(jsCustomfieldsArr!=null){
                final int size=jsCustomfieldsArr.size();
                if(size>0){
                    JSONObject customfield=jsCustomfieldsArr.getJSONObject(0);
                    if(customfield!=null){
                        String _customfieldId=customfield.getString("_customfieldId");
                        if(_customfieldId!=null&&_customfieldId.equals(cuzFeildId)){
                            JSONArray jsRetValuesArr=customfield.getJSONArray("values");
                            if(jsRetValuesArr!=null&&jsRetValuesArr.size()>0){
                                String id=jsRetValuesArr.getString(0);
                                if(id!=null&&id.trim().equals(cuzFieldValue.trim())){
                                    return true;
                                }
                            }
                        }
                    }
                }
            }    

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
            
    }             
    
    
    /**
     * 
     * @param jsWorks
     * @return 
     */
    public JSONObject addWorks(JSONObject jsWorks){

        String url=new UrlBuilder().path(API_TEAMBITION).path(SUBPATH_WORKS)
                                   .query("access_token",access_token) 
                                   .toString(); 
        return MPHttpClientCaller.doReusableHttpPostForJsonObject(url, jsWorks, httpErrorResponseHandler);        
        
    }
        
    

    /**
     * 
     *{
     * "attachments":[],
     * "mentions":[{"56c57c826a70307c69ac8fc4":"黄总助理","56c57c826a70307c69ac8fc4":"黄总助理"}],
     * "content":"@黄总助理 请务必规范、详细的填写客户信息和要求，并且填写快盘中的生产表"
     * }
     * 
     * {
     * "content":"@黄明 xxxx",
     * "attachments":[],
     * "mentions":{"567aba9727a96de55ed9ab49":"黄明"}
     * }
     * 
     * @param _taskId
     * @param _mentionedUserIds
     * @param attachmentIds
     * @param comment
     * @return 
     * 
     */
    public JSONObject addActivity(String _taskId,
                                  ArrayList<String> _mentionedUserIds,
                                  ArrayList<String> attachmentIds,
                                  String comment){

        JSONObject jsActivity=new JSONObject();

        try{

            jsActivity.put("content", comment);

            JSONArray jsAttachmentIdsArr=new JSONArray();
            if(attachmentIds!=null&&!attachmentIds.isEmpty()){
                for(int i=0;i<attachmentIds.size();i++){
                    jsAttachmentIdsArr.add(attachmentIds.get(i));
                }
            }                

            jsActivity.put("attachments", jsAttachmentIdsArr);                


            JSONArray  jsMentionedIdsArr=new JSONArray();
            if(_mentionedUserIds!=null&&!_mentionedUserIds.isEmpty()){
                for(int i=0;i<_mentionedUserIds.size();i++){ 
                      jsMentionedIdsArr.add(_mentionedUserIds.get(i));
                }
            }
            jsActivity.put("mentionedUsers", jsMentionedIdsArr);

        }catch(Exception e){
            e.printStackTrace();
        }


        String url=new UrlBuilder().path(API_TEAMBITION).path(SUBPATH_TASKS).path(_taskId) 
                                   .path("activities") 
                                   .query("access_token",access_token) 
                                   .toString(); 
        return MPHttpClientCaller.doReusableHttpPostForJsonObject(url, jsActivity, httpErrorResponseHandler);            

    }     
    
    /**
     * @return 
     * @deprecated 老式的调用接口，艾特某个人时，内容添加上了 @某某 字样
     * @param _taskId
     * @param _mentionedUserId
     * @param mentionedUserName
     * @param comment 
     */
    public JSONObject addComment(String _taskId,String _mentionedUserId,String mentionedUserName,String comment){
        
        JSONObject jsActivity=new JSONObject();
        try{
            jsActivity.put("content", "@"+mentionedUserName+" "+comment);
            JSONArray jsAttachmentsArr=new JSONArray();
            jsActivity.put("attachments", jsAttachmentsArr);
            JSONObject mention=new JSONObject();
            mention.put(_mentionedUserId, mentionedUserName);
            jsActivity.put("mentions", mention);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        String url=new UrlBuilder().path(API_TEAMBITION).path(SUBPATH_TASKS).path(_taskId) 
                                   .path("activities") 
                                   .query("access_token",access_token) 
                                   .toString(); 
        return MPHttpClientCaller.doReusableHttpPostForJsonObject(url, jsActivity, httpErrorResponseHandler);          

    }        
    
    public JSONObject getProjectTags(String _projectId){

        String url=new UrlBuilder().path(API_TEAMBITION).path(SUBPATH_PROJECTS).path(_projectId)
                                   .path("tags") 
                                   .query("access_token",access_token) 
                                   .toString(); 

        return MPHttpClientCaller.doReusableHttpGetJsonObj(url, httpErrorResponseHandler);   
       
                
    }
        
    /**
     * 
     * @param _projectId
     * @param tag
     * @return 
     */
    public JSONObject addProjectTag(String _projectId,String tag){
        
        JSONObject jsTag=new JSONObject();
        jsTag.put("_projectId", _projectId);
        jsTag.put("name", tag);

        String url=new UrlBuilder().path(API_TEAMBITION).path(SUBPATH_PROJECTS).path(_projectId) 
                                   .path("tags") 
                                   .query("access_token",access_token) 
                                   .toString(); 
        return MPHttpClientCaller.doReusableHttpPostForJsonObject(url, jsTag, httpErrorResponseHandler);            
        
    }    
    
    /**
     * 
     *   {
     *     "_id": "0000000000000000",
     *     "_projectId": "11111111111111111111111",
     *     "name": "拼版任务",
     *     "_creatorId": "22222222222222",
     *     "updated": "2016-07-30T16:03:45.631Z",
     *     "created": "2016-07-30T15:05:37.949Z",
     *     "isArchived": null,
     *     "color": "blue"
     *   }
     * @param _taskId
     * @param jsTagIdArr
     * @return 
    */
    public JSONObject setTaskTag(String _taskId,JSONArray jsTagIdArr){

        JSONObject jsSetTag=new JSONObject();
        jsSetTag.put("tagIds", jsTagIdArr);
        
        String url=new UrlBuilder().path(API_TEAMBITION).path(SUBPATH_TASKS).path(_taskId) 
                                   .path("tagIds") 
                                   .query("access_token",access_token) 
                                   .toString(); 
        return MPHttpClientCaller.doReusableHttpPutForJsonObject(url, jsSetTag, httpErrorResponseHandler);   
        
    }      
    
    /**
     * 
     * @param _taskId
     * @param isDone
     * @return 
     */
    public JSONObject setFinished(String _taskId,boolean isDone){

        JSONObject jsReq=new JSONObject();
        jsReq.put("isDone", isDone);

        String url=new UrlBuilder().path(API_TEAMBITION).path(SUBPATH_TASKS).path(_taskId) 
                                   .path("status") 
                                   .query("access_token",access_token) 
                                   .toString(); 
        return MPHttpClientCaller.doReusableHttpPutForJsonObject(url, jsReq, httpErrorResponseHandler);           
            
    }

    /**
     * @deprecated 通过获取固定数量的消息，然后判断这些消息里面有哪些没有读。
     * 这种方式是不准确的，因此这个方法被废除了
     * @param latestN
     * @return 
     */
    public ArrayList<Message> getUnreadMessages(int latestN){

        String url=new UrlBuilder().path(API_TEAMBITION_V2).path(SUBPATH_MESSAGES)
                                   .query("access_token",access_token) 
                                   .query("count",latestN) 
                                   .query("page",1) 
                                   .toString(); 

        ArrayList<Message> messages=null;

        try{
                JSONArray jsArr=MPHttpClientCaller.doReusableHttpGetJsonArray(url, httpErrorResponseHandler);               
                final int size=jsArr.size();
                if(size>0){
                    messages=new ArrayList<>(size+1);
                }
                for(int i=0;i<size;i++){
                    JSONObject jsMessage=jsArr.getJSONObject(i);
                    if(jsMessage==null){
                        continue;
                    }
                    Message message=Message.createInstanceByJson(jsMessage);
                    if(message!=null&&!message.isRead()){
                        messages.add(message);
                    }
                }
        }catch(Exception e){
            e.printStackTrace();
        }      
        return messages;       
        
    }
    
    
    /**
     * 
     * @param localDateTime
     * @param count
     * @return 
     */
    public ArrayList<Message> getLatestMessages(Date localDateTime,int count){

        String strUtcTime=Utils.localToUTC(localDateTime,"YYYY-MM-DDTHH:mm:ss.sssZ");
        
        String url=new UrlBuilder().path(API_TEAMBITION).path(SUBPATH_MESSAGES)
                                   .path("latest") 
                                   .query("access_token",access_token) 
                                   .query("updated_gt",strUtcTime) 
                                   .query("count",count) 
                                   .toString(); 
        
        ArrayList<Message> messages=null;

        try{
                JSONArray jsArr=MPHttpClientCaller.doReusableHttpGetJsonArray(url, httpErrorResponseHandler);               
                final int size=jsArr.size();
                if(size>0){
                    messages=new ArrayList<>(size+1);
                }
                for(int i=0;i<size;i++){
                    JSONObject jsMessage=jsArr.getJSONObject(i);
                    if(jsMessage==null){
                        continue;
                    }
                    Message message=Message.createInstanceByJson(jsMessage);
                    if(message!=null){
                        messages.add(message);
                    }
                }
        }catch(Exception e){
            e.printStackTrace();
        }      
        return messages;       
        
    }    
    
    /**
     * 
     * @param localDateTime
     * @param count
     * @return 
     */
    public ArrayList<Message> getLatestUnreadMessages(Date localDateTime ,int count){
        
        ArrayList<Message> messages=getLatestMessages(localDateTime,count);
        if(messages!=null){
            for(int i=0;i<messages.size();i++){
                Message message=messages.get(i);
                if(message==null||message.isRead()){
                    messages.remove(i--);
                }
            }
        }
        return messages;       
        
    }        
    
    
    /**
     * 返回消息结构
     * { "_id": "53141095054522f292798fa0", "isRead": true }
     * @param messageId
     * @return 
     */
    public JSONObject setMessageRead(String messageId){

            String url=new UrlBuilder().path(API_TEAMBITION).path(SUBPATH_MESSAGES).path(messageId) 
                                       .query("access_token",access_token) 
                                       .toString(); 
            return MPHttpClientCaller.doReusableHttpPutForJsonObject(url, (JSONObject)null, httpErrorResponseHandler);       
        
    }        
    
    
    /**
     * 
     * @param _taskId
     * @param _stageId
     * @return 
     */
    public JSONObject moveTask(String _taskId,String _stageId){
        
            JSONObject jsReq=new JSONObject();
            jsReq.put("_stageId", _stageId);
            jsReq.put("withTags", true);
           
            String url=new UrlBuilder().path(API_TEAMBITION).path(SUBPATH_TASKS).path(_taskId) 
                                       .path("move") 
                                       .query("access_token",access_token) 
                                       .toString(); 
            return MPHttpClientCaller.doReusableHttpPutForJsonObject(url, jsReq, httpErrorResponseHandler);                 

            
    }

    /**
     * 
     * @param _taskId
     * @return 
     */
    public Task getTask(String _taskId){
            
            Task task=null;
            
            String url=new UrlBuilder().path(API_TEAMBITION).path(SUBPATH_TASKS).path(_taskId)
                                       .query("access_token",access_token) 
                                       .toString(); 
            try{
                task=Task.createInstanceByJson(MPHttpClientCaller.doReusableHttpGetJsonObj(url, httpErrorResponseHandler));               
            }catch(Exception e){
                e.printStackTrace();
            }
            
            return task;        
    }
    
    
    
}
