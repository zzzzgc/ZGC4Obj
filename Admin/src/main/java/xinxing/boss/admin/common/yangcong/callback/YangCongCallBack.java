package xinxing.boss.admin.common.yangcong.callback;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import xinxing.boss.admin.common.utils.http.HttpUtils;
import xinxing.boss.admin.common.utils.json.JsonUtils;
import xinxing.boss.admin.common.yangcong.utils.YangCongUtils;
import xinxing.boss.admin.system.user.domain.User;
import xinxing.boss.admin.system.user.service.UserService;


@Controller
public class YangCongCallBack {
	
	private static final Logger log = Logger.getLogger(YangCongCallBack.class);

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/yangcong_callback.do")
    public void mobilePayBack(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String postData = HttpUtils.getReqPostString(request, log);

		if(StringUtils.isNotBlank(postData)){
			Map<String,String> map = new HashMap<String, String>();
			String []postArray = postData.split("&");
			for(int i=0,len=postArray.length;i<len;i++){
				String []param = StringUtils.split(postArray[i], "=", 2);//防止加密后带两个==的错误
				map.put(param[0], URLDecoder.decode(param[1],"UTF-8"));
			}
			System.out.println(JsonUtils.obj2Json(map));
			if(YangCongUtils.isSignOk(map)){
			User user = userService.getUserByYangCongEventId(map.get("event_id"));
			if(user!=null){
				user.setBind(1);
				user.setYangId(map.get("uid"));
				userService.updateUserNotPwd(user);
			}
			PrintWriter pw = response.getWriter();
            pw.print("{status:\"ok\"}");
            pw.flush();
            pw.close();
			}
		}else {
			log.info("返回数据为空");
		}
		
	}
}
