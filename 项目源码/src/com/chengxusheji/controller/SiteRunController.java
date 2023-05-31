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
import com.chengxusheji.service.SiteRunService;
import com.chengxusheji.po.SiteRun;

//SiteRun管理控制层
@Controller
@RequestMapping("/SiteRun")
public class SiteRunController extends BaseController {

    /*业务层对象*/
    @Resource SiteRunService siteRunService;

	@InitBinder("siteRun")
	public void initBinderSiteRun(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("siteRun.");
	}
	/*跳转到添加SiteRun视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new SiteRun());
		return "SiteRun_add";
	}

	/*客户端ajax方式提交添加运行情况信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated SiteRun siteRun, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        siteRunService.addSiteRun(siteRun);
        message = "运行情况添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询运行情况信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(String ssdw,String zddh,String zdlb,String zdmc,String gzlx,String gzsj,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (ssdw == null) ssdw = "";
		if (zddh == null) zddh = "";
		if (zdlb == null) zdlb = "";
		if (zdmc == null) zdmc = "";
		if (gzlx == null) gzlx = "";
		if (gzsj == null) gzsj = "";
		if(rows != 0)siteRunService.setRows(rows);
		List<SiteRun> siteRunList = siteRunService.querySiteRun(ssdw, zddh, zdlb, zdmc, gzlx, gzsj, page);
	    /*计算总的页数和总的记录数*/
	    siteRunService.queryTotalPageAndRecordNumber(ssdw, zddh, zdlb, zdmc, gzlx, gzsj);
	    /*获取到总的页码数目*/
	    int totalPage = siteRunService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = siteRunService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(SiteRun siteRun:siteRunList) {
			JSONObject jsonSiteRun = siteRun.getJsonObject();
			jsonArray.put(jsonSiteRun);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询运行情况信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<SiteRun> siteRunList = siteRunService.queryAllSiteRun();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(SiteRun siteRun:siteRunList) {
			JSONObject jsonSiteRun = new JSONObject();
			jsonSiteRun.accumulate("siteRunId", siteRun.getSiteRunId());
			jsonArray.put(jsonSiteRun);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询运行情况信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(String ssdw,String zddh,String zdlb,String zdmc,String gzlx,String gzsj,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (ssdw == null) ssdw = "";
		if (zddh == null) zddh = "";
		if (zdlb == null) zdlb = "";
		if (zdmc == null) zdmc = "";
		if (gzlx == null) gzlx = "";
		if (gzsj == null) gzsj = "";
		List<SiteRun> siteRunList = siteRunService.querySiteRun(ssdw, zddh, zdlb, zdmc, gzlx, gzsj, currentPage);
	    /*计算总的页数和总的记录数*/
	    siteRunService.queryTotalPageAndRecordNumber(ssdw, zddh, zdlb, zdmc, gzlx, gzsj);
	    /*获取到总的页码数目*/
	    int totalPage = siteRunService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = siteRunService.getRecordNumber();
	    request.setAttribute("siteRunList",  siteRunList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("ssdw", ssdw);
	    request.setAttribute("zddh", zddh);
	    request.setAttribute("zdlb", zdlb);
	    request.setAttribute("zdmc", zdmc);
	    request.setAttribute("gzlx", gzlx);
	    request.setAttribute("gzsj", gzsj);
		return "SiteRun/siteRun_frontquery_result"; 
	}

     /*前台查询SiteRun信息*/
	@RequestMapping(value="/{siteRunId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer siteRunId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键siteRunId获取SiteRun对象*/
        SiteRun siteRun = siteRunService.getSiteRun(siteRunId);

        request.setAttribute("siteRun",  siteRun);
        return "SiteRun/siteRun_frontshow";
	}

	/*ajax方式显示运行情况修改jsp视图页*/
	@RequestMapping(value="/{siteRunId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer siteRunId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键siteRunId获取SiteRun对象*/
        SiteRun siteRun = siteRunService.getSiteRun(siteRunId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonSiteRun = siteRun.getJsonObject();
		out.println(jsonSiteRun.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新运行情况信息*/
	@RequestMapping(value = "/{siteRunId}/update", method = RequestMethod.POST)
	public void update(@Validated SiteRun siteRun, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			siteRunService.updateSiteRun(siteRun);
			message = "运行情况更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "运行情况更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除运行情况信息*/
	@RequestMapping(value="/{siteRunId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer siteRunId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  siteRunService.deleteSiteRun(siteRunId);
	            request.setAttribute("message", "运行情况删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "运行情况删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条运行情况记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String siteRunIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = siteRunService.deleteSiteRuns(siteRunIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出运行情况信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(String ssdw,String zddh,String zdlb,String zdmc,String gzlx,String gzsj, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(ssdw == null) ssdw = "";
        if(zddh == null) zddh = "";
        if(zdlb == null) zdlb = "";
        if(zdmc == null) zdmc = "";
        if(gzlx == null) gzlx = "";
        if(gzsj == null) gzsj = "";
        List<SiteRun> siteRunList = siteRunService.querySiteRun(ssdw,zddh,zdlb,zdmc,gzlx,gzsj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "SiteRun信息记录"; 
        String[] headers = { "所属单位","站点代号","站点类别","站点名称","故障类型","故障时间","故障时长","恢复情况"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<siteRunList.size();i++) {
        	SiteRun siteRun = siteRunList.get(i); 
        	dataset.add(new String[]{siteRun.getSsdw(),siteRun.getZddh(),siteRun.getZdlb(),siteRun.getZdmc(),siteRun.getGzlx(),siteRun.getGzsj(),siteRun.getGzsc(),siteRun.getHfqk()});
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
			response.setHeader("Content-disposition","attachment; filename="+"SiteRun.xls");//filename是下载的xls的名，建议最好用英文 
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
