var Nuts = Nuts || {};

Nuts.GraficoAtividades2019PorMes = (function(){
	
	function GraficoAtividades2019PorMes(){
		this.ctx = $('#atividades2019PorMes')[0].getContext('2d');
		
	}
	
	GraficoAtividades2019PorMes.prototype.iniciar = function(){
		$.ajax({
			url:'atividade/dados/atividades2019',
			method: 'GET',
			success: onDadosRecebidos.bind(this)
		});
	}
	
	function onDadosRecebidos(atividades2019PorMes){
		var valores = [];
		
		atividades2019PorMes.forEach(function (obj){
			valores.push(obj);
		});
		
		var graficoAtividade2019PorMes = new Chart(this.ctx, {
		    type: 'line',
		    data: {
		    	labels : ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
		    	datasets : [{
		    		label : 'Atividades',
		    		backgroundColor : 'rgba(119, 139, 145, 0.5)',
		    		borderColor: "rgba(119, 139, 145, 1)",
		    		pointBorderColor : 'rgba(2, 6, 8, 0.5)',
		    		pointRadius: 3,
		    	    pointBackgroundColor: "rgba(119, 139, 145, 0.5)", //rgba(78, 115, 223, 1)
		    	    pointBorderColor: "rgba(119, 139, 145, 1)",
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
	
	return GraficoAtividades2019PorMes;
	
}());


$(function(){
	var graficoAtividade2019PorMes = new Nuts.GraficoAtividades2019PorMes();
	graficoAtividade2019PorMes.iniciar();
});