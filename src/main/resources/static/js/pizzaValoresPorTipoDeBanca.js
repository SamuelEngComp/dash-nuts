var Nuts = Nuts || {};

Nuts.GraficoPizzaValoresBancas = (function(){
	
	function GraficoPizzaValoresBancas(){
		this.ctx = $('#pizzavalores')[0].getContext('2d');
		
	}
	
	GraficoPizzaValoresBancas.prototype.iniciar = function(){
		$.ajax({
			url:'banca/dados/valoresbancas2015porano',
			method: 'GET',
			success: onDadosRecebidos.bind(this)
		});
	}
	
	function onDadosRecebidos(pizzaValoresBancas){
		var valores = [];
		
		pizzaValoresBancas.forEach(function(obj){
			
			console.log('Chamou aqui+');
			
			valores.push(obj);
		});
		
//		
		
		var  graficoPizzaValoresBancas = new Chart(this.ctx, {
			type: 'pie',
			  data: {
			    labels: ['TCC', 'TCR','Quali-Mestrado', 'Quali-Doutorado', 'Dissertação Mestrado', 'Tese Doutorado'],
			    datasets: [{
			      data: valores,
			      backgroundColor: ['#4e73df', '#1cc88a', '#4ed5df', '#ff1000', '#ffbb00', '#8c7b7b'],
			      hoverBackgroundColor: ['#2e59d9', '#17a673', '#63b9bf', '#c72c22', '#c99b1a', '#736464'],
			      hoverBorderColor: "rgba(234, 236, 244, 1)",
			    }],
			  },
			  options: {
			    maintainAspectRatio: false,
			    tooltips: {
			      backgroundColor: "rgb(255,255,255)",
			      bodyFontColor: "#858796",
			      borderColor: '#dddfeb',
			      borderWidth: 1,
			      xPadding: 15,
			      yPadding: 15,
			      displayColors: false,
			      caretPadding: 10,
			    },
			    legend: {
			      display: false
			    },
			    cutoutPercentage: 0, //50
			    plugins:{
			    	labels:{
			    		render: function (args) {
			    		    // args will be something like:
			    		    // { label: 'Label', value: 123, percentage: 50, index: 0, dataset: {...} }
			    		    return 'R$ ' + args.value;

			    		    // return object if it is image
			    		    // return { src: 'image.png', width: 16, height: 16 };
			    		  },
			    		fontColor: '#fff',
			    		precision: 2,
			    		position: 'border'
			    	}
			    }
			    
			  },
		});
	}
	
	return GraficoPizzaValoresBancas;
	
}());


$(function(){
	var graficoPizzaValoresBancas = new Nuts.GraficoPizzaValoresBancas();
	graficoPizzaValoresBancas.iniciar();
});