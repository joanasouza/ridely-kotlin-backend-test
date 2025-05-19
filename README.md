# Desafio Backend Kotlin - Projeto Ridely

## 🌟 Objetivo do Desafio

Este desafio tem como base o projeto **Ridely**, um aplicativo de transporte de passageiros por taxi criado para atender a demanda crescente por mobilidade urbana em Aracaju. O candidato deve analisar o código existente e implementar melhorias e novas funcionalidades com foco em **escalabilidade**, **performance** e **cálculos baseados em geolocalização**.

O objetivo é avaliar sua capacidade de:

* Ler e entender um projeto Kotlin já em funcionamento
* Implementar novas funcionalidades
* Refatorar e melhorar a estrutura existente
* Garantir performance e escalabilidade em cenários de alta demanda
* Projeto [PROJECT-SETTINGS](PROJECT-SETTINGS.md)

---

## 🚀 Novas Funcionalidades Obrigatórias

### 1. Cálculo de tempo e distância baseado em rota

Ao solicitar uma corrida, deve ser feito o cálculo do tempo estimado e da distância total entre o ponto de origem e o ponto de destino, utilizando a **API do Google Maps**:

* Endpoint sugerido: `GET https://maps.googleapis.com/maps/api/directions/json`
* A resposta deve conter:

  * `tempo_estimado_minutos`
  * `distancia_km`
  * `preco_estimado` (veja monetização abaixo)

### 2. Alocação do motorista mais próximo

Implementar uma lógica que, ao solicitar a corrida, encontre o motorista mais próximo da localização do passageiro, baseado em **latitude/longitude**. Utilize:

* Cálculo de distância via Haversine formula (pode ser local ou API do Google Maps)
* Retorne os 3 motoristas mais próximos (caso queira mostrar opções)

### 3. Cálculo automático de preço

Com base nos dados da rota:

* Valor por km: **R\$ 3,00**
* Valor por minuto: **R\$ 2,00**
* Exemplo: 10 km e 15 minutos = R\$ 30 + R\$ 30 = **R\$ 60,00**
* Monetização: Simular taxa de 1% para o app

---

## 🚫 Restrições e Validações

* **Sem uso de taxímetro**
* **Sem reprocessamento de corridas canceladas**
* As corridas devem ser persistidas com os dados do passageiro, origem, destino, motorista alocado e preço final

---

## 🧰 Cenário de Escalabilidade

A arquitetura atual suporta 10% da frota (200 motoristas). Sua proposta deve considerar:

* Como escalar para suportar **10.000 requisições por minuto**?
* O que você faria para otimizar:

  * Banco de dados
  * Threads/processamento concorrente
  * Cache para estimativas frequentes
  * Indexações geoespaciais (sugestão: PostGIS, MongoDB, Redis GEO)

Inclua essa avaliação em um arquivo `SCALABILITY.md` explicando suas escolhas.

---

## ⚖️ Critérios de Avaliação

* Clareza e organização do código
* Domínio da linguagem Kotlin
* Estrutura de pastas, separação de responsabilidades (Clean Architecture é um plus)
* Performance da lógica de localização
* Escalabilidade e segurança das soluções
* Testes unitários e/ou integrados (preferencialmente usando JUnit + Mockk)
* Logs e tratamento de erros

---

## 🏢 Contexto Real

* Aracaju possui cerca de **30.000 corridas diárias**
* A frota do app crescerá de 200 para 2000 motoristas nos próximos meses
* O app deve funcionar bem em regiões de **alta densidade demográfica**
* Usuários relatam cancelamento por falta de previsão de chegada do motorista

---

## 📆 Entrega

1. **Fork do repositório original:** [ridely-kotlin-backend-test](https://github.com/jaya/ridely-kotlin-backend-test)
2. **Crie uma branch com seu nome** em snake\_case (ex: `joao_silva_souza`)
3. **Suba seu código com commits organizados**
4. **Abra um Pull Request** para a branch `main` com:

   * Título: `Entrega - joao_silva_souza`
   * Corpo: Nome completo, data da entrega e observações se desejar

---

## 🎓 Licença

Este desafio é baseado no projeto Ridely e deve ser usado apenas para fins de avaliação técnica e educacional.

---

## 📢 Contato

* Autor original: Leandro Costa
* Avaliação técnica por: Equipe de Engenharia Jaya Tech
