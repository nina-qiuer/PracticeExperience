package com.tuniu.gt.score.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;

import com.tuniu.gt.score.entity.SrQcTeamEntity;
import com.tuniu.gt.score.service.SrQcTeamService;


@Service("score_action-sr_qc_team")
@Scope("prototype")
public class SrQcTeamAction extends FrmBaseAction<SrQcTeamService, SrQcTeamEntity> {

	private static final long serialVersionUID = 1L;
	
	public SrQcTeamAction() {
		setManageUrl("sr_qc_team");
	}
	
	@Autowired
	@Qualifier("score_service_impl-sr_qc_team")
	public void setService(SrQcTeamService service) {
		this.service = service;
	};
	
	private List<SrQcTeamEntity> teamList;
	
	public List<SrQcTeamEntity> getTeamList() {
		return teamList;
	}

	public void setTeamList(List<SrQcTeamEntity> teamList) {
		this.teamList = teamList;
	}

	@SuppressWarnings("unchecked")
	public String execute() {
		teamList = (List<SrQcTeamEntity>) service.fetchList();
		return "sr_qc_team_config";
	}
	
	public String toAdd() {
		return "sr_qc_team_form";
	}
	
	public String add() {
		service.insert(entity);
		return "sr_qc_team_form";
	}
	
	public String toUpdate() {
		entity = (SrQcTeamEntity) service.get(entity.getId());
		return "sr_qc_team_form";
	}
	
	public String update() {
		service.update(entity);
		return "sr_qc_team_form";
	}
	
	public String delete() {
		service.delete(entity.getId());
		return "sr_qc_team_config";
	}
	
}
