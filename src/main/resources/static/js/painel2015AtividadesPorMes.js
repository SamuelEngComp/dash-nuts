var Nuts = Nuts || {};

Nuts.GraficoAtividades2015PorMes = (function(){
	
	function GraficoAtividades2015PorMes(){
		this.ctx = $('#atividades2015PorMes')[0].getContext('2d');
		
	}
	
	GraficoAtividades2015PorMes.prototype.iniciar = function(){
		$.ajax({
			url:'atividade/dados/atividades2015',
			method: 'GET',
			success: onDadosRecebidos.bind(this)
		});
	}
	
	function onDadosRecebidos(atividades2015PorMes){
		var valores = [];
		
		atividades2015PorMes.forEach(function (obj){
			valores.push(obj);
		});
		
		var graficoAtividade2015PorMes = new Chart(this.ctx, {
		    type: 'line',
		    data: {
		    	labels : ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
		    	datasets : [{
		    		label : 'Atividades',
		    		backgroundColor : 'rgba(61, 201, 36, 0.5)',
		    		borderColor: "rgba(61, 140, 46, 1)",
		    		pointBorderColor : 'rgba(2, 6, 8, 0.5)',
		    		pointRadius: 3,
		    	    pointBackgroundColor: "rgba(255, 255, 255, 0.5)", //rgba(78, 115, 223, 1)
		    	    pointBorderColor: "rgba(61, 140, 46, 1)",
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
	
	return GraficoAtividades2015PorMes;
	
}());


$(function(){
	var graficoAtividade2015PorMes = new Nuts.GraficoAtividades2015PorMes();
	graficoAtividade2015PorMes.iniciar();
});