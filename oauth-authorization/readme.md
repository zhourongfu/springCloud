curl -X POST
-H "Authorization: Basic YWNtZTphY21lc2VjcmV0"
-F "grant_type=password"
-F "client_id=acme"
-F "username=liutaiq"
-F "password=123321"
"http://192.168.198.1:8080/oauth/token"


curl -d 'grant_type=authorization_code&client_id=a10086&scope=user_info&redirect_uri=http://aa.ccdd&code=pg4Vz2'  \
 http://a10086:aabcc@192.168.100.3:8080/oauth/token