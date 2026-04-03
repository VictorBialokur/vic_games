package br.com.victor.vicgames.controller;

import br.com.victor.vicgames.dto.CadastroJogoDto;
import br.com.victor.vicgames.dto.JogoDto;
import br.com.victor.vicgames.exception.ValidacaoException;
import br.com.victor.vicgames.service.JogoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jogos")
public class JogoController {
    @Autowired
    private JogoService jogoService;

    @GetMapping
    public ResponseEntity<List<JogoDto>> listarJogos() {
        List<JogoDto> jogos = jogoService.listarJogos();
        return ResponseEntity.ok(jogos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JogoDto> listarJogoPorId(@PathVariable("id") Long idJogo) {
            try {
                JogoDto jogo = this.jogoService.listarJogoPorId(idJogo);
                return ResponseEntity.ok(jogo);
            } catch (ValidacaoException e) {
                return ResponseEntity.notFound().build();
            }
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrarJogo(@RequestBody @Valid CadastroJogoDto dto) {
        try {
            this.jogoService.cadastrarJogo(dto);
            return ResponseEntity.ok().build();
        } catch (ValidacaoException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<String> alterarJogo(@PathVariable("id") Long idJogo, @RequestBody @Valid CadastroJogoDto dto) {
        try {
            this.jogoService.alterarJogo(idJogo, dto);
            return ResponseEntity.ok().build();
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
