create table INVENTORY(
ID bigint AUTO_INCREMENT,
NAME VARCHAR(255),
PRICE NUMERIC(19,2),
QTY INTEGER
)

create table ORDERS(
ID bigint AUTO_INCREMENT,
TOTAL NUMERIC(19,2)
)

create table ORDER_ITEM(
ORDER_ID INTEGER,
ITEM_ID INTEGER,
QTY INTEGER
)