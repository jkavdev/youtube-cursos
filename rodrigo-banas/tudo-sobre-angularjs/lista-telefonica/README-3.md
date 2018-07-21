# Lista Telefonica com AngularJS

# Performance

* `::` significa one time binding, estamos indicando ao angular que a expressao sera gerenciada apenas uma vez durante ciclo de renderizacao
* o angular reduzira os watches para os campos indicados com `::`, `<td>{{::contato.serial}}</td>`, sera gerenciado apenas uma vez durante a renderizacao da pagina

        <table class="table table-striped" ng-show="contatos.length > 0">
            .....................................
            <tr ng-class="{selecionado: contato.selecionado, negrito: contato.selecionado}" ng-repeat="contato in contatos | limitTo:10 | filter:{nome: criterioDeBusca} | orderBy:criterioDeOrdenacao:direcaoDeOrdenacao">
                <td>{{::contato.serial}}</td>
                <td>{{::contato.telefone}}</td>
                <td>{{::contato.operadora.nome | uppercase}}</td>
                <td>{{::contato.operadora.precoComImposto | currency}}</td>
                <td>{{::contato.data | date: 'dd/MM/yyyy HH:mm'}}</td>
            </tr>
        </table>
        
* evitando que o angular execute muitas logicas desnecessarias em componentes gerenciados pelo angular na view
* alterando forma de verificar se o registro esta selecionado
* teremos um atributo indicando o estado selecionado `ng-disabled="!hasContatoSelecionado"`

        <button class="btn btn-danger btn-block" ng-click="apagarContato(contatos)" ng-disabled="!hasContatoSelecionado" ng-if="contatos.length > 0">Apagar Contato</button>
        
* verificando estado        

        $scope.verificarContatoSelecionado = function (contatos) {
            $scope.hasContatoSelecionado = contatos.some(function (contato) {
                return contato.selecionado;
            });
        };        
        
* soh sera verificado se esta selecionado apenas quando selecionar o checkbox de selecionado

        <td><input type="checkbox" ng-model="contato.selecionado" ng-change="verificarContatoSelecionado(contatos)" /></td>   

* diminuindo o tempo de verificacao do filtro de registros na tela
* `ng-model-options="{updateOn: 'default blur', debounce: {default: 500, blur: 0}}"`
* `updateOn: 'default blur'` estamos indicando que ao sair do campo o model sera atualizado
* configurando o tempo de resposta para atualizacao do model `debounce: {default: 500, blur: 0}}"`, `default: 500` tempo a atualizar, tempo a atualizar caso perda o foco do campo `blur: 0`

        <input class="form-control" type="text" ng-model="criterioDeBusca" placeholder="O que vc estah buscando?"
            ng-model-options="{updateOn: 'default blur', debounce: {default: 500, blur: 0}}"/>
            
* Diferenca entre ngIf e ngShow

* `ngIf` temos menos watchers e o tempo de resposta mais alto, demora mais 
* `ngShow` temos mais watchers e o tempo de resposta e menos, demora menos 

* mas se minha pagina contem muitos elementos o numero de watcher pode ficar muito grande, sendo
* preferivel utilizar o `ngIf` com menos watchers

* podemos ainda ter uma lazy-loading em recursos
* com `ng-src` indicamos que o recurso estara pront apenas quando renderizado
* se tivessemos utilizado apenas `src` o recurso seria carregado junto com a pagina 

        <div ng-if="showImage">
            <img ng-src="/img/github-mark.png" width="300" />
        </div>

* Utilizando o filter no controller
* ao inves de utilizar um filter na view

        <h3>{{app | upper}}</h3>
        
* podemos diminuir o acesso ao filter, realizando o filter no controller        

        angular.module("listaTelefonica").controller("listaTelefonicaCtrl", function ($scope, $filter) {
            $scope.app = $filter('upper')("Lista Telefonica");
        }            

