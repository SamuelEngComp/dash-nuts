var Nuts = Nuts || {};

Nuts.GraficoPontosConectadosAnual = (function(){
	
	function GraficoPontosConectadosAnual(){
		this.ctx = $('#comparativoAnualPontosConectados')[0].getContext('2d');
		
	}
	
	GraficoPontosConectadosAnual.prototype.iniciar = function(){
		$.ajax({
			url:'atividade/dados/comparativopontosconectadosanual',
			method: 'GET',
			success: onDadosRecebidos.bind(this)
		});
	}
	
	function onDadosRecebidos(comparativoanualpontosconectados){
		var valores = [];
		
		/*comparativofinal.forEach(function(obj){
			valores.push(obj);
		});*/
		
		console.log(comparativoanualpontosconectados['2016']);
		
		var graficoPontosConectadosAnual = new Chart(this.ctx, {
			
		    type: 'bar',
		    data:{
		    	labels : ['Pontos Conectados'],
		    	datasets : [
		    		{
			    		label: '2015',
			    		backgroundColor: 'rgba(78, 115, 223, 0.5)', 
			    		borderColor: 'rgba(178, 15, 23, 1)',
			    		borderWidth: 1,
			    	    data: [comparativoanualpontosconectados['2015']]
		    	    
		    		},
		    		{
			    		label: '2016',
			    		backgroundColor: 'rgba(250, 250, 0, 0.5)', 
			    		borderColor: 'rgba(178, 15, 23, 1)',
			    		borderWidth: 1,
			    	    data: [comparativoanualpontosconectados['2016']]
		    	    
		    		},
		    		{
			    		label: '2017',
			    		backgroundColor: 'rgba(255, 99, 132, 0.5)', 
			    		borderColor: 'rgba(178, 15, 23, 1)',
			    		borderWidth: 1,
			    	    data: [comparativoanualpontosconectados['2017']]
		    	    
		    		},
		    		
		    		{
			    		label: '2018',
			    		backgroundColor: 'rgba(54, 162, 235, 0.5)', 
			    		borderColor: 'rgba(178, 15, 23, 1)',
			    		borderWidth: 1,
			    	    data: [comparativoanualpontosconectados['2018']]
		    	    
		    		},
		    		
		    		{
			    		label: '2019',
			    		backgroundColor: 'rgba(255, 206, 86, 0.5)', 
			    		borderColor: 'rgba(178, 15, 23, 1)',
			    		borderWidth: 1,
			    	    data: [comparativoanualpontosconectados['2019']]
		    	    
		    		},
		    		
		    		{
			    		label: '2020',
			    		backgroundColor: 'rgba(75, 192, 192, 0.5)', 
			    		borderColor: 'rgba(178, 15, 23, 1)',
			    		borderWidth: 1,
			    	    data: [comparativoanualpontosconectados['2020']]
		    	    
		    		},
		    		
		    		{
			    		label: '2021',
			    		backgroundColor: 'rgba(255, 0, 0, 0.5)', 
			    		borderColor: 'rgba(178, 15, 23, 1)',
			    		borderWidth: 1,
			    	    data: [comparativoanualpontosconectados['2021']]
		    	    
		    		},
		    		
		    		{
			    		label: 'Atual',
			    		backgroundColor: 'rgba(155, 106, 186, 0.5)', 
			    		borderColor: 'rgba(178, 15, 23, 1)',
			    		borderWidth: 1,
			    	    data: [comparativoanualpontosconectados['Atual']]
		    	    
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
	
	return GraficoPontosConectadosAnual;
	
}());


$(function(){
	var graficoPontosConectadosAnual = new Nuts.GraficoPontosConectadosAnual();
	graficoPontosConectadosAnual.iniciar();
});