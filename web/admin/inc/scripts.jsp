


<script type="text/javascript">
	$(function() {	
		if($('#loginForm').size()>0){
			$('#loginForm').validate({
				highlight: function(element, errorClass) {
					$(element).addClass('error');
				},
	
				unhighlight: function(element, errorClass) {
					$(element).removeClass('error');
				},
	
				submitHandler: function(form) {
					form.submit();
				},
	
				rules: {
					login: {
						required: true
					},
					senha: {
						required: true
					}
				}
			});
		}
	});
</script>