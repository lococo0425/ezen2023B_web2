package ezenweb.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


@Component
public class ChatSocket extends TextWebSocketHandler {

    //0. 접속 성공한 session들을 모아두기 (접속자 명단)
    private List<WebSocketSession> 접속명단 = new Vector<>();




    //1. 클라이언트 소켓의 접속이 성공일때
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("session = " + session);
        //1. 접속한 세션 정보를 리스트에 담기
        접속명단.add(session);
        System.out.println("접속명단 : " + 접속명단);
    }

    //2. 클라이언트로 부터 메시지를 받았을때
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("session = " + session + ", message = " + message);
        System.out.println("message : " + message.getPayload());
        //1. 접속명단의 클라이언트소켓들에게 메시지 보내기
        for(WebSocketSession 각클라이언트 : 접속명단){
            각클라이언트.sendMessage(message);

        }

    }

    //3. 클라이언트로 소켓과 접속 종료되었을때(session : 접속이 종료된 클라이언트 소켓 정보)

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        접속명단.remove(session);
    }
}
