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
import com.easyorder.modules.product.entity.ProductCustomerGroupPrice;
import com.easyorder.modules.product.service.ProductCustomerGroupPriceService;

/**
 * 商品客户组指定价Controller
 * @author qiudequan
 * @version 2017-06-09
 */
@Controller
@RequestMapping(value = "${adminPath}/productManager/productCustomerGroupPrice")
public class ProductCustomerGroupPriceController extends BaseController {

	@Autowired
	private ProductCustomerGroupPriceService productCustomerGroupPriceService;

	@ModelAttribute
	public ProductCustomerGroupPrice get(@RequestParam(required=false) String id) {
		ProductCustomerGroupPrice entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = productCustomerGroupPriceService.get(id);
		}
		if (entity == null){
			entity = new ProductCustomerGroupPrice();
		}
		return entity;
	}

	/**
	 * 商品客户组指定价列表页面
	 */
	@RequiresPermissions("product:productCustomerGroupPrice:list")
	@RequestMapping(value = {"list", ""})
	public String list(ProductCustomerGroupPrice productCustomerGroupPrice, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProductCustomerGroupPrice> page = productCustomerGroupPriceService.findPage(new Page<ProductCustomerGroupPrice>(request, response), productCustomerGroupPrice); 
		model.addAttribute("page", page);
		return "easyorder/product/productCustomerGroupPriceList";
	}

	/**
	 * 查看，增加，编辑商品客户组指定价表单页面
	 */
	@RequiresPermissions(value={"product:productCustomerGroupPrice:view","product:productCustomerGroupPrice:add","product:productCustomerGroupPrice:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ProductCustomerGroupPrice productCustomerGroupPrice, Model model) {
		model.addAttribute("productCustomerGroupPrice", productCustomerGroupPrice);
		return "easyorder/product/productCustomerGroupPriceForm";
	}

	/**
	 * 保存商品客户组指定价
	 */
	@RequiresPermissions(value={"product:productCustomerGroupPrice:add","product:productCustomerGroupPrice:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ProductCustomerGroupPrice productCustomerGroupPrice, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, productCustomerGroupPrice)){
			return form(productCustomerGroupPrice, model);
		}
		if(!productCustomerGroupPrice.getIsNewRecord()){//编辑表单保存
			ProductCustomerGroupPrice t = productCustomerGroupPriceService.get(productCustomerGroupPrice.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(productCustomerGroupPrice, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			productCustomerGroupPriceService.save(t);//保存
		}else{//新增表单保存
			productCustomerGroupPriceService.save(productCustomerGroupPrice);//保存
		}
		addMessage(redirectAttributes, "保存商品客户组指定价成功");
		return "redirect:"+Global.getAdminPath()+"/productManager/productCustomerGroupPrice/?repage";
	}

	/**
	 * 删除商品客户组指定价
	 */
	@RequiresPermissions("product:productCustomerGroupPrice:del")
	@RequestMapping(value = "delete")
	public String delete(ProductCustomerGroupPrice productCustomerGroupPrice, RedirectAttributes redirectAttributes) {
		productCustomerGroupPriceService.delete(productCustomerGroupPrice);
		addMessage(redirectAttributes, "删除商品客户组指定价成功");
		return "redirect:"+Global.getAdminPath()+"/productManager/productCustomerGroupPrice/?repage";
	}

	/**
	 * 批量删除商品客户组指定价
	 */
	@RequiresPermissions("product:productCustomerGroupPrice:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			productCustomerGroupPriceService.delete(productCustomerGroupPriceService.get(id));
		}
		addMessage(redirectAttributes, "删除商品客户组指定价成功");
		return "redirect:"+Global.getAdminPath()+"/productManager/productCustomerGroupPrice/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("product:productCustomerGroupPrice:export")
	@RequestMapping(value = "export", method=RequestMethod.POST)
	public String exportFile(ProductCustomerGroupPrice productCustomerGroupPrice, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "商品客户组指定价"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			Page<ProductCustomerGroupPrice> page = productCustomerGroupPriceService.findPage(new Page<ProductCustomerGroupPrice>(request, response, -1), productCustomerGroupPrice);
			new ExportExcel("商品客户组指定价", ProductCustomerGroupPrice.class).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出商品客户组指定价记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/productManager/productCustomerGroupPrice/?repage";
	}

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("product:productCustomerGroupPrice:import")
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ProductCustomerGroupPrice> list = ei.getDataList(ProductCustomerGroupPrice.class);
			for (ProductCustomerGroupPrice productCustomerGroupPrice : list){
				try{
					productCustomerGroupPriceService.save(productCustomerGroupPrice);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条商品客户组指定价记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条商品客户组指定价记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入商品客户组指定价失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/productManager/productCustomerGroupPrice/?repage";
	}

	/**
	 * 下载导入商品客户组指定价数据模板
	 */
	@RequiresPermissions("product:productCustomerGroupPrice:import")
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "商品客户组指定价数据导入模板.xlsx";
			List<ProductCustomerGroupPrice> list = Lists.newArrayList(); 
			new ExportExcel("商品客户组指定价数据", ProductCustomerGroupPrice.class, 1).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/productManager/productCustomerGroupPrice/?repage";
	}




}