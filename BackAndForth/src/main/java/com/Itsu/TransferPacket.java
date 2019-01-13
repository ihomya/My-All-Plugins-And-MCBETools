package com.Itsu;

import cn.nukkit.network.protocol.DataPacket;

public class TransferPacket extends DataPacket{

	public static final byte NETWORK_ID = ProtocolInfo2.TRANSFER_PACKET;

	public String address;
	public short port;

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

	public void decode(){

	}

	public void encode(){
		this.reset();
		this.putString(this.address);
		this.putLShort(this.port);
	}

}
