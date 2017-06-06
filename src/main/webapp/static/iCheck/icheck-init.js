$(document).ready(function () {
	var $ichecks = $('.i-checks');
	if($ichecks.length > 0){
		$ichecks.iCheck({
            checkboxClass: 'icheckbox_square-green',
            radioClass: 'iradio_square-green',
        });
	}
});