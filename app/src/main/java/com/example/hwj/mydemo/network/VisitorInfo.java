package com.example.hwj.mydemo.network;

import java.util.List;


/**
 * 访客数据格式
 * Created by hwj on 17-7-20.
 */

public class VisitorInfo {

    private List<ResultListBean> resultList;

    public List<ResultListBean> getResultList () {
        return resultList;
    }

    public void setResultList (List<ResultListBean> resultList) {
        this.resultList = resultList;
    }

    public static class ResultListBean {
        //客户头像
        private String custAvatar;
        //客户userid
        private long custId;
        //客户名称
        private String custName;
        //客户昵称
        private String custNickName;
        //是否在线 0-不在线；1-在线
        private int isOnline;
        //上次访问时间
        private String lastViewTime;
        //浏览次数
        private int viewCnt;
        //浏览帖子id
        private long viewInfoId;
        //帖子发布时间
        private String viewInfoPostDate;
        //浏览帖子标题
        private String viewInfoTitle;
        //帖子url
        private String viewInfoUrl;
        //用户性别
        private String custGender;
        //是否查看过 1－查看过　０－没查看过
        private String isViewed;

        public String getIsViewed () {
            return isViewed;
        }

        public void setIsViewed (String isViewed) {
            this.isViewed = isViewed;
        }

        public String getCustNickName () {
            return custNickName;
        }

        public void setCustNickName (String custNickName) {
            this.custNickName = custNickName;
        }

        public String getCustGender () {
            return custGender;
        }

        public void setCustGender (String custGender) {
            this.custGender = custGender;
        }

        public String getCustAvatar () {
            return custAvatar;
        }

        public void setCustAvatar (String custAvatar) {
            this.custAvatar = custAvatar;
        }

        public long getCustId () {
            return custId;
        }

        public void setCustId (long custId) {
            this.custId = custId;
        }

        public String getCustName () {
            return custName;
        }

        public void setCustName (String custName) {
            this.custName = custName;
        }

        public int getIsOnline () {
            return isOnline;
        }

        public void setIsOnline (int isOnline) {
            this.isOnline = isOnline;
        }

        public String getLastViewTime () {
            return lastViewTime;
        }

        public void setLastViewTime (String lastViewTime) {
            this.lastViewTime = lastViewTime;
        }

        public int getViewCnt () {
            return viewCnt;
        }

        public void setViewCnt (int viewCnt) {
            this.viewCnt = viewCnt;
        }

        public long getViewInfoId () {
            return viewInfoId;
        }

        public void setViewInfoId (long viewInfoId) {
            this.viewInfoId = viewInfoId;
        }

        public String getViewInfoPostDate () {
            return viewInfoPostDate;
        }

        public void setViewInfoPostDate (String viewInfoPostDate) {
            this.viewInfoPostDate = viewInfoPostDate;
        }

        public String getViewInfoTitle () {
            return viewInfoTitle;
        }

        public void setViewInfoTitle (String viewInfoTitle) {
            this.viewInfoTitle = viewInfoTitle;
        }

        public String getViewInfoUrl () {
            return viewInfoUrl;
        }

        public void setViewInfoUrl (String viewInfoUrl) {
            this.viewInfoUrl = viewInfoUrl;
        }
    }

}
