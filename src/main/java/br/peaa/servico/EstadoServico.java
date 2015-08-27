package br.peaa.servico;

import br.peaa.entidades.Estado;
import java.util.List;


public interface EstadoServico {
   
    Estado buscar(Long codigo);

    List<Estado> buscarTodos();

}
