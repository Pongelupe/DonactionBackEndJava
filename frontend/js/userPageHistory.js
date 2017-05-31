//Histórico de Doações
$(function(){
	'use strict';

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

	// attach table filter plugin to inputs
	$('[data-action="filter"]').filterTable();
	$('a[title]').tooltip();
	$('[data-toggle="tooltip"]').tooltip();

	$('.container').on('click', '.panel-heading span.filter', function(e){
		var $this = $(this),
			$panel = $this.parents('.panel');

		$panel.find('.panel-body').slideToggle();
		if($this.css('display') != 'none') {
			$panel.find('.panel-body input').focus();
		}
	});

	$("#donaction").on("click", function() {
		var donationsTable = $('#dev-table');
	  	var address = 'http://127.0.0.1:8080/historicoDoador';
	  	var userData = JSON.parse(localStorage.getItem("userData"));
	  	if (userData) {
		    var formData = "cdDoador=" + userData.id;
		    $.ajax({
		        type: "POST",
		        url: address,
		        data: formData,
		        success: function(data, textStatus, jqXHR) {
		            var donationTableBody = '';
				  	data.forEach(function(row) {
				  		donationTableBody += '<tr>';
				  		donationTableBody += '<td>' + row.nmCampanha + '</td>';
				  		donationTableBody += '<td>' + row.nmEmpresa + '</td>';
				  		donationTableBody += '<td>' + row.dtInicio + '</td>';
				  		donationTableBody += '<td>' + row.dtFim + '</td>';
				  		donationTableBody += '</tr>';
				  	});
			  		donationsTable.find('tbody').html(donationTableBody);
		        },
		        error: function(err) {
		        	donationsTable.html('<h3 style="text-align:center; padding:20px">Não foram encontradas campanhas :(</h3>');
		        }
		    });
	  	} else {
	  		donationsTable.html('<h3 style="text-align:center; padding:20px">Erro ao carregar dados do usuário. Tente novamente mais tarde.</h3>');
	  	}
	});
});