# LocationApp
La Api de la Aplicacion esta alojada en heroku
su url base es const val BASE_URL = "https://diegoandroidapi.herokuapp.com/api/auth/"
por ejemplo esta url obtiene todas las evaluaciones "https://diegoandroidapi.herokuapp.com/api/auth/evaluations/"
Como es un servicio gratuito puede que las peticiones al servidor sean lentas.
la aplicacion funciona actualmente solo con internet, por cuestiones de tiempo no se pudo manejar el cacheo de datos con ROOM, por ejemplo,
La aplicacion se realizo con courutinas, view model y navigation components, aunque el manejo de hilos no esta optmizado al 100
las credenciales de acceso son:

 "email": "testing@hotmail.com",
  "password": "12345678"
  
  Una build de la app en formato apk esta localizado en MyApplication\app\build\outputs\apk\debug

