package com.accenture.controller.advice;

import com.accenture.exception.AdminException;
import com.accenture.exception.ClientException;
import com.accenture.model.ErreurReponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationControllerAdvice {
    @ExceptionHandler(ClientException.class)
    public ResponseEntity<ErreurReponse> gestionClientException(ClientException ex){
        ErreurReponse er = new ErreurReponse(LocalDateTime.now(), "Erreur fonctionnelle", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
    }

    @ExceptionHandler(AdminException.class)
    public ResponseEntity<ErreurReponse> gestionAdminException(AdminException ex){
        ErreurReponse er = new ErreurReponse(LocalDateTime.now(), "Erreur fonctionnelle", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErreurReponse> entityNotFoundException(EntityNotFoundException ex){
        ErreurReponse er = new ErreurReponse(LocalDateTime.now(), "Mauvaise Requete", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(er);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErreurReponse> problemeValidation(MethodArgumentNotValidException ex){
        String message = ex.getBindingResult().getAllErrors()
                .stream()
                .map(objectError -> objectError.getDefaultMessage())
                .collect(Collectors.joining(", "));
        ErreurReponse er = new ErreurReponse(LocalDateTime.now(), "Validation erreur", message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErreurReponse> problemeDuplication(DataIntegrityViolationException ex){
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
