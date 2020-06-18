function exibir(){
	
	var ocultarCampo = $('#tipoAtividade').val();
	
	if(ocultarCampo == 'BANCA'){
		$('#camposBanca').show();
	}
	else {
		$('#camposBanca').hide();
	}
}