package com.twokeys.moinho.dto;

public class UserUpdateDTO extends UserInsertDTO {
	private static final long serialVersionUID = 1L;
	
	private boolean updatePassword;
	
	UserUpdateDTO(){
		super();
	}

	public boolean isUpdatePassword() {
		return updatePassword;
	}

	public void setUpdatePassword(boolean updatePassword) {
		this.updatePassword = updatePassword;
	}
	
	
}
