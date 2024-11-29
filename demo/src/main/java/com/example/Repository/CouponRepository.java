package com.example.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Entity.Coupon;

@Repository
public interface CouponRepository  extends JpaRepository<Coupon,Long>{

}
