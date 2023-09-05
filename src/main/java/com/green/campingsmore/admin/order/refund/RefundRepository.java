package com.green.campingsmore.admin.order.refund;

import com.green.campingsmore.entity.RefundEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefundRepository extends JpaRepository<RefundEntity,Long>, RefundRepositoryCustom {
}
