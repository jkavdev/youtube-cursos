angular.module("listaTelefonica").factory("errorInterceptor", function ($q, $location) {

    return {

        responseError: function (error) {
            console.log(error);

            if(error.status === 404){
                $location.path("/error")
            }

            return $q.reject(error);
        }

    }


});