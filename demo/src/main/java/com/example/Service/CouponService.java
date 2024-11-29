package com.example.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.Entity.CartItem;
import com.example.Entity.Coupon;
import com.example.Exception.CouponNotFoundException;
import com.example.Repository.CouponRepository;

@Service
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public Coupon createCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    public Coupon getCouponById(Long id) {
        return couponRepository.findById(id).orElseThrow(() -> new CouponNotFoundException("Coupon not found!"));
    }

    public Coupon updateCoupon(Long id, Coupon updatedCoupon) {
        Coupon existingCoupon = getCouponById(id);
        existingCoupon.setName(updatedCoupon.getName());
        existingCoupon.setType(updatedCoupon.getType());
        existingCoupon.setDiscountDetails(updatedCoupon.getDiscountDetails());
        existingCoupon.setConditions(updatedCoupon.getConditions());
        existingCoupon.setRepetitionLimit(updatedCoupon.getRepetitionLimit());
        existingCoupon.setExpirationDate(updatedCoupon.getExpirationDate());
        return couponRepository.save(existingCoupon);
    }

    public void deleteCoupon(Long id) {
        Coupon coupon = getCouponById(id);
        couponRepository.delete(coupon);
    }

    public List<Coupon> getApplicableCoupons(List<CartItem> cartItems) {
        // Fetch all active and non-expired coupons
        List<Coupon> coupons = couponRepository.findAll()
            .stream()
            .filter(coupon -> coupon.isActive() && coupon.getExpirationDate().isAfter(LocalDate.now()))
            .collect(Collectors.toList());

        // Filter coupons that are applicable to the cart items
        return coupons.stream()
            .filter(coupon -> isCouponApplicable(coupon, cartItems))
            .collect(Collectors.toList());
    }

    public Map<String, Object> applyCoupon(Long couponId, List<CartItem> cartItems) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new CouponNotFoundException("Coupon not found!"));

        if (!isCouponApplicable(coupon, cartItems)) {
            throw new IllegalArgumentException("Coupon conditions are not met!");
        }

        double totalDiscount = calculateDiscount(coupon, cartItems);
        double totalCartPrice = cartItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        Map<String, Object> response = Map.of(
                "cartItems", cartItems,
                "totalCartPrice", totalCartPrice,
                "discountApplied", totalDiscount,
                "finalPrice", totalCartPrice - totalDiscount
        );

        return response;
    }

    private boolean isCouponApplicable(Coupon coupon, List<CartItem> cartItems) {
        // Add logic to evaluate if the coupon can be applied
        return true; // Placeholder for implementation
    }

    private double calculateDiscount(Coupon coupon, List<CartItem> cartItems) {
        // Add logic for discount calculation
        return 10.0; // Placeholder for implementation
    }
}