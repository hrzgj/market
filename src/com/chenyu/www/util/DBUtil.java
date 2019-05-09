package com.chenyu.www.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * DB工具类：通用的数据操作方法
 * @author 86323
 */
public class DBUtil {

    private static final String URL  ="jdbc:mysql://localhost:3306/market?useUnicode=true&characterEncoding=UTF-8" ;
    private static final String JDBC_NAME = "com.mysql.jdbc.Driver";
    private static final String USERNAME  ="root" ;
    private static final String PASSWORD  ="2289360" ;
    public static PreparedStatement pstmt = null ;
    public static Connection connection = null ;
    public static ResultSet rs = null ;
    static{
        try {
            Class.forName(JDBC_NAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * 通用的增删改方法
     * @param sql sql语句
     * @param params 增删改的集合
     * @return 成功与否
     */
    public static boolean executeUpdate(String sql,Object[] params) {
        try {
            //setXxx()方法的个数 依赖于 ?的个数， 而?的个数 又和 数组params的个数一致
            //setXxx()方法的个数 ->数组params的个数一致
            pstmt = createPreParedStatement(sql,params);
            return pstmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return false ;
        }catch (Exception e) {
            e.printStackTrace();
            return false ;
        }
        finally {
            closeAll(null,pstmt,connection);
        }
    }

    /**
     * 关闭
     * @param rs ResultSet
     * @param stmt Statement
     * @param connection Connection
     */
    public static void closeAll(ResultSet rs, Statement stmt, Connection connection)
    {
        try {
            if (rs!=null) {
                rs.close();
            }

            if(pstmt!=null){
                pstmt.close();
            }
            if(connection!=null){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    /**
     * 获得Connection
     * @return Connection
     * @throws SQLException SQLException
     */
    private static Connection getConnection() throws SQLException {
        connection= DriverManager.getConnection( URL,USERNAME,PASSWORD ) ;
        return connection;
    }


    /**
     * 将集合按顺序插入PreparedStatement
     * @param sql sql语句
     * @param params 集合
     * @return PreparedStatement
     * @throws SQLException SQLException
     */
    private static PreparedStatement createPreParedStatement(String sql, Object[] params) throws SQLException {
        pstmt = getConnection() .prepareStatement( sql) ;
        if(params!=null ) {
            for(int i=0;i<params.length;i++) {
                pstmt.setObject(i+1, params[i]);
            }
        }
        return pstmt;
    }


    /**
     * 通用的查  :适合任何查询
     * @param sql sql语句
     * @param params  更新的集合
     * @return  ResultSet
     */
    public static ResultSet executeQuery( String sql ,Object[] params)
    {
        try {

            pstmt = createPreParedStatement(sql,params);
            rs =  pstmt.executeQuery() ;
            return rs ;
        } catch (SQLException e) {
            e.printStackTrace();
            return null ;
        }catch (Exception e) {
            e.printStackTrace();
            return null ;
        }
    }

    /**
     * 判断结果集中是否有元素
     * @param resultSet 结果集
     * @return  true 有 ，false 没有
     */
    public static Boolean isHaveResultSet(ResultSet resultSet){
        try {
            if(resultSet!=null && resultSet.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeAll(resultSet,pstmt,connection);
        }
        return false;
    }

    /**
     * 查询总数据数
     * @param sql sql语句
     * @return 总数据数
     */
    public static int getTotalCount(String sql ,Object[] params) {
        int count = -1 ;
        try {
            pstmt = createPreParedStatement(sql, params );
            rs = pstmt.executeQuery()  ;
            if(rs.next()) {
                count = rs.getInt(1) ;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeAll(rs, pstmt, connection);
        }
        return count;
    }


}
