var Nuts = Nuts || {};

Nuts.GraficoAtividades2016PorMes = (function(){
	
	function GraficoAtividades2016PorMes(){
		this.ctx = $('#atividades2016PorMes')[0].getContext('2d');
		
	}
	
	GraficoAtividades2016PorMes.prototype.iniciar = function(){
		$.ajax({
			url:'atividade/dados/atividades2016',
			method: 'GET',
			success: onDadosRecebidos.bind(this)
		});
	}
	
	function onDadosRecebidos(atividades2016PorMes){
		var valores = [];
		
		atividades2016PorMes.forEach(function (obj){
			valores.push(obj);
		});
		
		var graficoAtividade2016PorMes = new Chart(this.ctx, {
		    type: 'line',
		    data: {
		    	labels : ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
		    	datasets : [{
		    		label : 'Atividades',
		    		backgroundColor : 'rgba(7, 235, 212, 0.5)',
		    		borderColor: "rgba(7, 235, 212, 1)",
		    		pointBorderColor : 'rgba(2, 6, 8, 0.5)',
		    		pointRadius: 3,
		    	    pointBackgroundColor: "rgba(255, 255, 255, 1)", //rgba(78, 115, 223, 1)
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
	
	return GraficoAtividades2016PorMes;
	
}());


$(function(){
	var graficoAtividade2016PorMes = new Nuts.GraficoAtividades2016PorMes();
	graficoAtividade2016PorMes.iniciar();
});