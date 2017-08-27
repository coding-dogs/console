package com.easyorder.modules.order.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.easyorder.common.beans.EasyResponse;
import com.easyorder.common.enums.EasyResponseEnums;
import com.easyorder.common.utils.StringUtils;
import com.easyorder.modules.order.entity.OrderExpress;
import com.easyorder.modules.order.service.OrderExpressService;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.sys.utils.UserUtils;

@Controller
@RequestMapping(value = "${adminPath}/orderManager/orderExpress")
public class OrderExpressController extends BaseController {
	
	@Autowired
	private OrderExpressService orderExpressService;
	
	@PostMapping(value = "save")
	@ResponseBody
	public EasyResponse<String> save(OrderExpress orderExpress, Model model) throws Exception {
		String supplierId = UserUtils.getUser().getSupplierId();
		if(com.easyorder.common.utils.StringUtils.isEmpty(supplierId)) {
			logger.error("Did not find the supplier.[supplierId : {}]", supplierId);
			return EasyResponse.buildByEnum(EasyResponseEnums.NOT_FOUND_SUPPLIER);
		}
		if(StringUtils.isEmpty(orderExpress.getOrderId())) {
			logger.error("When saving the orderExpress,the param orderId is empty.");
			return EasyResponse.buildByEnum(EasyResponseEnums.REQUEST_PARAM_ERROR);
		}
		if (!beanValidator(model, orderExpress)){
			logger.error("When saving the orderExpress, bean validate error.");
			return EasyResponse.buildByEnum(EasyResponseEnums.REQUEST_PARAM_ERROR);
		}
		String orderExpressId = orderExpress.getId();
		if(!orderExpress.getIsNewRecord()){//编辑表单保存
			OrderExpress t = orderExpressService.get(orderExpress.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(orderExpress, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			orderExpressService.save(t);//保存
			orderExpressId = t.getId();
		}else{//新增表单保存
			orderExpressService.save(orderExpress);//保存
		}
		return EasyResponse.buildSuccess(orderExpressId);
	}
	
	/**
	 * 查看，增加，编辑物流信息表单页面
	 */
	@RequestMapping(value = "form")
	public String form(OrderExpress orderExpress, Model model) {
		String supplierId = UserUtils.getUser().getSupplierId();
		if(com.easyorder.common.utils.StringUtils.isEmpty(supplierId)) {
			logger.error("Did not find the supplier.[supplierId : {}]", supplierId);
			addMessage(model, EasyResponseEnums.NOT_FOUND_SUPPLIER.message);
			return "easyorder/order/orderExpressForm";
		}
		model.addAttribute("orderExpress", orderExpress);
		return "easyorder/order/orderExpressForm";
	}
}
