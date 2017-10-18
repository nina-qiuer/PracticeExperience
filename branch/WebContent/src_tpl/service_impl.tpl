package {?app_dot_dir}{?app_id}.service{?module_dot_dir}.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.{?app_id}.dao.impl.{?ufirst_table_name}Dao;
import com.tuniu.gt.{?app_id}.service{?module_dot_dir}.I{?ufirst_table_name}Service;
@Service("{?app_id}_service{?module_underline_dir}_impl-{?table_name}")
public class {?ufirst_table_name}ServiceImpl extends ServiceBaseImpl<{?ufirst_table_name}Dao> implements I{?ufirst_table_name}Service {
	@Autowired
	@Qualifier("{?app_id}_dao_impl-{?table_name}")
	public void setDao({?ufirst_table_name}Dao dao) {
		this.dao = dao;
	};
}