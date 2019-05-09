package com.chenyu.www.dao;

import com.chenyu.www.po.*;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * @author 86323
 */
public interface TradeDao {
    /**
     * 根据结果集返回相应和集合
     * @param resultSet 结果集
     * @param list 集合
     * @return ArrayList集合
     */
    ArrayList<Trade> getTradeList(ResultSet resultSet,ArrayList<Trade> list);

    /**
     * 适合sql中有一句where的limit查询
     * @param lineName 拼接sql中的where部分
     * @param lineDetails where中？的值
     * @param tradePage page类
     * @return page类
     */
    Page<Trade> vagueFind(String lineName,String lineDetails,Page<Trade> tradePage);

    /**
     * 增加商品
     * @param trade 商品信息
     * @return 增加是否成功
     */
    Boolean addTrade(Trade trade);

    /**
     * 从数据库找到当前页在售的商品的page类
     * @param tradePage 要存储当前页page类
     * @param sort 排序的特征
     * @return 返回存储好的page类
     */
    Page<Trade> findTrade(Page<Trade> tradePage,int sort);

    /**
     * 将数据库的商品载入商品类
     * @param trade 商品trade类
     * @param resultSet 结果集
     * @return 装载好的商品类
     */
    Trade loadTrade(Trade trade, ResultSet resultSet);

    /**
     * 得到数据库中商品状态为在售的总个数
     * @return 数据库中商品状态为在售的总个数
     */
    int getTotalTrade();

    /**
     * 得到数据库中商品状态为审核的总个数
     * @return 商品个数
     */
    int getNeedCheck();

    /**
     * 得到数据库找到当前页审核状态的商品的page类
     * @param tradePage 待载入的page类
     * @return 载入完毕的page类
     */
    Page<Trade> adminFindCheck(Page<Trade> tradePage);

    /**
     * 根据商品id从数据库找到该商品载入商品类
     * @param tradeId  商品id
     * @return 商品类
     */
    Trade findTradeById(int tradeId);

    /**
     * 根据商品id改变数据库中该商品的状态
     * @param tradeId 商品id
     * @param sqlTradeState 商品状态，格式为 '常量类中商品状态'
     * @return 是否改变成功
     */
    Boolean updateTradeState(int tradeId,String sqlTradeState);

    /**
     * 从数据库删除指定商品
     * @param tradeId 商品id
     * @return 是否删除成功
     */
    boolean removeTrade(int tradeId);

    /**
     * 根据用户id得到数据库对应的商品总数
     * @param userId 用户id
     * @return 商品总个数
     */
    int getTotalByUser(String userId);

    /**
     * 根据用户id得到数据库找到该用户发售商品的page类
     * @param tradePage 待载入的page类
     * @param userID 用户id
     * @return 载入完毕的page类
     */
    Page<Trade> findTradeByUser(Page<Trade> tradePage,String userID);


    /**
     * 更新数据库对应的商品
     * @param trade 要更改内容的商品类
     * @return 更改是否成功
     */
    Boolean updateTrade(Trade trade);

    /**
     * 更新数据库对应的商品的图片
     * @param trade 要更改内容的商品类
     * @return 更改是否成功
     */
    Boolean updateTradePhoto(Trade trade);

    /**
     * 根据用户输出从数据库模糊搜索出商品的个数
     * @param userAccount 用户账户
     * @return 模糊搜索的商品个数
     */
    int getTotalVagueByUserAccount(String userAccount);

    /**
     * 根据用户输出从数据库模糊搜索出商品
     * @param userAccount 用户输入的账户
     * @param page 待载入的page类
     * @return 载入完毕的page类
     */
    Page<Trade> findVagueByUserAccount(String userAccount,Page<Trade> page);

    /**
     * 根据用户输出从数据库模糊搜索出商品的个数
     * @param tradeName 商品名称
     * @return 模糊搜索的商品个数
     */
    int getTotalVagueByTradeName(String tradeName);

    /**
     * 根据用户输出从数据库模糊搜索出商品
     * @param tradeName 用户输入的商品名称
     * @param page 待载入的page类
     * @return 载入完毕的page类
     */
    Page<Trade> findVagueByTradeName(String tradeName,Page<Trade> page);

    /**
     * 根据用户选择的商品种类从数据库搜索出对应种类商品的个数
     * @param tradeKind 商品种类
     * @return 搜索出的商品个数
     */
    int getTotalByTradeKind(String tradeKind);

    /**
     * 根据商品类型从数据库中搜索出商品
     * @param tradeKind 商品类型
     * @param page 待载入的page类
     * @return page
     */
    Page<Trade> findByTradeKind(String tradeKind,Page<Trade> page);

    /**
     * 数据库增加商品进入用户的购物车
     * @param car 购物车
     * @return 是否增加成功
     */
    boolean addTradeToCar(Car car);

    /**
     * 判断数据库该用户的购物车上是否有该商品
     * @param car 用户购物车
     * @return 数量
     */
    int isHaveTrade(Car car);

    /**
     * 将数据库的对应用户的购物车装载成userCar
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
    boolean removeTradeInCar(int tradeId,String userAccount);

    /**
     * 从数据库减少指定用户的指定商品的一个数量
     * @param car 向数据库更新的car类
     * @return 是否更新成功
     */
    boolean removeOneAmount(Car car);

    /**
     * 从数据库删除该用户的购物车内容
     * @param userAccount 用户账户
     * @return 是否删除成功
     */
    boolean removeAll(String userAccount);

    /**
     * 因为订单发货导致该商品的剩余数量减少和商品出售量增加
     * @param trade 该商品的实体类
     */
    void reduceTradeAmount(Trade trade);

    /**
     * 将用户购物车所有商品变成订单
     * @param list 订单集合
     * @param user 用户
     * @return 是否增加成功
     */
    boolean addCarToOrder(List<Order> list,User user);

    /**
     * 根据队列商品id顺序从数据库取出对应商品价格，存到一个新的队列返回
     * @param tradesId 存有多个商品id的队列
     * @return 存有多个商品价格的队列
     */
    Queue<Double> findTradesId(Queue<Integer> tradesId);
}
