package pl.bykowski.numbfacts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.bykowski.numbfacts.model.Fact;
import pl.bykowski.numbfacts.repo.FactDao;
import pl.bykowski.numbfacts.service.FactService;

@Controller
public class FactController {

    private FactDao factDao;
    private FactService factService;

    @Autowired
    public FactController(FactDao factDao, FactService factService) {
        this.factDao = factDao;
        this.factService = factService;
    }

    @GetMapping
    public String getFacts(Model model) {
        Fact fact = factService.getRandomFact();

        model.addAttribute("randomFact", fact);
        model.addAttribute("facts", factDao.getAllFact());

        return "index";
    }

    @PostMapping
    public String saveFact(@ModelAttribute Fact randomFact) {
        factDao.saveFact(randomFact);
        return "redirect:/";
    }

    @GetMapping("/delete/{factId}")
    public String deleteFact(@PathVariable Long factId) {
        factDao.deleteFactById(factId);
        return "redirect:/";
    }

    @GetMapping("/edit/{factId}")
    public String editText(@PathVariable Long factId, Model model) {
        model.addAttribute("editedFact", factDao.getFactById(factId));
        return "edit";
    }

    @PostMapping("/edit/{factId}")
    public String editTextPost(@ModelAttribute Fact editedFact) {
        factDao.editFactText(editedFact);
        return "redirect:/";
    }
}
