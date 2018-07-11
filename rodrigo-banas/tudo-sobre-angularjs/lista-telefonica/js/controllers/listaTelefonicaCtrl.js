angular.module("listaTelefonica").controller("listaTelefonicaCtrl", function ($scope, $http, $filter, lowercaseFilter) {
    $scope.app = "Lista Telefonica";

    var carregarContatos = function () {
        $http.get('http://localhost:8080/api/contatos/').then(function (resp, status) {
            $scope.contatos = resp.data;
        }).catch(function (error) {
            $scope.message = "Ocorreu um problema: " + error;
            console.log(error)
        })
    }
    var carregarOperadoras = function () {
        $http.get('http://localhost:8080/api/operadoras/').then(function (resp, status) {
            $scope.operadoras = resp.data;
        })
    }
    $scope.adicionarContato = function (contato) {
        contato.data = new Date();
        $http.post('http://localhost:8080/api/contatos/', contato).then(function (resp, status) {
            delete $scope.contato;
            $scope.contatoForm.$setPristine();
            carregarContatos();
        })
    }
    $scope.apagarContato = function (contatos) {
        $scope.contatos = contatos.filter(function (contato) {
            if (!contato.selecionado) return contato;
        });
    }
    $scope.isContatoSelecionado = function (contatos) {
        return contatos.some(function (contato) {
            return contato.selecionado;
        });
    }
    $scope.ordenarPor = function (campo) {
        $scope.criterioDeOrdenacao = campo;
        $scope.direcaoDeOrdenacao = !$scope.direcaoDeOrdenacao;
    }

    carregarOperadoras();
    carregarContatos();
});