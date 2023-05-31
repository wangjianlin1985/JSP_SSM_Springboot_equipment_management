package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class SiteRun {
    /*记录id*/
    private Integer siteRunId;
    public Integer getSiteRunId(){
        return siteRunId;
    }
    public void setSiteRunId(Integer siteRunId){
        this.siteRunId = siteRunId;
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

    /*站点代号*/
    @NotEmpty(message="站点代号不能为空")
    private String zddh;
    public String getZddh() {
        return zddh;
    }
    public void setZddh(String zddh) {
        this.zddh = zddh;
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

    /*站点名称*/
    @NotEmpty(message="站点名称不能为空")
    private String zdmc;
    public String getZdmc() {
        return zdmc;
    }
    public void setZdmc(String zdmc) {
        this.zdmc = zdmc;
    }

    /*故障类型*/
    @NotEmpty(message="故障类型不能为空")
    private String gzlx;
    public String getGzlx() {
        return gzlx;
    }
    public void setGzlx(String gzlx) {
        this.gzlx = gzlx;
    }

    /*故障时间*/
    @NotEmpty(message="故障时间不能为空")
    private String gzsj;
    public String getGzsj() {
        return gzsj;
    }
    public void setGzsj(String gzsj) {
        this.gzsj = gzsj;
    }

    /*故障时长*/
    @NotEmpty(message="故障时长不能为空")
    private String gzsc;
    public String getGzsc() {
        return gzsc;
    }
    public void setGzsc(String gzsc) {
        this.gzsc = gzsc;
    }

    /*恢复情况*/
    @NotEmpty(message="恢复情况不能为空")
    private String hfqk;
    public String getHfqk() {
        return hfqk;
    }
    public void setHfqk(String hfqk) {
        this.hfqk = hfqk;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonSiteRun=new JSONObject(); 
		jsonSiteRun.accumulate("siteRunId", this.getSiteRunId());
		jsonSiteRun.accumulate("ssdw", this.getSsdw());
		jsonSiteRun.accumulate("zddh", this.getZddh());
		jsonSiteRun.accumulate("zdlb", this.getZdlb());
		jsonSiteRun.accumulate("zdmc", this.getZdmc());
		jsonSiteRun.accumulate("gzlx", this.getGzlx());
		jsonSiteRun.accumulate("gzsj", this.getGzsj().length()>19?this.getGzsj().substring(0,19):this.getGzsj());
		jsonSiteRun.accumulate("gzsc", this.getGzsc());
		jsonSiteRun.accumulate("hfqk", this.getHfqk());
		return jsonSiteRun;
    }}