package com.gi.rhapp.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Poste  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @ElementCollection
    private List<String> competences = new ArrayList<>();

    @CreationTimestamp
    private Date dateCreation;

    @UpdateTimestamp
    private Date dateModification;

    private String division;

    @OneToOne
    @JsonIgnoreProperties({ "poste" })
    private Salarie salarie;

    @ManyToOne
    @JsonIgnoreProperties({ "salaries", "postes" })
    private Service service;

    @ManyToOne
    @JsonIgnoreProperties({ "salaries", "postes" })
    private Direction direction;
}
