INSERT INTO user (name,email,password) VALUES ('Diego Wandrofski Borges','diego@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');


INSERT INTO role (authority) VALUES ('ROLE_ADMIN');
INSERT INTO role (authority) VALUES ('ROLE_OPERATOR');
INSERT INTO role (authority) VALUES ('ROLE_VISITOR');

INSERT INTO user_role (user_id, role_id) VALUES (1, 1);

INSERT INTO unity(id,description)VALUES('UN','UNIDADE');
INSERT INTO unity(id,description)VALUES('KG','KILO');
INSERT INTO unity(id,description)VALUES('L','LITRO');
INSERT INTO unity(id,description)VALUES('CX','CAIXA');

INSERT INTO line(name)VALUES('INSUMO');
INSERT INTO line(name)VALUES('MATERIA PRIMA');
INSERT INTO line(name)VALUES('EMBALAGEM');
INSERT INTO line(name)VALUES('USO E CONSUMO');
INSERT INTO line(name)VALUES('PRODUTO ACABADO');

INSERT INTO product(description, gross_weight,name,net_weight,packaging,line_id,unity_id,validity_days)VALUES('Leite in natura',1.032,'LEITE IN NATURA',1.032,'',2,'L',0);
INSERT INTO product(description, gross_weight,name,net_weight,packaging,line_id,unity_id,validity_days)VALUES('Saco utilizado para envase de leite em pó da marca Moinho',0.700,'SACO MOINHO 25 KG',0.700,'',3,'UN',0);
INSERT INTO product(description, gross_weight,name,net_weight,packaging,line_id,unity_id,validity_days)VALUES('Saco utilizado para envase de leite em pó',0.700,'SACO 25 KG PARDO',0.700,'',3,'UN',0);
INSERT INTO product(description, gross_weight,name,net_weight,packaging,line_id,unity_id,validity_days)VALUES('Abraçadeira utilizada para vedar o saco interno de leite',0.1,'ABRACADEIRA DE NYLON',0.1,'',3,'UN',0);
INSERT INTO product(description, gross_weight,name,net_weight,packaging,line_id,unity_id,validity_days)VALUES('Linha de poliester utilizada para para costurar a embalagem externa',1,'LINHA DE POLIESTER',1,'',3,'KG',0);  
INSERT INTO product(description, gross_weight,name,net_weight,packaging,line_id,unity_id,validity_days)VALUES('Leite em pó integral',1,'LEITE EM PO',1,'SC COM 25KG',5,'KG',365);

INSERT INTO STOCK_MOVEMENT(date,description,id_orign_movement,type,product_id,cost,in,out)VALUES(TIMESTAMP WITH TIME ZONE '2021-04-01T20:50:07.12345Z','',0,5,1,1.20,50000,0);
INSERT INTO STOCK_MOVEMENT(date,description,id_orign_movement,type,product_id,cost,in,out)VALUES(TIMESTAMP WITH TIME ZONE '2021-04-10T20:50:07.12345Z','',0,5,1,1.25,10000,0);
INSERT INTO STOCK_MOVEMENT(date,description,id_orign_movement,type,product_id,cost,in,out)VALUES(TIMESTAMP WITH TIME ZONE '2021-04-15T20:50:07.12345Z','',0,5,1,1.05,100500,0);
INSERT INTO STOCK_MOVEMENT(date,description,id_orign_movement,type,product_id,cost,in,out)VALUES(TIMESTAMP WITH TIME ZONE '2021-04-25T20:50:07.12345Z','',0,5,1,1.35,5000,0);
INSERT INTO STOCK_MOVEMENT(date,description,id_orign_movement,type,product_id,cost,in,out)VALUES(TIMESTAMP WITH TIME ZONE '2021-04-05T20:50:07.12345Z','',0,5,2,0.90,1000,0);
INSERT INTO STOCK_MOVEMENT(date,description,id_orign_movement,type,product_id,cost,in,out)VALUES(TIMESTAMP WITH TIME ZONE '2021-04-10T20:50:07.12345Z','',0,5,2,0.91,500,0);
INSERT INTO STOCK_MOVEMENT(date,description,id_orign_movement,type,product_id,cost,in,out)VALUES(TIMESTAMP WITH TIME ZONE '2021-04-16T20:50:07.12345Z','',0,5,2,0.93,1500,0);
INSERT INTO STOCK_MOVEMENT(date,description,id_orign_movement,type,product_id,cost,in,out)VALUES(TIMESTAMP WITH TIME ZONE '2021-04-26T20:50:07.12345Z','',0,5,2,0.95,100,0);
INSERT INTO STOCK_MOVEMENT(date,description,id_orign_movement,type,product_id,cost,in,out)VALUES(TIMESTAMP WITH TIME ZONE '2021-04-05T20:50:07.12345Z','',0,5,3,0.90,1000,0);
INSERT INTO STOCK_MOVEMENT(date,description,id_orign_movement,type,product_id,cost,in,out)VALUES(TIMESTAMP WITH TIME ZONE '2021-04-10T20:50:07.12345Z','',0,5,3,0.91,500,0);
INSERT INTO STOCK_MOVEMENT(date,description,id_orign_movement,type,product_id,cost,in,out)VALUES(TIMESTAMP WITH TIME ZONE '2021-04-16T20:50:07.12345Z','',0,5,3,0.93,1500,0);
INSERT INTO STOCK_MOVEMENT(date,description,id_orign_movement,type,product_id,cost,in,out)VALUES(TIMESTAMP WITH TIME ZONE '2021-04-30T20:50:07.12345Z','',0,5,3,0.95,100,0);
INSERT INTO STOCK_MOVEMENT(date,description,id_orign_movement,type,product_id,cost,in,out)VALUES(TIMESTAMP WITH TIME ZONE '2021-04-02T20:50:07.12345Z','',0,5,4,0.15,1000,0);
INSERT INTO STOCK_MOVEMENT(date,description,id_orign_movement,type,product_id,cost,in,out)VALUES(TIMESTAMP WITH TIME ZONE '2021-04-05T20:50:07.12345Z','',0,5,4,0.13,100,0);
INSERT INTO STOCK_MOVEMENT(date,description,id_orign_movement,type,product_id,cost,in,out)VALUES(TIMESTAMP WITH TIME ZONE '2021-04-05T20:50:07.12345Z','',0,5,5,19,100,0);


INSERT INTO FORMULATION (COEFFICIENT,DESCRIPTION,PRODUCT_ID) VALUES(1000,'LEITE EM PO',6);
INSERT INTO FORMULATION (COEFFICIENT,DESCRIPTION,PRODUCT_ID) VALUES(1000,'TESTE DE FORMULAÇÃO',6);  


INSERT INTO FORMULATION_ITEMS(FORMULATION_ID,PRODUCT_ID,QUANTITY,ROUND,TYPE)VALUES(1,1,8400,0,0);
INSERT INTO FORMULATION_ITEMS(FORMULATION_ID,PRODUCT_ID,QUANTITY,ROUND,TYPE)VALUES(1,2,40,1,0);
INSERT INTO FORMULATION_ITEMS(FORMULATION_ID,PRODUCT_ID,QUANTITY,ROUND,TYPE)VALUES(1,3,40,1,1);
INSERT INTO FORMULATION_ITEMS(FORMULATION_ID,PRODUCT_ID,QUANTITY,ROUND,TYPE)VALUES(1,4,40,1,0);
INSERT INTO FORMULATION_ITEMS(FORMULATION_ID,PRODUCT_ID,QUANTITY,ROUND,TYPE)VALUES(1,5,0.36,0,0);
