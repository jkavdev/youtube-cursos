# Hello World com AngularJS

* importando o angular

		<script src="angular.min.js"></script>

* fazendo refencia ao modulo da aplicacao

		<html ng-app="helloWorld"></html>

* criando o modulo da aplicacao

		angular.module("helloWorld", []);

* utilizando um controller

		<div ng-controller="helloWorldCtrl">
			{{message}}
		</div>

* criando um controller
* `angular.module("helloWorld")` estamos fazendo referencia ao modulo da aplicacao
* `angular.module("helloWorld").controller("helloWorldCtrl", function ($scope) {}` criando o controller, com uma dependencia `$scope`

		angular.module("helloWorld").controller("helloWorldCtrl", function ($scope) {
            $scope.message = "Olha soh, ta funcionando...............................";
        });