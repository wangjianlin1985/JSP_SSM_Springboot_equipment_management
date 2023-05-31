package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class DataDraw {
    /*记录id*/
    private Integer drawId;
    public Integer getDrawId(){
        return drawId;
    }
    public void setDrawId(Integer drawId){
        this.drawId = drawId;
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

    /*图纸类别*/
    @NotEmpty(message="图纸类别不能为空")
    private String drawClass;
    public String getDrawClass() {
        return drawClass;
    }
    public void setDrawClass(String drawClass) {
        this.drawClass = drawClass;
    }

    /*图纸名称*/
    @NotEmpty(message="图纸名称不能为空")
    private String drawName;
    public String getDrawName() {
        return drawName;
    }
    public void setDrawName(String drawName) {
        this.drawName = drawName;
    }

    /*图纸描述*/
    private String drawDesc;
    public String getDrawDesc() {
        return drawDesc;
    }
    public void setDrawDesc(String drawDesc) {
        this.drawDesc = drawDesc;
    }

    /*图纸文件*/
    private String drawFile;
    public String getDrawFile() {
        return drawFile;
    }
    public void setDrawFile(String drawFile) {
        this.drawFile = drawFile;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonDataDraw=new JSONObject(); 
		jsonDataDraw.accumulate("drawId", this.getDrawId());
		jsonDataDraw.accumulate("ssdw", this.getSsdw());
		jsonDataDraw.accumulate("drawClass", this.getDrawClass());
		jsonDataDraw.accumulate("drawName", this.getDrawName());
		jsonDataDraw.accumulate("drawDesc", this.getDrawDesc());
		jsonDataDraw.accumulate("drawFile", this.getDrawFile());
		return jsonDataDraw;
    }}