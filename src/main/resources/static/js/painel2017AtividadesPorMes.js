var Nuts = Nuts || {};

Nuts.GraficoAtividades2017PorMes = (function(){
	
	function GraficoAtividades2017PorMes(){
		this.ctx = $('#atividades2017PorMes')[0].getContext('2d');
		
	}
	
	GraficoAtividades2017PorMes.prototype.iniciar = function(){
		$.ajax({
			url:'atividade/dados/atividades2017',
			method: 'GET',
			success: onDadosRecebidos.bind(this)
		});
	}
	
	function onDadosRecebidos(atividades2017PorMes){
		var valores = [];
		
		atividades2017PorMes.forEach(function (obj){
			valores.push(obj);
		});
		
		var graficoAtividade2017PorMes = new Chart(this.ctx, {
		    type: 'line',
		    data: {
		    	labels : ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
		    	datasets : [{
		    		label : 'Atividades',
		    		backgroundColor : 'rgba(247, 0, 4, 0.5)',
		    		borderColor: "rgba(247, 0, 4, 1)",
		    		pointBorderColor : 'rgba(2, 6, 8, 0.5)',
		    		pointRadius: 3,
		    	    pointBackgroundColor: "rgba(255, 255, 255, 1)", //rgba(78, 115, 223, 1)
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
	
	return GraficoAtividades2017PorMes;
	
}());


$(function(){
	var graficoAtividade2017PorMes = new Nuts.GraficoAtividades2017PorMes();
	graficoAtividade2017PorMes.iniciar();
});