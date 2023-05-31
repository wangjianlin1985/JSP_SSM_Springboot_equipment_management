package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Device {
    /*记录id*/
    private Integer deviceId;
    public Integer getDeviceId(){
        return deviceId;
    }
    public void setDeviceId(Integer deviceId){
        this.deviceId = deviceId;
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

    /*设备图片*/
    private String devicePhoto;
    public String getDevicePhoto() {
        return devicePhoto;
    }
    public void setDevicePhoto(String devicePhoto) {
        this.devicePhoto = devicePhoto;
    }

    /*规格型号*/
    @NotEmpty(message="规格型号不能为空")
    private String guige;
    public String getGuige() {
        return guige;
    }
    public void setGuige(String guige) {
        this.guige = guige;
    }

    /*设备状态*/
    @NotEmpty(message="设备状态不能为空")
    private String deviceState;
    public String getDeviceState() {
        return deviceState;
    }
    public void setDeviceState(String deviceState) {
        this.deviceState = deviceState;
    }

    /*使用日期*/
    @NotEmpty(message="使用日期不能为空")
    private String useDate;
    public String getUseDate() {
        return useDate;
    }
    public void setUseDate(String useDate) {
        this.useDate = useDate;
    }

    /*检修周期*/
    @NotEmpty(message="检修周期不能为空")
    private String jxzq;
    public String getJxzq() {
        return jxzq;
    }
    public void setJxzq(String jxzq) {
        this.jxzq = jxzq;
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
    private String jhsj;
    public String getJhsj() {
        return jhsj;
    }
    public void setJhsj(String jhsj) {
        this.jhsj = jhsj;
    }

    /*设备描述*/
    private String deviceDesc;
    public String getDeviceDesc() {
        return deviceDesc;
    }
    public void setDeviceDesc(String deviceDesc) {
        this.deviceDesc = deviceDesc;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonDevice=new JSONObject(); 
		jsonDevice.accumulate("deviceId", this.getDeviceId());
		jsonDevice.accumulate("deviceTypeObj", this.getDeviceTypeObj().getTypeName());
		jsonDevice.accumulate("deviceTypeObjPri", this.getDeviceTypeObj().getTypeId());
		jsonDevice.accumulate("deviceName", this.getDeviceName());
		jsonDevice.accumulate("devicePhoto", this.getDevicePhoto());
		jsonDevice.accumulate("guige", this.getGuige());
		jsonDevice.accumulate("deviceState", this.getDeviceState());
		jsonDevice.accumulate("useDate", this.getUseDate().length()>19?this.getUseDate().substring(0,19):this.getUseDate());
		jsonDevice.accumulate("jxzq", this.getJxzq());
		jsonDevice.accumulate("ssdw", this.getSsdw());
		jsonDevice.accumulate("jhsj", this.getJhsj().length()>19?this.getJhsj().substring(0,19):this.getJhsj());
		jsonDevice.accumulate("deviceDesc", this.getDeviceDesc());
		return jsonDevice;
    }}