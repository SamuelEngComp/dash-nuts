var Nuts = Nuts || {};

Nuts.MultiSelecao = (function() {
	
	function MultiSelecao(){
		this.statusBtn = $(".js-status-btn");
		this.selecaoCheckbox = $(".js-selecao");
	}
	
	MultiSelecao.prototype.iniciar = function() {
		this.statusBtn.on("click", onStatusBtnClicado.bind(this));		
	}
	
	function onStatusBtnClicado(event) {
		var botaoClicado = $(event.currentTarget);
		var status = botaoClicado.data("status");

		var checkBoxSelecionados = this.selecaoCheckbox.filter(":checked");
		
		
		var codigos = $.map(checkBoxSelecionados, function(c) {
			return $(c).data("codigo");
		});
		
		if (codigos.length > 0) {		
			$.ajax({
				url: "/usuario/filtros/status",
				method: "PUT",
				data:{
					codigos : codigos,
					status : status
				}, 
				success: function() {
					window.location.reload();
				}
			});
			
		}
	}
	
	return MultiSelecao;
	
}());

$(function carregando(){
	var multiselecao = new Nuts.MultiSelecao();
	multiselecao.iniciar();
});

