var Nuts = Nuts || {};

Nuts.Grafico2017PontosConectadosPorMes = (function(){
	
	function Grafico2017PontosConectadosPorMes(){
		this.ctx = $('#2017PontosConectadosPorMes')[0].getContext('2d');
		
	}
	
	Grafico2017PontosConectadosPorMes.prototype.iniciar = function(){
		$.ajax({
			url:'atividade/dados/pontosConectados2017',
			method: 'GET',
			success: onDadosRecebidos.bind(this)
		});
	}
	
	function onDadosRecebidos(pontosConectados2017PorMes){
		var valores = [];
		
		pontosConectados2017PorMes.forEach(function(obj){
			valores.push(obj);
		});
		
		var graficoPontosConectados2017PorMes = new Chart(this.ctx, {
		    type: 'bar',
		    data: {
		    	labels : ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
		    	datasets : [{
		    		label : 'Pontos Conectados',
		    		backgroundColor : 'rgba(235, 200, 7, 0.5)',
		    		borderColor: "rgba(78, 115, 223, 1)",
		    		pointBorderColor : 'rgba(2, 6, 8, 0.5)',
		    		pointRadius: 3,
		    	    pointBackgroundColor: "rgba(25, 156, 109, 1)",
		    	    pointBorderColor: "rgba(78, 115, 223, 1)",
		    	    pointHoverRadius: 3,
		    	    pointHoverBackgroundColor: "rgba(25, 156, 109, 1)",
		    	    pointHoverBorderColor: "rgba(78, 115, 223, 1)",
		    	    pointHitRadius: 10,
		    	    pointBorderWidth: 2,
		    		
		    		data : valores
		    	}]
		    },
		    
		    options:{
		    	plugins:{
		    		labels:{
		    			render:'value'	
		    		}
		    	}
		    }
		    
		});
	}
	
	return Grafico2017PontosConectadosPorMes;
	
}());


$(function(){
	var grafico2017PontosConectadosPorMes = new Nuts.Grafico2017PontosConectadosPorMes();
	grafico2017PontosConectadosPorMes.iniciar();
});