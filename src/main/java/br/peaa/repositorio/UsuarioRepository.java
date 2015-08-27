package br.peaa.repositorio;

import br.peaa.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    
    Usuario findByLogin (String login);


}
