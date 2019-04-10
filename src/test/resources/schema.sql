create table sap_sequencenumber

(

   InterfaceName varchar(30) not null,

   Region varchar(30) not null,

   CurrentSequenceNumber int not null,

   primary key(InterfaceName, Region)

);