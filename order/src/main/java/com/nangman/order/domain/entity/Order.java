package com.nangman.order.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "p_orders")
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID supplierId;

    @Column(nullable = false)
    private UUID receiverId;

    @Column(nullable = false)
    private UUID productId;

    private UUID deliveryId;

    @Column(nullable = false)
    private Integer productQuantity;

    @Column(length = 50)
    private String requestMessage;

    public void updateAll(UUID supplierId,
                          UUID receiverId,
                          UUID productId,
                          UUID deliveryId,
                          Integer productQuantity,
                          String requestMessage) {
        this.supplierId = supplierId;
        this.receiverId = receiverId;
        this.productId = productId;
        this.deliveryId = deliveryId;
        this.productQuantity = productQuantity;
        this.requestMessage = requestMessage;
    }

    public void updateDeliveryId(UUID deliveryId) {
        this.deliveryId = deliveryId;
    }

}
