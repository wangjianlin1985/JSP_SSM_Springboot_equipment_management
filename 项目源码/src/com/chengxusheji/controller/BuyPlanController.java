package com.chengxusheji.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.chengxusheji.utils.ExportExcelUtil;
import com.chengxusheji.utils.UserException;
import com.chengxusheji.service.BuyPlanService;
import com.chengxusheji.po.BuyPlan;
import com.chengxusheji.service.DeviceTypeService;
import com.chengxusheji.po.DeviceType;

//BuyPlan管理控制层
@Controller
@RequestMapping("/BuyPlan")
public class BuyPlanController extends BaseController {

    /*业务层对象*/
    @Resource BuyPlanService buyPlanService;

    @Resource DeviceTypeService deviceTypeService;
	@InitBinder("deviceTypeObj")
	public void initBinderdeviceTypeObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("deviceTypeObj.");
	}
	@InitBinder("buyPlan")
	public void initBinderBuyPlan(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("buyPlan.");
	}
	/*跳转到添加BuyPlan视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new BuyPlan());
		/*查询所有的DeviceType信息*/
		List<DeviceType> deviceTypeList = deviceTypeService.queryAllDeviceType();
		request.setAttribute("deviceTypeList", deviceTypeList);
		return "BuyPlan_add";
	}

	/*客户端ajax方式提交添加购置计划信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated BuyPlan buyPlan, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        buyPlanService.addBuyPlan(buyPlan);
        message = "购置计划添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询购置计划信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(@ModelAttribute("deviceTypeObj") DeviceType deviceTypeObj,String deviceName,String sydw,String jhsj,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (deviceName == null) deviceName = "";
		if (sydw == null) sydw = "";
		if (jhsj == null) jhsj = "";
		if(rows != 0)buyPlanService.setRows(rows);
		List<BuyPlan> buyPlanList = buyPlanService.queryBuyPlan(deviceTypeObj, deviceName, sydw, jhsj, page);
	    /*计算总的页数和总的记录数*/
	    buyPlanService.queryTotalPageAndRecordNumber(deviceTypeObj, deviceName, sydw, jhsj);
	    /*获取到总的页码数目*/
	    int totalPage = buyPlanService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = buyPlanService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(BuyPlan buyPlan:buyPlanList) {
			JSONObject jsonBuyPlan = buyPlan.getJsonObject();
			jsonArray.put(jsonBuyPlan);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询购置计划信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<BuyPlan> buyPlanList = buyPlanService.queryAllBuyPlan();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(BuyPlan buyPlan:buyPlanList) {
			JSONObject jsonBuyPlan = new JSONObject();
			jsonBuyPlan.accumulate("planId", buyPlan.getPlanId());
			jsonBuyPlan.accumulate("deviceName", buyPlan.getDeviceName());
			jsonArray.put(jsonBuyPlan);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询购置计划信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(@ModelAttribute("deviceTypeObj") DeviceType deviceTypeObj,String deviceName,String sydw,String jhsj,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (deviceName == null) deviceName = "";
		if (sydw == null) sydw = "";
		if (jhsj == null) jhsj = "";
		List<BuyPlan> buyPlanList = buyPlanService.queryBuyPlan(deviceTypeObj, deviceName, sydw, jhsj, currentPage);
	    /*计算总的页数和总的记录数*/
	    buyPlanService.queryTotalPageAndRecordNumber(deviceTypeObj, deviceName, sydw, jhsj);
	    /*获取到总的页码数目*/
	    int totalPage = buyPlanService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = buyPlanService.getRecordNumber();
	    request.setAttribute("buyPlanList",  buyPlanList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("deviceTypeObj", deviceTypeObj);
	    request.setAttribute("deviceName", deviceName);
	    request.setAttribute("sydw", sydw);
	    request.setAttribute("jhsj", jhsj);
	    List<DeviceType> deviceTypeList = deviceTypeService.queryAllDeviceType();
	    request.setAttribute("deviceTypeList", deviceTypeList);
		return "BuyPlan/buyPlan_frontquery_result"; 
	}

     /*前台查询BuyPlan信息*/
	@RequestMapping(value="/{planId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer planId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键planId获取BuyPlan对象*/
        BuyPlan buyPlan = buyPlanService.getBuyPlan(planId);

        List<DeviceType> deviceTypeList = deviceTypeService.queryAllDeviceType();
        request.setAttribute("deviceTypeList", deviceTypeList);
        request.setAttribute("buyPlan",  buyPlan);
        return "BuyPlan/buyPlan_frontshow";
	}

	/*ajax方式显示购置计划修改jsp视图页*/
	@RequestMapping(value="/{planId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer planId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键planId获取BuyPlan对象*/
        BuyPlan buyPlan = buyPlanService.getBuyPlan(planId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonBuyPlan = buyPlan.getJsonObject();
		out.println(jsonBuyPlan.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新购置计划信息*/
	@RequestMapping(value = "/{planId}/update", method = RequestMethod.POST)
	public void update(@Validated BuyPlan buyPlan, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			buyPlanService.updateBuyPlan(buyPlan);
			message = "购置计划更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "购置计划更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除购置计划信息*/
	@RequestMapping(value="/{planId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer planId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  buyPlanService.deleteBuyPlan(planId);
	            request.setAttribute("message", "购置计划删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "购置计划删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条购置计划记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String planIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = buyPlanService.deleteBuyPlans(planIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出购置计划信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(@ModelAttribute("deviceTypeObj") DeviceType deviceTypeObj,String deviceName,String sydw,String jhsj, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(deviceName == null) deviceName = "";
        if(sydw == null) sydw = "";
        if(jhsj == null) jhsj = "";
        List<BuyPlan> buyPlanList = buyPlanService.queryBuyPlan(deviceTypeObj,deviceName,sydw,jhsj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "BuyPlan信息记录"; 
        String[] headers = { "设备类型","设备名称","数量","金额","规格型号","用途","使用单位","计划时间","是否购置"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<buyPlanList.size();i++) {
        	BuyPlan buyPlan = buyPlanList.get(i); 
        	dataset.add(new String[]{buyPlan.getDeviceTypeObj().getTypeName(),buyPlan.getDeviceName(),buyPlan.getBuyNum() + "",buyPlan.getBuyMoney() + "",buyPlan.getGuige(),buyPlan.getYongtu(),buyPlan.getSydw(),buyPlan.getJhsj(),buyPlan.getSfgz()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		OutputStream out = null;//创建一个输出流对象 
		try { 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"BuyPlan.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,_title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
    }
}
