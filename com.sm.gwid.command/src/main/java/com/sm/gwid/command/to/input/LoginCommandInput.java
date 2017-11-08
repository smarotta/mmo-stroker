package com.sm.gwid.command.to.input;

import com.sm.gwid.command.to.CodecHelper;
import com.sm.gwid.command.to.Command;

public class LoginCommandInput extends Command {

	private String login;
	
	private String encodedPassword;
	
	@Override
	public Type getType() {
		return Type.C1;
	}

	@Override
	public SubType getSubType() {
		return SubType.B0;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEncodedPassword() {
		return encodedPassword;
	}

	public void setEncodedPassword(String encodedPassword) {
		this.encodedPassword = encodedPassword;
	}

	@Override
	public void deserialize(byte[] packet) {
		// C1 xx xx B0 [LL LL LL LL LL LL LL LL LL LL LL LL LL LL LL LL LL LL LL LL] [PW PW PW PW PW PW PW PW PW PW PW PW PW PW PW PW PW PW PW PW PW PW PW PW PW PW PW PW PW PW PW PW]
		login = CodecHelper.readStringUTF8(packet, 4, 20);
		encodedPassword = CodecHelper.readStringUTF8(packet, 24, 32);
	}

	@Override
	public byte[] serialize() {
		// C1 xx xx B0 [LL LL LL LL LL LL LL LL LL LL LL LL LL LL LL LL LL LL LL LL] [PW PW PW PW PW PW PW PW PW PW PW PW PW PW PW PW PW PW PW PW PW PW PW PW PW PW PW PW PW PW PW PW]
		byte [] data = new byte [56];
		
		//header
		data[0] = getType().getByteValue();
		CodecHelper.writeShort((short)data.length, data, 1);
		data[3] = getSubType().getByteValue();
		
		//login
		if (login != null) {
			CodecHelper.writeStringUTF8(login, data, 4);
		}
		
		//password
		if (encodedPassword != null) {
			CodecHelper.writeStringUTF8(encodedPassword, data, 24);
		}
		
		return data;
	}

}
