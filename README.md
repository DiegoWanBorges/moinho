[![NPM](https://img.shields.io/npm/l/react)](https://github.com/DiegoWanBorges/moinho/blob/main/LICENSE) [![Netlify Status](https://api.netlify.com/api/v1/badges/f0905887-1259-4cec-8d65-01a0fa121428/deploy-status)](https://app.netlify.com/sites/moinho/deploys)

# Moinho - Controle de produção e apuração de custo
### Moinho é uma aplicação web para controle de produção e apuração de custo.

## Cadastro [![YouTube Video Views](https://img.shields.io/youtube/views/GY9IN5eLZmA?label=Cadastro%20Web&style=social)](https://www.youtube.com/watch?v=GY9IN5eLZmA) [![YouTube Video Views](https://img.shields.io/youtube/views/zDfWIWJTCrM?label=Cadastro%20Mobile&style=social)](https://www.youtube.com/watch?v=zDfWIWJTCrM)

A rotina de cadastro é composta pelas seguintes funcionalidades:
- Parâmetros - Responsável por configurações  gerais do sistema.
- Usuários - Cadastro de usuários e permissão de acesso.
- Grupos - Cadastro de grupos de produtos.
- Unidades - Cadastro das unidades do produto.
- Produto - Cadastro do produto.
- Setores - Cadastro dos setores dos funcionários.
- Funcionários - Cadastro de funcionários.
- Custo funcionário - Cadastro dos centros de custos de funcionários.
- Custo operacional - Cadastro do centro de custo operacional.
- Ocorrências - Cadastro de ocorrências relacionadas aos consumos que excedem a formulação.
- Status Pallet - Cadastro que determina o status do produto acabado.

## Formulação [![YouTube Video Views](https://img.shields.io/youtube/views/5g2lOgRidjM?label=Formulação%20Web&style=social)](https://www.youtube.com/watch?v=5g2lOgRidjM) [![YouTube Video Views](https://img.shields.io/youtube/views/gkfm7Wzv3dg?label=Formulação%20Mobile&style=social)](https://www.youtube.com/watch?v=gkfm7Wzv3dg)
  O cadastro da formulação é a base para o controle e qualidade de cada produção e, consequentemente, a apuração de custo, pois a partir dela serão geradas as quantidades dos ingredientes (insumos, matéria-prima, etc..) que deverão ser utilizados em cada ordem de produção. É a partir da formulação que também configuramos os rateios, vinculamos a ela os setores dos funcionários que participam do processo produtivo e os centros de custos operacionais, por exemplo, energia elétrica, custos relacionados à limpeza, etc.

## Ordem de Produção [![YouTube Video Views](https://img.shields.io/youtube/views/YHMtW24M-rE?label=O.P%20Web&style=social)](https://www.youtube.com/watch?v=YHMtW24M-rE) [![YouTube Video Views](https://img.shields.io/youtube/views/Z00XlzhXMu0?label=O.P%20Mobile&style=social)](https://www.youtube.com/watch?v=Z00XlzhXMu0)
Para cada produto produzido, deve ter uma ordem de produção. Para gerar a OP, informa-se a quantidade que se espera produzir. Levando como base a formulação, o sistema irá gerar a quantidade dos ingredientes necessários para se produzir a quantidade esperada. Caso ocorra alguma perda de ingredientes, deve-se gerar um consumo complementar dentro da produção, informando o motivo do consumo que excede a formulação.  
A informação sobre o que foi produzido será separada em “pallets”,  para cada “pallet” será impressa uma etiqueta, que terá as informações de data de fabricação, validade, ordem de produção de origem, lote e status, este último, informará a liberação ou não do que foi produzido, levando em consideração o controle de qualidade.

## Estoque [![YouTube Video Views](https://img.shields.io/youtube/views/sz3MOZjUyW0?label=Estoque%20Web&style=social)](https://www.youtube.com/watch?v=sz3MOZjUyW0) [![YouTube Video Views](https://img.shields.io/youtube/views/uDIC1kJJ-gM?label=Estoque%20Mobile&style=social)](https://www.youtube.com/watch?v=uDIC1kJJ-gM)
A rotina de estoque irá listar toda a movimentação, podendo ter o controle de custo médio por data, bastando apenas filtrar o produto desejado.

## Pagamentos [![YouTube Video Views](https://img.shields.io/youtube/views/8BTScSh1Kxg?label=Pagamento%20Web&style=social)](https://www.youtube.com/watch?v=8BTScSh1Kxg) [![YouTube Video Views](https://img.shields.io/youtube/views/5uzq5yeBMvo?label=Pagamento%20Mobile&style=social)](https://www.youtube.com/watch?v=5uzq5yeBMvo)
Rotina responsável pelo cadastro das despesas que serão rateadas junto à produção. É separada em dois grupos:  
Mão de obra – Todo custo referente a funcionários.  
Custos Operacionais – Demais custos indiretos que possuem algum vínculo com a produção.

## Apuração [![YouTube Video Views](https://img.shields.io/youtube/views/_KmpXqdByIQ?label=Apuração%20Web&style=social)](https://www.youtube.com/watch?v=_KmpXqdByIQ) [![YouTube Video Views](https://img.shields.io/youtube/views/zDd2qCZBUNk?label=Apuração%20Mobile&style=social)](https://www.youtube.com/watch?v=zDd2qCZBUNk)
A rotina de apuração é responsável pela consolidação de todas as informações. Seleciona-se um período e o sistema irá realizar o cálculo por ordem de produção, levando em consideração todo custo direto (ingredientes) e indireto (custo de mão de obra e custos operacionais) que foram absorvidos pela produção. Ao final, será possível ter o custo médio de produção do período e o custo médio geral.

# Tecnologias utilizadas
## Implantação em produção
- Back end: Heroku
- Front web: Netlify
- Banco de dados: Postgresql
## Back end
- Java
- Spring Boot
- JPA / Hibernate
- Jasper Reports
- OAuth 2
- Maven
## Front end
- HTML / CSS / JS / TypeScript
- ReactJS



# Autor

Diego Wandrofski Borges

https://www.linkedin.com/in/diego-wandrofski-borges-93a5a059/
