/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.meteplus.tb.objects;

import java.util.Comparator;

/**
 *
 * @author HuangMing
 */
public class mem_Stage {
    public static final String xunpan_tianmao="5ac748bcc6a07222227868fb";//天猫的询盘
    public static final String xunpan_lulan="567ae11d053f31121b81a295";//星辰淘宝的询盘
    public static final String xunpan_liyun="56c69f36938c9ecc4f781e18";//百度的询盘
    public static final String xunpan_qiuyu="56de8fbf93e1169a4177f737";//星辰阿里的询盘
    public static final String xunpan_hanwen="57ba7c2aff147f90283108e2";//塞外阿里的询盘
    public static final String xunpan_houqiu="57844d2b6285608341171424";//塞外淘宝的询盘
    public static final String xunpan_liaoaiping="567ae11d053f31121b81a296";//其他询盘
    public static final String xunpan_hezuo_dianshang="5a4cb04755a5584b08555a79";//第三方电商合作
    public static final String dingao="56c58be5cefca3427c735eff";//"定稿 (5000起)
    public static final String dingao_1000="58db1ef1cc4968557d00165c";//定稿 (1000/2000起)
    public static final String pinbanguihua="56c8934261a35dc35de387e6";//拼版规划
    public static final String yinshua="567ae11d053f31121b81a297";//印刷
    public static final String qiezhi="567ae11d053f31121b81a298";//切纸
    public static final String chengxing="567ae11d053f31121b81a299";//成型
    public static final String dadandabaofahuo="59749fab968be7308085da98";//打单/打包/发货
    public static final String dabao="5780944aebd50b277e7be260";//等待尾款
    public static final String fahuo="56d1a58874acf708298f184d";//已发出
    public static final String tousujiufen="567bb103bce0439b1e7bfd67";//投诉与纠纷
    public static final String zhongzhi="56c2dae19d6c978505deabb0";//终止的订单
    public static final String wancheng="567ae11d053f31121b81a29b";//已完成
    
    public String stageId;
    public String stageName;
    public int stageIndex;
    public boolean isXunpan;
    public String stageImage;
    
    public mem_Stage(String stageId,String stageName,int stageIndex,boolean isXunpan,String stageImage){
        this.stageId=stageId;
        this.stageName=stageName;
        this.stageIndex=stageIndex;
        this.isXunpan=isXunpan;
        this.stageImage=stageImage;
    }
    
    
}
