package Ibrahim.SpringBoot.service;

import Ibrahim.SpringBoot.model.Agent;
import Ibrahim.SpringBoot.repository.AgentRepository;
import Ibrahim.SpringBoot.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgentServiceImp implements AgentService {

    @Autowired
    private AgentRepository aRepo;
    @Autowired
    private RolesRepository rRepo;

    public List<Agent> getAllAgents() {
        return aRepo.findAll();
    }

    public Agent getAgentById(Integer id) {
        return aRepo.findById(id).get();
    }

    public void saveAgent(Agent agent) {
        aRepo.save(agent);
    }

    public void deleteAgent(Integer id) {
        aRepo.deleteById(id);
    }
}
