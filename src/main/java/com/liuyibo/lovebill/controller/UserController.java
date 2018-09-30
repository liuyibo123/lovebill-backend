package com.liuyibo.lovebill.controller;


import com.alibaba.fastjson.JSON;
import com.liuyibo.lovebill.common.BillResult;
import com.liuyibo.lovebill.common.BillResultConstrant;
import com.liuyibo.lovebill.dao.BillTotalRepository;
import com.liuyibo.lovebill.dao.BillUserRepository;
import com.liuyibo.lovebill.dao.entity.BillTotal;
import com.liuyibo.lovebill.dao.entity.BillUser;
import com.liuyibo.lovebill.utils.idgen.IdGenerate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    BillUserRepository billUserRepository;
    @Autowired
    BillTotalRepository billTotalRepository;
    @RequestMapping("/reg")
    public Object regist(@RequestBody Map<String,Object> userData){
        if(log.isDebugEnabled()){
            log.debug("========userData========{}",userData);
        }
        String openId = (String) userData.get("openId");
        Optional<BillUser> suser = billUserRepository.findById(openId);
        if(suser.isPresent()){
            return new BillResult(BillResultConstrant.user_already_exist,suser.get());
        }
        String billId;
        if(userData.containsKey("billId")&&userData.get("billId")!=null){
             billId= (String) userData.get("billId");
        }else{
             billId = IdGenerate.nextId();
             BillTotal billTotal = new BillTotal();
             billTotal.setBillId(billId);
             billTotal.setId(IdGenerate.nextId());
             billTotal.setMonth(new SimpleDateFormat("yyyyMM").format(new Date()));
             billTotalRepository.save(billTotal);
        }
        Map<String,Object> userInfo = (Map<String, Object>) userData.get("userInfo");
        if(log.isDebugEnabled()){
            log.debug("========userInfo.nickName========{}",userInfo.get("nickName"));
        }
        BillUser billUser = new BillUser(userInfo);
        billUser.setBillId(billId);
        billUser.setUserId(openId);
        BillUser user = billUserRepository.save(billUser);
        return new BillResult(BillResultConstrant.success,user);
    }
    @RequestMapping("/getUserSum")
    public Object getUserSum(@RequestParam("openId")String userId){
        Optional<BillUser> opt  = billUserRepository.findById(userId);

        if (!opt.isPresent()){
            return new BillResult(BillResultConstrant.user_not_exist,"");
        }
        BillUser user = opt.get();
        List<BillTotal> billTotalList = billTotalRepository.findAllByBillId(user.getBillId());
        BigDecimal sum = new BigDecimal("0");
        BigDecimal left = new BigDecimal("0");
        BigDecimal cost = new BigDecimal("0");
        for(BillTotal billTotal:billTotalList){
            sum = sum.add(billTotal.getInCome());
            left = left.add(billTotal.getRemain());
        }
        cost = sum.subtract(left);
        Map<String,BigDecimal> result = new HashMap<>();
        result.put("sum",sum);
        result.put("cost",cost);
        result.put("left",left);
        return new BillResult(BillResultConstrant.success,result);
    }
}
