(function() {

	// jQuery UI hookup and click handlers
	$(function() {
		$('#tabs').tabs();

		// click handler to POST a stat
		$('#postStatGuided').button().click(function() {
			var stat = JSON.stringify({
				statName : $('#tabSendStat .request .statName option:selected').val(),
				value : $('#tabSendStat .request .value').val(),
				createdAt : new Date($('#tabSendStat .request .createdAt').val()).getTime()
			});
			var url = getBaseUrl() + '?userName=' + $('#tabSendStat .request .userName').val();
			postStat(stat, url, fillGuidedPost);
		});
		$('#postStatRaw').button().click(function() {
			var stat = $('#tabSendStat .rawJson').val();
			var url = getBaseUrl() + '?' + $('#tabSendStat .queryParam').val();
			postStat(stat, url, fillRawPost);
		});

		// click handler to GET a leaderboard
		$('#getLeaderboardGuided').button().click(function() {
			var url = getBaseUrl() + '/leaderboard?statName=' + $('#tabGetLeaderboard .statName option:selected').val();
			loadLeaderboard(url, fillGuidedLeaderboard);
		});
		$('#getLeaderboardRaw').button().click(function() {
			var url = getBaseUrl() + '/leaderboard?' + $('#tabGetLeaderboard .queryParam').val();
			loadLeaderboard(url, fillRawLeaderboard);
		});

		// click handler to GET a stat
		$('#getStatsGuided').button().click(function() {
			var url = getBaseUrl() + '?userName=' + $('#tabGetStat .userName').val();
			loadStats(url, fillGuidedGet);
		});
		$('#getStatsRaw').button().click(function() {
			var url = getBaseUrl() + '?' + $('#tabGetStat .queryParam').val();
			loadStats(url, fillRawGet);
		});

		$('#tabSendStat .request .createdAt').datetimepicker();

		if (window.location.hostname) {
			$('#baseUrl').val('http://' + window.location.hostname);
		} else {
			$('#baseUrl').val('http://localhost');
		}
	});

	// display AJAX errors in div
	$(document).ajaxError(function(e, xhr, settings, exception) {
		$('#ajaxError').text('Error in: ' + settings.url + ' - Error: ' + exception + " - Response: " + xhr.responseText);
	});

	// retrieve the base REST URL
	function getBaseUrl() {
		var slash = ($('#baseUrl').val().slice(-1) === '/') ? '' : '/';
		return $('#baseUrl').val() + slash + 'statserver/stats';
	}

	// load the leaderboard from the back end
	function loadLeaderboard(url, successFunc) {
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
				successFunc(responseJson);
			}
		});
	}

	function fillGuidedLeaderboard(json) {
		$("#leaderboardTable").find("tr").remove();
		for (var i = 0, len = json.length; i < len; i++) {
			$('#leaderboardTable').append(
					'<tr><td>' + json[i]['rank'] + '</td><td>' + json[i]['userName'] + '</td><td>' + json[i]['value'] + '</td><tr>');
		}
	}

	function fillRawLeaderboard(json) {
		$('#tabGetLeaderboard .raw .response .rawJson').html(JSON.stringify(json));
	}

	// load the stats from the back end
	function loadStats(url, successFunc) {
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
				successFunc(responseJson);
			}
		});
	}

	function fillGuidedGet(json) {
		$("#statsTable").find("tr").remove();
		for (var i = 0, len = json.length; i < len; i++) {
			var date = new Date(json[i]['createdAt'] * 1000);
			var formattedTime = padTo2Digit(date.getHours()) + ':' + padTo2Digit(date.getMinutes()) + ':' + padTo2Digit(date.getSeconds());
			$('#statsTable').append('<tr><td>' + json[i]['statName'] + '</td><td>' + json[i]['value'] + '</td><td>' + formattedTime + '</td><tr>');
		}
	}

	function fillRawGet(json) {
		$('#tabGetStat .rawJson').html(JSON.stringify(json));
	}

	// post a new stat to the back end
	function postStat(stat, url, successFunc) {
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
				successFunc(responseJson);
			}
		});
	}

	function fillGuidedPost(json) {
		$('#tabSendStat .response .id').val(json["id"]);
		$('#tabSendStat .response .userName').val(json["userName"]);
		$('#tabSendStat .response .statName').val(json["statName"]);
		$('#tabSendStat .response .value').val(json["value"]);
		$('#tabSendStat .response .createdAt').val(new Date(json["createdAt"] * 1000));
	}

	function fillRawPost(json) {
		$('#tabSendStat .response .rawJson').html(JSON.stringify(json));
	}

	// pads single digit numbers with a zero
	function padTo2Digit(num) {
		var s = '0' + num;
		return s.substr(s.length - 2);
	}
})();