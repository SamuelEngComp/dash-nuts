var Nuts = Nuts || {};

Nuts.Grafico2018PublicoPorMes = (function(){
	
	function Grafico2018PublicoPorMes(){
		this.ctx = $('#2018PublicoPorMes')[0].getContext('2d');
		
	}
	
	Grafico2018PublicoPorMes.prototype.iniciar = function(){
		$.ajax({
			url:'atividade/dados/Publico2018',
			method: 'GET',
			success: onDadosRecebidos.bind(this)
		});
	}
	
	function onDadosRecebidos(publico2018PorMes){
		var valores = [];
		
		publico2018PorMes.forEach(function(obj){
			valores.push(obj);
		});
		
		var graficoPublico2018PorMes = new Chart(this.ctx, {
		    type: 'bar',
		    data: {
		    	labels : ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
		    	datasets : [{
		    		label : 'Público local',
		    		backgroundColor : 'rgba(73, 48, 255, 0.5)',
		    		borderColor: "rgba(78, 115, 223, 1)",
		    		pointBorderColor : 'rgba(2, 6, 8, 0.5)',
		    		pointRadius: 3,
		    	    pointBackgroundColor: "rgba(8, 130, 124, 1)",
		    	    pointBorderColor: "rgba(78, 115, 223, 1)",
		    	    pointHoverRadius: 3,
		    	    pointHoverBackgroundColor: "rgba(8, 130, 124, 1)",
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
	
	return Grafico2018PublicoPorMes;
	
}());


$(function(){
	var grafico2018PublicoPorMes = new Nuts.Grafico2018PublicoPorMes();
	grafico2018PublicoPorMes.iniciar();
});