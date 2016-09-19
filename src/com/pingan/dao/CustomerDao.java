package com.pingan.dao;

import com.pingan.domain.Ivector;

public interface CustomerDao {

	void register(Ivector c);

	Ivector find(String telnum);

	void update(Ivector c);

	void remove(String telnum);

}
