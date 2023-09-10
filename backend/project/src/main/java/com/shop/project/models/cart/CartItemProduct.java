package com.shop.project.models.cart;

import com.shop.project.models.Product;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "cart_item_product")
public class CartItemProduct implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "cart_item_id")
    private CartItem cartItem;
    @Id
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;
}
