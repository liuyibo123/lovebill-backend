package com.liuyibo.lovebill.controller;

import com.liuyibo.lovebill.common.BillResult;
import com.liuyibo.lovebill.common.BillResultConstrant;
import com.liuyibo.lovebill.dao.BillBillRepository;
import com.liuyibo.lovebill.dao.BillCateGoryRepository;
import com.liuyibo.lovebill.dao.BillTotalRepository;
import com.liuyibo.lovebill.dao.BillUserRepository;
import com.liuyibo.lovebill.dao.entity.BillBill;
import com.liuyibo.lovebill.dao.entity.BillCateGory;
import com.liuyibo.lovebill.dao.entity.BillTotal;
import com.liuyibo.lovebill.dao.entity.BillUser;
import com.liuyibo.lovebill.utils.collect.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping("/bill")
@RestController
@Slf4j
@SuppressWarnings("unused")
public class BillTotalController {
    private final Logger logger = LoggerFactory.getLogger(BillTotalController.class);
    @Autowired
    BillUserRepository billUserRepository;
    @Autowired
    BillTotalRepository billTotalRepository;
    @Autowired
    BillBillRepository billBillRepository;
    @Autowired
    BillCateGoryRepository billCateGoryRepository;
    @RequestMapping("/getBillTotal")
    public Object getBillTotalByDate(@RequestBody Map<String, String> body) {
        String openId = body.get("openId");
        String month = body.get("month");
        String by = body.get("by");
        if (logger.isDebugEnabled()) {
            logger.debug("======== BillTotalController getBillTotal start========{}", openId);
        }
        BillUser billUser;
        Optional<BillUser> user = billUserRepository.findById(openId);
        if (user.isPresent()) {
            billUser = user.get();
        } else {
            return new BillResult(BillResultConstrant.user_not_exist, openId);
        }
        String billId = billUser.getBillId();
        if (logger.isDebugEnabled()) {
            logger.debug("========billId========{}", billId);
        }
        if ("".equals(billId)) {
            return new BillResult(BillResultConstrant.bill_id_not_set, openId);
        }
        BillTotal total = billTotalRepository.findFirstByBillIdAndMonth(billId,month);
        if (total == null) {
            return new BillResult(BillResultConstrant.get_bill_total_fail, billId);
        }
        BillBill billBill = new BillBill();
        billBill.setBillId(billId);
        billBill.setDate(month);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("month", ExampleMatcher.GenericPropertyMatchers.startsWith());
        Example example = Example.of(billBill, matcher);
        List<BillBill> billList = billBillRepository.findAll(example);
        if (log.isDebugEnabled()) {
            log.debug("========billList ========{}", billList);
        }
        Map<String, List<BillBill>> dateBillMap = new HashMap<>();
        List<Map<String, Object>> allBills = new ArrayList<>();
        for (BillBill bill : billList) {
            String flag;
            if("date".equals(by)){
                flag = bill.getDate();
            }else{
                flag = bill.getCategoryId();
            }
            if (dateBillMap.containsKey(flag)) {
                dateBillMap.get(flag).add(bill);
            } else {
                List<BillBill> temp = new ArrayList<>();
                temp.add(bill);
                dateBillMap.put(flag, temp);
            }
        }
        for (String key : dateBillMap.keySet()) {
            Map<String, Object> temp = new HashMap<>();
            temp.put(by, key);
            temp.put("bills", dateBillMap.get(key));
            allBills.add(temp);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("bill_id", total.getBillId());
        data.put("id", total.getId());
        data.put("in_come", total.getInCome());
        data.put("month", total.getMonth());
        data.put("remain", total.getRemain());
        data.put("allbills", allBills);
        return new BillResult(BillResultConstrant.success,data);
    }

    @RequestMapping("/getCategoryDict")
    public Object getDictCategory(@RequestParam("billId")String billId){
        List<BillCateGory> cateGoryList= billCateGoryRepository.findByBillIdEquals(billId);
        Map<String,String> categoryMap = new HashMap<>();
        for(BillCateGory billCateGory:cateGoryList){
            categoryMap.put(billCateGory.getCategoryId(),billCateGory.getCategoryName());
        }
        Map<String,Object> category = new HashMap<>();
        category.put("categorydict",cateGoryList);
        category.put("categorymap",categoryMap);

        return category;
    }

    @RequestMapping("/add")
    @Transactional
    public Object addBill(@RequestBody Map<String,Object> request){
        BigDecimal amount = new BigDecimal(String.valueOf(request.get("amount")));
        Date date = new Date();
        String month = new SimpleDateFormat("yyyyMM").format(date);
        String billId = (String) request.get("billId");
        String categoryId = (String) request.get("category_id");
        String type = (String) request.get("type");
        BillBill billBill = new BillBill();
        billBill.setBillId(billId);
        billBill.setNote((String) request.get("note"));
        billBill.setAmount(amount);
        billBill.setCategoryId(categoryId);
        billBill.setType(type);
        billBill.setGeoPoint((String) request.get("geo_point"));

        billBill.setDate(new SimpleDateFormat("yyyyMMdd").format(date));
        billBill.setTime(new SimpleDateFormat("HH:mm:ss").format(date));
        billBillRepository.save(billBill);
        BillTotal billTotal = billTotalRepository.findFirstByBillIdAndMonth(billId,month);
        BillCateGory billCateGory = billCateGoryRepository.findFirstByBillIdAndCategoryIdAndMonth(billId,categoryId,month);
        //1.收入 2.支出
        if(type.equals("1")){
            billTotal.setInCome(billTotal.getInCome().add(amount));
            billCateGory.setTotal(billCateGory.getTotal().add(amount));
        }else{
            billTotal.setRemain(billTotal.getRemain().subtract(amount));
            billCateGory.setRemain(billCateGory.getRemain().subtract(amount));
        }
        billTotalRepository.save(billTotal);
        billCateGoryRepository.save(billCateGory);

        return new BillResult(BillResultConstrant.success,null);
    }
}
