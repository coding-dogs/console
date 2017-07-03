$(document).ready(function () {
	var $ichecks = $('.i-checks');
	if($ichecks.length > 0){
		$ichecks.iCheck({
            checkboxClass: 'icheckbox_flat-green',
            radioClass: 'iradio_flat-green',
        });
	}
});