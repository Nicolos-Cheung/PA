package com.pingan.service.impl;

import com.pingan.dao.PCMdao;
import com.pingan.dao.impl.PCMdaoImpl;
import com.pingan.domain.PCMRequestBean;
import com.pingan.service.PCMSerivce;

public class PCMSerivceImpl implements PCMSerivce {
	private PCMdao dao = new PCMdaoImpl();

	@Override
	public void register(PCMRequestBean pcb) {

		dao.register(pcb);
	}

	@Override
	public String QueryIvectorPath(String person_id) {
		PCMRequestBean pcb = dao.Query(person_id);
		return pcb.getIvector_path();
	}

	@Override
	public String QueryIvectorVersion(String person_id) {
		PCMRequestBean pcb = dao.Query(person_id);
		return pcb.getIvector_version();
	}

	@Override
	public String QueryRegisterVoicePath(String person_id) {
		PCMRequestBean pcb = dao.Query(person_id);
		return pcb.getRegister_voice_path();
	}

	@Override
	public void update(PCMRequestBean pcb) {

		dao.update(pcb);
	}

	@Override
	public PCMRequestBean Query(String person_id) {
		return dao.Query(person_id);
	}

	@Override
	public boolean IsUserExist(String person_id) {

		PCMRequestBean pcb = dao.Query(person_id);
		if (pcb.getPerson_id()==null) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isAvailable(String person_id) {
		if (dao.Query(person_id).getAvailable().equals("1")) {
			return true;
		} else {
			return false;
		}
	}

}
