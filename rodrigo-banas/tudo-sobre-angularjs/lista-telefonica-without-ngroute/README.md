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

* disabilitando o botao de adicionar antes que os dados obrigatorios forem preenchidos
* com `ng-disabled="!contato.nome || !contato.telefone"` 

		<button class="btn btn-primary btn-block" ng-click="adicionarContato(contato)" ng-disabled="!contato.nome || !contato.telefone">Adicionar Contato</button>

* removendo um contato
* adicionando um input que indicara o selecionado `ng-model="contato.selecionado"`

		<td><input type="checkbox" ng-model="contato.selecionado" /></td>

* criando o botao para remover

		<button class="btn btn-danger btn-block" ng-click="apagarContato(contatos)">Apagar Contato</button>

* funcao de remocao dos contatos
* aqui realizamos uma filtragem dos dados, retornando apenas os nao selecionados

		$scope.apagarContato = function (contatos) {
			$scope.contatos = contatos.filter(function (contato) {
				if (!contato.selecionado) return contato;
			});
		}

* disabilitando o botao de remocao caso nenhum contato selecionado

		<button class="btn btn-danger btn-block" ng-click="apagarContato(contatos)" ng-disabled="!isContatoSelecionado(contatos)">Apagar Contato</button>

* funcao de verificacao de selecionado
* retornara true caso algum esteja selecionado

		$scope.isContatoSelecionado = function (contatos) {
			return contatos.some(function (contato) {
				return contato.selecionado;
			});
		}

* adicionando um options de operadora
* `operadora in operadoras` indicando de onde vem os dados
* `operadora.nome for operadora in operadoras` dando um nome para escolher no options
* `operadora.codigo as operadora.nome for operadora in operadoras` indicando o valor a ser enviado quando selecionado
* `operadora.nome group by operadora.categoria for operadora in operadoras` indicando que os valores seram agrupados pela categoria
* `<option value="">Selecione uma operadora</option>` adicionando um valor padrao ao options

		<select class="form-control" ng-model="contato.operadora" ng-options="operadora.nome group by operadora.categoria for operadora in operadoras">
            <option value="">Selecione uma operadora</option>
        </select>

* arrays de operadoras

		$scope.operadoras = [
			{ nome: 'Vivo', codigo: 14, categoria: 'Celular' },
			.........
		]

* exibindo apenas o nome da operadora

		<td>{{contato.operadora.nome}}</td>

* alterando estilo dinamicamente, quando selecionar um contato
* `ng-class="{selecionado: contato.selecionado, negrito: contato.selecionado}"` indicando que seram adicionados as classes de css, caso o contato esteja selecionado

	<tr ng-class="{selecionado: contato.selecionado, negrito: contato.selecionado}" ng-repeat="contato in contatos">
		<td>
			<input type="checkbox" ng-model="contato.selecionado" />
		</td>
	</tr>

* estilo a ser aplicado

		.selecionado {
            background-color: yellow;
        }

        .negrito {
            font-weight: bold;
        }

* aplicado valores de estilo dinamicamente, teremos uma cor que virar com o contato, e aplicado a um campo
* com `ng-style="{'background-color': contato.cor}"` indicamos que teremos um cor para o estilo `background-color`

		<td><div style="width: 20px; height: 20px;" ng-style="{'background-color': contato.cor}"></div></td>

* contatos com as cores

		$scope.contatos = [
			{ nome: 'Jhonatan', telefone: '12345678', cor: 'blue' },
			{ nome: 'Lucas', telefone: '12344618', cor: 'black' },
			{ nome: 'Maria', telefone: '12345615', cor: 'red' },
		];

* renderizacao de componentes, com ngIf, ngShow, ngHide
* exibindo a tabelas apenas quando tiver elementos
* com `ng-show="contatos.length > 0"` o html ficara apenas com display:none, sendo possivel acessa-lo atraves do html gerado

		<table class="table table-striped" ng-show="contatos.length > 0">

* com `ng-if="contatos.length > 0"` o html nao eh gerado, nao sendo possivel acessa-lo, caso de falso a expressao

		<button class="btn btn-danger btn-block" ng-click="apagarContato(contatos)" ng-disabled="!isContatoSelecionado(contatos)" ng-if="contatos.length > 0">Apagar Contato</button>

* realizando a inclusao de paginas html
* com `ng-include="'footer.html'"` indicamos o caminho ate a imagem com `'caminho'`

		<div ng-include="'footer.html'"></div>

* validando campos com `ngRequired`		
* indicando que o campo eh obrigatorio `ng-required="true"`

		<input type="text" ng-model="contato.nome" ng-required="true" />

* para a validacao ser realizada o elemento deve esta contido em um form

		<form name="contatoForm">
            <input type="text" ng-model="contato.nome" ng-required="true" />
			..................
        </form>

* teremos uma `div` que exibira o conteudo dos erros de acordo com os campos do formulario
* podemos identificar os campos dando um nome para tal `name="nome"`
* sera exibido caso o campo nome do formulario nao esteja preenchido `ng-show="contatoForm.nome.$invalid"`

		<input type="text" ng-model="contato.nome" name="nome" ng-required="true" />

		<div class="alert alert-danger" ng-show="contatoForm.nome.$invalid">
            Preencha o nome!
        </div>

* exibindo varias mensagens de erro
* como temos varios campos que podemos gerar erros, podemos acessalos da propriedade `$error` do `ngForm`

		<form name="contatoForm">
			<input type="text" class="form-control" ng-model="contato.nome" name="nome" placeholder="Nome" ng-required="true" ng-minlength="10" />
            <input type="text" class="form-control" ng-model="contato.telefone" name="telefone" placeholder="Telefone" ng-required="true"
                ng-pattern="/^\d{4,5}-\d{4}$/" />
        </form>

        <div class="alert alert-danger" ng-show="contatoForm.nome.$error.required && contatoForm.nome.$dirty">
            Preencha o nome!
        </div>
        <div class="alert alert-danger" ng-show="contatoForm.nome.$error.minlength">
            O campo nome deve conter no mínimo 10 caracteres!
        </div>
        <div class="alert alert-danger" ng-show="contatoForm.telefone.$error.required && contatoForm.telefone.$dirty">
            Preencha o telefone!
        </div>
        <div class="alert alert-danger" ng-show="contatoForm.telefone.$error.pattern">
            O campo telefone deve ter o formato DDDDD-DDDD!
        </div>

* utilizando o `ng-minlength="10"` para indicar o tamanho do campo

		<input type="text" class="form-control" ng-model="contato.nome" name="nome" placeholder="Nome" ng-required="true" ng-minlength="10" />

* exibindo mensagem de erro sobre o tamanho to campo		

		<div class="alert alert-danger" ng-show="contatoForm.nome.$error.minlength">
            O campo nome deve conter no mínimo 10 caracteres!
        </div>

* utilizando o `ng-pattern="/^\d{4,5}-\d{4}$/"` para indicar um padrao para o campo de telefone

		<input type="text" class="form-control" ng-model="contato.telefone" name="telefone" placeholder="Telefone" ng-required="true" ng-pattern="/^\d{4,5}-\d{4}$/" />

* exibindo mensagem de erro sobre o padrao to campo		

		<div class="alert alert-danger" ng-show="contatoForm.telefone.$error.pattern">
            O campo telefone deve ter o formato DDDDD-DDDD!
        </div>

* adicionando modulo de mensagens do angularjs

		<script src="js/angular/angular-messages.js"></script>

* declarando como dependencias do modulo da aplicacao

		angular.module("listaTelefonica", ["ngMessages"]);

* utilizando modulo de mensagens
* indicando de quem sera tratado os erros `ng-messages="contatoForm.nome.$error"`
* `ng-message="required"` indicando qual erro a ser exibido

		<div class="alert alert-danger" ng-messages="contatoForm.nome.$error">
            <div ng-message="required" ng-show="contatoForm.nome.$dirty">
                Preencha o nome!
            </div>
            <div ng-message="minlength">
                O campo nome deve conter no mínimo 10 caracteres!
            </div>
        </div>

# Filtros

* indicando para o campo nome ficar em maiusculo

		<td>{{contato.nome | uppercase | limitTo:5}}</td>

* adicionando campo de busca dos contatos
* criamos um input para informar o que buscar

		<input class="form-control" type="text" ng-model="criterioDeBusca" placeholder="O que vc estah buscando?">

* indicamos na listagem o filtro a ser aplicado `filter:{nome: criterioDeBusca}`

		<tr ng-repeat="contato in contatos | limitTo:10 | filter:{nome: criterioDeBusca} | orderBy:'criterioDeOrdenacao':direcaoDeOrdenacao">
			........
		</tr>

* adicionando filtro de data

		{ nome: 'Lucas', telefone: '12344618', cor: 'black', data: new Date(), ....},

* exibindo a o dia/mes/ano hora:min

		<td>{{contato.data | date: 'dd/MM/yyyy HH:mm'}}</td>

* adicionando filtro de moeda e aplicando ordenacao dos dados
* estamos concatenando o valor e transformando o preco para moeda `operadora.nome + '( '+ (operadora.preco | currency) +' )'`

		<select class="form-control" ng-model="contato.operadora" ng-options="operadora.nome + '( '+ (operadora.preco | currency) +' )' group by operadora.categoria for operadora in operadoras | orderBy:'nome'">
			....
		</select>

* adicionando atributo preco

		{ nome: 'Vivo', codigo: 14, categoria: 'Celular', preco:2 },

* adicionando ordenacao por nome e telefone
* teremos links nos titulos que ordenaram os registros, passando o nome do campo como criterio

		<th><a href="" ng-click="ordenarPor('nome')">Nome</a></th>
		<th><a href="" ng-click="ordenarPor('telefone')">Telefone</a></th>

* criacao da funcao
* indicamos tambem a direcao, se eh ASC ou DESC com `$scope.direcaoDeOrdenacao = !$scope.direcaoDeOrdenacao;`

		$scope.ordenarPor = function (campo) {
			$scope.criterioDeOrdenacao = campo;
			$scope.direcaoDeOrdenacao = !$scope.direcaoDeOrdenacao;
		}

* aplicando o filter no array junto com a ordenacao

		<tr ng-repeat="contato in contatos | filter:{nome: criterioDeBusca} | orderBy:'criterioDeOrdenacao':direcaoDeOrdenacao">

* limitando a quantidade de registros exibidos com filter ` | limitTo:10 |`

		<tr ng-repeat="contato in contatos | limitTo:10 | filter:{nome: criterioDeBusca} | orderBy:'criterioDeOrdenacao':direcaoDeOrdenacao">

* podemos aplicar tambem para campos textos

		<td>{{contato.nome | uppercase | limitTo:5}}</td>