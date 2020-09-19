var Nuts = Nuts || {};

Nuts.Grafico2015PublicoPorMes = (function(){
	
	function Grafico2015PublicoPorMes(){
		this.ctx = $('#2015PublicoPorMes')[0].getContext('2d');
		
	}
	
	Grafico2015PublicoPorMes.prototype.iniciar = function(){
		$.ajax({
			url:'atividade/dados/Publico2015',
			method: 'GET',
			success: onDadosRecebidos.bind(this)
		});
	}
	
	function onDadosRecebidos(publicoPorMes){
		var valores = [];
		
		publicoPorMes.forEach(function(obj){
			valores.push(obj);
		});
		
		var graficoPublicoPorMes = new Chart(this.ctx, {
		    type: 'bar',
		    data: {
		    	labels : ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
		    	datasets : [{
		    		label : 'Público local',
		    		backgroundColor : 'rgba(16, 201, 192, 0.5)',
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
	
	return Grafico2015PublicoPorMes;
	
}());


$(function(){
	var grafico2015PublicoPorMes = new Nuts.Grafico2015PublicoPorMes();
	grafico2015PublicoPorMes.iniciar();
});