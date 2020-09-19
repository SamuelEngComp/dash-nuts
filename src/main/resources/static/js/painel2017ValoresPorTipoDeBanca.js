var Nuts = Nuts || {};

Nuts.GraficoPizzaValores2017Bancas = (function(){
	
	function GraficoPizzaValores2017Bancas(){
		this.ctx = $('#pizzavalores2017')[0].getContext('2d');
		
	}
	
	GraficoPizzaValores2017Bancas.prototype.iniciar = function(){
		$.ajax({
			url:'banca/dados/valoresbancas2017porano',
			method: 'GET',
			success: onDadosRecebidos.bind(this)
		});
	}
	
	function onDadosRecebidos(pizzaValores2017Bancas){
		var valores = [];
		
		pizzaValores2017Bancas.forEach(function(obj){
			
			valores.push(obj);
		});
		
//		
		
		var  graficoPizzaValores2017Bancas = new Chart(this.ctx, {
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
	
	return GraficoPizzaValores2017Bancas;
	
}());


$(function(){
	var graficoPizzaValores2017Bancas = new Nuts.GraficoPizzaValores2017Bancas();
	graficoPizzaValores2017Bancas.iniciar();
});