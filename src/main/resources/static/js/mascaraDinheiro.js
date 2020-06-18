$(function() {
	
	var decimal = $('.js-decimal');
	decimal.maskMoney({ decimal: ',', thousands: '.' });
	
	var plan = $('.js-inteiro');
	plan.maskMoney({precision: 0, thousands: '.' });
	
	
	var datas = $('.js-data');
	
	datas.mask('00/00/0000');
	
  });