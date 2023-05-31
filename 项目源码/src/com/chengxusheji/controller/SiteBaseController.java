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
import com.chengxusheji.service.SiteBaseService;
import com.chengxusheji.po.SiteBase;

//SiteBase管理控制层
@Controller
@RequestMapping("/SiteBase")
public class SiteBaseController extends BaseController {

    /*业务层对象*/
    @Resource SiteBaseService siteBaseService;

	@InitBinder("siteBase")
	public void initBinderSiteBase(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("siteBase.");
	}
	/*跳转到添加SiteBase视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new SiteBase());
		return "SiteBase_add";
	}

	/*客户端ajax方式提交添加站点基本信息信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated SiteBase siteBase, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        siteBaseService.addSiteBase(siteBase);
        message = "站点基本信息添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询站点基本信息信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(String ssdw,String gsd,String zddh,String zdmc,String zdlb,String txfs,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (ssdw == null) ssdw = "";
		if (gsd == null) gsd = "";
		if (zddh == null) zddh = "";
		if (zdmc == null) zdmc = "";
		if (zdlb == null) zdlb = "";
		if (txfs == null) txfs = "";
		if(rows != 0)siteBaseService.setRows(rows);
		List<SiteBase> siteBaseList = siteBaseService.querySiteBase(ssdw, gsd, zddh, zdmc, zdlb, txfs, page);
	    /*计算总的页数和总的记录数*/
	    siteBaseService.queryTotalPageAndRecordNumber(ssdw, gsd, zddh, zdmc, zdlb, txfs);
	    /*获取到总的页码数目*/
	    int totalPage = siteBaseService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = siteBaseService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(SiteBase siteBase:siteBaseList) {
			JSONObject jsonSiteBase = siteBase.getJsonObject();
			jsonArray.put(jsonSiteBase);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询站点基本信息信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<SiteBase> siteBaseList = siteBaseService.queryAllSiteBase();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(SiteBase siteBase:siteBaseList) {
			JSONObject jsonSiteBase = new JSONObject();
			jsonSiteBase.accumulate("siteBaseId", siteBase.getSiteBaseId());
			jsonSiteBase.accumulate("zdmc", siteBase.getZdmc());
			jsonArray.put(jsonSiteBase);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询站点基本信息信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(String ssdw,String gsd,String zddh,String zdmc,String zdlb,String txfs,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (ssdw == null) ssdw = "";
		if (gsd == null) gsd = "";
		if (zddh == null) zddh = "";
		if (zdmc == null) zdmc = "";
		if (zdlb == null) zdlb = "";
		if (txfs == null) txfs = "";
		List<SiteBase> siteBaseList = siteBaseService.querySiteBase(ssdw, gsd, zddh, zdmc, zdlb, txfs, currentPage);
	    /*计算总的页数和总的记录数*/
	    siteBaseService.queryTotalPageAndRecordNumber(ssdw, gsd, zddh, zdmc, zdlb, txfs);
	    /*获取到总的页码数目*/
	    int totalPage = siteBaseService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = siteBaseService.getRecordNumber();
	    request.setAttribute("siteBaseList",  siteBaseList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("ssdw", ssdw);
	    request.setAttribute("gsd", gsd);
	    request.setAttribute("zddh", zddh);
	    request.setAttribute("zdmc", zdmc);
	    request.setAttribute("zdlb", zdlb);
	    request.setAttribute("txfs", txfs);
		return "SiteBase/siteBase_frontquery_result"; 
	}

     /*前台查询SiteBase信息*/
	@RequestMapping(value="/{siteBaseId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer siteBaseId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键siteBaseId获取SiteBase对象*/
        SiteBase siteBase = siteBaseService.getSiteBase(siteBaseId);

        request.setAttribute("siteBase",  siteBase);
        return "SiteBase/siteBase_frontshow";
	}

	/*ajax方式显示站点基本信息修改jsp视图页*/
	@RequestMapping(value="/{siteBaseId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer siteBaseId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键siteBaseId获取SiteBase对象*/
        SiteBase siteBase = siteBaseService.getSiteBase(siteBaseId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonSiteBase = siteBase.getJsonObject();
		out.println(jsonSiteBase.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新站点基本信息信息*/
	@RequestMapping(value = "/{siteBaseId}/update", method = RequestMethod.POST)
	public void update(@Validated SiteBase siteBase, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			siteBaseService.updateSiteBase(siteBase);
			message = "站点基本信息更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "站点基本信息更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除站点基本信息信息*/
	@RequestMapping(value="/{siteBaseId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer siteBaseId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  siteBaseService.deleteSiteBase(siteBaseId);
	            request.setAttribute("message", "站点基本信息删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "站点基本信息删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条站点基本信息记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String siteBaseIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = siteBaseService.deleteSiteBases(siteBaseIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出站点基本信息信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(String ssdw,String gsd,String zddh,String zdmc,String zdlb,String txfs, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(ssdw == null) ssdw = "";
        if(gsd == null) gsd = "";
        if(zddh == null) zddh = "";
        if(zdmc == null) zdmc = "";
        if(zdlb == null) zdlb = "";
        if(txfs == null) txfs = "";
        List<SiteBase> siteBaseList = siteBaseService.querySiteBase(ssdw,gsd,zddh,zdmc,zdlb,txfs);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "SiteBase信息记录"; 
        String[] headers = { "所属单位","归属地","站点代号","站点名称","站点类别","通讯方式","安装地点","经度","纬度"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<siteBaseList.size();i++) {
        	SiteBase siteBase = siteBaseList.get(i); 
        	dataset.add(new String[]{siteBase.getSsdw(),siteBase.getGsd(),siteBase.getZddh(),siteBase.getZdmc(),siteBase.getZdlb(),siteBase.getTxfs(),siteBase.getZzdd(),siteBase.getLongitude() + "",siteBase.getLatitude() + ""});
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
			response.setHeader("Content-disposition","attachment; filename="+"SiteBase.xls");//filename是下载的xls的名，建议最好用英文 
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
