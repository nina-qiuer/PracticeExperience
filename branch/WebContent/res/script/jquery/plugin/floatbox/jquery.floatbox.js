$.extend({ 
	
	floatbox:function(settings) {   
		
		 this.html = '';
		 this.settings  = {
			has_title:true,
			width:false,
			height:false,
			type:'id', //可选参数id,html,ajax三种形式
			content:'', 
			ajax_param:'', 
			title:'',
			iframe:false,
			callback:false,
		};
		
		this.init = function(settings) {
			this.mergeSettings(settings);
			this.createLayer();
			this.getData();
			this.createBox();
			this.setData(); 
			//	$('#fbox_box').show(); 
		}
		
		
		this.mergeSettings = function(settings) {	 
			for(var it in settings) {
				this.settings[it] = settings[it];
			}
		}
		
		this.setData = function() {
			var title =  this.settings.title; 
			$('#fbox_title').html(title); 
			if(this.settings.iframe) {			
				$("#fbox_content_iframe").get(0).contentDocument.write(this.html); 
			} else {
				$('#fbox_content').append(this.html);  
			}
			this.setPos();
			
			if(this.settings.callback != '') {
				eval(this.settings.callback);
			}
			
			if(this.settings.type == 'id') {
				this.html.show(); 
			}
			
		}
		
		this.getData = function() {
			var html = '';
			switch(this.settings.type) { 
				case 'id':
					this.html =  $('#'+this.settings.content); 
					
				break;
				case 'ajax': 
					$.ajax({
						type:'post', 
						async:false,  
						url:this.settings.content,
						data:this.settings.ajax_param,
						success:function(msg) {
							
							html= msg;  
							
						},
						error:function() {
							alert('error');
						}
					});
					
					this.html = html; 
					
				break;
				default:
					this.html = this.settings.content;
				
			}
			
		}
		
		this.setPos = function() {
//			var left = parseInt($('#fbox_box').css('left')); 
			var width = parseInt($('#fbox_box').width());
//			left = left - parseInt(width/2);  
			
			$('#fbox_box').css('marginLeft','-'+ parseInt(width/2));
			
			
//			var top = parseInt($('#fbox_box').css('top'));
			var height = parseInt($('#fbox_box').height());  
//			top = top - parseInt(height/2);
			$('#fbox_box').css('marginTop','-'+parseInt(height/2));  
		}
		
		
		this.createLayer = function() {
			$('body *:first').before('<div id="fbox_layer" style="display: block; opacity: 0.9; cursor: pointer;"></div>'); 
		};
		
		this.createBox = function() {
			$('#fbox_layer').after('<div id="fbox_box" style=""></div>'); 
			$('#fbox_box').append('<div id="fbox_title_container"></div>');
			$('#fbox_title_container').append('<div id="fbox_title" style="float:left; padding-left:2px;"></div>'); 
			$('#fbox_title_container').append('<div id="fbox_close" style="float:right;cursor:pointer; padding-right:2px;">×</div>');
			$('#fbox_box').append('<div id="fbox_content" style="clear:both;"></div>');  
			
			if(this.settings.width) {
				$('#fbox_content').width(this.settings.width); 
			}
			if(this.settings.height) {
				$('#fbox_content').height(this.settings.height); 
			}
			
			if(this.settings.iframe) { 
				$('#fbox_content').append('<iframe id="fbox_content_iframe" scrolling="No" name="fbox_content_iframe" height="100%" width="100%" frameborder="0" src="about:blank"></iframe>');    
				 
			} 
			
			$('#fbox_close').bind('click',{type:this.settings.type,content:this.settings.content},function(event){
				
				if(event.data.type == 'id') {
					$('#'+event.data.content).hide(); 
					$('body').append($('#'+event.data.content));  
				}
				$('#fbox_layer').remove(); 
				$('#fbox_box').remove(); 
			})
		}
		
		this.init(settings); 
		
	}
});