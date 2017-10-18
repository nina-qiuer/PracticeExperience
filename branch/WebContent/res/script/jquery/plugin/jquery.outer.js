jQuery.fn.outer = function() { 
    return $($('<div></div>').html(this.clone())).html();
}