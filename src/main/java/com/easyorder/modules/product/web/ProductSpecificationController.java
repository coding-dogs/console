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
import com.easyorder.modules.product.entity.ProductSpecification;
import com.easyorder.modules.product.service.ProductSpecificationService;

/**
 * 商品规格Controller
 * @author qiudequan
 * @version 2017-06-09
 */
@Controller
@RequestMapping(value = "${adminPath}/productManager/productSpecification")
public class ProductSpecificationController extends BaseController {

	@Autowired
	private ProductSpecificationService productSpecificationService;

	@ModelAttribute
	public ProductSpecification get(@RequestParam(required=false) String id) {
		ProductSpecification entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = productSpecificationService.get(id);
		}
		if (entity == null){
			entity = new ProductSpecification();
		}
		return entity;
	}

	/**
	 * 商品规格列表页面
	 */
	@RequiresPermissions("product:productSpecification:list")
	@RequestMapping(value = {"list", ""})
	public String list(ProductSpecification productSpecification, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProductSpecification> page = productSpecificationService.findPage(new Page<ProductSpecification>(request, response), productSpecification); 
		model.addAttribute("page", page);
		return "easyorder/product/productSpecificationList";
	}

	/**
	 * 查看，增加，编辑商品规格表单页面
	 */
	@RequiresPermissions(value={"product:productSpecification:view","product:productSpecification:add","product:productSpecification:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ProductSpecification productSpecification, Model model) {
		model.addAttribute("productSpecification", productSpecification);
		return "easyorder/product/productSpecificationForm";
	}

	/**
	 * 保存商品规格
	 */
	@RequiresPermissions(value={"product:productSpecification:add","product:productSpecification:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ProductSpecification productSpecification, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, productSpecification)){
			return form(productSpecification, model);
		}
		if(!productSpecification.getIsNewRecord()){//编辑表单保存
			ProductSpecification t = productSpecificationService.get(productSpecification.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(productSpecification, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			productSpecificationService.save(t);//保存
		}else{//新增表单保存
			productSpecificationService.save(productSpecification);//保存
		}
		addMessage(redirectAttributes, "保存商品规格成功");
		return "redirect:"+Global.getAdminPath()+"/productManager/productSpecification/?repage";
	}

	/**
	 * 删除商品规格
	 */
	@RequiresPermissions("product:productSpecification:del")
	@RequestMapping(value = "delete")
	public String delete(ProductSpecification productSpecification, RedirectAttributes redirectAttributes) {
		productSpecificationService.delete(productSpecification);
		addMessage(redirectAttributes, "删除商品规格成功");
		return "redirect:"+Global.getAdminPath()+"/productManager/productSpecification/?repage";
	}

	/**
	 * 批量删除商品规格
	 */
	@RequiresPermissions("product:productSpecification:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			productSpecificationService.delete(productSpecificationService.get(id));
		}
		addMessage(redirectAttributes, "删除商品规格成功");
		return "redirect:"+Global.getAdminPath()+"/productManager/productSpecification/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("product:productSpecification:export")
	@RequestMapping(value = "export", method=RequestMethod.POST)
	public String exportFile(ProductSpecification productSpecification, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "商品规格"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			Page<ProductSpecification> page = productSpecificationService.findPage(new Page<ProductSpecification>(request, response, -1), productSpecification);
			new ExportExcel("商品规格", ProductSpecification.class).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出商品规格记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/productManager/productSpecification/?repage";
	}

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("product:productSpecification:import")
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ProductSpecification> list = ei.getDataList(ProductSpecification.class);
			for (ProductSpecification productSpecification : list){
				try{
					productSpecificationService.save(productSpecification);
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
		return "redirect:"+Global.getAdminPath()+"/productManager/productSpecification/?repage";
	}

	/**
	 * 下载导入商品规格数据模板
	 */
	@RequiresPermissions("product:productSpecification:import")
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "商品规格数据导入模板.xlsx";
			List<ProductSpecification> list = Lists.newArrayList(); 
			new ExportExcel("商品规格数据", ProductSpecification.class, 1).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/productManager/productSpecification/?repage";
	}




}