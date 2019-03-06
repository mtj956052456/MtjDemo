package com.example.pmprogect.fragment.explore.entity;

import java.util.List;

/**
 * Created by 蛟龙之子 on 2017/11/6.
 */

public class PMBean {

    /**
     * total : 30
     * rows : [{"gid":"246","usergid":"1363","username":"哥哥","headimg":"http://wx.qlogo.cn/mmopen/vi_32/wprwBKG6jpwHib4bfl9K9VlNbdyqvF9dm8a0CSGs1cFdQSowIFobLbbiaiabiaQBWOVZHWCeDtEfxTdQZJCD5ic9xow/0","city":"","clienttype":"IOS","title":"数据不对","content":"藁城市环保局的AQI指数不对","answernum":"0","imgnum":"0","imgurl":"","thumbnail":"","likenum":"0","time":"2017-11-06 14:55:29","type":"","status":"0"},{"gid":"245","usergid":"1315","username":"五味杂陈","headimg":"http://wx.qlogo.cn/mmhead/Q3auHgzwzM7ldu1L7viasCqViahIZzgvbxFer4CVicoicj45Up4kNpvOzg/0","city":"","clienttype":"IOS","title":"APP切换地图时闪退，这个问题怎么解决？","content":"苹果APP查看地图不能进入，病闪退，求解决。","answernum":"0","imgnum":"0","imgurl":"","thumbnail":"","likenum":"1","time":"2017-11-03 05:59:13","type":"","status":"0"},{"gid":"244","usergid":"1034","username":"孙向中","headimg":"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLwdKR3AtWTh48BtvQ71ZEtSshuBRqbDiaDBZ1DHAW4lu2mhGvzwD6zHmicvaTEZdwRr88iaRApJeNVA/0","city":"","clienttype":"Android","title":"应显示各县市区6项数据","content":"各县区6项数据实时发布，才能精准施策，科学治霾。","answernum":"0","imgnum":"0","imgurl":"","thumbnail":"","likenum":"0","time":"2017-09-02 04:26:16","type":"ti","status":"0"}]
     */

    private int total;
    private List<RowsBean> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * gid : 246
         * usergid : 1363
         * username : 哥哥
         * headimg : http://wx.qlogo.cn/mmopen/vi_32/wprwBKG6jpwHib4bfl9K9VlNbdyqvF9dm8a0CSGs1cFdQSowIFobLbbiaiabiaQBWOVZHWCeDtEfxTdQZJCD5ic9xow/0
         * city :
         * clienttype : IOS
         * title : 数据不对
         * content : 藁城市环保局的AQI指数不对
         * answernum : 0
         * imgnum : 0
         * imgurl :
         * thumbnail :
         * likenum : 0
         * time : 2017-11-06 14:55:29
         * type :
         * status : 0
         */

        private String gid;
        private String usergid;
        private String username;
        private String headimg;
        private String city;
        private String clienttype;
        private String title;
        private String content;
        private String answernum;
        private String imgnum;
        private String imgurl;
        private String thumbnail;
        private String likenum;
        private String time;
        private String type;
        private String status;

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public String getUsergid() {
            return usergid;
        }

        public void setUsergid(String usergid) {
            this.usergid = usergid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getClienttype() {
            return clienttype;
        }

        public void setClienttype(String clienttype) {
            this.clienttype = clienttype;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAnswernum() {
            return answernum;
        }

        public void setAnswernum(String answernum) {
            this.answernum = answernum;
        }

        public String getImgnum() {
            return imgnum;
        }

        public void setImgnum(String imgnum) {
            this.imgnum = imgnum;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getLikenum() {
            return likenum;
        }

        public void setLikenum(String likenum) {
            this.likenum = likenum;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "RowsBean{" +
                    "gid='" + gid + '\'' +
                    ", usergid='" + usergid + '\'' +
                    ", username='" + username + '\'' +
                    ", headimg='" + headimg + '\'' +
                    ", city='" + city + '\'' +
                    ", clienttype='" + clienttype + '\'' +
                    ", title='" + title + '\'' +
                    ", content='" + content + '\'' +
                    ", answernum='" + answernum + '\'' +
                    ", imgnum='" + imgnum + '\'' +
                    ", imgurl='" + imgurl + '\'' +
                    ", thumbnail='" + thumbnail + '\'' +
                    ", likenum='" + likenum + '\'' +
                    ", time='" + time + '\'' +
                    ", type='" + type + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        }
    }
}
