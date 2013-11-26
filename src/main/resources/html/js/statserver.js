(function() {

	// jQuery UI hookup and click handlers
	$(function() {
		$('#tabs').tabs();
		$('#requestLeaderboard').button().click(loadLeaderboard);
		$('#requestStats').button().click(loadStats);
		$('#postStat').button().click(postStat);

		if (window.location.hostname) {
			$('#baseUrl').val(window.location.hostname);
		} else {
			$('#baseUrl').val('http://localhost:8080');
		}
	});

	// display AJAX errors in div
	$(document).ajaxError(
			function(e, xhr, settings, exception) {
				$('#ajaxError').text(
						'Error in: ' + settings.url + ' - Error: ' + exception
								+ " - Response: " + xhr.responseText);
			});

	// retrieve the base REST URL
	function getBaseUrl() {
		var slash = ($('#baseUrl').val().slice(-1) === '/') ? '' : '/';
		return $('#baseUrl').val() + slash + 'statserver/stats';
	}

	// load the leaderboard from the back end
	function loadLeaderboard() {
		var url = getBaseUrl() + '/leaderboard?statName='
				+ $('#statName option:selected').val();

		$.ajax({
			cache : false,
			async : false,
			url : url,
			type : 'GET',
			contentType : 'text/plain',
			dataType : 'json',
			global : true,
			beforeSend : function() {
				$('input:button').hide();
			},
			complete : function() {
				$('input:button').show();
			},
			success : function(responseJson) {
				$("#leaderboardTable").find("tr:gt(0)").remove();
				for (var i = 0, len = responseJson.length; i < len; i++) {
					$('#leaderboardTable tr:last').after(
							'<tr><td>' + responseJson[i]['rank'] + '</td><td>'
									+ responseJson[i]['userName'] + '</td><td>'
									+ responseJson[i]['value'] + '</td><tr>');

				}
			}
		});
	}

	// load the stats from the back end
	function loadStats() {
		var url = getBaseUrl() + '?userName=' + $('#userName').val();

		$.ajax({
			cache : false,
			async : false,
			url : url,
			type : 'GET',
			contentType : 'text/plain',
			dataType : 'json',
			global : true,
			beforeSend : function() {
				$('input:button').hide();
			},
			complete : function() {
				$('input:button').show();
			},
			success : function(responseJson) {
				$("#statTable").find("tr:gt(0)").remove();
				for (var i = 0, len = responseJson.length; i < len; i++) {
					var date = new Date(responseJson[i]['createdAt'] * 1000);
					var formattedTime = padTo2Digit(date.getHours()) + ':'
							+ padTo2Digit(date.getMinutes()) + ':'
							+ padTo2Digit(date.getSeconds());
					$('#statTable tr:last')
							.after(
									'<tr><td>' + responseJson[i]['statName']
											+ '</td><td>'
											+ responseJson[i]['value']
											+ '</td><td>' + formattedTime
											+ '</td><tr>');

				}
				;
			}
		});
	}

	// post a new stat to the back end
	// TODO fix me
	function postStat() {
		var url = getBaseUrl() + '?userName=' + $('#postUserName').val();

		var stat = JSON.stringify({
			statName : $('#postStatName option:selected').val(),
			value : $('#postValue').val(),
			createdAt : $('#postCreatedAt').val()
		});

		$.ajax({
			cache : false,
			async : false,
			url : url,
			type : 'POST',
			contentType : 'application/json',
			dataType : 'json',
			data : stat,
			global : true,
			beforeSend : function() {
				$('input:button').hide();
			},
			complete : function() {
				$('input:button').show();
			},
			success : function(responseJson) {
				$('#postResponse').html(responseJson.toString());
			}
		});
	}

	// pads single digit numbers with a zero
	function padTo2Digit(num) {
		var s = '0' + num;
		return s.substr(s.length - 2);
	}
})();