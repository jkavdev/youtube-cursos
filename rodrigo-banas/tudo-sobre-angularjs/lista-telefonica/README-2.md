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