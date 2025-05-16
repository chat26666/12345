package com.privateperson.distributedlock.coupon.dto;

public record CouponSaveRequest(

	Double discountRate,

	Integer quantity

) {
}
