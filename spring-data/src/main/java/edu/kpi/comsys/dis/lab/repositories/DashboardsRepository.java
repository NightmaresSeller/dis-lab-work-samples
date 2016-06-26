package edu.kpi.comsys.dis.lab.repositories;

import edu.kpi.comsys.dis.lab.entities.Dashboard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DashboardsRepository extends CrudRepository<Dashboard, Long> {

    List<Dashboard> findByTitleContaining(String title);
    List<Dashboard> findByUserId(Long userId);
    @Query("SELECT DISTINCT d FROM User u JOIN u.sharedDashboards d WHERE u.id = :userId")
    List<Dashboard> findAllSharedWithUser(@Param("userId") Long userId);

}
