package edu.kpi.comsys.dis.lab.repositories;

import edu.kpi.comsys.dis.lab.entities.StoriesList;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoriesListRepository extends CrudRepository<StoriesList, Long> {

    List<StoriesList> findByTitleContaining(String title);
    @Query("SELECT DISTINCT sl FROM Dashboard d JOIN d.lists sl WHERE d.id = :dashboardId")
    List<StoriesList> findAllOnDashboard(@Param("dashboardId") Long dashboardId);

}
