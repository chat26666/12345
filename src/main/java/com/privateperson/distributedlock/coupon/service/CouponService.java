package com.privateperson.distributedlock.coupon.service;

import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.privateperson.distributedlock.annotation.DistributedLock;
import com.privateperson.distributedlock.coupon.dto.CouponSaveRequest;
import com.privateperson.distributedlock.coupon.model.Coupon;
import com.privateperson.distributedlock.coupon.model.CouponHistory;
import com.privateperson.distributedlock.coupon.repository.CouponHistoryRepository;
import com.privateperson.distributedlock.coupon.repository.CouponRepository;
import com.privateperson.distributedlock.user.model.User;
import com.privateperson.distributedlock.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CouponService {

	private final CouponRepository couponRepository;
	private final UserRepository userRepository;
	private final CouponHistoryRepository couponHistoryRepository;

	@Transactional
	public void createCoupon(CouponSaveRequest request) {

		Coupon coupon = Coupon.builder()
			.discountRate(request.discountRate())
			.quantity(request.quantity())
			.build();

		couponRepository.save(coupon);
	}

	@Transactional
	@DistributedLock(key = "#couponId")
	public void issueCoupon(Long userId, Long couponId) {

		User user = userRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("유저없음"));

		Coupon coupon = couponRepository.findOrThrow(couponId);

		coupon.decreaseCoupon();

		CouponHistory couponHistory = CouponHistory.builder()
			.coupon(coupon)
			.user(user)
			.build();

		couponHistoryRepository.save(couponHistory);

	}
}

