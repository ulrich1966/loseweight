package de.juli.loseweight.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.juli.loseweight.model.Consumption;
import de.juli.loseweight.model.Model; 

@Repository
public interface ConsumptionDataRepository<T extends Model<T>> extends JpaRepository<T, Long>{

}
