angular.module("listaTelefonica").filter("upper", function () {

    return function (input) {

        var counter = 0;
        if (!input) return input;
        console.log("aqui", counter++);
        return input.toUpperCase();

    };

});