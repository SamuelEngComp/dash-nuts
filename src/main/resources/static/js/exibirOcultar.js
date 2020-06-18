function exibir_ocultar() {
	//    var exibir = document.getElementById('camposBanca');
	var exibir2 = $('#pontosExternos').val();
	var exibir3 = $('#pontosExternos').val();
	var exibir4 = $('#pontosExternos').val();

	console.log(exibir2);

	switch (exibir2) {

	case '':
		$('#valorDiv01').hide();
		$('#valorDiv02').hide();
		$('#valorDiv03').hide();
		$('#valorDiv04').hide();
		$('#valorDiv05').hide();
		$('#valorDiv06').hide();
		break;

	case '1':
		$('#valorDiv01').hide();
		$('#valorDiv02').hide();
		$('#valorDiv03').hide();
		$('#valorDiv04').hide();
		$('#valorDiv05').hide();
		$('#valorDiv06').hide();
		break;

	case '2':
		$('#valorDiv01').show();
		$('#valorDiv02').hide();
		$('#valorDiv03').hide();
		$('#valorDiv04').hide();
		$('#valorDiv05').hide();
		$('#valorDiv06').hide();
		break;

	case '3':
		$('#valorDiv01').show();
		$('#valorDiv02').show();
		$('#valorDiv03').hide();
		$('#valorDiv04').hide();
		$('#valorDiv05').hide();
		$('#valorDiv06').hide();
		break;

	case '4':
		$('#valorDiv01').show();
		$('#valorDiv02').show();
		$('#valorDiv03').show();
		$('#valorDiv04').hide();
		$('#valorDiv05').hide();
		$('#valorDiv06').hide();
		break;

	case '5':
		$('#valorDiv01').show();
		$('#valorDiv02').show();
		$('#valorDiv03').show();
		$('#valorDiv04').show();
		$('#valorDiv05').hide();
		$('#valorDiv06').hide();
		break;

	case '6':
		$('#valorDiv01').show();
		$('#valorDiv02').show();
		$('#valorDiv03').show();
		$('#valorDiv04').show();
		$('#valorDiv05').show();
		$('#valorDiv06').hide();
		break;

	default:
		$('#valorDiv01').hide();
		$('#valorDiv02').hide();
		$('#valorDiv03').hide();
		$('#valorDiv04').hide();
		$('#valorDiv05').hide();
		$('#valorDiv06').hide();
		break;
	}

	switch (exibir3) {

	case '':
		$('#valDiv02').hide();
		$('#valDiv03').hide();
		$('#valDiv04').hide();
		$('#valDiv05').hide();
		$('#valDiv06').hide();
		break;

	case '1':
		$('#valDiv02').hide();
		$('#valDiv03').hide();
		$('#valDiv04').hide();
		$('#valDiv05').hide();
		$('#valDiv06').hide();
		break;

	case '2':
		$('#valDiv02').show();
		$('#valDiv03').hide();
		$('#valDiv04').hide();
		$('#valDiv05').hide();
		$('#valDiv06').hide();
		break;

	case '3':
		$('#valDiv02').show();
		$('#valDiv03').show();
		$('#valDiv04').hide();
		$('#valDiv05').hide();
		$('#valDiv06').hide();
		break;

	case '4':
		$('#valDiv02').show();
		$('#valDiv03').show();
		$('#valDiv04').show();
		$('#valDiv05').hide();
		$('#valDiv06').hide();
		break;

	case '5':
		$('#valDiv02').show();
		$('#valDiv03').show();
		$('#valDiv04').show();
		$('#valDiv05').show();
		$('#valDiv06').hide();
		break;

	case '6':
		$('#valDiv02').show();
		$('#valDiv03').show();
		$('#valDiv04').show();
		$('#valDiv05').show();
		$('#valDiv06').show();
		break;

	default:
		$('#valDiv02').hide();
		$('#valDiv03').hide();
		$('#valDiv04').hide();
		$('#valDiv05').hide();
		$('#valDiv06').hide();
		break;
	}

	switch (exibir4) {

	case '':
		$('#divDiarias02').hide();
		$('#divDiarias03').hide();
		$('#divDiarias04').hide();
		$('#divDiarias05').hide();
		$('#divDiarias06').hide();
		break;

	case '1':
		$('#divDiarias02').hide();
		$('#divDiarias03').hide();
		$('#divDiarias04').hide();
		$('#divDiarias05').hide();
		$('#divDiarias06').hide();
		break;

	case '2':
		$('#divDiarias02').show();
		$('#divDiarias03').hide();
		$('#divDiarias04').hide();
		$('#divDiarias05').hide();
		$('#divDiarias06').hide();
		break;

	case '3':
		$('#divDiarias02').show();
		$('#divDiarias03').show();
		$('#divDiarias04').hide();
		$('#divDiarias05').hide();
		$('#divDiarias06').hide();
		break;

	case '4':
		$('#divDiarias02').show();
		$('#divDiarias03').show();
		$('#divDiarias04').show();
		$('#divDiarias05').hide();
		$('#divDiarias06').hide();
		break;

	case '5':
		$('#divDiarias02').show();
		$('#divDiarias03').show();
		$('#divDiarias04').show();
		$('#divDiarias05').show();
		$('#divDiarias06').hide();
		break;

	case '6':
		$('#divDiarias02').show();
		$('#divDiarias03').show();
		$('#divDiarias04').show();
		$('#divDiarias05').show();
		$('#divDiarias06').show();
		break;

	default:
		$('#divDiarias02').hide();
		$('#divDiarias03').hide();
		$('#divDiarias04').hide();
		$('#divDiarias05').hide();
		$('#divDiarias06').hide();
		break;
	}
	/*if(exibir2 == '1'){
		
	}  
	if(exibir2 == '2'){
	    $('#valorDiv01').hide();
	}
	if(exibir2 == '3'){
	    $('#valorDiv01').show();
	}*/
};