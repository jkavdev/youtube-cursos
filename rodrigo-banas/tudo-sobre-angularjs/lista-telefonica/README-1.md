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