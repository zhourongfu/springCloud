create table  T_USER(
ID int(11) not null primary key auto_increment,
USERNAME varchar(50),
USERPWD varchar(50),
LOCKED   int(1) default 0,
ENABLE int(1) default 0,
ACCOUNTEXPIRED int(1) default 0
);

create table T_AUTH2_CLIENT(
ID int(11) not null primary key auto_increment,
CLIENT_ID varchar(50),
CLIENT_SECRET varchar(50),
ACCESS_TOKEN_VALIDITY_SECONDS int(5),
REFRESH_TOKEN_VALIDITY_SECONDS int(5),
GRANT_TYPES varchar(50),
SCOPES varchar(50)
);