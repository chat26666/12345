package com.privateperson.distributedlock.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.privateperson.distributedlock.coupon.model.Coupon;


public interface CouponRepository extends JpaRepository<Coupon, Long> {

	//@Lock(LockModeType.PESSIMISTIC_WRITE)
	default Coupon findOrThrow(Long id) {
		return findById(id).orElseThrow(() -> new RuntimeException("쿠폰없음"));
	}
}
