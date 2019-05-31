/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.meteplus.tb.objects;

import com.meteplus.tb.objects.UnreadMessage.Message;
import java.util.ArrayList;

/**
 *
 * @author HuangMing
 */
public class MessageTraces {
    public Task taskInfo;
    public Message lastClientMsg;
    public ArrayList<Message> msgAtDesigners;
    public ArrayList<Message> designersResps;
    public ArrayList<Message> lastDesignMsg;//带图的消息
    public Message lastNoneSysMsg;
}


