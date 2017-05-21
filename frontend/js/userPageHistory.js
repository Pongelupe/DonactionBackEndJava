//Histórico de Doações
$(function() {
    $('a[title]').tooltip();
});

(function(){
    'use strict';
	var $ = jQuery;
	$.fn.extend({
		filterTable: function(){
			return this.each(function(){
				$(this).on('keyup', function(e){
					$('.filterTable_no_results').remove();
					var $this = $(this), 
                        search = $this.val().toLowerCase(), 
                        target = $this.attr('data-filters'), 
                        $target = $(target), 
                        $rows = $target.find('tbody tr');
                        
					if(search == '') {
						$rows.show(); 
					} else {
						$rows.each(function(){
							var $this = $(this);
							$this.text().toLowerCase().indexOf(search) === -1 ? $this.hide() : $this.show();
						})
						if($target.find('tbody tr:visible').size() === 0) {
							var col_count = $target.find('tr').first().find('td').size();
							var no_results = $('<tr class="filterTable_no_results"><td colspan="'+col_count+'">Nenhum Resultado foi Encontrado</td></tr>')
							$target.find('tbody').append(no_results);
						}
					}
				});
			});
		}
	});
	$('[data-action="filter"]').filterTable();
})(jQuery);

$(function(){
    // attach table filter plugin to inputs
	$('[data-action="filter"]').filterTable();
	
	$('.container').on('click', '.panel-heading span.filter', function(e){
		var $this = $(this), 
			$panel = $this.parents('.panel');
		
		$panel.find('.panel-body').slideToggle();
		if($this.css('display') != 'none') {
			$panel.find('.panel-body input').focus();
		}
	});
	$('[data-toggle="tooltip"]').tooltip();
});

$(function() {
	$("#donaction").on("click", function(){
	  	var address = 'http://127.0.0.1:8080/historico';
	  	var userData = JSON.parse(localStorage.getItem("userData"));
	    var formData = "cdDoador=" + userData.id;
	    $.ajax({
	        type: "POST",
	        url: address,
	        data: formData,
	        success: function(data, textStatus, jqXHR) {
	            var userHistory = JSON.parse(jqXHR.responseText);
    			console.log(userHistory);
	        },
	        error: function(xhr, textStatus, errorThrown) {
	            sweetAlert("Deu Ruim!","","error");
	        }
	    });
	});
});