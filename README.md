[![NPM](https://img.shields.io/npm/l/react)](https://github.com/DiegoWanBorges/moinho/blob/main/LICENSE)

# Moinho - Controle de produção e apuração de custo.
## Cadastro
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

## Formulação
  O cadastro da formulação é a base para o controle e qualidade de cada produção e, consequentemente, a apuração de custo, pois a partir dela serão geradas as quantidades dos ingredientes (insumos, matéria-prima, etc..) que deverão ser utilizados em cada ordem de produção. É a partir da formulação que também configuramos os rateios, vinculamos a ela os setores dos funcionários que participam do processo produtivo e os centros de custos operacionais, por exemplo, energia elétrica, custos relacionados à limpeza, etc.

## Ordem de Produção
  Para cada produto produzido, deve ter uma ordem de produção. Para gerar a OP, informa-se a quantidade que se espera produzir. Levando como base a formulação, o sistema irá gerar a quantidade dos ingredientes necessários para se produzir a quantidade esperada. Caso ocorra alguma perda de ingredientes, deve-se gerar um consumo complementar dentro da produção, informando o motivo do consumo que excede a formulação.
	A informação sobre o que foi produzido será separada em “pallets”,  para cada “pallet” será impressa uma etiqueta, que terá as informações de data de fabricação, validade, ordem de produção de origem, lote e status, este último, informará a liberação ou não do que foi produzido, levando em consideração o controle de qualidade.

## Estoque
	A rotina de estoque irá listar toda a movimentação, podendo ter o controle de custo médio por data, bastando apenas filtrar o produto desejado.

## Pagamentos
	Rotina responsável pelo cadastro das despesas que serão rateadas junto à produção. É separada em dois grupos:
	 	Mão de obra – Todo custo referente a funcionários.
	          	Custos Operacionais – Demais custos indiretos que possuem algum vínculo com a produção.
## Apuração
	A rotina de apuração é responsável pela consolidação de todas as informações. Seleciona-se um período e o sistema irá realizar o cálculo por ordem de produção, levando em consideração todo custo direto (ingredientes) e indireto (custo de mão de obra e custos operacionais) que foram absorvidos pela produção. Ao final, será possível ter o custo médio de produção do período e o custo médio geral.

# Tecnologias utilizadas
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
