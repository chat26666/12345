package com.privateperson.distributedlock.coupon.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponAsyncService {

	private final CouponService couponService;

	@Async
	public CompletableFuture<Void> issueCouponAsync(Long userId, Long couponId) {
		couponService.issueCoupon(userId, couponId);
		return CompletableFuture.completedFuture(null);
	}
}
