package br.univille.dacs2022.service;

import java.util.List;

import br.univille.dacs2022.dto.PacienteDTO;

public interface PacienteService {
    public List<PacienteDTO> getAll();
}
