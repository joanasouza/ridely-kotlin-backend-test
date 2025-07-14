## Contexto 

10.000 acessos por minuto, 167 requisições por segundo
O app deve funcionar bem em regiões de alta densidade demográfica
Usuários relatam cancelamento por falta de previsão de chegada do motorista
Picos de acesso: Horário de pico, 7h às 9h e 17h às 19h

## Solução Proposta
Orquestração com Kubernetes para escalar horizontalmente
Consultar apenas motoristas disponíveis e próximos ao local de partida, para não carregar toda a frota
Cache de estimativas de preço e tempo em rotas mais consultadas 
Banco de dados distribuído para suportar alta concorrência (PostgreSQL com extensão PostGIS ou MongoDB)
Indexes geoespaciais para consultas rápidas por localização

## Tecnologias Sugeridas
- Docker: containerização de serviços
- Kubernetes: orquestração e escalabilidade
- PostgreSQL + PostGIS ou MongoDB: dados geoespaciais, consultas rápidas
- Redis: cache de estimativas e geolocalização