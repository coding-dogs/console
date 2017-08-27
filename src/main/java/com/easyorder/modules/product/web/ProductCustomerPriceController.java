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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.easyorder.common.beans.EasyResponse;
import com.easyorder.common.enums.EasyResponseEnums;
import com.easyorder.modules.product.entity.ProductCustomerPrice;
import com.easyorder.modules.product.service.ProductCustomerPriceService;
import com.google.common.collect.Lists;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.common.web.BaseController;

/**
 * 商品客户指定价Controller
 * @author qiudequan
 * @version 2017-06-09
 */
@Controller
@RequestMapping(value = "${adminPath}/productManager/productCustomerPrice")
public class ProductCustomerPriceController extends BaseController {

	@Autowired
	private ProductCustomerPriceService productCustomerPriceService;

	@ModelAttribute
	public ProductCustomerPrice get(@RequestParam(required=false) String id) {
		ProductCustomerPrice entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = productCustomerPriceService.get(id);
		}
		if (entity == null){
			entity = new ProductCustomerPrice();
		}
		return entity;
	}

	/**
	 * 商品客户指定价列表页面
	 */
	@RequiresPermissions("product:productCustomerPrice:list")
	@RequestMapping(value = {"list", ""})
	public String list(ProductCustomerPrice productCustomerPrice, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProductCustomerPrice> page = productCustomerPriceService.findPage(new Page<ProductCustomerPrice>(request, response), productCustomerPrice); 
		model.addAttribute("page", page);
		return "easyorder/product/productCustomerPriceList";
	}

	/**
	 * 查看，增加，编辑商品客户指定价表单页面
	 */
	@RequiresPermissions(value={"product:productCustomerPrice:view","product:productCustomerPrice:add","product:productCustomerPrice:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ProductCustomerPrice productCustomerPrice, Model model) {
		model.addAttribute("productCustomerPrice", productCustomerPrice);
		return "easyorder/product/productCustomerPriceForm";
	}

	/**
	 * 保存商品客户指定价
	 */
	@RequiresPermissions(value={"product:productCustomerPrice:add","product:productCustomerPrice:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ProductCustomerPrice productCustomerPrice, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, productCustomerPrice)){
			return form(productCustomerPrice, model);
		}
		if(!productCustomerPrice.getIsNewRecord()){//编辑表单保存
			ProductCustomerPrice t = productCustomerPriceService.get(productCustomerPrice.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(productCustomerPrice, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			productCustomerPriceService.save(t);//保存
		}else{//新增表单保存
			productCustomerPriceService.save(productCustomerPrice);//保存
		}
		addMessage(redirectAttributes, "保存商品客户指定价成功");
		return "redirect:"+Global.getAdminPath()+"/productManager/productCustomerPrice/?repage";
	}

	/**
	 * 删除商品客户指定价
	 */
	@RequiresPermissions("product:productCustomerPrice:del")
	@RequestMapping(value = "delete")
	public String delete(ProductCustomerPrice productCustomerPrice, RedirectAttributes redirectAttributes) {
		productCustomerPriceService.delete(productCustomerPrice);
		addMessage(redirectAttributes, "删除商品客户指定价成功");
		return "redirect:"+Global.getAdminPath()+"/productManager/productCustomerPrice/?repage";
	}

	/**
	 * 批量删除商品客户指定价
	 */
	@RequiresPermissions("product:productCustomerPrice:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			productCustomerPriceService.delete(productCustomerPriceService.get(id));
		}
		addMessage(redirectAttributes, "删除商品客户指定价成功");
		return "redirect:"+Global.getAdminPath()+"/productManager/productCustomerPrice/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("product:productCustomerPrice:export")
	@RequestMapping(value = "export", method=RequestMethod.POST)
	public String exportFile(ProductCustomerPrice productCustomerPrice, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "商品客户指定价"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			Page<ProductCustomerPrice> page = productCustomerPriceService.findPage(new Page<ProductCustomerPrice>(request, response, -1), productCustomerPrice);
			new ExportExcel("商品客户指定价", ProductCustomerPrice.class).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出商品客户指定价记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/productManager/productCustomerPrice/?repage";
	}

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("product:productCustomerPrice:import")
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ProductCustomerPrice> list = ei.getDataList(ProductCustomerPrice.class);
			for (ProductCustomerPrice productCustomerPrice : list){
				try{
					productCustomerPriceService.save(productCustomerPrice);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条商品客户指定价记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条商品客户指定价记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入商品客户指定价失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/productManager/productCustomerPrice/?repage";
	}

	/**
	 * 下载导入商品客户指定价数据模板
	 */
	@RequiresPermissions("product:productCustomerPrice:import")
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "商品客户指定价数据导入模板.xlsx";
			List<ProductCustomerPrice> list = Lists.newArrayList(); 
			new ExportExcel("商品客户指定价数据", ProductCustomerPrice.class, 1).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/productManager/productCustomerPrice/?repage";
	}

	@RequiresPermissions("product:product:list")
	@RequestMapping(value = "customers")
	@ResponseBody
	public EasyResponse<List<String>> getCustomers(String productId) {
		if(!com.easyorder.common.utils.StringUtils.hasText(productId)) {
			logger.error("When querying the customerPrices, the param productId is empty.");
			return EasyResponse.buildByEnum(EasyResponseEnums.REQUEST_PARAM_ERROR);
		}
		
		List<String> customerIds = productCustomerPriceService.getCustomerIds(productId);
		return EasyResponse.buildSuccess(customerIds);
	}


}