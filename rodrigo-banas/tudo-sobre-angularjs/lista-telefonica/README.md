# Lista Telefonica com AngularJS

* definindo um array no `$scope`

		$scope.contatos = [
			{ nome: 'Jhonatan', telefone: '12345678' },
			{ nome: 'Lucas', telefone: '12344618' },
			{ nome: 'Maria', telefone: '12345615' },
		];

* exibindo os contatos
* iterando sobre os contatos `<tr ng-repeat="contato in contatos">`
* exibindo dado de contato `<td>{{contato.nome}}</td>`

		<table class="table table-striped">
            <tr>
                <th>Nome</th>
                <th>Telefone</th>
            </tr>
            <tr ng-repeat="contato in contatos">
                <td>{{contato.nome}}</td>
                <td>{{contato.telefone}}</td>
            </tr>
        </table>

* atribuindo dado ao `$scope` com `ngModel`
* `ng-model="contato.nome"` sera criado um objeto no `$scope` contato com o atributo nome

		<input type="text" class="form-control" ng-model="contato.nome" placeholder="Nome"/>
		<input type="text" class="form-control" ng-model="contato.telefone" placeholder="Telefone"/>

* disparando um evento com `ngClick`
* enviando o contato com `ng-click="adicionarContato(contato)"`

		<button class="btn btn-primary btn-block" ng-click="adicionarContato(contato)">Adicionar Contato</button>

* criando a funcao de adicionar
* realizando a copia do objeto `$scope.contatos.push(angular.copy(contato));`
* no final removemos o objeto `delete $scope.contato;`

		$scope.adicionarContato = function(contato){
			$scope.contatos.push(angular.copy(contato));
			delete $scope.contato;
		}