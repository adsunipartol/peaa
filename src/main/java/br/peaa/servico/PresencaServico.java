package br.peaa.servico;

import br.peaa.entidades.Evento;


public interface PresencaServico {

    void registrar (Evento event) throws Exception;
    
}
