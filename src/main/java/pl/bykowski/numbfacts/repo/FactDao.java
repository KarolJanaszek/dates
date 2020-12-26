package pl.bykowski.numbfacts.repo;

import pl.bykowski.numbfacts.model.Fact;

import java.util.List;

public interface FactDao {
    List<Fact> getAllFact();
    Fact getFactById(Long id);
    void saveFact(Fact fact);
    void editFactText(Fact fact);
    void deleteFactById(Long id);
}
