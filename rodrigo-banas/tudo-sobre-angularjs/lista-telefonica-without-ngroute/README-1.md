# Lista Telefonica com AngularJS

# Integrando com Backend

* criando metodo de busca de dados de contato e operadora
* para isso usamos o $http, que realiza requisicoes http

		angular.module("listaTelefonica").controller("listaTelefonicaCtrl", function ($http) {

* realizando requisicao de dados de contato e operadora
* temos `$http.get('http://localhost:8080/api/contatos/').then(function(resp, status){}`, que retornara uma promise contendo a resposta da requisicao

			var carregarContatos = function(){
				$http.get('http://localhost:8080/api/contatos/').then(function(resp, status){
					$scope.contatos = resp.data;
				})
			}
			var carregarOperadoras = function(){
				$http.get('http://localhost:8080/api/operadoras/').then(function(resp, status){
					$scope.operadoras = resp.data;
				})
			}
		}

* adicionado requisicao de adicionar contato, e no metodo de callback, fazemos novamente outra consulta pelo contatos

		$scope.adicionarContato = function (contato) {
			contato.data = new Date();
			$http.post('http://localhost:8080/api/contatos/', contato).then(function(resp, status){
				delete $scope.contato;
				$scope.contatoForm.$setPristine();
				carregarContatos();
			})
		}

* obtendo o erro caso a requisicao de errado

		var carregarContatos = function(){
			$http.get('http://localhost:8080/api/contatos/').then(function(resp, status){
				$scope.contatos = resp.data;
			}).catch(function(error){
				$scope.message = "Ocorreu um problema: " + error;
				console.log(error)
			})
		}

* exibindo mensagem do ocorrido

		{{message}}

# Serviços

* criando um servico para o acesso as requisicoes de dados
* criaremos uma factory `angular.module("...").factory("contatosAPI", function ($http) {}`
* teremos metodos da api `var _getContatos = function () {}` privados `_getContatos`
* e um retorno dos metodos disponibilizados pela api `return { getContatos: _getContatos, saveContato: _saveContato, }`

		angular.module("listaTelefonica").factory("contatosAPI", function ($http) {
			var _getContatos = function () {
				return $http.get('http://localhost:8080/api/contatos/');
			};
			var _saveContato = function (contato) {
				return $http.post('http://localhost:8080/api/contatos/', contato);
			};
			return {
				getContatos: _getContatos,
				saveContato: _saveContato,
			}
		});

* utilizando servico criado de acesso a api do backend

		angular.module("...").controller("...", function ($scope, contatosAPI) {

		var carregarContatos = function () {
			contatosAPI.getContatos().then(function (resp, status) {
				..........
			}).catch(function (error) {
				..........
			})
		}
		$scope.adicionarContato = function (contato) {
			contatosAPI.saveContato(contato).then(function (resp, status) {
				..........
			})
		}

* criando um servico para acesso as operadoras
* neste caso criaremos um service

		angular.module("listaTelefonica").service("operadorasAPI", function ($http) {
			this.getOperadoras = function () {
				return $http.get('http://localhost:8080/api/operadoras/');
			};
		});

* utilizando servico criado de acesso a api do backend

		var carregarOperadoras = function () {
			operadorasAPI.getOperadoras().then(function (resp, status) {
				..........
			})
		}

* criando `values`, valores que serao utilizados para a aplicacao semelhantes a contantes
* criando um value contendo a url base para acesso aos servicos

		angular.module("listaTelefonica").value("config", {
			baseUrl: "http://localhost:8080/api"
		});

* utilizando a baseUrl criada nos servicos de acesso ao backend

		 var _getContatos = function () {
			return $http.get(config.baseUrl + '/contatos/');
		};
		var _saveContato = function (contato) {
			return $http.post(config.baseUrl + '/contatos/', contato);
		};
		this.getOperadoras = function () {
			return $http.get(config.baseUrl + '/operadoras/');
		};

* criando um servico que gerara um serial
* podemos criar um provider
* precisamos definir `this.$get = function () {}` necessario
* depois podemos definir o que o servico disponibilizara `return { generate: function () {}}`
* temos uma varia que controla o tamanho do serial gerado `var _length = 10;` padrao

		angular.module("listaTelefonica").provider("serialGenerator", function () {
			var _length = 10;
			this.getLength = function () {
				return _length;
			};
			this.setLength = function (length) {
				_length = length;
			};
			this.$get = function () {
				return {
					generate: function () {
						var serial = "";
						while (serial.length < _length) {
							serial += String.fromCharCode(Math.floor(Math.random() * 64) + 32);
						}
						return serial;
					}
				};
			};
		});

* utilizando o gerador de serial no controller

		angular.module("....").controller("....", function ($scope,serialGenerator) {
			console.log(serialGenerator.generate());
			$scope.adicionarContato = function (contato) {
				contato.serial = serialGenerator.generate();
			}

* alterando o valor do provider com servicos de config
* definindo um config, recebendo o provider com sufixo `provider`+`Provider`
* alterando propriedades do provider `serialGeneratorProvider.setLength(100);`

		angular.module("listaTelefonica").config(function (serialGeneratorProvider) {
			console.log("tamanho do serial: ", serialGeneratorProvider.getLength());
			serialGeneratorProvider.setLength(100);
			console.log("tamanho do serial: ", serialGeneratorProvider.getLength());
		});

* provider sao os unicos servicos que conseguem ter seus valores alterados pelo config		

# Filters

* criando filter para deixar a primeira letra do nome maiuscula
* o filter vai sempre receber um input e retornar algo `return function (input) { return output;}`

		angular.module("listaTelefonica").filter("name", function () {
			return function (input) {
				var listaDeNomes = input.split(" ");
				var listaDeNomesFormatada = listaDeNomes.map(function (nome) {
					if (/(da|de)/.test(nome)) return nome;
					return nome.charAt(0).toUpperCase() + nome.substring(1).toLowerCase();
				});
				return listaDeNomesFormatada.join(" ");
			};
		});

* utilizando o filter no campo nome

		<td>{{contato.nome | name}}</td>

* criando filter para indicar omissao de texto, caso o texto seja maior que o definido
* passando o nome a ser trabalhado e o tamanho do texto a ser exibido
* retorna apenas o texto sem formatacao, porque eh menor que o tamanho a ser exibido `if (input.length <= size) return input;`
* `var output = input.substring(0, (size | 2)) + "...";` retorna o texto com o tamanho especificado
* `(size | 2)` evalua o campo size, se tiver valor, continua com o valor, senao tiver atribui o valor 2

		angular.module("listaTelefonica").filter("ellipsis", function () {
			return function (input, size) {
				if (input.length <= size) return input;
				var output = input.substring(0, (size | 2)) + "...";
				return output;
			};
		});
		
* utilizando o filter especificando o tamanho a ser exibido

        <td>{{contato.nome | ellipsis:15}}</td>		
        
* utilizando o filter nao especificando o tamanho a ser exibido

        <td>{{contato.nome | ellipsis}}</td>
        		
* encadeando filtros

        <td>{{contato.nome | name | ellipsis:15}}</td>
        
# Directives

* criando uma diretiva para exibir mensagens
* teremos um titulo `{{title}}` que sera injetado
* e o corpo `ng-transclude` que sera injetado como elemento filho da diretiva

        <div class="ui-alert">
            <div class="ui-alert-title">
                {{title}}
            </div>
            <div class="ui-alert-messge" ng-transclude></div>
        </div>        		
        
* definindo a diretiva
* nome de acesso a diretiva `angular.module("listaTelefonica").directive("uiAlert", function () {}`
* `templateUrl: "view/alert.html",` indicando onde esta o html desta diretiva
* `replace: true,` estamos indicando se sera renderizado o html em si da diretiva ou sera adicionado o `<nome da diretiva><htmlDiretiva><htmlDiretiva></nome da diretiva>`
* estamos indincando que podemos utilizar esta diretiva como atributo ou elemento `restrict: "AE",`
* `transclude: true` indica que esta diretiva pode ter elementos como filho
* `scope: { title: "@" },` estamos mapeando os atributos da diretiva para o escopo da diretiva
* `title: "@"` indica que o um atributo tanto da diretiva quanto no escopo com nomes iguais e o valor do atributo sera passado uma unica vez para a diretiva com `@`
* `message: "="` indica que temos valores iguais, e criamos um twoway databind do escope externo quanto o interno da diretiva


        angular.module("listaTelefonica").directive("uiAlert", function () {
            return {
                templateUrl: "view/alert.html",
                replace: true,
                restrict: "AE",
                scope: {
                    title: "@"
                },
                transclude: true
            };
        });        
        
* utilizando a diretiva

        <div ui-alert title="Ops, acontenceu um problema!">
            {{error}}
        </div>
        <ui-alert title="Ops, acontenceu um problema!">
            {{error}}
        </ui-alert>        
        
* criando uma diretiva para formata datas
* com `link: function(){}` estamos indicando que teremos uma directiva que alterar o DOM
* `link: function (scope, element, attrs, ctrl) {}` recebendo os paramentos da diretiva, para que possamos alterar seu valor
* para  receber `ctrl` temos que indicar que precisamos de um modulo do angular, `require: "ngModel",`

* estamos indica que a cada pressionamento de botao executaremos uma tarefa
* `ctrl.$setViewValue(_formatDate(ctrl.$viewValue));` indicando que o valor a ser rederizado eh o valor formatado do elemento
* `ctrl.$render();` realizando a renderizacao da alteracao do valor

        element.bind("keyup", function () {
            ctrl.$setViewValue(_formatDate(ctrl.$viewValue));
            console.log("valor a ser renderizado no DOM:" + ctrl.$viewValue);
            ctrl.$render();
        }); 
        
* atribuindo um parse que atribuira uma data para o valor da diretiva  
* podemos ter varios parsers
* apenas atribuiremos um valor caso a data esteje completa `if (value.length === 10) {return new Date(dateArray[2], dateArray[1] - 1, dateArray[0])}`

        ctrl.$parsers.push(function (value) {
            if (value.length === 10) {
                var dateArray = value.split("/");
                console.log("new Date: " + new Date(dateArray[2], dateArray[1] - 1, dateArray[0]));
                return new Date(dateArray[2], dateArray[1] - 1, dateArray[0]);
            }
        });        
 
* formatando o valor da diretiva caso seja inicializado com algum valor
* inicializando valor com um `new Date()`

        $scope.contato = {
            data: new Date()
        }

* formatando o valor
* como os parsers temos varios formatadores
        
* no caso necessitaremos o `filter` do angular para realizar o parse da data no formato desejado

        angular.module("listaTelefonica").directive("uiDate", function ($filter) {}        
        
* definindo os formatters
* utilizando o filter para formatar o valor para o desejado

        ctrl.$formatters.push(function (value) {
            return $filter("date")(value, "dd/MM/yyyy")
        });         
        
* utilizando a diretiva em um input

        <input type="text" class="form-control" ng-model="contato.data" name="data" placeholder="Data" ui-date/>
        
* criando uma diretiva para representar um accordion
* com `link: function (scope, element, attrs, ctrl) {}` podemos adicionar o comportamento de quando clicado
* `ctrl.registerAccordion(scope);` adicionando este accordion no array de accordions
* `scope.open = function () {}` fechara todos os accordions e abrirara o que esta sendo clicado

        angular.module("listaTelefonica").directive("uiAccordion", function () {
            return {
                templateUrl: "view/accordion.html",
                transclude: true,
                scope: {
                    title: "@"
                },
                require: "^uiAccordions",
                link: function (scope, element, attrs, ctrl) {
                    ctrl.registerAccordion(scope);
                    scope.open = function () {
                        ctrl.closeAll();
                        scope.isOpened = true;
                    }
                }
            };
        });    
        
* como precisaremos de um modulo que gerencie os accordions, criaremos uma diretiva pai dos accordions
* `this.registerAccordion = function (accordion) {}` adicionando o accordion no array
* `this.closeAll = function () {}` itera sobre os accordions e atribui o estado de fechado

        angular.module("listaTelefonica").directive("uiAccordions", function () {
            return {
                controller: function ($scope, $element, $attrs) {
                    var accordions = [];
                    this.registerAccordion = function (accordion) {
                        accordions.push(accordion);
                    };
                    this.closeAll = function () {
                        accordions.forEach(function (accordion) {
                            accordion.isOpened = false;
                        });
                    };
                }
            };
        });
        
* indicando que a diretiva filha tem um pai `require: "^uiAccordions"`
* utilizando metodos da diretiva pai `ctrl.registerAccordion(scope);`

        angular.module("listaTelefonica").directive("uiAccordion", function () {
                    return {
                        require: "^uiAccordions",
                        link: function (scope, element, attrs, ctrl) {
                            ctrl.registerAccordion(scope);
                        }
                    };
                });            
        
        
* html da diretiva
* `ng-transclude` pode ter elementos filhos

        <div class="ui-accordion-title" ng-click="open()">{{title}}</div>
        <div class="ui-accordion-content" ng-show="isOpened" ng-transclude></div>     
        
* utilizando a diretiva

        <ui-accordions>
            <ui-accordion title="Accordion1">
                Nicha has found that Thai citizens have been used as lab rats to test an antidote for a virus created by a drugs company. Nicha must find the victims before it's too
            </ui-accordion>
            <ui-accordion title="Accordion2">
                Nicha has found that Thai citizens have been used as lab rats to test an antidote for a virus created by a drugs company. Nicha must find the victims before it's too
            </ui-accordion>
            <ui-accordion title="Accordion3">
                A stream is an abstract concept that represents a sequence of objects created by a source, it’s neither a data structure nor a collection object where we can store items.
            </ui-accordion>
        </ui-accordions>                       