package com.taifex.smartorder.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders") // 避免和 SQL 保留字衝突
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB 自動產生 id
    private Long id;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // 關聯：多個訂單對應一個使用者
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
