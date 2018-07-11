angular.module("listaTelefonica").config(function (serialGeneratorProvider) {

    console.log("tamanho do serial: ", serialGeneratorProvider.getLength());

    serialGeneratorProvider.setLength(100);

    console.log("tamanho do serial: ", serialGeneratorProvider.getLength());

});