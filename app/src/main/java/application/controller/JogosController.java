package application.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


import application.model.Jogos;
import application.repository.JogosRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;



@RestController
@RequestMapping("/jogos")
public class JogosController {
        @Autowired
        private JogosRepository jogosRepo;

        @GetMapping
        public Iterable<Jogos> list(){
            return jogosRepo.findAll();
        }

        @PostMapping
        public Jogos insert(@RequestBody Jogos al){
            return jogosRepo.save(al);
        }

        @GetMapping("/{id}")
        public Jogos details(@PathVariable long id){
            Optional<Jogos> resultado = jogosRepo.findById(id);
            if (resultado.isEmpty()) {
                throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,"Jogos não encontrados"
                );
            }
            return jogosRepo.findById(id).get();
        }

        @PutMapping("/{id}")
        public Jogos put(
            @PathVariable long id,
            @RequestBody Jogos novosDados){

            Optional<Jogos> resultado =  jogosRepo.findById(id);
            if (resultado.isEmpty()) {
                throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Jogos não encontrados"
                );
            }
            if(novosDados.getDescricao().isEmpty()){
                throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,"Descriçao invalida"
                );
            }
            if (novosDados.getConcluido() == null) {
                throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "O campo 'concluido' deve ser verdadeiro ou falso."
                );
            }

            Jogos jogosExistente = resultado.get();
            jogosExistente.setDescricao(novosDados.getDescricao());
            jogosExistente.setConcluido(novosDados.getConcluido());

            return jogosRepo.save(resultado.get());
        }

        @DeleteMapping("/{id}")
        public void delete(@PathVariable long id){
            if(!jogosRepo.existsById(id)){
                throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,"Tarefa não encontrada"
                );
            }
            jogosRepo.deleteById(id);
        }
    
}