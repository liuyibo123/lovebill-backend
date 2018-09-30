package com.liuyibo.lovebill.service;

import com.alibaba.fastjson.JSON;
import com.liuyibo.lovebill.utils.web.http.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author liuyibo01
 */
@Slf4j
@Service
public class WxService implements IWxService {
    private final String appId = "wxafa80476382c6e9a";
    private final String  secret = "5410e6e494afab8ef59a1adea4e9f7e0";
    private final String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+appId+"&secret="+secret+"&grant_type=authorization_code&js_code=";
    @Override
    public String getOpenId(String code){
        if(log.isDebugEnabled()){
            log.debug("========WxService getOpenId start========");
            log.debug("url:{}",url);
            log.debug("code:{}",code);
        }
        String rs = HttpClientUtils.get(url+code);
        Map<String,Object> result = (Map<String, Object>) JSON.parse(rs);
        if(log.isDebugEnabled()){
            log.debug("========result========{}",result);

        }
        String openId = (String) result.get("openid");
        return openId;
    }
}
