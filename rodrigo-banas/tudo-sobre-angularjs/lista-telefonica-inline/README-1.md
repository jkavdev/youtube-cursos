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