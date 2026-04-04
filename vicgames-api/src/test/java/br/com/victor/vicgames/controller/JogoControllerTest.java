package br.com.victor.vicgames.controller;

import br.com.victor.vicgames.dto.CadastroJogoDto;
import br.com.victor.vicgames.exception.ValidacaoException;
import br.com.victor.vicgames.service.JogoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class JogoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private JogoService service;

    @Autowired
    private JacksonTester<CadastroJogoDto> jsonDto;

    @Test
    void deveriaRetornar200ParaListarJogos() throws Exception {
        var response = mvc.perform(
                get("/jogos")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaRetornar200ParaListarJogoPorId() throws Exception {
        Long id = 0L;
        var response = mvc.perform(
                get("/jogos/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaRetornar404ParaListarJogoPorId() throws Exception {
        Long id = 0L;
        BDDMockito.given(service.listarJogoPorId(id)).willThrow(ValidacaoException.class);

        var response = mvc.perform(
                get("/jogos/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(404, response.getStatus());
    }

    @Test
    void deveriaRetornar200ParaCadastrarJogo() throws Exception {
        CadastroJogoDto dto = new CadastroJogoDto("Teste", 15000, "M", 10);

        var response = mvc.perform(
                post("/jogos")
                        .content(jsonDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaRetornar400ParaCadastrarJogoComNomeInvalido() throws Exception {
        CadastroJogoDto dto = new CadastroJogoDto("", 15000, "M", 10);

        var response = mvc.perform(
                post("/jogos")
                        .content(jsonDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaRetornar400ParaCadastrarJogoComClassificacaoInvalida() throws Exception {
        CadastroJogoDto dto = new CadastroJogoDto("Teste", 15000, "", 10);

        var response = mvc.perform(
                post("/jogos")
                        .content(jsonDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaRetornar400ParaCadastrarJogoComPrecoInvalido() throws Exception {
        String json = """
                {
                    "nome": "Teste",
                    "preco": null,
                    "classificacaoIndicativa": "M",
                    "quantidadeEstoque": 10
                }
                """;

        var response = mvc.perform(
                post("/jogos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaRetornar400ParaCadastrarJogoComQuantidadeInvalida() throws Exception {
        String json = """
                {
                    "nome": "Teste",
                    "preco": 15000,
                    "classificacaoIndicativa": "M",
                    "quantidadeEstoque": null
                }
                """;

        var response = mvc.perform(
                post("/jogos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaRetornar200ParaAlterarJogo() throws Exception {
        Long idJogo = 0L;
        CadastroJogoDto dto = new CadastroJogoDto("Teste", 15000, "M", 10);

        var response = mvc.perform(
                put("/jogos/"+idJogo)
                        .content(jsonDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaRetornar400ParaAlterarJogoComNomeInvalido() throws Exception {
        Long idJogo = 0L;
        CadastroJogoDto dto = new CadastroJogoDto("", 15000, "M", 10);

        var response = mvc.perform(
                put("/jogos/"+idJogo)
                        .content(jsonDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaRetornar400ParaAlterarJogoComClassificacaoInvalida() throws Exception {
        Long idJogo = 0L;
        CadastroJogoDto dto = new CadastroJogoDto("Teste", 15000, "", 10);

        var response = mvc.perform(
                put("/jogos/"+idJogo)
                        .content(jsonDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaRetornar400ParaAlterarJogoComPrecoInvalido() throws Exception {
        Long idJogo = 0L;
        String json = """
                {
                    "nome": "Teste",
                    "preco": null,
                    "classificacaoIndicativa": "M",
                    "quantidadeEstoque": 10
                }
                """;

        var response = mvc.perform(
                put("/jogos/"+idJogo)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaRetornar400ParaAlterarJogoComQuantidadeInvalida() throws Exception {
        Long idJogo = 0L;
        String json = """
                {
                    "nome": "Teste",
                    "preco": 15000,
                    "classificacaoIndicativa": "M",
                    "quantidadeEstoque": null
                }
                """;

        var response = mvc.perform(
                put("/jogos/"+idJogo)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }
}