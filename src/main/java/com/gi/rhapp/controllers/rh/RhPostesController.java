package com.gi.rhapp.controllers.rh;

import com.gi.rhapp.enumerations.Role;
import com.gi.rhapp.models.*;
import com.gi.rhapp.repositories.*;
import com.gi.rhapp.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/rh/api/postes")
@CrossOrigin("*")
public class RhPostesController {

    @Autowired
    private PosteRepository posteRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private SalarieRepository salarieRepository;

    @Autowired
    private DirectionRepository directionRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private AuthService authService;

    private String service = "Gestion des RH - Gestion de postes";

    @GetMapping()
    public List<Poste> getPostes() {
        return posteRepository.findAllByOrderByDateCreationDesc();
    }

    @PostMapping("/create")
    public Poste createPoste(@RequestBody Poste poste) {
        Service service = poste.getService();
        if (service.getId() == null) {
            service = serviceRepository.save(service);
            activityRepository.save(
                Activity.builder()
                    .evenement("Création d'un nouveau service : " + service.getNom())
                    .service(this.service)
                    .user(authService.getCurrentUser())
                    .scope(Role.RH)
                    .build()
            );
        }

        Direction direction = poste.getDirection();
        if (direction.getId() == null) {
            direction = directionRepository.save(direction);
            activityRepository.save(
                Activity.builder()
                    .evenement("Création d'une nouvelle direction : " + direction.getNom())
                    .service(this.service)
                    .user(authService.getCurrentUser())
                    .scope(Role.RH)
                    .build()
            );
        }
        poste = posteRepository.save(poste);
        activityRepository.save(
            Activity.builder()
                .evenement("Création d'une nouveau poste " + poste.getNom() + " dans le service de " + service.getNom())
                .service(this.service)
                .user(authService.getCurrentUser())
                .scope(Role.RH)
                .build()
        );
        return poste;
    }

    @PostMapping("/{id}/affecter")
    public Poste affecterSalarie(@PathVariable("id") Long id, @RequestBody AffectationRequest request) {
        Poste poste = posteRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Poste introuvable")
        );
        Salarie selectedSalarie = salarieRepository.findById(request.getSalarieId()).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Salarie introuvable")
        );

        // check if salarie is already affected to another poste, if it's the case it will be overwritten
        if (selectedSalarie.getPoste() != null)
            selectedSalarie.getPoste().setSalarie(null);

        selectedSalarie.setFonction(request.getFonctions());
        selectedSalarie.setDateAffectation(new Date());

        poste.setSalarie(selectedSalarie);

        poste = posteRepository.save(poste);
        activityRepository.save(
            Activity.builder()
                .evenement("Affectation de " + selectedSalarie.getUser().getPrenom() + " au poste de " + poste.getNom())
                .service(this.service)
                .user(authService.getCurrentUser())
                .scope(Role.RH)
                .build()
        );


        return poste;
    }

    @PutMapping("/{id}/salarie/supprimer")
    public Poste deleteSalarie(@PathVariable("id") Long id) {
        Poste poste = posteRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Poste introuvable")
        );
        Salarie salarie = poste.getSalarie();
        salarie.setFonction(null);
        poste.setSalarie(null);
        poste = posteRepository.save(poste);
        activityRepository.save(
            Activity.builder()
                .evenement("Deaffectation de " + salarie.getUser().getPrenom() + " du poste de " + poste.getNom())
                .service(this.service)
                .user(authService.getCurrentUser())
                .scope(Role.RH)
                .build()
        );
        return poste;
    }
}
