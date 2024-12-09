package com.moysha.jsftest.entity;

import jakarta.enterprise.context.SessionScoped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Session;

import java.io.Serializable;

@Getter
@Setter
@Entity
@ToString
@Table(name = "points")
@SessionScoped
public class Points implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double x;

    @Column(nullable = false)
    private double y;

    @Column(nullable = false)
    private double r = 1;

    @Column(nullable = false)
    private boolean status;

    @Column(nullable = false)
    private String sessionId;
}

