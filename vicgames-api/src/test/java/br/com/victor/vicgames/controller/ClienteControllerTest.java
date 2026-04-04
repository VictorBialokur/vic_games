package br.com.victor.vicgames.controller;

import br.com.victor.vicgames.dto.ClienteDto;
import br.com.victor.vicgames.exception.ValidacaoException;
import br.com.victor.vicgames.repository.ClienteRepository;
import br.com.victor.vicgames.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ClienteControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<ClienteDto> jsonDto;

    @MockitoBean
    private ClienteService service;

    @Mock
    private ClienteRepository repository;

    @Test
    void deveriaRetornar200ParaListarClientes() throws Exception {
        var response = mvc.perform(
                get("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaRetornar200ParaCadastrarCliente() throws Exception {
        ClienteDto dto = new ClienteDto("Teste", "11999223344", "teste@email.com", "93871469017");

        var response = mvc.perform(
                post("/clientes")
                        .content(jsonDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaRetornar400ParaCadastrarClienteComNomeInvalido() throws Exception {
        ClienteDto dto = new ClienteDto("", "11999223344", "teste@email.com", "93871469017");

        var response = mvc.perform(
                post("/clientes")
                        .content(jsonDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaRetornar400ParaCadastrarClienteComTelefoneInvalido() throws Exception {
        ClienteDto dto = new ClienteDto("Teste", "", "teste@email.com", "93871469017");

        var response = mvc.perform(
                post("/clientes")
                        .content(jsonDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaRetornar400ParaCadastrarClienteComEmailInvalido() throws Exception {
        ClienteDto dto = new ClienteDto("Teste", "11999223344", "", "93871469017");


        var response = mvc.perform(
                post("/clientes")
                        .content(jsonDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaRetornar400ParaCadastrarClienteComCpfInvalido() throws Exception {
        ClienteDto dto = new ClienteDto("Teste", "11999223344", "teste@email.com", "");

        var response = mvc.perform(
                post("/clientes")
                        .content(jsonDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaRetornar200ParaListarClientePorId() throws Exception {
        Long idCliente = 0L;
        var response = mvc.perform(
                get("/clientes/"+idCliente)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaRetornar404ParaListarClientePorId() throws Exception {
        Long idCliente = 0L;

        BDDMockito.given(service.listarClientePorId(idCliente)).willThrow(ValidacaoException.class);
        var response = mvc.perform(
                get("/clientes/"+idCliente)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(404, response.getStatus());
    }

    @Test
    void deveriaRetornar200ParaListarJogosComprados() throws Exception {
        Long idCliente = 0L;

        var response = mvc.perform(
                get("/clientes/"+idCliente+"/comprados")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaRetornar200ParaComprarJogo() throws Exception {
        var idCliente = 0L;
        var idJogo = 0L;

        var response = mvc.perform(
                put("/clientes/"+idCliente+"/comprar")
                        .content(String.valueOf(idJogo))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaRetornar400ParaComprarJogo() throws Exception {
        Long idCliente = 0L;
        Long idJogo = null;

        var response = mvc.perform(
                put("/clientes/"+idCliente+"/comprar")
                        .content(String.valueOf(idJogo))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaRetornar200ParaAlterarCliente() throws Exception {
        Long idCliente = 0L;
        ClienteDto dto = new ClienteDto("Teste", "11999223344", "teste@email.com", "93871469017");

        var response = mvc.perform(
                put("/clientes/"+idCliente)
                        .content(jsonDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaRetornar400ParaAlterarClienteComNomeInvalido() throws Exception {
        Long idCliente = 0L;
        ClienteDto dto = new ClienteDto("", "11999223344", "teste@email.com", "93871469017");

        var response = mvc.perform(
                put("/clientes/"+idCliente)
                        .content(jsonDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaRetornar200ParaAlterarClienteComTelefoneInvalido() throws Exception {
        Long idCliente = 0L;
        ClienteDto dto = new ClienteDto("Teste", "", "teste@email.com", "93871469017");

        var response = mvc.perform(
                put("/clientes/"+idCliente)
                        .content(jsonDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaRetornar200ParaAlterarClienteComEmailInvalido() throws Exception {
        Long idCliente = 0L;
        ClienteDto dto = new ClienteDto("Teste", "11999223344", "", "93871469017");

        var response = mvc.perform(
                put("/clientes/"+idCliente)
                        .content(jsonDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaRetornar200ParaAlterarClienteComCpfInvalido() throws Exception {
        Long idCliente = 0L;
        ClienteDto dto = new ClienteDto("Teste", "11999223344", "teste@email.com", "");

        var response = mvc.perform(
                put("/clientes/"+idCliente)
                        .content(jsonDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaRetornar200ParaDeletarCliente() throws Exception {
        Long idCliente = 0L;

        var response = mvc.perform(
                delete("/clientes/"+idCliente+"/delete")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaRetornar400ParaDeletarCliente() throws Exception {
        Long idCliente = null;

        var response = mvc.perform(
                delete("/clientes/"+idCliente+"/delete")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }

}