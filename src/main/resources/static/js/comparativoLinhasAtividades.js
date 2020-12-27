var Nuts = Nuts || {};

Nuts.GraficoAtividadesPorMesLinhas = (function(){
	
	function GraficoAtividadesPorMesLinhas(){
		this.ctx = $('#comparativoAtividadePorMesLinhas')[0].getContext('2d');
		
	}
	
	GraficoAtividadesPorMesLinhas.prototype.iniciar = function(){
		$.ajax({
			url:'atividade/dados/linhas',
			method: 'GET',
			success: onDadosRecebidos.bind(this)
		});
	}
	
	function onDadosRecebidos(atividadesPorMesLinhas){
		var valores = [];
		
		/*atividadesPorMesLinhas.forEach(function (obj){
			valores.push(obj);
		});*/
		
		var graficoAtividadePorMesLinhas = new Chart(this.ctx, {
		    type: 'line',
		    data: {
		    	labels : ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
		    	datasets : [
		    		{
			    		label : '2015',
			    		backgroundColor : 'rgba(78, 115, 223, 0.5)',
			    		borderColor: "rgba(78, 115, 223, 1)",
			    		pointBorderColor : 'rgba(2, 6, 8, 0.5)',
			    		pointRadius: 3,
			    	    pointBackgroundColor: "rgba(255, 255, 255, 0.5)",
			    	    pointBorderColor: "rgba(78, 115, 223, 1)",
			    	    pointHoverRadius: 3,
			    	    pointHoverBackgroundColor: "rgba(78, 115, 223, 1)",
			    	    pointHoverBorderColor: "rgba(78, 115, 223, 1)",
			    	    pointHitRadius: 10,
			    	    pointBorderWidth: 2,
			    		
			    		data : atividadesPorMesLinhas['2015']
		    		},
		    		
		    		{
		    			label : '2016',
			    		backgroundColor : 'rgba(250, 250, 0, 0.5)',
			    		borderColor: "rgba(242, 255, 0, 1)",
			    		pointBorderColor : 'rgba(2, 6, 8, 0.5)',
			    		pointRadius: 3,
			    	    pointBackgroundColor: "rgba(255, 255, 255, 0.5)",
			    	    pointBorderColor: "rgba(232, 242, 85, 1)",
			    	    pointHoverRadius: 3,
			    	    pointHoverBackgroundColor: "rgba(78, 115, 223, 1)",
			    	    pointHoverBorderColor: "rgba(78, 115, 223, 1)",
			    	    pointHitRadius: 10,
			    	    pointBorderWidth: 2,
			    		
			    		data : atividadesPorMesLinhas['2016']
		    		},
		    		
		    		{
		    			label : '2017',
			    		backgroundColor : 'rgba(34, 255, 0, 0.5)',
			    		borderColor: "rgba(52, 184, 31, 1)",
			    		pointBorderColor : 'rgba(52, 184, 31, 0.5)',
			    		pointRadius: 3,
			    	    pointBackgroundColor: "rgba(255, 255, 255, 0.5)",
			    	    pointBorderColor: "rgba(52, 184, 31, 1)",
			    	    pointHoverRadius: 3,
			    	    pointHoverBackgroundColor: "rgba(78, 115, 223, 1)",
			    	    pointHoverBorderColor: "rgba(78, 115, 223, 1)",
			    	    pointHitRadius: 10,
			    	    pointBorderWidth: 2,
			    		
			    		data : atividadesPorMesLinhas['2017']
		    		},
		    		
		    		{
		    			label : '2018',
			    		backgroundColor : 'rgba(0, 255, 255, 0.5)',
			    		borderColor: "rgba(10, 242, 242, 1)",
			    		pointBorderColor : 'rgba(10, 242, 242, 0.5)',
			    		pointRadius: 3,
			    	    pointBackgroundColor: "rgba(255, 255, 255, 0.5)",
			    	    pointBorderColor: "rgba(10, 242, 242, 1)",
			    	    pointHoverRadius: 3,
			    	    pointHoverBackgroundColor: "rgba(78, 115, 223, 1)",
			    	    pointHoverBorderColor: "rgba(78, 115, 223, 1)",
			    	    pointHitRadius: 10,
			    	    pointBorderWidth: 2,
			    		
			    		data : atividadesPorMesLinhas['2018']
		    		},
		    		
		    		{
		    			label : '2019',
			    		backgroundColor : 'rgba(39, 82, 82, 0.5)',
			    		borderColor: "rgba(39, 82, 82, 1)",
			    		pointBorderColor : 'rgba(39, 82, 82, 0.5)',
			    		pointRadius: 3,
			    	    pointBackgroundColor: "rgba(255, 255, 255, 0.5)",
			    	    pointBorderColor: "rgba(39, 82, 82, 1)",
			    	    pointHoverRadius: 3,
			    	    pointHoverBackgroundColor: "rgba(78, 115, 223, 1)",
			    	    pointHoverBorderColor: "rgba(78, 115, 223, 1)",
			    	    pointHitRadius: 10,
			    	    pointBorderWidth: 2,
			    		
			    		data : atividadesPorMesLinhas['2019']
		    		},
		    		
		    		{
		    			label : '2020',
			    		backgroundColor : 'rgba(212, 25, 174, 0.5)',
			    		borderColor: "rgba(212, 25, 174, 1)",
			    		pointBorderColor : 'rgba(212, 25, 174, 0.5)',
			    		pointRadius: 3,
			    	    pointBackgroundColor: "rgba(255, 255, 255, 0.5)",
			    	    pointBorderColor: "rgba(212, 25, 174, 1)",
			    	    pointHoverRadius: 3,
			    	    pointHoverBackgroundColor: "rgba(78, 115, 223, 1)",
			    	    pointHoverBorderColor: "rgba(78, 115, 223, 1)",
			    	    pointHitRadius: 10,
			    	    pointBorderWidth: 2,
			    		
			    		data : atividadesPorMesLinhas['2020']
		    		},
		    		
		    		{
		    			label : '2021',
			    		backgroundColor : 'rgba(255, 0, 0, 0.5)',
			    		borderColor: "rgba(255, 0, 0, 1)",
			    		pointBorderColor : 'rgba(255, 0, 0, 0.5)',
			    		pointRadius: 3,
			    	    pointBackgroundColor: "rgba(255, 255, 255, 0.5)",
			    	    pointBorderColor: "rgba(255, 0, 0, 1)",
			    	    pointHoverRadius: 3,
			    	    pointHoverBackgroundColor: "rgba(78, 115, 223, 1)",
			    	    pointHoverBorderColor: "rgba(78, 115, 223, 1)",
			    	    pointHitRadius: 10,
			    	    pointBorderWidth: 2,
			    		
			    		data : atividadesPorMesLinhas['2021']
		    		},
		    		
		    		{
		    			label : 'Atual',
			    		backgroundColor : 'rgba(237, 127, 9, 0.5)',
			    		borderColor: "rgba(237, 127, 9, 1)",
			    		pointBorderColor : 'rgba(237, 127, 9, 0.5)',
			    		pointRadius: 3,
			    	    pointBackgroundColor: "rgba(255, 255, 255, 0.5)",
			    	    pointBorderColor: "rgba(237, 127, 9, 1)",
			    	    pointHoverRadius: 3,
			    	    pointHoverBackgroundColor: "rgba(78, 115, 223, 1)",
			    	    pointHoverBorderColor: "rgba(78, 115, 223, 1)",
			    	    pointHitRadius: 10,
			    	    pointBorderWidth: 2,
			    		
			    		data : atividadesPorMesLinhas['Atual']
		    		}
		    	] 
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
	
	return GraficoAtividadesPorMesLinhas;
	
}());


$(function(){
	var graficoAtividadePorMesLinhas = new Nuts.GraficoAtividadesPorMesLinhas();
	graficoAtividadePorMesLinhas.iniciar();
});