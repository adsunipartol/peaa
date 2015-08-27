package br.peaa.servico;

import br.peaa.entidades.Evento;
import br.peaa.repositorio.EventoRepository;
import br.peaa.repositorio.PresencaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("presencaServico")
@Transactional(readOnly = false)
public class PresencaServicoImpl implements PresencaServico {
    
    @Autowired
    private EventoRepository repositorioEvento;
    
    
    @Autowired
    private PresencaRepository repositorioPresenca;
    
    @Override
    @Transactional(readOnly = false)
    public void registrar(Evento event) {
        try {
            if (event == null) {
                throw new Exception("Não foi encontrada pessoa com matricula ");
            }
            if (event.getPessoas() == null){
                throw new Exception ("Pessoa não encontrada");
            }
            repositorioEvento.save(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
