package pl.bykowski.numbfacts.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.bykowski.numbfacts.model.Fact;

@Service
public class FactServiceImpl implements FactService {
    @Override
    public Fact getRandomFact() {
        RestTemplate restTemplate = new RestTemplate();
        Fact fact = restTemplate.getForObject("http://numbersapi.com/random/year?json", Fact.class);
        return fact;
    }
}
