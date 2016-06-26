package edu.kpi.comsys.dis.lab.repositories;

import edu.kpi.comsys.dis.lab.entities.Dashboard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DashboardsRepository extends CrudRepository<Dashboard, Long> {

    List<Dashboard> findByTitleContaining(String title);

}
