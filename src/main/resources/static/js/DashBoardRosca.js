var Nuts = Nuts || {};

Nuts.GraficoFormasConexaoPorMes = (function(){
	
	function GraficoFormasConexaoPorMes(){
		this.ctx = $('#rosca')[0].getContext('2d');
		
	}
	
	GraficoFormasConexaoPorMes.prototype.iniciar = function(){
		$.ajax({
			url:'atividade/dados/formasconexao',
			method: 'GET',
			success: onDadosRecebidos.bind(this)
		});
	}
	
	function onDadosRecebidos(formaConexaoPorMes){
		var valores = [];
		
		formaConexaoPorMes.forEach(function(obj){
			valores.push(obj);
		});
		
//		
		
		var graficoFormaConexaoPorMes = new Chart(this.ctx, {
			type: 'doughnut',
			  data: {
			    labels: ["Webconferência", "Videoconferência", "Video/Web", "Web/Streaming"],
			    datasets: [{
			      data: valores,
			      backgroundColor: ['#4e73df', '#1cc88a', '#36b9cc', '#ff0004'],
			      hoverBackgroundColor: ['#2e59d9', '#17a673', '#2c9faf', '#cf1b1e'],
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
			    cutoutPercentage: 50,
			    
			    plugins:{
			    		labels:{
			    			render:'percentage',
			    			fontColor:'#fff',
			    			position: 'border'
			    		}
			    	}
			    
			  },
		});
	}
	
	return GraficoFormasConexaoPorMes;
	
}());


$(function(){
	var graficoFormaConexaoPorMes = new Nuts.GraficoFormasConexaoPorMes();
	graficoFormaConexaoPorMes.iniciar();
});