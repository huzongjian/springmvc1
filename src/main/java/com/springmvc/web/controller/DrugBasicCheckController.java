package com.springmvc.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import antlr.RecognitionException;
import antlr.TokenStreamException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sdicons.json.mapper.MapperException;
import com.springmvc.base.util.CollectionUtils;
import com.springmvc.base.util.DateUtils;
import com.springmvc.base.util.StringManager;
import com.springmvc.base.util.StringUtils;

/**
 * 
 * @ClassName: DrugBasicCheckController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author HuZongJian
 * @date 2014-7-8 下午3:19:36
 * 
 */
/*@Controller
@RequestMapping("DrugBasicCheck")
public class DrugBasicCheckController {
	@Resource
	private PrescriptionVerifier prescriptionVerifier;
	@Resource
	private BasicDrugCheckService basicDrugCheckService;
	@Resource
	private UsageService usageService;
	@Resource
	private FrequencyService frequencyService;

	*//**
	 * 
	 * @Title: SearchFrequency
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param matchInfo
	 * @param @param request
	 * @param @param response
	 * @param @throws IOException
	 * @param @throws ServiceException 设定文件
	 * @return void 返回类型
	 * @throws
	 * @author HuZongjian
	 * @date 2014-7-8 下午4:28:39
	 *//*

	@RequestMapping("searchFrequency")
	@ResponseBody
	private void SearchFrequency(String matchInfo, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServiceException {
		List<Frequency> listFrequency = frequencyService
				.findByNameAndSymbol(matchInfo);
		JsonArray array = new JsonArray();

		for (Frequency f : listFrequency) {
			JsonObject obj = new JsonObject();
			obj.addProperty("id", f.getId());
			obj.addProperty("name", f.getName());
			obj.addProperty("symbol", f.getSymbol());
			array.add(obj);
		}

		PrintWriter out = null;
		response.setContentType("applicaiton/json;charset=UTF-8");
		out = response.getWriter();
		out.print(array);

	}

	*//**
	 * 
	 * @Title: SearchDrug
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param matchInfo
	 * @param @param request
	 * @param @param response
	 * @param @throws IOException
	 * @param @throws ServiceException 设定文件
	 * @return void 返回类型
	 * @throws
	 * @author HuZongjian
	 * @date 2014-7-8 下午4:28:48
	 *//*

	@RequestMapping("searchDrug")
	@ResponseBody
	private void SearchDrug(String matchInfo, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServiceException {
		List<BasicDrugRelationship> listbasicDrugCheck = bRelationshipService
				.findByNameAndSymbol(matchInfo);
		JsonArray array = new JsonArray();

		for (BasicDrugRelationship basic : listbasicDrugCheck) {
			JsonObject obj = new JsonObject();
			obj.addProperty("id", basic.getId());
			obj.addProperty("drugname", basic.getCommodityName());
			obj.addProperty("symbol", basic.getProductName());
			if(basic.getBasicDrugCheck()!=null){
				obj.addProperty("basicid", basic.getBasicDrugCheck().getId());
				obj.addProperty("unit", basic.getBasicDrugCheck().getRuleUnit());	
			}else{
				obj.addProperty("basicid","");
				obj.addProperty("unit","");	
			}
			array.add(obj);
		}
		PrintWriter out = null;
		response.setContentType("applicaiton/json;charset=UTF-8");
		out = response.getWriter();
		out.print(array);

	}

	*//**
	 * 
	 * @Title: SearchUsage
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param matchInfo
	 * @param @param request
	 * @param @param response
	 * @param @throws IOException
	 * @param @throws ServiceException 设定文件
	 * @return void 返回类型
	 * @throws
	 * @author HuZongjian
	 * @date 2014-7-8 下午4:28:55
	 *//*
	@RequestMapping("searchUsage")
	@ResponseBody
	private void SearchUsage(String matchInfo, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServiceException {
		List<Usage> listUsage = usageService.findByNameAndSymbol(matchInfo);
		JsonArray array = new JsonArray();

		for (Usage u : listUsage) {
			JsonObject obj = new JsonObject();
			obj.addProperty("id", u.getId());
			obj.addProperty("name", u.getName());
			obj.addProperty("symbol", u.getSymbol());
			array.add(obj);
		}

		PrintWriter out = null;
		response.setContentType("applicaiton/json;charset=UTF-8");
		out = response.getWriter();
		out.print(array);

	}

	private static boolean isNum(String str) {
		try {

			new BigDecimal(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	*//**
	 * 
	 * @Title: drugCheck
	 * @param @param datas
	 * @param @param request
	 * @param @param response
	 * @param @throws IOException
	 * @param @throws TokenStreamException
	 * @param @throws RecognitionException
	 * @param @throws MapperException 设定文件
	 * @return void 返回类型
	 * @throws CheckException 
	 * @throws
	 * @author HuZongjian
	 * @date 2014-7-8 下午4:29:07
	 *//*
	@RequestMapping("drugCheck")
	@ResponseBody
	public void drugCheck(String datas, HttpServletRequest request,
			HttpServletResponse response) throws IOException,
			TokenStreamException, RecognitionException, MapperException, CheckException {

		String[] jsonArr = request.getParameterValues("datas[]");
		List<PrescribeCell> cellList = new ArrayList<PrescribeCell>();
		JSONArray jsonarray = JSONArray.fromObject(jsonArr);
		// 把jsonarray转成jsonobject
		JSONObject baseobject = (JSONObject) jsonarray.get(0);
		PatientInfo patientInfo = new PatientInfo();
		DateUtils.now();

		if (baseobject.getString("age").equals("1")) {
			// 少于16岁的儿童年龄从14岁转化成出生年月
			patientInfo.setBirthday(DateUtils.nextMonths(DateUtils.now(),
					-14 * 12));
		} else if (baseobject.getString("age").equals("2")
				|| baseobject.getString("age").equals("0")) {
			// 成人按25岁转化成出生年月
			patientInfo.setBirthday(DateUtils.nextMonths(DateUtils.now(),
					-25 * 12));
		} else if (baseobject.getString("age").equals("3")) {
			// 老人按52岁转化成出生年月
			patientInfo.setBirthday(DateUtils.nextMonths(DateUtils.now(),
					-52 * 12));

		}
		patientInfo.setCureType(CureTypeEnum.OUT_PATIENT);
		if (baseobject.getString("male").equals("1")) {
			patientInfo.setGender(GenderEnum.MALE);
		} else {
			patientInfo.setGender(GenderEnum.FEMALE);
			patientInfo.setGestateTime(0);
		}
		patientInfo.setName("某某");
		patientInfo.setSerialNo("12345");
		if (isNum(baseobject.getString("weight"))) {
			patientInfo.setWeight(Integer.parseInt(baseobject
					.getString("weight")));
		}
		if (isNum(baseobject.getString("height"))) {
			patientInfo.setHeight(Integer.parseInt(baseobject
					.getString("height")));
		}
		if (baseobject.getString("isLiver").equals("0")) {
			patientInfo.setLiver(false);
		} else if (baseobject.getString("isLiver").equals("1")) {
			patientInfo.setLiver(true);
		} else if (baseobject.getString("isLiver").equals("2")) {
			patientInfo.setCliver(true);
		}
		patientInfo.setGfr(Long.parseLong(baseobject.getString("gfr")));

		// 从第2条json组开始才是药品数据
		for (int i = 1; i < jsonarray.size(); i++) {
			JSONObject jsonobject = (JSONObject) jsonarray.get(i);
			PrescribeCell cell = new PrescribeCell();

			cell.setGroupNo(1);
			// 药品的id为空应该去数据查询药品是否存在
			String[] basicdrug = jsonobject.getString("drugname").split("_");
			
			if (isNum(basicdrug[0])) {
				BasicDrugCheck a =this.basicDrugCheckService.get(Long
						.parseLong(basicdrug[0]));
				cell.setBasicDrugCheck(a);
				if(basicdrug.length>1){
					cell.setDrugName(basicdrug[1]);
				}else{
					cell.setDrugName(basicdrug[0]);	
				}
				

			} else {
				if (basicdrug.equals("")) {
					cell.setBasicDrugCheck(null);
				} else {
					BasicDrugCheck b = this.basicDrugCheckService
							.findByName(basicdrug[0]);
					if (b != null) {
						cell.setBasicDrugCheck(b);
					} else {
						cell.setBasicDrugCheck(null);
					}
				}
				cell.setDrugName(basicdrug[0]);
			}
			cell.setAdviceType(AdviceTypeEnum.TEMP_ADVICE);
			// 用法如果为空应该去数据查询用法是否存在
			String usage = jsonobject.getString("usage");
			if (isNum(usage)) {
				Usage u = this.usageService.get(Long.parseLong(usage));
				cell.setUsage(u);
			} else {
				if (usage.equals("")) {
					cell.setUsage(null);
				} else {
					Usage u = this.usageService.findByName(usage);
					if (u != null) {
						cell.setUsage(u);
					} else {
						cell.setUsage(null);
					}
				}
			}
			cell.setDaynum(null); // 天数
			String frequery = jsonobject.getString("frequery");
			// 频次
			if (isNum(frequery)) {
				Frequency f = this.frequencyService.get(Long.parseLong(frequery));
				cell.setFrequency(f);
			} else {
				if (frequery.equals("")) {
					cell.setFrequency(null);
				} else {
					Frequency f = this.frequencyService.findByName(frequery);
					if (f != null) {
						cell.setFrequency(f);
					} else {
						cell.setFrequency(null);
					}
				}
			}
			// 如果用量与单位为手工输入则把单位分出来
			String dosage = jsonobject.getString("dosage");
			if (!StringUtils.isNullOrBlank(dosage)) {
				if (isNum(dosage)) {
					cell.setDosage(Double.parseDouble(dosage));
				} else {
					String unit = splitNotNumber(dosage);
					String d = dosage.replace(unit, "");
					if(isNum(d)){
						cell.setDosage(Double.parseDouble(d));
						cell.setUnit(unit);	
					}else{
						cell.setDosage(0);
					}
					
				}
			} else {
				cell.setDosage(0);
			}
			// 添加
			cellList.add(cell);
		}

		
		 * patientInfo.setBirthday(DateUtils.toDate("1973-06-13",
		 * DateUtils.YEAR_MONTH_DAY));
		 * patientInfo.setCureType(CureTypeEnum.OUT_PATIENT);
		 * patientInfo.setGender(GenderEnum.FEMALE);
		 * patientInfo.setGestateTime(3L); patientInfo.setName("������");
		 * patientInfo.setSerialNo("12345"); patientInfo.setWeight(56);
		 * patientInfo.setHeight(167);
		 

		PrescribeBasicInfo pBasicInfo = new PrescribeBasicInfo();

		pBasicInfo.setCreateTime(DateUtils.now());
		pBasicInfo.setDiseaseCode("A01.101");
		pBasicInfo.setDiseaseName("流行性感冒");
		pBasicInfo.setDivisionCode("dept-2046");
		pBasicInfo.setDivisionName("外科");
		pBasicInfo.setMedicalerName("某医生");
		pBasicInfo.setMedicalerNo("m-3721");
		pBasicInfo.setMedicalerTitle(TitleEnum.MainMedicaler);
		pBasicInfo.setPrescribeNo("1314");
		pBasicInfo.setTotalAmount(BigDecimal.valueOf(999.98));
		JsonArray array = new JsonArray();
		VerifyReport v = prescriptionVerifier.verify(patientInfo, pBasicInfo,
				cellList, "");
		if (v != null) {
			if (CollectionUtils.isNotEmpty(v.getOriginReport())) {
				for (ReportDetail report : v.getOriginReport()) {
					JsonObject obj = new JsonObject();
					obj.addProperty("type", report.getRuleType().getText()+"：");
					obj.addProperty("grade", report.getGrade().getText());
					obj.addProperty("result", report.getRemark());
					array.add(obj);
				}
				PrintWriter out = null;
				response.setContentType("applicaiton/json;charset=UTF-8");
				out = response.getWriter();
				out.print(array);
			} else {
				JsonObject obj = new JsonObject();
				obj.addProperty("type", "");
				obj.addProperty("grade", "提示");
				obj.addProperty("result", "未发现配伍禁忌和重复用药!");
				array.add(obj);
				PrintWriter out = null;
				response.setContentType("applicaiton/json;charset=UTF-8");
				out = response.getWriter();
				out.print(array);
			}
		}
		
		 * Gson gson = new Gson(); List<basicDrug> list = gson.fromJson(datas,
		 * new TypeToken<List<basicDrug>>() {}.getType());
		 * System.err.println(datas); for (basicDrug b : list) {
		 * System.err.println(b.drugname+
		 * "============================================================================"
		 * ); }
		 
	}

	// 取字条串中的数据，返回第一个数字
	public static String getNumbers(String content) {
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			return matcher.group(0);
		}
		return "";
	}

	// 取字符串最后面的非数字字符串
	public static String splitNotNumber(String content) {
		Pattern pattern = Pattern.compile("\\D+");
		StringBuilder s = new StringBuilder(content);
		Matcher matcher = pattern.matcher(s.reverse());
		while (matcher.find()) {
			StringBuilder st = new StringBuilder(matcher.group(0));
			return st.reverse().toString();
		}
		return "";
	}

	public static void main(String[] args) {
		System.err.println(splitNotNumber("12.3slb"));
		System.err.println(isNum("12.334"));
	}
	
	@Resource
	private BasicDrugRelationshipService bRelationshipService;
	@RequestMapping("updatedrugrelationship")
	@ResponseBody
	public void updatedrugrelationship(String datas, HttpServletRequest request,
			HttpServletResponse response) throws IOException,
			TokenStreamException, RecognitionException, MapperException, CheckException {
		List<BasicDrugRelationship> list = bRelationshipService.findAll();
		 for(int i = 0 ; i< list.size();i++){   
			
			 BasicDrugRelationship b =new BasicDrugRelationship();
			 BasicDrugCheck s =new BasicDrugCheck();
			 b= list.get(i);
			 s =basicDrugCheckService.findByName(b.getProductName());
			 b.setBasicDrugCheck(s);
			 b.setCommodityNameSymbol(StringManager.getFirstPinYin(b.getCommodityName()));
			 b.setProductNameSymbol(StringManager.getFirstPinYin(b.getProductName()));
			 bRelationshipService.update(b);
			 
        } 
		 JsonArray array = new JsonArray();
		 JsonObject obj = new JsonObject();
			obj.addProperty("type", "");
			obj.addProperty("grade", "提示");
			obj.addProperty("result", "更新完成!");
			array.add(obj);
			PrintWriter out = null;
			response.setContentType("applicaiton/json;charset=UTF-8");
			out = response.getWriter();
			out.print(array);
		
		
		
	}
	

}
*/