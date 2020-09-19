var Nuts = Nuts || {};

Nuts.Grafico2015PontosConectadosPorMes = (function(){
	
	function Grafico2015PontosConectadosPorMes(){
		this.ctx = $('#2015PontosConectadosPorMes')[0].getContext('2d');
		
	}
	
	Grafico2015PontosConectadosPorMes.prototype.iniciar = function(){
		$.ajax({
			url:'atividade/dados/pontosConectados2015',
			method: 'GET',
			success: onDadosRecebidos.bind(this)
		});
	}
	
	function onDadosRecebidos(pontosConectadosPorMes){
		var valores = [];
		
		pontosConectadosPorMes.forEach(function(obj){
			valores.push(obj);
		});
		
		var graficoPontosConectadosPorMes = new Chart(this.ctx, {
		    type: 'bar',
		    data: {
		    	labels : ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
		    	datasets : [{
		    		label : 'Pontos Conectados',
		    		backgroundColor : 'rgba(8, 194, 127, 0.5)',
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
	
	return Grafico2015PontosConectadosPorMes;
	
}());


$(function(){
	var grafico2015PontosConectadosPorMes = new Nuts.Grafico2015PontosConectadosPorMes();
	grafico2015PontosConectadosPorMes.iniciar();
});