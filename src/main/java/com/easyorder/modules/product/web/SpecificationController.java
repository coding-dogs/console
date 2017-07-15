/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.easyorder.modules.product.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.easyorder.modules.product.entity.Specification;
import com.easyorder.modules.product.service.SpecificationService;

/**
 * 商品规格Controller
 * @author qiudequan
 * @version 2017-06-09
 */
@Controller
@RequestMapping(value = "${adminPath}/productManager/specification")
public class SpecificationController extends BaseController {

	@Autowired
	private SpecificationService specificationService;

	@ModelAttribute
	public Specification get(@RequestParam(required=false) String id) {
		Specification entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = specificationService.get(id);
		}
		if (entity == null){
			entity = new Specification();
		}
		return entity;
	}

	/**
	 * 商品规格列表页面
	 */
	@RequiresPermissions("product:specification:list")
	@RequestMapping(value = {"list", ""})
	public String list(Specification specification, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Specification> page = specificationService.findPage(new Page<Specification>(request, response), specification); 
		model.addAttribute("page", page);
		return "easyorder/product/specificationList";
	}

	/**
	 * 查看，增加，编辑商品规格表单页面
	 */
	@RequiresPermissions(value={"product:specification:view","product:specification:add","product:specification:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Specification specification, Model model) {
		model.addAttribute("specification", specification);
		return "easyorder/product/specificationForm";
	}

	/**
	 * 保存商品规格
	 */
	@RequiresPermissions(value={"product:specification:add","product:specification:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Specification specification, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, specification)){
			return form(specification, model);
		}
		if(!specification.getIsNewRecord()){//编辑表单保存
			Specification t = specificationService.get(specification.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(specification, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			specificationService.save(t);//保存
		}else{//新增表单保存
			specificationService.save(specification);//保存
		}
		addMessage(redirectAttributes, "保存商品规格成功");
		return "redirect:"+Global.getAdminPath()+"/productManager/specification/?repage";
	}

	/**
	 * 删除商品规格
	 */
	@RequiresPermissions("product:specification:del")
	@RequestMapping(value = "delete")
	public String delete(Specification specification, RedirectAttributes redirectAttributes) {
		specificationService.delete(specification);
		addMessage(redirectAttributes, "删除商品规格成功");
		return "redirect:"+Global.getAdminPath()+"/productManager/specification/?repage";
	}

	/**
	 * 批量删除商品规格
	 */
	@RequiresPermissions("product:specification:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			specificationService.delete(specificationService.get(id));
		}
		addMessage(redirectAttributes, "删除商品规格成功");
		return "redirect:"+Global.getAdminPath()+"/productManager/specification/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("product:specification:export")
	@RequestMapping(value = "export", method=RequestMethod.POST)
	public String exportFile(Specification specification, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "商品规格"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			Page<Specification> page = specificationService.findPage(new Page<Specification>(request, response, -1), specification);
			new ExportExcel("商品规格", Specification.class).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出商品规格记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/productManager/specification/?repage";
	}

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("product:specification:import")
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Specification> list = ei.getDataList(Specification.class);
			for (Specification specification : list){
				try{
					specificationService.save(specification);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条商品规格记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条商品规格记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入商品规格失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/productManager/specification/?repage";
	}

	/**
	 * 下载导入商品规格数据模板
	 */
	@RequiresPermissions("product:specification:import")
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "商品规格数据导入模板.xlsx";
			List<Specification> list = Lists.newArrayList(); 
			new ExportExcel("商品规格数据", Specification.class, 1).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/productManager/specification/?repage";
	}




}