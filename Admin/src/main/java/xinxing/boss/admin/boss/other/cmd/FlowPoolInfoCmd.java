package xinxing.boss.admin.boss.other.cmd;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import xinxing.boss.admin.boss.other.domain.FlowPoolInfo;
import xinxing.boss.admin.boss.other.service.FlowPoolInfoService;
import xinxing.boss.admin.common.persistence.Page;
import xinxing.boss.admin.common.persistence.PropertyFilter;
import xinxing.boss.admin.common.utils.http.HttpUtils;
import xinxing.boss.admin.common.utils.json.JsonUtils;
import xinxing.boss.admin.common.utils.string.StringUtils;
import xinxing.boss.admin.common.web.BaseController;

@Controller
@RequestMapping(value = "boss/flowPoolInfo")
public class FlowPoolInfoCmd extends BaseController {
	private static Logger log = LoggerFactory.getLogger(FlowPoolInfoCmd.class);
	@Autowired
	private FlowPoolInfoService flowPoolInfoService;

	// 弹出框的方法
	@RequestMapping(value = "alertWindow")
	@ResponseBody
	public String alertWindow(HttpServletRequest req, FlowPoolInfo flowPoolInfo, Integer[] ids, String method) {
		try {
			Map<String, String> reqParams = HttpUtils.getReqParams(req);
			if (reqParams == null) {
				reqParams = new HashMap<String, String>();
			}
			log.info("alertWindow,reqParams:" + JsonUtils.obj2Json(reqParams));
			Integer changeValue = reqParams.get("changeValue") != null ? Integer.parseInt(reqParams.get("changeValue")) : null;
			if (changeValue == null) {
				return "有值未填";
			}
			if (changeValue < 0) {
				return "不能为负数";
			}

			for (Integer id : ids) {
				FlowPoolInfo before = flowPoolInfoService.get(id);
				switch (method) {
				case "changeReserveNum":
					int addNum = changeValue - before.getReserveNum();
					if (before.getPoolRemain() - addNum < 0) {
						return "id:" + id + "的剩余量不能为负数";
					}
					flowPoolInfoService.changeReserveNumAndReserveNum(id, addNum);
					break;
				case "changeAlarmNum":
					flowPoolInfoService.changeAlarmNum(id, changeValue);
					break;
				default:
					log.error("错误的method:" + method);
					break;
				}

				log.info("method:" + method + ",changeValue:" + changeValue + ",before:" + JsonUtils.obj2Json(before));
			}
		} catch (Exception e) {
			System.out.println("-----------cuowu ");
			log.error(e.getMessage(), e);
			return "";
		}

		return "success";
	}

	// 额外的方法
	@RequestMapping(value = "updateStatus", method = RequestMethod.POST)
	@ResponseBody
	public String updateStatus(HttpServletRequest request, Integer[] ids, Integer status) {
		if (ids == null || ids.length == 0 || status == null) {
			return "没有id";
		}
		if (status != null && status >= 0 && status <= 1) {
			for (Integer id : ids) {
				FlowPoolInfo before = flowPoolInfoService.get(id);
				log.info("updateStatus:" + status + ",before:" + JsonUtils.obj2Json(before));
				flowPoolInfoService.changeStatus(id, status);
			}
		}
		return "success";
	}

	/**
	 * 默认页面
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request) {
		Map<String, String> reqMap = HttpUtils.getReqParams(request);
		if (reqMap == null) {
			reqMap = new HashMap<String, String>();
		}
		String page = reqMap.get("page");
		if (StringUtils.isEmpty(page)) {
			page = "List";
		}
		request.getSession().setAttribute("reqMap", reqMap);
		return "boss/flowPoolInfo/flowPoolInfo" + page;
	}

	/**
	 * 获取所有订单信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "flowPoolInfoList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getConfigList(HttpServletRequest request) {
		Page<FlowPoolInfo> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page.setOrderBy("providerId,poolNum");
		page.setOrder("asc,asc");
		page = flowPoolInfoService.search(page, filters);
		List<FlowPoolInfo> result = page.getResult();
		log.info("流量池查询结果:" + JsonUtils.obj2Json(result));
		return getEasyUIData(page);
	}

	/**
	 * 添加订单信息跳转
	 * 
	 * @param model
	 * @return
	 */
	@RequiresPermissions("boss:flowPoolInfo:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute("flowPoolInfo", new FlowPoolInfo());
		return "boss/flowPoolInfo/flowPoolInfoAdd";
	}

	/**
	 * 添加订单信息
	 * 
	 * @param dict
	 * @param model
	 */
	@RequiresPermissions("boss:flowPoolInfo:add")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@Valid FlowPoolInfo flowPoolInfo, Model model) {
		flowPoolInfo.setPoolRemain(flowPoolInfo.getTotalNum() - flowPoolInfo.getReserveNum());
		flowPoolInfo.setLastUpdateTime(new Date());
		flowPoolInfoService.save(flowPoolInfo);
		// ParameterListener.flushStatus("init_boss_config");
		log.info("flowPoolInfo add:" + flowPoolInfo.getId());
		return "success";
	}

	/**
	 * 修改订单信息跳转
	 */
	@RequiresPermissions("boss:flowPoolInfo:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("flowPoolInfo", flowPoolInfoService.get(id));
		return "boss/flowPoolInfo/flowPoolInfoUpdate";
	}

	/**
	 * 修改订单信息
	 * 
	 * @param tmallOrder
	 * @param model
	 * @return
	 */
	@RequiresPermissions("boss:flowPoolInfo:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody FlowPoolInfo flowPoolInfo, Model model) {
		Integer id = flowPoolInfo.getId();
		FlowPoolInfo before = flowPoolInfoService.get(id);
		log.info("流量池修改前:" + JsonUtils.obj2Json(before));
		flowPoolInfoService.getEntityDao().getSession().clear();
		flowPoolInfoService.update(flowPoolInfo);
		log.info("流量池修改后:" + JsonUtils.obj2Json(flowPoolInfo));
		// ParameterListener.flushStatus("nit_fail_reason");
		return "success";
	}

	/**
	 * 删除订单
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("boss:flowPoolInfo:delete")
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		flowPoolInfoService.delete(id);
		log.info("flowPoolInfo delete:" + id);
		// ParameterListener.flushStatus("nit_fail_reason");
		return "success";
	}

}
