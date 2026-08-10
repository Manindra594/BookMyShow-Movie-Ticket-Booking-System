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
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Shows")
@Entity
public class Show implements Comparable<Show> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID sysId;
    @Column(unique = true)
    private  String showId;
    private  Double showPrice;
    private  String showName;
    private  String movieName;
    @ManyToOne
    private  Hall hall;
    private Long startTimeInSeconds;
    private Long endTimeInSeconds;
    private LocalDateTime startTime;
    private  LocalDateTime endTime;
    private LocalDateTime createdAt;
    private  LocalDateTime updatedAt;
    private  String createdBy;
    private  String updatedBy;

    public int compareTo(Show b) {
        return Math.toIntExact(this.startTimeInSeconds - b.getStartTimeInSeconds());
    }
}
