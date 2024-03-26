package com.flinter.shop.member;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(unique = true)
    private String username;

    private String password;

    private String displayName;


}
