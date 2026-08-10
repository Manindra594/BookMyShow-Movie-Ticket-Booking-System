package com.acciojob.book_my_show.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Theaters")
@Entity
public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID sysId;
    @Column(unique = true)
    private  String TheaterId;
    private  String theatername;
    private  String city;
    private String state;
    private String country;
    private  String address;
    @ManyToOne
    private User owner;
    @OneToMany
    private List<Hall> halls;
    private LocalDateTime createdAt;
    private  LocalDateTime updatedAt;
    private  String createdBy;
    private  String updatedBy;
}
