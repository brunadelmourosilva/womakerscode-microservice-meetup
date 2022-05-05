#  WoMakersCode - Bootcamp Java 🦋

## RestAPI para agendamento de meetups

API desenvolvida no bootcamp back-end Java da WoMakersCode, cujo objetivo é registrar eventos e seus respectivos participantes.

---

### Status 📈
_Em andamento_ 

---

### Tecnologias 🖥️

* Java 11
* Spring Boot 2.6.6
* Spring Data JPA
* Spring Security
* Gradle
* MySQL
* H2 Database
* TDD - Test Driven Development
* JUnit 5
* Mockito
* Azure functions
* Swagger

---

### Como utilizar a aplicação 🤔

* 1 - A aplicação encontra-se hospedada na Azure, disponível na seguinte URL: https://microservice-meetup.azurewebsites.net/swagger-ui/#/

* 2 - Para acessar os endpoints, crie uma nova conta e insira um e-mail válido, como mostra o exemplo

![image](https://user-images.githubusercontent.com/61791877/166851325-65f9a2c0-78bc-4a94-a4e2-a092f0e7ddbf.png)

* 3 - Após a criação da conta, verifique seu e-mail

![image](https://user-images.githubusercontent.com/61791877/166851509-7147e68e-2ded-4742-bc39-223e93038203.png)

* 4 - A aplicação encontra-se implementada com o Spring Security. Dessa forma, é necessário criar um token à partir da nova conta, para acessar os endpoints que necessitam de autenticação. Siga os seguites passos para a criação do token:

    * 4.1 - Acesse a ferramenta **Postman**;
    * 4.2 - Em seguida, insira a seguinte url em _Enter request URL_ e selecione o método `POST`: `https://microservice-meetup.azurewebsites.net/login`;
      
      ![image](https://user-images.githubusercontent.com/61791877/166853320-79d1d5c6-bf25-4c23-b37f-24b8f3372e75.png)
    
    * 4.3 - Para encontrar seu token, acesse a aba _Headers_ e encontre a key _Authorization_ com seu respectivo value(token);

      ![image](https://user-images.githubusercontent.com/61791877/166853632-c0954a75-d601-4a9b-beef-cbadfc2a007f.png)
      
      _Obs: é importante ressaltar que o token tem duração de 16 minutos. Logo após a esse intervalo, o token expirará e dessa forma, será ncessário gerar um novo     token, seguindo os mesmos passos da seção 4._

* 5 - Com o token em mãos, basta voltar ao **Swagger**, clicar no botão _Authorize_ e inserir o token

![image](https://user-images.githubusercontent.com/61791877/166854075-3e10fbb3-7627-468c-801c-954922f8c19f.png)
![image](https://user-images.githubusercontent.com/61791877/166854097-a3f23238-b4bc-430e-bc45-7f923afe4e52.png)

* 6 - Após seguir os passos, você poderá acessar os endpoints e se cadastrar em um meetup! Segue como exemplo, uma demonstração de como se registrar em um determinado evento:
  
  * 6.1 - Por padrão, a aplicação está com 2 eventos cadastrados. No exemplo, será demonstrado um cadastro em um evento já inserido no sistema, mas você também pode criar um novo evento com o endpoint `/api/meetups/registerMeetup`. É opcional!;
     
  * 6.2 - Vá até o endpoint `/api/meetups/registerRegistration/{meetupId}`. Em seguida, insira o id do meetup, o _registration number_ e clique em **Execute**;

![image](https://user-images.githubusercontent.com/61791877/166855964-fb5478b2-17c7-4a41-bbd7-1be4c74ed45b.png)

![image](https://user-images.githubusercontent.com/61791877/166855104-fe0903ea-b5fb-4b40-97ad-36e1291b07d6.png)


  * 6.3 - O retorno das informações será enviado por e-mail;

![image](https://user-images.githubusercontent.com/61791877/166855164-05cc9165-777d-481e-929e-a8c0740efda8.png)

 * 7 - Os demais endpoints estarão disponíveis para serem acessados e testados. :)

---

### Diagrama do sistema :bar_chart:

![rest-api-meetups drawio (1)](https://user-images.githubusercontent.com/61791877/166856485-efaa856f-8874-4145-85ac-052f67057763.png)


<h4><i> clique na imagem para uma melhor visualização </i></h4>

---

### Features 💡

terminar

---
### Checklist ✔️

- [x] Implementação da classe Registration
- [x] Implementação da classe Meetup
- [x] Implementação da camada service
- [x] Implementação da camada controller
- [x] Implementação do repository
- [x] Implementação de DTO's
- [x] Adicionar MySQL ao projeto
- [x] Adicionar LOGs à aplicação
- [x] Tratamento de exceptions
- [x] Tratamento de validação em Registration
- [x] Tratamento de validação em Meetup
- [x] Envio de e-mail ao registrar o cadastro
- [x] Envio de e-mail ao registrar em um meetup
- [ ] Número de registro como _auto generated_ [opcional]
- [x] Adicionar Spring Security com token JWT
- [x] Swagger - https://microservice-meetup.azurewebsites.net/swagger-ui/#/
- [x] Desenhar diagrama do sistema
- [x] Testes locais via postman
- [x] Deploy no Azure

### Checklist para testes 🧪

- [x] Meetup | Testes do repository
- [x] Meetup | Testes do controller
- [x] Meetup | Testes do service
- [x] Registration | Testes do repository
- [x] Registration | Testes do controller
- [x] Registration | Testes do service

