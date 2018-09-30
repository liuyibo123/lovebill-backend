package com.liuyibo.lovebill.common;

import com.liuyibo.lovebill.dao.BillCateGoryRepository;
import com.liuyibo.lovebill.dao.BillTotalRepository;
import com.liuyibo.lovebill.dao.BillUserRepository;
import com.liuyibo.lovebill.dao.entity.BillCateGory;
import com.liuyibo.lovebill.dao.entity.BillTotal;
import com.liuyibo.lovebill.dao.entity.BillUser;
import com.liuyibo.lovebill.utils.idgen.IdGenerate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@Slf4j
public class ScheduleService {
    @Autowired
    private BillUserRepository billUserRepository;
    @Autowired
    private BillTotalRepository billTotalRepository;
    @Autowired
    private BillCateGoryRepository billCateGoryRepository;
    @Scheduled(cron = "00 01 00 01 * ?")
    //每月创建下个月的表
    @Transactional(rollbackFor = Exception.class)
    public void nextMonth(){
        final BigDecimal zero = new BigDecimal("0");
        final String month = new SimpleDateFormat("yyyyMM").format(new Date());
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        Date lastMonth = calendar.getTime();

        List<BillUser> billUserList = billUserRepository.findAll();
        Set<String> billIdSet = new TreeSet<>();
        for(BillUser user:billUserList){
            billIdSet.add(user.getBillId());
        }
        for(String billId:billIdSet){
            BillTotal total = new BillTotal();
            total.setId(IdGenerate.nextId());
            total.setBillId(billId);
            total.setMonth(month);
            total.setInCome(zero);
            total.setRemain(zero);
            billTotalRepository.save(total);
            List<BillCateGory> billCateGories = billCateGoryRepository.findAllByBillIdAndMonth(billId,new SimpleDateFormat("yyyyMM").format(lastMonth));
            for(BillCateGory cateGory:billCateGories){
                BillCateGory billCateGory = new BillCateGory();
                billCateGory.setBillId(cateGory.getBillId());
                billCateGory.setTotal(zero);
                billCateGory.setRemain(zero);
                billCateGory.setCategoryId(cateGory.getId());
                billCateGory.setCategoryName(cateGory.getCategoryName());
                billCateGory.setId(IdGenerate.nextId());
                billCateGory.setMonth(month);
                billCateGory.setType(cateGory.getType());
                billCateGoryRepository.save(billCateGory);
            }
        }

    }
}
