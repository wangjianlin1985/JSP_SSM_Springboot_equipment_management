package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Maintain {
    /*记录id*/
    private Integer maintainId;
    public Integer getMaintainId(){
        return maintainId;
    }
    public void setMaintainId(Integer maintainId){
        this.maintainId = maintainId;
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

    /*计划时间*/
    @NotEmpty(message="计划时间不能为空")
    private String jhsj;
    public String getJhsj() {
        return jhsj;
    }
    public void setJhsj(String jhsj) {
        this.jhsj = jhsj;
    }

    /*维护内容*/
    private String whnr;
    public String getWhnr() {
        return whnr;
    }
    public void setWhnr(String whnr) {
        this.whnr = whnr;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonMaintain=new JSONObject(); 
		jsonMaintain.accumulate("maintainId", this.getMaintainId());
		jsonMaintain.accumulate("ssdw", this.getSsdw());
		jsonMaintain.accumulate("jhsj", this.getJhsj().length()>19?this.getJhsj().substring(0,19):this.getJhsj());
		jsonMaintain.accumulate("whnr", this.getWhnr());
		return jsonMaintain;
    }}