package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class SiteBase {
    /*记录id*/
    private Integer siteBaseId;
    public Integer getSiteBaseId(){
        return siteBaseId;
    }
    public void setSiteBaseId(Integer siteBaseId){
        this.siteBaseId = siteBaseId;
    }

    /*所属单位*/
    @NotEmpty(message="所属单位不能为空")
    private String ssdw;
    public String getSsdw() {
        return ssdw;
    }
    public void setSsdw(String ssdw) {
        this.ssdw = ssdw;
    }

    /*归属地*/
    @NotEmpty(message="归属地不能为空")
    private String gsd;
    public String getGsd() {
        return gsd;
    }
    public void setGsd(String gsd) {
        this.gsd = gsd;
    }

    /*站点代号*/
    @NotEmpty(message="站点代号不能为空")
    private String zddh;
    public String getZddh() {
        return zddh;
    }
    public void setZddh(String zddh) {
        this.zddh = zddh;
    }

    /*站点名称*/
    @NotEmpty(message="站点名称不能为空")
    private String zdmc;
    public String getZdmc() {
        return zdmc;
    }
    public void setZdmc(String zdmc) {
        this.zdmc = zdmc;
    }

    /*站点类别*/
    @NotEmpty(message="站点类别不能为空")
    private String zdlb;
    public String getZdlb() {
        return zdlb;
    }
    public void setZdlb(String zdlb) {
        this.zdlb = zdlb;
    }

    /*通讯方式*/
    @NotEmpty(message="通讯方式不能为空")
    private String txfs;
    public String getTxfs() {
        return txfs;
    }
    public void setTxfs(String txfs) {
        this.txfs = txfs;
    }

    /*安装地点*/
    @NotEmpty(message="安装地点不能为空")
    private String zzdd;
    public String getZzdd() {
        return zzdd;
    }
    public void setZzdd(String zzdd) {
        this.zzdd = zzdd;
    }

    /*经度*/
    @NotNull(message="必须输入经度")
    private Float longitude;
    public Float getLongitude() {
        return longitude;
    }
    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    /*纬度*/
    @NotNull(message="必须输入纬度")
    private Float latitude;
    public Float getLatitude() {
        return latitude;
    }
    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonSiteBase=new JSONObject(); 
		jsonSiteBase.accumulate("siteBaseId", this.getSiteBaseId());
		jsonSiteBase.accumulate("ssdw", this.getSsdw());
		jsonSiteBase.accumulate("gsd", this.getGsd());
		jsonSiteBase.accumulate("zddh", this.getZddh());
		jsonSiteBase.accumulate("zdmc", this.getZdmc());
		jsonSiteBase.accumulate("zdlb", this.getZdlb());
		jsonSiteBase.accumulate("txfs", this.getTxfs());
		jsonSiteBase.accumulate("zzdd", this.getZzdd());
		jsonSiteBase.accumulate("longitude", this.getLongitude());
		jsonSiteBase.accumulate("latitude", this.getLatitude());
		return jsonSiteBase;
    }}