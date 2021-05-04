package com.revature.group2.project2.services;

import com.revature.group2.project2.beans.User;

public interface UserService {
	
	//-As an Admin, I can remove (ban) a user from the system.
	//--As a Mod, I can remove (ban) a user from the system.
	void removeUser(User u);
}
