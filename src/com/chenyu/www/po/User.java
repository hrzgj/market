package com.chenyu.www.po;

/**
 * User实体类
 * @author 86323
 */
public class User {

    /**
     * 用户账户
     */
    private String userAccount;
    /**
     * 用户昵称
     */
    private String userName;
    /**
     * 用户密码
     */
    private String userPassword;
    /**
     * 用户身份
     */
    private String userIdentity;
    /**
     * 用户手机号
     */
    private String userPhone;
    /**
     * 用户身份证
     */
    private String userIdCard;
    /**
     * 用户图片文件名
     */
    private String userPhoto;
    /**
     * 用户地址
     */
    private String userAddress;
    /**
     * 用户是否可以发布商品
     */
    private String userCan;
    /**
     * 用户等级
     */
    private int userGrade;
    /**
     * 用户积分
     */
    private double userPoint;
    /**
     *用户星级
     */
    private int userStar;
    /**
     *用户购物车编号
     */
    private int userCar;

    public int getUserGrade() {
        return userGrade;
    }

    public double getUserPoint() {
        return userPoint;
    }

    public void setUserPoint(double userPoint) {
        this.userPoint = userPoint;
    }

    /**
     * 用户被禁售的原因
     */
    private String userReason;

    /**
     * 用户申诉的内容
     */
    private String userAppeal;

    public String getUserReason() {
        return userReason;
    }

    public void setUserReason(String userReason) {
        this.userReason = userReason;
    }

    public String getUserAppeal() {
        return userAppeal;
    }

    public void setUserAppeal(String userAppeal) {
        this.userAppeal = userAppeal;
    }

    public User() {
    }

    public User(String userAccount, String userName, String userPassword, String userIdentity, String userPhone, String userIdCard, String userPhoto, String userAddress, String userCan, int userGrade, int userPoint, int userStar, int userCar) {
        this.userAccount = userAccount;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userIdentity = userIdentity;
        this.userPhone = userPhone;
        this.userIdCard = userIdCard;
        this.userPhoto = userPhoto;
        this.userAddress = userAddress;
        this.userCan = userCan;
        this.userGrade = userGrade;
        this.userPoint = userPoint;
        this.userStar = userStar;
        this.userCar = userCar;
    }

    public User(String userAccount, String userName, String userPassword, String userIdentity, String userCan, int userGrade, int userPoint, int userStar, int userCar) {
        this.userAccount = userAccount;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userIdentity = userIdentity;
        this.userCan = userCan;
        this.userGrade = userGrade;
        this.userPoint = userPoint;
        this.userStar = userStar;
        this.userCar = userCar;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserIdentity() {
        return userIdentity;
    }

    public void setUserIdentity(String userIdentity) {
        this.userIdentity = userIdentity;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserIdCard() {
        return userIdCard;
    }

    public void setUserIdCard(String userIdCard) {
        this.userIdCard = userIdCard;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserCan() {
        return userCan;
    }

    public void setUserCan(String userCan) {
        this.userCan = userCan;
    }

    public void setUserGrade(int userGrade) {
        this.userGrade = userGrade;
    }

    public int getUserStar() {
        return userStar;
    }

    public void setUserStar(int userStar) {
        this.userStar = userStar;
    }

    public int getUserCar() {
        return userCar;
    }

    public void setUserCar(int userCar) {
        this.userCar = userCar;
    }

    @Override
    public String toString() {
        return "User{" +
                "userAccount='" + userAccount + '\'' +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userIdentity='" + userIdentity + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", userIdCard='" + userIdCard + '\'' +
                ", userPhoto='" + userPhoto + '\'' +
                ", userAddress='" + userAddress + '\'' +
                ", userCan='" + userCan + '\'' +
                ", userGrade=" + userGrade +
                ", userPoint=" + userPoint +
                ", userStar=" + userStar +
                ", userCar=" + userCar +
                '}';
    }
}
