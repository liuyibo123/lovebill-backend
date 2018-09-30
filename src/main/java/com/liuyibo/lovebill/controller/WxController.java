package com.liuyibo.lovebill.controller;

import com.liuyibo.lovebill.common.BillResult;
import com.liuyibo.lovebill.common.BillResultConstrant;
import com.liuyibo.lovebill.dao.BillUserRepository;
import com.liuyibo.lovebill.dao.entity.BillUser;
import com.liuyibo.lovebill.service.IWxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 服务器调用微信api的方式
 * @author liuyibo01
 */
@RestController
@RequestMapping("/wx")
@SuppressWarnings("unused")
public class WxController {
    private final IWxService wxService;
    private final BillUserRepository billUserRepository;

    @Autowired
    public WxController(IWxService wxService, BillUserRepository billUserRepository) {
        this.wxService = wxService;
        this.billUserRepository = billUserRepository;
    }

    /**
     * 获取用户登录的OpenId
     * @param code 登录获取的临时码 有效时间5分钟
     * @return openId
     */
    @RequestMapping("/getOpenId")
    public Object getOpenId(@RequestParam(name = "code") String code){
        String openId = wxService.getOpenId(code);
        Optional<BillUser> optional=billUserRepository.findById(openId);
        boolean registed = optional.isPresent();

        Map<String,Object> result = new HashMap<>();
        result.put("openId",openId);
        result.put("registed",registed);
        if(registed){
            BillUser user = optional.get();
            result.put("user",user);
        }
        return new BillResult(BillResultConstrant.success, result);
    }
}
