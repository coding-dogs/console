/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.easyorder.modules.product.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringEscapeUtils;
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

import com.easyorder.common.constant.Constants;
import com.easyorder.common.enums.EasyResponseEnums;
import com.easyorder.common.enums.SequenceTypeEnums;
import com.easyorder.common.utils.BeanUtils;
import com.easyorder.modules.common.service.SequenceService;
import com.easyorder.modules.product.entity.Product;
import com.easyorder.modules.product.service.ProductCustomerGroupPriceService;
import com.easyorder.modules.product.service.ProductCustomerPriceService;
import com.easyorder.modules.product.service.ProductService;
import com.google.common.collect.Lists;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 商品Controller
 * @author qiudequan
 * @version 2017-06-09
 */
@Controller
@RequestMapping(value = "${adminPath}/productManager/product")
public class ProductController extends BaseController {

	@Autowired
	private ProductService productService;
	@Autowired
	ProductCustomerPriceService productCustomerPriceService;
	@Autowired
	ProductCustomerGroupPriceService productCustomerGroupPriceService;
	@Autowired
	SequenceService sequenceService;

	@ModelAttribute
	public Product get(@RequestParam(required=false) String id) {
		Product entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = productService.get(id);
		}
		if (entity == null){
			entity = new Product();
		}
		return entity;
	}

	/**
	 * 商品列表页面
	 */
	@RequiresPermissions("product:product:list")
	@RequestMapping(value = {"list", ""})
	public String list(Product product, HttpServletRequest request, HttpServletResponse response, Model model) {
		String supplierId = UserUtils.getUser().getSupplierId();
		if(com.easyorder.common.utils.StringUtils.isEmpty(supplierId)) {
			logger.error("Did not find the supplier.[supplierId : {}]", supplierId);
			addMessage(model, EasyResponseEnums.NOT_FOUND_SUPPLIER.message);
			return "easyorder/product/productList";
		}
		product.setSupplierId(supplierId);
		Page<Product> page = productService.findPage(new Page<Product>(request, response), product); 
		model.addAttribute("page", page);
		return "easyorder/product/productList";
	}

	/**
	 * 查看，增加，编辑商品表单页面
	 */
	@RequiresPermissions(value={"product:product:view","product:product:add","product:product:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Product product, Model model) {
		String supplierId = UserUtils.getUser().getSupplierId();
		if(com.easyorder.common.utils.StringUtils.isEmpty(supplierId)) {
			logger.error("Did not find the supplier.[supplierId : {}]", supplierId);
			addMessage(model, EasyResponseEnums.NOT_FOUND_SUPPLIER.message);
			return "easyorder/product/productForm";
		}
		product.setSupplierId(supplierId);
		model.addAttribute("product", product);
		if(Constants.ACTION_VIEW.equals(product.getAction())) {
			return "easyorder/product/productDetail";
		}
		if(Constants.ACTION_ADD.equals(product.getAction())) {
			Long seq = sequenceService.getSeqByType(SequenceTypeEnums.PRODUCT_NO.type);
			if(seq != null) {
				product.setProductNo(String.valueOf(seq));
			}
		}
		return "easyorder/product/productForm";
	}

	/**
	 * 保存商品
	 */
	@RequiresPermissions(value={"product:product:add","product:product:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Product product, Model model, RedirectAttributes redirectAttributes) throws Exception{
		String supplierId = UserUtils.getUser().getSupplierId();
		if(com.easyorder.common.utils.StringUtils.isEmpty(supplierId)) {
			logger.error("Did not find the supplier.[supplierId : {}]", supplierId);
			addMessage(redirectAttributes, EasyResponseEnums.NOT_FOUND_SUPPLIER.message);
			return "redirect:"+Global.getAdminPath()+"/productManager/product/?repage";
		}
		product.setSupplierId(supplierId);
		if (!beanValidator(model, product)){
			return form(product, model);
		}
		
		product.setSpecJson(StringEscapeUtils.unescapeHtml4(product.getSpecJson()));;
		if(!product.getIsNewRecord()){//编辑表单保存
			Product t = productService.get(product.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(product, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			productService.save(t);//保存
		}else{//新增表单保存
			productService.save(product);//保存
		}
		addMessage(redirectAttributes, "保存商品成功");
		return "redirect:"+Global.getAdminPath()+"/productManager/product/?repage";
	}

	/**
	 * 删除商品
	 */
	@RequiresPermissions("product:product:del")
	@RequestMapping(value = "delete")
	public String delete(Product product, RedirectAttributes redirectAttributes) {
		productService.delete(product);
		addMessage(redirectAttributes, "删除商品成功");
		return "redirect:"+Global.getAdminPath()+"/productManager/product/?repage";
	}

	/**
	 * 批量删除商品
	 */
	@RequiresPermissions("product:product:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			productService.delete(productService.get(id));
		}
		addMessage(redirectAttributes, "删除商品成功");
		return "redirect:"+Global.getAdminPath()+"/productManager/product/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("product:product:export")
	@RequestMapping(value = "export", method=RequestMethod.POST)
	public String exportFile(Product product, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "商品"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			Page<Product> page = productService.findPage(new Page<Product>(request, response, -1), product);
			new ExportExcel("商品", Product.class).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出商品记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/productManager/product/?repage";
	}

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("product:product:import")
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Product> list = ei.getDataList(Product.class);
			for (Product product : list){
				try{
					productService.save(product);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条商品记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条商品记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入商品失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/productManager/product/?repage";
	}

	/**
	 * 下载导入商品数据模板
	 */
	@RequiresPermissions("product:product:import")
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "商品数据导入模板.xlsx";
			List<Product> list = Lists.newArrayList(); 
			new ExportExcel("商品数据", Product.class, 1).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/productManager/product/?repage";
	}

	@RequiresPermissions(value={"product:product:add","product:product:edit"},logical=Logical.OR)
	@RequestMapping(value = "picDesc", method = RequestMethod.POST)
	public String savePicAndDesc(Product product, Model model, RedirectAttributes redirectAttributes) throws Exception{
		String supplierId = UserUtils.getUser().getSupplierId();
		if(com.easyorder.common.utils.StringUtils.isEmpty(supplierId)) {
			logger.error("Did not find the supplier.[supplierId : {}]", supplierId);
			addMessage(redirectAttributes, EasyResponseEnums.NOT_FOUND_SUPPLIER.message);
			return "redirect:" + Global.getAdminPath() + "/productManager/product/?repage";
		}
		if(!com.easyorder.common.utils.StringUtils.hasText(product.getId())) {
			logger.error("ProductId is empty.[productId : {}]", product.getId());
			addMessage(redirectAttributes, EasyResponseEnums.NOT_FOUND_PRODUCT.message);
			return "redirect:" + Global.getAdminPath() + "/productManager/product/?repage";
		}
		product.setSupplierId(supplierId);
		Product p = productService.get(product);
		if(BeanUtils.isEmpty(p)) {
			logger.error("Did not find the product info.[productId : {}]", product.getId());
			addMessage(redirectAttributes, EasyResponseEnums.NOT_FOUND_PRODUCT.message);
			return "redirect:" + Global.getAdminPath() + "/productManager/product/?repage"; 
		}
		
		p.setCoverUrl(product.getCoverUrl());
		p.setDescription(StringEscapeUtils.unescapeHtml4(product.getDescription()));
		p.setPictures(product.getPictures());
		productService.save(p);
		addMessage(redirectAttributes, "商品图片及描述保存成功");
		return "redirect:" + Global.getAdminPath() + "/productManager/product/?repage";
	}
	

}