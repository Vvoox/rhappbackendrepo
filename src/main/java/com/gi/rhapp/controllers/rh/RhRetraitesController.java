package com.gi.rhapp.controllers.rh;


import com.gi.rhapp.models.Absence;
import com.gi.rhapp.models.AvantageNat;
import com.gi.rhapp.models.Retraite;
import com.gi.rhapp.models.Salarie;
import com.gi.rhapp.repositories.*;
import com.gi.rhapp.services.MailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/rh/api/retraites")
@CrossOrigin("*")
public class RhRetraitesController {

    @Autowired
    private SalarieRepository salarieRepository;

    @Autowired
    private AbsenceRepository absenceRepository;

    @Autowired
    private RetraiteRepository retraiteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AvantageNatRepository avantageNatRepository;

    @Autowired
    private MailService mailService;


    //    **************************************************************************************************************************************************
    //    *********************************************** API get all "retraites" ******************************************************************

    @GetMapping() //works
    public List<Retraite> getRetraites(){
            return  retraiteRepository.findAll();

    }

    //    **************************************************************************************************************************************************
    //    *********************************************** API get "retraite"  by id  ******************************************************************

    @GetMapping(value = "/{id}") // works
    public Retraite getOneRetraite(@PathVariable(value = "id")Long id){
        try{
            return retraiteRepository.findById(id).get();

        }catch (NoSuchElementException e){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "Le retraite avec id = " + id + " est introuvable.");

        }
    }

    //    **************************************************************************************************************************************************
    //    *********************************************** API add "retraite"  ******************************************************************

    @PostMapping(value = "/ajouter") //works
    public Retraite addRetraite(@RequestBody Retraite retraite){

       return retraiteRepository.save(retraite);

    }

    //    **************************************************************************************************************************************************
    //    *********************************************** API modify "retraite"   ******************************************************************

    @PutMapping(value = "/{id}/modifier" ) //not yet
    @Transactional
    public ResponseEntity<?> addRetraite(@PathVariable(value = "id")Long id , @RequestBody Retraite retraite){
        try{
            retraite.setId(id);
            return ResponseEntity.ok(retraiteRepository.save(retraite));

        }catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le retraite avec id = " + id + " est introuvable.");

        }
    }
}
