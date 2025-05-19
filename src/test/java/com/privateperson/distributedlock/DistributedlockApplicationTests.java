package com.privateperson.distributedlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.ActiveProfiles;

import com.privateperson.distributedlock.coupon.model.Coupon;
import com.privateperson.distributedlock.coupon.repository.CouponHistoryRepository;
import com.privateperson.distributedlock.coupon.repository.CouponRepository;
import com.privateperson.distributedlock.coupon.service.CouponAsyncService;
import com.privateperson.distributedlock.coupon.service.CouponService;
import com.privateperson.distributedlock.user.model.Role;
import com.privateperson.distributedlock.user.model.User;
import com.privateperson.distributedlock.user.repository.UserRepository;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EnableAsync
class DistributedlockApplicationTests {

	@Autowired
	CouponService couponService;

	@Autowired
	CouponAsyncService asyncService;

	@Autowired
	CouponRepository couponRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CouponHistoryRepository couponHistoryRepository;

	@BeforeEach
	void setUp() {
		List<User> batch = new ArrayList<>();
		for (int i = 1; i <= 10000; i++) {
			String nick = String.valueOf(i);
			batch.add(User.builder()
				.email("email" + i)
				.password("1234")
				.name(nick)
				.role(Role.USER)
				.build());
			if (batch.size() == 1000) {
				userRepository.saveAll(batch);
				userRepository.flush();
				batch.clear();
			}
		}
		if (!batch.isEmpty()) {
			userRepository.saveAll(batch);
		}
		couponRepository.save(Coupon.builder()
			.discountRate(0.5)
			.quantity(10000)
			.build());
	}

	@Test
	void issueCouponTest() {

		/*
		List<CompletableFuture<Void>> futures = IntStream.range(1, 10001)
			.mapToObj(i -> asyncService.issueCouponAsync((long)i, 1L))
			.toList();

		CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
		 */

	}

}