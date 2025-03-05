package com.accenture.controller.advice;

import com.accenture.exception.*;
import com.accenture.model.ErreurReponse;
import com.accenture.repository.entity.location.Location;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ApplicationControllerAdvice {
    /**
     * <p>La méthode <code>gestionUtilisateurException</code>gère toutes les exceptions de type `UtilisateurException`.</p>
     *
     * @param ex L'exception de type `UtilisateurException` capturée.
     * @return Une réponse HTTP contenant un statut HTTP BAD_REQUEST(400) et les détails de l'erreur.
     */
    @ExceptionHandler(UtilisateurException.class)
    public ResponseEntity<ErreurReponse> gestionUtilisateurException(UtilisateurException ex){
        log.error("Erreur fonctionnelle : {}", ex.getMessage(), ex);
        ErreurReponse er = new ErreurReponse(LocalDateTime.now(), "Erreur fonctionnelle", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
    }


    /**
     * <p>La méthode <code>gestionVehiculeException</code> gère toutes les exceptions de type `VehiculeException`.</p>
     *
     * @param ex L'exception de type `VehiculeException` capturée.
     * @return Une réponse HTTP contenant un statut HTTP BAD_REQUEST(400) et les détails de l'erreur.
     */

    @ExceptionHandler(VehiculeException.class)
    public ResponseEntity<ErreurReponse> gestionVehiculeException(VehiculeException ex){
        log.error("Erreur fonctionnelle : {}", ex.getMessage(), ex);
        ErreurReponse er = new ErreurReponse(LocalDateTime.now(), "Erreur fonctionnelle", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
    }

    /**
     * <p>La méthode <code>gestionLocationException</code> gère toutes les exceptions de type `LocationException`.</p>
     *
     * @param ex L'exception de type `LocationException` capturée.
     * @return Une réponse HTTP contenant un statut HTTP BAD_REQUEST(400) et les détails de l'erreur.
     */

    @ExceptionHandler(LocationException.class)
    public ResponseEntity<ErreurReponse> gestionLocationException(LocationException ex){
        log.error("Erreur fonctionnelle : {}", ex.getMessage(), ex);
        ErreurReponse er = new ErreurReponse(LocalDateTime.now(), "Erreur fonctionnelle", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
    }

    /**
     * <p>La méthode <code>entityNotFoundException</code> gère les exceptions de type `EntityNotFoundException`.</p>
     *
     * @param ex L'exception de type `EntityNotFoundException` capturée.
     * @return  Une réponse HTTP contenant un statut HTTP NOT_FOUND(404) et les détails de l'erreur.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErreurReponse> entityNotFoundException(EntityNotFoundException ex){
        log.error("Mauvaise requête : {}", ex.getMessage(), ex);
        ErreurReponse er = new ErreurReponse(LocalDateTime.now(), "Mauvaise requête", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(er);
    }

    /**
     * <p>La méthode <code>problemeValidation</code> gère les exceptions de type `MethodArgumentNotValidException`.</p>
     *
     * @param ex L'exception de type `MethodArgumentNotValidException` capturée.
     * @return Une réponse HTTP contenant un statut HTTP BAD_REQUEST (400) et les détails de l'erreur de validation.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErreurReponse> problemeValidation(MethodArgumentNotValidException ex){
        log.error("Validation erreur : {}", ex.getMessage(), ex);
        String message = ex.getBindingResult().getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        ErreurReponse er = new ErreurReponse(LocalDateTime.now(), "Validation erreur", message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
    }

    /**
     * <p>La méthode <code>problemeDuplication</code> gère les exceptions de type `DataIntegrityViolationException`.</p>
     *
     * @param ex L'exception de type `DataIntegrityViolationException` capturée.
     * @return Une réponse HTTP contenant un statut HTTP INTERNAL_SERVER_ERROR (500) et les détails de l'erreur.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErreurReponse> problemeDuplication(DataIntegrityViolationException ex){
        log.error("Duplication problème : {}", ex.getMessage(), ex);
        String message = ex.getMessage();
        if (message.contains("duplicate key value violates unique constraint")) {
            message = "L'email est déjà utilisé, veuillez choisir un autre.";
        } else {
            message = "Erreur de contrainte de base de données.";
        }
        ErreurReponse er = new ErreurReponse(LocalDateTime.now(), "Duplication problème", message);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
    }


}

