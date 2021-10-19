## Trabalho Prático - Grupo 2
Bryan Jonathan, Davi Montalvão, Igor de Castro, Luiza Romani.

Na 2ª parte do projeto em grupo foi desenvolvida a parte de Acesso da interface do nosso projeto (Front e Back End). Para isso criamos as classes BackEndInterface e FrontEndInterface, a classe BackEndInterface faz *placeholder* e a FrontEndInterface permite via os metodos de choice, showmenu e showarea o usuário interagir com o banco de dados e obter as informações necessárias via um menu de opções.

Utilizamos a CRUD Genérica indexada desenvolvida pelo Igor, sendo necessário adicionar um segundo índide, indireto, para termos acesso ao ID através do Email digitado pelo próprio usuário.

No Main, criamos um código de Menu que acessa a classe com as opções, sendo elas Registro, Acesso ou Finalização do progama.
As 2 primeiras opções levam a uma classe respectiva a cada, uma realiza o registro do usuário e a outra é responsável pelo login do mesmo.