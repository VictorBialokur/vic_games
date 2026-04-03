package br.com.victor.vicgames.controller;

import br.com.victor.vicgames.dto.ClienteDto;
import br.com.victor.vicgames.dto.ListarClientesDto;
import br.com.victor.vicgames.dto.ListarJogoDto;
import br.com.victor.vicgames.exception.ValidacaoException;
import br.com.victor.vicgames.service.ClienteService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ListarClientesDto>> listarClientes() {
        List<ListarClientesDto> clientes = this.clienteService.listarClientes();
        return ResponseEntity.ok(clientes);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrarCliente(@RequestBody @Valid ClienteDto dto) {
        try {
            this.clienteService.cadastrarCliente(dto);
            return ResponseEntity.ok().build();
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDto> listarClientePorId(@PathVariable("id") Long idCliente) {
        try {
            ClienteDto cliente = this.clienteService.listarClientePorId(idCliente);
            return ResponseEntity.ok(cliente);
        } catch (ValidacaoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/comprados")
    public ResponseEntity<List<ListarJogoDto>> listarJogosComprados(@PathVariable("id") Long idCliente) {
        List<ListarJogoDto> jogosComprados = this.clienteService.listarJogosComprados(idCliente);
        return ResponseEntity.ok(jogosComprados);
    }

    @PutMapping("/{id}/comprar")
    @Transactional
    public ResponseEntity<String> comprarJogo(@PathVariable("id") Long idCliente, @RequestBody @Valid Long idJogo) {
        try {
            this.clienteService.comprarJogo(idCliente, idJogo);
            return ResponseEntity.ok().build();
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<String> alterarCliente(@PathVariable("id") Long idCliente, @RequestBody @Valid ClienteDto dto) {
        try {
            this.clienteService.alterarCliente(idCliente, dto);
            return ResponseEntity.ok().build();
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/delete")
    @Transactional
    public ResponseEntity<String> deletarCliente(@PathVariable("id") Long idCliente) {
        try {
            this.clienteService.deletarCliente(idCliente);
            return ResponseEntity.ok().build();
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
