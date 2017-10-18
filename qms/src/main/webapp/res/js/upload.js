$(document).ready(function() {
	
});

function fileOnChange() {
		var filepath = $("#fileInput").val();
		//var extStart = filepath.lastIndexOf(".");
//		var ext = filepath.substring(extStart, filepath.length).toUpperCase();
//		if (ext != ".XLS") {
//			top.UOMPComp.showFailedDialog("只允许上传xls类型文件！", "2");
//			return false;
//		}
		$("#a").html(filepath); //将 #file 的值赋给 #a 
		$("#a").show();
	}
//function  startUpload() {
//	
//		var filepath = $("#fileInput").val();
//		var extStart = filepath.lastIndexOf(".");
//		var ext = filepath.substring(extStart, filepath.length).toUpperCase();
//		if ($("#a").is(':hidden') || $("#a").html() == '文件上传成功!') {
//			return false;
//		}
//
//		var paramData = {
//			'qpId' : $('#qpId').val(),
//			'operType' : 1,
//			'templateName' : "",
//			'extType' :ext
//		};
//		$.ajaxFileUpload({
//			url : 'qc/upload/fileUpload', //需要链接到服务器地址  
//			secureuri : false,
//			fileElementId : 'fileInput', //文件选择框的id属性  
//			dataType : 'json', //服务器返回的格式，可以是json  
//			data : paramData,
//			success: function (data)            //相当于java中try语句块的用法  
//              {       
//	            	if(data.retCode == "0")
//					{
//	            		
//	            		
//					}else
//					{
//						layer.alert("上传文件失败，请刷新页面重新上传！", {icon: 2});
//					}
//              },  
//              error: function (data)            //相当于java中catch语句块的用法  
//              {  
//              	 layer.alert("上传文件失败，请刷新页面重新上传！", {icon: 2});
//              }
//		});
//		$('#fileInput').remove();
//		$("#addFileHrefId")
//				.html(
//						'添加附件<input type="file" id="fileInput" name="fileInput" class="fileInput" onchange="return fileOnChange();"/> ');
//	}


function cancelUpload() {
		$("#a").hide();
		$('#fileInput').remove();
		$("#addFileHrefId")
				.html(
						'添加附件<input type="file" id="fileInput" name="fileInput" class="fileInput" onchange="return fileOnChange();"/> ');
	}
	
function jsonToString(obj){ 
		debugger;
        var THIS = this;    
        switch(typeof(obj)){   
            case 'string':   
                return '"' + obj.replace(/(["\\])/g, '\\$1') + '"';   
            case 'array':   
                return '[' + obj.map(THIS.jsonToString).join(',') + ']';   
            case 'object':   
                 if(obj instanceof Array){   
                    var strArr = [];   
                    var len = obj.length;   
                    for(var i=0; i<len; i++){   
                        strArr.push(THIS.jsonToString(obj[i]));   
                    }   
                    return '[' + strArr.join(',') + ']';   
                }else if(obj==null){   
                    return 'null';   
  
                }else{   
                    var string = [];   
                    for (var property in obj) string.push(THIS.jsonToString(property) + ':' + THIS.jsonToString(obj[property]));   
                    return '{' + string.join(',') + '}';   
                }   
            case 'number':   
                return obj;   
            case false:   
                return obj;   
        }   
    }


jQuery.extend({
    handleError: function( s, xhr, status, e )      {  
        // If a local callback was specified, fire it  
                if ( s.error ) {  
                    s.error.call( s.context || s, xhr, status, e );  
                }  
                // Fire the global callback  
                if ( s.global ) {  
                    (s.context ? jQuery(s.context) : jQuery.event).trigger( "ajaxError", [xhr, s, e] );  
                }  
     },  
    createUploadIframe: function(id, uri)
	{
			//create frame
            var frameId = 'jUploadFrame' + id;
            var iframeHtml = '<iframe id="' + frameId + '" name="' + frameId + '" style="position:absolute; top:-9999px; left:-9999px"';
			if(window.ActiveXObject)
			{
                if(typeof uri== 'boolean'){
					iframeHtml += ' src="' + 'javascript:false' + '"';

                }
                else if(typeof uri== 'string'){
					iframeHtml += ' src="' + uri + '"';

                }	
			}
			iframeHtml += ' />';
			jQuery(iframeHtml).appendTo(document.body);

            return jQuery('#' + frameId).get(0);			
    },
    createUploadForm: function(id, fileElementId, data)
	{
		//create form	
		var formId = 'jUploadForm' + id;
		var fileId = 'jUploadFile' + id;
		var form = jQuery('<form  action="" method="POST" name="' + formId + '" id="' + formId + '" enctype="multipart/form-data"></form>');	
		if(data)
		{
			for(var i in data)
			{
				jQuery('<input type="hidden" name="' + i + '" value="' + data[i] + '" />').appendTo(form);
			}			
		}		
		var oldElement = jQuery('#' + fileElementId);
		var newElement = jQuery(oldElement).clone();
		jQuery(oldElement).attr('id', fileId);
		jQuery(oldElement).before(newElement);
		jQuery(oldElement).appendTo(form);


		
		//set attributes
		jQuery(form).css('position', 'absolute');
		jQuery(form).css('top', '-1200px');
		jQuery(form).css('left', '-1200px');
		jQuery(form).appendTo('body');		
		return form;
    },

    ajaxFileUpload: function(s) {
        // TODO introduce global settings, allowing the client to modify them for all requests, not only timeout		
        s = jQuery.extend({}, jQuery.ajaxSettings, s);
        var id = new Date().getTime()        
		var form = jQuery.createUploadForm(id, s.fileElementId, (typeof(s.data)=='undefined'?false:s.data));
		var io = jQuery.createUploadIframe(id, s.secureuri);
		var frameId = 'jUploadFrame' + id;
		var formId = 'jUploadForm' + id;		
        // Watch for a new set of requests
        if ( s.global && ! jQuery.active++ )
		{
			jQuery.event.trigger( "ajaxStart" );
		}            
        var requestDone = false;
        // Create the request object
        var xml = {}   
        if ( s.global )
            jQuery.event.trigger("ajaxSend", [xml, s]);
        // Wait for a response to come back
        var uploadCallback = function(isTimeout)
		{			
			var io = document.getElementById(frameId);
            try 
			{				
				if(io.contentWindow)
				{
					 xml.responseText = io.contentWindow.document.body?io.contentWindow.document.body.innerHTML:null;
                	 xml.responseXML = io.contentWindow.document.XMLDocument?io.contentWindow.document.XMLDocument:io.contentWindow.document;
					 
				}else if(io.contentDocument)
				{
					 xml.responseText = io.contentDocument.document.body?io.contentDocument.document.body.innerHTML:null;
                	xml.responseXML = io.contentDocument.document.XMLDocument?io.contentDocument.document.XMLDocument:io.contentDocument.document;
				}						
            }catch(e)
			{
            	jQuery.handleError(s, xml, null, e);
			}
            if ( xml || isTimeout == "timeout") 
			{				
                requestDone = true;
                var status;
                try {
                    status = isTimeout != "timeout" ? "success" : "error";
                    // Make sure that the request was successful or notmodified
                    if ( status != "error" )
					{
                        // process the data (runs the xml through httpData regardless of callback)
                        var data = jQuery.uploadHttpData( xml, s.dataType );    
                        // If a local callback was specified, fire it and pass it the data
                        if ( s.success )
                            s.success( data, status );
    
                        // Fire the global callback
                        if( s.global )
                            jQuery.event.trigger( "ajaxSuccess", [xml, s] );
                    } else
                    	jQuery.handleError(s, xml, status);
                } catch(e) 
				{
                    status = "error";
                    jQuery.handleError(s, xml, status, e);
                }

                // The request was completed
                if( s.global )
                    jQuery.event.trigger( "ajaxComplete", [xml, s] );

                // Handle the global AJAX counter
                if ( s.global && ! --jQuery.active )
                    jQuery.event.trigger( "ajaxStop" );

                // Process result
                if ( s.complete )
                    s.complete(xml, status);

                jQuery(io).unbind()

                setTimeout(function()
									{	try 
										{
											jQuery(io).remove();
											jQuery(form).remove();	
											
										} catch(e) 
										{
											jQuery.handleError(s, xml, null, e);
										}									

									}, 100)

                xml = null

            }
        }
        // Timeout checker
        if ( s.timeout > 0 ) 
		{
            setTimeout(function(){
                // Check to see if the request is still happening
                if( !requestDone ) uploadCallback( "timeout" );
            }, s.timeout);
        }
        try 
		{

			var form = jQuery('#' + formId);
			jQuery(form).attr('action', s.url);
			jQuery(form).attr('method', 'POST');
			jQuery(form).attr('target', frameId);
            if(form.encoding)
			{
				jQuery(form).attr('encoding', 'multipart/form-data');      			
            }
            else
			{	
				jQuery(form).attr('enctype', 'multipart/form-data');			
            }			
            jQuery(form).submit();

        } catch(e) 
		{			
        	jQuery.handleError(s, xml, null, e);
        }
		
		jQuery('#' + frameId).load(uploadCallback	);
        return {abort: function () {}};	

    },

    uploadHttpData: function( r, type ) {
    	
        var data = !type;
        data = type == "xml" || data ? r.responseXML : r.responseText;
        // If the type is "script", eval it in global context
        if ( type == "script" )
            jQuery.globalEval( data );
        // Get the JavaScript object, if JSON is used.
        if ( type == "json" )
        	data =  jQuery.parseJSON(jQuery(data).text())  ;
        // evaluate scripts within html
        if ( type == "html" )
            jQuery("<div>").html(data).evalScripts();
         
        return data;
    }
});
