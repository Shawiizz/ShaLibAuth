<div align="center">
  <h1>ShaLibAuth 0.1</h1>

### Java library to authenticate a Minecraft Java account to Mojang servers and get infos about it (Uuid, Token, ClientToken, Username) ! This also support offline mode (crack accounts)
_This shouldn't have any bug but if you have one you can report it._
</div>

**Download**
------
Click [here](https://github.com/Shawiizz/ShaLibAuth/releases/download/shalibauth/ShaLibAuth_0.1.jar) to download ShaLibAuth !

**Code**
------
This code is to authenticate a Minecraft account in online mode (to mojang servers) :
> ShaLibAuth.login("email", "password", AuthType.MOJANG);
<br>

And for the offline mode (crack account) :
> ShaLibAuth.login("email", "password", AuthType.CRACK);

And it's all ! You can after get infos of the logged account using these String variables in ShaLibAuth class :
- username
- token
- uuid
- clientToken
<br>
(clientToken isn't set with crack mode)
<br>
ShaLibAuth.login() is a boolean and return if the login succeeded or failed. If false, the login failed, if true, the login is successful.
