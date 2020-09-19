var Nuts = Nuts || {};

Nuts.GraficoPizzaTipo2020Bancas = (function(){
	
	function GraficoPizzaTipo2020Bancas(){
		this.ctx = $('#pizzatipo2020')[0].getContext('2d');
		
	}
	
	GraficoPizzaTipo2020Bancas.prototype.iniciar = function(){
		$.ajax({
			url:'banca/dados/tiposdebancas2020porano',
			method: 'GET',
			success: onDadosRecebidos.bind(this)
		});
	}
	
	function onDadosRecebidos(pizzaTipo2020Bancas){
		var valores = [];
		
		pizzaTipo2020Bancas.forEach(function(obj){
			
			valores.push(obj);
		});
		
		//////////////////////////////////////////////////////////////////////
		
				
		///////////////////////////////////////////////////////////////////
		
		
		var  graficoPizzaTipo2020Bancas = new Chart(this.ctx, {
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
			    cutoutPercentage: 50,
			    
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
	
	return GraficoPizzaTipo2020Bancas;
	
}());


$(function(){
	var graficoPizzaTipo2020Bancas = new Nuts.GraficoPizzaTipo2020Bancas();
	graficoPizzaTipo2020Bancas.iniciar();
});