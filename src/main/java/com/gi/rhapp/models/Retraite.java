package com.gi.rhapp.models;

import com.gi.rhapp.enumerations.EtatRetraite;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@DynamicUpdate
public class Retraite  {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dateRetraite, dateValidation;

    private String remarques;

    @Enumerated(EnumType.STRING)
    private EtatRetraite etat;

    @CreationTimestamp
    private Date dateCreation;

    @UpdateTimestamp
    private Date dateModification;

    @ManyToOne
    private TypeRetraite type;

    @OneToOne
    private Salarie salarie;

    //just for test
    public Retraite(Date dateRetraite , Date dateValidation , Salarie salarie) {
        this.dateCreation=dateRetraite;
        this.dateValidation = dateValidation;
        this.salarie=salarie;
    }
}
