var Nuts = Nuts || {};

Nuts.GraficoAtividadesPorMes = (function(){
	
	function GraficoAtividadesPorMes(){
		this.ctx = $('#atividadesPorMes')[0].getContext('2d');
		
	}
	
	GraficoAtividadesPorMes.prototype.iniciar = function(){
		$.ajax({
			url:'atividade/dados',
			method: 'GET',
			success: onDadosRecebidos.bind(this)
		});
	}
	
	function onDadosRecebidos(atividadesPorMes){
		var valores = [];
		
		atividadesPorMes.forEach(function (obj){
			valores.push(obj);
		});
		
		var graficoAtividadePorMes = new Chart(this.ctx, {
		    type: 'line',
		    data: {
		    	labels : ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
		    	datasets : [{
		    		label : 'Atividades por mês',
		    		backgroundColor : 'rgba(78, 115, 223, 0.5)',
		    		borderColor: "rgba(78, 115, 223, 1)",
		    		pointBorderColor : 'rgba(2, 6, 8, 0.5)',
		    		pointRadius: 3,
		    	    pointBackgroundColor: "rgba(255, 255, 255, 0.5)", //rgba(78, 115, 223, 1)
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
	
	return GraficoAtividadesPorMes;
	
}());


$(function(){
	var graficoAtividadePorMes = new Nuts.GraficoAtividadesPorMes();
	graficoAtividadePorMes.iniciar();
});