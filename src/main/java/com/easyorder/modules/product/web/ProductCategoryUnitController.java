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
import com.easyorder.modules.product.entity.ProductCategoryUnit;
import com.easyorder.modules.product.service.ProductCategoryUnitService;

/**
 * 商品类目单位关联Controller
 * @author qiudequan
 * @version 2017-06-09
 */
@Controller
@RequestMapping(value = "${adminPath}/productManager/productCategoryUnit")
public class ProductCategoryUnitController extends BaseController {

	@Autowired
	private ProductCategoryUnitService productCategoryUnitService;

	@ModelAttribute
	public ProductCategoryUnit get(@RequestParam(required=false) String id) {
		ProductCategoryUnit entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = productCategoryUnitService.get(id);
		}
		if (entity == null){
			entity = new ProductCategoryUnit();
		}
		return entity;
	}

	/**
	 * 商品类目单位关联列表页面
	 */
	@RequiresPermissions("product:productCategoryUnit:list")
	@RequestMapping(value = {"list", ""})
	public String list(ProductCategoryUnit productCategoryUnit, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProductCategoryUnit> page = productCategoryUnitService.findPage(new Page<ProductCategoryUnit>(request, response), productCategoryUnit); 
		model.addAttribute("page", page);
		return "easyorder/product/productCategoryUnitList";
	}

	/**
	 * 查看，增加，编辑商品类目单位关联表单页面
	 */
	@RequiresPermissions(value={"product:productCategoryUnit:view","product:productCategoryUnit:add","product:productCategoryUnit:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ProductCategoryUnit productCategoryUnit, Model model) {
		model.addAttribute("productCategoryUnit", productCategoryUnit);
		return "easyorder/product/productCategoryUnitForm";
	}

	/**
	 * 保存商品类目单位关联
	 */
	@RequiresPermissions(value={"product:productCategoryUnit:add","product:productCategoryUnit:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ProductCategoryUnit productCategoryUnit, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, productCategoryUnit)){
			return form(productCategoryUnit, model);
		}
		if(!productCategoryUnit.getIsNewRecord()){//编辑表单保存
			ProductCategoryUnit t = productCategoryUnitService.get(productCategoryUnit.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(productCategoryUnit, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			productCategoryUnitService.save(t);//保存
		}else{//新增表单保存
			productCategoryUnitService.save(productCategoryUnit);//保存
		}
		addMessage(redirectAttributes, "保存商品类目单位关联成功");
		return "redirect:"+Global.getAdminPath()+"/productManager/productCategoryUnit/?repage";
	}

	/**
	 * 删除商品类目单位关联
	 */
	@RequiresPermissions("product:productCategoryUnit:del")
	@RequestMapping(value = "delete")
	public String delete(ProductCategoryUnit productCategoryUnit, RedirectAttributes redirectAttributes) {
		productCategoryUnitService.delete(productCategoryUnit);
		addMessage(redirectAttributes, "删除商品类目单位关联成功");
		return "redirect:"+Global.getAdminPath()+"/productManager/productCategoryUnit/?repage";
	}

	/**
	 * 批量删除商品类目单位关联
	 */
	@RequiresPermissions("product:productCategoryUnit:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			productCategoryUnitService.delete(productCategoryUnitService.get(id));
		}
		addMessage(redirectAttributes, "删除商品类目单位关联成功");
		return "redirect:"+Global.getAdminPath()+"/productManager/productCategoryUnit/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("product:productCategoryUnit:export")
	@RequestMapping(value = "export", method=RequestMethod.POST)
	public String exportFile(ProductCategoryUnit productCategoryUnit, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "商品类目单位关联"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			Page<ProductCategoryUnit> page = productCategoryUnitService.findPage(new Page<ProductCategoryUnit>(request, response, -1), productCategoryUnit);
			new ExportExcel("商品类目单位关联", ProductCategoryUnit.class).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出商品类目单位关联记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/productManager/productCategoryUnit/?repage";
	}

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("product:productCategoryUnit:import")
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ProductCategoryUnit> list = ei.getDataList(ProductCategoryUnit.class);
			for (ProductCategoryUnit productCategoryUnit : list){
				try{
					productCategoryUnitService.save(productCategoryUnit);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条商品类目单位关联记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条商品类目单位关联记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入商品类目单位关联失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/productManager/productCategoryUnit/?repage";
	}

	/**
	 * 下载导入商品类目单位关联数据模板
	 */
	@RequiresPermissions("product:productCategoryUnit:import")
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "商品类目单位关联数据导入模板.xlsx";
			List<ProductCategoryUnit> list = Lists.newArrayList(); 
			new ExportExcel("商品类目单位关联数据", ProductCategoryUnit.class, 1).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/productManager/productCategoryUnit/?repage";
	}




}