package com.kurumi.query;

import com.kurumi.util.StringUtil;

public class UserQuery extends PageQuery {

	 private String userCode;

    private String userName;

    private String userNickName;

    private String idNumber;

    private String telePhone;
    
    //查询结果集
	private Object queryDatas;

	public String getUserCode() {
		return StringUtil.meaningStr(userCode);
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return StringUtil.meaningStr(userName);
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserNickName() {
		return StringUtil.meaningStr(userNickName);
	}

	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}

	public String getIdNumber() {
		return StringUtil.meaningStr(idNumber);
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getTelePhone() {
		return StringUtil.meaningStr(telePhone);
	}

	public void setTelePhone(String telePhone) {
		this.telePhone = telePhone;
	}

	public Object getQueryDatas() {
		return queryDatas;
	}

	public void setQueryDatas(Object queryDatas) {
		this.queryDatas = queryDatas;
	}
    
    public boolean isEmpty(){
    	if(this.getUserCode() == null && this.getUserName() == null && this.getIdNumber() == null
    			&& this.getTelePhone() == null){
    		return true;
    	}
    	return false;
    }
    
}
