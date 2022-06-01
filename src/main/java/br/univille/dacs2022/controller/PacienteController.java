package br.univille.dacs2022.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.univille.dacs2022.dto.PacienteDTO;
import br.univille.dacs2022.service.PacienteService;

@Controller
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private PacienteService service;

    @GetMapping
    public ModelAndView index(){

        List<PacienteDTO> listaPacientes =
            service.getAll();

        return new ModelAndView("paciente/index",
                            "listaPacientes",listaPacientes);
    }

    @GetMapping("/novo")
    public ModelAndView novo() {
        return new ModelAndView("paciente/form");
    }
}
