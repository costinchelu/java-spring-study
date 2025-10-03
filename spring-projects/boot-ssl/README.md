## SSL

For description: [Spring Boot SSL (HTTPS) examples](https://mkyong.com/spring-boot/spring-boot-ssl-https-examples/)

[keytool command documentation](https://docs.oracle.com/javase/9/tools/keytool.htm#JSWOR-GUID-5990A2E4-78E3-47B7-AE75-6D1826259549)

[Different types of key stores (article)](http://www.pixelstech.net/article/1408345768-Different-types-of-keystore-in-Java----Overview)

For this example, we will use the JDK’s keytool to generate a self-sign certificate in PKCS12 format. The below command will create a PKCS12 cert, name keystore.p12, puts this file into the resources folder.

```bash
keytool -genkeypair -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore keystore.p12 -validity 365
```


## DevTools

Intellij settings:

[Stack Overflow article](https://stackoverflow.com/questions/69449905/how-to-enable-spring-boot-live-dev-tools-on-intellij-2021-2-to-rebuild-classes-a)

1. **Settings >> Build, Execution, Deployment >> Compiler** >> check "Build project automatically"

2. **Settings >> Advanced Settings >> Compiler** >> check 'Allow auto-make to start even if developed application is currently running' 
