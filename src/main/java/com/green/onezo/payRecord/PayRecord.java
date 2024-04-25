package com.green.onezo.payRecord;


import com.green.onezo.pay.Pay;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payRecord_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pay_id")
    private Pay pay;


}