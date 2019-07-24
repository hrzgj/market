package com.chenyu.www.util;

/**
 * 常量类
 * @author 86323
 */
public class Constant {
    /**
     * 验证码的字符
     */
    public static final String CODE= "23456789abcdefghjkmnopqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";

    /**
     * 验证码的大小
     */
    public static final int CODE_SIZE =4;
    /**
     * 图片上传地址
     */
    public static final String PATH="D:\\market\\web\\image";


    /**
     * 找回密码成功的标志
     */
    public static final String FIND_PASSWORD="找回密码成功";

    /**
     * 找回密码失败的原因
     */
    public static final String FIND_PASSWORD_ERROR="账户身份证不匹配";

    /**
     * 用户管理员标志
     */
    public static final String USER_IDENTITY1="管理员";
    /**
     * 用户是普通用户的标志
     */
    public static final String USER_IDENTITY2="普通用户";
    /**
     * 前端传来的name
     */
    public static final String PRICE_SORT="priceSort";

    /**
     * 价格升序的标志
     */
    public static final int PRICE_ASCEND_SORT=1;
    /**
     * 价格降序的标志
     */
    public static final int PRICE_DESC_SORT=2;
    /**
     * 商品已售数量升序
     */
    public static final int BEEN_AMOUNT_ASCEND=3;
    /**
     * 商品已售数量降序
     */
    public static final int BEEN_AMOUNT_DESC=4;

    //订单四种状态
    public static final String ORDER_STATE_ONE="已发送";
    public static final String ORDER_STATE_TWO="已发货";
    public static final String ORDER_STATE_THREE="已收货";
    public static final String ORDER_STATE_FOUR="拒绝发货";

    //商品类型
    public static final String TRADE_KIND_ONE="日用品";
    public static final String TRADE_KIND_TWO="服装";
    public static final String TRADE_KIND_THREE="学习";
    public static final String TRADE_KIND_FOUR="食品";
    public static final String TRADE_KIND_FIVE="其他";

    //商品状态
    public static final String TRADE_STATE_ONE="审核";
    public static final String TRADE_STATE_TWO="在售";
    public static final String TRADE_STATE_THREE="申请删除";

    //用户是否可以出售
    public static final String USER_CAN="是";
    public static final String USER_CANNOT="否";

    //导出数据库的订单先存到这里
    public static final String ORDER_PATH="D:/market/sql/";
//C:\ProgramData\MySQL\MySQL Server 5.7\Data\market
    /**
     * 用户得到的积分是当前订单金钱的11分之1
     */
    public static final int GET_POINT=11;

    /**
     * 用户根据用户账户搜索商品，从前端接收用户账户的request.getParameter的name
     */
    public static final String USER_ACCOUNT="userAccount";

    /**
     * 用户根据商品名称搜索商品，从前端接收用户账户的request.getParameter的name
     */
    public static final String TRADE_NAME="tradeName";

    /**
     * 用户根据商品类型搜索商品，从前端接收用户账户的request.getParameter的name
     */
    public static final String TRADE_TYPE="tradeType";

    /**
     * 用户登录状态过久时，传给前端一个提示，即一个session的名字
     */
    public static final String OUT_DATED="outDated";

    /**
     * 一个session名，用户浏览商品时点击上一页下一页等跳转servlet方法名
     */
    public static final String JUDGE_TRADE="judgeTrade";
}
