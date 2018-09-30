package com.liuyibo.lovebill.dao;

import com.liuyibo.lovebill.dao.entity.BillBill;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillBillRepository extends JpaRepository<BillBill,Long> {
}
