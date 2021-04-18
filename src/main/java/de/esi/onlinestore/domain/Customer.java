package de.esi.onlinestore.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import de.esi.onlinestore.domain.enumeration.Gender;
import de.esi.onlinestore.exceptions.BadRequestException;
import org.springframework.http.ResponseEntity;

/**
 * Die Klasse beschreibt Kunden.
 */
@Entity
@Table(name = "customer")
public class Customer implements Serializable {


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }



}
