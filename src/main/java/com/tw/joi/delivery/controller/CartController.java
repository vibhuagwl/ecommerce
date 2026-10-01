package com.tw.joi.delivery.controller;

import com.tw.joi.delivery.domain.Cart;
import com.tw.joi.delivery.dto.request.AddProductRequest;
import com.tw.joi.delivery.dto.response.CartProductInfo;
import com.tw.joi.delivery.service.CartService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/product")
    public ResponseEntity<CartProductInfo> addProductToCart(
            @RequestBody AddProductRequest addProductRequest) throws BadRequestException {
        return ResponseEntity.ok(cartService.addProductToCartForUser(addProductRequest));
    }

    @GetMapping("/view")
    public ResponseEntity<Cart> viewCart(@RequestParam(name = "userId") String userId) {
        return ResponseEntity.ok(cartService.getCartForUser(userId));
    }
}
