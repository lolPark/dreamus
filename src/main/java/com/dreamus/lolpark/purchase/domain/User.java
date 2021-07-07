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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String name;

    @UpdateTimestamp
    private LocalDateTime updatedAt;    //업데이트 일자

    @CreationTimestamp
    private LocalDateTime createdAt;    //생성 일자
}
