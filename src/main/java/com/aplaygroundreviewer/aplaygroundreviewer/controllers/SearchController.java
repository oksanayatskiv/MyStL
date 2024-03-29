package com.aplaygroundreviewer.aplaygroundreviewer.controllers;

import com.aplaygroundreviewer.aplaygroundreviewer.models.Playground;
import com.aplaygroundreviewer.aplaygroundreviewer.models.data.PlaygroundDao;
import com.aplaygroundreviewer.aplaygroundreviewer.models.forms.SearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;

@Controller
public class SearchController {

    @Autowired
    private PlaygroundDao playgroundDao;

    static ArrayList<Playground> playgrounds = new ArrayList<>();

    @RequestMapping(value = "")
    public String index(Model model) {
        model.addAttribute(new SearchForm());
        return "index";
    }

    //refactored search method for the routes to also work with the search bar.
    @RequestMapping(value="search")
    public String search(Model model){
        model.addAttribute(new SearchForm());
        return"search";
    }

    @RequestMapping(value="results")
    public String search(Model model, @ModelAttribute SearchForm searchForm){
        model.addAttribute("aname", searchForm.getName());
        model.addAttribute("anotherlistmodel", playgroundDao.findByNameContainingOrDescriptionContaining(searchForm.getName(), searchForm.getName()));
        return "search-results";
    }

    /*
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String index(Model model, SearchForm searchForm) {
        String name = searchForm.getName();
        return "redirect:/search?term=" + name;
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public String displaySearch(Model model, HttpServletRequest request) {
        String name = request.getParameter("term");
        model.addAttribute("aname", name);
        model.addAttribute("anotherlistmodel",playgroundDao.findByNameContainingOrDescriptionContaining(name, name));
        return "search-results";
    }*/

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddPlaygroundForm(Model model) {
        model.addAttribute("title", "Add a Playground Object");
        model.addAttribute(new SearchForm());
        model.addAttribute(new Playground());
        return "add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddPlaygroundForm(@ModelAttribute @Valid Playground newPlayground, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Playground");
            return "add";
        }
        playgroundDao.save(newPlayground);
        return "redirect:view/" + newPlayground.getId();
    }


    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemovePlaygroundForm(Model model) {
        model.addAttribute(new SearchForm());
        model.addAttribute("playgrounds", playgroundDao.findAll());
        model.addAttribute("title", "Remove Playground");
        return "remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemovePlaygroundForm(@RequestParam int[] playgroundIds) {
        for (int playgroundId : playgroundIds) {
            playgroundDao.delete(playgroundId);
        }
        return "redirect:";
    }


    //Viewing a Playground's details by ID in URL
    @RequestMapping(value="view/{id}", method = RequestMethod.GET)
    public String viewPlayground(Model model,
                                 @PathVariable int id){
        Playground playground = playgroundDao.findOne(id);
        model.addAttribute(new SearchForm());
        model.addAttribute("playground", playground);
        model.addAttribute("title", "View a Playground");
        return "view";
    }

    //Returns all playgrounds
    @RequestMapping(value="list")
    public String listPlayground(Model model){
        model.addAttribute(new SearchForm());
        model.addAttribute("playgrounds", playgroundDao.findAll());
        model.addAttribute("title", "All Playgrounds");
        return "list";
    }

}
