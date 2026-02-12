package br.com.pcdev.steps;

import br.com.pcdev.config.DriverFactory;
import br.com.pcdev.pages.PesquisaPage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.pt.*;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

public class PesquisaSteps {

    private WebDriver driver;
    private PesquisaPage page;

    @Before
    public void setup() {
        driver = DriverFactory.getDriver();
        page = new PesquisaPage(driver);
    }

    @After
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    @Dado("que acesso a página inicial do PCPDev")
    public void acessarPagina() {
        page.acessarPagina();
    }

    @Dado("abro o painel de busca avançada")
    public void abrirPainelBusca() {
        page.abrirBuscaAvancada();
    }

    @Quando("seleciono o status {string}")
    public void selecionarStatus(String status) {
        page.preencherStatus(status);
    }

    @Quando("seleciono a modalidade {string}")
    public void selecionarModalidade(String modalidade) {
        page.preencherModalidade(modalidade);
    }

    @Quando("seleciono a realização {string}")
    public void selecionarRealizacao(String realizacao) {
        page.preencherRealizacao(realizacao);
    }

    @Quando("seleciono a UF {string}")
    public void selecionarUF(String uf) {
        page.selecionarUF(uf);
    }

    @Quando("aguardo o carregamento dos municípios")
    public void aguardarMunicipios() {
        page.aguardarMunicipiosCarregarem();
    }

    @Quando("seleciono o município {string}")
    public void selecionarMunicipio(String municipio) {
        page.selecionarMunicipio(municipio);
    }

    @Quando("altero o tipo de data para {string}")
    public void alterarTipoData(String tipo) {
        page.alterarTipoData(tipo);
    }

    @Quando("clico em buscar")
    public void clicarBuscar() {
        page.clicarBuscar();
    }

    @Entao("devo visualizar a lista de resultados")
    public void validarResultados() {
        boolean tabelaVisivel = page.validarResultados();
        Assert.assertTrue("A tabela de resultados deveria estar visível", tabelaVisivel);
    }

    @Quando("informo o período de {string} a {string}")
    public void informoOPeriodo(String inicio, String fim) {
        page.preencherPeriodo(inicio, fim);
    }
    
}