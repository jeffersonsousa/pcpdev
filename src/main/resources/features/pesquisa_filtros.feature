# language: pt
# coding: utf-8

Funcionalidade: Validar Filtros de Pesquisa do PCPDev
  Como um usuário do sistema
  Quero testar todas as combinações de filtros
  Para garantir que a busca retorna os resultados corretos

  Contexto:
    Dado que acesso a página inicial do PCPDev
    E abro o painel de busca avançada

    @cenario1
    # CT001: Validação de todos os status
  Cenário: Filtrar processos por Status
    Quando seleciono o status "<status>"
    E clico em buscar
    Entao devo visualizar a lista de resultados
    
    Exemplos:
      | status                 |
      | Em Andamento           |
      | Em Republicação        |
      | Finalizado             |
      | Iminência de deserto   |
      | Recebendo Propostas    |

    @cenario2
    # CT002: Validação das principais Modalidades
  Cenário: Filtrar processos por Modalidade
    Quando seleciono a modalidade "<modalidade>"
    E clico em buscar
    Entao devo visualizar a lista de resultados

    Exemplos:
      | modalidade                            |
      | Chamada Pública da Agricultura Familiar|
      | Concorrência                          |
      | Concurso                              |
      | Dispensa                              |
      | Leilão                                |
      | Pregão                                |
      | Regime Diferenciado de Contratação    |

    @cenario3
    # CT003: Validação por Tipo de Realização
  Cenário: Filtrar por Realização
    Quando seleciono a realização "<realizacao>"
    E clico em buscar
    Entao devo visualizar a lista de resultados

    Exemplos:
      | realizacao         |
      | Eletrônico         |
      | Presencial         |
      | Simples informação |

    @cenario4
    # CT004: Validação de Dependência (UF carrega Município)
  Cenário: Buscar por UF e Município (Caso Brasília)
    Quando seleciono a UF "DF"
    E aguardo o carregamento dos municípios
    E seleciono o município "Brasília"
    E clico em buscar
    Entao devo visualizar a lista de resultados

    @cenario5
    # CT005: Validação do Tipo de Data (Abertura vs Publicação)
  Cenário: Alternar tipo de data no filtro de período
    Quando altero o tipo de data para "<tipo>"
    E clico em buscar
    Entao devo visualizar a lista de resultados

    Exemplos:
      | tipo       |
      | Abertura   |
      | Publicação |

    @cenario6
    # CT006: Validação por Tipo de Julgamento (Novo)
  Cenário: Filtrar processos por Tipo de Julgamento
    Quando seleciono o julgamento "<julgamento>"
    E clico em buscar
    Entao devo visualizar a lista de resultados

    Exemplos:
      | julgamento               |
      | Menor Preço              |
      | Maior Desconto           |
      | Técnica e Preço          |
      | Conteúdo Artístico       |

    @cenario7
    # CT007: Busca por Período Específico
  Cenário: Buscar processos publicados em um intervalo de datas
    Quando informo o período de "08/02/2026" a "11/02/2026"
    E clico em buscar
    Entao devo visualizar a lista de resultados

    @cenario8
  # CT008: O Teste de Ouro - Todos os Filtros Juntos (Atualizado)
  Cenário: Busca refinada com combinação total de filtros
    Quando seleciono o status "Em Andamento"
    E seleciono a modalidade "Pregão"
    E seleciono a realização "Eletrônico"
    E seleciono o julgamento "Menor Preço"  
    E seleciono a UF "DF"
    E aguardo o carregamento dos municípios
    E seleciono o município "Brasília"
    E informo o período de "08/02/2026" a "11/02/2026"
    E clico em buscar
    Entao devo visualizar a lista de resultados
