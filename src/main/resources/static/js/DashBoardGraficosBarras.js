var Nuts = Nuts || {};

Nuts.GraficoPublicoPorMes = (function(){
	
	function GraficoPublicoPorMes(){
		this.ctx = $('#publicoPorMes')[0].getContext('2d');
		
	}
	
	GraficoPublicoPorMes.prototype.iniciar = function(){
		$.ajax({
			url:'atividade/dados/participantes',
			method: 'GET',
			success: onDadosRecebidos.bind(this)
		});
	}
	
	function onDadosRecebidos(participantesPorMes){
		var valores = [];
		
		participantesPorMes.forEach(function(obj){
			valores.push(obj);
		});
		
		var graficoPublicoPorMes = new Chart(this.ctx, {
		    type: 'bar',
		    data: {
		    	labels : ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
		    	datasets : [{
		    		label : 'Público Local por mês',
		    		backgroundColor : 'rgba(78, 115, 223, 0.5)',
		    		borderColor: "rgba(78, 115, 223, 1)",
		    		pointBorderColor : 'rgba(2, 6, 8, 0.5)',
		    		pointRadius: 3,
		    	    pointBackgroundColor: "rgba(78, 115, 223, 1)",
		    	    pointBorderColor: "rgba(78, 115, 223, 1)",
		    	    pointHoverRadius: 3,
		    	    pointHoverBackgroundColor: "rgba(78, 115, 223, 1)",
		    	    pointHoverBorderColor: "rgba(78, 115, 223, 1)",
		    	    pointHitRadius: 10,
		    	    pointBorderWidth: 2,
		    		
		    		data : valores
		    	}]
		    },
		});
	}
	
	return GraficoPublicoPorMes;
	
}());


$(function(){
	var graficoPublicoPorMes = new Nuts.GraficoPublicoPorMes();
	graficoPublicoPorMes.iniciar();
});