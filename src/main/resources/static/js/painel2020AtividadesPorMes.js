var Nuts = Nuts || {};

Nuts.GraficoAtividades2020PorMes = (function(){
	
	function GraficoAtividades2020PorMes(){
		this.ctx = $('#atividades2020PorMes')[0].getContext('2d');
		
	}
	
	GraficoAtividades2020PorMes.prototype.iniciar = function(){
		$.ajax({
			url:'atividade/dados/atividades2020',
			method: 'GET',
			success: onDadosRecebidos.bind(this)
		});
	}
	
	function onDadosRecebidos(atividades2020PorMes){
		var valores = [];
		
		atividades2020PorMes.forEach(function (obj){
			valores.push(obj);
		});
		
		///////////////////////////////////////////////////
		
		
		
		
		
		
		/////////////////////////////////////////////////
		
		
		var graficoAtividade2020PorMes = new Chart(this.ctx, {
			
		    type: 'line',
		    data: {
		    	labels : ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
		    	datasets : [{
		    		label : 'Atividades',
		    		backgroundColor : 'rgba(252, 189, 0, 0.5)',
		    		borderColor: "rgba(252, 189, 0, 1)",
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
		    
		    options: {
		        plugins: {
		          labels: {
		            // render 'label', 'value', 'percentage', 'image' or custom function, default is 'percentage'
		            render: 'value'
		          }
		        }
		    }
		  
		});
	}
	
	return GraficoAtividades2020PorMes;
	
}());


$(function(){
	var graficoAtividade2020PorMes = new Nuts.GraficoAtividades2020PorMes();
	graficoAtividade2020PorMes.iniciar();
});