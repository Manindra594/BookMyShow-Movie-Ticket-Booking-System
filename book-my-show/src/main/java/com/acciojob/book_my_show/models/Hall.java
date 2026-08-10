package com.acciojob.book_my_show.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Halls")
@Entity
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID sysId;
    @Column(unique = true,nullable = false)
    private String hallId;
    private String hallName;
    private String rowRange; //A-G
    private Integer seatCapacity;
    @ManyToOne
    private Theater theater;
    private LocalDateTime createdAt;
    private  LocalDateTime updatedAt;
    private  String createdBy;
    private  String updatedBy;
}
