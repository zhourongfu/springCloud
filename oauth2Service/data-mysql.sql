INSERT INTO T_AUTH2_CLIENT(ID,CLIENT_ID,CLIENT_SECRET,SCOPES,GRANT_TYPES,ACCESS_TOKEN_VALIDITY_SECONDS,REFRESH_TOKEN_VALIDITY_SECONDS)
VALUES
(1, 'acme',   'acmesecret',  'read,write', 'password,refresh_token',          180, 3600),
(2, 'sdfaesa','aasdf5#%asdf','read,write', 'client_credentials,refresh_token',200, 4000);

INSERT INTO T_USER(ID,USERNAME,USERPWD,LOCKED,ENABLE,ACCOUNTEXPIRED)
 VALUES (1, 'admin', 'weilus', 0, 0, 0);
