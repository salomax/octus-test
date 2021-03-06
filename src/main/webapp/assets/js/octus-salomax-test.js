/**
 * @author salomax <saloma.marcos@gmail.com>
 * version: 1.0.0
 */

/**
 * API REST for Text Resource.
 */
!function($){
	"use strict";
	// Create API
	$.api = {};
	/**
	 * Generic REST API call for Text resource.
	 */
	$.api.rest = function(options) {
		// Verify path and query parameters
		options.path_params = (options.path_params? '/' + options.path_params : '');
		options.query_params = (options.query_params? '?' + options.query_params : '');
		// Promise
		var deferred = $.Deferred();
	    $.ajax({
	        url: 'api/text' + options.path_params + options.query_params,
        	type: options.type,
        	contentType: "application/json; charset=utf-8",
        	data: JSON.stringify(options.data),
        	 beforeSend: function (request) {
        		 if ($.cookie('authToken')) {
                     request.setRequestHeader("X-Auth-Token", $.cookie('authToken'));
        		 }
             },
	    }).then(function(response) {
	    	deferred.resolve(response);
	    }, function(xhr, ajaxOptions, thrownError) {
	    	// Verify authentication
	    	if (xhr.status && xhr.status == 401) {
	    		// similar behavior as an HTTP redirect
	    		window.location.replace("login.html");
    		} else {
    	    	deferred.reject(xhr, ajaxOptions, thrownError);
    		}
	    	
	    });	
	    // Return promise
	    return deferred.promise();
	};
	/**
	 * Search (GET) REST API call for Text resource.
	 */
	$.api.list = function() {
		return $.api.rest({
			type : 'GET'
		});
	}; // End list()	
	/**
	 * POST REST API call for Text resource.
	 */
	$.api.post = function(item) {
		return $.api.rest({
			type : 'POST',
			data : item
		});
	}; // End post()
	/**
	 * DELETE REST API call for Text resource.
	 */
	$.api.del = function(id) {
		return $.api.rest({
			type : 'DELETE',
			path_params : id
		});
	}; // End del()
}(jQuery);
 

/**
 * Create bootstrap table on startup.
 */
!function(){

	// Function to bind to on delete buttons
	var removeItemEvent = function() {
		// Get id by tr attribute (unique)
		var id = $(this).parents('tr').attr('data-uniqueid');
		// Call REST API to delete
		$.api.del(id).then(
				function() {
					$('#text-table').bootstrapTable('removeByUniqueId', id);
				},
				function(xhr, ajaxOptions, thrownError) {
					alert('Error: ' + xhr.responseText); // error
				}
		);
	};
	
	// Build the table
	$('#text-table').bootstrapTable({
		search : true,
		searchAlign : 'left',
		sortable : true,
		striped : true,
		pagination : true,
		pageList : [15],
		uniqueId : 'id',
	    columns: [{
	        field: 'id',
	        title: 'ID',
	        visible : false,
	        searchable : false
	    }, {
	        field: 'text',
	        title: 'Item Text',
	        sortable : true,
	        searchable : true,
	        formatter : function(value) {
	        	return [$($.parseHTML(value)).text(),
	        	        '<div class="pull-right data-remove">',
	        	        '<span class="fa fa-remove red"></span>',
	        	        '</div>'].join('');
	        }
	    }]
	}).on('post-body.bs.table', function () {
		// Bind remove btn with fn
		$('.data-remove').bind('click', removeItemEvent);
	});

	// This code creates button to add item
	// and bind its events
	var addTextButton = $('<a class="lnk-add-item"><span class="fa fa-plus"/> Add Item</a>');
	addTextButton.bind('click', function() {
		// Insert new row to add text
		$('#text-table').bootstrapTable('insertRow', 
				{index : 0, row : {id:'', text:'Enter text here...'}});	
		// Get focus to new row added
		$('#text-table tbody tr[data-uniqueid=""] td').trigger('dblclick');
	});		
	
	// Append button Add Item to table toolbar
	$('<div class="pull-right search">').append(
			addTextButton).appendTo($('div.fixed-table-toolbar'));

	// Create remove button and bind click event
	var removeButton = $('<div class="pull-right data-remove">' 
			+ '<span class="fa fa-remove red"></span></div>');
	removeButton.bind('click', removeItemEvent);
	
	var updateColumn = function(response, $element) {
		$element.html(response.text);
		$element.append(removeButton.clone().bind('click', removeItemEvent));
	}
	
	// Build double click feature to update text item
	$('#text-table').on('dbl-click-cell.bs.table', function (event, field, value, row, $element) {
		var input = $('<input class="input-column-data" type="text" />').val(row.text);
		$element.toggleClass('td-column-data-editable', true);
		$element.html(input);
		// Focus and select all text
		input.focus().select();
		var updateRow = function() {
			// Execute POST
			$.api.post({
				id : row.id,
				text : $element.find('input').val()
			}).then(function(response) {
				// Update unique id from table
				$('#text-table').bootstrapTable('updateByUniqueId', {id : row.id, row : response});
				// Update td element
				updateColumn(response, $element);
			}, function(xhr, ajaxOptions, thrownError) {
				alert('Error : It was not possible to execute the request.'); // error
				// Update td element
				$('#text-table').bootstrapTable('removeByUniqueId', row.id);
			});
			// Update td element
			$element.toggleClass('td-column-data-editable', false);			
			$element.html('Updating data...');
		};
		// Bind updateRow on blur event
		input.bind('blur focusout', updateRow).keypress(function(e) {
			 if(e.which == 13) { // the enter key code
			    $(this).blur();
			    return false;  
			  }			
		});
	});
	
	$()
	
 }();

/**
 * Logout button.
 */
!function() {
	$('.lnk-logout').bind('click', function(event) {
		event.preventDefault();
		$.removeCookie("authToken");
		window.location.replace("./");
	});
}();



