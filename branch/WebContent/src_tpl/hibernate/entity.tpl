package hbm; 
import java.io.Serializable;
@Component("hbm-{table_name}_entity");
public class {ufirst_table_name} implements Serializable {
<!-- head end -->
	private {field_type} {field_name};
<!-- properties end -->	
	public {field_type} get{ucfirst_field_name}() {
		return {field_name};
	}
	public void set{ucfirst_field_name}({field_type} {field_name}) {
		this.{field_name} = {field_name};
	}
<!-- methods end -->	
}