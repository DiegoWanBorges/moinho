package com.twokeys.moinho.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.twokeys.moinho.entities.FormulationItems;
import com.twokeys.moinho.entities.pk.FormulationItemsPK;

@Repository
public interface FormulationItemsRepository extends JpaRepository<FormulationItems, FormulationItemsPK> {
		
}

