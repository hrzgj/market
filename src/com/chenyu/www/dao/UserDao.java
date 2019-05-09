package com.chenyu.www.dao;

import com.chenyu.www.po.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 86323
 */
public interface UserDao {

    /**
     * 验证登录账户密码是否正常
     * @param user 实体类
     * @return  是否正确
     */
    Boolean login(User user) ;

    /**
     * 将数据库中的用户所有信息载入
     * @param user 实体类
     * @return 实体类
     */
    User informationInput(User user) ;

    /**
     * 将ResultSet的内容载入user类
     * @param user 实体类
     * @param resultSet ResultSet
     * @return user类
     */
    User load(User user, ResultSet resultSet) throws SQLException;

    /**
     * 判断数据库中是否存在该账户
     * @param userAccount 用户账户
     * @return  true有该账户，false没有该账户
     */
    Boolean isHaveAccount(String userAccount);

    /**
     * 判断数据库是否存在该身份证
     * @param userIdCard 要注册用户身份证
     * @return  true有该身份证，false没有该身份证
     */
    Boolean isHaveIdCard(String userIdCard);

    /**
     * 将要注册的用户存入数据库
     * @param user 要增加进数据库的user类
     */
    void register(User user);

    /**
     * 更改用户密码
     * @param user 更改密码的user类
     */
    void updatePassword(User user);

    /**
     * 判断数据库是否存在一个用户账户身份证和user类中的相同
     * @param user 要判断的user类
     * @return  是返回true 否返回false
     */
    Boolean isHaveAccountAndIdCard(User user);

    /**
     * 更新数据库一个用户内容
     * @param user 更新用户的user类
     * @return 是返回true 否返回false
     */
    Boolean updateUser(User user);

    /**
     * 查询数据库中所有用户数量
     * @return 总数据数
     */
    int totalCount();

    /**
     * 找到某一页的page类
     * @param page 页数类
     * @return 存有用户集合的page类
     */
    Page<User> findAllUser(Page<User> page);

    /**
     * 更新用户数据库用户头像的名称
     * @param user 新头像的名称
     * @return 是否更改成功
     */
    Boolean updateUserPhoto(User user);

    /**
     * 模拟搜索数据库是否有该用户账户的用户
     * @param userAccount 用户账户
     * @param page 空的page类
     * @return 成功返回Page类，失败返回null
     */
    Page<User> findUserByVagueAccount(String userAccount,Page<User> page);

    /**
     * 得到数据库模糊查询后的用户总数
     * @param userAccount 模糊查询的用户账户
     * @return 模糊查询的用户总数
     */
    int getTotalVagueUser(String userAccount);

    /**
     * 管理员改变指定用户是否可以出售,改变数据库用户user_can为否和ser_reason
     * @param user 要禁售的用户信息
     * @param sqlUserCan 拼接sql语句 格式为 否 或 是
     * @return 是否禁售成功
     */
    boolean banUserSell(User user,String sqlUserCan);

    /**
     * 用户申请解禁，改变数据库中的user_appeal
     * @param user 要禁售的用户信息
     * @return 是否禁售成功
     */
    boolean appealSell(User user);

    /**
     * 管理员解禁被禁售的用户，即将数据库对应用户的user_can设为是
     * @param userAccount 用户账户
     * @return 是否解禁成功
     */
    boolean allowSell(String userAccount);

    /**
     * 更新当前用户在数据库的积分
     * @param user 用户
     * @return 是否更新成功
     */
    boolean updateUserPoint(User user);




    /**
     * 纯粹增加订单的数据库的sql
     * @param order 订单
     * @return 是否增加
     */
    boolean addOrder(Order order);

    /**
     * 用户不使用积分购买商品,提交订单，从数据库中增加订单
     * @param order 订单
     * @return 是否增加成功
     */
    double addOrderNoPoint(Order order);

    /**
     * 用户使用积分减少付款，从数据库增加订单
     * @param order 订单
     * @return 返回当前用户剩余积分
     */
    double addOneOrderWithPoint(Order order);

    /**
     * 用户以不同身份访问，得到不同状态的订单
     * @param userOrder userOrder
     * @param sqlOrderUser 根据用户不同身份设置查找订单是购买者还是出售者,格式为orders_xxx=? xxx为buyer和seller
     * @param sqlOrderState 想要查找的订单的状态
     * @return userOrder
     */
    UserOrder findOrderByState(UserOrder userOrder,String sqlOrderUser,String sqlOrderState);

    /**
     * 用户以不同身份访问，得到不同订单
     * @param userOrder userOrder
     * @param sqlOrderUser 根据用户不同身份设置查找订单是购买者还是出售者,格式为orders_xxx  xxx为buyer和seller
     * @param sqlOrderAccount 要匹配的用户账户 格式为 orders_xxx 与前者相反
     * @param sqlOrderDelete 要匹配的用户是否有申请删除，格式为orders_xxx xxx为buyDelete或sellDelete
     * @return userOrder
     */
    UserOrder findOrder(UserOrder userOrder,String sqlOrderUser,String sqlOrderAccount,String sqlOrderDelete);

    /**
     * 将数据库一行订单载入LinkedList集合
     * @param linkedList 集合
     * @param resultSet 结果集
     * @return 集合
     */
    LinkedList<OrderShow> loadLinkList(LinkedList<OrderShow> linkedList,ResultSet resultSet);

    /**
     * 改变数据库对应订单的状态
     * @param order 订单
     * @param orderState 订单状态
     * @return 是否改变成功
     */
    boolean updateOrderState(Order order,String orderState);

    /**
     * 删除订单，但不是完全删除
     * 如果是购买者删除订单，则把数据库该订单的order_buyerDelete设为1
     * 如果是出售者删除订单，则把数据库该订单的order_sellerDelete设为1
     * 如果订单的order_buyerDelete和order_sellerDelete，则把数据库的该订单删除
     * @param order 与数据库一一对应的订单
     * @param sqlOrderUser 根据用户不同身份设置对应订单的账户为null，格式为orders_xxxx xxx为buyerDelete或sellerDelete
     * @return 是否删除成功
     */
    boolean removerOrder(Order order,String sqlOrderUser);

    /**
     * 购买用户取消已发送的订单,即完全删除数据库对应订单
     * @param order 订单
     * @return 是否删除成功
     */
    boolean deleteOrder(Order order);

    /**
     * 用户取消已发送的订单，检查数据库该订单的实付款和应付款是否相同来判断是否要返回用户积分
     * @param order 订单
     * @return 用户是否返回积分，是返回积分的大小，否返回一个较大负数
     */
    double checkOrderSumIsSame(Order order);

    /**
     * 根据用户的身份和订单的状态，得到相应的订单
     * @param order 展示给前端的UserOrder
     * @param sqlOrderUser 用户身份，格式为 orders_xxx=?
     * @param orderState 订单状态，格式为 xxx; 使用常量类的订单状态
     * @return userOrder
     */
    UserOrder findLimitOrder(UserOrder order,String sqlOrderUser,String orderState);

    /**
     * 买家用户收货后对订单进行评价和给予等级，即修改对应订单的orders_comment和orders_star
     * @param order 订单
     * @return 是否增加成功
     */
    boolean buyCommentOrder(Order order);

    /**
     * 买家回复买家的评价和评分，即修改数据库中对应订单的orders_reply
     * @param order 订单
     * @return 是否修改成功
     */
    boolean sellReplyOrder(Order order);

    /**
     * 用户作为卖家从数据库查出订单，同时在D:/market/sql/下生成对应文件名称的文件
     * @param userOrder userOrder
     * @param fileName 文件名称 格式为xxx.txt
     * @return 是否生成文件成功
     */
    boolean sellExportFile(UserOrder userOrder,String fileName);

    /**
     * 用户作为买家从数据库查出订单，同时在D:/market/sql/下生成对应文件名称的文件
     * @param userOrder userOrder
     * @param fileName 文件名称 格式为xxx.txt
     * @return 是否生成文件成功
     */
    boolean buyExportFile(UserOrder userOrder,String fileName);
}
