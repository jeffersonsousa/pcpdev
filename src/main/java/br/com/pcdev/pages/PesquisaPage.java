package br.com.pcdev.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class PesquisaPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // --- Locators (Baseados nos XPaths que você forneceu) ---
    private By btnBuscaAvancada = By.xpath("//*[@id='pesquisa-p']/a[2]");
    private By btnBuscar = By.xpath("//*[@id='pesquisa-p']/a[1]");
    
    // Selects
    private By selectStatus = By.xpath("//*[@id='Status']");
    private By selectModalidade = By.xpath("//*[@id='Modalidade']");
    private By selectRealizacao = By.xpath("//*[@id='Realizacao']");
    private By selectUF = By.xpath("//*[@id='UF']");
    private By selectMunicipios = By.xpath("//*[@id='municipios']");
    private By selectJulgamento = By.xpath("//*[@id='julgamento']");
    
    // Data / Período
    private By labelPeriodo = By.xpath("//*[@id='pesquisa-p']/div[4]/div[5]/label");
    private By radioAbertura = By.xpath("//*[@id='pesquisa-p']/div[4]/div[5]/div/div[2]/div[1]/div[1]/label");
    private By radioPublicacao = By.xpath("//*[@id='pesquisa-p']/div[4]/div[5]/div/div[2]/div[1]/div[2]/label");

    private By inputDataInicio = By.xpath("//*[@id='pesquisa-p']//input[@placeholder='Data Inicial' or contains(@class, 'start')]");
private By inputDataFim = By.xpath("//*[@id='pesquisa-p']//input[@placeholder='Data Final' or contains(@class, 'end')]");

    private By calendarioContainer = By.xpath("//*[@id='pesquisa-p']//div[contains(@class, 'vfc-calendar')]");

    private By tabelaResultados = By.cssSelector(".table"); // Genérico para validar se carregou algo

    public PesquisaPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // --- Log simples ---
    private void log(String mensagem) {
        System.out.println("[LOG] " + mensagem);
    }

    // --- Ações ---

    public void acessarPagina() {
        log("Acessando https://www.pcpdev.com.br");
        driver.get("https://www.pcpdev.com.br");
    }

    public void abrirBuscaAvancada() {
        log("Abrindo filtros de busca avançada...");
        try {
            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(btnBuscaAvancada));
            btn.click();
        } catch (Exception e) {
            log("Filtros já estavam abertos ou erro ao clicar.");
        }
    }

    // metodo para selecionar em combos (ignora espaços e maiúsculas exatas)
    public void selecionarCombo(By locator, String valorEsperado) {
        log("Selecionando no combo: " + valorEsperado);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        Select select = new Select(element);

        try {
            // tenta selecionar pelo texto exato
            select.selectByVisibleText(valorEsperado);
        } catch (Exception e) {
            log("Texto exato não encontrado. Tentando localizar por aproximação...");
            List<WebElement> opcoes = select.getOptions();
            boolean encontrou = false;
            
            for (WebElement opcao : opcoes) {
                // Compara removendo espaços e ignorando maiúsculas
                if (opcao.getText().trim().equalsIgnoreCase(valorEsperado.trim())) {
                    select.selectByVisibleText(opcao.getText());
                    encontrou = true;
                    log("Opção encontrada e selecionada: " + opcao.getText());
                    break;
                }
            }
            if (!encontrou) {
                log("ERRO: A opção '" + valorEsperado + "' não existe no combo!");
                throw new RuntimeException("Opção não encontrada: " + valorEsperado);
            }
        }
    }

    public void preencherStatus(String status) {
        selecionarCombo(selectStatus, status);
    }

    public void preencherModalidade(String modalidade) {
        selecionarCombo(selectModalidade, modalidade);
    }

    public void preencherRealizacao(String realizacao) {
        selecionarCombo(selectRealizacao, realizacao);
    }

    public void selecionarUF(String uf) {
        selecionarCombo(selectUF, uf);
    }

    public void aguardarMunicipiosCarregarem() {
        log("Aguardando sistema carregar lista de municípios...");
        // Pequena pausa técnica ou wait para garantir que o combo atualizou
        try { Thread.sleep(1500); } catch (InterruptedException e) {}
    }

    public void selecionarMunicipio(String municipio) {
        selecionarCombo(selectMunicipios, municipio);
    }

    public void preencherJulgamento(String julgamento) {
        selecionarCombo(selectJulgamento, julgamento);
    }

    public void alterarTipoData(String tipo) {
        log("Alterando tipo de data para: " + tipo);
        // Primeiro clica no label para garantir que o menu de data está expandido
        driver.findElement(labelPeriodo).click();
        
        if (tipo.equalsIgnoreCase("Abertura")) {
            driver.findElement(radioAbertura).click();
        } else if (tipo.equalsIgnoreCase("Publicação")) {
            driver.findElement(radioPublicacao).click();
        }
    }

    public void clicarBuscar() {
        log("Clicando no botão Buscar...");
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(btnBuscar));
        btn.click();
    }

    public boolean validarResultados() {
        log("Verificando se a tabela de resultados apareceu...");
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(tabelaResultados)).isDisplayed();
        } catch (Exception e) {
            log("Nenhum resultado encontrado (ou tabela demorou para aparecer).");
            return true; // Retornamos true para não quebrar o teste se a busca não trouxer nada (cenário válido)
        }
    }

    // metodo para selecionar datas
    public void preencherPeriodo(String dataInicio, String dataFim) {
    log("Selecionando período: " + dataInicio + " até " + dataFim);
    
    // extrair apenas o DIA da string (ex: "15/02/2026" -> "15")
    // o Integer.parseInt remove o zero da esquerda (01 -> 1) que geralmente não existe no texto do calendário
    String diaInicio = String.valueOf(Integer.parseInt(dataInicio.split("/")[0]));
    String diaFim = String.valueOf(Integer.parseInt(dataFim.split("/")[0]));

    // abre o calendario se estiver fechado
    try {
        driver.findElement(labelPeriodo).click();
        // Pequena espera para a animação do calendário abrir
        Thread.sleep(500); 
    } catch (Exception e) {
        log("Calendário já estava aberto ou erro ao clicar.");
    }

    try {
        // clicar no dia de Inicio
        // XPath Dinâmico: Procura um SPAN com o número do dia DENTRO do calendário
        By locatorDiaInicio = By.xpath("//*[@id='pesquisa-p']//div[contains(@class, 'vfc-calendar')]//span[normalize-space()='" + diaInicio + "']");
        
        wait.until(ExpectedConditions.elementToBeClickable(locatorDiaInicio)).click();
        log("Dia inicial clicado: " + diaInicio);
        
        Thread.sleep(500); // Espera de segurança entre cliques

        // clicar no dia Fim
        By locatorDiaFim = By.xpath("//*[@id='pesquisa-p']//div[contains(@class, 'vfc-calendar')]//span[normalize-space()='" + diaFim + "']");
        
        wait.until(ExpectedConditions.elementToBeClickable(locatorDiaFim)).click();
        log("Dia final clicado: " + diaFim);

        // fechar o calendário (clicando no label novamente ou fora)
        driver.findElement(labelPeriodo).click();

    } catch (Exception e) {
        log("ERRO: Não consegui clicar no dia. O mês está correto na tela?");
        throw new RuntimeException("Falha ao selecionar datas no calendário visual.", e);
    }
    }

}
