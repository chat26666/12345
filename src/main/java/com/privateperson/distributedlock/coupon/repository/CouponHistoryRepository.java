package com.privateperson.distributedlock.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.privateperson.distributedlock.coupon.model.CouponHistory;

public interface CouponHistoryRepository extends JpaRepository<CouponHistory, Long> {
}
