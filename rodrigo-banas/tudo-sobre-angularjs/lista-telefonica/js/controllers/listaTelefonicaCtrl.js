angular.module("listaTelefonica").controller("listaTelefonicaCtrl", function ($scope, contatos, contatosAPI, serialGenerator) {
    $scope.app = "Lista Telefonica";
    $scope.contatos = contatos.data;

    console.log(contatos.config.url);

    var init = function () {
        calcularImpostos($scope.contatos);
        generateSerial($scope.contatos);
    };

    var calcularImpostos = function (contatos) {
        contatos.forEach(function (contato) {
            contato.operadora.precoComImposto = calcularImposto(contato.operadora.preco);
        });
    };

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
        $scope.verificarContatoSelecionado($scope.contatos);
    };
    var counter = 0;
    $scope.verificarContatoSelecionado = function (contatos) {
        console.log("counter selecionado mais: ", counter++);
        console.log("counter selecionado mais: ", contatos);
        $scope.hasContatoSelecionado = contatos.some(function (contato) {
            console.log("counter selecionado mais: ", contato.selecionado);
            return contato.selecionado;
        });
    };
    $scope.ordenarPor = function (campo) {
        $scope.criterioDeOrdenacao = campo;

        $scope.direcaoDeOrdenacao = !$scope.direcaoDeOrdenacao;
    };
    var calcularImposto = function (preco) {
        var imposto = 1.2;
        return preco * imposto;
    };

    init();

});