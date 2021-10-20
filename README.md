## Trabalho Prático - Grupo 2
Bryan Jonathan, Davi Montalvão, Igor de Castro, Luiza Romani.

Na 2ª etapa do projeto em grupo foi desenvolvida a parte de Acesso da interface do nosso projeto (Front e Back End). Para isso criamos as classes BackEndInterface e FrontEndInterface; a classe BackEndInterface habilita o registro pelo método userRegister() e acesso dos usuários userAccess() já a FrontEndInterface permite via os métodos de choice, showmenu e showarea o usuário interagir com o banco de dados e obter as informações necessárias via um menu de opções.

Utilizamos a CRUD Genérica indexada desenvolvida pelo Igor, sendo necessário adicionar um segundo índide, indireto, para termos acesso ao ID através do Email digitado pelo próprio usuário na primeira parte do trabalho. Contudo, conforme informado pelo professor, adicionamos novos CRUD (via extends) para manipulação de Usuários e Questões; pois assim removemos o acrescimo de índice e mantemos o CRUD Genérico verdadeiramente Genérico.

No Main, criamos um código de Menu que acessa os CRUDS de usuario e questões, ademais acessamos o FrontEndInterface.


OBS: O trabalho se encontra em falta na implementação correta no que diz respeito as questões. A listagem e inserção estão funcional, faltou atualização e arquivamento das perguntas.