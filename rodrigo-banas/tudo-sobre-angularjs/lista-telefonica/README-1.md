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