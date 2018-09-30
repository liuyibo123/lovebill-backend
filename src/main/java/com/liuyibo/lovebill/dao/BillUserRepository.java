package com.liuyibo.lovebill.dao;

import com.liuyibo.lovebill.dao.entity.BillUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
public interface BillUserRepository extends JpaRepository<BillUser,String> {
}
