var Nuts = Nuts || {};

Nuts.Grafico2016PublicoPorMes = (function(){
	
	function Grafico2016PublicoPorMes(){
		this.ctx = $('#2016PublicoPorMes')[0].getContext('2d');
		
	}
	
	Grafico2016PublicoPorMes.prototype.iniciar = function(){
		$.ajax({
			url:'atividade/dados/Publico2016',
			method: 'GET',
			success: onDadosRecebidos.bind(this)
		});
	}
	
	function onDadosRecebidos(publico2016PorMes){
		var valores = [];
		
		publico2016PorMes.forEach(function(obj){
			valores.push(obj);
		});
		
		var graficoPublico2016PorMes = new Chart(this.ctx, {
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
	
	return Grafico2016PublicoPorMes;
	
}());


$(function(){
	var grafico2016PublicoPorMes = new Nuts.Grafico2016PublicoPorMes();
	grafico2016PublicoPorMes.iniciar();
});