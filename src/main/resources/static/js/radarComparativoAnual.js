var Nuts = Nuts || {};

Nuts.GraficoRadarComparativoAnual = (function(){
	
	function GraficoRadarComparativoAnual(){
		this.ctx = $('#comparativoAnual')[0].getContext('2d');
		
	}
	
	GraficoRadarComparativoAnual.prototype.iniciar = function(){
		$.ajax({
			url:'atividade/dados/comparativoAnual',
			method: 'GET',
			success: onDadosRecebidos.bind(this)
		});
	}
	
	function onDadosRecebidos(comparativoAnual){
		var valores = [];
		
		comparativoAnual.forEach(function(obj){
			valores.push(obj);
		});
		
		
		
		
		var barOptions = {
			    
			    plugins: {
			        labels: {
			            render: 'value'
			        }
			    },
			    animation: {
			        duration: 1,
			        onProgress() {
			        	
			        	let chartInstance = this.chart;
			            let ctx = chartInstance.ctx;

			            // ctx.fillStyle = this.scale.textColor

			            ctx.textAlign = "center";
			            ctx.textBaseline = "middle";
			            ctx.fillStyle = "#000";

			            this.data.datasets.forEach(function (dataset, i) {
			                var label = dataset.label;
			                var meta = chartInstance.controller.getDatasetMeta(i);
			                var total = dataset.data.reduce(function(total, num) { return total + num; })

			                meta.data.forEach(function (bar, index) {
			                    var data = dataset.data[index];
			                    data = Math.ceil(data);
			                    ctx.fillText(data, bar._model.x - 15, bar._model.y);
			                });
			            });
			            
			        }
			    }
			};
		
		
		
		
		var graficoRadarComparativoAnual = new Chart(this.ctx, {
		    type: 'horizontalBar',
		    data:{
		    	labels : ['2015','2016','2017', '2018', '2019', '2020', '2021', 'Atual'],
		    	datasets : [{
		    		label : 'Atividades realizadas',
		    		backgroundColor:['rgba(78, 115, 223, 0.5)', 'rgba(255, 99, 132, 0.5)', 'rgba(54, 162, 235, 0.5)',
		    						 'rgba(255, 206, 86, 0.5)', 'rgba(75, 192, 192, 0.5)', 'rgba(153, 102, 255, 0.5)', 
		    						 'rgba(255, 0, 0, 0.5)', 'rgba(183, 162, 155, 0.5)'], 
		    		
		    		borderColor: ['rgba(178, 15, 23, 1)', 'rgba(255, 99, 132, 1)','rgba(54, 162, 235, 1)','rgba(255, 206, 86, 1)',
		    			'rgba(75, 192, 192, 1)','rgba(153, 102, 255, 1)','rgba(255, 159, 64, 1)', 'rgba(215, 119, 44, 1)'],
		    		
		    		borderWidth: 1,
		    		pointBorderColor : 'rgba(78, 115, 223, 0.5)',
		    		pointRadius: 3,
		    	    pointBackgroundColor: "rgba(78, 115, 223, 1)",
		    	    pointBorderColor: "rgba(78, 115, 223, 1)",
		    	    pointHoverRadius: 3,
		    	    pointHoverBackgroundColor: "rgba(78, 115, 223, 1)",
		    	    pointHoverBorderColor: "rgba(78, 115, 223, 1)",
		    	    pointHitRadius: 10,
		    	    pointBorderWidth: 2,
		    	    
		    	    data : valores
		    	    
		    	}]
		    },
		    
		    options:barOptions
		    
		    /*{
		    	plugins:{
		    		labels:{
		    			render:'value'	
		    		}
		    	}
		    }*/
		    
		});
	}
	
	return GraficoRadarComparativoAnual;
	
}());


$(function(){
	var graficoRadarComparativoAnual = new Nuts.GraficoRadarComparativoAnual();
	graficoRadarComparativoAnual.iniciar();
});