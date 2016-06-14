package com.page.controller;

import java.io.*;
import java.util.*;

import javax.websocket.server.ServerEndpoint;
import javax.websocket.Session;
import javax.websocket.OnOpen;
import javax.websocket.OnMessage;
import javax.websocket.OnError;
import javax.websocket.OnClose;
import javax.websocket.CloseReason;

import com.room.model.RoomService;
import com.room.model.RoomVO;

import net.sf.json.JSONObject;

@ServerEndpoint("/EchoServer")
public class RoomStateEcho {
	
private static final Set<Session> allSessions = Collections.synchronizedSet(new HashSet<Session>());
	
	@OnOpen
	public void onOpen(Session userSession) throws IOException {
		allSessions.add(userSession);
		System.out.println(userSession.getId() + ": 已連線");
//		userSession.getBasicRemote().sendText("WebSocket 連線成功");
	}

	
	@OnMessage
	public void onMessage(Session userSession, String message) {
		for (Session session : allSessions) {
			if (session.isOpen())
				session.getAsyncRemote().sendText(message);
		}
		System.out.println("Message received: " + message);
		String jsonStr=message.replaceAll("[{\"}]", "");
		System.out.println(jsonStr);
		String[] jsonStrAry=jsonStr.split(",");
		RoomService roomService=new RoomService();
		System.out.println(jsonStrAry[0].substring(jsonStrAry[0].indexOf(":")+1));
		System.out.println(jsonStrAry[1].substring(jsonStrAry[1].indexOf(":")+1));
		System.out.println(jsonStrAry[2].substring(jsonStrAry[2].indexOf(":")+1));
		Integer roomNo=new Integer(jsonStrAry[0].substring(jsonStrAry[0].indexOf(":")+1));
		String roomState =jsonStrAry[1].substring(jsonStrAry[1].indexOf(":")+1);
		Integer hostelOrderDetailNo=new Integer(jsonStrAry[2].substring(jsonStrAry[2].indexOf(":")+1));
		RoomVO roomVO=roomService.getOneRoom(roomNo);
		roomService.updateRoom(roomNo, roomVO.getHostelNo(), roomVO.getRoomTypeNo()
				, hostelOrderDetailNo, roomState);
		
	}
	
	@OnError
	public void onError(Session userSession, Throwable e){
//		e.printStackTrace();
	}
	
	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		allSessions.remove(userSession);
		System.out.println(userSession.getId() + ": Disconnected: " + Integer.toString(reason.getCloseCode().getCode()));
	}

 
}

