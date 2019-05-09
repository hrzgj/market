package com.chenyu.www.service;

import com.chenyu.www.po.*;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 86323
 */
public interface UserService {
    /**
     * 验证登录账户密码
     * @param user 实体类
     * @return  是否成功
     */
    Boolean login(User user);


    /**
     * 将数据库中的用户信息载入
     * @param user 实体类
     * @return 实体类
     * @throws SQLException sql异常
     */
    User informationInput(User user) throws SQLException;

    /**
     * 注册功能
     * @param user 要注册的实体类
     * @return  注册成功或者失败的原因
     */
    Boolean register(User user);

    /**
     * 找回密码功能
     * @param user 要找回密码的实体类
     * @return  账户密码是否输入成功
     */
    String findPassword(User user);

    /**
     * 更新用户名字地址和手机
     * @param user 更新的实体类
     * @return  更新完的用户
     */
    User updateNameAddressAndPhone(User user);

    /**
     * 更新用户密码
     * @param user 要更新的user类
     * @return 成功返回true 失败返回false
     */
    Boolean updatePassword(User user);

    /**
     * 判断密码是否输出正确
     * @param user 要判断的user类
     * @param oldPassword 输入的密码
     * @return 相同返回true 不同返回false
     */
    Boolean judgePassword(User user,String oldPassword);

    /**
     * 获取总数据数
     * @return 总数据数
     */
    int getTotalCount();

    /**取某一个的page值
     * @param page 要存入的page类
     * @return 已存入用户集合的page类
     */
    Page<User> getSomeUser(Page<User> page);

    /**
     * 判断用户账户是否存在
     * @param userAccount 用户账户
     * @return 是否存在
     */

    Boolean isHaveAccount(String userAccount);
    /**
     * 判断用户身份证是否存在
     * @param userIdCard 用户身份证
     * @return 是否存在
     */
    Boolean isHaveIdCard(String userIdCard);

    /**
     * 更新用户头像
     * @param user 新头像的名称
     * @return 是否更改成功
     */
    Boolean updateUserPhoto(User user);

    /**
     * 模拟搜索是否有该用户账户的用户
     * @param userAccount 用户账户
     * @param page 空类
     * @return 成功返回Page类，失败返回null
     */
    Page<User> findUserByVagueAccount(String userAccount,Page<User> page);

    /**
     * 得到模糊查询后的用户总数
     * @param userAccount 模糊查询的用户账户
     * @return 模糊查询的用户总数
     */
    int getTotalVagueUser(String userAccount);

    /**
     * 管理员禁售指定用户
     * @param user 要禁售的用户信息
     * @return 是否禁售成功
     */
    boolean banUserSell(User user);

    /**
     * 用户申请解禁
     * @param user 要解禁的用户信息
     * @return 是否解禁成功
     */
    boolean appealSell(User user);

    /**
     * 管理员解禁被禁售的用户
     * @param userAccount 用户账户
     * @return 是否解禁成功
     */
    boolean allowSell(String userAccount);






    /**
     * 增加订单
     * @param order 订单
     * @return 是否增加成功
     */
    double addOrder(Order order);

    /**
     * 用户使用积分减少付款，增加订单
     * @param order 订单
     * @return 返回当前用户剩余积分
     */
    double addOneOrderWithPoint(Order order);

    /**
     * 根据出售用户得到订单出售用户为该用户的订单
     * @param userOrder userOrder
     * @return userOrder
     */
    UserOrder findSellerOrder(UserOrder userOrder);

    /**
     * 出售用户发货，改变对应订单的状态
     * @param order 订单类与数据库一一对应
     * @param userOrder 当前用户的总订单
     * @return 是否改变成功
     */
    boolean sellDeliverOrder(Order order,UserOrder userOrder);

    /**
     * 直接更改展示给前端的内容，改变指定订单的商品状态,并返回该商品在订单集合的索引
     * @param list 集合
     * @param orderId 商品id
     * @param orderState 商品状态
     * @return 商品的索引
     */
    int updateShowOrder(List<OrderShow> list,int orderId,String orderState);

    /**
     * 出售用户拒绝发货，改变对应订单的状态
     * @param order 订单
     * @return 是否修改成功
     */
    boolean sellRefuseOrder(Order order);

    /**
     * 根据登录用户得到订单购买用户为该用户的订单
     * @param userOrder userOrder
     * @return userOrder
     */
    UserOrder findBuyerOrder(UserOrder userOrder);

    /**
     * 购买用户收货，改变对应订单的状态
     * @param order 订单
     * @return 是否更改成功
     */
    boolean buyerTakeOrder(Order order);

    /**
     * 购买用户取消已发送的订单,即完全删除，删除前判断是否应该返还用户积分
     * @param order 订单
     * @param user 用户
     * @return 是否删除成功
     */
    boolean deleteOrder(Order order,User user);

    /**
     * 如果后台删除订单，则直接删除展示给前端的内容，删除其对应订单
     * @param userOrder 展示给前端的类
     * @param orderId 订单id
     * @return userOrder
     */
    UserOrder removerShowOrder(UserOrder userOrder,int orderId);

    /**
     * 删除订单，但视情况完全删除
     * 如果是购买者删除订单，则把数据库该订单的购买者设为null
     * 如果是出售者删除订单，则把数据库该订单的出售者设为null
     * 如果订单的出售者和购买者都为nul，则把数据库的该订单删除
     * @param order 与数据库一一对应的订单
     * @return 是否有删除
     */
    boolean sellerRemoveOrder(Order order);

    /**
     * 删除订单，但视情况完全删除
     * @param order 订单
     * @return 是否有删除
     */
    boolean buyerRemoverOrder(Order order);

    /**
     * 如果后台没有删除订单，则将userOrder对应订单的出售者设为null
     * @param userOrder 展示给前端的内容
     * @param orderId 订单id
     * @return userOrder
     */
    UserOrder setNullUserAccount(UserOrder userOrder,int orderId);

    /**
     * 买家用户查询所有已发送的订单
     * @param userOrder userOrder
     * @return userOrder
     */
    UserOrder buyerFindSendOrder(UserOrder userOrder);

    /**
     * 买家用户查询所有已发货的订单
     * @param userOrder userOrder
     * @return userOrder
     */
    UserOrder buyerFindDeliverOrder(UserOrder userOrder);

    /**
     * 买家用户查询所有已收货的订单
     * @param userOrder userOrder
     * @return userOrder
     */
    UserOrder buyerFindTakeOrder(UserOrder userOrder);

    /**
     * 买家用户查询所有已收货的订单
     * @param userOrder userOrder
     * @return userOrder
     */
    UserOrder buyerFindRefuseOrder(UserOrder userOrder);

    /**
     * 卖家用户查询所有已发送的订单
     * @param userOrder userOrder
     * @return userOrder
     */
    UserOrder sellerFindSendOrder(UserOrder userOrder);


    /**
     * 卖家用户查询所有已发送的订单
     * @param userOrder userOrder
     * @return userOrder
     */
    UserOrder sellerFindDeliverOrder(UserOrder userOrder);

    /**
     * 卖家用户查询所有已发送的订单
     * @param userOrder userOrder
     * @return userOrder
     */
    UserOrder sellerFindTakeOrder(UserOrder userOrder);

    /**
     * 卖家用户查询所有已收货的订单
     * @param userOrder userOrder
     * @return userOrder
     */
    UserOrder sellerFindRefuseOrder(UserOrder userOrder);

    /**
     * 买家用户收货后对订单进行评价和给予等级
     * @param order 订单
     * @return 是否增加成功
     */
    boolean buyCommentOrder(Order order);

    /**
     * 买家回复买家的评价和评分
     * @param order 订单
     * @return 是否修改成功
     */
    boolean sellReplyOrder(Order order);

    /**
     * 用户身为卖家查出订单，同时在D:/market/sql/下生成对应文件名称的文件
     * @param userOrder userOrder
     * @param fileName 文件名称
     * @return 是否生成文件成功
     */
    boolean sellExportFile(UserOrder userOrder,String fileName);

    /**
     * 用户身为买家查出订单，同时在D:/market/sql/下生成对应文件名称的文件
     * @param userOrder userOrder
     * @param fileName 文件名称
     * @return 是否生成文件成功
     */
    boolean buyExportFile(UserOrder userOrder,String fileName);
}
