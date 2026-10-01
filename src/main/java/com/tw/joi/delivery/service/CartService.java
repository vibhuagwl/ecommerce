package com.tw.joi.delivery.service;

import com.tw.joi.delivery.domain.Cart;
import com.tw.joi.delivery.domain.GroceryProduct;
import com.tw.joi.delivery.domain.User;
import com.tw.joi.delivery.dto.request.AddProductRequest;
import com.tw.joi.delivery.dto.response.CartProductInfo;
import com.tw.joi.delivery.exception.NotFoundException;
import com.tw.joi.delivery.seedData.SeedData;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CartService {

    private final Map<String, Cart> userCarts = SeedData.cartForUsers;
    private final UserService userService;
    private final ProductService productService;

    public CartProductInfo addProductToCartForUser(AddProductRequest addProductRequest) throws BadRequestException {
        User user = userService.fetchUserById(addProductRequest.getUserId());
        Cart cart = fetchCartForUser(user);
        GroceryProduct product = productService.getProduct(addProductRequest.getProductId(),
                addProductRequest.getOutletId());
        if (product == null) {
            throw new NotFoundException("Product not found" + addProductRequest.getProductId());
        }
        if (product.getStore() == null) {
            throw new BadRequestException("Product is not sold at any outlet");
        }

        if (cart.getOutlet() != null && !cart.getOutlet()
                .getOutletId()
                .equals(product.getStore()
                        .getOutletId())) {
            throw new BadRequestException("Product is not sold at the selected outlet");
        }
        if (product.isOutOfStock()) {
            throw new BadRequestException("Product is out of stock");
        }

        product.setAvailableStock(product.getAvailableStock() - 1);

        if (cart.getOutlet() == null) {
            cart.setOutlet(product.getStore());
        }

        BigDecimal sellingPrice = product.calculateSellingPrice();


        cart.getProducts()
                .add(product);
        return new CartProductInfo(cart, product, product.getSellingPrice());
    }

    public Cart getCartForUser(String userId) {
        User user = userService.fetchUserById(userId);
        return fetchCartForUser(user);
    }

    private Cart fetchCartForUser(User user) {
        return userCarts.get(user.getUserId());
    }

}
