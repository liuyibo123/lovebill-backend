package com.liuyibo.lovebill.dao;

import com.liuyibo.lovebill.dao.entity.BillCateGory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillCateGoryRepository extends JpaRepository<BillCateGory, String> {
    List<BillCateGory> findByBillIdEquals(String billId);
    BillCateGory findFirstByBillIdAndCategoryIdAndMonth(String billId, String categoryId, String month);
    List<BillCateGory> findAllByBillIdAndMonth(String billId, String month);
}
