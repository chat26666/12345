package com.privateperson.distributedlock.coupon.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "coupon")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	Double discountRate;

	//@Version
	//private Long version;

	Integer quantity;

	public void decreaseCoupon() {
		quantity--;
	}
}
