package com.gi.rhapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.hibernate.annotations.CreationTimestamp;


import javax.persistence.*;
import javax.validation.constraints.Email;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Builder
public class Salarie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(unique = true)
    private Long numSomme;

    @Column(unique = true)
    private String cin ;

    private Long salaire;

    private String telephone, adresse;
    private String image , cv;

    private Date dateNaissance ;
    private String lieuNaissance ;


    private Date dateAffectation;

    @CreationTimestamp
    private Date dateCreation;

    @UpdateTimestamp
    private Date dateUpdate;

    private String diplomeObt;

    private String fonction;

    private String cinUrg , nomUrg  , prenomUrg , adresseUrg;

    @Email
    private String emailUrg ;
    private Long solde;

    @ManyToOne
    private Direction direction;

    @ManyToOne
    private Service service;

    @OneToMany(mappedBy = "salarie", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"salarie"})
    private List<Absence> absences;

    @OneToOne(mappedBy = "salarie", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"salarie"})
    private Poste poste;

    @OneToOne
    @JsonIgnoreProperties({"salarie", "id", "dateCreation", "dateModification"})
    @JsonUnwrapped
    private User user;

    @OneToMany(mappedBy = "salarie")
    @JsonIgnoreProperties({"salarie"})
    private List<Conge> conges;

    @OneToOne(mappedBy = "salarie", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"salarie"})
    private Retraite retraite;

    @OneToMany(mappedBy = "salarie", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"salarie"})
    private Collection<AvantageNat> avantages;

    public Salarie(String cin, String prenom, String nom, Date date, String lieuNaissance, String fonction) {
        this.cin=cin;
        this.dateNaissance=date;
        this.lieuNaissance=lieuNaissance;
        this.fonction=fonction;
    }
}
