function tree() {
	this.data={}
	this.config = {
		'plus':'<img src="'+RES_URL+'images/icon/tree/plus.gif" />',
		'plus_bottom':'<img src="'+RES_URL+'images/icon/tree/plus_bottom.gif" />',
		'minus':'<img src="'+RES_URL+'images/icon/tree/minus.gif" />',
		'minus_bottom':'<img src="'+RES_URL+'images/icon/tree/minus_bottom.gif" />',
		'join':'<img src="'+RES_URL+'images/icon/tree/join.gif" />',
		'join_bottom':'<img src="'+RES_URL+'images/icon/tree/join_bottom.gif" />', 
		'empty':'<img src="'+RES_URL+'images/icon/tree/empty.gif" />',
		'line':'<img src="'+RES_URL+'images/icon/tree/line.gif" />',
		'node_prefix':'tree_node_',
		'symbol_prefix':'symbol_',
		'childs_prefix':'childs_',
		'tree_id_len':parseInt(g_tree_id_len)
	};
	
	this.init = function(obj) {
		
		this.data = obj.data;
		for(var item in this.config) {
			if(typeof(obj[item]) != 'undefined' )  { 
				this.config[item] = obj[item];
			}
		}	
		
		//对结点加上{symbol}
		var curr_obj = {};
		var father_id = 0;
		var bottom_nodes = this.get_all_bottom();
		
		//取出第0层的最后一个结点
		var last_tree_id =  this.data[bottom_nodes[0]].tree_id;
		 
		for(var item in this.data) { 
			//显示第一级
			father_id = this.data[item].father_id; 
			curr_obj = $('#'+this.config.node_prefix+father_id+'_'+item);
			curr_obj.show();
			if(this.data[item].depth == 0 ) {
				this.data[item].css_display = '';
			} else {
				//将其加入到相应的结点
				var father_childs_node = $('#'+this.config.childs_prefix+father_id);
				father_childs_node.append(curr_obj); 
				this.data[item].css_display = 'none';   
			} 
			
			//菜单的展开状态
			this.data[item].css_expand = false;
			curr_obj = $('#'+this.config.symbol_prefix+item);
			
			
			
			/*计算出每个结点前面的html*/
			pre_html = '';
			if(this.data[item].depth > 0) { 
				
				if(this.data[item].tree_father_id.substr(0,this.config.tree_id_len) == last_tree_id) { 
					pre_html = this.config.empty;
				} else {
					pre_html = this.config.line;
				}
				
				for(i = 1;i< this.data[item].depth;i++) {  
					pre_html += this.config.empty;
				}
				
			}
			this.data[item].pre_html = pre_html;
		
			/*判断是否是最未结点*/
			if(bottom_nodes[father_id] == item) { 
				
				this.data[item].is_bottom = true;
				this.data[item].self_html = this.config.join_bottom;
			} else {
				this.data[item].is_bottom = false;
				this.data[item].self_html = this.config.join;
			}

			/*含有子级,初始化其标识并添加绑定点击事件*/ 
			if(this.data[item].has_child) {  
				
				if(this.data[item].is_bottom) {
					this.data[item].self_html = this.config.plus_bottom;
				} else {
					this.data[item].self_html = this.config.plus;
				}
				curr_obj.bind('click',{'father_id':father_id,'id':item,'obj':this},function(event) {var obj = event.data.obj;obj.click(event.data.father_id,event.data.id)});    
				if(this.data[item].menu_url == '') { // 不含 链接的菜单,也加上点击事件 
					curr_obj.nextAll("span").bind('click',{'father_id':father_id,'id':item,'obj':this},function(event) {var obj = event.data.obj;obj.click(event.data.father_id,event.data.id)}).css('cursor','pointer'); 
				} 
			} else {
				if(this.data[item].is_bottom) {
					this.data[item].self_html = this.config.join_bottom;
				} else {
					this.data[item].self_html = this.config.join;
				}
			}
			curr_obj.html(pre_html + this.data[item].self_html );  
		}
	}
}

tree.prototype.expand_all =  function() {
	$('#'+this.config.childs_prefix+id).css('display','');
}

/*
树为按顺序输出,所以,每个father_id 最后一次值 即为末结点
*/
tree.prototype.get_all_bottom = function() {
	var ret = {};
	var tmp_obj = {};
	var now_seq = 0;
	var now_id = 0;
	for(var it in this.data) {
		
		this.data[it].sequence = parseInt(this.data[it].sequence);
		it = parseInt(it); 
			
		if(typeof tmp_obj[this.data[it].father_id] == 'undefined') {
			tmp_obj[this.data[it].father_id] =  this.data[it].sequence;
			ret[this.data[it].father_id] = it;
			
		} else {  
			
			if(tmp_obj[this.data[it].father_id] < this.data[it].sequence ||  (tmp_obj[this.data[it].father_id] == this.data[it].sequence  && ret[this.data[it].father_id] <it) ) { 
				tmp_obj[this.data[it].father_id] = this.data[it].sequence;
				ret[this.data[it].father_id] = it;  
			}
		}
	}
	
	return ret;
}

tree.prototype.click=function(father_id,id) {
	var css_expand = false;
	if(this.data[id].css_expand) {  
		$('#'+this.config.symbol_prefix+id).html(this.data[id].pre_html + this.data[id].self_html);    
	} else {
		css_expand = true; 
		var self_html = '';
		if(this.data[id].is_bottom) {
			self_html = this.config.minus_bottom;
		} else {
			self_html = this.config.minus;
		}
		$('#'+this.config.symbol_prefix+id).html(this.data[id].pre_html + self_html);   
	}
	this.show_hide_childs(father_id,id);
	this.data[id].css_expand = css_expand; 
}

tree.prototype.show_hide_childs=function(father_id,id) { 
	var display = this.data[id].css_expand?'none':'';
	$('#'+this.config.childs_prefix+id).css('display',display);  
}

/*
html格式如下 

	<div style="display:{v.display}" id="tree_node_{v.father_id}_{v.id}"> 
		{v.add}
		<span style="cursor:pointer;display:inline-block;vertical-align:middle;" id="symbol_{v.id}">
			{v.symbol}
		</span>   
		<span style="vertical-align:middle;cursor:pointer;">
			{v.menu_name}
		</span>     
		<div id='childs_{v.id}'></div>
	</div> 
	


<script language="javascript" src="{this->settings.res_url}script/tree.js" ></script>
<script language="javascript" src="{this->settings.web_url}cache/menu.js" ></script>
$(document).ready(function(){
	var tree1 = new tree(); 
	var config = {
		data:g_menu_obj 
	};
	tree1.init(config);  
})

可配置 属性
	this.config = {
		'plus':'<img src="'+RES_URL+'images/icon/tree/plus.gif" />',
		'plus_bottom':'<img src="'+RES_URL+'images/icon/tree/plus_bottom.gif" />',
		'minus':'<img src="'+RES_URL+'images/icon/tree/minus.gif" />',
		'minus_bottom':'<img src="'+RES_URL+'images/icon/tree/minus_bottom.gif" />',
		'join':'<img src="'+RES_URL+'images/icon/tree/join.gif" />', 
		'join_bottom':'<img src="'+RES_URL+'images/icon/tree/join_bottom.gif" />', 
		'empty':'<img src="'+RES_URL+'images/icon/tree/empty.gif" />',
		'line':'<img src="'+RES_URL+'images/icon/tree/line.gif" />',
		'node_prefix':'tree_node_',
		'symbol_prefix':'symbol_',
		'childs_prefix':'childs'
	};

*/