package com.easyorder.modules.order.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.util.CollectionUtils;
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
import com.easyorder.common.constant.Constants;
import com.easyorder.common.enums.EasyResponseEnums;
import com.easyorder.modules.order.entity.Order;
import com.easyorder.modules.order.entity.OrderStatistics;
import com.easyorder.modules.order.service.OrderService;
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

@Controller
@RequestMapping(value = "${adminPath}/orderManager/order")
public class OrderController extends BaseController {
	@Autowired
	private OrderService orderService;

	@ModelAttribute
	public Order get(@RequestParam(required=false) String id) {
		Order entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderService.get(id);
		}
		if (entity == null){
			entity = new Order();
		}
		return entity;
	}

	/**
	 * 订货单列表页面
	 */
	@RequiresPermissions("order:order:list")
	@RequestMapping(value = {"list", ""})
	public String list(Order order, HttpServletRequest request, HttpServletResponse response, Model model) {
		String supplierId = UserUtils.getUser().getSupplierId();
		if(com.easyorder.common.utils.StringUtils.isEmpty(supplierId)) {
			logger.error("Did not find the supplier.[supplierId : {}]", supplierId);
			addMessage(model, EasyResponseEnums.NOT_FOUND_SUPPLIER.message);
			return "easyorder/order/orderList";
		}
		order.setSupplierId(supplierId);
		Page<Order> page = orderService.findPage(new Page<Order>(request, response), order); 
		model.addAttribute("page", page);
		return "easyorder/order/orderList";
	}

	/**
	 * 查看，增加，编辑订货单表单页面
	 */
	@RequiresPermissions(value={"order:order:view","order:order:add","order:order:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Order order, Model model) {
		String returnPage = "easyorder/order/orderDetail";
		if(Constants.ACTION_ADD.equals(order.getAction())) {
			returnPage = "easyorder/order/orderForm";
		}
		String supplierId = UserUtils.getUser().getSupplierId();
		if(com.easyorder.common.utils.StringUtils.isEmpty(supplierId)) {
			logger.error("Did not find the supplier.[supplierId : {}]", supplierId);
			addMessage(model, EasyResponseEnums.NOT_FOUND_SUPPLIER.message);
			return returnPage;
		}
		order.setSupplierId(supplierId);
		model.addAttribute("order", order);
		return returnPage;
	}

	/**
	 * 保存订货单
	 */
	@RequiresPermissions(value={"order:order:add","order:order:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Order order, Model model, RedirectAttributes redirectAttributes) throws Exception{
		String supplierId = UserUtils.getUser().getSupplierId();
		if(com.easyorder.common.utils.StringUtils.isEmpty(supplierId)) {
			logger.error("Did not find the supplier.[supplierId : {}]", supplierId);
			addMessage(redirectAttributes, EasyResponseEnums.NOT_FOUND_SUPPLIER.message);
			return "redirect:"+Global.getAdminPath()+"/orderManager/order/?repage";
		}
		order.setSupplierId(supplierId);
		if (!beanValidator(model, order)){
			return form(order, model);
		}
		if(!order.getIsNewRecord()){//编辑表单保存
			Order t = orderService.get(order.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(order, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			orderService.save(t);//保存
			if(Constants.ACTION_EDIT.equals(order.getAction())) {
				t.setAction(order.getAction());
				model.addAttribute("order", t);
				addMessage(redirectAttributes, "保存订货单成功");
				return "easyorder/order/orderDetail";
			}
		}else{//新增表单保存
			orderService.save(order);//保存
		}
		addMessage(redirectAttributes, "保存订货单成功");
		return "redirect:"+Global.getAdminPath()+"/orderManager/order/?repage";
	}

	/**
	 * 删除订货单
	 */
	@RequiresPermissions("order:order:del")
	@RequestMapping(value = "delete")
	public String delete(Order order, RedirectAttributes redirectAttributes) {
		orderService.delete(order);
		addMessage(redirectAttributes, "删除订货单成功");
		return "redirect:"+Global.getAdminPath()+"/orderManager/order/?repage";
	}

	/**
	 * 批量删除订货单
	 */
	@RequiresPermissions("order:order:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			orderService.delete(orderService.get(id));
		}
		addMessage(redirectAttributes, "删除订货单成功");
		return "redirect:"+Global.getAdminPath()+"/productManager/productBrand/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("order:order:export")
	@RequestMapping(value = "export", method=RequestMethod.POST)
	public String exportFile(Order order, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "订货单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			Page<Order> page = orderService.findPage(new Page<Order>(request, response, -1), order);
			new ExportExcel("订货单", Order.class).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出订货单记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/orderManager/order/?repage";
	}

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("order:order:import")
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Order> list = ei.getDataList(Order.class);
			for (Order order : list){
				try{
					orderService.save(order);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条订货单记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条订货单记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入订货单失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/orderManager/order/?repage";
	}

	/**
	 * 下载导入订货单数据模板
	 */
	@RequiresPermissions("order:order:import")
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "订货单数据导入模板.xlsx";
			List<Order> list = Lists.newArrayList(); 
			new ExportExcel("订货单数据", Order.class, 1).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/orderManager/order/?repage";
	}
	
	@RequestMapping(value = "statistics")
	@ResponseBody
	public EasyResponse<OrderStatistics> getStatistics(Order order) {
		String supplierId = UserUtils.getUser().getSupplierId();
		if(com.easyorder.common.utils.StringUtils.isEmpty(supplierId)) {
			logger.error("Did not find the supplier.[supplierId : {}]", supplierId);
			return EasyResponse.buildByEnum(EasyResponseEnums.NOT_FOUND_SUPPLIER);
		}
		order.setSupplierId(supplierId);
		List<OrderStatistics> resultList = orderService.getOrderStatistics(order);
		if(CollectionUtils.isEmpty(resultList)) {
			return EasyResponse.buildSuccess(null);
		}
		return EasyResponse.buildSuccess(resultList.get(0));
	}
	
	@RequestMapping(value = "statisticsDetail")
	@ResponseBody
	public EasyResponse<Page<OrderStatistics>> getStatisticsDetail(OrderStatistics orderStatistics, HttpServletRequest request, HttpServletResponse response) {
		String supplierId = UserUtils.getUser().getSupplierId();
		if(com.easyorder.common.utils.StringUtils.isEmpty(supplierId)) {
			logger.error("Did not find the supplier.[supplierId : {}]", supplierId);
			return EasyResponse.buildByEnum(EasyResponseEnums.NOT_FOUND_SUPPLIER);
		}
		orderStatistics.setSupplierId(supplierId);
		Page<OrderStatistics> resultList = orderService.getOrderStatisticsDetail(new Page<OrderStatistics>(request, response), orderStatistics);
		return EasyResponse.buildSuccess(resultList);
	}

}
