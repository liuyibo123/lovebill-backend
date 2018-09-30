package com.liuyibo.lovebill.dao;

import com.liuyibo.lovebill.dao.entity.BillTotal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillTotalRepository extends JpaRepository<BillTotal, String> {
    BillTotal findFirstByBillId(String billId);
    BillTotal findFirstByBillIdAndMonth(String billId, String month);
    List<BillTotal> findAllByBillId(String billId);
}
