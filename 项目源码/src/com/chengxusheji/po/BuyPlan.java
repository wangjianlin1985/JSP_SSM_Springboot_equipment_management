package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class BuyPlan {
    /*记录id*/
    private Integer planId;
    public Integer getPlanId(){
        return planId;
    }
    public void setPlanId(Integer planId){
        this.planId = planId;
    }

    /*设备类型*/
    private DeviceType deviceTypeObj;
    public DeviceType getDeviceTypeObj() {
        return deviceTypeObj;
    }
    public void setDeviceTypeObj(DeviceType deviceTypeObj) {
        this.deviceTypeObj = deviceTypeObj;
    }

    /*设备名称*/
    @NotEmpty(message="设备名称不能为空")
    private String deviceName;
    public String getDeviceName() {
        return deviceName;
    }
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    /*数量*/
    @NotNull(message="必须输入数量")
    private Integer buyNum;
    public Integer getBuyNum() {
        return buyNum;
    }
    public void setBuyNum(Integer buyNum) {
        this.buyNum = buyNum;
    }

    /*金额*/
    @NotNull(message="必须输入金额")
    private Float buyMoney;
    public Float getBuyMoney() {
        return buyMoney;
    }
    public void setBuyMoney(Float buyMoney) {
        this.buyMoney = buyMoney;
    }

    /*规格型号*/
    private String guige;
    public String getGuige() {
        return guige;
    }
    public void setGuige(String guige) {
        this.guige = guige;
    }

    /*用途*/
    private String yongtu;
    public String getYongtu() {
        return yongtu;
    }
    public void setYongtu(String yongtu) {
        this.yongtu = yongtu;
    }

    /*使用单位*/
    @NotEmpty(message="使用单位不能为空")
    private String sydw;
    public String getSydw() {
        return sydw;
    }
    public void setSydw(String sydw) {
        this.sydw = sydw;
    }

    /*计划时间*/
    private String jhsj;
    public String getJhsj() {
        return jhsj;
    }
    public void setJhsj(String jhsj) {
        this.jhsj = jhsj;
    }

    /*是否购置*/
    @NotEmpty(message="是否购置不能为空")
    private String sfgz;
    public String getSfgz() {
        return sfgz;
    }
    public void setSfgz(String sfgz) {
        this.sfgz = sfgz;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonBuyPlan=new JSONObject(); 
		jsonBuyPlan.accumulate("planId", this.getPlanId());
		jsonBuyPlan.accumulate("deviceTypeObj", this.getDeviceTypeObj().getTypeName());
		jsonBuyPlan.accumulate("deviceTypeObjPri", this.getDeviceTypeObj().getTypeId());
		jsonBuyPlan.accumulate("deviceName", this.getDeviceName());
		jsonBuyPlan.accumulate("buyNum", this.getBuyNum());
		jsonBuyPlan.accumulate("buyMoney", this.getBuyMoney());
		jsonBuyPlan.accumulate("guige", this.getGuige());
		jsonBuyPlan.accumulate("yongtu", this.getYongtu());
		jsonBuyPlan.accumulate("sydw", this.getSydw());
		jsonBuyPlan.accumulate("jhsj", this.getJhsj().length()>19?this.getJhsj().substring(0,19):this.getJhsj());
		jsonBuyPlan.accumulate("sfgz", this.getSfgz());
		return jsonBuyPlan;
    }}