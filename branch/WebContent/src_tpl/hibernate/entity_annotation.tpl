package hbm; 
import java.io.Serializable;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "{tblpre}{table_name}")
@Cache(usage = CacheConcurrencyStrategy.NONE)
@Component("hbm-{table_name}_entity")
public class {ufirst_table_name}Entity implements Serializable {
<!-- head end -->
public static enum {field_type} {
	{enum_declare}
};
<!-- enum_declare end -->
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
<!-- pk end -->
	@Enumerated(EnumType.STRING)
<!-- enum annotation end -->
	@Column(name = "{field_name}", nullable = false,columnDefinition="{field_def}")
<!-- column end -->
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