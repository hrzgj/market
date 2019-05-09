package com.chenyu.www.service;

import com.chenyu.www.po.*;

import java.util.List;

/**
 * @author 86323
 */
public interface TradeService {
    /**
     * 增加商品
     * @param trade 商品信息
     * @return 增加是否成功
     */
    Boolean addTrade(Trade trade);

    /**
     * 找到当前页的商品在售的page类
     * @param tradePage 存储当前页page类
     * @param sort 执行什么排序
     * @return 返回page类
     */
    Page<Trade> findTrade(Page<Trade> tradePage,int sort);

    /**
     * 得到数据库中商品状态为在售的总个数
     * @return 数据库中商品状态为在售的总个数
     */
    int getTotalTrade();

    /**
     * 找到当前页审核状态的商品的page类
     * @param tradePage 待载入的page类
     * @return 载入完毕的page类
     */
    Page<Trade> adminFindCheck(Page<Trade> tradePage);

    /**
     * 得到商品状态为审核的总个数
     * @return 商品个数
     */
    int getNeedCheck();

    /**
     * 根据商品id返回带有全部数据的商品类
     * @param tradeId  商品id
     * @return 商品类
     */
    Trade findTradeByID(int tradeId);

    /**
     * 根据商品id改变该商品的状态，从审核改为发售
     * @param tradeId 商品id
     * @return 是否改变成功
     */
    Boolean updateTradeState(int tradeId);

    /**
     * 管理员退回商品，修改对应商品的状态为审核
     * @param tradeId 商品id
     * @return 是否修改成功
     */
    boolean adminSendBack(int tradeId);

    /**
     * 删除指定商品
     * @param tradeId 商品id
     * @return 是否删除成功
     */
    boolean removeTrade(int tradeId);

    /**
     * 用户修改商品状态为申请删除
     * @param tradeId 商品id
     * @return 是否修改成功
     */
    boolean readyRemoveTrade(int tradeId);

    /**
     * 根据用户id得到对应的商品总数
     * @param userId 用户id
     * @return 商品总个数
     */
    int getTotalByUser(String userId);

    /**
     * 根据用户id得到该用户发售商品的page类
     * @param tradePage 待载入的page类
     * @param userID 用户id
     * @return 载入完毕的page类
     */
    Page<Trade> findTradeByUser(Page<Trade> tradePage,String userID);

    /**
     * 更新对应的商品
     * @param trade 要更改内容的商品类
     * @return 更改是否成功
     */
    Boolean updateTrade(Trade trade);

    /**
     * 更新对应的商品图片
     * @param trade 要更改内容的商品类
     * @return 更改是否成功
     */
    Boolean updateTradePhoto(Trade trade);

    /**
     * 根据用户账户模糊搜索出商品的个数
     * @param userAccount 用户账户
     * @return 模糊搜索的商品个数
     */
    int getTotalVagueByUserAccount(String userAccount);

    /**
     * 根据用户输出模糊搜索出商品
     * @param userAccount 用户输入的账户
     * @param page 待载入的page类
     * @return 载入完毕的page类
     */
    Page<Trade> findVagueByUserAccount(String userAccount,Page<Trade> page);

    /**
     * 根据用户输出模糊搜索出商品的个数
     * @param tradeName 商品名称
     * @return 模糊搜索的商品个数
     */
    int getTotalVagueByTradeName(String tradeName);

    /**
     * 根据用户输出模糊搜索出商品
     * @param tradeName 用户输入的商品名称
     * @param page 待载入的page类
     * @return 载入完毕的page类
     */
    Page<Trade> findVagueByTradeName(String tradeName,Page<Trade> page);

    /**
     * 根据用户选择的商品种类搜索出对应种类商品的个数
     * @param tradeKind 商品种类
     * @return 如果商品种类正确就返回商品个数，否则返回一则错误提示字符串
     */
    int getTotalByTradeKind(String tradeKind);

    /**
     * 根据商品类型中搜索出商品
     * @param tradeKind 商品类型
     * @param page 待载入的page类
     * @return page
     */
    Page<Trade> findByTradeKind(String tradeKind,Page<Trade> page);

    /**
     * 判断商品类型是否是输入正确
     * @param tradeKind 商品类型
     * @return 是否是正确的类型
     */
    boolean isRealTradeKind(String tradeKind);

    /**
     * 增加商品进入用户的购物车
     * @param car 购物车
     * @return 是否增加成功
     */
    boolean addTradeToCar(Car car);

    /**
     * 将对应用户的购物车装载成userCar
     * @param userCar 展示给前端的javaBean
     * @return userCar
     */
    UserCar findCar(UserCar userCar);

    /**
     * 从数据库移除该用户购物车的指定商品
     * @param tradeId 商品id
     * @param userAccount 用户账户
     * @return 移除是否成功
     */
    boolean removeTrade(int tradeId,String userAccount);

    /**
     * 根据商品id移除userCar中的商品，从而不需要从数据库重新取userCar
     * @param tradeId 商品id
     * @param userCar 前端javaBean
     * @return userCar
     */
    UserCar removeTrade(int tradeId,UserCar userCar);

    /**
     * 移除该用户购物车指定商品的一个购买量，若商品只有一个数量，则移除商品
     * @param tradeId 商品id
     * @param userAccount 用户账户
     * @return 是否删除成功
     */
    boolean removeOneAmount(int tradeId,String userAccount);

    /**
     * 根据商品id移除userCar中的商品，从而不需要从数据库重新取userCar
     * @param tradeId 商品id
     * @param userCar userCar
     * @return userCar
     */
    UserCar removeOneAmount(int tradeId,UserCar userCar);

    /**
     * 删除该用户的购物车内容
     * @param userAccount 用户账户
     * @return 是否删除成功
     */
    boolean removeAll(String userAccount);

    /**
     * 将用户购物车所有商品变成订单
     * @param userCar 用户购物车
     * @param user 用户
     * @return 是否增加成功
     */
    boolean addCarToOrder(UserCar userCar,User user);
}
