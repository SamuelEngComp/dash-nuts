var Nuts = Nuts || {};

Nuts.GraficoBancasExaminadoras = (function(){
	
	function GraficoBancasExaminadoras(){
		this.ctx = $('#bancasExaminadoras')[0].getContext('2d');
		
	}
	
	GraficoBancasExaminadoras.prototype.iniciar = function(){
		$.ajax({
			url:'banca/dados/totalPorTipo',
			method: 'GET',
			success: onDadosRecebidos.bind(this)
		});
	}
	
	function onDadosRecebidos(bancasExaminadoras){
		var valores = [];
		
		bancasExaminadoras.forEach(function(obj){
			valores.push(obj);
		});
		
		var graficoBancasExaminadoras = new Chart(this.ctx, {
		    type: 'polarArea',
		    data: {
		    	labels : ['TCC','TCR','Banca de Mestrado', 'Banca de Doutorado'],
		    	datasets : [{
		    		data : valores,
		    		label : 'Bancas examinadoras',
		    		backgroundColor: ['#4e73df', '#1cc88a', '#36b9cc','#FF0000' ],
		    		hoverBackgroundColor: ['#2e59d9', '#17a673', '#2c9faf', '#FF0000'],
		    		hoverBorderColor: "rgba(234, 236, 244, 1)",
		    	}],
		    },
		    
		    options: {
			    maintainAspectRatio: false,
			    tooltips: {
			      backgroundColor: "rgb(255,255,255)",
			      bodyFontColor: "#858796",
			      borderColor: '#dddfeb',
			      borderWidth: 1,
			      xPadding: 15,
			      yPadding: 15,
			      displayColors: false,
			      caretPadding: 10,
			    },
			    legend: {
			      display: false
			    },
			    cutoutPercentage: 80,
			  },
		    
		    
		});
	}
	
	return GraficoBancasExaminadoras;
	
}());


$(function(){
	var graficoBancasExaminadoras = new Nuts.GraficoBancasExaminadoras();
	graficoBancasExaminadoras.iniciar();
});