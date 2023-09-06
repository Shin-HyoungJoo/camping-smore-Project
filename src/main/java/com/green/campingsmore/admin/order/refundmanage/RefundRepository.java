package com.green.campingsmore.admin.order.refundmanage;

import com.green.campingsmore.entity.RefundEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefundRepository extends JpaRepository<RefundEntity,Long>, RefundRepositoryCustom {

}
