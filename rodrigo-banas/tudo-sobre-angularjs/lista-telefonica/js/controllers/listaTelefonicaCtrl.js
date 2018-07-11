angular.module("listaTelefonica").controller("listaTelefonicaCtrl", function ($scope, contatosAPI, operadorasAPI, serialGenerator) {
    $scope.app = "Lista Telefonica";

    console.log(serialGenerator.generate());

    var carregarContatos = function () {
        contatosAPI.getContatos().then(function (resp, status) {
            $scope.contatos = resp.data;
        }).catch(function (error) {
            $scope.message = "Ocorreu um problema: " + error;
            console.log(error)
        })
    }
    var carregarOperadoras = function () {
        operadorasAPI.getOperadoras().then(function (resp, status) {
            $scope.operadoras = resp.data;
        })
    }
    $scope.adicionarContato = function (contato) {
        contato.serial = serialGenerator.generate();
        contato.data = new Date();
        contatosAPI.saveContato(contato).then(function (resp, status) {
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