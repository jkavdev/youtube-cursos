# Lista Telefonica com AngularJS

# Disponibilizando modulos

* disponibilizando um como com o gerador de serial
* criando o modulo do gerador `angular.module("serialGenerator", []);`
* declaracao do provider com o modulo do gerador `angular.module("serialGenerator").provider("serialGenerator", function () {`

        angular.module("serialGenerator", []);
        angular.module("serialGenerator").provider("serialGenerator", function () {
            ...................
        }); 
        
* importando o modulo

        <script src="lib/serialGenerator/serialGeneratorService.js"></script>
        
* indicando que o modulo da aplicacao utilizara o modulo do gerador de serial

        angular.module("listaTelefonica", ["ngMessages", "serialGenerator"]); 
        
* modularizando o componente accordion
* criando a modulo `angular.module("ui", []);`
* criando as diretivas com o modulo criado `angular.module("ui").directive("uiAccordions", function () {}`
* como o componente define uma diretiva visual, podemos indicar o html com `$templateCache`   
* registrando templatendo com o run do modulo `angular.module("ui").run(function ($templateCache) {})`
* onde o `$templateCache` eh um map `$templateCache.put("view/accordion.html", '<div class="ui-accordion-title" ng-click="open()">{{title}}</div> <div class="ui-accordion-content" ng-show="isOpened" ng-transclude></div>')`


        angular.module("ui", []);
        
        angular.module("ui").run(function ($templateCache) {
            $templateCache.put("view/accordion.html", '<div class="ui-accordion-title" ng-click="open()">{{title}}</div> <div class="ui-accordion-content" ng-show="isOpened" ng-transclude></div>')
        })
        
        angular.module("ui").directive("uiAccordions", function () {
            ............
        });
        
        angular.module("ui").directive("uiAccordion", function () {
            ............
        });    
        
# Single Page Application com ngRoute

* importando o ngRoute 

        <script src="lib/angular/angular-route.js"></script>
        
* disponibilizando modulo para a aplicacao

        angular.module("listaTelefonica", ["ngMessages", "serialGenerator", "ui", "ngRoute"]);
        
* separando a aplicacao
* separando a criacao de contato em outra tela `novoContato.html`

        <div class="jumbotron">
            <form name="contatoForm">
                <input type="text" class="form-control" ng-model="contato.nome" name="nome" placeholder="Nome" ng-required="true" ng-minlength="10" />
                <input type="text" class="form-control" ng-model="contato.telefone" name="telefone" placeholder="Telefone" ng-required="true" ng-pattern="/^\d{4,5}-\d{4}$/" />
                <input type="text" class="form-control" ng-model="contato.data" name="data" placeholder="Data" ui-date/>
                <select class="form-control" ng-model="contato.operadora" ng-options="operadora.nome + '( '+ (operadora.preco | currency) +' )' group by operadora.categoria for operadora in operadoras | orderBy:'nome'">
                    <option value="">Selecione uma operadora</option>
                </select>
            </form>
            <button class="btn btn-primary btn-block" ng-click="adicionarContato(contato)" ng-disabled="contatoForm.$invalid">Adicionar Contato</button>
        </div>

* configuracao da rota
* com `$routeProvider` poderemos configurar as rotas
* quando acessado `/novoContato` `$routeProvider.when("/novoContato", {}` sera invocado
* indicamos aonde esta o html `templateUrl: "view/novoContato.html",`
* `controller: "novoContatoCtrl",` indicamos quem eh o controller responsavel
* podemos ainda dizer as dependencias para esta rota
* como anteriormente precisavamos da lista de contato para exibir a lista
* indicaremos no `resolve: { operadoras: function (operadorasAPI) { return operadorasAPI.getOperadoras(); } }`
* onde operadoras ficara disponivel para o controller responsavel

        angular.module("listaTelefonica").config(function ($routeProvider) {
            $routeProvider.when("/novoContato", {
                templateUrl: "view/novoContato.html",
                controller: "novoContatoCtrl",
                resolve: {
                    operadoras: function (operadorasAPI) {
                        return operadorasAPI.getOperadoras();
                    }
                }
            });
            $routeProvider.otherwise({redirectTo: "/contatos"});
        });
        
* controller
* recebendo operadoras como depedencia do resolve do ngRoute `angular.module("listaTelefonica").controller("novoContatoCtrl", function (operadoras) {`
* atribuindo os dados ao escopo `$scope.operadoras = operadoras.data;`
* como separamos de tela, quando for adicionado um contato usaremos o location para redirecionar para a lista de contatos
* recendo o location como dependencia `angular.module("listaTelefonica").controller("novoContatoCtrl", function ($location) {`
* `$location.path("/contatos");` redirecionando para contatos

        angular.module("listaTelefonica").controller("novoContatoCtrl", function ($scope, contatosAPI, serialGenerator, $location, operadoras) {
            $scope.operadoras = operadoras.data;
            
            $scope.adicionarContato = function (contato) {
                contatosAPI.saveContato(contato).then(function (resp) {
                    ............................
                    $location.path("/contatos");
                })
            };
        });        
                      
* criando a listagem de contatos
* adicionando link para a tela de adicionar contato `<a class="btn btn-info btn-block" href="#!/novoContato">Novo Contato</a>`

        <table class="table table-striped" ng-show="contatos.length > 0">
            <tr>
                <th></th>
                <th>Serial</th>
                <th><a href="" ng-click="ordenarPor('nome')">Nome</a></th>
                <th><a href="" ng-click="ordenarPor('telefone')">Telefone</a></th>
                <th>Operadora</th>
                <th>Data</th>
                <th></th>
            </tr>
            <tr ng-class="{selecionado: contato.selecionado, negrito: contato.selecionado}" ng-repeat="contato in contatos | limitTo:10 | filter:{nome: criterioDeBusca} | orderBy:criterioDeOrdenacao:direcaoDeOrdenacao">
                <td><input type="checkbox" ng-model="contato.selecionado" /></td>
                <td>{{contato.serial}}</td>
                <td>{{contato.nome | name | ellipsis:15}}</td>
                <td>{{contato.telefone}}</td>
                <td>{{contato.operadora.nome | uppercase}}</td>
                <td>{{contato.data | date: 'dd/MM/yyyy HH:mm'}}</td>
                <td><div style="width: 20px; height: 20px;" ng-style="{'background-color': contato.cor}"></div></td>
            </tr>
        </table>
        <hr />
        <a class="btn btn-info btn-block" href="#!/novoContato">Novo Contato</a>
        <button class="btn btn-danger btn-block" ng-click="apagarContato(contatos)" ng-disabled="!isContatoSelecionado(contatos)" ng-if="contatos.length > 0">Apagar Contato</button>
        
* configuracao da rota de contatos

        $routeProvider.when("/contatos", {
            templateUrl: "view/contatos.html",
            controller: "listaTelefonicaCtrl",
            resolve: {
                contatos: function (contatosAPI) {
                    return contatosAPI.getContatos();
                }
            }
        });                                          

* controller da lista de contatos
* recebendo os contatos de ngRoute `angular.module("listaTelefonica").controller("listaTelefonicaCtrl", function (contatos) {`
* atribuindo dados ao escope

        angular.module("listaTelefonica").controller("listaTelefonicaCtrl", function ($scope, contatos, contatosAPI, serialGenerator) {
            $scope.contatos = contatos.data;
            var generateSerial = function (contatos) {
                contatos.forEach(function (contato) {
                    if(!contato.serial)
                        contato.serial = serialGenerator.generate();
                })
            };
            ...........................
            generateSerial($scope.contatos);
        }); 
        
* visualizando os detalhes do contato
* para isso passaremos o id contato pelo url para identifica-lo
* `$routeProvider.when("/detalhesContato/:id", {` com `:id` estamos indicando que receberemos um parametro pela url
* podemos ja configurar um resolver para buscar o contato
* mas temos que passar alguns parametro, o `$route`, atraves dele pegamos o id `$route.current.params.id`

        $routeProvider.when("/detalhesContato/:id", {
            templateUrl: "view/detalhesContato.html",
            controller: "detalhesContatoCtrl",
            resolve: {
                contato: function (contatosAPI, $route) {
                    return contatosAPI.getContato($route.current.params.id);
                }
            }
        });        
        
* controller

        angular.module("listaTelefonica").controller("detalhesContatoCtrl", function ($scope, contato) {
            $scope.contato = contato.data;
        });        
        
* adicionamos um link na listagem de contatos, no atributo nome
* que chamara a rota de detalhes passando seu id como parametro

        <td><a href="#!/detalhesContato/{{contato.id}}">{{contato.nome | name}}</a></td>
                
* exibindo as informacoes do contato

        <div class="jumbotron">
            <table class="table">
                <tr><td>{{contato.nome}}</td></tr>
                <tr><td>{{contato.telefone}}</td></tr>
                <tr><td>{{contato.data | date:"dd/MM/yyyy"}}</td></tr>
            </table>
            <a class="btn btn-info btn-block" href="#!/contatos">Contatos</a>
        </div>                
        
# Interceptors

* adicionando interceptor para adicionar a todas as requisicoes um parametro indicando quando foi feita
* definindo interceptor, `angular.module("listaTelefonica").factory("timestampInterceptor", function () {}`
* `request: function (config) {}` indicamos que interceptaremos todas as requisicoes
* `var url = config.url;` url da requisicao
* `if(url.indexOf('view') > -1) {}` evitando requisicoes para paginas html
* adicionando o parametro da data `var timestamp = new Date().getTime(); config.url = url + "?timestamp" + timestamp;`

        angular.module("listaTelefonica").factory("timestampInterceptor", function () {
            return {
                request: function (config) {
                    var url = config.url;
                    if(url.indexOf('view') > -1) {
                        return config;
                    }
                    var timestamp = new Date().getTime();
                    config.url = url + "?timestamp" + timestamp;
                    return config;
                }
            };
        });
        
* registrando nosso interceptador

        angular.module("listaTelefonica").config(function ($httpProvider) {
            $httpProvider.interceptors.push("timestampInterceptor");
        });
        
* importando nosso interceptador

       <script src="js/interceptors/timestampInterceptor.js"></script>
       
* criando um interceptador para interceptar erros e redirecionar para uma pagina de error
* definindo `angular.module("listaTelefonica").factory("errorInterceptor", function ($q, $location) {}`
* receberemos como depedencia `$q` que trabalha com promises
* `$location` para nos usar o redirecionamento
* indicando quando for uma resposta de error `responseError: function (error) {`
* registrando o erro no gerenciador de promessas `return $q.reject(error);`

        angular.module("listaTelefonica").factory("errorInterceptor", function ($q, $location) {
            return {
                responseError: function (error) {
                    if(error.status === 404){
                        $location.path("/error")
                    }
                    return $q.reject(error);
                }
            }
        });
        
* registrando interceptador

        angular.module("listaTelefonica").config(function ($httpProvider) {
            $httpProvider.interceptors.push("timestampInterceptor");
            $httpProvider.interceptors.push("errorInterceptor");
        });                               

* configurando rota para acesso da pagina de erro

        $routeProvider.when("/error", {
            templateUrl: "view/error.html",
        });
        
* html do error

        <div class="jumbotron">
            <h3>Não foi possível processar sua solicitação</h3>
            <a class="btn btn-info btn-block" href="#!/contatos">Voltar a contatos</a>
        </div>        
        
* criando um interceptador para o carregamento da rota
* definicao `angular.module("listaTelefonica").factory("loadingInterceptor", function ($q, $rootScope, $timeout) {}`
* adicionaremos `$rootScope` para adicionar uma propriedade no escopo pai
* `$timeout` simularemos um delay na resposta
* usaremos todos os modos de interceptacao
* no inicio da requisicao atribuimos o estado de carregando
* e ao final de todas os outros modos de interceptacao retiramos o estado de carregando

        angular.module("listaTelefonica").factory("loadingInterceptor", function ($q, $rootScope, $timeout) {
            return {
                request: function (config) {
                    $rootScope.loading = true;
                    return config;
                },
                requestError: function (rejection) {
                    $rootScope.loading = false;
                    return $q.reject(rejection);
                },
                response: function (response) {
                    $timeout(function () {
                        $rootScope.loading = false;
                    }, 500);
                    return response;
                },
                responseError: function (rejection) {
                    $rootScope.loading = false;
                    return $q.reject(rejection);
                },
            }
        });        
        
* registrando interceptador

        angular.module("listaTelefonica").config(function ($httpProvider) {
            $httpProvider.interceptors.push("timestampInterceptor");
            $httpProvider.interceptors.push("errorInterceptor");
            $httpProvider.interceptors.push("loadingInterceptor");
        });
        
* indicamos onde estiver carregando nao sera renderizada as views        
        
        <div ng-hide="loading">
            <div ng-view></div>
            <div ng-include="'/view/footer.html'"></div>
        </div>        
        
* exibindo tela de carregamento

        <div class="jumbotron" ng-show="loading">
            Carregando........ aguarde................
        </div>        