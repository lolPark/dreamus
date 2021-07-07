package com.dreamus.lolpark.purchase.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productId")
    private Product product; //상품

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user; //사용자

    @Column(nullable = false, length = 100)
    private Integer price; //가격

    @UpdateTimestamp
    private LocalDateTime updatedAt;    //업데이트 일자

    @CreationTimestamp
    private LocalDateTime createdAt;    //생성 일자
}
