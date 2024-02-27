package com.hsbc.demo.restursnt.reservation.entities;

import lombok.*;

import jakarta.persistence.*;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Builder
public class CustomerBooking {
        @Id
        @GeneratedValue
        private Long id;
        @NonNull
        private String customerName;
        private int tableSize;
        @NonNull
        private LocalDate bookDate;
        private Float bookTime;
}
