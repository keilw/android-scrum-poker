package com.rmgtug.scrumpoker.net;

import java.net.DatagramPacket;

public interface ServerPacketListener {

	public void OnReceivedDataPackage(DatagramPacket dataPackage);
	
}
