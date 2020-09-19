var Nuts = Nuts || {};

Nuts.GraficoPizzaCategoria2018Bancas = (function(){
	
	function GraficoPizzaCategoria2018Bancas(){
		this.ctx = $('#pizzaCategoria2018')[0].getContext('2d');
		
	}
	
	GraficoPizzaCategoria2018Bancas.prototype.iniciar = function(){
		$.ajax({
			url:'banca/dados/categoria2018bancas',
			method: 'GET',
			success: onDadosRecebidos.bind(this)
		});
	}
	
	function onDadosRecebidos(pizzaCategoria2018Bancas){
		var valores = [];
		
		pizzaCategoria2018Bancas.forEach(function(obj){
			
			valores.push(obj);
		});
		
//		
		
		var  graficoPizzaCategoria2018Bancas = new Chart(this.ctx, {
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
	
	return GraficoPizzaCategoria2018Bancas;
	
}());


$(function(){
	var graficoPizzaCategoria2018Bancas = new Nuts.GraficoPizzaCategoria2018Bancas();
	graficoPizzaCategoria2018Bancas.iniciar();
});