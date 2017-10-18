package {?app_dot_dir}{?app_id}.entity; 
import java.io.Serializable;
import java.util.Date;
import org.springframework.stereotype.Component;
import com.tuniu.gt.frm.entity.EntityBase;


public class {?ufirst_table_name}Entity extends EntityBase implements Serializable {
	private static final long serialVersionUID = {?serialVersionUID};
<!-- if enumMap -->
<!-- loop enumMap -->
	public static enum {?field_type} {
		{?enum_declare}
	};
<!-- end loop -->
<!-- end if -->
<!-- loop fieldMap -->
	private {?field_type} {?standard_field_name}{?field_default}; //{?db_comment}
<!-- end loop -->

<!-- loop fieldMap -->
	public {?field_type} get{?ucfirst_field_name}() {
		return {?standard_field_name};
	}
	public void set{?ucfirst_field_name}({?field_type} {?standard_field_name}) {
		this.{?standard_field_name} = {?standard_field_name}; 
	}
<!-- end loop -->	
}