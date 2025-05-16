package com.privateperson.distributedlock.coupon.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.privateperson.distributedlock.user.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "coupon_history")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@CreationTimestamp
	@Column(updatable = false)
	LocalDateTime issuedAt;

	@UpdateTimestamp
	LocalDateTime usedAt;

	@Column(name = "is_used")
	private Boolean isUsed;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	User user;

	@ManyToOne
	@JoinColumn(name = "coupon_id", nullable = false)
	Coupon coupon;
}
