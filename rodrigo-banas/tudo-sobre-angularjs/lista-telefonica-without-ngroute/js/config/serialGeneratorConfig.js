angular.module("listaTelefonica").config(function (serialGeneratorProvider) {

    console.log("tamanho do serial: ", serialGeneratorProvider.getLength());

    serialGeneratorProvider.setLength(5);

    console.log("tamanho do serial: ", serialGeneratorProvider.getLength());

});