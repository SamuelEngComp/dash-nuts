var Nuts = Nuts || {};

Nuts.Grafico2021PontosConectadosPorMes = (function(){
	
	function Grafico2021PontosConectadosPorMes(){
		this.ctx = $('#2021PontosConectadosPorMes')[0].getContext('2d');
		
	}
	
	Grafico2021PontosConectadosPorMes.prototype.iniciar = function(){
		$.ajax({
			url:'atividade/dados/pontosConectados2021',
			method: 'GET',
			success: onDadosRecebidos.bind(this)
		});
	}
	
	function onDadosRecebidos(pontosConectados2021PorMes){
		var valores = [];
		
		pontosConectados2021PorMes.forEach(function(obj){
			valores.push(obj);
		});
		
		
		////////////////////////////////////////////////////////////
		
				
		///////////////////////////////////////////////////////////
		
		
		
		
		
		var graficoPontosConectados2021PorMes = new Chart(this.ctx, {
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
	
	return Grafico2021PontosConectadosPorMes;
	
}());


$(function(){
	var grafico2021PontosConectadosPorMes = new Nuts.Grafico2021PontosConectadosPorMes();
	grafico2021PontosConectadosPorMes.iniciar();
});