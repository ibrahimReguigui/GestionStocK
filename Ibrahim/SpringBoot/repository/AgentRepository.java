package Ibrahim.SpringBoot.repository;


import Ibrahim.SpringBoot.model.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Integer> {
    Agent readByEmail(String email);
}
