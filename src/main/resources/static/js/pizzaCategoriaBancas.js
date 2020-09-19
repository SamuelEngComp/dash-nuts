var Nuts = Nuts || {};

Nuts.GraficoPizzaCategoriaBancas = (function(){
	
	function GraficoPizzaCategoriaBancas(){
		this.ctx = $('#pizzaCategoria')[0].getContext('2d');
		
	}
	
	GraficoPizzaCategoriaBancas.prototype.iniciar = function(){
		$.ajax({
			url:'banca/dados/categoria2015bancas',
			method: 'GET',
			success: onDadosRecebidos.bind(this)
		});
	}
	
	function onDadosRecebidos(pizzaCategoriaBancas){
		var valores = [];
		
		pizzaCategoriaBancas.forEach(function(obj){
			
			console.log('Chamou aqui+');
			
			valores.push(obj);
		});
		
//		
		
		var  graficoPizzaCategoriaBancas = new Chart(this.ctx, {
			type: 'pie',
			  data: {
			    labels: ['Nacionais', 'Internacionais'],
			    datasets: [{
			      data: valores,
			      backgroundColor: ['#4e73df', '#1cc88a'],
			      hoverBackgroundColor: ['#2e59d9', '#17a673'],
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
			    cutoutPercentage: 0,
			    plugins:{
			    	labels:{
			    		render:'value',
			    		fontColor: '#fff',
				    	position: 'border'
			    	}
			    }
			    
			  },
		});
	}
	
	return GraficoPizzaCategoriaBancas;
	
}());


$(function(){
	var graficoPizzaCategoriaBancas = new Nuts.GraficoPizzaCategoriaBancas();
	graficoPizzaCategoriaBancas.iniciar();
});