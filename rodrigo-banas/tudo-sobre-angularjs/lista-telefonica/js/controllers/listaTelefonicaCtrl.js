angular.module("listaTelefonica").controller("listaTelefonicaCtrl", function ($scope, contatos, contatosAPI, serialGenerator) {
    $scope.app = "Lista Telefonica";
    $scope.contatos = contatos.data;

    console.log(contatos.config.url);

    var generateSerial = function (contatos) {
        contatos.forEach(function (contato) {
            if(!contato.serial)
                contato.serial = serialGenerator.generate();
        })
    };
    $scope.apagarContato = function (contatos) {
        $scope.contatos = contatos.filter(function (contato) {
            if (!contato.selecionado) return contato;
        });
    };
    $scope.isContatoSelecionado = function (contatos) {
        return contatos.some(function (contato) {
            return contato.selecionado;
        });
    };
    $scope.ordenarPor = function (campo) {
        $scope.criterioDeOrdenacao = campo;
        $scope.direcaoDeOrdenacao = !$scope.direcaoDeOrdenacao;
    };

    generateSerial($scope.contatos);
});