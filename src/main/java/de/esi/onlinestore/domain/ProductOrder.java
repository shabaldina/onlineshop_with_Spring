package de.esi.onlinestore.domain;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.esi.onlinestore.domain.enumeration.OrderStatus;

/**
 * Die Klasse beschreibt Bestellungen
 */
@Entity
@Table(name = "product_order")
public class ProductOrder implements Serializable {



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductOrder)) {
            return false;
        }
        return id != null && id.equals(((ProductOrder) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }



}


