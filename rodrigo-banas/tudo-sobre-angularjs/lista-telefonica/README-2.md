# Lista Telefonica com AngularJS

# Disponibilizando modulos

* disponibilizando um como com o gerador de serial
* criando o modulo do gerador `angular.module("serialGenerator", []);`
* declaracao do provider com o modulo do gerador `angular.module("serialGenerator").provider("serialGenerator", function () {`

        angular.module("serialGenerator", []);
        angular.module("serialGenerator").provider("serialGenerator", function () {
            ...................
        }); 
        
* importando o modulo

        <script src="lib/serialGenerator/serialGeneratorService.js"></script>
        
* indicando que o modulo da aplicacao utilizara o modulo do gerador de serial

        angular.module("listaTelefonica", ["ngMessages", "serialGenerator"]); 
        
* modularizando o componente accordion
* criando a modulo `angular.module("ui", []);`
* criando as diretivas com o modulo criado `angular.module("ui").directive("uiAccordions", function () {}`
* como o componente define uma diretiva visual, podemos indicar o html com `$templateCache`   
* registrando templatendo com o run do modulo `angular.module("ui").run(function ($templateCache) {})`
* onde o `$templateCache` eh um map `$templateCache.put("view/accordion.html", '<div class="ui-accordion-title" ng-click="open()">{{title}}</div> <div class="ui-accordion-content" ng-show="isOpened" ng-transclude></div>')`


        angular.module("ui", []);
        
        angular.module("ui").run(function ($templateCache) {
            $templateCache.put("view/accordion.html", '<div class="ui-accordion-title" ng-click="open()">{{title}}</div> <div class="ui-accordion-content" ng-show="isOpened" ng-transclude></div>')
        })
        
        angular.module("ui").directive("uiAccordions", function () {
            ............
        });
        
        angular.module("ui").directive("uiAccordion", function () {
            ............
        });      
        
                  