package com.privateperson.distributedlock.coupon.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.privateperson.distributedlock.coupon.service.CouponService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/coupons")
@RequiredArgsConstructor
public class CouponController {

	private final CouponService couponService;

	@PostMapping("/{id}")
	public ResponseEntity<Void> createCoupon(@PathVariable Long id) {

		couponService.issueCoupon(id,1L);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
