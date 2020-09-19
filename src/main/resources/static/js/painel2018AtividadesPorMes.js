var Nuts = Nuts || {};

Nuts.GraficoAtividades2018PorMes = (function(){
	
	function GraficoAtividades2018PorMes(){
		this.ctx = $('#atividades2018PorMes')[0].getContext('2d');
		
	}
	
	GraficoAtividades2018PorMes.prototype.iniciar = function(){
		$.ajax({
			url:'atividade/dados/atividades2018',
			method: 'GET',
			success: onDadosRecebidos.bind(this)
		});
	}
	
	function onDadosRecebidos(atividades2018PorMes){
		var valores = [];
		
		atividades2018PorMes.forEach(function (obj){
			valores.push(obj);
		});
		
		var graficoAtividade2018PorMes = new Chart(this.ctx, {
		    type: 'line',
		    data: {
		    	labels : ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
		    	datasets : [{
		    		label : 'Atividades',
		    		backgroundColor : 'rgba(255, 0, 230, 0.5)',
		    		borderColor: "rgba(181, 33, 166, 1)",
		    		pointBorderColor : 'rgba(2, 6, 8, 0.5)',
		    		pointRadius: 3,
		    	    pointBackgroundColor: "rgba(181, 33, 166, 1)", //rgba(78, 115, 223, 1)
		    	    pointBorderColor: "rgba(247, 0, 4, 1)",
		    	    pointHoverRadius: 3,
		    	    pointHoverBackgroundColor: "rgba(78, 115, 223, 1)",
		    	    pointHoverBorderColor: "rgba(61, 140, 46, 1)",
		    	    pointHitRadius: 10,
		    	    pointBorderWidth: 2,
		    		
		    		data : valores
		    	}] 
		    },
		});
	}
	
	return GraficoAtividades2018PorMes;
	
}());


$(function(){
	var graficoAtividade2018PorMes = new Nuts.GraficoAtividades2018PorMes();
	graficoAtividade2018PorMes.iniciar();
});