var Nuts = Nuts || {};

Nuts.GraficoPublicoLocal = (function(){
	
	function GraficoPublicoLocal(){
		this.ctx = $('#comparativoAnualPublicoLocal')[0].getContext('2d');
		
	}
	
	GraficoPublicoLocal.prototype.iniciar = function(){
		$.ajax({
			url:'atividade/dados/comparativopublicolocal',
			method: 'GET',
			success: onDadosRecebidos.bind(this)
		});
	}
	
	function onDadosRecebidos(comparativoanualpublicolocal){
		var valores = [];
		
		/*comparativoanualpublicolocal.forEach(function(obj){
			valores.push(obj);
		});*/
		
		
		console.log(comparativoanualpublicolocal['2016']);
		console.log(comparativoanualpublicolocal['2017']);
		console.log(comparativoanualpublicolocal['2018']);
		console.log(comparativoanualpublicolocal["2015"]);
		console.log(valores['2015']);
		
		var graficoPublicoLocal = new Chart(this.ctx, {
		
		    type: 'bar',
		    data:{
		    	labels : ['Público Local'],
		    	datasets : [
		    		{
			    		label: '2015',
			    		backgroundColor: 'rgba(78, 115, 223, 0.5)', 
			    		borderColor: 'rgba(178, 15, 23, 1)',
			    		borderWidth: 1,
			    	    data: [comparativoanualpublicolocal['2015']]
		    	    
		    		},
		    		{
			    		label: '2016',
			    		backgroundColor: 'rgba(250, 250, 0, 0.5)', 
			    		borderColor: 'rgba(178, 15, 23, 1)',
			    		borderWidth: 1,
			    	    data: [comparativoanualpublicolocal['2016']]
		    	    
		    		},
		    		{
			    		label: '2017',
			    		backgroundColor: 'rgba(255, 99, 132, 0.5)', 
			    		borderColor: 'rgba(178, 15, 23, 1)',
			    		borderWidth: 1,
			    	    data: [comparativoanualpublicolocal['2017']]
		    	    
		    		},
		    		{
			    		label: '2018',
			    		backgroundColor: 'rgba(54, 162, 235, 0.5)', 
			    		borderColor: 'rgba(178, 15, 23, 1)',
			    		borderWidth: 1,
			    	    data: [comparativoanualpublicolocal['2018']]
		    	    
		    		},
		    		{
			    		label: '2019',
			    		backgroundColor: 'rgba(255, 206, 86, 0.5)', 
			    		borderColor: 'rgba(178, 15, 23, 1)',
			    		borderWidth: 1,
			    	    data: [comparativoanualpublicolocal['2019']]
		    	    
		    		},
		    		{
			    		label: '2020',
			    		backgroundColor: 'rgba(75, 192, 192, 0.5)', 
			    		borderColor: 'rgba(178, 15, 23, 1)',
			    		borderWidth: 1,
			    	    data: [comparativoanualpublicolocal['2020']]
		    	    
		    		},
		    		{
			    		label: '2021',
			    		backgroundColor: 'rgba(255, 0, 0, 0.5)', 
			    		borderColor: 'rgba(178, 15, 23, 1)',
			    		borderWidth: 1,
			    	    data: [comparativoanualpublicolocal['2021']]
		    	    
		    		},
		    		
		    		{
			    		label: 'Atual',
			    		backgroundColor: 'rgba(155, 106, 186, 0.5)', 
			    		borderColor: 'rgba(178, 15, 23, 1)',
			    		borderWidth: 1,
			    	    data: [comparativoanualpublicolocal['Atual']]
		    	    
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
	
	return GraficoPublicoLocal;
	
}());


$(function(){
	var graficoPublicoLocal = new Nuts.GraficoPublicoLocal();
	graficoPublicoLocal.iniciar();
});