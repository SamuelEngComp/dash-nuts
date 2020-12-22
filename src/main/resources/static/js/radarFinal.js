var Nuts = Nuts || {};

Nuts.GraficoRadar = (function(){
	
	function GraficoRadar(){
		this.ctx = $('#radarFinal')[0].getContext('2d');
		
	}
	
	GraficoRadar.prototype.iniciar = function(){
		$.ajax({
			url:'atividade/dados/comparativoFinal',
			method: 'GET',
			success: onDadosRecebidos.bind(this)
		});
	}
	
	function onDadosRecebidos(comparativofinal){
		var valores = [];
		
		/*comparativofinal.forEach(function(obj){
			valores.push(obj);
		});*/
		
		console.log(comparativofinal['2016']);
		
		var graficoRadar = new Chart(this.ctx, {
			
		    type: 'bar',
		    data:{
		    	labels : ['Sessão Clinica','Gravação de Video','SIG', 'EBSERH', 'Banca Examinadora'],
		    	datasets : [
		    		{
			    		label: '2016',
			    		backgroundColor: 'rgba(78, 115, 223, 0.5)', 
			    		borderColor: 'rgba(178, 15, 23, 1)',
			    		borderWidth: 1,
			    	    data: comparativofinal['2016']
		    	    
		    		},
		    		{
			    		label: '2017',
			    		backgroundColor: 'rgba(255, 99, 132, 0.5)', 
			    		borderColor: 'rgba(178, 15, 23, 1)',
			    		borderWidth: 1,
			    	    data: comparativofinal['2017']
		    	    
		    		},
		    		
		    		{
			    		label: '2018',
			    		backgroundColor: 'rgba(54, 162, 235, 0.5)', 
			    		borderColor: 'rgba(178, 15, 23, 1)',
			    		borderWidth: 1,
			    	    data: comparativofinal['2018']
		    	    
		    		},
		    		
		    		{
			    		label: '2019',
			    		backgroundColor: 'rgba(255, 206, 86, 0.5)', 
			    		borderColor: 'rgba(178, 15, 23, 1)',
			    		borderWidth: 1,
			    	    data: comparativofinal['2019']
		    	    
		    		},
		    		
		    		{
			    		label: '2020',
			    		backgroundColor: 'rgba(75, 192, 192, 0.5)', 
			    		borderColor: 'rgba(178, 15, 23, 1)',
			    		borderWidth: 1,
			    	    data: comparativofinal['2020']
		    	    
		    		},
		    		
		    		{
			    		label: '2021',
			    		backgroundColor: 'rgba(255, 0, 0, 0.5)', 
			    		borderColor: 'rgba(178, 15, 23, 1)',
			    		borderWidth: 1,
			    	    data: comparativofinal['2021']
		    	    
		    		},
		    		
		    		{
			    		label: 'Atual',
			    		backgroundColor: 'rgba(155, 106, 186, 0.5)', 
			    		borderColor: 'rgba(178, 15, 23, 1)',
			    		borderWidth: 1,
			    	    data: comparativofinal['Atual']
		    	    
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
	
	return GraficoRadar;
	
}());


$(function(){
	var graficoRadar = new Nuts.GraficoRadar();
	graficoRadar.iniciar();
});